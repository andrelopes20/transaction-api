package com.andrelopes.transaction_api.controllers;

import com.andrelopes.transaction_api.dtos.TransactionDTO;
import com.andrelopes.transaction_api.dtos.UserDTO;
import com.andrelopes.transaction_api.enums.RoleEnum;
import com.andrelopes.transaction_api.models.UserModel;
import com.andrelopes.transaction_api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;
    UserModel userModel;

    @PostMapping(value = "/transaction", consumes = "application/json")
    public ResponseEntity<?> transaction(@RequestBody TransactionDTO transactionDTO) throws Exception {

        userModel = userService.findById(transactionDTO.payer()).orElse(null);
        assert userModel != null;
        if(!userModel.getUserType().equals(RoleEnum.COMUM)) return ResponseEntity.status(401).body("Usuário do tipo lojista não pode realizar transação");

        userService.saveTransaction(transactionDTO);

        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping(value = "/addUser", consumes = "application/json")
    public ResponseEntity<UserDTO> addUser (@RequestBody UserDTO userDTO){

        userModel = new UserModel();

        BeanUtils.copyProperties(userDTO,userModel);

        userService.save(userModel);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers (){

        ObjectMapper mapper = new ObjectMapper();
        List<UserModel> listUser = userService.findAll();

        return ResponseEntity.ok(listUser.stream().map(UserDTO::new).toList());
    }

}
