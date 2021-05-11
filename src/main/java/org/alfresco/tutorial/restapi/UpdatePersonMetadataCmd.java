package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.PeopleApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UpdatePersonMetadataCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(UpdatePersonMetadataCmd.class);

    @Autowired
    PeopleApi peopleApi;

    public void execute(String personId) throws IOException {
        List<String> fields = null;

        PersonBodyUpdate personBodyUpdate = new PersonBodyUpdate();
        // Mandatory fields during an update
        personBodyUpdate.setFirstName("Martin");
        personBodyUpdate.setLastName("Bergljung");
        personBodyUpdate.setEmail("martin@example.com");
        personBodyUpdate.setEmailNotificationsEnabled(true);
        personBodyUpdate.setOldPassword("1234");
        personBodyUpdate.setPassword("1234");
        personBodyUpdate.setEnabled(true);

        // Other fields
        personBodyUpdate.setJobTitle("Techie");
        Company company = new Company();
        company.setAddress1("Alfresco way 1");
        company.setOrganization("Alfresco Org");
        company.setTelephone("12345678");
        personBodyUpdate.setCompany(company);

        PersonEntry person = peopleApi.updatePerson(personId, personBodyUpdate, fields).getBody();
        LOGGER.info("Updated person metadata {}", person);
    }
}
