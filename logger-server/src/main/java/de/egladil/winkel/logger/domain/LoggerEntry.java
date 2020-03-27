package de.egladil.winkel.logger.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class LoggerEntry implements Serializable {

    /* serialVersionUID */
	private static final long serialVersionUID = 1L;

    @Id 
    String uuid;

    @NotNull
    LocalDateTime loggingTime;

    @NotNull
	@Size(min = 1, max = 20)
    String loggingDevice;

    @NotNull
	@Size(min = 1, max = 20)
    String loggingType;

    @NotNull
    Double loggingValue;

    @NotNull
    @Size(min =1, max = 20)
    String loggingUnit;

    public LoggerEntry(final LocalDateTime loggingTime, final String loggingDevice, final String loggingType,
            final Double loggingValue, final String loggingUnit) {

        final String id = loggingTime + loggingDevice + loggingType;
        final String uuid = UUID.nameUUIDFromBytes(id.getBytes()).toString();
        this.uuid=uuid;
        this.loggingTime=loggingTime;
        this.loggingDevice=loggingDevice;
        this.loggingType=loggingType;
        this.loggingValue=loggingValue;
        this.loggingUnit=loggingUnit;
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

    public String getLoggingUnit() {
        return loggingUnit;
    }

    @Override
    public String toString() {
        return "LoggerEntry{" +
                "uuid='" + uuid + '\'' +
                ", time='" + loggingTime + '\'' +
                ", device='" + loggingDevice + '\'' +
                ", type='" + loggingType + '\'' +
                ", value=" + loggingValue + '\'' +
                ", unit='" + loggingUnit + '\'' +
                '}';
    }
    

} 