package com.zbiir.github_api;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.zbiir.github_api.model.ResultRepo;
import com.zbiir.github_api.service.GitHubApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = {"github.api.url=http://localhost:9561"})
@AutoConfigureWireMock(port = 9561)
class GithubApiApplicationTests {

    @Autowired
    private GitHubApiService gitHubApiService;


    @BeforeEach
    void setup() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/users/user1/repos"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
								  {
								    "name": "repo1",
								    "fork": false,
								    "owner": {
								      "login": "user1"
								    }
								  },
								  {
								    "name": "repo2",
								    "fork": false,
								    "owner": {
								      "login": "user1"
								    }
								  }
								]
                                               """)
                        .withStatus(200)));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/user1/repo1/branches"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                               [
                                                 { "name": "branch1",
                                                  "commit": {"sha": "123456"}
                                                  }
                                ]
                                               """)
                        .withStatus(200)));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/user1/repo2/branches"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                               [
                                                 { "name": "branch2",
                                                  "commit": {"sha": "7891078"}
                                                  }
                                ]
                                               """)
                        .withStatus(200)));

    }

    @Test
    void contextLoads() {

        List<ResultRepo> repos = gitHubApiService.getRepositories("user1");
        assertEquals("user1", repos.get(0).getOwnerLogin());
		assertTrue(List.of("repo1", "repo2").contains(repos.get(0).getRepositoryName()));
		assertEquals(2,repos.size());
		assertTrue(List.of("branch1", "branch2").contains(repos.get(0).getBranches().get(0).getName()));


	}

}
