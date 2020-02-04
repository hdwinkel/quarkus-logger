package de.egladil.winkel.logger.domain;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;


@JsonbPropertyOrder({
    "temperature",
    "humidity"
    })
public class BodyDHT {

    @JsonbProperty("temperature")
    private Double temperature;
    @JsonbProperty("humidity")
    private Double humidity;

    @JsonbProperty("temperature")
    public Double getTemperature() {
        return temperature;
    }

    @JsonbProperty("temperature")
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @JsonbProperty("humidity")
    public Double getHumidity() {
        return humidity;
    }

    @JsonbProperty("humidity")
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Body::temperature=");
        builder.append(temperature);
        builder.append(":humidity=");
        builder.append(humidity);
        return builder.toString();
    }

}
