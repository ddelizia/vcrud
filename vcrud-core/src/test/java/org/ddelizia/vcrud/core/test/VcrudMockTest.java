package org.ddelizia.vcrud.core.test;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author ddelizia
 * @since 07/04/14 22:23
 */
public class VcrudMockTest {

	private static final Logger LOGGER = Logger.getLogger(VcrudMockTest.class);

	public AppConfig appConfig;
	public static final String APP_CONFIG_GETPROPERTY = "Property Return";

	public void mockAppConfig(){
		appConfig = Mockito.mock(AppConfig.class);
		Mockito.when(appConfig.getProperty(
				Matchers.any(String.class),
				Matchers.eq(String.class),
				Matchers.any(String.class)
		)).thenReturn(APP_CONFIG_GETPROPERTY);
	}


}
