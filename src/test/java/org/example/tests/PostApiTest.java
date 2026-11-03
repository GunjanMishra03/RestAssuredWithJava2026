package org.example.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostApiTest {
    @Test
    public void addPlace_shouldReturnSuccess() {
        given().baseUri("https://rahulshettyacademy.com/")
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Frontline house\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}").when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("status", org.hamcrest.Matchers.equalTo("OK"));

    }
}
