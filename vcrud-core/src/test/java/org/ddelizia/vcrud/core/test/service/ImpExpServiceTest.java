package org.ddelizia.vcrud.core.test.service;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.ddelizia.vcrud.model.usermanagement.Role;
import org.ddelizia.vcrud.model.usermanagement.Role_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 8/17/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */

public class ImpExpServiceTest extends AbstractJunit4Vcrud {

    private boolean isSetUp = false;

    private static final Logger LOGGER = Logger.getLogger(ImpExpServiceTest.class);

    @Test
    public void testImportFileInsert() {
        LOGGER.info("Testing Import INSERT " + ImpExpServiceTest.class);

        List<User> users = getModelService().getModels(User_.password.getName(), "ddelizia", User.class, null, null);

        Assert.assertEquals(users.size(), 2);
        org.springframework.util.Assert.noNullElements(users.toArray());
    }

    @Test
    public void testImportFileUpdateInsert() {
        LOGGER.info("Testing Import UPDATE_INSERT " + ImpExpServiceTest.class);

        List<User> users = getModelService().getModels(User_.username.getName(), "ddelizia1", User.class, null, null);

        Assert.assertEquals(users.size(), 1);
        org.springframework.util.Assert.noNullElements(users.toArray());
        User u = users.get(0);
        Assert.assertEquals(u.getPassword(), "ddelizia2");
    }

    @Test
    public void testImportFileUpdate() {
        LOGGER.info("Testing Import UPDATE " + ImpExpServiceTest.class);

        List<User> users = getModelService().getModels(User_.username.getName(), "ddelizia2", User.class, null, null);

        Assert.assertEquals(users.size(), 1);
        org.springframework.util.Assert.noNullElements(users.toArray());
        User u = users.get(0);
        Assert.assertEquals(u.getPassword(), "ddelizia3");
    }

    @Test
    public void testImportAttributeRetriving() {
        LOGGER.info("Retrive attribute to store reference " + ImpExpServiceTest.class);

        User user = getModelService().getModel(User_.username.getName(), "ddelizia1", User.class);
        Role role = getModelService().getModel(Role_.code.getName(), "user", Role.class);

        Assert.assertEquals(user.getRole().getCode(), role.getCode());
        Assert.assertEquals(user.getRole().getDescription(), role.getDescription());
    }

    @Override
    public void vcrudBefore() {

    }
}
