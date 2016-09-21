package com.goeuro.cl.service;

import java.util.List;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.goeuro.cl.service.model.location.LocationModel;

/**
 * JAX-RS implementation for the {@link LocationService} service.
 */
public class LocationServiceRest implements LocationService {

    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger(LocationService.class);

    private final WebTarget target;

    /**
     * c-tor for the service.
     * 
     * @param url
     *            this parameter must not be null.
     */
    public LocationServiceRest(String url) {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        target = client.target(url);

    }

    public List<LocationModel> listLocations(String cityName) throws ServiceException {
        WebTarget queryTarget = target.path(cityName);
        try {
            List<LocationModel> response = queryTarget.request().get()
                    .readEntity(new GenericType<List<LocationModel>>() {
                    });
            return response;
        } catch (ProcessingException ex) {
            log.error("Error calling the service...", ex);
            throw new ServiceException(ex);
        }
    }

}
