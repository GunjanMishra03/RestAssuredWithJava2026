package org.example.tests.utilities;

import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.restassured.response.Response;

public final class ResponseAssertions {
    private ResponseAssertions() {
    }

    public static void assertStatusCode(Response response, int expectedStatus) {
        assertEquals(response.statusCode(), expectedStatus, "Unexpected HTTP status");
    }

    public static void assertJsonContentType(Response response) {
        response.then().contentType(containsString("application/json"));
    }

    public static void assertResponseTimeBelow(Response response, long thresholdMillis) {
        assertTrue(response.getTime() < thresholdMillis,
                "Response took " + response.getTime() + " ms; expected less than " + thresholdMillis + " ms");
    }
}
