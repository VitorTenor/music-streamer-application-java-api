package com.music.musicStreamer.api.v1.models.dtos;

import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("CreatePlaylistDTO Test")
public class TestCreatePlaylistDTO {

    @Test
    @Order(1)
    @DisplayName("001 - Test toEntity method")
    public void testToEntity() {
        String name = "My Playlist";
        int userId = 1;

        CreatePlaylistDTO createPlaylistDTO = new CreatePlaylistDTO();
        createPlaylistDTO.setName(name);
        createPlaylistDTO.setUserId(userId);

        PlaylistRequest expectedPlaylistRequest = new PlaylistRequest(name, userId);

        PlaylistRequest result = createPlaylistDTO.toEntity();

        assertEquals(expectedPlaylistRequest.getName(), result.getName());
        assertEquals(expectedPlaylistRequest.getUserId(), result.getUserId());
    }

}