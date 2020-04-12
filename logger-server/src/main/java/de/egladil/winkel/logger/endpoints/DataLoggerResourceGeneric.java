package de.egladil.winkel.logger.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.winkel.logger.domain.DeviceLoggerGeneric;
import de.egladil.winkel.logger.services.LoggerService;

@Path("/generic")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DataLoggerResourceGeneric {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoggerResourceGeneric.class); //log4j not device logger

    @Inject
    LoggerService loggerService;    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveLoggings(@Valid DeviceLoggerGeneric generic) {
        String ret = loggerService.createLoggerEntries(generic);
        LOG.info(ret);
        return Response.ok(ret).build();
    } 

}
