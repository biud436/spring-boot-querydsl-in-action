package com.biud436.rest.domain.mail.dto;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Builder
public class CreateEmailDto {

    private String from;

    // 받는 사람 (여러 명)
    private final List<String> to;
    private final String subject;
    private final String content;

    public SendEmailRequest toSendRequestDto() {

        final Destination destination = new Destination()
                .withToAddresses(this.to);

        final Content content = new Content()
                .withData(this.content)
                .withCharset("UTF-8");

        final Message message = new Message()
                .withSubject(content)
                .withBody(new Body().withHtml(content));

        return new SendEmailRequest()
                .withSource(this.from)
                .withDestination(destination)
                .withMessage(message);
    }
}
