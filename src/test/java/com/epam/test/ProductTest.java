package com.epam.test;

import com.epam.bean.Product;
import com.epam.step.ChangeStep;
import com.epam.step.CreateStep;
import com.epam.step.DeleteStep;
import com.epam.step.GetProductStep;
import com.epam.util.XmlBuilder;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.codehaus.groovy.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


/**
 * Created by Mark_Harbunou on 3/31/2017.
 */
public class ProductTest {
    private final String baseUri = "http://www.thomas-bayer.com/sqlrest/PRODUCT";
    Product product;
    String productPath;
    CreateStep createStep;
    DeleteStep deleteStep;
    ChangeStep changeStep;
    GetProductStep getProductStep;


    @BeforeMethod
    public void setUp() {
        product = new Product(50000, "sword", 5.5);
        productPath = "/" + product.getId();
        createStep = new CreateStep();
        deleteStep = new DeleteStep();
        changeStep = new ChangeStep();
        getProductStep = new GetProductStep();

    }

    @DataProvider(name = "invalid id for 404")
    public Object[][] invalidDataForFourHundredFour() throws Exception {
        return new Object[][]{
                {"/lol"},
                {"/5&#423"},
                {"/®"},     //!!!! using restGate i was receive 500 error
                {"/тест"}  //!!!! using restGate i was receive 500 error
        };
    }

    @DataProvider(name = "valid id for create products")
    public Object[][] validDataForCreateProducts() throws Exception {
        return new Object[][]{
                {999},
                {-1},
                {500000000}
        };
    }

    @DataProvider(name = "valid price for change")
    public Object[][] validDataForChangePrice() throws Exception {
        return new Object[][]{
                {new Product(99)},
                {new Product(5.5)},
                {new Product(0)},
                {new Product(-1.0)}
        };
    }



    @DataProvider(name = "valid name for change")
    public Object[][] validDataForChangeName() throws Exception {
        return new Object[][]{
                {new Product("not sword")},
                {new Product("shield")},
                {new Product("")},
                {new Product("99")}
        };
    }


    @Test
    public void goToProductPage() {
        get(baseUri).then().statusCode(200);
    }


    @Test(dataProvider = "invalid id for 404")
    public void negativeTestForInvalidUriWithFourHundredFour(String path) {
        getProductStep.getProduct(baseUri, path).then().assertThat().statusCode(404);
    }

    @Test(dataProvider = "valid price for change")
    public void changePriceInProductPositiveTest(Product productWithPrice) {
        createStep.createNewProductWithPut(product, baseUri, productPath);
        changeStep.changePrice(Double.toString(productWithPrice.getPrice()), baseUri, productPath);
        getProductStep.getProduct(baseUri, productPath).then().
                        body("PRODUCT.PRICE", equalTo(Double.toString(productWithPrice.getPrice())));


    }

    @Test
    public void changePriceInProductNegativeTestForFiveHundred() {
        createStep.createNewProductWithPut(product, baseUri, productPath);
        changeStep.changePrice("lol", baseUri, productPath).then().statusCode(500);
    }

    @Test
    public void changePriceInProductNegativeTestForFouHundredThree() {
        createStep.createNewProductWithPut(product, baseUri, productPath);
        changeStep.changePrice("®", baseUri, productPath).then().statusCode(403);
    }


    @Test(dataProvider = "valid name for change")
    public void changeNameInProductTest(Product productWithName) {
        createStep.createNewProductWithPut(product, baseUri, productPath);
        changeStep.changeName(productWithName.getName(), baseUri, productPath);
        getProductStep.getProduct(baseUri, productPath).
                then().
                body("PRODUCT.NAME", equalTo(productWithName.getName()));

    }

    @Test
    public void deleteProductTest() {
        createStep.createNewProductWithPut(product, baseUri, productPath);
        deleteStep.deleteProduct(product, baseUri, productPath).then().body("resource.deleted.text()", equalTo(Integer.toString(product.getId())));
    }


    @Test(dataProvider = "valid id for create products")
    public void createNewProductWithPutTest(int id) {
        product.setId(id);
        createStep.createNewProductWithPut(product, baseUri, productPath).then().
                statusCode(201);
        deleteStep.deleteProduct(product,baseUri,productPath);
    }


    //this test does not work
    @Ignore
    public void createProductWithPostTest() {

        //  with().params("ID",200,"NAME","sword","PRICE",5.5).when().post(baseUri).then().statusCode(201);
        Response r = given().contentType("application/xml").
                body(XmlBuilder.xmlBuilderForPostCreate(product)).
                when().
                post(baseUri + productPath);

        String body = r.getBody().asString();
        System.out.println(body);
    }

    @AfterMethod
    public void tearDown() {
        deleteStep.deleteProduct(product, baseUri, productPath);
    }







    /*XmlBuilder.xmlBuilderForPost(200,"sword",5.5)
    <lol>
<id>50000</ID>
<NAME>sword</NAME>
<PRICE>5.5</PRICE>
</lol>
     */
}
