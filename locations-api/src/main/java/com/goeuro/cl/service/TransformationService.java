package com.goeuro.cl.service;

import java.io.OutputStream;
import java.util.List;

import com.goeuro.cl.service.model.location.LocationModel;

/**
 * Service which transforms {@link LocationModel}s to the desired format.
 */
public interface TransformationService {
    /**
     * Method transforms the {@link LocationModel}s to the desired format and
     * writes the bytes to the {@link OutputStream}. <br>
     * <b>Note:</b><br>
     * The implementation of the method does not close the {@link OutputStream},
     * this is the responsibility of the caller!
     * 
     * @param locations
     *            list of locations to be transformed. The value must not be
     *            null!
     * @param outputStream
     *            the output stream to which the method will write. The value
     *            must not be null and the stream sould be writable!
     * @throws ServiceException
     *             exception thrown when an IO exception is thrown by the
     *             {@link OutputStream}
     */
    public void transform(List<LocationModel> locations, OutputStream outputStream) throws ServiceException;
}
