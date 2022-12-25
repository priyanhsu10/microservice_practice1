package org.order.orderapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.order.orderapi.dtos.*;
import org.order.orderapi.models.Order;
import org.order.orderapi.models.OrderItem;
import org.order.orderapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private  final ObjectMapper mapper;

    @Transactional
    public String createOrder(OrderDto orderDto) throws JsonProcessingException {

        Order order = Order.builder().orderNumber(UUID.randomUUID().toString())
                .orderStatus("created")
                .orderItemList(orderDto.getOrderItemDtoList().stream().map(x ->
                        OrderItem.builder().quantity(x.getQuantity())
                                .skuCode(x.getSkuCode()).build()).collect(Collectors.toList())
                ).build();
        log.info("creating   order  order object :" + mapper.writeValueAsString(orderDto));

        //check all sku code quantity present in next iteration
        List<InventoryItemDto> inventoryItems = order.getOrderItemList().stream().map(x -> InventoryItemDto.builder()
                .skuCode(x.getSkuCode()).quantity(x.getQuantity()).build()).collect(Collectors.toList());
        log.info("calling inventory service for check Inventory   " );
        List<InventoryCheckDto> inventoryCheckDtos = inventoryService.checkInventory(inventoryItems);
        log.info(" inventory check object coming from service  :" + mapper.writeValueAsString(inventoryCheckDtos) );
        List<String> collect = inventoryCheckDtos.stream().filter(x -> !x.isHasQuantity())
                .map(x -> x.getSkuCode() + " :insufficient Quantity\\n")
                .collect(Collectors.toList());


        if (collect.size() > 0) {
            String collect1 = collect.stream().collect(Collectors.joining());
            log.info(" inventory insufficient quantity:" + mapper.writeValueAsString(collect) );
            throw new RuntimeException(collect1);

        }
        orderRepository.save(order);

        //update order async kafka or rabit mq

        return "Order Is created order number :" + order.getOrderNumber();

    }

    public OrderDtoResponse getOrder(String orderNumber) {

        Optional<Order> optOrderNumber = orderRepository.findByOrderNumber(orderNumber);
        if (optOrderNumber.isEmpty()) {

            return null;
        }
        Order order = optOrderNumber.get();
        return   OrderDtoResponse.builder().orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .orderItemDtoList(order.getOrderItemList().stream().map(x ->
                        OrderItemDto.builder().skuCode(x.getSkuCode())
                                .quantity(x.getQuantity()).build()).collect(Collectors.toList())
                ).build();
    }

    public List<OrderDtoResponse> getAll() {
        return orderRepository.findAll().stream().map(order ->

                OrderDtoResponse.builder().orderNumber(order.getOrderNumber())
                        .orderStatus(order.getOrderStatus())
                        .orderItemDtoList(order.getOrderItemList().stream().map(x ->
                                OrderItemDto.builder().skuCode(x.getSkuCode())
                                        .quantity(x.getQuantity()).build()).collect(Collectors.toList())
                        ).build()).collect(Collectors.toList());
    }
}
