package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.AuditApi;
import org.alfresco.core.model.AuditAppEntry;
import org.alfresco.core.model.AuditAppPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListAuditAppsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListAuditAppsCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute() throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        List<String> fields = null;

        LOGGER.info("Listing active audit applications in the repository:");
        AuditAppPaging auditApps = auditApi.listAuditApps(skipCount, maxItems, fields).getBody();
        for (AuditAppEntry auditAppEntry: auditApps.getList().getEntries()) {
            LOGGER.info("  {}", auditAppEntry);
        }
    }
}
