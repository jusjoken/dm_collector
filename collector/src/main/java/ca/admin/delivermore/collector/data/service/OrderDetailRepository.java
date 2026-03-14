package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail getOrderDetailByOrderId(Long aLong);

    OrderDetail findOrderDetailByOrderId(Long aLong);
}
