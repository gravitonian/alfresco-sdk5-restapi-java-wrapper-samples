package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.alfresco.core.model.GroupEntry;
import org.alfresco.core.model.GroupPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListPersonGroupMembershipsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListPersonGroupMembershipsCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String personId) throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        String where = null;
        List<String> orderBy = null;
        List<String> include = null;
        List<String> fields = null;

        LOGGER.info("Listing group memberships for person {}", personId);
        GroupPaging groups = groupsApi.listGroupMembershipsForPerson(
                personId, skipCount, maxItems, orderBy, include, where, fields).getBody();
        for (GroupEntry groupEntry: groups.getList().getEntries()) {
            LOGGER.info("  {}", groupEntry.getEntry().getDisplayName());
        }
    }
}
