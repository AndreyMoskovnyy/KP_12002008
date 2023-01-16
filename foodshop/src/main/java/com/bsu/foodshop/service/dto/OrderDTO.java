package com.bsu.foodshop.service.dto;

import lombok.Data;
import java.util.Set;

/**
 * ДТО для заказа
 */
@Data
public class OrderDTO {

  private String address;

  private Set<Long> dishes;
}
