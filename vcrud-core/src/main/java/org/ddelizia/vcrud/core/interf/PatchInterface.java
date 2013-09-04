package org.ddelizia.vcrud.core.interf;

import org.ddelizia.vcrud.core.service.model.ImpExpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/3/13
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PatchInterface {

    @Autowired
    private ImpExpService impExpService;

    public boolean importFromResource(String resource){
        InputStream in = this.getClass().getResourceAsStream(resource);
        Reader reader = new InputStreamReader(in);
        try {
            impExpService.importData(reader);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public abstract boolean runPatch();

}
