package org.example.client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {
    protected RequestSpecification request() {
        return given().spec(RequestSpecificationFactory.create());
    }
}
