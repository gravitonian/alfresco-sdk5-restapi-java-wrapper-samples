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
