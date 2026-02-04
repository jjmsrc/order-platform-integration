package com.jm.orderplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jm.orderplatform.entity.ShipmentEntity;
import com.jm.orderplatform.entity.ShipmentPk;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, ShipmentPk> {
}
