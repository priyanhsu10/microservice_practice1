package org.order.orderapi.repositories;

import org.order.orderapi.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order,Integer> {
    Optional<Order> findByOrderNumber(String orderNumber);
}
