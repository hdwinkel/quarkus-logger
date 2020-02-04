package de.egladil.winkel.logger.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
    public LoggerService(final LoggerRepository loggerRepo) {
        this.loggerRepo = loggerRepo;
    }

    @Inject
    EntityManager em;

    @Transactional
    public String createLoggerEntries(final DeviceLoggerDHT dht) {

        final LoggerEntry loggerEntryTemperature = new LoggerEntry(dht.getHeader().getTimestamp(),
                dht.getHeader().getDevice(), "temperature", dht.getBody().getTemperature());
        em.persist(loggerEntryTemperature);
        final LoggerEntry loggerEntryHumidity = new LoggerEntry(dht.getHeader().getTimestamp(),
                dht.getHeader().getDevice(), "humidity", dht.getBody().getHumidity());
        em.persist(loggerEntryHumidity);

        this.loggerRepo.save(loggerEntryTemperature);
        this.loggerRepo.save(loggerEntryHumidity);

        LOG.info("RepoSize:" + loggerRepo.all().size());

        return "entries:\n" + loggerEntryTemperature + "\n" + loggerEntryHumidity;
    }



}

