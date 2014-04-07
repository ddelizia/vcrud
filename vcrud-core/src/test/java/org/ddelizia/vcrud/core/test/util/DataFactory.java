package org.ddelizia.vcrud.core.test.util;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.basic.helper.MongoHelper;
import org.ddelizia.vcrud.core.config.AppConfig;

import javax.annotation.Resource;

/**
 * @author ddelizia
 * @since 10/03/14 09:12
 */
public abstract class DataFactory {

	private static final Logger LOGGER = Logger.getLogger(DataFactory.class);

	@Resource
	private AppConfig appConfig;

	@Resource
	private MongoHelper mongoHelper;

	public abstract void createData();

	public abstract void removeData();

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public MongoHelper getMongoHelper() {
		return mongoHelper;
	}
}
