
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class Api_Test {
    @BeforeClass
    public static void initializeRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "TEST_TASK_REST/rest/orderplace";
        RestAssured.registerParser("aplication/json", Parser.JSON);
    }

    @Test
    public void getHallsFromCinema() {

        given()
                .when()
                .get("/cinema_id/{cinema_id}", 2)
                .then()
                .statusCode(200)
                .content("halls.HallName", hasItems("Hall2", "Hall7", "Hall11", "Hall13"));

    }

    @Test
    public void getCinemas() {
        given()
                .when()
                .get("")
                .then()
                .statusCode(200)
                .content("cinemas.CinemaName", hasItems("Cinema1", "Cinema2", "Cinema3", "Cinema4"));

    }

    @Test
    public void getHall() {

        given()
                .when()
                .get("/cinema_id/{cinema_id}/Hall_id/{hall_id}", 2, 7)
                .then()
                .statusCode(200)
                .body("HallName", equalTo("Hall7"));


    }

    @Test
    public void addCinema() {
        String json = "{\"ROW_ID\":6,\"CinemaName\":\"CinemaFromRest\",\"HallCount\":2}";
        given()
                .when()
                .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("aplication/json", ContentType.JSON)))
                .contentType("aplication/json")
                .body(json)
                .post()
                .then()
                .statusCode(200);

        given()
                .when()
                .get("")
                .then()
                .statusCode(200)
                .content("cinemas.CinemaName", hasItem("CinemaFromRest"));


    }

    @Test
    public void addHall() {

        String json = "{\"ROW_ID\":162,\"HallName\":\"Hall162_fromREST\",\"PlaceCount\":100}";
        given()
                .when()
                .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("aplication/json", ContentType.JSON)))
                .contentType("aplication/json")
                .body(json)
                .post("/cinema_id/{cinema_id}", 1)
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/cinema_id/{cinema_id}", 1)
                .then()
                .statusCode(200)
                .content("halls.HallName", hasItem("Hall162_fromREST"));
    }

    @Test
    public void updatePlaceStatus1() {
        String json = "{\"places\":[{\"Row\":1,\"place\":1,\"free\":true}," +
                "{\"Row\":1,\"place\":2,\"free\":true},{\"Row\":1,\"place\":3,\"free\":true}," +
                "{\"Row\":1,\"place\":4,\"free\":true},{\"Row\":1,\"place\":5,\"free\":true}," +
                "{\"Row\":1,\"place\":6,\"free\":true},{\"Row\":1,\"place\":7,\"free\":true}," +
                "{\"Row\":1,\"place\":8,\"free\":true},{\"Row\":2,\"place\":1,\"free\":true}," +
                "{\"Row\":2,\"place\":2,\"free\":true},{\"Row\":2,\"place\":3,\"free\":true}," +
                "{\"Row\":2,\"place\":4,\"free\":true},{\"Row\":2,\"place\":5,\"free\":true}," +
                "{\"Row\":2,\"place\":6,\"free\":true},{\"Row\":2,\"place\":7,\"free\":true}," +
                "{\"Row\":2,\"place\":8,\"free\":true},{\"Row\":2,\"place\":9,\"free\":true}," +
                "{\"Row\":3,\"place\":1,\"free\":true},{\"Row\":3,\"place\":2,\"free\":true}," +
                "{\"Row\":3,\"place\":3,\"free\":true},{\"Row\":3,\"place\":4,\"free\":true}," +
                "{\"Row\":3,\"place\":5,\"free\":true},{\"Row\":3,\"place\":6,\"free\":true}," +
                "{\"Row\":3,\"place\":7,\"free\":true},{\"Row\":3,\"place\":8,\"free\":true}," +
                "{\"Row\":3,\"place\":9,\"free\":true},{\"Row\":4,\"place\":1,\"free\":true}," +
                "{\"Row\":4,\"place\":2,\"free\":true},{\"Row\":4,\"place\":3,\"free\":true}," +
                "{\"Row\":4,\"place\":4,\"free\":true},{\"Row\":4,\"place\":5,\"free\":true}," +
                "{\"Row\":4,\"place\":6,\"free\":true},{\"Row\":4,\"place\":7,\"free\":true}," +
                "{\"Row\":4,\"place\":8,\"free\":true},{\"Row\":4,\"place\":9,\"free\":true}," +
                "{\"Row\":5,\"place\":1,\"free\":true},{\"Row\":5,\"place\":2,\"free\":true}," +
                "{\"Row\":5,\"place\":3,\"free\":true},{\"Row\":5,\"place\":4,\"free\":true}," +
                "{\"Row\":5,\"place\":5,\"free\":true},{\"Row\":5,\"place\":6,\"free\":true}," +
                "{\"Row\":5,\"place\":7,\"free\":true},{\"Row\":5,\"place\":8,\"free\":true}," +
                "{\"Row\":5,\"place\":9,\"free\":true},{\"Row\":6,\"place\":1,\"free\":true}," +
                "{\"Row\":6,\"place\":2,\"free\":true},{\"Row\":6,\"place\":3,\"free\":true}," +
                "{\"Row\":6,\"place\":4,\"free\":true},{\"Row\":6,\"place\":5,\"free\":true}," +
                "{\"Row\":6,\"place\":6,\"free\":true},{\"Row\":6,\"place\":7,\"free\":true}," +
                "{\"Row\":6,\"place\":8,\"free\":true},{\"Row\":6,\"place\":9,\"free\":true}," +
                "{\"Row\":7,\"place\":1,\"free\":true},{\"Row\":7,\"place\":2,\"free\":true}," +
                "{\"Row\":7,\"place\":3,\"free\":true},{\"Row\":7,\"place\":4,\"free\":true}," +
                "{\"Row\":7,\"place\":5,\"free\":true},{\"Row\":7,\"place\":6,\"free\":true}," +
                "{\"Row\":7,\"place\":7,\"free\":true},{\"Row\":7,\"place\":8,\"free\":true}," +
                "{\"Row\":7,\"place\":9,\"free\":true},{\"Row\":8,\"place\":1,\"free\":true}," +
                "{\"Row\":8,\"place\":2,\"free\":true},{\"Row\":8,\"place\":3,\"free\":true}," +
                "{\"Row\":8,\"place\":4,\"free\":true},{\"Row\":8,\"place\":5,\"free\":true}," +
                "{\"Row\":8,\"place\":6,\"free\":true},{\"Row\":8,\"place\":7,\"free\":false}," +
                "{\"Row\":8,\"place\":8,\"free\":true},{\"Row\":8,\"place\":9,\"free\":true}," +
                "{\"Row\":9,\"place\":1,\"free\":true},{\"Row\":9,\"place\":2,\"free\":true}," +
                "{\"Row\":9,\"place\":3,\"free\":true},{\"Row\":9,\"place\":4,\"free\":true}," +
                "{\"Row\":9,\"place\":5,\"free\":true},{\"Row\":9,\"place\":6,\"free\":true}," +
                "{\"Row\":9,\"place\":7,\"free\":true},{\"Row\":9,\"place\":8,\"free\":true}," +
                "{\"Row\":9,\"place\":9,\"free\":false}]}";
        System.out.println(json);
        given()
                .when()
                .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("aplication/json", ContentType.JSON)))
                .contentType("aplication/json")
                .body(json)
                .post("/cinema_id/{cinema_id}/Hall_id/{hall_id}/order", 2, 13)

                .then()
                .statusCode(200);
        given()
                .when()
                .get("/cinema_id/{cinema_id}/Hall_id/{hall_id}", 2, 13)
                .then()
                .statusCode(200)
                .body(equalTo("{\"ROW_ID\":13,\"HallName\":\"Hall13\",\"PlaceCount\":50,\"PlaceList\":{\"places\":[{\"Row\":1,\"place\":1,\"free\":true},{\"Row\":1,\"place\":2,\"free\":true},{\"Row\":1,\"place\":3,\"free\":true},{\"Row\":1,\"place\":4,\"free\":true},{\"Row\":1,\"place\":5,\"free\":true},{\"Row\":1,\"place\":6,\"free\":true},{\"Row\":1,\"place\":7,\"free\":true},{\"Row\":1,\"place\":8,\"free\":true},{\"Row\":1,\"place\":9,\"free\":true},{\"Row\":1,\"place\":10,\"free\":true},{\"Row\":2,\"place\":1,\"free\":true},{\"Row\":2,\"place\":2,\"free\":true},{\"Row\":2,\"place\":3,\"free\":true},{\"Row\":2,\"place\":4,\"free\":true},{\"Row\":2,\"place\":5,\"free\":true},{\"Row\":2,\"place\":6,\"free\":true},{\"Row\":2,\"place\":7,\"free\":true},{\"Row\":2,\"place\":8,\"free\":true},{\"Row\":2,\"place\":9,\"free\":true},{\"Row\":2,\"place\":10,\"free\":true},{\"Row\":3,\"place\":1,\"free\":true},{\"Row\":3,\"place\":2,\"free\":true},{\"Row\":3,\"place\":3,\"free\":true},{\"Row\":3,\"place\":4,\"free\":true},{\"Row\":3,\"place\":5,\"free\":true},{\"Row\":3,\"place\":6,\"free\":true},{\"Row\":3,\"place\":7,\"free\":true},{\"Row\":3,\"place\":8,\"free\":true},{\"Row\":3,\"place\":9,\"free\":true},{\"Row\":3,\"place\":10,\"free\":true},{\"Row\":4,\"place\":1,\"free\":true},{\"Row\":4,\"place\":2,\"free\":true},{\"Row\":4,\"place\":3,\"free\":true},{\"Row\":4,\"place\":4,\"free\":true},{\"Row\":4,\"place\":5,\"free\":true},{\"Row\":4,\"place\":6,\"free\":true},{\"Row\":4,\"place\":7,\"free\":true},{\"Row\":4,\"place\":8,\"free\":true},{\"Row\":4,\"place\":9,\"free\":true},{\"Row\":4,\"place\":10,\"free\":true},{\"Row\":5,\"place\":1,\"free\":true},{\"Row\":5,\"place\":2,\"free\":true},{\"Row\":5,\"place\":3,\"free\":true},{\"Row\":5,\"place\":4,\"free\":true},{\"Row\":5,\"place\":5,\"free\":true},{\"Row\":5,\"place\":6,\"free\":true},{\"Row\":5,\"place\":7,\"free\":true},{\"Row\":5,\"place\":8,\"free\":true},{\"Row\":5,\"place\":9,\"free\":true},{\"Row\":5,\"place\":10,\"free\":true}]}}"));

    }

    @Test
    public void updatePlaceStatus2() {

        String json = "{\"places\":[{\"Row\":1,\"place\":1,\"free\":true}," +
                "{\"Row\":1,\"place\":2,\"free\":true},{\"Row\":1,\"place\":3,\"free\":true}," +
                "{\"Row\":1,\"place\":4,\"free\":true},{\"Row\":1,\"place\":5,\"free\":true}," +
                "{\"Row\":1,\"place\":6,\"free\":true},{\"Row\":1,\"place\":7,\"free\":true}," +
                "{\"Row\":1,\"place\":8,\"free\":true},{\"Row\":2,\"place\":1,\"free\":true}," +
                "{\"Row\":2,\"place\":2,\"free\":true},{\"Row\":2,\"place\":3,\"free\":true}," +
                "{\"Row\":2,\"place\":4,\"free\":false},{\"Row\":2,\"place\":5,\"free\":true}," +
                "{\"Row\":2,\"place\":6,\"free\":true},{\"Row\":2,\"place\":7,\"free\":true}," +
                "{\"Row\":2,\"place\":8,\"free\":true},{\"Row\":2,\"place\":9,\"free\":false}," +
                "{\"Row\":3,\"place\":1,\"free\":true},{\"Row\":3,\"place\":2,\"free\":true}," +
                "{\"Row\":3,\"place\":3,\"free\":true},{\"Row\":3,\"place\":4,\"free\":true}," +
                "{\"Row\":3,\"place\":5,\"free\":true},{\"Row\":3,\"place\":6,\"free\":true}," +
                "{\"Row\":3,\"place\":7,\"free\":true},{\"Row\":3,\"place\":8,\"free\":true}," +
                "{\"Row\":3,\"place\":9,\"free\":true},{\"Row\":4,\"place\":1,\"free\":true}," +
                "{\"Row\":4,\"place\":2,\"free\":true},{\"Row\":4,\"place\":3,\"free\":true}," +
                "{\"Row\":4,\"place\":4,\"free\":true},{\"Row\":4,\"place\":5,\"free\":true}," +
                "{\"Row\":4,\"place\":6,\"free\":true},{\"Row\":4,\"place\":7,\"free\":true}," +
                "{\"Row\":4,\"place\":8,\"free\":true},{\"Row\":4,\"place\":9,\"free\":true}," +
                "{\"Row\":5,\"place\":1,\"free\":true},{\"Row\":5,\"place\":2,\"free\":true}," +
                "{\"Row\":5,\"place\":3,\"free\":true},{\"Row\":5,\"place\":4,\"free\":true}," +
                "{\"Row\":5,\"place\":5,\"free\":true},{\"Row\":5,\"place\":6,\"free\":true}," +
                "{\"Row\":5,\"place\":7,\"free\":true},{\"Row\":5,\"place\":8,\"free\":true}," +
                "{\"Row\":5,\"place\":9,\"free\":true},{\"Row\":6,\"place\":1,\"free\":true}," +
                "{\"Row\":6,\"place\":2,\"free\":true},{\"Row\":6,\"place\":3,\"free\":true}," +
                "{\"Row\":6,\"place\":4,\"free\":true},{\"Row\":6,\"place\":5,\"free\":true}," +
                "{\"Row\":6,\"place\":6,\"free\":true},{\"Row\":6,\"place\":7,\"free\":true}," +
                "{\"Row\":6,\"place\":8,\"free\":true},{\"Row\":6,\"place\":9,\"free\":true}," +
                "{\"Row\":7,\"place\":1,\"free\":true},{\"Row\":7,\"place\":2,\"free\":true}," +
                "{\"Row\":7,\"place\":3,\"free\":true},{\"Row\":7,\"place\":4,\"free\":true}," +
                "{\"Row\":7,\"place\":5,\"free\":true},{\"Row\":7,\"place\":6,\"free\":true}," +
                "{\"Row\":7,\"place\":7,\"free\":true},{\"Row\":7,\"place\":8,\"free\":true}," +
                "{\"Row\":7,\"place\":9,\"free\":true},{\"Row\":8,\"place\":1,\"free\":true}," +
                "{\"Row\":8,\"place\":2,\"free\":true},{\"Row\":8,\"place\":3,\"free\":true}," +
                "{\"Row\":8,\"place\":4,\"free\":true},{\"Row\":8,\"place\":5,\"free\":true}," +
                "{\"Row\":8,\"place\":6,\"free\":true},{\"Row\":8,\"place\":7,\"free\":false}," +
                "{\"Row\":8,\"place\":8,\"free\":true},{\"Row\":8,\"place\":9,\"free\":true}," +
                "{\"Row\":9,\"place\":1,\"free\":true},{\"Row\":9,\"place\":2,\"free\":true}," +
                "{\"Row\":9,\"place\":3,\"free\":true},{\"Row\":9,\"place\":4,\"free\":true}," +
                "{\"Row\":9,\"place\":5,\"free\":true},{\"Row\":9,\"place\":6,\"free\":true}," +
                "{\"Row\":9,\"place\":7,\"free\":true},{\"Row\":9,\"place\":8,\"free\":true}," +
                "{\"Row\":9,\"place\":9,\"free\":false}]}";
        System.out.println(json);
        given()
                .when()
                .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("aplication/json", ContentType.JSON)))
                .contentType("aplication/json")
                .body(json)
                .post("/cinema_id/{cinema_id}/Hall_id/{hall_id}/order", 3, 5)

                .then()
                .statusCode(200);
        given()
                .when()
                .get("/cinema_id/{cinema_id}/Hall_id/{hall_id}", 3, 5)
                .then()
                .statusCode(200)
                .body(equalTo("{\"ROW_ID\":5,\"HallName\":\"Hall5\",\"PlaceCount\":50,\"PlaceList\":{\"places\":[{\"Row\":1,\"place\":1,\"free\":true},{\"Row\":1,\"place\":2,\"free\":true},{\"Row\":1,\"place\":3,\"free\":true},{\"Row\":1,\"place\":4,\"free\":true},{\"Row\":1,\"place\":5,\"free\":true},{\"Row\":1,\"place\":6,\"free\":true},{\"Row\":1,\"place\":7,\"free\":true},{\"Row\":1,\"place\":8,\"free\":true},{\"Row\":1,\"place\":9,\"free\":true},{\"Row\":1,\"place\":10,\"free\":true},{\"Row\":2,\"place\":1,\"free\":true},{\"Row\":2,\"place\":2,\"free\":true},{\"Row\":2,\"place\":3,\"free\":true},{\"Row\":2,\"place\":4,\"free\":true},{\"Row\":2,\"place\":5,\"free\":true},{\"Row\":2,\"place\":6,\"free\":true},{\"Row\":2,\"place\":7,\"free\":true},{\"Row\":2,\"place\":8,\"free\":true},{\"Row\":2,\"place\":9,\"free\":true},{\"Row\":2,\"place\":10,\"free\":true},{\"Row\":3,\"place\":1,\"free\":true},{\"Row\":3,\"place\":2,\"free\":true},{\"Row\":3,\"place\":3,\"free\":true},{\"Row\":3,\"place\":4,\"free\":true},{\"Row\":3,\"place\":5,\"free\":true},{\"Row\":3,\"place\":6,\"free\":true},{\"Row\":3,\"place\":7,\"free\":true},{\"Row\":3,\"place\":8,\"free\":true},{\"Row\":3,\"place\":9,\"free\":true},{\"Row\":3,\"place\":10,\"free\":true},{\"Row\":4,\"place\":1,\"free\":true},{\"Row\":4,\"place\":2,\"free\":true},{\"Row\":4,\"place\":3,\"free\":true},{\"Row\":4,\"place\":4,\"free\":true},{\"Row\":4,\"place\":5,\"free\":true},{\"Row\":4,\"place\":6,\"free\":true},{\"Row\":4,\"place\":7,\"free\":true},{\"Row\":4,\"place\":8,\"free\":true},{\"Row\":4,\"place\":9,\"free\":true},{\"Row\":4,\"place\":10,\"free\":true},{\"Row\":5,\"place\":1,\"free\":true},{\"Row\":5,\"place\":2,\"free\":true},{\"Row\":5,\"place\":3,\"free\":true},{\"Row\":5,\"place\":4,\"free\":true},{\"Row\":5,\"place\":5,\"free\":true},{\"Row\":5,\"place\":6,\"free\":true},{\"Row\":5,\"place\":7,\"free\":true},{\"Row\":5,\"place\":8,\"free\":true},{\"Row\":5,\"place\":9,\"free\":true},{\"Row\":5,\"place\":10,\"free\":true}]}}"));

    }

    @Test
    public void deleteHall() {
        given()
                .when()
                .delete("/cinema_id/{cinema_id}/Hall_id/{hall_id}", 1, 162)
                .then()
                .statusCode(200);


    }

    @Test
    public void deleteCinema() {

        given()
                .when()
                .delete("/cinema_id/{cinema_id}", 6)
                .then()
                .statusCode(200);

    }

}
