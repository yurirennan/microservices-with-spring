package org.orderservice.controller;

import org.orderservice.dto.RequestOrderDTO;
import org.orderservice.dto.ResponseOrderDTO;
import org.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity saveOrder(@Valid @RequestBody final RequestOrderDTO orderDTO) {
        this.orderService.saveOrder(orderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseOrderDTO>> listAllOrders() {
        final List<ResponseOrderDTO> orders = this.orderService.listAllOrders();

        return ResponseEntity.ok(orders);
    }
}
