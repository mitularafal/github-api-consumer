package com.example.service;

import com.example.model.Branch;
import com.example.model.Commit;
import com.example.model.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {
    @Value("${github.api.url:https://api.github.com}")
    private String githubApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Repository> getRepositories(String username) {
        try{
            String repoUrl = String.format("%s/users/%s/repos", githubApiUrl, username);
            Repository[] repositories = restTemplate.getForObject(repoUrl, Repository[].class);

            if (repositories == null) {
                return List.of(); // Zwracanie pustej listy
            }

            // Filtrowanie repozytoriów by uwzględnić tylko te, które nie są forkami
            List<Repository> filteredRepositories = Arrays.stream(repositories)
                    .filter(repo -> !repo.isFork())
                    .collect(Collectors.toList());

            //Dla każdego repozytorium pobieramy listę gałęzi i informacje o ostatnich commitach
            filteredRepositories.forEach(repo -> {
                String branchesUrl = String.format("%s/repos/%s/%s/branches", githubApiUrl, username, repo.getName());
                Branch[] branches = restTemplate.getForObject(branchesUrl, Branch[].class);

                if (branches != null) {
                    for (Branch branch : branches) {
                        String commitUrl = String.format("%s/repos/%s/%s/commits/%s", githubApiUrl, username, repo.getName(), branch.getName());
                        Commit commit = restTemplate.getForObject(commitUrl, Commit.class);
                        branch.setCommit(commit);
                    }
                    repo.setBranches(Arrays.asList(branches));
                }
            });

            return filteredRepositories;

        } catch (HttpClientErrorException.NotFound e){
            throw new RuntimeException("User not found " + username);
        } catch (Exception e){
            throw new RuntimeException("An error occurred while fetching repositories for user: " + username,e);
        }
    }
}
