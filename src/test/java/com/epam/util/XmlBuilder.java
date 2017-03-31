package com.epam.util;

import com.epam.bean.Product;

/**
 * Created by Mark_Harbunou on 3/31/2017.
 */
public  class XmlBuilder {

    public static String xmlBuilderForPostCreate(Product product) {

        String body = "<resource>" +
                         "<ID>" + product.getId() + "</ID" +
                         "<NAME>" + product.getName() + "</NAME>" +
                         "<PRICE>" + product.getPrice() + "</PRICE>" +
                      "</resource>";

        return body;
    }

    public static String xmlBuilderForPutCreate(Product product) {

        String body = "<resource>" +
                         "<NAME>" + product.getName() + "</NAME>" +
                         "<PRICE>" + product.getPrice() + "</PRICE>" +
                      "</resource>";

        return body;
    }

    public static String xmlBuilderPriceForChange(String price) {
        String body = "<resource>" +
                "<PRICE>" + price + "</PRICE>" +
                "</resource>";

        return body;
    }

    public static String xmlBuilderNameForChange(String name) {
        String body = "<resource>" +
                "<NAME>" + name + "</NAME>" +
                "</resource>";

        return body;
    }
}
