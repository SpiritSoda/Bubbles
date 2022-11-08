package com.bubbles.bubbles_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatroom")
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    int chatroomId;

    String title;
    String comment;

    @ToString.Exclude
    @ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "chatrooms")
    @JsonIgnore
    List<User> users = new ArrayList<>();
}
