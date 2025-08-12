package com.zbiir.github_api;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.zbiir.github_api.service.GitHubApiService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(properties = {"github.api.url=http://localhost:9561"},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9561)
@AutoConfigureWebTestClient
class GithubApiApplicationTests   {

    @Autowired
    private GitHubApiService gitHubApiService;

	@Autowired
	private WebTestClient webTestClient;

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

//    @Test
//    void contextLoads() {
//
//        List<ResultRepo> repos = gitHubApiService.getRepositories("user1");
//        assertEquals("user1", repos.get(0).getOwnerLogin());
//		assertTrue(List.of("repo1", "repo2").contains(repos.get(0).getRepositoryName()));
//		assertEquals(2,repos.size());
//		assertTrue(List.of("branch1", "branch2").contains(repos.get(0).getBranches().get(0).getName()));
//
//	}
//
//	@Test
//	void shouldReturnRepoDataFromWireMock() throws Exception {
//
//		mockMvc.perform(get("/api/repos/user1"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$",hasSize(2)))
//				.andExpect(jsonPath("$[0].repositoryName",anyOf(is("repo1"),is("repo2"))))
//				.andExpect(jsonPath("$[1].ownerLogin").value("user1"))
//				.andDo(print());
//	}
@Test
void shouldReturnReposJsonMatches() throws JSONException{
	String expectedJson = """
    [
      {
        "repositoryName": "repo1",
        "ownerLogin": "user1",
        "branches": [
          {
            "name": "branch1",
            "lastCommitSHA": "123456"
          }
        ]
      },
      {
        "repositoryName": "repo2",
        "ownerLogin": "user1",
        "branches": [
          {
            "name": "branch2",
            "lastCommitSHA": "7891078"
          }
        ]
      }
    ]
    """;

	String responseBody = webTestClient.get()
			.uri("/api/repos/user1")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class)
			.returnResult()
			.getResponseBody();

	JSONAssert.assertEquals(expectedJson, responseBody, JSONCompareMode.LENIENT);
}




}
