package com.acloudchina.hacker.njat.dto.common;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 21:51
 **/
@Data
public abstract class ResponseDto<T> {
    private ResponseHeaderDto header;
    public abstract T getBody();
}

