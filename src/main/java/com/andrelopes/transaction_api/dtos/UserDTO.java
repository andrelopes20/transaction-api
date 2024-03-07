package com.andrelopes.transaction_api.dtos;

import com.andrelopes.transaction_api.enums.RoleEnum;
import com.andrelopes.transaction_api.models.UserModel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UserDTO(
        Long userId,
        String userName,
        String userEmail,
        String userPassword,
        String userRegister,
        @Enumerated(EnumType.STRING)
        RoleEnum userType,
        Float userBalance
) {
        public UserDTO(UserModel userModel){
                this(userModel.getUserId(), userModel.getUsername(), userModel.getUserEmail(), userModel.getPassword(), userModel.getUserRegister(), userModel.getUserType(), userModel.getUserBalance());
        }
}
