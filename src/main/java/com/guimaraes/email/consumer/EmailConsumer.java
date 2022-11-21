package com.guimaraes.email.consumer;

import com.guimaraes.email.dto.EmailDto;
import com.guimaraes.email.entity.EmailEntity;
import com.guimaraes.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    EmailService emailService;

    EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public EmailDto listen(@Payload EmailDto emailDto) {
        EmailEntity emailModel = new EmailEntity();
        BeanUtils.copyProperties(emailDto, emailModel);


        return  emailService.sendEmail(emailModel);
    }
}
