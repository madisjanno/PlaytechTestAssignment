package com.playtech.summerinternship.rest;

import com.playtech.summerinternship.data_structures.AggregatedData;
import com.playtech.summerinternship.data_structures.DataRequest;
import com.playtech.summerinternship.file.FileUtility;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Madis on 6.05.2016.
 */

@Path("aggr")
public class AggregatedDataService {

    @Path("query")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AggregatedData> getAggregatedData(DataRequest request) throws IOException {
        return FileUtility.getRequestedData(request.getPattern(), request.getStart(), request.getEnd());
    }

    @Path("query")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AggregatedData> getAggregatedData(@QueryParam("pattern") String path, @QueryParam("start") Long start, @QueryParam("end") Long end) throws Exception {
        if (path == null)
            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                        .entity("path parameter is mandatory")
                        .build()
            );
        if (start == null)
            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                    .entity("start parameter is mandatory")
                    .build()
            );
        if (end == null)
            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                    .entity("end parameter is mandatory")
                    .build()
            );
        return FileUtility.getRequestedData(path, start, end);
    }

    private AggregatedData composeData(String path, long start, long end) {
        return new AggregatedData("fdsf", null);
    }
}