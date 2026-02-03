package com.jm.orderplatform.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jm.orderplatform.entity.OrderEntity;
import com.jm.orderplatform.entity.OrderPk;

public interface OrderRepository extends JpaRepository<OrderEntity, OrderPk> {

	@Query("select o from OrderEntity o order by o.id.orderId desc")
	List<OrderEntity> findLatestOrder(Pageable pageable);
}
