package com.bubbles.bubbles_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    @ManyToMany(cascade= {CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "user_chatroom",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "chatroom_id")})
    private List<Chatroom> chatrooms = new ArrayList<>();

    @Override
    public int hashCode(){
        return userId;
    }
    @Override
    public boolean equals(Object o){
        if(o.getClass() != User.class)
            return false;
        return this.userId == ((User) o).getUserId();
    }
}
