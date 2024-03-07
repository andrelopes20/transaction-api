package com.andrelopes.transaction_api.repositories;

import com.andrelopes.transaction_api.dtos.TransactionDTO;
import com.andrelopes.transaction_api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    public UserDetails findByUserEmail(String email);

}
