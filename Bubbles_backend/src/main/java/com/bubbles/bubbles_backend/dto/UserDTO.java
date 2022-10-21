package com.bubbles.bubbles_backend.dto;

import lombok.*;

@Data
public class UserDTO {
    private int userId;
    private String username;
    private String password;
    private int icon;
}
