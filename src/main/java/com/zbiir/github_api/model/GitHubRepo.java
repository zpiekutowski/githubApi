package com.zbiir.github_api.model;

import lombok.Data;

@Data
public class GitHubRepo {
    private String name;
    private boolean fork;
    private Owner owner;

    @Data
    public static class Owner{
        String login;
    }


}
