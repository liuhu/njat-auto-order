package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.RequestDto;
import com.acloudchina.hacker.njat.dto.common.RequestHeaderDto;
import com.acloudchina.hacker.njat.utils.EncryptionUtils;
import com.acloudchina.hacker.njat.utils.GetKeyUtils;
import com.acloudchina.hacker.njat.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 21:57
 **/
@Slf4j
public abstract class TransportBaseService {
    private static final JsonMapper JSON_MAPPER = JsonMapper.alwaysMapper();
    private static final RequestHeaderDto HEADER = new RequestHeaderDto();
    private static final AtomicLong ADDER = new AtomicLong();
    /**
     * 模拟10个终端
     */
    private static final Map<Long, String> DEVICE_ID_MAP = LongStream.rangeClosed(0, 9).sorted()
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
            ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestDto, String.class);
            String responseJsonStr = EncryptionUtils.decodeFromAes(response.getBody(), GetKeyUtils.getKey());
            return JSON_MAPPER.fromJson(responseJsonStr, type);
        } catch (RestClientException e) {
            log.error("接口调用异常, requestUrl = {}, e = {}", requestUrl, e.getMessage());
            throw new RuntimeException("接口调用异常!");
        }
    }
}
