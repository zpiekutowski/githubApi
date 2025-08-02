package com.zbiir.github_api.model;

import lombok.Data;

@Data
public class BranchInfo {
    private String name;
    private Commit commit;

    @Data
    public static class Commit{
        String sha;
    }

}
