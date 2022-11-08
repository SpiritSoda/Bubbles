package com.bubbles.bubbles_backend.entity;

import com.bubbles.bubbles_backend.dto.MessageDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    private int chatroomId;
    private int type;
    private String content;
    private int senderId;
    private long timestamp;

    public Message(MessageDTO message){
        this.messageId = 0;
        this.chatroomId = message.getChatroomId();
        this.type = message.getType();
        this.content = message.getContent();
        this.senderId = message.getSenderId();
        this.timestamp = message.getTimestamp();
    }
}
