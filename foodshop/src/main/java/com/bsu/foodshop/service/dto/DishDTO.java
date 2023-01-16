package com.bsu.foodshop.service.dto;

import lombok.Data;

/**
 * ДТО для товара
 */
@Data
public class DishDTO {

  private String composition;

  private String title;

  private String description;

  private double price;

  private double timeToReady;
}
