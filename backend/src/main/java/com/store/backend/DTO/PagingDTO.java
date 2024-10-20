package com.store.backend.DTO;

import com.store.backend.Entity.Product;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingDTO {
    private int start;

    private int limit;

    private String sort = "";

    private String where = "";

    private Object data;

    private long total = 0;

    private Integer totalPages = 0;

//    public PagingDTO(String sort, String where, Object data, Integer total) {
//        this.sort = sort == null? "" : sort;
//        this.where = where == null? "" : where;
//        this.total = total == null ? 0 : total;
//        this.data = data == null ? new Object() : total;
//    }
}
