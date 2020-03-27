package de.egladil.winkel.logger.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/version")
public class VersionTracker {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "v3";
    }
}
