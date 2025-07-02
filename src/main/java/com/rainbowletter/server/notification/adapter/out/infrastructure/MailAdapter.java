package com.rainbowletter.server.notification.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.SystemEnvironment;
import com.rainbowletter.server.notification.application.port.out.SendMailPort;
import com.rainbowletter.server.notification.application.port.out.dto.SendMailRequest;
import jakarta.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@InfraAdapter
@RequiredArgsConstructor
class MailAdapter implements SendMailPort {

    private final JavaMailSender javaMailSender;
    private final SystemEnvironment systemEnvironment;

    @Override
    public void sendMail(final SendMailRequest request) {
        if (systemEnvironment.isActiveTest()) {
            return;
        }

        try {
            final var mimeMessage = javaMailSender.createMimeMessage();
            final var messageHelper = new MimeMessageHelper(mimeMessage, true,
                StandardCharsets.UTF_8.name());
            messageHelper.setFrom("무지개 편지 <noreply@rainbowletter.co.kr>");
            messageHelper.setTo(request.receiver());
            messageHelper.setSubject(request.title());
            messageHelper.setText(request.content(), true);
            messageHelper.addInline("logo", new ClassPathResource("static/images/logo.png"));
            javaMailSender.send(mimeMessage);
        } catch (final MessagingException exception) {
            throw new RainbowLetterException("send.email.fail", exception);
        }
    }

}
