package com.bsu.foodshop.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс представляющий собой запись в таблице orders
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
@ToString
public class OrderEntity {
  //Автоинкрементирующийся уникальный идентификатор заказа
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "address")
  private String address;

  @Column(name = "status")
  private boolean status;

  @Column(name = "final_cost")
  private double finalCost;
  // определение средствами hibernate
  // (фреймворк для работы с СУБД из spring data jpa) связи "многие ко многим"
  // которую мы описали с помощью связующей таблицы dish_orders
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "dishes_orders",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "dish_id"))
  private Set<DishEntity> dishes = new LinkedHashSet<>();
}
