# GitHub Repository and Branch Information API

This Spring Boot application provides an API to fetch GitHub repositories and their branches for a given user, excluding forked repositories. It also includes the branch names and the last commit SHA for each branch.

## Features

Fetch repositories for a given GitHub user, excluding forks.
Retrieve branch names and the last commit SHA for each branch in the repositories.
Handle rate limiting and user-not-found scenarios gracefully.

## Prerequisites

Java 21 or later
Maven
GitHub Personal Access Token (for authenticated requests to increase rate limits)

## Installation

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

```bash
pip install foobar

```

## Configuration

In the application.properties file in src/main/resources replace "your_personal_access_token" with your actual GitHub Personal Access Token.

## Usage

```bash
mvn spring-boot:run
```

## API Endpoints

Get User Repositories

    URL: /github/user/{username}/repos
    Method: GET
    Description: Fetches repositories for the specified GitHub user, excluding forked repositories. Includes branch names and last commit SHAs.
    Response:
```json
[
    {
        "repositoryName": "repo-name",
        "ownerLogin": "owner-login",
        "branches": [
            {
                "branchName": "branch-name",
                "lastCommitSha": "commit-sha"
            }
        ]
    }
]

```

## Error Handling

User Not Found

    Response: 404 Not Found
    Example:
```json
{
    "status": 404,
    "message": "User not found: username"
}

```

## Rate Limit Exceeded
Response: 403 Forbidden
Example:
```json
{
    "status": 403,
    "message": "API rate limit exceeded. Please try again later or authenticate for a higher rate limit.",
    "documentation_url": "https://docs.github.com/rest/overview/resources-in-the-rest-api#rate-limiting"
}
```
## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.s

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
