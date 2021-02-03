# Swagger Pet Store API tests

# Example:
[![CircleCI](https://circleci.com/gh/joaogfarias/backend.svg?style=shield)](https://circleci.com/gh/JoaoGFarias/backend)

## Run tests:

Against [Swagger Pet Store instance](http://petstore.swagger.io/#):
```make run_external```

Against the instance running locally via Docker (or exposed on port 8080), as instructed 
in the [Swagger Pet Store repository](https://github.com/swagger-api/swagger-petstore#to-run-via-docker):
```make run_local```

## Things you will find:

* API client object creation using Dependency Injection
* User flows defined using Gherkin
* User flows simulated using Cucumber
* Continuous building using CircleCI (see badge link above)
* HTML report (on _target/cucumber-reports_ folder) when tests are executed locally. CircleCI saves the reports as well;
example [here](https://app.circleci.com/pipelines/github/JoaoGFarias/backend/12/workflows/80a48aa7-5313-47a4-a2fd-aa6d7c49dfdc/jobs/12/artifacts)