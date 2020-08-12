### 背景
我司安排每周四 19:00 ~ 21:00 组织一次羽毛球活动。我们定的场馆是支持手机 APP 预订的，在办卡的时候得知场馆实在是太火爆了，每天早上 6 点开抢，热门时段的场地在 4 秒之内绝对秒杀完。为了保证活动每次都能够正常组织，我准备为大家写个小工具，实现高成功率的自动抢购。程序员就是有个好处，遇到问题会想着制造工具完成。
考虑到安全性问题，文章一些私密信息都隐匿了，这里主要是介绍了我的思路，供大家参考。

### 抓包分析
通过分析手机 APP 和 服务端的请求响应报文，来了解需要模拟的报文结构，最终实现自动下单抢购。这里介绍一款神器 `Burp Suite`，他是一款安全渗透测试工具，可以拦截、分析、修改、重放报文，还支持 HTTPS ，这些功能我屡试不爽。

Step1. 设置代理
在 `Burp Suite` 中配置代理信息，并在手机的网络设置中配置该代理信息，使手机上产生的请求响应经过 `Burp Suite` 。
<img src="https://img.liuhu.me/2019/06/snatch-ticket-01.png" width="400px">

Step2. 分析报文
如下是订购场地的请求和响应报文。
请求是Json格式的，由 header 和 body 两部分组成；header中包含了一些终端信息，还有程序员特别敏感的 `accessToken` 字段。这个貌似是认证用的，经过修改报文的 `accessToken` 内容，然后重放测试，订单依旧可以创建成功，基本可以说明 `accessToken` 对于安全认证是一点作用都没有。
麻烦是 body 和 Response 的内容都是加密的，看不出一点规律，仅能猜测出应该使用的是对称加密算法。
Request：
<img src="https://img.liuhu.me/2019/06/snatch-ticket-02.png" width="400px">

Response：
<img src="https://img.liuhu.me/2019/06/snatch-ticket-03.png" width="400px">

