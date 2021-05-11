package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeleteGroupMembershipCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteGroupMembershipCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String groupId, String groupMemberId) throws IOException {
        HttpEntity<Void> result = groupsApi.deleteGroupMembership(groupId, groupMemberId);
        LOGGER.info("Deleted group membership for group {} member {} result {}", groupId, groupMemberId, result);
    }
}
