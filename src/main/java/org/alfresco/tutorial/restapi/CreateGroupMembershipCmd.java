package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.alfresco.core.model.GroupMemberEntry;
import org.alfresco.core.model.GroupMembershipBodyCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreateGroupMembershipCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(CreateGroupMembershipCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String groupId, String memberId, String type) throws IOException {
        List<String> fields = null;

        GroupMembershipBodyCreate groupMembershipBodyCreate = new GroupMembershipBodyCreate();
        groupMembershipBodyCreate.setId(memberId);
        groupMembershipBodyCreate.setMemberType(GroupMembershipBodyCreate.MemberTypeEnum.fromValue(type));

        GroupMemberEntry groupMember = groupsApi.createGroupMembership(groupId, groupMembershipBodyCreate, fields).getBody();
        LOGGER.info("Added member to group {} {}", groupId, groupMember.getEntry());
    }
}
