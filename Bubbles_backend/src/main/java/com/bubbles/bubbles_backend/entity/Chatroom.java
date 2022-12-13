package com.bubbles.bubbles_backend.entity;

import com.bubbles.bubbles_backend.dto.ChatroomDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatroom")
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private int chatroomId;
    private int admin;
    private int icon;
    private String title;
    private String comment;
    private int maxUser;

    @ToString.Exclude
    @ManyToMany(cascade={CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "chatrooms")
    @JsonIgnore
    private List<User> users = new ArrayList<>();


    @Override
    public int hashCode(){
        return chatroomId;
    }
    @Override
    public boolean equals(Object o){
        if(o.getClass() != Chatroom.class)
            return false;
        return this.chatroomId == ((Chatroom) o).getChatroomId();
    }

}
