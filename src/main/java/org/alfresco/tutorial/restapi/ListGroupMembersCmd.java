package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.alfresco.core.model.GroupMemberEntry;
import org.alfresco.core.model.GroupMemberPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListGroupMembersCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListGroupMembersCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String groupId) throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        String where = null;
        List<String> orderBy = null;
        List<String> fields = null;

        LOGGER.info("Listing members of group {}:", groupId);
        GroupMemberPaging groupMembers = groupsApi.listGroupMemberships(
                groupId, skipCount, maxItems, orderBy, where, fields).getBody();
        for (GroupMemberEntry groupMemberEntry: groupMembers.getList().getEntries()) {
            LOGGER.info("  {} ({})", groupMemberEntry.getEntry().getDisplayName(), groupMemberEntry.getEntry().getMemberType());
        }
    }
}
