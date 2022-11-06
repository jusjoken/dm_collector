package ca.admin.delivermore.collector.data;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private final Double driverPayForDelivery = 4.00;

    private String fromEmail = "tara.birch@delivermore.ca";
    private List<String> paymentMethods = new ArrayList<>(List.of("CASH", "CARD", "ONLINE"));
    private String jobs;
    private Boolean runDriverJob = Boolean.FALSE;
    private Boolean runOrderJob = Boolean.FALSE;
    private Boolean runGlobalOrderJob = Boolean.FALSE;

    private Boolean runTaskJob = Boolean.FALSE;

    private Boolean runRestaurantJob = Boolean.FALSE;

    public static Config instance = null;

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
        return runOrderJob;
    }

    public void setRunOrderJob(Boolean runOrderJob) {
        this.runOrderJob = runOrderJob;
    }

    public Boolean getRunGlobalOrderJob() {
        return runGlobalOrderJob;
    }

    public void setRunGlobalOrderJob(Boolean runGlobalOrderJob) {
        this.runGlobalOrderJob = runGlobalOrderJob;
    }

    public Boolean getRunTaskJob() {
        return runTaskJob;
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

    public Double getDriverPayForDelivery() {
        return this.driverPayForDelivery;
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
}
