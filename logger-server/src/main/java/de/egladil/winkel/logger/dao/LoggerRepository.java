package de.egladil.winkel.logger.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import de.egladil.winkel.logger.domain.*;

@ApplicationScoped
public class LoggerRepository{

    static Map<String, LoggerEntry> data = new ConcurrentHashMap<>();    public List<LoggerEntry> all() {
        return new ArrayList<>(data.values());
    }    public LoggerEntry getById(String id) {
        return data.get(id);
    }    public LoggerEntry save(LoggerEntry loggerEntry) {
        data.put(loggerEntry.getUuid(), loggerEntry);
        return loggerEntry;
    }    public void deleteById(String id) {
        data.remove(id);
    }

} 
