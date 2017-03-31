package com.epam.step;

import io.restassured.response.Response;

import static io.restassured.RestAssured.get;

/**
 * Created by Mark_Harbunou on 3/31/2017.
 */
public class GetProductStep {
    public Response getProduct(String baseUri, String productPath) {
        Response r = get(baseUri + productPath);
        return r;
    }
}
