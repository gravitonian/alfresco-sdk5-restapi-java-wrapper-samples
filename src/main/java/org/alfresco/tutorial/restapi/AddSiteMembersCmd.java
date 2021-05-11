package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static org.alfresco.core.model.SiteMembershipBodyCreate.RoleEnum.SITECOLLABORATOR;

@Component
public class AddSiteMembersCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(AddSiteMembersCmd.class);

    private List<String> fields = null;

    @Autowired
    SitesApi sitesApi;

    public void execute(String siteId, String personId) throws IOException {
        SiteMembershipBodyCreate siteMembershipBodyCreate = new SiteMembershipBodyCreate();
        siteMembershipBodyCreate.setId(personId);
        siteMembershipBodyCreate.setRole(SITECOLLABORATOR);
        SiteMemberEntry siteMemberEntry = sitesApi.createSiteMembership(siteId, siteMembershipBodyCreate, fields).getBody();
        LOGGER.info("Created site membership {}", siteMemberEntry);
    }
}
