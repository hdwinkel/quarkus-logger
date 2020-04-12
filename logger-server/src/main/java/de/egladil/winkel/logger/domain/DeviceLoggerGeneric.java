package de.egladil.winkel.logger.domain;


import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;


@JsonbPropertyOrder({
    "Header",
    "Body"
    })
public class DeviceLoggerGeneric {

    @JsonbProperty("Header")
    private Header header;
    @JsonbProperty("Body")
    private BodyGeneric body;
    
    @JsonbProperty("Header")
    public Header getHeader() {
    return header;
    }
    
    @JsonbProperty("Header")
    public void setHeader(Header header) {
    this.header = header;
    }
    
    @JsonbProperty("Body")
    public BodyGeneric getBody() {
    return body;
    }
    
    @JsonbProperty("Body")
    public void setBody(BodyGeneric body) {
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
