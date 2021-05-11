# Alfresco SDK 5 ReST API Java Wrapper Samples

These samples are associated with the official documentation of the [Alfresco SDK 5 ReST API Java Wrapper](https://docs.alfresco.com/content-services/latest/develop/oop-ext-points/rest-api-java-wrapper/).  

To run these samples do something like this:

- Look in the `src/main/java/org/alfresco/tutorial/restapi/RestApiApplication.java` file and choose what sample to run, for example `get-person-metadata`
- Then check the number of argument this command takes, in this case `username`
- Make sure you have built the project: `% mvn clean package -Dlicense.skip=true`
- Run the specific sample: `% java -jar target/rest-api-0.0.1-SNAPSHOT.jar get-person-metadata abeecher`
