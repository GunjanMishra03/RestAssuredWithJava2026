package org.example.client;

import com.aventstack.extentreports.Status;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.reporting.ExtentReportManager;

public class ApiLoggingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(ApiLoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification request,
                           FilterableResponseSpecification responseSpecification,
                           FilterContext context) {
        String requestBody = request.getBody() == null ? "" : request.getBody().toString();
        String requestDetails = "REQUEST " + request.getMethod() + " " + request.getURI()
                + (requestBody.isBlank() ? "" : "\nRequest body:\n" + requestBody);
        LOGGER.info(requestDetails);
        ExtentReportManager.log(Status.INFO, requestDetails);

        try {
            Response response = context.next(request, responseSpecification);
            String responseDetails = "RESPONSE " + response.statusCode() + " ("
                    + response.getTime() + " ms)\n" + response.asPrettyString();
            LOGGER.info(responseDetails);
            ExtentReportManager.log(Status.INFO, responseDetails);
            return response;
        } catch (RuntimeException exception) {
            LOGGER.error("Request failed: {} {}", request.getMethod(), request.getURI(), exception);
            ExtentReportManager.log(Status.FAIL, "Request failed: " + exception.getMessage());
            throw exception;
        }
    }
}
