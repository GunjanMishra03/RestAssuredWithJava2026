# REST Assured API Automation Framework

A Java 17+ Maven starter for maintainable REST API tests. Reusable API clients, configuration, request setup, and reporting live in `src/main/java`; TestNG tests, listeners, and test-data helpers live in `src/test/java`. Keeping the test runner and test-only classes in the test source set means they are not shipped with the framework artifact.

## Architecture

- `config`: loads the selected environment's properties; `api.baseUrl` can be overridden with the JVM property of the same name or `REST_BASE_URL`.
- `client`: API Object Pattern clients, a reusable request specification factory, and a request/response logging filter.
- `constants`: shared endpoint paths.
- `models`: Jackson response models.
- `reporting`: thread-local Extent test logging and report lifecycle.
- `src/main/resources`: environment properties, JSON schema, and Log4j2 configuration.
- `src/test/java`: TestNG listeners, JSON data reader/provider, and API tests.
- `src/test/resources/testdata`: external JSON datasets.

The sample environment property files point to JSONPlaceholder because it is the one API used by this demo. In a real deployment, provide the appropriate environment-specific base URL rather than pointing production tests at the public demo service.

## Run

```shell
mvn clean test
mvn clean test -Pdev
mvn clean test -Pqa
mvn clean test -Puat
mvn clean test -Pprod
```

Override the profile's base URL when needed:

```shell
mvn test -Pqa -Dapi.baseUrl=https://your-api.example
```

The response-time threshold is configured per environment in `src/main/resources/config/*.properties`. TestNG XML results are written to `target/surefire-reports/`, the Extent HTML report to `target/extent-reports/extent-report.html`, and Log4j2 output to `target/logs/api-tests.log`.

## Sample coverage

`UserApiTest` reads user IDs and expected values from `testdata/users.json` through a TestNG `DataProvider`. Each case checks status, JSON content type, response-time threshold, JSON schema, and the expected user fields. The TestNG listener captures request and response method/URI, status, duration, and payload in both Log4j2 and Extent Reports.

## Extending the framework

Create one API client per resource/domain and centralize endpoint paths and common request behavior there. Add typed models for request and response contracts, keep test inputs in separate JSON datasets, and factor repeated assertions into focused domain-specific assertion helpers. For larger suites, add explicit TestNG suite groups, controlled parallelism, CI environment secrets, contract/version-specific schemas, and service virtualization for deterministic non-production tests. Do not store credentials in source-controlled property files.

Jenkins and GitHub Actions examples are provided in `Jenkinsfile` and `.github/workflows/api-tests.yml`.