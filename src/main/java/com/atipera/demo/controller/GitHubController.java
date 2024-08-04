package com.atipera.demo.controller;

import com.atipera.demo.model.GitHubBranch;
import com.atipera.demo.model.GitHubRepository;
import com.atipera.demo.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/user/{username}/repos")
    public List<Map<String, Object>> getUserRepositoriesWithBranches(@PathVariable String username) {
        List<GitHubRepository> repositories = gitHubService.getUserRepositories(username);
        List<Map<String, Object>> response = new ArrayList<>();

        if (repositories.isEmpty()) {
            Map<String, Object> non_existing = new HashMap<>();
            non_existing.put("status", 404);
            non_existing.put("message", "User not found: " + username);
            response.add(non_existing);
        }

        for (GitHubRepository repository : repositories) {
            Map<String, Object> repoInfo = new HashMap<>();
            repoInfo.put("repositoryName", repository.getName());
            repoInfo.put("ownerLogin", repository.getOwner().getLogin());

            List<GitHubBranch> branches = gitHubService.getRepositoryBranches(repository.getOwner().getLogin(), repository.getName());
            List<Map<String, String>> branchInfoList = new ArrayList<>();

            for (GitHubBranch branch : branches) {
                Map<String, String> branchInfo = new HashMap<>();
                branchInfo.put("branchName", branch.getName());
                branchInfo.put("lastCommitSha", branch.getCommit().getSha());
                branchInfoList.add(branchInfo);
            }

            repoInfo.put("branches", branchInfoList);
            response.add(repoInfo);
        }

        return response;
    }
}
