package org.ddelizia.vcrud.core.service.model;

import java.io.IOException;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public interface ImpExpService {

    public void importData(Reader reader) throws IOException;

}
