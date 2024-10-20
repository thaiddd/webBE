package com.store.backend.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@EnableWebSecurity
public class ImageController {
    @Autowired
    private ServletContext servletContext;


    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        String rootPath2 = resourceLoader.getResource("").getURI().getPath();
        String rootPath = "resources";
        String imageDir = rootPath + "/images";
        Path imagePath = Paths.get(imageDir, filename);

        // read the file into a byte array
        byte[] imageBytes = Files.readAllBytes(imagePath);

        // create and return a ResponseEntity with the image bytes and headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // change this to the appropriate content type
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
