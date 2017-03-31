package com.epam.step;

import com.epam.bean.Product;

import io.restassured.response.Response;

import static io.restassured.RestAssured.delete;


/**
 * Created by Mark_Harbunou on 3/31/2017.
 */
public class DeleteStep {

    public Response deleteProduct(Product product, String baseUri, String productPath) {
        Response r = delete(baseUri + productPath);


        return r;
    }
}
