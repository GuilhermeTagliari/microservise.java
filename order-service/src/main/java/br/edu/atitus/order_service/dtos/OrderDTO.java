package br.edu.atitus.order_service.dtos;

import java.util.List;

public record OrderDTO(List<OrderItemDTO> items) {}
