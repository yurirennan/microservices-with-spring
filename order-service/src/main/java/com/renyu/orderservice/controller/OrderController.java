package com.renyu.orderservice.controller;

import com.renyu.orderservice.dto.OrderRequestDTO;
import com.renyu.orderservice.dto.OrderResponseDTO;
import com.renyu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody @Valid final OrderRequestDTO orderRequestDTO) {

        final OrderResponseDTO orderResponseDTO = this.orderService.placeOrder(orderRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }

}
