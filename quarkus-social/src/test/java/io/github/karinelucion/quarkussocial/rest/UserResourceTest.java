package io.github.karinelucion.quarkussocial.rest;

import io.github.karinelucion.quarkussocial.rest.dto.CreateUserRequest;
import io.github.karinelucion.quarkussocial.rest.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {
    @TestHTTPResource("/users")
    URL apiURL;

    @Test
    @DisplayName("should create an user successfully")
    @Order(1)
    public void createUserTest(){
        var user = new CreateUserRequest();
        user.setName("Fulano");
        user.setAge(30);

        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

    @Test
    @DisplayName("Should return error when json is not valid")
    @Order(2)
    public void createUserValidationErrorTest(){
        var user = new CreateUserRequest();
        user.setAge(null);
        user.setName(null);

        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
        assertEquals("Validation Error", response.jsonPath().getString("message"));

        List<Map<String,String>> erros = response.jsonPath().getList("erros");

        assertNotNull(erros.get(0).get("message"));
        assertNotNull(erros.get(1).get("message"));
//        assertEquals("Age is Required", erros.get(0).get("message"));
//        assertEquals("Name is Required", erros.get(1).get("message"));
    }

    @Test
    @DisplayName("should list all users")
    @Order(3)
    public void listAllUsersTest(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .post(apiURL)
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }
}