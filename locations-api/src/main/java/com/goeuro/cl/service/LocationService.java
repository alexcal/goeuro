package com.goeuro.cl.service;

import java.util.List;

import com.goeuro.cl.service.model.location.LocationModel;

/**
 * Location service.
 *
 */
public interface LocationService {

    /**
     * Method that returns a list of {@link LocationModel}s, based on the city
     * name
     * 
     * @param cityName
     *            the name of the city. The parameter should not be null.
     * @return a list with {@link LocationModel}
     * @throws ServiceException
     *             this exception is thrown when the remote call has failed.
     */
    public List<LocationModel> listLocations(String cityName) throws ServiceException;
}
