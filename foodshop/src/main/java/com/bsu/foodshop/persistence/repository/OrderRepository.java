package com.bsu.foodshop.persistence.repository;

import com.bsu.foodshop.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * Репозиторий для работы с записями из таблицы orders как с обычными java объектами
 * в нем уже реализованны все необходимые методы такие findById, deleteById, findAll, save и т.д.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
