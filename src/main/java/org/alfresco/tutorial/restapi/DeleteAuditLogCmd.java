package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.AuditApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeleteAuditLogCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteAuditLogCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String auditAppId, String auditLogId) throws IOException {
        HttpEntity<Void> response = auditApi.deleteAuditEntry(auditAppId, auditLogId);
        LOGGER.info("Deleted audit log: app {} log id {} response {}", auditAppId, auditLogId, response);
    }
}
