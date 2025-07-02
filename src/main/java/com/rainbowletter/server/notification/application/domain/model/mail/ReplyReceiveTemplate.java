package com.rainbowletter.server.notification.application.domain.model.mail;

import static com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode.REPLY;

import com.rainbowletter.server.common.config.ClientConfig;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

@Component
class ReplyReceiveTemplate extends AbstractMailTemplate {

    private static final String KOREAN_REGEX = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
    private static final Pattern KOREAN_PATTERN = Pattern.compile(KOREAN_REGEX);

    private final ClientConfig clientConfig;
    private final MessageSource messageSource;
    private final ISpringTemplateEngine templateEngine;

    ReplyReceiveTemplate(
        final ClientConfig clientConfig,
        final MessageSource messageSource,
        final ISpringTemplateEngine templateEngine
    ) {
        super(REPLY);
        this.clientConfig = clientConfig;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Override
    public String getTitle(final String title) {
        final Locale locale = getLocale(title);
        return messageSource.getMessage("receive.reply.email.title", new String[]{title}, locale);
    }

    @Override
    public String getContent(final String receiver, final Object... args) {
        validateTemplateParameters(2, args);
        final var url = clientConfig.getBaseUrl() + args[0];
        final Locale locale = getLocale(args[1].toString());
        final Context context = new Context();
        context.setLocale(locale);
        context.setVariable("url", url);
        if (locale.getLanguage().equals("ko")) {
            context.setVariable("surveyUrl", "https://forms.gle/jouKvaubQQaL57Tg6");
        } else {
            context.setVariable("surveyUrl",
                "https://docs.google.com/forms/d/e/1FAIpQLSfwkSYfmCue-9cE-OvEGETPwc7wr9QxE_sMNfGYeytzHsF6Mw/viewform?usp=dialog");
        }
        return templateEngine.process("receive-reply", context);
    }

    private Locale getLocale(final String str) {
        if (KOREAN_PATTERN.matcher(str).matches()) {
            return Locale.KOREA;
        } else {
            return Locale.ENGLISH;
        }
    }

}
