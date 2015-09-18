# wildfly10-infinispan-test
A quick start test service for wildfly container with infinspan and ebj configuration

## Install

### Wildfly container
For lanching wildfly enter into bin directory

	cd $WILDFLY_HOME/bin
	
execute the standalone batch

	linux:  ./standalone.sh --debug
	window: ./standalone.bat --debug

### WAR packaging
Enter into project root dir

	cd $PROJECT_DIR/

Generate package 

	wildfly 10: mvn -Pw10 clean package
	wildfly 9:  mvn -Pw9 clean package

Deploy into container

	mvn wildfly:deploy

Test application

	curl http://localhost:8080/hcloud/test
	
