package examples;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(DataProviderRunner.class)
public class Chapter3Test {

    @DataProvider
    public static Object[][] zipCodeAndPlaces() {
        return new Object[][] {
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"},
                {"nl", "1001", "Amsterdam"}
        };
    }

    @Test
    @UseDataProvider("zipCodeAndPlaces")
    public void requestUsZipCode90210ZipCodeFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {

        given().
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
        when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
        then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }
}
