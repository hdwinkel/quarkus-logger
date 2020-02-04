package de.egladil.winkel.logger.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.winkel.logger.dao.LoggerRepository;
import de.egladil.winkel.logger.domain.DeviceLoggerDHT;
import de.egladil.winkel.logger.domain.LoggerEntry;

@ApplicationScoped
public class LoggerService {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerService.class); //log4j not device logger

    private final LoggerRepository loggerRepo;
    

    @Inject
    public LoggerService(LoggerRepository loggerRepo){
       this.loggerRepo=loggerRepo;
    }  

    public String createLoggerEntries(DeviceLoggerDHT dht) {
    
        LoggerEntry loggerEntryTemperature = new LoggerEntry(dht.getHeader().getTimestamp(), dht.getHeader().getDevice(), "temperature", dht.getBody().getTemperature());
        LoggerEntry loggerEntryHumidity = new LoggerEntry(dht.getHeader().getTimestamp(), dht.getHeader().getDevice(), "humidity", dht.getBody().getHumidity());

        this.loggerRepo.save(loggerEntryTemperature);
        this.loggerRepo.save(loggerEntryHumidity);

        LOG.info("RepoSize:" + loggerRepo.all().size());

        return "entries:\n" + loggerEntryTemperature + "\n" + loggerEntryHumidity;
    }



}

