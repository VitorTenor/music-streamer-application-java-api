package com.music.musicStreamer.gateway;

import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistWithMusicEntity;

import java.util.List;

public interface PlaylistGateway {
    PlaylistEntity create(CreatePlaylistEntity createPlaylistEntity);
    PlaylistWithMusicEntity getPlaylistById(int id);
    List<PlaylistEntity> getPlaylistByUserId(int id);
}
