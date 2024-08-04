
# GitHub API Consumer

## Opis

Aplikacja Spring Boot do pobierania informacji o repozytoriach, gałęziach i commitach z GitHub API. Umożliwia pobranie danych o repozytoriach, gałęziach oraz commitach dla podanego użytkownika GitHub.

## Wymagania

- Java 21
- Spring Boot wersja 3.1.0
- Maven wersja 3.8.4 lub nowsza

## Konfiguracja

Podstawowe ustawienia aplikacji można skonfigurować w pliku `application.properties`
```bash
# URL GitHub API
github.api.url=https://api.github.com
```

## Instalacja

1. **Klonowanie Repozytorium**

   ```bash
   git clone https://github.com/username/repository.git
2. **Przejście do katalogu projektu**

   ```bash
   cd repository
   
3. **Buduwowanie za pomocą Mavena**
    ```bash
   mvn clean install
   
## Uruchamianie Aplikacji 
W celu uruchomienia aplikacji należy użyć poniższego polecania:
    
   ```bash
  mvn spring-boot:run
```

## Endpointy

1. **Pobieranie Repozytoriów**
   * URL: `/api/github/repositories/{username}`
   * Metoda: GET
   * Opis: Pobiera listę repozytoriów dla podanego użytkownika GitHub
   * Parametry:
      * username (wymagany): Nazwa użytkownika GitHub
   * Przykład Żądania:
   ```bash
   curl -X GET "http://localhost:8080/api/github/repositories/mitularafal"
   ```
   * Przykład Odpowiedzi:
   
   ```json
   [
    {
   "id": 12345678,
   "name": "example-repo",
   "full_name": "mitularafal/example-repo",
   "private": false,
   "html_url": "https://github.com/mitularafal/example-repo"
    }
   ]
   ```
2. **Pobieranie Gałęzi Repozytoriów**
   * URL: `/api/github/repositories/{username}/branches/{repoName}`
   * Metoda: GET
   * Opis: Pobiera listę repozytoriów dla danego repozytorium i użytkownika GitHub
   * Parametry:
      * username (wymagany): Nazwa użytkownika GitHub
      * reponame (wymagany): Nazwa repozytorium

   * Przykład Żądania:
   ```bash
   curl -X GET "http://localhost:8080/api/github/repositories/mitularafal/example-repo/branches"
   ```
   * Przykład Odpowiedzi
   ```json
   [
     {
   "name": "main",
    "commit": {
      "sha": "abcd1234",
      "url": "https://github.com/mitularafal/example-repo/commits/main"
      }
     }
   ]
   ```
3. **Pobieranie Commitów Gałęzi**
   * URL: `/api/github/repositories/{username}/branches/{repoName}/commits/{branchName}`
   * Metoda: GET
   * Opis: Pobiera listę commitów dla podanej gałęzi w repozytorium i wybranego użytkownika GitHub
   * Parametry:
      * username (wymagany): Nazwa użytkownika GitHub
      * reponame (wymagany): Nazwa repozytorium
      * branchName (wymagany): Nazwa gałęzi

* Przykład Żądania:
  ```bash
   curl -X GET "http://localhost:8080/api/github/repositories/mitularafal/example-repo/branches/main/commits"
  ```
* Przykład Odpowiedzi
  ```json
     [
       {
         "sha": "abcd1234",
         "commit": {
           "message": "Initial commit"
         }
       }
     ]
  ```
   
   




