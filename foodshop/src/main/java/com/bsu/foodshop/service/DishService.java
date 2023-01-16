package com.bsu.foodshop.service;

import com.bsu.foodshop.persistence.entity.DishEntity;
import com.bsu.foodshop.persistence.repository.DishRepository;
import com.bsu.foodshop.service.dto.DishDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/** Сервис для работы с товарами */
@Service
@RequiredArgsConstructor
public class DishService {

  private final DishRepository dishRepository;

  /**
   * Метод для создания и записи в базу данных информации о новом товаре
   *
   * @param dishDTO ДТО с информацией о товаре
   * @return сохраненный в базу данных объект
   */
  public DishEntity createDish(DishDTO dishDTO) {
    // Создаем новый объект блюда
    DishEntity dishEntity = new DishEntity();
    // Заполняем поля
    dishEntity.setComposition(dishDTO.getComposition());
    dishEntity.setDescription(dishDTO.getDescription());
    dishEntity.setPrice(dishDTO.getPrice());
    dishEntity.setTitle(dishDTO.getTitle());
    dishEntity.setTimeToReady(dishDTO.getTimeToReady());
    // Сохраняем в бд
    dishEntity = dishRepository.save(dishEntity);
    System.out.println("Товар сохранен в БД");
    // возвращаем ответ
    return dishEntity;
  }

  /**
   * Метод для изменения информации о товаре
   * @param dishDTO ДТО с информацией о товаре
   * @param id ID товара у которого меняем свойства
   * @return измененный товар
   */
  public DishEntity changeDishParams(DishDTO dishDTO, Long id) {
    // находим блюдо с указанным id
    DishEntity dishEntity = getDishById(id);
    // Заполняем поля
    if (dishDTO.getComposition() != null && !dishDTO.getComposition().equalsIgnoreCase("")) {
      dishEntity.setComposition(dishDTO.getComposition());
    }
    if (dishDTO.getDescription() != null && !dishDTO.getDescription().equalsIgnoreCase("")) {
      dishEntity.setDescription(dishDTO.getDescription());
    }
    if (dishDTO.getPrice() != 0 ) {
      dishEntity.setPrice(dishDTO.getPrice());
    }
    if (dishDTO.getTitle() != null && !dishDTO.getTitle().equalsIgnoreCase("")) {
      dishEntity.setTitle(dishDTO.getTitle());
    }
    if (dishDTO.getTimeToReady() != 0) {
      dishEntity.setTimeToReady(dishDTO.getTimeToReady());
    }
    // Сохраняем в бд
    dishEntity = dishRepository.save(dishEntity);
    System.out.println("Товар сохранен в БД");
    // возвращаем ответ
    return dishEntity;
  }

  /**
   * Получить список всех товаров
   * @return список товаров
   */
  public List<DishEntity> getAllDishes() {
    // находим все имеющиеся в базе данных блюда
    return dishRepository.findAll();
  }

  /**
   * Удалить товар по ID
   * @param id ID удаляемого товара
   */
  public void deleteDishById(Long id) {
    // удаляем блюдо с указанным id
    dishRepository.deleteById(id);
    System.out.println("Товар с ID - "+ id+" успешно удален!");
  }

  /**
   * Найти товар по ID
   * @param id ID товара
   * @return объект товара
   */
  public DishEntity getDishById(Long id) {
    // находим блюдо с указанным id
    return dishRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Товара с ID - " +id+" не существует"));
  }

  /**
   * Найти товар по совпадению в названии
   * @param title название
   * @return список товаров
   */
  public List<DishEntity> getDishByTitleLike(String title) {
    return dishRepository.findAllByTitleLikeIgnoreCase(title);
  }
}
