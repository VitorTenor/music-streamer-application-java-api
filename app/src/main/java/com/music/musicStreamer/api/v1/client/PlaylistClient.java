package com.music.musicStreamer.api.v1.client;

import com.music.musicStreamer.api.v1.database.model.PlaylistModel;
import com.music.musicStreamer.api.v1.database.model.PlaylistMusicModel;
import com.music.musicStreamer.api.v1.database.repository.PlaylistMusicRepository;
import com.music.musicStreamer.api.v1.database.repository.PlaylistRepository;
import com.music.musicStreamer.core.util.factory.PlaylistFactory;
import com.music.musicStreamer.core.util.validator.PlaylistValidator;
import com.music.musicStreamer.core.util.validator.UserValidator;
import com.music.musicStreamer.entity.music.Music;
import com.music.musicStreamer.entity.playlist.CreatePlaylistEntity;
import com.music.musicStreamer.entity.playlist.MusicPlaylistRequest;
import com.music.musicStreamer.entity.playlist.PlaylistEntity;
import com.music.musicStreamer.entity.playlist.PlaylistMusic;
import com.music.musicStreamer.enums.PlaylistMessages;
import com.music.musicStreamer.exception.PlaylistException;
import com.music.musicStreamer.gateway.PlaylistGateway;
import com.music.musicStreamer.gateway.PlaylistMusicGateway;
import com.music.musicStreamer.usecase.playlistMusic.GetMusicByPlaylistIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.music.musicStreamer.core.util.factory.LogFactory.info;

@Component
@RequiredArgsConstructor
public class PlaylistClient implements PlaylistGateway {
    private final UserValidator userValidator;
    private final PlaylistFactory playlistFactory;
    private final PlaylistValidator playlistValidator;
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final GetMusicByPlaylistIdUseCase getMusicByPlaylistIdUseCase;


    private final PlaylistMusicGateway playlistMusicGateway;

    private final Logger LOGGER = Logger.getLogger(PlaylistClient.class.getName());

    @Override
    @Transactional
    public PlaylistEntity create(CreatePlaylistEntity entity) {
        info(this.getClass(), "Create playlist");
        info(this.getClass(), "Playlist name: " + entity.name());

        final var playlist = playlistFactory.toModel(entity);
        final var playlistModel = save(playlist);

        final var musicIds = playlistMusicGateway.getMusicIdByPlaylistId((long) playlistModel.getId());

        info(this.getClass(), "Playlist created");
        info(this.getClass(), "Playlist id: " + playlistModel.getId());

        return playlistFactory.toEntity(playlistModel, musicIds);
    }

    @Override
    @Transactional
    public String addMusicToPlaylist(MusicPlaylistRequest musicPlaylistRequest) {
        LOGGER.info("[PlaylistClient] Add song to playlist");
        LOGGER.info("[PlaylistClient] Playlist id: " + musicPlaylistRequest.getPlaylistId());
        LOGGER.info("[PlaylistClient] Music id: " + musicPlaylistRequest.getMusicId());

        playlistValidator.validateMusicPlaylist(musicPlaylistRequest);
        PlaylistMusicModel playlistMusicModel = playlistFactory.createPlaylistMusicModel(musicPlaylistRequest);
        playlistMusicRepository.save(playlistMusicModel);
        LOGGER.info("[PlaylistClient] Song added to playlist in database");

        return PlaylistMessages.MUSIC_ADDED.getMessage();
    }

    @Override
    public PlaylistMusic getPlaylistById(int id) {
        LOGGER.info("[PlaylistClient] Get playlist by id");
        PlaylistModel playlistModel = playlistRepository.findById(id).orElseThrow(() -> new PlaylistException(PlaylistMessages.NOT_FOUND));

        LOGGER.info("[PlaylistClient] Playlist found");
        LOGGER.info("[PlaylistClient] Playlist id: " + playlistModel.getId());
        LOGGER.info("[PlaylistClient] Playlist name: " + playlistModel.getName());

        return new PlaylistMusic(playlistModel.getId(), playlistModel.getName(), playlistModel.getUserId(), getMusicByList(id));
    }

    @Override
    public List<PlaylistEntity> getPlaylistByUserId(int id) {
        LOGGER.info("[PlaylistClient] Get playlist by user id");
        LOGGER.info("[PlaylistClient] User id: " + id);

        userValidator.validateIfExistById(id);

        LOGGER.info("[PlaylistClient] User found");

        return playlistRepository.findAllByUserId(id).stream().map(playlistFactory::createPlaylist).toList();
    }

    private List<Music> getMusicByList(int playlistId) {
        return new ArrayList<>(getMusicByPlaylistIdUseCase.execute(playlistId));
    }

    private PlaylistModel save(PlaylistModel playlist) {
        return playlistRepository.save(playlist);
    }
}

