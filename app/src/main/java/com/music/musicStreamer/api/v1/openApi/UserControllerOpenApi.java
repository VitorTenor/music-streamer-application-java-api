package com.music.musicStreamer.api.v1.openApi;

import com.music.musicStreamer.api.v1.request.UserLoginRequest;
import com.music.musicStreamer.api.v1.request.UserRegisterRequest;
import com.music.musicStreamer.api.v1.response.UserLoginResponse;
import com.music.musicStreamer.api.v1.response.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@Tag(name = "User Controller")
public interface UserControllerOpenApi {
    @Operation(summary = "Create user",
            description = "Create user",
            tags = {"User Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserRegisterResponse.class))),
            }
    )
    ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest);

    @Operation(summary = "Login user",
            description = "Login user",
            tags = {"User Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserLoginResponse.class))),
            }
    )
    ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userRegisterRequest);

}
