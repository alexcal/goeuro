package com.goeuro.cl.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.goeuro.cl.service.LocationService;
import com.goeuro.cl.service.LocationServiceRest;
import com.goeuro.cl.service.ServiceException;
import com.goeuro.cl.service.TransformationService;
import com.goeuro.cl.service.TransformationServiceDefault;
import com.goeuro.cl.service.model.location.LocationModel;

/**
 * Main entry point for the command line tool.
 */
public class MainEntry {

    private static final Logger log = Logger.getLogger("Main");
    private static String location;
    private static String fileName = "output.csv";

    public static void main(String[] args) {
        parseArgs(args);
        OutputStream os = null;
        try {
            os = openStream();
        } catch (IOException e) {
            log.error("Unable to open file for writing the results!");
            System.exit(-1);
        }
        // List locations
        LocationService ls = new LocationServiceRest("http://api.goeuro.com/api/v2/position/suggest/en/");
        List<LocationModel> locations = null;
        try {
            locations = ls.listLocations(location);
        } catch (ServiceException e) {
            log.error("An exception happened when calling the remote service!");
            System.exit(-1);
        }
        // Transform to CSV
        TransformationService ts = new TransformationServiceDefault();
        try {
            ts.transform(locations, os);
        } catch (ServiceException e) {
            log.error("An exception happened when transforming to CSV!", e.getCause());
            System.exit(-1);
        }

        try {
            os.close();
        } catch (IOException e) {
            // ignore this
        }
    }

    /**
     * In a latter version, Apache CLI can be used to parse the command line
     * parameters.
     * 
     * @param args
     */
    private static void parseArgs(String[] args) {
        location = args[0];
    }

    private static OutputStream openStream() throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);

        return fos;
    }
}
