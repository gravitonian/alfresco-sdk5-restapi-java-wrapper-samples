package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.PeopleApi;
import org.alfresco.core.handler.PreferencesApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GetPersonMetadataCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(GetPersonMetadataCmd.class);

    @Autowired
    PeopleApi peopleApi;

    @Autowired
    PreferencesApi preferencesApi;

    public void execute(String personId) throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        List<String> fields = null;

        PersonEntry person = peopleApi.getPerson(personId, fields).getBody();
        LOGGER.info("Got person metadata {}", person);
        PreferencePaging preferencePaging = preferencesApi.listPreferences(personId, skipCount, maxItems, fields).getBody();
        LOGGER.info("Got person preferences:");
        for (PreferenceEntry preferenceEntry: preferencePaging.getList().getEntries()) {
            LOGGER.info("  preference: {}", preferenceEntry.getEntry());
        }
    }
}
