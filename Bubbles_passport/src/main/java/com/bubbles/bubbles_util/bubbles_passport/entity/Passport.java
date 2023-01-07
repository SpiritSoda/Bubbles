package com.bubbles.bubbles_util.bubbles_passport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "passport")
@AllArgsConstructor
@NoArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private int passportId;

    @NotNull
    @Lob
    private String token;
    /*
     * 0: chatroom create passport
     */
    private int type;

    public Passport(String token, int type){
        this.token = token;
        this.type = type;
    }
}
