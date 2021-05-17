
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
