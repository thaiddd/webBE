package com.store.backend.DTO;

import com.store.backend.Util.Sex;
import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Getter
@Setter
public class SignupRequestDTO implements Serializable {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Sex sex;
    
    private String birthDate;

    private Set<String> role;


    @NotBlank
    @Size(min = 6, max = 40)
    private String fullName;

    @NotBlank
    @Size(min = 6, max = 100)
    private String address;

    private String phoneNumber;


    private MultipartFile avatar;
}
