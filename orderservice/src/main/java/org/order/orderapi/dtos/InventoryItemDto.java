package org.order.orderapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDto {
    private  String skuCode;
    private  int quantity;

}

