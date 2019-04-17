package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.RequestDto;
import com.acloudchina.hacker.njat.dto.common.RequestHeaderDto;
import com.acloudchina.hacker.njat.utils.EncryptionUtils;
import com.acloudchina.hacker.njat.utils.GetKeyUtils;
import com.acloudchina.hacker.njat.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 21:57
 **/
@Slf4j
public abstract class TransportBaseService {
    private static final JsonMapper jsonMapper = JsonMapper.alwaysMapper();
    private static final RequestHeaderDto headerDto = new RequestHeaderDto();

    @Autowired
    private RestTemplate restTemplate;

    public <T> T exchange(Object request, String requestUrl, Class<T> type) {
        RequestDto requestDto = new RequestDto();
        requestDto.setBody(EncryptionUtils.encrypt2Aes(jsonMapper.toJson(request), GetKeyUtils.getKey()));
        requestDto.setHeader(headerDto);

        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestDto, String.class);
        String responseJsonStr = EncryptionUtils.decodeFromAes(response.getBody(), GetKeyUtils.getKey());

        return jsonMapper.fromJson(responseJsonStr, type);
    }
}
