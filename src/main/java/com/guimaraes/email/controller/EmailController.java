package com.guimaraes.email.controller;

import com.guimaraes.email.consumer.EmailConsumer;
import com.guimaraes.email.dto.EmailDto;
import com.guimaraes.email.entity.EmailEntity;
import com.guimaraes.email.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final EmailConsumer emailConsumer;

    public EmailController(EmailService emailService, EmailConsumer emailConsumer) {
        this.emailConsumer = emailConsumer;
        this.emailService = emailService;
    }

    @PostMapping("/sending-email")
    public ResponseEntity<EmailDto> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emailConsumer.listen(emailDto));
    }

    @GetMapping("/emails")
    public ResponseEntity<Page<EmailEntity>> getAllEmails(
            @PageableDefault(size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<EmailEntity> page = emailService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
