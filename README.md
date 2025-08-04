# GitHub Repository Info Service

A Spring Boot application that exposes a REST API at  
`http://localhost:8080/api/repos/{username}`  
to retrieve **non-fork GitHub repositories** and their **branches with the last commit SHA** for a given user.

---

## 🚀 Features

- Connects to the public GitHub API
- Filters out forked repositories
- Retrieves branches for each repository
- Fetches the latest commit SHA per branch
- Exposes data through a REST endpoint
- Includes integration tests using WireMock

---

## 🧪 Technologies Used

- Java 17
- Spring Boot 3.x
- RestTemplate
- WireMock
- JUnit 5
- Lombok
- Gradle

---

## 📦 Running the Application

### ✅ Prerequisites

- Java 17 installed
- Internet access (to call GitHub API)
- GitHub username to test

#### 🔹 On **Linux/macOS**

```bash
./gradlew bootRun
```

#### 🔹 On **Windows (CMD)**

```cmd
gradlew.bat bootRun
```

#### 🔹 On **Windows (PowerShell)**

```powershell
.\gradlew.bat bootRun
```

#### 🔹 Or run from IDE

Launch the main class:

```
com.zbiir.github_api.GithubApiApplication
```

After starting, the application is available at:  
📍 `http://localhost:8080`

---

## 🧠 Notes

- The API only returns **non-fork repositories**
- Each repository includes a list of its branches and the **last commit SHA**
- The application throws a custom exception for non-existing GitHub users
- All HTTP requests are made using `RestTemplate`
- No real GitHub calls are made during tests

---