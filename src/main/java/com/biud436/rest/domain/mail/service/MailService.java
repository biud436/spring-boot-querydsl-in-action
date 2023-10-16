package com.biud436.rest.domain.mail.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.biud436.rest.domain.mail.dto.CreateEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    private void send(CreateEmailDto createEmailDto) {
        final SendEmailResult sendEmailResult =
                amazonSimpleEmailService.sendEmail(createEmailDto.toSendRequestDto());

        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            log.info("Email has been sent successfully.");
        } else {
            log.info("Email has been failed to send.");
        }
    }
}
