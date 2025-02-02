package com.pp.cyan.controller;

import com.pp.cyan.controller.dto.CheckDomainRequest;
import com.pp.cyan.controller.dto.CheckDomainResponse;
import com.pp.cyan.controller.dto.StatisticResponse;
import com.pp.cyan.test.service.TestBlockingStatisticService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class DomainCheckerControllerTest {

  @Autowired
  private TestBlockingStatisticService testBlockingStatisticService;

  @LocalServerPort
  protected int port;

  @BeforeEach
  void setUp() {
    testBlockingStatisticService.deleteAll();
  }

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

  @Test
  void shouldReturnStatistics() {
    testBlockingStatisticService.saveBlockingStatistics("PORNOGRAPHY", new BigInteger("21"));
    testBlockingStatisticService.saveBlockingStatistics("SOCIAL_MEDIA", new BigInteger("22"));
    testBlockingStatisticService.saveBlockingStatistics("DRUGS", new BigInteger("28"));
    testBlockingStatisticService.saveBlockingStatistics("MALWARE_AND_PHISHING", new BigInteger("20"));

    StatisticResponse statisticResponse = given()
        .port(port)
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .when()
        .get("/cyanapp/api/domain-checker/statistics")
        .then()
        .statusCode(200)
        .extract()
        .as(StatisticResponse.class);

    assertThat(statisticResponse).isNotNull();
    assertThat(statisticResponse.getNumberOfMalwareAndPhishingBlocked()).isEqualByComparingTo(new BigInteger("20"));
    assertThat(statisticResponse.getNumberOfOthersBlocked()).isEqualByComparingTo(new BigInteger("71"));
  }
}