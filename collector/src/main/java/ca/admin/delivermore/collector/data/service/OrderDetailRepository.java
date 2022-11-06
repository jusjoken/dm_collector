package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {

    OrderDetail getOrderDetailByOrderId(Long aLong);

    OrderDetail findOrderDetailByOrderId(Long aLong);
}
