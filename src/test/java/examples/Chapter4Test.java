package examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Chapter4Test {

    private static RequestSpecification requestSpecification;

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpecification = new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us")
                .build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                spec(requestSpecification).
        when().
                get("/us/90210").
        then().
                assertThat().statusCode(200);

    }

    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectedBeverlyHills() {
        given().
                spec(requestSpecification).
        when().
                get("http://zippopotam.us/us/90210").
        then().
                spec(responseSpecification).
        and().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertBeverlyHills() {

        String placeName = given().
                spec(requestSpecification).
        when().
                get("http://zippopotam.us/us/90210").
        then().
                extract().
                path("places[0].'place name'");

        Assert.assertEquals(placeName, "Beverly Hills");
    }
}
