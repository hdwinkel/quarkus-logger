package de.egladil.winkel.logger.domain;


import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;


@JsonbPropertyOrder({
    "Header",
    "Body"
    })
public class DeviceLoggerDHT {

    @JsonbProperty("Header")
    private Header header;
    @JsonbProperty("Body")
    private BodyDHT body;
    
    @JsonbProperty("Header")
    public Header getHeader() {
    return header;
    }
    
    @JsonbProperty("Header")
    public void setHeader(Header header) {
    this.header = header;
    }
    
    @JsonbProperty("Body")
    public BodyDHT getBody() {
    return body;
    }
    
    @JsonbProperty("Body")
    public void setBody(BodyDHT body) {
    this.body = body;
    }    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Header\n");
        builder.append(this.header);
        builder.append("\nBody\n");
        builder.append(this.body);
        return builder.toString();
    }

}
