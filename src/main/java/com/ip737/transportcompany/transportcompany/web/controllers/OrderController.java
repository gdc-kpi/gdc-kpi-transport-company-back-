package com.ip737.transportcompany.transportcompany.web.controllers;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.security.services.AuthenticationFacade;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.exceptions.AccessDeniedException;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.services.OrderService;
import com.ip737.transportcompany.transportcompany.services.UserService;
import com.ip737.transportcompany.transportcompany.web.dto.ChangePasswordDto;
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
@RequestMapping("/api")
public class OrderController {

    final private OrderService orderService;
    final private UserService userService;

    final private AuthenticationFacade authenticationFacade;

    @Autowired
    public OrderController( UserService userService, OrderService orderService, AuthenticationFacade authenticationFacade) {
        this.orderService = orderService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<?> chooseCar(@PathVariable UUID id, @PathParam("update") Boolean update) {
        if(update == null) update = false;
        return new ResponseEntity<>(orderService.getPath(id, update), HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto order) {
        if (authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            OrderDtoValidator.validate(order);

            order.setAdmins_id(authenticationFacade.getId());
            return new ResponseEntity<>(orderService.insertOrder(order.toOrder()), HttpStatus.CREATED);
        } else {
            throw new AccessDeniedException("Resource forbidden for this user due to their role");
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") String orderId) {
            return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);

    }

    @PatchMapping("/order/{orderId}")
    public ResponseEntity<?> assignDriver(@PathVariable("orderId") String orderId, @PathParam("driver") String driver) {
        if (authenticationFacade.isAllowed(Constants.ROLE_ADMIN)) {
            orderService.assignDriver(orderId, driver);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new AccessDeniedException("Resource forbidden for this user due to their role");
        }
    }

    @PatchMapping("/order/status/{orderId}")
    public ResponseEntity<?> changeStatus(@PathVariable("orderId") String orderId, @PathParam("status") String status) {
        if (authenticationFacade.isAllowed(Constants.ROLE_DRIVER)) {
            orderService.changeStatus(orderId, status, authenticationFacade.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new AccessDeniedException("Resource forbidden for this user due to their role");
        }
    }


    @PatchMapping("auth/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto pass) {
        UUID userId = UUID.fromString(authenticationFacade.getId());
        User currentUser = userService.getById(userId);
        if (currentUser.getPassword().equals(pass.getOldPassword())) {
            currentUser.setPassword(pass.getNewPassword());
            userService.update(currentUser);
        } else throw new ValidationException(Constants.INCORRECT_PASSWORD);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/drivers-list")
    public ResponseEntity<?> getDriversList(@RequestBody OrderDto order) {
        return new ResponseEntity<>(orderService.getDriversList(order.toOrder()), HttpStatus.OK);
    }
}
