package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.AuditApi;
import org.alfresco.core.model.AuditApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GetAuditAppCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(GetAuditAppCmd.class);

    @Autowired
    AuditApi auditApi;

    public void execute(String auditAppId) throws IOException {
        List<String> fields = null;
        List<String> include = null;

        AuditApp auditApp = auditApi.getAuditApp(auditAppId, fields, include).getBody();
        LOGGER.info("Got audit app metadata  {}", auditApp);
    }
}
