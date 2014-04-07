package org.ddelizia.vcrud.core.test;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.basic.helper.MongoHelper;
import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.test.util.UserManagmentDataFactory;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;

import javax.annotation.Resource;

/**
 * @author ddelizia
 * @since 08/03/14 21:23
 */
public abstract class VcrudCoreIntegrationTest extends AbstractJunit4Vcrud{

	private static final Logger LOGGER = Logger.getLogger(VcrudCoreIntegrationTest.class);

	@Resource
	private UserManagmentDataFactory userManagmentDataFactory;

	@Resource
	private MongoHelper mongoHelper;

	@Resource
	private AppConfig appConfig;

	public void removeFromDbDataForEntity(Class<? extends VcrudItem> aClass){
		mongoHelper.removeAllDataFromCollection(aClass);
	}

	public UserManagmentDataFactory getUserManagmentDataFactory() {
		return userManagmentDataFactory;
	}

	public MongoHelper getMongoHelper() {
		return mongoHelper;
	}

	public AppConfig getAppConfig() {
		return appConfig;
	}
}
