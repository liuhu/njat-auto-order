package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.venue.*;
import com.acloudchina.hacker.njat.dto.venue.order.SellOrderDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderQueryDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseDto;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 23:10
 **/
@Service
@Slf4j
public class VenueTransportService extends TransportBaseService {

    /**
     * 缓存场地订单信息
     */
    private Cache<String, Map<Integer, List<SellOrderDto>>> orderCache = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .build();

    /**
     * 查询场地类型信息
     * @return
     */
    public String getVenueTypeCodeByName(String venueTypeName) {
        VenueTypeQueryDto queryDto = new VenueTypeQueryDto();
        VenueTypeResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_TYPE_LIST, VenueTypeResponseDto.class);
        if (null == responseDto
                || null == responseDto.getBody()
                || null == responseDto.getBody().getVenueTypeList()) {
            throw new RuntimeException("无法获取场地列表");
        }
        Optional<VenueTypeDto> venueTypeDtoOptional = responseDto.getBody().getVenueTypeList().stream().filter(x -> x.getVenueTypeName().equals(venueTypeName)).findAny();
        if (venueTypeDtoOptional.isPresent()) {
            return venueTypeDtoOptional.get().getVenueTypeCode();
        }
        throw new RuntimeException("无法获取场地Code");
    }

    /**
     * 获取场馆列表信息
     * @return
     */
    public VenueListEntityDto getVenueInfoByCode(String venueTypeCode) {
        VenueListQueryDto queryDto = new VenueListQueryDto();
        queryDto.setVenueTypeCode(venueTypeCode);
        VenueListResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_LIST, VenueListResponseDto.class);
        if (null == responseDto
                || null == responseDto.getBody()
                || null == responseDto.getBody().getVenueEntityList()
                || responseDto.getBody().getVenueEntityList().isEmpty()) {
            throw new RuntimeException("获取场馆信息异常");
        }
        return responseDto.getBody().getVenueEntityList().get(0);
    }

    /**
     * 查询场馆详细信息
     * @param venueId
     * @return
     */
    public VenueEntityDto getVenueEntityInfo(String venueId) {
        VenueEntityQueryDto queryDto = new VenueEntityQueryDto();
        queryDto.setVenueId(venueId);
        VenueEntityResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_INFO, VenueEntityResponseDto.class);
        if (null == responseDto
                || null == responseDto.getBody()
                || null == responseDto.getBody().getVenueEntity()) {
            throw new RuntimeException("获取场馆详细信息异常");
        }
        return responseDto.getBody().getVenueEntity();
    }

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
            log.error("场地订单信息获取异常, queryDto = {}", queryDto);
            return null;
        }
    }

    /**
     * 生成场地订单信息
     * @param queryDto
     * @return
     */
    private String buildOrderKey(VenueOrderQueryDto queryDto) {
        return queryDto.getDate() + ":" + queryDto.getUserId();
    }

}
