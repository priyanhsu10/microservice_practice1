package org.order.orderapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.orderapi.dtos.OrderDto;
import org.order.orderapi.dtos.OrderDtoResponse;
import org.order.orderapi.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private  final ObjectMapper mapper;

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        log.info("creating  order  input :" + mapper.writeValueAsString(orderDto));
        return orderService.createOrder(orderDto);

    }

    @GetMapping("/{orderNumber}")
    public OrderDtoResponse getOrder(@PathVariable String orderNumber) {
        return orderService.getOrder(orderNumber);

    }
    @GetMapping
    public List<OrderDtoResponse> get() {
        return orderService.getAll();

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlerException(RuntimeException ex) {

        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
