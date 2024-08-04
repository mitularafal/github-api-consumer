package com.example.controller;

import com.example.model.Repository;
import com.example.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getRepositories(@PathVariable String username){
        try {
            List<Repository> repositories = gitHubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (RuntimeException e){
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
