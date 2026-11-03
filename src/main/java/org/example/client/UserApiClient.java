package org.example.client;

import io.restassured.response.Response;
import org.example.constants.ApiEndpoints;

public class UserApiClient extends BaseApiClient {
    public Response getUser(int userId) {
        return request()
                .pathParam("userId", userId)
                .when()
                .get(ApiEndpoints.USERS);
    }
}
