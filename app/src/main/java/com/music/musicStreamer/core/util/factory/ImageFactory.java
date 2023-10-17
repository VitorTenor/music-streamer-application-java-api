package com.music.musicStreamer.core.util.factory;

import com.music.musicStreamer.api.v1.model.ImageModel;
import com.music.musicStreamer.entity.image.Image;
import com.music.musicStreamer.entity.image.ImageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ImageFactory {
    private @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private @Value("${storage.image.url}") String IMAGE_URL;
    private @Value("${storage.image.defaultImage}") String DEFAULT_IMAGE;

    public ImageModel createImageModel(ImageRequest imageRequest, String pathName) {
        return new ImageModel(
                imageRequest.getId(),
                pathName + IMAGE_TYPE,
                new Date(),
                new Date()
        );
    }

    public Image createImage(ImageModel imageModel) {
        return new Image(
                imageModel.getMusicId(),
                imageModel.getPathName(),
                IMAGE_URL + imageModel.getPathName()
        );
    }

    public Image createDefaultImage() {
        return new Image(
                null,
                DEFAULT_IMAGE,
                IMAGE_URL + DEFAULT_IMAGE
        );
    }
}
