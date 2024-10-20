package com.store.backend.DTO;

import lombok.Data;

@Data
public class DashboardCount {
    private long userCount;
    private long saleCount;
    private long orderCount;
    private long orderFinishCount;
}
