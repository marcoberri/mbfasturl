package it.marcoberri.fasturl;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class UrlResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/C:/dev/git-bash/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }

}