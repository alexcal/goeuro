package com.goeuro.cl.service;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import com.goeuro.cl.service.model.location.GeoPosition;
import com.goeuro.cl.service.model.location.LocationModel;

/**
 * Default implementation for {@link TransformationService}. It uses Apache
 * Commons CSV to transform the data.
 *
 */
public class TransformationServiceDefault implements TransformationService {
    private static final Logger log = Logger.getLogger(TransformationService.class);

    @Override
    public void transform(List<LocationModel> locations, OutputStream outputStream) throws ServiceException {
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);
        try {
            CSVPrinter printer = CSVFormat.DEFAULT.withNullString("NULL").withRecordSeparator(";").print(osw);
            for (LocationModel loc : locations) {
                printer.printRecord(parseValues(loc));
            }
            printer.flush();
        } catch (Exception e) {
            log.error("An exception hapened when trying to write the CSS: ", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Parses the model and returns an array containing the required values for
     * the CSV printer.
     * 
     * @param locationModel
     * @return
     */
    private Object[] parseValues(LocationModel locationModel) {
        GeoPosition gp = locationModel.getGeoPosition();
        if (null == gp) {
            gp = new GeoPosition();
        }
        Object[] objarr = new Object[] { locationModel.getId(), locationModel.getName(), locationModel.getType(),
                gp.getLatitude(), gp.getLongitude() };
        return objarr;
    }
}
