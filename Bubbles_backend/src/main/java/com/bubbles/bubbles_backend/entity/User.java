package com.bubbles.bubbles_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    private int avatar;
    private String password;

    private String username;

    @ToString.Exclude
    @ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "user_chatroom",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "chatroom_id")})
    private List<Chatroom> chatrooms = new ArrayList<>();
}
