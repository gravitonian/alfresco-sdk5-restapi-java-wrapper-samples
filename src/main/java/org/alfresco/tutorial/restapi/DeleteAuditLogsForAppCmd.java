package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.AuditApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeleteAuditLogsForAppCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteAuditLogsForAppCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String auditAppId) throws IOException {
        String where = "(id BETWEEN ('1', '79'))";

        HttpEntity<Void> response = auditApi.deleteAuditEntriesForAuditApp(auditAppId, where);
        LOGGER.info("Deleted audit logs for app {} where {} response {}", auditAppId, where, response);
    }
}
