package ca.admin.delivermore.collector.config;

import ca.admin.delivermore.collector.data.tookan.Driver;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class DriverProcessor implements ItemProcessor<Driver, Driver> {

    List<Driver> validDrivers = new ArrayList<>();

    public DriverProcessor(List<Driver> validDrivers) {
        this.validDrivers = validDrivers;
    }

    @Override
    public Driver process(Driver driver) throws Exception {
        return driver.updateDriverIsActive(driver,validDrivers);
        /*
        //mark drivers not in the validDrivers list as inactive
        if(inDriverList(driver)){
            //log.info("DriverProcessor: checking driver: found:" + driver.getName());
            driver.setIsActive(1L);
            return driver;
        }else {
            //log.info("DriverProcessor: checking driver: NOT found:" + driver.getName());
            driver.setIsActive(0L);
            return driver;
        }

         */
    }

    private Boolean inDriverList(Driver checkDriver){
        for (Driver driver: validDrivers) {
            if(driver.getFleetId().equals(checkDriver.getFleetId())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
