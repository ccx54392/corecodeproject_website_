package integrationTest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestTemplate {

    public static Response getRequest(String url){
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(url)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response postRequest(String url, String body){
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(body)
                .post(url)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response deleteRequest(String url){
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .delete(url)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();
        return response;
    }
}
