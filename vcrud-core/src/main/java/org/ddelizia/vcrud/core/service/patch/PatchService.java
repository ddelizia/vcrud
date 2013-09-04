package org.ddelizia.vcrud.core.service.patch;

import org.ddelizia.vcrud.core.interf.PatchInterface;
import org.ddelizia.vcrud.model.patch.Patch;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/3/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PatchService {

    public boolean executePatch(PatchInterface patchInterface);

    public  Map<Patch, Boolean> executeAllPatches();

}
