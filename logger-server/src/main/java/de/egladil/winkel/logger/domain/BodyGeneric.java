package de.egladil.winkel.logger.domain;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;


@JsonbPropertyOrder({
    "measurement",
    "value"
    })
public class BodyGeneric {

    @JsonbProperty("measurement")
    private String measurement;
    @JsonbProperty("value")
    private Double value;

    @JsonbProperty("temperature")
    public String getMeasurement() {
        return measurement;
    }

    @JsonbProperty("measurement")
    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    @JsonbProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonbProperty("value")
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Body::measurement=");
        builder.append(measurement);
        builder.append(":value=");
        builder.append(value);
        return builder.toString();
    }

}
