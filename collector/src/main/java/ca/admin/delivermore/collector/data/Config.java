package ca.admin.delivermore.collector.data;

import ca.admin.delivermore.collector.data.entity.JobEntity;
import ca.admin.delivermore.collector.data.entity.SettingEntity;
import ca.admin.delivermore.collector.data.service.CollectorRegistry;
import ca.admin.delivermore.collector.data.service.JobStatusRepository;
import ca.admin.delivermore.collector.data.service.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Config {

    public enum TaskType{
        Global,
        Custom,
        Form
    }
    private Logger log = LoggerFactory.getLogger(Config.class);
    private final Double driverPayForDelivery = 4.00;

    private final Double serviceFeeGlobal = 11.0;
    private final Double serviceFeeCustom = 11.0;
    private final Double serviceFeePhonein = 11.0;

    private String fromEmail = "tara.birch@delivermore.ca";
    private List<String> paymentMethods = new ArrayList<>(List.of("CASH", "CARD", "ONLINE"));
    private String jobs;
    private Boolean runDriverJob = Boolean.FALSE;
    private Boolean runOrderJob = Boolean.FALSE;
    private Boolean runGlobalOrderJob = Boolean.FALSE;
    private Boolean runTaskJob = Boolean.FALSE;
    private Boolean runRestaurantJob = Boolean.FALSE;

    private Boolean runScheduleReportJob = Boolean.FALSE;
    private Boolean runDriverJobDb = null;
    private Boolean runOrderJobDb = null;
    private Boolean runGlobalOrderJobDb = null;
    private Boolean runTaskJobDb = null;
    private Boolean runRestaurantJobDb = null;

    private Boolean runScheduleReportJobDb = null;

    private String QBOAppClientId = null;
    private String QBOAppClientSecret = null;
    private String QBOAppRedirectUri = null;
    private String QBOState = null;

    private String QBOApiCall = null;

    private SettingRepository settingRepository;

    private static String emailNotification = "EmailNotification";

    public static Config instance = null;

    public Config() {
        log.info("Config constructor called");
    }

    public static Config getInstance() {
        if (Config.instance == null) {
            Config.instance = new Config();
        }
        return Config.instance;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public Boolean getRunDriverJob() {
        return runDriverJob;
    }

    public void setRunDriverJob(Boolean runDriverJob) {
        this.runDriverJob = runDriverJob;
    }

    public Boolean getRunOrderJob() {
        return checkJob("orderDetailJob", runOrderJob);
    }

    public void setRunOrderJob(Boolean runOrderJob) {
        this.runOrderJob = runOrderJob;
    }

    public Boolean getRunGlobalOrderJob() {
        return checkJob("globalOrderJob", runGlobalOrderJob);
    }

    public void setRunGlobalOrderJob(Boolean runGlobalOrderJob) {
        this.runGlobalOrderJob = runGlobalOrderJob;
    }

    public Boolean getRunTaskJob() {
        return checkJob("taskJob", runTaskJob);
    }

    public void setRunTaskJob(Boolean runTaskJob) {
        this.runTaskJob = runTaskJob;
    }

    public Boolean getRunRestaurantJob() {
        return runRestaurantJob;
    }

    public void setRunRestaurantJob(Boolean runRestaurantJob) {
        this.runRestaurantJob = runRestaurantJob;
    }

    public Boolean getRunScheduleReportJob() {
        return runScheduleReportJob;
    }

    public void setRunScheduleReportJob(Boolean runScheduleReportJob) {
        this.runScheduleReportJob = runScheduleReportJob;
    }

    public Double getDriverPayForDelivery() {
        return this.driverPayForDelivery;
    }

    public Double getServiceFeeGlobal() {
        return serviceFeeGlobal;
    }

    public Double getServiceFeeCustom() {
        return serviceFeeCustom;
    }

    public Double getServiceFeePhonein() {
        return serviceFeePhonein;
    }

    public List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public List<String> getTaskTypes(){
        return (List<String>) Stream.of(TaskType.values());
    }

    private Boolean checkJob(String jobId, Boolean enabled){
        if(enabled){
            //check the db - check if the db has already been checked
            if(jobId.equals("orderDetailJob")){
                if(runOrderJobDb==null){
                    runOrderJobDb = checkJobDb(jobId);
                    return runOrderJobDb;
                }else{
                    return runOrderJobDb;
                }
            }else if(jobId.equals("globalOrderJob")){
                if(runGlobalOrderJobDb==null){
                    runGlobalOrderJobDb = checkJobDb(jobId);
                    return runGlobalOrderJobDb;
                }else{
                    return runGlobalOrderJobDb;
                }
            }else if(jobId.equals("taskJob")){
                if(runTaskJobDb==null){
                    runTaskJobDb = checkJobDb(jobId);
                    return runTaskJobDb;
                }else{
                    return runTaskJobDb;
                }
            }else if(jobId.equals("driverJob")){
                if(runDriverJobDb==null){
                    runDriverJobDb = checkJobDb(jobId);
                    return runDriverJobDb;
                }else{
                    return runDriverJobDb;
                }
            }else if(jobId.equals("restaurantJob")){
                if(runRestaurantJobDb==null){
                    runRestaurantJobDb = checkJobDb(jobId);
                    return runRestaurantJobDb;
                }else{
                    return runRestaurantJobDb;
                }
            }else if(jobId.equals("scheduleReportJob")){
                if(runScheduleReportJobDb==null){
                    runScheduleReportJobDb = checkJobDb(jobId);
                    return runScheduleReportJobDb;
                }else{
                    return runScheduleReportJobDb;
                }
            }
        }
        return Boolean.FALSE;
    }

    private Boolean checkJobDb(String jobId){
        JobStatusRepository jobRepository = CollectorRegistry.getBean(JobStatusRepository.class);
        JobEntity jobEntity = jobRepository.findByJobId(jobId);
        if(jobEntity!=null){
            if(jobEntity.getEnabled()){
                log.info("checkJob:" + jobId + " ENABLED and will run");
                jobEntity.setLastRun(LocalDateTime.now());
                jobRepository.save(jobEntity);
            }else{
                log.info("checkJob:" + jobId + " NOT enabled in database and will NOT run");
            }
            return jobEntity.getEnabled();
        }
        return Boolean.FALSE;
    }

    public String getQBOAppClientId() {
        return QBOAppClientId;
    }

    public void setQBOAppClientId(String QBOAppClientId) {
        this.QBOAppClientId = QBOAppClientId;
    }

    public String getQBOAppClientSecret() {
        return QBOAppClientSecret;
    }

    public void setQBOAppClientSecret(String QBOAppClientSecret) {
        this.QBOAppClientSecret = QBOAppClientSecret;
    }

    public String getQBOAppRedirectUri() {
        return QBOAppRedirectUri;
    }

    public void setQBOAppRedirectUri(String QBOAppRedirectUri) {
        this.QBOAppRedirectUri = QBOAppRedirectUri;
    }

    public String getQBOState() {
        return QBOState;
    }

    public void setQBOState(String QBOState) {
        this.QBOState = QBOState;
    }

    public String getQBOApiCall() {
        return QBOApiCall;
    }

    public void setQBOApiCall(String QBOApiCall) {
        this.QBOApiCall = QBOApiCall;
    }

    //generic setting getter/setter
    public String getSetting(String section, String name){
        settingRepository = CollectorRegistry.getBean(SettingRepository.class);
        SettingEntity settingEntity = settingRepository.findBySectionAndName(section, name);
        if(settingEntity==null) return null;
        return settingEntity.getValue();
    }

    public Boolean getSettingAsBoolean(String section, String name, Boolean defaultValue){
        String tempValue = getSetting(section, name);
        if(tempValue==null) return defaultValue;
        Boolean tempBool = Boolean.valueOf(tempValue);
        if(tempBool==null) return defaultValue;
        return tempBool;
    }

    public Long getSettingAsLong(String section, String name, Long defaultValue){
        String tempValue = getSetting(section, name);
        if(tempValue==null) return defaultValue;
        Long tempLong = Long.valueOf(tempValue);
        if(tempLong==null) return defaultValue;
        return tempLong;
    }

    public void setSetting(String section, String name, String value){
        setSetting(section,name,null,value);
    }
    //TODO: add generic call to handle List, Double, Integer types
    public void setSetting(String section, String name, String description, String value){
        log.info("setSetting: section:" + section + " name:" + name + " description:" + description + " value:" + value);
        settingRepository = CollectorRegistry.getBean(SettingRepository.class);
        log.info("setSetting: prior to running find");
        SettingEntity settingEntity = settingRepository.findBySectionAndName(section,name);
        if(settingEntity==null){
            log.info("setSetting: find returned null. Creating new setting");
            settingEntity = new SettingEntity(section,name,description,value, SettingEntity.ValueType.STRING);
        }else{
            log.info("setSetting: find returned:" + settingEntity.toString());
            settingEntity.setValue(value);
            if(description!=null){
                settingEntity.setDescription(description);
            }
        }
        log.info("Config: setSetting: section:" + section + " name:" + name + " value:" + value);
        settingRepository.save(settingEntity);
    }

    public String getQBOToken(){
        return getSetting("QBO", "Token");
    }

    public void setQBOToken(String value){
        log.info("setQBOToken: value:" + value);
        setSetting("QBO", "Token","Token for QBO API calls",value );
    }

    public String getQBORefreshToken(){
        return getSetting("QBO", "RefreshToken");
    }

    public void setQBORefreshToken(String value){
        log.info("setQBORefreshToken: value:" + value);
        setSetting("QBO","RefreshToken","Refresh Token for QBO API calls",value);
    }

    public String getQBORealmId(){
        return getSetting("QBO", "RealmId");
    }

    public void setQBORealmId(String value){
        setSetting("QBO","RealmId","RealmId for QBO API calls",value);
    }

    public String getQBOAuthCode(){
        return getSetting("QBO", "AuthCode");
    }

    public void setQBOAuthCode(String value){
        setSetting("QBO","AuthCode","AuthCode for QBO API calls",value);
    }

    public String getQBOMinorVersion(){
        return getSetting("QBO", "MinorVersion");
    }

    public void setQBOMinorVersion(String value){
        setSetting("QBO","MinorVersion","MinorVersion for QBO API calls",value);
    }

    public String getQBOAccountNumber(String accountName){
        return getSetting("QBOAccount", accountName);
    }

    public void setQBOAccountNumber(String accountName, String value){
        setSetting("QBOAccount",accountName,null,value);
    }

    //Notification Settings
    public Boolean getEmailNotifications(String driverId, Boolean defaultValue){
        return getSettingAsBoolean(driverId, emailNotification, defaultValue);
    }

    public void setEmailNotifications(String driverId, Boolean value){
        setSetting(driverId,emailNotification,"Send notifications via email",value.toString());
    }


}
