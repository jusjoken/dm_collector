package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.OrderDetail;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailLoader {
    List<OrderDetail> orderDetailList = new ArrayList<>();
    private Logger log = LoggerFactory.getLogger(OrderDetailLoader.class);

    public OrderDetailLoader() {
        log.info("OrderDetailLoader: constructor started");
    }

    public List<OrderDetail> getOrderDetailList() {
        log.info("OrderDetailLoader: getOrderDetailList called");
        return orderDetailList;
    }

    public Boolean loadFromCSV(String fileName){
        log.info("OrderDetailLoader: loadFromCSV called");

        try {
            CsvToBeanBuilder<OrderDetail> beanBuilder = new CsvToBeanBuilder<>(new InputStreamReader(new FileInputStream(fileName)));

            beanBuilder.withType(OrderDetail.class);
            // build methods returns a list of Beans
            beanBuilder.build().parse().forEach(e -> orderDetailList.add(e));
            /*
            for (OrderDetail orderDetail: orderDetailList ) {
                log.info("OrderDetail:" + orderDetail);
            }
             */

        } catch (FileNotFoundException e) {
            log.info("OrderDetailLoader: Csv load failed");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }

}
