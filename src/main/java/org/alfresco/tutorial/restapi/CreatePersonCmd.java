package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.PeopleApi;
import org.alfresco.core.model.PersonBodyCreate;
import org.alfresco.core.model.PersonEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreatePersonCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(CreatePersonCmd.class);

    @Autowired
    PeopleApi peopleApi;

    public void execute(String username, String pwd, String firstname, String lastname, String email) throws IOException {
        List<String> fields = null;

        PersonBodyCreate personBodyCreate = new PersonBodyCreate();
        personBodyCreate.setId(username);
        personBodyCreate.setPassword(pwd);
        personBodyCreate.setFirstName(firstname);
        personBodyCreate.setLastName(lastname);
        personBodyCreate.setEmail(email);
        PersonEntry person = peopleApi.createPerson(personBodyCreate, fields).getBody();
        LOGGER.info("Created person  {}", person);
    }
}
