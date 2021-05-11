package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.PeopleApi;
import org.alfresco.core.model.PersonEntry;
import org.alfresco.core.model.PersonPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListPeopleCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListPeopleCmd.class);

    @Autowired
    PeopleApi peopleApi;

    public void execute() throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        List<String> orderBy = null;
        List<String> include = null;
        List<String> fields = null;

        LOGGER.info("Listing people in the repository");
        PersonPaging people = peopleApi.listPeople(skipCount, maxItems, orderBy, include, fields).getBody();
        for (PersonEntry personEntry: people.getList().getEntries()) {
            LOGGER.info("  {} ({})", personEntry.getEntry().getDisplayName(), personEntry.getEntry().getId());
        }
    }
}
