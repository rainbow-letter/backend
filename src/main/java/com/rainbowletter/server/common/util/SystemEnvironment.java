package com.rainbowletter.server.common.util;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SystemEnvironment {
    public boolean isActiveTest() {
        return false;
    }

}
