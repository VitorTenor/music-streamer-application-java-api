package com.music.musicStreamer.api.v1.openApi;

import com.music.musicStreamer.api.v1.request.UserRegister;
import com.music.musicStreamer.entities.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User Controller")
public interface UserControllerOpenApi {
    @Operation(summary = "Create user",
            description = "Create user",
            tags = {"User Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = User.class))),
            }
    )
    public ResponseEntity<User> register(@RequestBody UserRegister userRegister);

}