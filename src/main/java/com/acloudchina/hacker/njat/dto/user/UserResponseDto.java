package com.acloudchina.hacker.njat.dto.user;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 22:46
 **/
@Data
public class UserResponseDto extends ResponseDto {
    private UserResponseBodyDto body;
}
