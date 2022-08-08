package com.guimaraes.email.entity;

import com.guimaraes.email.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "email")
public class EmailEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID emailId;

    @Column
    private String ownerRef;

    @Column
    private String emailFrom;

    @Column
    private String emailTo;

    @Column
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private LocalDateTime sendDataTime;

    @Column
    private StatusEmail statusEmail;

}
