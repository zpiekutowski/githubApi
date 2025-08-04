package com.zbiir.github_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResultRepo {
    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;

    @Data
    @Builder
    public static class Branch{
        private String name;
        private String lastCommitSHA;
    }

}
