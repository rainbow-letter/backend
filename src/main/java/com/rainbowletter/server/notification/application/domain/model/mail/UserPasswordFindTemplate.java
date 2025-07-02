package com.rainbowletter.server.notification.application.domain.model.mail;

import static com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode.FIND_PASSWORD;

import com.rainbowletter.server.common.config.ClientConfig;
import com.rainbowletter.server.common.util.JwtTokenProvider;
import com.rainbowletter.server.common.util.TimeHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
class UserPasswordFindTemplate extends AbstractMailTemplate {

    private static final int DEFAULT_VERIFY_EXPIRATION_MINUTE = 10;

    private final TimeHolder timeHolder;
    private final ClientConfig clientConfig;
    private final TemplateEngine templateEngine;
    private final JwtTokenProvider jwtTokenProvider;

    UserPasswordFindTemplate(
        final TimeHolder timeHolder,
        final ClientConfig clientConfig,
        final TemplateEngine templateEngine,
        final JwtTokenProvider jwtTokenProvider
    ) {
        super(FIND_PASSWORD);
        this.timeHolder = timeHolder;
        this.clientConfig = clientConfig;
        this.templateEngine = templateEngine;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String getContent(final String receiver, final Object... args) {
        validateTemplateParameters(0, args);
        final var expire = timeHolder.currentTimeMillis()
            + timeHolder.minuteToMillis(DEFAULT_VERIFY_EXPIRATION_MINUTE);
        final var token = jwtTokenProvider.create(receiver, "VERIFY", expire);
        final var url = clientConfig.getBaseUrl() + "/users/password/reset?token=" + token;
        final Context context = new Context();
        context.setVariable("url", url);
        return templateEngine.process("reset-password", context);
    }

}
