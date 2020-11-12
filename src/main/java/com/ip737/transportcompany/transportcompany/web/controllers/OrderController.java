package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.security.services.AuthenticationFacade;
import com.ip737.transportcompany.transportcompany.exceptions.AccessDeniedException;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import com.ip737.transportcompany.transportcompany.web.dto.OrderDto;
import com.ip737.transportcompany.transportcompany.web.validators.OrderDtoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderController {

    final private OrderService orderService;

    final private AuthenticationFacade authenticationFacade;

    @Autowired
    public OrderController(OrderService orderService, AuthenticationFacade authenticationFacade) {
        this.orderService = orderService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<?> chooseCar(@PathVariable UUID id, @PathParam("update") Boolean update) {
        if(update == null) update = false;
        return new ResponseEntity<>(orderService.getPath(id, update), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderDto order) {
        if (authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            System.out.println(order.toString());
            OrderDtoValidator.validate(order);

            order.setAdmins_id(authenticationFacade.getId());
            return new ResponseEntity<>(orderService.insertOrder(order.toOrder()), HttpStatus.CREATED);
        } else {
            throw new AccessDeniedException("Resource forbidden for this user due to their role");
        }
    }
}
