package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.AuditApi;
import org.alfresco.core.model.AuditEntryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GetAuditLogCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(GetAuditLogCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String auditAppId, String auditLogId) throws IOException {
        List<String> fields = null;

        AuditEntryEntry auditLog = auditApi.getAuditEntry(auditAppId, auditLogId, fields).getBody();
        LOGGER.info("Got audit log metadata  {}", auditLog);
    }
}
