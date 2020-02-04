package de.egladil.winkel.logger.domain;

import javax.json.bind.annotation.JsonbProperty;

import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbPropertyOrder;


@JsonbPropertyOrder({
    "Timestamp",
    "Device"
    })
public class Header {

    @JsonbProperty("Timestamp")
    private LocalDateTime timestamp;
    @JsonbProperty("Device")
    private String device;

    @JsonbProperty("Timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @JsonbProperty("Timestamp")
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonbProperty("Device")
    public String getDevice() {
        return device;
    }

    @JsonbProperty("Device")
    public void setDevice(String device) {
        this.device = device;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Header::timestamp=");
        builder.append(timestamp);
        builder.append(":device=");
        builder.append(device);
        return builder.toString();
     
    }


}
