package com.atipera.demo.service;

import com.atipera.demo.exception.UserNotFoundException;
import com.atipera.demo.model.GitHubBranch;
import com.atipera.demo.model.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private static final String GITHUB_API_URL = "https://api.github.com";

    @Autowired
    private RestTemplate restTemplate;
    
    public List<GitHubRepository> getUserRepositories(String username) {
        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        try {
            GitHubRepository[] repositories = restTemplate.getForObject(url, GitHubRepository[].class);
            return Arrays.stream(repositories)
                             .filter(repo -> !repo.isFork())  
                             .collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User not found: " + username);
            }
            throw e;
        }
    }

    public List<GitHubBranch> getRepositoryBranches(String owner, String repo) {
        String url = GITHUB_API_URL + "/repos/" + owner + "/" + repo + "/branches";
        GitHubBranch[] branches = restTemplate.getForObject(url, GitHubBranch[].class);
        return Arrays.asList(branches);
    }

   
}