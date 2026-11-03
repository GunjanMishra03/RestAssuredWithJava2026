package org.example.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.config.ConfigurationManager;

public final class RequestSpecificationFactory {
    private RequestSpecificationFactory() {
    }

    public static RequestSpecification create() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigurationManager.baseUrl())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new ApiLoggingFilter())
                .build();
    }
}
