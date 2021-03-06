package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.order.*;
import com.acloudchina.hacker.njat.dto.task.TaskInfoDto;
import com.acloudchina.hacker.njat.dto.venue.order.SellOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-22 21:59
 **/
@Service
@Slf4j
public class OrderTransportService extends TransportBaseService {

    /**
     * 处理创建订单
     * @param taskInfoDto 任务信息
     * @param sellOrderDtos 场地信息
     * @return 支付单编号
     */
    public String createOrder(final TaskInfoDto taskInfoDto,
                              final List<SellOrderDto> sellOrderDtos) {
        if (null ==  sellOrderDtos || sellOrderDtos.isEmpty()) {
            log.warn("场地订单信息为空, taskInfoDto = {}, sellOrderDtos = {}", taskInfoDto, sellOrderDtos);
            throw new RuntimeException("场地订单信息为空!");
        }
        List<SellOrderDto> pendingOrder = sellOrderDtos.stream()
                // 筛选出时间段
                .filter(x -> taskInfoDto.getTimes().contains(x.getStartDate()))
                // 筛选出未被预定的
                .filter(x -> x.getIsBook() == 0)
                .collect(Collectors.toList());
        if (pendingOrder.size() != taskInfoDto.getTimes().size()) {
            log.warn("场地已被预定, orderInfo = {}", sellOrderDtos.stream().filter(x -> taskInfoDto.getTimes().contains(x.getStartDate())).collect(Collectors.toList()));
            throw new RuntimeException("场地已被预定");
        }

        List<GoodOrderDto> goodOrderList = pendingOrder.stream().map(x -> {
            GoodOrderDto goodOrderDto = new GoodOrderDto();
            goodOrderDto.setGoodCutPrice(String.valueOf(x.getOrderPrice()));
            goodOrderDto.setGoodFkid(x.getSellOrderMxId());
            goodOrderDto.setGoodId(x.getGoodId());
            goodOrderDto.setGoodName(GoodOrderDto.buildGoodName(taskInfoDto.getDate(), x.getCertainVenueName(), x.getStartDate(), x.getEndDate()));
            goodOrderDto.setGoodPrice(String.valueOf(x.getOrderPrice()));
            goodOrderDto.setVenueSaleId(x.getVenueSaleId());
            return goodOrderDto;
        }).collect(Collectors.toList());
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        Integer totalAmount = pendingOrder.stream().mapToInt(SellOrderDto::getOrderPrice).sum();
        CreateOrderRequestBodyDto orderRequest = new CreateOrderRequestBodyDto();
        orderRequest.setOrgCode(taskInfoDto.getOrgCode());
        orderRequest.setSourceFkId(taskInfoDto.getVenueId());
        orderRequest.setSourceName(taskInfoDto.getVenueName());
        orderRequest.setGoodOrderList(goodOrderList);
        orderRequest.setTotalAmount(decimalFormat.format(totalAmount));
        orderRequest.setTotalCutAmount(decimalFormat.format(totalAmount));
        orderRequest.setUserId(taskInfoDto.getUserId());
        CreateOrderResponseDto responseDto = exchange(orderRequest, Constants.CREATE_ORDER, CreateOrderResponseDto.class);

        if (null == responseDto.getHeader()
                || responseDto.getHeader().getRetStatus() != 0
                || null == responseDto.getBody()
                || StringUtils.isBlank(responseDto.getBody().getBookNumber())) {
            log.error("创建订单异常, errorMsg = {}, responseDto = {}", null != responseDto ? null != responseDto.getHeader() ? responseDto.getHeader().getRetMessage() : null : null, responseDto);
            throw new RuntimeException("创建订单异常");
        }
        return responseDto.getBody().getBookNumber();
    }

    /**
     * 支付订单
     * @param bookNumber
     * @param taskInfoDto
     */
    public void payOrder(String bookNumber, TaskInfoDto taskInfoDto) {
        log.info("支付订单, bookNumber = {}, taskInfoDto = {}", bookNumber, taskInfoDto);
        PayOrderRequestBodyDto requestDto = new PayOrderRequestBodyDto();
        requestDto.setBookNumber(bookNumber);
        requestDto.setUserId(taskInfoDto.getUserId());
        requestDto.setPaytypeId(taskInfoDto.getPayCardId());
        requestDto.setOrgCode(taskInfoDto.getOrgCode());
        PayOrderResponseDto responseDto = exchange(requestDto, Constants.PAY_ORDER, PayOrderResponseDto.class);
        if (null == responseDto
                || null == responseDto.getHeader()
                || responseDto.getHeader().getRetStatus() != 0) {
            log.error("订单支付失败, requestDto = {}, errorMsg = {}", requestDto, null != responseDto ? null != responseDto.getHeader() ? responseDto.getHeader().getRetMessage() : null : null);
            throw new RuntimeException("订单支付失败");
        }

        PayCheckRequestDto payCheckRequestDto = new PayCheckRequestDto();
        payCheckRequestDto.setOrderId(bookNumber);
        payCheckRequestDto.setPassWord(taskInfoDto.getPayPass());
        payCheckRequestDto.setPaytypeId(taskInfoDto.getPayCardId());
        payCheckRequestDto.setUserId(taskInfoDto.getUserId());
        PayCheckResponseDto countDownResponseDto = exchange(payCheckRequestDto, Constants.PAY_CHECK, PayCheckResponseDto.class);

        if (null == countDownResponseDto
                || null == countDownResponseDto.getHeader()
                || countDownResponseDto.getHeader().getRetStatus() != 0) {
            log.error("订单支付校验失败, payCheckRequestDto = {}, errorMsg = {}", payCheckRequestDto, null != countDownResponseDto ? null != countDownResponseDto.getHeader() ? countDownResponseDto.getHeader().getRetMessage() : null : null);
            throw new RuntimeException("订单支付校验失败");
        }
    }
}
