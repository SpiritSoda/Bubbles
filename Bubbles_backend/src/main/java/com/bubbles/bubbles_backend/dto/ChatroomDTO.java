package com.bubbles.bubbles_backend.dto;

import com.bubbles.bubbles_backend.entity.Chatroom;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatroomDTO {
    private int id;
    private String title;
    private int icon;
    private String comment;
    @JsonProperty("max_user")
    private int maxUser;
    private String passport;
    private int admin;
    @JsonProperty("total_user")
    private int totalUser;
    private int onlines;

    public ChatroomDTO(Chatroom chatroom){
        this.id = chatroom.getChatroomId();
        this.icon = chatroom.getIcon();
        this.title = chatroom.getTitle();
        this.comment = chatroom.getComment();
        this.maxUser = chatroom.getMaxUser();
        this.passport = null;
        this.admin = chatroom.getAdmin();
        this.totalUser = chatroom.getUsers().size();
        this.onlines = 0;
    }
}
