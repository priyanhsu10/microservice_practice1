package org.order.orderapi.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.order.orderapi.models.OrderItem;

import java.util.List;
@Data
@NoArgsConstructor

public class OrderDto {
    List<OrderItemDto> orderItemDtoList;
}
