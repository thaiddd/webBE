package com.store.backend.Service;

import com.store.backend.DTO.DashboardCount;
import com.store.backend.DTO.SignupRequestDTO;
import com.store.backend.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User updateUser(SignupRequestDTO user, Long id) throws IOException;

    List<User> getAllUsers();

    boolean addUser(SignupRequestDTO signupRequestDTO);

    boolean deleteUser(Long userId);

    User getByUserId(Long userId);

    User updateProfile(SignupRequestDTO user, Long id) throws IOException;

    DashboardCount getCount();

    Optional<User> getUserByUserName(String username);

    User save(User user);
}
