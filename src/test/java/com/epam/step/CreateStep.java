package com.epam.step;

import com.epam.bean.Product;
import com.epam.util.XmlBuilder;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Created by Mark_Harbunou on 3/31/2017.
 */
public class CreateStep {

    public Response createNewProductWithPut(Product product, String baseUri, String productPath) {
       Response r = given().
                         contentType("application/xml").
                         body(XmlBuilder.xmlBuilderForPutCreate(product)).
                    when().
                         put(baseUri + productPath);


                  return r;
    }
}
