run_external:
	mvn clean verify -Dtest=CucumberTestsRunner  -Dtarget=external -DbaseUrl=https://petstore.swagger.io -DapiUrl=/v2

run_local:
	mvn clean verify -Dtest=CucumberTestsRunner -Dtarget=local -DapiUrl=/api/v3 -Dport=8080