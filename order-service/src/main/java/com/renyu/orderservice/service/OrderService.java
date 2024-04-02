package com.renyu.orderservice.service;

import com.renyu.orderservice.dto.OrderRequestDTO;
import com.renyu.orderservice.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO placeOrder(final OrderRequestDTO orderRequestDTO);

}
