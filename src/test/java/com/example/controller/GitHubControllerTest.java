package com.example.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import com.example.model.Repository;
import com.example.service.GitHubService;

@SpringBootTest
public class GitHubControllerTest {

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private GitHubController gitHubController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRepositories() {
        String username = "testuser";

        // Stworzenie przykładowych repozytoriów
        Repository repo1 = new Repository();
        repo1.setName("repo1");

        Repository repo2 = new Repository();
        repo2.setName("repo2");

        List<Repository> mockRepositories = Arrays.asList(repo1, repo2);

        // Mockowanie GitHubService
        when(gitHubService.getRepositories(username)).thenReturn(mockRepositories);

        // Wywołanie kontrolera
        ResponseEntity<?> responseEntity = gitHubController.getRepositories(username);

        // Sprawdzenie statusu odpowiedzi
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Weryfikacja odpowiedzi
        List<Repository> repositories = (List<Repository>) responseEntity.getBody();
        assertNotNull(repositories);
        assertEquals(2, repositories.size());
        assertEquals("repo1", repositories.get(0).getName());
        assertEquals("repo2", repositories.get(1).getName());
    }
}

