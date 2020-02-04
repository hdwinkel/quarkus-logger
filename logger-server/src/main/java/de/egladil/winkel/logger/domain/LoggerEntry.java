package de.egladil.winkel.logger.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoggerEntry implements Serializable {

    String uuid;
    LocalDateTime loggingTime;
    String loggingDevice;
    String loggingType;
    Double loggingValue;

    public LoggerEntry(final LocalDateTime loggingTime, final String loggingDevice, final String loggingType,
            final Double loggingValue) {

        final String id = loggingTime + loggingDevice + loggingType;
        final String uuid = UUID.nameUUIDFromBytes(id.getBytes()).toString();
        this.uuid=uuid;
        this.loggingTime=loggingTime;
        this.loggingDevice=loggingDevice;
        this.loggingType=loggingType;
        this.loggingValue=loggingValue;
    } 

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getLoggingTime() {
        return loggingTime;
    }

    public String getLoggingDevice() {
        return loggingDevice;
    }

    public String getLoggingType() {
        return loggingType;
    }

    public Double getLoggingValue() {
        return loggingValue;
    }

    @Override
    public String toString() {
        return "LoggerEntry{" +
                "uuid='" + uuid + '\'' +
                ", time='" + loggingTime + '\'' +
                ", device='" + loggingDevice + '\'' +
                ", type='" + loggingType + '\'' +
                ", value=" + loggingValue +
                '}';
    }
    

} 