package com.store.backend.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private  Long Id;
    private  String productName;
    private Long price;
    private String sex;
    private String color;
    private int quantity;
    private String description;
    private Long categoryId;
    private Long brandId;
    private String image;

}
