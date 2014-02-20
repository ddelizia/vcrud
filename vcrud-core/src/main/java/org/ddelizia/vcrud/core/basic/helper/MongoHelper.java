package org.ddelizia.vcrud.core.basic.helper;

import com.mongodb.BasicDBObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author ddelizia
 * @since 20/02/14 10:43
 */
public class MongoHelper {

    private static final Logger LOGGER = Logger.getLogger(MongoHelper.class);

    @Autowire
    private MongoTemplate mongoTemplate;

    /**
     * Executes a Mongo Javascript function on current template
     * @param path
     */
    public void executeMongoFile(String path){
        try {
            BasicDBObject obj = new BasicDBObject();
            StringWriter writer = new StringWriter();
            LOGGER.debug("Importing file: "+ path);
            IOUtils.copy(ClassLoader.getSystemResourceAsStream(path), writer, "UTF-8");
            String theString = writer.toString();
            obj.append( "$eval" ,  theString);
            mongoTemplate.executeCommand(obj);
        } catch (IOException e) {
            LOGGER.error("Cannot load file for executing mongo command");
            e.printStackTrace();
        }
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
