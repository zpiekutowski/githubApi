package com.zbiir.github_api.controler;

import com.zbiir.github_api.model.GitHubRepo;
import com.zbiir.github_api.model.ResultRepo;
import com.zbiir.github_api.service.GitHubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controler {

    @Autowired
    private GitHubApiService gitHubApiService;

    @GetMapping ("/repos/{username}")
    public List<ResultRepo> getRepositoriesResult(@PathVariable String username){
    return gitHubApiService.getRepositories(username);
}





}
