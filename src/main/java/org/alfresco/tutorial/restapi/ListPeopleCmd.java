
/*
 * Copyright 2021-2021 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
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
