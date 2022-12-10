package com.bubbles.bubbles_backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ban_record")
public class BanRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id")
    private int id;
    private int user;
    private int chatroom;

    private long endTimestamp;
}
