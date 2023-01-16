package com.bsu.foodshop.persistence.repository;

import com.bsu.foodshop.persistence.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Репозиторий для работы с записями из таблицы dishes как с обычными java объектами
 * в нем уже реализованны все необходимые методы такие findById, deleteById, findAll, save и т.д.
 */
@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {
  // найти товар по совпадению в названии
  // hibernate сам составит SQL запрос для поиска интересующей нас информации
  // правильно описанное название метода говорит hibernate'у то какой запрос необходимо составить
  List<DishEntity> findAllByTitleLikeIgnoreCase(String title);
}
