package ca.admin.delivermore.collector.data.service;

import ca.admin.delivermore.collector.data.entity.Restaurant;
import ca.admin.delivermore.collector.data.entity.TaskEntity;
import ca.admin.delivermore.collector.data.global.GlobalOrder;
import ca.admin.delivermore.collector.data.global.GlobalOrderList;
import ca.admin.delivermore.collector.data.tookan.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Service
public class RestClientService implements Serializable {

    private String tookan_api = "53606482f1430f144a136d654e4f2d471ae1cdfd28d4793e5f1506c4";
    private String tookan_baseUrl = "https://api.tookanapp.com/v2";
    private String global_baseUrl = "https://pos.globalfoodsoft.com/pos/order/pop";
    private Logger log = LoggerFactory.getLogger(RestClientService.class);

    /**
     * Returns parsed {@link Task} objects from the REST service.
     *
     * Useful when the response data has a known structure.
     */
    public List<TaskDetail> getAllTasks(LocalDate fromDate, LocalDate toDate) {
        return getAllTasks(fromDate, toDate, 0L);
    }
    public List<TaskDetail> getAllTasks(LocalDate fromDate, LocalDate toDate, Long maxJobId) {

        List<TaskDetail> taskDetails = new ArrayList<>();

        String urlExtra = "/get_all_tasks";
        String urlFull = tookan_baseUrl + urlExtra;

        //log.info("Fetching all Task objects through Tookan REST..");

        //List<Task> tasks = new ArrayList<>();
        Boolean lastPage = Boolean.FALSE;
        Integer currentPage = 1;

        //get all pages of tasks - run at least once
        do {

            // Fetch from Tookan API
            RequestHeadersSpec<?> spec = WebClient.create().post()
                    .uri(urlFull)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(getTaskHeaderBody(fromDate,toDate,currentPage.toString())));

            // do fetch and map result
            //log.info("taskList: gettingJSON");
            String taskString = spec.retrieve().toEntity(String.class).block().getBody();
            //TODO: check return status
            //log.info("taskList String:" + taskString);
            ObjectMapper objectMapper = new ObjectMapper();
            TaskList taskList = null;
            try {
                taskList = objectMapper.readValue(taskString,TaskList.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if(taskList==null){
                //log.info("taskList: No tasks found");
            }else{
                log.info("taskList: processing page " + currentPage + " of " + taskList.getTotalPageCount() + " pages");
                //tasks.addAll(taskList.getData());
                //List<Long> tempList = new ArrayList<>();
                //tempList.add(436403278L);
                //TODO: check if on the first run there are actually tasks - do not call taskdetails if none
                taskDetails.addAll(getTaskDetails(taskList.getTaskIDs(maxJobId)));
                //taskDetails.addAll(getTaskDetails(tempList));

            }
            currentPage++;
            if(currentPage>taskList.getTotalPageCount()){
                lastPage = Boolean.TRUE;
            }

        } while(lastPage.equals(Boolean.FALSE));

        log.info(String.format("...processed %d tasks.", taskDetails.size()));

        return taskDetails;
    }

    private String getTaskHeaderBody(LocalDate fromDate, LocalDate toDate, String curPage){

        //log.info("getTaskHeaderBody: fromDate:" + fromDate.toString() + " toDate:" + toDate.toString());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();

        bodyValues.put("api_key", tookan_api);
        bodyValues.put("job_type", "1");
        //bodyValues.Add("team_id", 1335347);
        //bodyValues.put("job_status", "2"); //2 is success only
        bodyValues.put("start_date", fromDate.toString());
        bodyValues.put("end_date", toDate.toString());
        bodyValues.put("custom_fields", "0");
        bodyValues.put("is_pagination", "1"); //change to 1 to page
        bodyValues.put("requested_page", curPage);

        //log.info("getTaskHeaderBody:" + bodyValues.toString());
        return bodyValues.toString();

    }

    /**
     * Returns parsed {@link TaskDetail} objects from the REST service.
     *
     * Useful when the response data has a known structure.
     */
    public List<TaskDetail> getTaskDetails(List<Long> taskIDs) {

        List<TaskDetail> taskDetails = new ArrayList<>();

        String urlExtra = "/get_job_details";
        String urlFull = tookan_baseUrl + urlExtra;

        //log.info("Fetching Task Details through Tookan REST..");

        WebClient myWebClient = WebClient.builder()
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build())
                .build();

        // Fetch from Tookan API
        RequestHeadersSpec<?> spec = myWebClient
                .post()
                .uri(urlFull)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getTaskDetailHeaderBody(taskIDs)));

        // do fetch and map result
        //log.info("taskDetailList: gettingJSON");
        String taskDetailString = spec.retrieve().toEntity(String.class).block().getBody();
        //TODO: check return status
        log.info("taskDetailList String:" + taskDetailString);
        ObjectMapper objectMapper = new ObjectMapper();
        TaskDetailList taskDetailList = null;
        try {
            taskDetailList = objectMapper.readValue(taskDetailString,TaskDetailList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(taskDetailList==null){

        }else{
            //log.info("taskList:" + taskDetailList);
            taskDetails.addAll(taskDetailList.getData());
        }

        log.info(String.format("...received %d taskDetails.", taskDetails.size()));

        return taskDetails;
    }

    private String getTaskDetailHeaderBody(List<Long> taskIDs){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Long item: taskIDs ) {
            arrayNode.add(item);
        }

        bodyValues.put("api_key", tookan_api);
        bodyValues.put("job_ids", arrayNode);
        bodyValues.put("include_task_history", "1");
        bodyValues.put("job_additional_info", "1");

        //log.info("getTaskDetailHeaderBody:" + bodyValues.toString());
        return bodyValues.toString();

    }

    public Boolean hasOrderId(String orderID) {
        TaskByOrderDetail taskByOrderDetail = getTaskByOrderId(orderID);
        if(taskByOrderDetail==null) return Boolean.FALSE;
        return Boolean.TRUE;
    }

    public TaskByOrderDetail getTaskByOrderId(String orderID) {

        TaskByOrderDetail taskByOrderDetail = null;

        String urlExtra = "/get_job_details_by_order_id";
        String urlFull = tookan_baseUrl + urlExtra;

        //log.info("getTaskByOrderId: Fetching Task Details by orderId through Tookan REST..");

        WebClient myWebClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();

        // Fetch from Tookan API
        RequestHeadersSpec<?> spec = myWebClient
                .post()
                .uri(urlFull)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getTaskDetailByOrderIdHeaderBody(orderID)));

        // do fetch and map result
        //log.info("getTaskByOrderId: taskDetailList: gettingJSON");
        String taskDetailString = spec.retrieve().toEntity(String.class).block().getBody();
        //TODO: check return status
        log.info("getTaskByOrderId: taskDetailString:" + taskDetailString);
        ObjectMapper objectMapper = new ObjectMapper();
        TaskByOrderResult taskDetailList = null;
        try {
            taskDetailList = objectMapper.readValue(taskDetailString,TaskByOrderResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(taskDetailList==null){
            return null;
        }else{
            if(taskDetailList.getStatus().equals(200L)){
                //log.info("getTaskByOrderId: global order id:" + orderID + " found in Tookan");
                if(taskDetailList.getData().size()>0){
                    taskByOrderDetail = taskDetailList.getData().get(0);
                    log.info("getTaskByOrderId: global order id:" + orderID + " task found:" + taskByOrderDetail.getJobId());
                    return taskByOrderDetail;
                }
            }
        }
        log.info("getTaskByOrderId: global order id:" + orderID + " NOT found in Tookan");
        return null;
    }

    private String getTaskDetailByOrderIdHeaderBody(String orderID){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(orderID);

        bodyValues.put("api_key", tookan_api);
        bodyValues.put("order_ids", arrayNode);
        bodyValues.put("include_task_history", "0");

        //log.info("getTaskDetailByOrderIdHeaderBody:" + bodyValues.toString());
        return bodyValues.toString();

    }





    public Integer getTaskCount(LocalDate fromDate, LocalDate toDate){
        Integer taskCount = 0;
        String urlExtra = "/user_task_stats";
        String urlFull = tookan_baseUrl + urlExtra;

        log.info("getTaskCount: date range " + fromDate + " - " + toDate + " through Tookan REST..");
        // Fetch from Tookan API
        RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri(urlFull)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getTaskCountHeaderBody(fromDate,toDate)));

        // do fetch and map result
        //log.info("getTaskCount: gettingJSON");
        String taskCountString = spec.retrieve().toEntity(String.class).block().getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        TaskCountResult taskCountResult = null;
        try {
            taskCountResult = objectMapper.readValue(taskCountString,TaskCountResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(taskCountResult==null){
            log.info("getTaskCount: gettingJSON failed parsing object to TaskCountResult");
        }else{
            log.info("getTaskCount:" + taskCountResult);
            List<TaskCount> taskCountList = taskCountResult.getData();
            for (TaskCount taskCountItem: taskCountList) {
                taskCount = taskCount + taskCountItem.getJobCount();
            }
            /*
            if(taskCountResult.getData().size()>0){
                taskCount = taskCountResult.getData().get(0).getJobCount();
            }
             */
        }

        log.info(String.format("...received %d taskCount.", taskCount));
        return taskCount;
    }

    private String getTaskCountHeaderBody(LocalDate fromDate, LocalDate toDate){

        //log.info("getTaskCountHeaderBody: fromDate:" + fromDate.toString() + " toDate:" + toDate.toString());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add("0");
        arrayNode.add("1");
        arrayNode.add("2");
        arrayNode.add("4");
        arrayNode.add("6");
        arrayNode.add("7");
        arrayNode.add("8");

        bodyValues.put("api_key", tookan_api);
        bodyValues.put("job_type", "1");
        bodyValues.put("job_status", arrayNode); //2 is success only
        bodyValues.put("start_date", fromDate.toString());
        bodyValues.put("end_date", toDate.toString());

        //log.info("getTaskHeaderBody:" + bodyValues.toString());
        return bodyValues.toString();

    }


    /**
     * Returns parsed {@link Task} objects from the REST service.
     *
     * Useful when the response data has a known structure.
     */
    public List<Driver> getAllDrivers() {

        List<Driver> driverList = new ArrayList<>();

        String urlExtra = "/get_all_fleets";
        String urlFull = tookan_baseUrl + urlExtra;

        log.info("Fetching all Drivers through Tookan REST..");

        // Fetch from Tookan API
        RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri(urlFull)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getDriversHeaderBody()));

        // do fetch and map result
        //log.info("driversList: gettingJSON");
        String driversString = spec.retrieve().toEntity(String.class).block().getBody();
        //TODO: check return status
        //log.info("driversList String:" + driversString);
        ObjectMapper objectMapper = new ObjectMapper();
        Drivers drivers = null;
        try {
            drivers = objectMapper.readValue(driversString,Drivers.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(drivers==null){
            log.info("driversList: No drivers found");
        }else{
            driverList.addAll(drivers.getData());
        }

        log.info(String.format("...processed %d drivers.", driverList.size()));

        return driverList;
    }

    private String getDriversHeaderBody(){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();

        bodyValues.put("api_key", tookan_api);
        bodyValues.put("include_team_id", 1);

        //log.info("getDriversHeaderBody:" + bodyValues.toString());
        return bodyValues.toString();

    }

    /**
     * Returns parsed {@link Task} objects from the REST service.
     *
     * Useful when the response data has a known structure.
     */
    public List<Team> getAllTeams() {

        List<Team> teamList = new ArrayList<>();

        String urlExtra = "/view_all_team_only";
        String urlFull = tookan_baseUrl + urlExtra;

        log.info("Fetching all Teams through Tookan REST..");

        // Fetch from Tookan API
        RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri(urlFull)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getTeamsHeaderBody()));

        // do fetch and map result
        //log.info("driversList: gettingJSON");
        String teamsString = spec.retrieve().toEntity(String.class).block().getBody();
        //TODO: check return status
        //log.info("driversList String:" + driversString);
        ObjectMapper objectMapper = new ObjectMapper();
        Teams teams = null;
        try {
            teams = objectMapper.readValue(teamsString,Teams.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(teams==null){
            log.info("teamsList: No teams found");
        }else{
            teamList.addAll(teams.getTeam());
        }

        log.info(String.format("...processed %d teams.", teamList.size()));

        return teamList;
    }

    private String getTeamsHeaderBody(){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();

        bodyValues.put("api_key", tookan_api);

        return bodyValues.toString();

    }



    /**
     * Returns parsed {@link TaskDetail} objects from the REST service.
     *
     * Useful when the response data has a known structure.
     */
    public String getGlobalOrderJson(Restaurant restaurant) {
        if(restaurant.getGlobalAuthCode()==null || restaurant.getGlobalAuthCode().isEmpty()){
            log.info("getGlobalOrderJson: No Auth Code available for restaurant:" + restaurant.getName());
            return null;
        }
        //log.info("Fetching Global Orders through Gloria Food REST..");

        WebClient myWebClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();

        // Fetch from Gloria Food API
        RequestHeadersSpec<?> spec = myWebClient
                .post()
                .uri(global_baseUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Glf-Api-Version", "2")
                .header("Authorization", restaurant.getGlobalAuthCode());

        // do fetch and map result
        //String globalOrderString = spec.retrieve().toEntity(String.class).block().getBody();
        String globalOrderString = null;
        //TODO: testing new try catch for global - add to others if no issues
        try {
            globalOrderString = spec.retrieve()
                    .toEntity(String.class)
                    .filter(
                            entity ->
                                    entity.getStatusCode().is2xxSuccessful()
                                            && entity.getBody() != null
                    )
                    .block().getBody();
        } catch (WebClientResponseException e) {
            log.error("getGlobalOrderJson: error response:" + e);
            return null;
        }

        if(globalOrderString.contains("\"count\":0,\"orders\":[]")){
            //log.info("...No orders returned for restaurant:" + restaurant.getName());
            return null;
        }else{
            log.info("..Processing order for restaurant:" + restaurant.getName() + " result:  globalOrderString:" + globalOrderString);

        }
        //make sure only valid characters as emoji's cause error in saving to database
        String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";
        String result = globalOrderString.replaceAll(regex, "");

        return result;

    }

    /**
     * Returns parsed {@link TaskDetail} objects from the REST service.
     *
     * Useful when the response data has a known structure.
     */
    public void updateTaskDescriptiion(Long taskID, String description) {

        String urlExtra = "/edit_task";
        String urlFull = tookan_baseUrl + urlExtra;

        //log.info("Updating Task Description through Tookan REST..");

        RequestHeadersSpec<?> spec = WebClient.create().post()
                .uri(urlFull)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getUpdateTaskDescHeaderBody(taskID, description)));

        // do fetch and map result
        //log.info("updateTaskDescriptiion: gettingJSON");
        String responseString = spec.retrieve().toEntity(String.class).block().getBody();
        log.info("updateTaskDescriptiion response:" + responseString);
        ObjectMapper objectMapper = new ObjectMapper();
        TaskEditResponse taskEditResponse = null;
        try {
            taskEditResponse = objectMapper.readValue(responseString,TaskEditResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(taskEditResponse==null){

        }else{
            if(taskEditResponse.getStatus().equals(200L)){
                log.info("updateTaskDescriptiion: description for task id:" + taskID + " updated in Tookan");
                return;
            }
        }

        log.info("updateTaskDescriptiion: failed to update description for task id:" + taskID);
        return;
    }

    private String getUpdateTaskDescHeaderBody(Long taskID, String description){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyValues = mapper.createObjectNode();

        bodyValues.put("api_key", tookan_api);
        bodyValues.put("job_id", taskID);
        bodyValues.put("job_description", description);

        return bodyValues.toString();

    }

}
