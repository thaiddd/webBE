package com.store.backend.Service.Impl;

import com.store.backend.DTO.DashboardCount;
import com.store.backend.DTO.SignupRequestDTO;
import com.store.backend.Entity.ERole;
import com.store.backend.Entity.Role;
import com.store.backend.Entity.User;
import com.store.backend.Repository.CartRepository;
import com.store.backend.Repository.OrderRepository;
import com.store.backend.Repository.RoleRepository;
import com.store.backend.Repository.UserRepository;
import com.store.backend.Service.OrderService;
import com.store.backend.Service.UserService;
import com.store.backend.Util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User updateUser(SignupRequestDTO user, Long id) throws IOException {
        User updated = userRepository.findById(id).get();
        updated.setAddress(user.getAddress());
        updated.setEmail(user.getEmail());
        updated.setFullName(user.getFullName());
        if (user.getPassword() != null){
            updated.setPassword(encoder.encode(user.getPassword()));
        }
        // Lưu avatar
        if (user.getAvatar() != null) {
            String imgUrl = ImageUtil.saveImage(user.getAvatar(), updated.getId(),"resources/images");
            updated.setImageUrl(imgUrl);
        }
        updated.setPhoneNumber(user.getPhoneNumber());
        Set<String> strRoles = user.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByName(ERole.SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(sellerRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        updated.setRoles(roles);
        User check = userRepository.save(updated);
        return check;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(SignupRequestDTO signupRequestDTO) {
        try {
            User user = new User(signupRequestDTO.getUsername(),
                    signupRequestDTO.getEmail(),
                    encoder.encode(signupRequestDTO.getPassword()),
                    signupRequestDTO.getAddress(),
                    signupRequestDTO.getFullName()
            );
            user.setPhoneNumber(signupRequestDTO.getPhoneNumber());
            user.setCreatedDate(LocalDate.now());

            user.setPassword(encoder.encode(signupRequestDTO.getPassword()));

            Set<String> strRoles = signupRequestDTO.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role.toLowerCase()) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "seller":
                            Role sellerRole = roleRepository.findByName(ERole.SELLER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(sellerRole);

                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            User userSaved = userRepository.save(user);

            // Lưu avatar
            if (signupRequestDTO.getAvatar() != null) {
                String imgUrl = ImageUtil.saveImage(signupRequestDTO.getAvatar(), userSaved.getId(),"resources/images");
                userSaved.setImageUrl(imgUrl);
            }else{
                userSaved.setImageUrl("avatar-none.png");
            }
            userRepository.save(userSaved);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public User getByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User updateProfile(SignupRequestDTO user, Long id) throws IOException {
        User updated = userRepository.findById(id).get();
        updated.setAddress(user.getAddress());
        updated.setEmail(user.getEmail());
        updated.setFullName(user.getFullName());

        if (user.getBirthDate() != null) {
            updated.setBirthDate(LocalDate.parse(user.getBirthDate()));
        }
        if (user.getPassword() != null){
            updated.setPassword(encoder.encode(user.getPassword()));
        }
        updated.setSex(user.getSex());
        // Lưu avatar
        if (user.getAvatar() != null) {
            String imgUrl = ImageUtil.saveImage(user.getAvatar(), updated.getId(),"resources/images");
            updated.setImageUrl(imgUrl);
        }
        updated.setPhoneNumber(user.getPhoneNumber());

        User check = userRepository.save(updated);
        return check;
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public DashboardCount getCount() {
        DashboardCount count = new DashboardCount();
        count.setUserCount(userRepository.count());

        Role seller = roleRepository.findByName(ERole.SELLER).get();
        count.setSaleCount(userRepository.countByRolesContains(seller));

        count.setOrderCount(orderService.count());
        count.setOrderFinishCount(orderService.findByState("done").size());
        return count;
    }
}
