
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

import org.alfresco.core.handler.AuditApi;
import org.alfresco.core.model.AuditEntryEntry;
import org.alfresco.core.model.AuditEntryPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListNodeAuditLogsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListNodeAuditLogsCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String nodeId) throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        String where = null;
        List<String> fields = null;
        List<String> include = new ArrayList<>();
        List<String> orderBy = null;

        // Include the log values
        include.add("values");

        LOGGER.info("Listing logs for node ID {}:", nodeId);
        AuditEntryPaging auditLogs = auditApi.listAuditEntriesForNode(
                nodeId, skipCount, orderBy, maxItems, where, include, fields).getBody();
        for (AuditEntryEntry auditAppEntry: auditLogs.getList().getEntries()) {
            String username = "N/A";
            if (auditAppEntry.getEntry().getCreatedByUser() != null) {
                username = auditAppEntry.getEntry().getCreatedByUser().getId();
            }
            String log = null;
            if (auditAppEntry.getEntry().getValues().toString().length() > 60) {
                log = auditAppEntry.getEntry().getValues().toString().substring(0, 60);
            } else {
                log = auditAppEntry.getEntry().getValues().toString();
            }
            LOGGER.info("  {} {} {} {}", auditAppEntry.getEntry().getId(), auditAppEntry.getEntry().getCreatedAt(),
                    username, log);
        }
    }
}
