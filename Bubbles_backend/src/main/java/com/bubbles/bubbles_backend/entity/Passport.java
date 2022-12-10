package com.bubbles.bubbles_backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "passport")
public class Passport {
    @Id
    @NotNull
    private String token;
    private long exceedTime;
}
