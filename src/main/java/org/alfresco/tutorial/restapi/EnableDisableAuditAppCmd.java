package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.AuditApi;
import org.alfresco.core.model.AuditApp;
import org.alfresco.core.model.AuditBodyUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EnableDisableAuditAppCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableAuditAppCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String auditAppId) throws IOException {
        List<String> fields = null;

        AuditBodyUpdate auditBodyUpdate = new AuditBodyUpdate();
        auditBodyUpdate.setIsEnabled(true);

        AuditApp auditApp = auditApi.updateAuditApp(auditAppId, auditBodyUpdate, fields).getBody();
        LOGGER.info("Enabled audit app  {}", auditApp);
    }
}
