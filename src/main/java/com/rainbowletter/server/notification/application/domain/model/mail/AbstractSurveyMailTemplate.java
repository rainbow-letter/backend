package com.rainbowletter.server.notification.application.domain.model.mail;

import com.rainbowletter.server.common.config.ClientConfig;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import java.util.Locale;

@Getter
abstract class AbstractSurveyMailTemplate extends AbstractMailTemplate {

    private final ClientConfig clientConfig;
    private final MessageSource messageSource;
    private final ISpringTemplateEngine templateEngine;

    protected AbstractSurveyMailTemplate(
        MailTemplateCode templateCode,
        ClientConfig clientConfig,
        MessageSource messageSource,
        ISpringTemplateEngine templateEngine
    ) {
        super(templateCode);
        this.clientConfig = clientConfig;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Override
    public String getTitle(final String title) {
        Locale locale = getLocale(title);
        return messageSource.getMessage("receive.reply.email.title", new String[]{title}, locale);
    }

    @Override
    public String getContent(final String receiver, final Object... args) {
        validateTemplateParameters(2, args);

        String url = clientConfig.getBaseUrl() + args[0];
        Locale locale = getLocale(args[1].toString());

        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("url", url);
        context.setVariable("surveyUrl", getSurveyUrl(locale));

        return templateEngine.process(getTemplateName(), context);
    }

    protected Locale getLocale(String str) {
        return str.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") ? Locale.KOREA : Locale.ENGLISH;
    }

    private String getSurveyUrl(Locale locale) {
        if ("ko".equals(locale.getLanguage())) {
            return "https://forms.gle/jouKvaubQQaL57Tg6";
        }
        return "https://docs.google.com/forms/d/e/1FAIpQLSfwkSYfmCue-9cE-OvEGETPwc7wr9QxE_sMNfGYeytzHsF6Mw/viewform?usp=dialog";
    }

    protected abstract String getTemplateName();
}
