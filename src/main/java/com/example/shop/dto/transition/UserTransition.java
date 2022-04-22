package com.example.shop.dto.transition;

import com.example.shop.dto.UserDTO;
import com.example.shop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserTransition {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }
}
