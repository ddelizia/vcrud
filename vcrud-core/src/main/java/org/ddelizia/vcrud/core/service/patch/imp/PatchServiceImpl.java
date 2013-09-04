package org.ddelizia.vcrud.core.service.patch.imp;

import org.ddelizia.vcrud.core.interf.PatchInterface;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.patch.PatchService;
import org.ddelizia.vcrud.model.patch.Patch;
import org.ddelizia.vcrud.model.patch.Patch_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/3/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PatchServiceImpl implements PatchService{

    @Autowired
    private ModelService modelService;

    @Override
    @Transactional
    public boolean executePatch(PatchInterface patchInterface) {
        String className = patchInterface.getClass().getName();

        boolean result = false;

        Patch patch = modelService.getModel(Patch_.className.getName(), className, Patch.class);

        if (patch == null){
            patch = new Patch();
            patch.setClassName(className);
        }

        if (patch.isExecuted() && patch.isExecuteOnce()){
            result = patch.isLastResult();
        }else{
            patch.setLastResult(patchInterface.runPatch());
            patch.setExecuted(true);
            result = patch.isLastResult();
        }

        modelService.persist(patch);

        return result;

    }

    @Override
    public Map<Patch, Boolean> executeAllPatches() {

        Map<Patch, Boolean> map = new HashMap<Patch, Boolean>();

        BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
        TypeFilter tf = new AssignableTypeFilter(PatchInterface.class);
        s.addIncludeFilter(tf);

        String[] beans = bdr.getBeanDefinitionNames();

        for (String strin : beans){
            try {
                Class clazz = Class.forName(strin);
                Object o = clazz.newInstance();
                map.put(null, executePatch((PatchInterface) o));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


        return map;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
