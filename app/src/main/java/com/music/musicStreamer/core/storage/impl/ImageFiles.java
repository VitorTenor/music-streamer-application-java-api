package com.music.musicStreamer.core.storage.impl;

import com.music.musicStreamer.core.storage.FilesBase;
import com.music.musicStreamer.entities.image.ImageRequest;
import com.music.musicStreamer.enumerators.ImageErrorMessage;
import com.music.musicStreamer.exceptions.ImageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ImageFiles implements FilesBase<ImageRequest> {
    private static @Value("${storage.image.mediaType}") String IMAGE_TYPE;
    private static @Value("${storage.image.path}") String IMAGE_PATH;


    @Override
    public void saveInFiles(ImageRequest imageRequest, String fileName) {
        Path path = Paths.get(IMAGE_PATH + fileName + IMAGE_TYPE);
        try {
            Files.write(path, imageRequest.getImage());
        } catch (IOException e) {
            throw new ImageException(ImageErrorMessage.SAVE_STORAGE);
        }
    }

    @Override
    public void deleteInFiles(String fileName) {
        try {
            Files.delete(Paths.get(IMAGE_PATH + fileName));
        } catch (IOException e) {
            throw new ImageException(ImageErrorMessage.DELETE_STORAGE);
        }
    }

    @Override
    public byte[] getBytesInFiles(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(IMAGE_PATH + fileName));
        } catch (IOException e) {
            throw new ImageException(ImageErrorMessage.READ_ERROR);
        }
    }

    @Override
    public List<?> getAllInFiles() {
        return null;
    }

    @Override
    public File getFile(String fileName) {
        return new File(IMAGE_PATH + fileName);
    }

    @Override
    public InputStream getInFilesStream(String fileName) {
        return null;
    }
}
