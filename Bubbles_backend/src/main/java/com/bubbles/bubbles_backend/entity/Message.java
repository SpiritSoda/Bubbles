package com.bubbles.bubbles_backend.entity;

import com.bubbles.bubbles_backend.dto.MessageDTO;
import com.bubbles.bubbles_backend.utils.MessageType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import javax.persistence.*;
import java.io.FileNotFoundException;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
@Slf4j
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
    public static String getFileName(Message message){
        if(message.type != MessageType.FILE && message.type != MessageType.IMAGE)
            return "";
        String content = message.getContent();
        int split = content.indexOf('.');
        String size_filename = content.substring(split + 1);
        split = size_filename.indexOf('.');
        String filename = size_filename.substring(split + 1);
        return filename;
    }
    public static String getFilePath(Message message) throws FileNotFoundException {
        if(message.type != MessageType.FILE && message.type != MessageType.IMAGE)
            return "";
        String content = message.getContent();
        int split = content.indexOf('.');
        String folder = content.substring(0, split);
        String size_filename = content.substring(split + 1);
        split = size_filename.indexOf('.');
        String filename = size_filename.substring(split + 1);
        String local_filename = filename + "." + message.getTimestamp();

        String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        if(message.type == MessageType.FILE)
            return realPath + "static/" + folder + "/" + local_filename;
        else if(message.type == MessageType.IMAGE)
            return realPath + "static/images/" + local_filename;
        return "";
    }
}
