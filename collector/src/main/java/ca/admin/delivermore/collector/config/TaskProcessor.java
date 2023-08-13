package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.service.DriversRepository;
import ca.admin.delivermore.collector.data.service.OrderDetailRepository;
import ca.admin.delivermore.collector.data.service.RestaurantRepository;
import ca.admin.delivermore.collector.data.service.TaskDetailRepository;
import ca.admin.delivermore.collector.data.tookan.TaskDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor implements ItemProcessor<TaskDetail, TaskEntity> {
    private RestaurantRepository restaurantRepository;
    private OrderDetailRepository orderDetailRepository;
    private DriversRepository driversRepository;

    private TaskDetailRepository taskDetailRepository;

    private Logger log = LoggerFactory.getLogger(TaskProcessor.class);

    public TaskProcessor(RestaurantRepository restaurantRepository, OrderDetailRepository orderDetailRepository, DriversRepository driversRepository, TaskDetailRepository taskDetailRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.driversRepository = driversRepository;
        this.taskDetailRepository = taskDetailRepository;
    }

    @Override
    public TaskEntity process(TaskDetail taskDetail) throws Exception {
        if(taskDetailRepository.hasSuccessfulJobID(taskDetail.getJobId())){
            //skip
            //log.info("TaskProcessor: process: skipping already successful task:" + taskDetail.getJobId());
            return null;
        }
        log.info("TaskProcessor: processing: task:" + taskDetail.getJobId());
        return taskDetail.getTaskEntity(restaurantRepository,orderDetailRepository,driversRepository);
    }
}
