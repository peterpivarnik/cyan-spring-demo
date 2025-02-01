package com.pp.cyan.ad.controller;

import com.pp.cyan.ad.controller.dto.CheckDomainRequest;
import com.pp.cyan.ad.controller.dto.CheckDomainResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class DomainCheckerControllerTest {

  @LocalServerPort
  protected int port;

  @Test
  void shouldReturnSuccessAndUnknownResponse() {
    CheckDomainRequest request = new CheckDomainRequest();
    request.setUrl("https://www.google.com");

    CheckDomainResponse checkDomainResponse = given()
        .port(port)
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .post("/cyanapp/api/domain-checker")
        .then()
        .statusCode(200)
        .extract()
        .as(CheckDomainResponse.class);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo("www.google.com");
    assertThat(checkDomainResponse.getCategory()).isEqualTo("UNKNOWN");
    assertThat(checkDomainResponse.getBlocked()).isFalse();
  }

  @Test
  void shouldReturnSuccessAndKnownResponse() {
    CheckDomainRequest request = new CheckDomainRequest();
    request.setUrl("https://communities.badinternetdomain.com/cyandigitalsecurity");

    CheckDomainResponse checkDomainResponse = given()
        .port(port)
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .post("/cyanapp/api/domain-checker")
        .then()
        .statusCode(200)
        .extract()
        .as(CheckDomainResponse.class);

    assertThat(checkDomainResponse).isNotNull();
    assertThat(checkDomainResponse.getDomain()).isEqualTo("communities.badinternetdomain.com");
    assertThat(checkDomainResponse.getCategory()).isEqualTo("SOCIAL_MEDIA");
    assertThat(checkDomainResponse.getBlocked()).isTrue();
  }
}