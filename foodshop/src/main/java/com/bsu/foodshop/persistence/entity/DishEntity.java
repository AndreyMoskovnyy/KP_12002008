package com.bsu.foodshop.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Класс представляющий собой запись в таблице dishes
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "dishes")
@ToString
public class DishEntity {
  //Автоинкрементирующийся уникальный идентификатор товара
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "composition")
  private String composition;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private double price;

  @Column(name = "time_to_ready")
  private double timeToReady;
}
