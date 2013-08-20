package org.ddelizia.vcrud.core.test.config;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 20/08/13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class VcrudTestSetUp {

    private static boolean testEnvSetUp = false;

    public static boolean isTestEnvSetUp() {
        return testEnvSetUp;
    }

    public static void setTestEnvSetUp(boolean testEnvSetUp) {
        VcrudTestSetUp.testEnvSetUp = testEnvSetUp;
    }
}