### 逆向工程
现在问题的关键就在于解密算法了，唯一的办法就是破解手机客户端，通过逆向工程，反编译出其加解密算法。
Step1. Android apk 逆向工程
> a. 解压 apk 文件，获取 classes.dex 文件
> b. 使用 [dex2jar](https://sourceforge.net/projects/dex2jar/) 逆向工程得到 jar 文件 ` d2j-dex2jar.sh classes.dex ` 

Step2. 使用 [JD-GUI](http://java-decompiler.github.io/) 反编译 jar 包并导出所有反编译代码文件，然后在IDE中打开，方便搜索。
Step3. 从请求 URL 为入口，找出加解密算法。

最终确认了加解密算法是通过ASE对称加密实现的，还有秘钥是网站的域名。

解密的订购请求信息如下:
``` json
{
    "goodOrderList": [
        {
            "cardCode": "",
            "goodCount": "1",
            "goodCutPrice": "60",
            "goodFkid": "3871728170121????",
            "goodId": "20387172816755710490????",
            "goodName": "06月03日~3号场-20:00~21:00",
            "goodPrice": "60",
            "goodType": "1",
            "venueSaleId": "9d84b1a709b64f72bdf543aedf50????"
        }
    ],
    "isCollect": "0",
    "orgCode": "fe9d896bf6ad4ff88d69d7c477c9????",
    "paySource": "1",
    "sourceFkId": "6049785525????",
    "sourceName": "游泳馆羽毛球",
    "totalAmount": "60.0",
    "totalCutAmount": "60.0",
    "userId": "34693????6029????"
}
```
进行到这里，整个项目从 APP 端到服务端的交互方式已经基本清晰，安全性校验几乎没有，这样小程序实现起来会简单很多。

### 订购流程梳理
最关键的问题解决了，接下来就是进行一次完整订购流程，分析哪些请求需要模拟。
如下时序图是简化版的订购流程：
<img src="https://img.liuhu.me/2019/06/snatch-ticket-04.png" width="300px">


### 设计、编码
* 使用 Spring Schedule + JUC ThreadPoolExecutor 实现定时并发抢购
``` java
    /**
     * 处理抢购任务
     */
    @Scheduled(zone = "Asia/Shanghai", cron = "${schedule.autoPanicOrder.cron}")
    public void panicScheduleTask() {
        taskMap.values().forEach(
                x -> {
                    // 抢购日期提前两天
                    String dateStr = LocalDateTime.now().plusDays(2).format(DATE_TIME_FORMATTER);
                    if (dateStr.equals(x.getDate())) {
                        threadPoolExecutor.execute(() -> panic(x));
                    } else {
                        log.info("还未到达抢购时间, x = {}", x);
                    }
                }
        );
    }
```

* 使用 `ReentrantLock` 实现对任务细粒度锁控制，防止同一任务在短时间内并发执行
``` java
    /**
     * 执行抢购任务
     * @param taskDto
     */
    private void panicTask(OrderTaskDto taskDto) {
        ReentrantLock lock = taskLockMap.get(taskDto.getOrderTaskId());
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                // 抢购逻辑
            }
        } catch (Exception e) {
            log.error("抢购任务异常, taskDto = {}, e = {}", taskDto, e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
```

* 使用 `Google Guava Cache` 实现对场地信息的缓存, 减少抢购时的请求次数，减少在网络IO上的等待
``` java
    /**
     * 缓存场地订单信息
     */
    private Cache<String, Map<Integer, List<SellOrderDto>>> orderCache = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .build();

   /**
     * 根据时间查询场地订单列表
     * @param queryDto
     * @return
     */
    public Map<Integer, List<SellOrderDto>> getVenueOrder(VenueOrderQueryDto queryDto) {
        try {
            return orderCache.get(buildOrderKey(queryDto), () -> {
                VenueOrderResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_SELL_ORDER, VenueOrderResponseDto.class);
                if (null == responseDto || null == responseDto.getBody()) {
                    return null;
                }
                Map<Integer, List<SellOrderDto>> orderMap = responseDto.getBody().getSellOrderMap();
                if (null == orderMap || orderMap.isEmpty()) {
                    return null;
                }
                return orderMap;
            });
        } catch (Exception e) {
            log.error("场地订单信息获取异常, queryDto = {}, e = {}", queryDto, e.getMessage());
            return null;
        }
    }         
```

* 封装请求和响应的加解密逻辑, 实现请求自动加解密（ASE对称加密）。
``` java
@Slf4j
public abstract class TransportBaseService {
    private static final JsonMapper JSON_MAPPER = JsonMapper.alwaysMapper();
    private static final RequestHeaderDto HEADER = new RequestHeaderDto();
    private static final AtomicLong ADDER = new AtomicLong();
    /**
     * 模拟20个终端
     */
    private static final Map<Long, String> DEVICE_ID_MAP = LongStream.rangeClosed(0, 19).sorted()
            .collect(HashMap::new, (m, v) -> m.put(v, UUID.randomUUID().toString()), HashMap::putAll);

    @Autowired
    private RestTemplate restTemplate;


    public <T> T exchange(Object request, String requestUrl, Class<T> type) {
        RequestDto requestDto = new RequestDto();
        requestDto.setBody(EncryptionUtils.encrypt2Aes(JSON_MAPPER.toJson(request), GetKeyUtils.getKey()));
        // 突破流控限制
        HEADER.setDeviceId("8" + DEVICE_ID_MAP.get(ADDER.incrementAndGet() % (DEVICE_ID_MAP.size())));
        requestDto.setHeader(HEADER);

        try {
            long startTime = System.currentTimeMillis();
            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestDto, String.class);
            long endTime = System.currentTimeMillis();
            log.info("请求: {}, 耗时: {}ms", requestUrl, endTime - startTime);
            String responseJsonStr = EncryptionUtils.decodeFromAes(response.getBody(), GetKeyUtils.getKey());
            return JSON_MAPPER.fromJson(responseJsonStr, type);
        } catch (RestClientException e) {
            log.error("接口调用异常, requestUrl = {}, e = {}", requestUrl, e.getMessage());
            throw new RuntimeException("接口调用异常!");
        }
    }
}
```


### 部署、实测
部署
* 部署的服务器开启 NTP 时间同步
* 使用 `hosts` 配置域名和 IP 的对应关系，减少请求时域名解析的时间

实测
在实测中效果非常好，根据配置的场地优先级列表，每次都可以抢到心仪的场地。用了快一个月了，可以保证想要那个场地就能抢到那个场地的效果，成功率目前是 100% 。机器抢和人肉抢就是不一样哇。


### 总结感悟
本次实战发现如下几个安全性问题:
1. APP 端和服务端通信不是 HTTPS 安全通信, 很容易截获修改
2. 验证码在 APP 本地校验，非服务端验证
3. 服务端流控限制规则太简单，仅通过请求中的 deviceId 识别用户, 只需要在请求中 deviceId 改成 UUID 可轻松突破限制
4. AES 的 PSK 是写死在 APP 中，没有进行安全加固
5. APP 端是不可以跨场地预定的，从接口设计上看貌似可以 (不敢随意尝试)
6. 创建订单，价格是在请求中传入的，貌似可以改价（不敢随意尝试）

安全问题无小事，开发的时候一定要在安全方面的问题考虑周全，否则很容易出大问题。现在很多初创公司都把精力放在业务发展上，在信息安全上的投入非常少，我司也不例外，其实是需要有一定的投入的，防止在事后救火，到时候损失就更大了。就比方是最近出现的安全事件，'易到用车' 的核心数据都被加密了，业务系统直接瘫痪。企业要是死在网络安全上，实在是可惜呀。
