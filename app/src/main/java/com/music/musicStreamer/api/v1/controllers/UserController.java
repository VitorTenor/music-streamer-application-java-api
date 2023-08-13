package com.music.musicStreamer.api.v1.controllers;

import com.music.musicStreamer.api.v1.models.dtos.UserLoginDTO;
import com.music.musicStreamer.api.v1.models.dtos.UserRegisterDTO;
import com.music.musicStreamer.entities.user.User;
import com.music.musicStreamer.entities.user.UserAuth;
import com.music.musicStreamer.usecases.user.CreateUserUseCase;
import com.music.musicStreamer.usecases.user.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-streamer/v1/users")
public class UserController {
    private final LoginUserUseCase loginUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @PostMapping("")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        logger.info("Create user");
        logger.info("User email:" + userRegisterDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(createUserUseCase.execute(userRegisterDTO.toEntity()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuth> login(@RequestBody UserLoginDTO userLogin) {
        logger.info("Login user");
        return ResponseEntity.status(HttpStatus.OK).body(loginUserUseCase.execute(userLogin.toEntity()));
    }
}
