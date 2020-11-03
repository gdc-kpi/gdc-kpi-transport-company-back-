package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.data.entities.Order;
import com.ip737.transportcompany.transportcompany.request.InsertOrderRequest;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderController {

    final private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody InsertOrderRequest order) {
        System.out.println(order.toString());
        return new ResponseEntity<>(orderService.insertOrder(order.toOrder()),HttpStatus.OK);
    }
}
