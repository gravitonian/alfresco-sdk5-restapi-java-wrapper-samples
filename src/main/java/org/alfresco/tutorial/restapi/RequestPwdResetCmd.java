package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.PeopleApi;
import org.alfresco.core.model.ClientBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestPwdResetCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(RequestPwdResetCmd.class);

    @Autowired
    PeopleApi peopleApi;

    public void execute(String personId) throws IOException {
        ClientBody clientBody = new ClientBody();
        clientBody.setClient("share"); // Alfresco Share UI client

        HttpEntity<Void> result = peopleApi.requestPasswordReset(personId, clientBody);
        LOGGER.info("Password reset request sent for {} result {}", personId, result);
    }
}
