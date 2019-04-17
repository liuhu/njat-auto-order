package com.acloudchina.hacker.njat.dto.common;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 23:04
 **/
@Data
public class RequestDto {
    /**
     * 转json在再密
     */
    private String body;

    private RequestHeaderDto header;
}
