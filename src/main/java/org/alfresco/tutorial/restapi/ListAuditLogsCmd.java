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
public class ListAuditLogsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListAuditLogsCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String auditAppId) throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        String where = null;
        List<String> fields = null;
        List<String> include = new ArrayList<>();
        List<String> orderBy = null;

        // Include the log values
        include.add("values");

        // Controls if the response provides the total numbers of items in the collection.
        // If not supplied then the default value is false.
        Boolean omitTotalItems = true;

        LOGGER.info("Listing logs for audit application {}:", auditAppId);
        AuditEntryPaging auditLogs = auditApi.listAuditEntriesForAuditApp(
                auditAppId, skipCount, omitTotalItems, orderBy, maxItems, where, include, fields).getBody();
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
            LOGGER.info("  {} {} {}", auditAppEntry.getEntry().getCreatedAt(), username, log);
        }
    }
}
