package com.bsu.foodshop.service;

import com.bsu.foodshop.persistence.entity.DishEntity;
import com.bsu.foodshop.persistence.entity.OrderEntity;
import com.bsu.foodshop.persistence.repository.OrderRepository;
import com.bsu.foodshop.service.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сервис для работы с заказами
 */
@Service
@RequiredArgsConstructor
public class OrderService {
  private final DishService dishService;
  private final OrderRepository orderRepository;

  /**
   * Создать заказ
   * @param orderDTO информация о заказе
   * @return созданный заказ
   */
  public OrderEntity createOrder(OrderDTO orderDTO) {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setAddress(orderDTO.getAddress());
    Set<DishEntity> dishesInOrder = new HashSet<>();
    double finalCost = 0; // считаем стоимость всех товаров в заказе
    for (Long dishId : orderDTO.getDishes()) {
      // добавляем в заказ товары если они есть в базе данных
      // в заказе хранится Set из объектов DishEntity
      // (необходимо для нормального функционирования связи "многие ко многим"
      // в spring-data-jpa)
      DishEntity dish = dishService.getDishById(dishId);
      dishesInOrder.add(dish);
      finalCost += dish.getPrice();
    }
    orderEntity.setDishes(dishesInOrder);
    orderEntity.setFinalCost(finalCost);
    orderEntity = orderRepository.save(orderEntity);
    System.out.println("Заказ успешно создан");
    return orderEntity;
  }

  public List<OrderEntity> getAllOrders() {
    return orderRepository.findAll();
  }

  /**
   * получить заказ по ID
   * @param id ID заказа
   * @return заказ
   */
  public OrderEntity getOrderById(Long id) {
    return orderRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Заказа с ID - " +id+" не существует"));
  }

  /**
   * Изменить статус заказа на "доставленный"
   * @param id ID доставленного заказа
   */
  public void changeStatusOfOrder(Long id) {
    OrderEntity orderEntity = getOrderById(id);
    if (orderEntity.isStatus()) {
      System.out.println("Данный заказ уже доставлен!");
    } else {
      orderEntity.setStatus(true);
      orderRepository.save(orderEntity);
      System.out.println("Заказ с ID - "+id+" успешно доставлен!");
    }
  }

  /**
   * Удалить заказ по ID
   * @param id ID заказа
   */
  public void deleteOrderById(Long id) {
    orderRepository.deleteById(id);
    System.out.println("Товар с ID - "+ id+" успешно удален!");
  }
}
