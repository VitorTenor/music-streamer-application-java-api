package com.music.musicStreamer.core.utils.validators;

import com.music.musicStreamer.api.v1.repositories.PlaylistRepository;
import com.music.musicStreamer.entities.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entities.playlist.PlaylistRequest;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.exceptions.PlaylistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistValidator {
    private final PlaylistRepository playlistRepository;
    private final MusicValidator musicValidator;
    private final UserValidator userValidator;

    public void validatePlaylist(PlaylistRequest playlistRequest) {
        if (playlistRequest.getName().isEmpty()) throw new PlaylistException(PlaylistMessages.NAME_REQUIRED);
        if (playlistRequest.validateUserId()) throw new PlaylistException(PlaylistMessages.USER_ID_REQUIRED);
    }

    public void validateMusicPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        if (!playlistRepository.existsById(musicPlaylistRequest.getPlaylistId())) throw new PlaylistException(PlaylistMessages.NOT_FOUND);
        musicValidator.validateIfExistById(musicPlaylistRequest.getMusicId());
        userValidator.validateIfExistById(musicPlaylistRequest.getUserId());
    }
}
