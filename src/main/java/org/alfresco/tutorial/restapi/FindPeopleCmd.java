package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.QueriesApi;
import org.alfresco.core.model.PersonEntry;
import org.alfresco.core.model.PersonPagingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FindPeopleCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(FindPeopleCmd.class);

    @Autowired
    QueriesApi queriesApi;

    public void execute() throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;

        List<String> orderBy = null;
        List<String> fields = null;

        String term = "*mi*";
        LOGGER.info("Searching for people by term: {}", term);
        PersonPagingList result = queriesApi.findPeople(term, skipCount, maxItems, fields, orderBy).getBody().getList();
        for (PersonEntry person: result.getEntries()) {
            LOGGER.info("Found person [id={}][name={}]", person.getEntry().getId(), person.getEntry().getDisplayName());
        }
    }
}
