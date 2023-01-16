package com.bsu.foodshop.ui.console;

import com.bsu.foodshop.persistence.entity.DishEntity;
import com.bsu.foodshop.persistence.entity.OrderEntity;
import com.bsu.foodshop.service.OrderService;
import com.bsu.foodshop.service.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/** Класс меню для работы с заказами Содержит методы для работы с заказами */
@Component
@RequiredArgsConstructor
public class OrderMenu {
  private static final Scanner in = new Scanner(System.in);
  private final OrderService orderService;
  private final DishMenu dishMenu;

  public void showMenu() {
    while (true) {
      System.out.println("\nВыберите операцию: ");
      System.out.println("[1] Создать заказ");
      System.out.println("[2] Вывести все заказы");
      System.out.println("[3] Удалить заказ");
      System.out.println("[4] Доставить заказ");
      System.out.println("[5] Найти заказ по ID");
      System.out.println("[6] Вернуться в главное меню");
      int select;
      try {
        select = Integer.parseInt(in.nextLine());
      } catch (NumberFormatException e) {
        // сделано так, чтобы кейс с дефолтным значением сработал если
        // пользователь введет не число, а строку
        // если пользователь введет число которого
        // нет в меню выбора все равно сработает дефолтный кейс
        select = 999;
      }
      switch (select) {
        case 1:
          createOrderMenu();
          break;
        case 2:
          getAllOrdersMenu();
          break;
        case 3:
          deleteOrderByIdMenu();
          break;
        case 4:
          completeOrder();
          break;
        case 5:
          getOrderByIdMenu();
          break;
        case 6:
          return;
        default:
          System.out.println("Неправильный формат ввода!\nПопробуйте снова");
      }
    }
  }

  private void createOrderMenu() {
    System.out.println("\nЗаполните информацию о заказе: ");
    try {
      OrderDTO orderDTO = new OrderDTO();
      Set<Long> dishesInOrder = new HashSet<>();
      System.out.println("Куда доставить?");
      String address = in.nextLine();
      orderDTO.setAddress(address);

      dishMenu.getAllDishesMenu();
      System.out.println(
          "Введите ID товаров которые нужно добавить в заказ(закончить ввод - \"выход\"): ");
      while (true) {
        String select = in.nextLine();
        if (select.equalsIgnoreCase("выход")) {
          break;
        } else {
          dishesInOrder.add(Long.parseLong(select));
        }
      }
      orderDTO.setDishes(dishesInOrder);
      OrderEntity orderEntity = orderService.createOrder(orderDTO);
      System.out.println("ID: "
        + orderEntity.getId()
        + "; Адрес доставки: "
        + orderEntity.getAddress()
        + "; Сумма заказа: "
        + orderEntity.getFinalCost()
        + "; Доставлен: "
        + orderEntity.isStatus()
        + ";");
      double totalTimeToReady = 0;
      for (DishEntity dish: orderEntity.getDishes()) {
        totalTimeToReady += dish.getTimeToReady();
      }
      System.out.println("Будет готов примерно через: "+totalTimeToReady+ "ч");
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    }
  }

  private void getAllOrdersMenu() {
    List<OrderEntity> orderEntities = orderService.getAllOrders();
    if (orderEntities.isEmpty()) {
      System.out.println("Список заказов пуст!");
    } else {
      double totalCost = 0;
      for (OrderEntity orderEntity : orderEntities) {
        System.out.println(
            "ID: "
                + orderEntity.getId()
                + "; Адрес доставки: "
                + orderEntity.getAddress()
                + "; Сумма заказа: "
                + orderEntity.getFinalCost()
                + "; Доставлен: "
                + orderEntity.isStatus()
                + ";");
        if (orderEntity.isStatus()) {
          totalCost += orderEntity.getFinalCost();
        }
      }
      System.out.println("Сумма всех доставленных заказов: "+totalCost+";");
    }
  }

  private void deleteOrderByIdMenu() {
    try {
      System.out.println("\nID заказа: ");
      Long id = Long.parseLong(in.nextLine());
      orderService.deleteOrderById(id);
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  private void getOrderByIdMenu() {
    try {
      System.out.println("\nID заказа: ");
      Long id = Long.parseLong(in.nextLine());
      System.out.println(orderService.getOrderById(id));
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  private void completeOrder() {
    try {
      System.out.println("\nID заказа: ");
      Long id = Long.parseLong(in.nextLine());
      orderService.changeStatusOfOrder(id);
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }
}
