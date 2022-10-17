package com.bubbles.bubbles_backend.entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    private int messageId;

    private String content;
    private int sendUserId;
    private long timestamp;
}
