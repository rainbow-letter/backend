package com.rainbowletter.server.notification.adapter.out.infrastructure;

import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface AligoAlimTalkClient {

    @PostExchange("/akv10/alimtalk/send/")
    SendAlimTalkResponse sendAlimTalk(@RequestBody MultiValueMap<String, String> body);

}
