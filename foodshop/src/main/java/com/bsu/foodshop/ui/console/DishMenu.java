package com.bsu.foodshop.ui.console;

import com.bsu.foodshop.persistence.entity.DishEntity;
import com.bsu.foodshop.service.DishService;
import com.bsu.foodshop.service.dto.DishDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

/** Класс меню для работы с товарами Содержит методы для работы с товарами */
@Component
@RequiredArgsConstructor
public class DishMenu {
  private static final Scanner in = new Scanner(System.in);
  private final DishService dishService;

  /** Отобразить меню */
  public void showMenu() {
    while (true) {
      System.out.println("\nВыберите операцию: ");
      System.out.println("[1] Создать товар");
      System.out.println("[2] Изменить информацию о товаре");
      System.out.println("[3] Вывести список товаров");
      System.out.println("[4] Найти товар по ID");
      System.out.println("[5] Удалить товар по ID");
      System.out.println("[6] Найти товар по названию");
      System.out.println("[7] Вернуться в главное меню");
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
          createDishMenu();
          break;
        case 2:
          changeDishParamsMenu();
          break;
        case 3:
          getAllDishesMenu();
          break;
        case 4:
          getDishByIdMenu();
          break;
        case 5:
          deleteDishByIdMenu();
          break;
        case 6:
          findDishByTitleMenu();
          break;
        case 7:
          return;
        default:
          System.out.println("Неправильный формат ввода!\nПопробуйте снова");
      }
    }
  }

  private void findDishByTitleMenu() {
    try {
      System.out.println("\nНазвание блюда: ");
      String title = in.nextLine();
      List<DishEntity> dishEntityList = dishService.getDishByTitleLike(title);
      if (dishEntityList.isEmpty()) {
        System.out.println("Совпадений не найдено!");
      } else {
        dishEntityList.forEach(System.out::println);
      }
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  private void createDishMenu() {
    System.out.println("\nЗаполните информацию о блюде: ");
    try {
      DishDTO dishDTO = getDishDTO();

      System.out.println(dishService.createDish(dishDTO));
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    }
  }

  private void changeDishParamsMenu() {
    try {
      System.out.println("\nID блюда: ");
      Long id = Long.parseLong(in.nextLine());
      System.out.println("Заполните информацию о блюде: ");
      DishDTO dishDTO = getDishDTO();

      System.out.println(dishService.changeDishParams(dishDTO, id));
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  protected void getAllDishesMenu() {
    List<DishEntity> dishEntities = dishService.getAllDishes();
    if (dishEntities.isEmpty()) {
      System.out.println("Список товаров пуст!");
    } else {
      System.out.println("Список доступных товаров:");
      for (DishEntity dish : dishEntities) {
        System.out.println("ID: " + dish.getId() + "; Название " + dish.getTitle() + ";");
      }
    }
  }

  private void getDishByIdMenu() {
    try {
      System.out.println("\nID блюда: ");
      Long id = Long.parseLong(in.nextLine());
      System.out.println(dishService.getDishById(id));
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  private void deleteDishByIdMenu() {
    try {
      System.out.println("\nID блюда: ");
      Long id = Long.parseLong(in.nextLine());
      dishService.deleteDishById(id);
    } catch (NumberFormatException e) {
      System.out.println("Неверные параметры ввода!");
    } catch (IllegalArgumentException e) {
      System.out.println(e);
    }
  }

  private DishDTO getDishDTO() {
    DishDTO dishDTO = new DishDTO();

    System.out.println("Название (обязательно): ");
    dishDTO.setTitle(in.nextLine());
    System.out.println("Состав (обязательно): ");
    dishDTO.setComposition(in.nextLine());
    System.out.println("Описание (опционально): ");
    dishDTO.setDescription(in.nextLine());
    System.out.println("Цена (обязательно): ");
    String price = in.nextLine();
    if (price != null && !price.equalsIgnoreCase("")) {
      dishDTO.setPrice(Double.parseDouble(price));
    }
    System.out.println("Время готовки (обязательно): ");
    String timeToReady = in.nextLine();
    if (timeToReady != null && !timeToReady.equalsIgnoreCase("")) {
      dishDTO.setTimeToReady(Double.parseDouble(timeToReady));
    }
    return dishDTO;
  }
}
