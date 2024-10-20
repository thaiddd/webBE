package com.store.backend.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.Serializable;
import java.util.List;

@Data
public class CategoryDto implements Serializable {
    private final String categoryName;
    private final String imageUrl;
    private final String description;


    private final MultipartFile image;
}
