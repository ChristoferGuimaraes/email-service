package com.guimaraes.email.service;

import com.guimaraes.email.dto.EmailDto;
import com.guimaraes.email.entity.EmailEntity;
import com.guimaraes.email.enums.StatusEmail;
import com.guimaraes.email.repository.EmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    public EmailDto sendEmail(EmailEntity emailEntity) {
        emailEntity.setSendDataEmail(LocalDateTime.now());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailEntity.getEmailFrom());
        message.setTo(emailEntity.getEmailTo());
        message.setSubject(emailEntity.getSubject());
        message.setText(emailEntity.getText());

        try {
            emailSender.send(message);
            emailEntity.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailEntity.setStatusEmail(StatusEmail.ERROR);
        } finally {
            emailRepository.save(emailEntity);

            EmailDto emailDto = new EmailDto();
            BeanUtils.copyProperties(emailEntity, emailDto);

            return emailDto;
        }
    }

    public Page<EmailEntity> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

}
