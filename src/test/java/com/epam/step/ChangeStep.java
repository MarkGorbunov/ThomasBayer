package com.epam.step;

import com.epam.bean.Product;
import com.epam.util.XmlBuilder;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Created by Mark_Harbunou on 3/31/2017.
 */
public class ChangeStep {
    public Response changePrice(String price,String baseUri,String productPath) {
        Response r = given().
                        contentType("application/xml").
                        body(XmlBuilder.xmlBuilderPriceForChange(price)).
                     when().
                        post(baseUri + productPath);
        return r;
    }

    public Response changeName(String name,String baseUri,String productPath) {
        Response r =  given().
                         contentType("application/xml").
                         body(XmlBuilder.xmlBuilderNameForChange(name)).
                      when().
                         post(baseUri + productPath);
        return r;
    }
}
