package com.store.backend.DTO;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private List<String> roles;

    public JwtResponseDTO(String accessToken, Long id, String username, String email,String imageUrl , List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.imageUrl = imageUrl;
    }
}
