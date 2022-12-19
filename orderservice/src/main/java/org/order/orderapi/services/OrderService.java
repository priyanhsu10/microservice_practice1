package org.order.orderapi.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.order.orderapi.dtos.*;
import org.order.orderapi.models.Order;
import org.order.orderapi.models.OrderItem;
import org.order.orderapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private  final InventoryService inventoryService;

    @Transactional
    public String createOrder(OrderDto orderDto) {

        Order order = Order.builder().orderNumber(UUID.randomUUID().toString())
                .orderStatus("created")
                .orderItemList(orderDto.getOrderItemDtoList().stream().map(x ->
                        OrderItem.builder().quantity(x.getQuantity())
                                .skuCode(x.getSkuCode()).build()).collect(Collectors.toList())
                ).build();

        //check all sku code quantity present in next iteration
        List<InventoryItemDto> inventoryItems= order.getOrderItemList().stream().map(x->InventoryItemDto.builder()
                .skuCode(x.getSkuCode()).quantity(x.getQuantity()).build()).collect(Collectors.toList());
        List<InventoryCheckDto> inventoryCheckDtos = inventoryService.checkInventory(inventoryItems);
        List<String> collect = inventoryCheckDtos.stream().filter(x -> !x.isHasQuantity())
                .map(x -> x.getSkuCode() + " :insufficient Quantity\\n")
                .collect(Collectors.toList());
        if(collect.size()>0){
            String collect1 = collect.stream().collect(Collectors.joining());
            throw new RuntimeException(collect1 );

        }
        orderRepository.save(order);

        //update order async kafka or rabit mq

        return  "Order Is created order number :"+order.getOrderNumber();

    }

    public  OrderDtoResponse getOrder(String orderNumber){

        Optional<Order> optOrderNumber = orderRepository.findByOrderNumber(orderNumber);
if(optOrderNumber.isEmpty()){

    return null;
}
        Order order=optOrderNumber.get();
        return OrderDtoResponse.builder().orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .orderItemDtoList(order.getOrderItemList().stream().map(x->
                    OrderItemDto.builder().skuCode(x.getSkuCode())
                            .quantity(x.getQuantity()).build()).collect(Collectors.toList())
                ).build();
    }
}
