package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.model.PlaylistModel;
import com.music.musicStreamer.api.v1.model.PlaylistMusicModel;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class PlaylistFactory {
    private final Logger LOGGER = Logger.getLogger(PlaylistFactory.class.getName());

    public PlaylistEntity createPlaylist(PlaylistModel playlistModel) {
        LOGGER.info("[PlaylistFactory] Create playlist");
        return new PlaylistEntity(playlistModel.getName(), playlistModel.getId());
    }

    public PlaylistMusicModel createPlaylistMusicModel(MusicPlaylistRequest musicPlaylistRequest) {
        LOGGER.info("[PlaylistFactory] Create playlist music model");

        return new PlaylistMusicModel(musicPlaylistRequest.getPlaylistId(), musicPlaylistRequest.getUserId(), musicPlaylistRequest.getMusicId(), new Date(), new Date());
    }

}
