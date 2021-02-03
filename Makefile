run_version2:
	mvn clean verify -Dtest=CucumberTestsRunner -DbaseUrl=https://petstore.swagger.io -Dversion=v2

run_version3:
	mvn clean verify -Dtest=CucumberTestsRunner -DbaseUrl=localhost -Dversion=v3