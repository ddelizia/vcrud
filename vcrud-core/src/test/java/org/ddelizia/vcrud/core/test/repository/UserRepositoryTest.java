package org.ddelizia.vcrud.core.test.repository;

import com.mongodb.BasicDBObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author ddelizia
 * @since 18/02/14 12:33
 */
public class UserRepositoryTest extends AbstractJunit4Vcrud{

    private static final Logger LOGGER = Logger.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void findByName(){
        LOGGER.info("Hola");




    }

    public void findByEmail (){
        //public User findByEmail (String email);
    }


    public void findUserBelongsToGroup(){
        //public boolean findUserBelongsToGroup(User user, UserGroup userGroup);
    }

    /**
     * Implement this method instead of @After
     */
    @Override
    public void vcrudAfter() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Implement this method instead of @Before
     */
    @Override
    public void vcrudBefore() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
