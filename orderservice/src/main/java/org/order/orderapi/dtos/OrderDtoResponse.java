package org.order.orderapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDtoResponse {
    private List<OrderItemDto> orderItemDtoList;
    private String orderNumber;
    private  String orderStatus;
}
