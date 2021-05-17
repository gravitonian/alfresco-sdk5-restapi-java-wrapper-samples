
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

import org.alfresco.core.handler.QueriesApi;
import org.alfresco.core.model.SiteEntry;
import org.alfresco.core.model.SitePagingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FindSitesCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(FindSitesCmd.class);

    @Autowired
    QueriesApi queriesApi;

    public void execute() throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;

        List<String> orderBy = null;
        List<String> fields = null;

        String term = "Soft*";
        LOGGER.info("Searching for sites by term: {}", term);
        SitePagingList result = queriesApi.findSites(term, skipCount, maxItems, orderBy, fields).getBody().getList();
        for (SiteEntry node: result.getEntries()) {
            LOGGER.info("Found site [id={}][name={}]", node.getEntry().getId(), node.getEntry().getTitle());
        }
    }
}
