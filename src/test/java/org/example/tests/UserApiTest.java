package org.example.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;

import io.restassured.response.Response;
import org.example.client.UserApiClient;
import org.example.config.ConfigurationManager;
import org.example.listeners.ExtentTestListener;
import org.example.models.User;
import org.example.tests.data.UserTestData;
import org.example.tests.dataproviders.UserDataProvider;
import org.example.tests.utilities.ResponseAssertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
public class UserApiTest {
    private final UserApiClient userApiClient = new UserApiClient();

    @Test(dataProvider = "users", dataProviderClass = UserDataProvider.class)
    public void getUser_shouldMatchExpectedDataAndSchema(UserTestData testData) {
        Response response = userApiClient.getUser(testData.getUserId());

        ResponseAssertions.assertStatusCode(response, 200);
        ResponseAssertions.assertJsonContentType(response);
        ResponseAssertions.assertResponseTimeBelow(
                response, ConfigurationManager.responseTimeThresholdMillis());
        response.then().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

        User user = response.as(User.class);
        assertEquals(user.getId(), testData.getUserId(), "Unexpected user ID");
        assertEquals(user.getName(), testData.getExpectedName(), "Unexpected name");
        assertEquals(user.getUsername(), testData.getExpectedUsername(), "Unexpected username");
        assertEquals(user.getEmail(), testData.getExpectedEmail(), "Unexpected email");
    }
}
