package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.OrderDetail;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailLoader {
    List<OrderDetail> orderDetailList = new ArrayList<>();

    public OrderDetailLoader() {
        System.out.println("OrderDetailLoader: constructor started");
    }

    public List<OrderDetail> getOrderDetailList() {
        System.out.println("OrderDetailLoader: getOrderDetailList called");
        return orderDetailList;
    }

    public Boolean loadFromCSV(String fileName){
        System.out.println("OrderDetailLoader: loadFromCSV called");

        try {
            CsvToBeanBuilder<OrderDetail> beanBuilder = new CsvToBeanBuilder<>(new InputStreamReader(new FileInputStream(fileName)));

            beanBuilder.withType(OrderDetail.class);
            // build methods returns a list of Beans
            beanBuilder.build().parse().forEach(e -> orderDetailList.add(e));
            /*
            for (OrderDetail orderDetail: orderDetailList ) {
                System.out.println("OrderDetail:" + orderDetail);
            }
             */

        } catch (FileNotFoundException e) {
            System.out.println("OrderDetailLoader: Csv load failed");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }

}
