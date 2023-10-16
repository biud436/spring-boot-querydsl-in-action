package com.biud436.rest.domain.mail.service;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.biud436.rest.domain.mail.dto.CreateEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public void send(CreateEmailDto createEmailDto) {
        final SendEmailResult sendEmailResult =
                amazonSimpleEmailService.sendEmail(createEmailDto.toSendRequestDto());

        final SdkHttpMetadata metadata = sendEmailResult.getSdkHttpMetadata();
        final int statusCode = metadata.getHttpStatusCode();
        final int HTTP_OK = HttpStatus.OK.value();

        if (statusCode == HTTP_OK) {
            log.info("Email has been sent successfully.");
        } else {
            log.info("Email has been failed to send.");
        }
    }
}
