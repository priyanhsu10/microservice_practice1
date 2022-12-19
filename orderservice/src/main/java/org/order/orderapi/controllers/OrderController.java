package org.order.orderapi.controllers;

import lombok.RequiredArgsConstructor;
import org.order.orderapi.dtos.OrderDto;
import org.order.orderapi.dtos.OrderDtoResponse;
import org.order.orderapi.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) {

        return orderService.createOrder(orderDto);

    }

    @GetMapping("/{orderNumber}")
    public OrderDtoResponse getOrder(@PathVariable String orderNumber) {
        return orderService.getOrder(orderNumber);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlerException(RuntimeException ex) {

        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
