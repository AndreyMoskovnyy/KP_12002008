package com.bsu.foodshop.ui.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Scanner;

/**
 * Класс консольного клиента приложения
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FoodShopConsoleClient {
  private final DishMenu dishMenu;
  private final OrderMenu orderMenu;
  private static final Scanner in = new Scanner(System.in);

  // запуск меню
  public void run() {
    log.info("Console client successfully started!");
    while (true) {
      System.out.println("\nВыберите категорию операций: ");
      System.out.println("[1] Работа с товарами ");
      System.out.println("[2] Работа с заказами ");
      System.out.println("[3] Завершить работу ");
      // у консольного ввода есть баг при использовании функции nextInt
      // и последующего ввода с помощью nextLine
      // лучше всегда использовать nextLine и отлавливать ошибки при неверном вводе числа
      int select;
      try{
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
          dishMenu.showMenu();
          break;
        case 2:
         orderMenu.showMenu();
          break;
        case 3:
          System.exit(0);
        default:
          System.out.println("Неправильный формат ввода!\nПопробуйте снова");
      }
    }
  }
}
