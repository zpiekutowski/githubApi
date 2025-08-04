package com.zbiir.github_api.service;

import com.zbiir.github_api.advice.UserNotExistException;
import com.zbiir.github_api.model.BranchInfo;
import com.zbiir.github_api.model.GitHubRepo;
import com.zbiir.github_api.model.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.api.url}")
    private String apiUrl;


    public List<ResultRepo> getRepositories(String username) {

        String reposUrl = apiUrl + "/users/{username}/repos";
        String branchesUrl = apiUrl + "/repos/{username}/{repos}/branches";

        GitHubRepo[] gitHubRepos;
        try {
            gitHubRepos = restTemplate.getForObject(reposUrl, GitHubRepo[].class, username);

            return Arrays.stream(gitHubRepos)
                    .filter(r -> !r.isFork())
                    .map(r -> {
                        return ResultRepo.builder()
                                .repositoryName(r.getName())
                                .ownerLogin(r.getOwner().getLogin())
                                .branches(Arrays.stream(restTemplate.getForObject(branchesUrl, BranchInfo[].class, username, r.getName()))
                                        .map(b -> {
                                            return ResultRepo.Branch.builder()
                                                    .name(b.getName())
                                                    .lastCommitSHA(b.getCommit().getSha())
                                                    .build();
                                        })
                                        .collect(Collectors.toList()))
                                .build();
                    })
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException ex) {
            throw new UserNotExistException("User Not Found");
        } catch (Exception ex) {
            throw new UserNotExistException(ex.getMessage());
        }
    }
}
