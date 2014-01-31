package org.ddelizia.vcrud.core.interceptor;

import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.interceptor.type.AfterSaveInterceptor;
import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;
import org.ddelizia.vcrud.core.interceptor.util.ClassComparatorByClassType;
import org.ddelizia.vcrud.core.interceptor.type.AfterConvertIntereptor;
import org.ddelizia.vcrud.core.interceptor.type.BeforeSaveInterceptor;
import org.ddelizia.vcrud.core.interceptor.util.InterceptorRegistry;
import org.ddelizia.vcrud.core.interceptor.util.InterceptorWrapper;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 13/01/14
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public class VcrudInterceptorListener<T extends VcrudItem> extends AbstractMongoEventListener<T>{

    @Autowired
    private InterceptorRegistry interceptorRegistry;

    private Map<Class<? extends VcrudItem>, List<Class<? extends VcrudItem>>> classStructure;

    private Map<InterceptorCacheKey, List<? extends VcrudInterceptor>> interceptorCache = new HashMap<>();

    private static final Logger logger = Logger.getLogger(VcrudInterceptorListener.class);

    @Override
    public void onAfterConvert(DBObject dbo, T source) {
        super.onAfterConvert(dbo, source);

        runInterceptor(source.getClass(),
                AfterConvertIntereptor.class,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                new Class[]{DBObject.class, VcrudItem.class},
                new Object[]{dbo, source});

    }

    @Override
    public void onBeforeSave(T source, DBObject dbo) {
        super.onBeforeSave(source, dbo);

        runInterceptor(source.getClass(),
                BeforeSaveInterceptor.class,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                new Class[]{VcrudItem.class, DBObject.class},
                new Object[]{source, dbo});
    }

    @Override
    public void onAfterSave(T source, DBObject dbo) {
        super.onAfterSave(source, dbo);

        runInterceptor(source.getClass(),
                AfterSaveInterceptor.class,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                new Class[]{VcrudItem.class, DBObject.class},
                new Object[]{source});
    }

    @Override
    public void onAfterDelete(DBObject dbo) {
        super.onAfterDelete(dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAfterLoad(DBObject dbo) {
        super.onAfterLoad(dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onApplicationEvent(MongoMappingEvent<?> event) {
        super.onApplicationEvent(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onBeforeConvert(T source) {
        super.onBeforeConvert(source);

        runInterceptor(source.getClass(),
                BeforeConvertInterceptor.class,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                new Class[]{VcrudItem.class},
                new Object[]{source});
    }

    @Override
    public void onBeforeDelete(DBObject dbo) {
        super.onBeforeDelete(dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }


    private void runInterceptor(Class<? extends VcrudItem> source,
                                Class<? extends VcrudInterceptor> interceptorClass,
                                String methodName,
                                Class<? extends Object>[] parametresCalsses,
                                Object[] parametres){
        InterceptorCacheKey interceptorCacheKey = new InterceptorCacheKey(source, interceptorClass);
        List<? extends VcrudInterceptor> cacheResultList= interceptorCache.get(interceptorCacheKey);
        if(cacheResultList != null){
            for (VcrudInterceptor vcrudInterceptor: cacheResultList){
                executeMethod(vcrudInterceptor,
                        methodName,
                        parametresCalsses,
                        parametres);
            }
        }else{
            //Ordering a classStructure
            buildClassStructure();

            //fill cache while executing
            List<Class<? extends VcrudItem>> classesToExecute = classStructure.get(source);

            if (classesToExecute == null){
                classesToExecute = addElementToClassStructure(source);
            }

            for (Class<? extends VcrudItem> aClass: classesToExecute){
                List<InterceptorWrapper> interceptors = interceptorRegistry.getMappings().get(aClass);
                //Order this array
                Collections.sort(interceptors);

                for (InterceptorWrapper vcrudInterceptorWrapper: interceptors){
                    VcrudInterceptor vcrudInterceptor =  vcrudInterceptorWrapper.getInterceptor();
                    InterceptorCacheKey currentInterceptorCacheKey = new InterceptorCacheKey(aClass, vcrudInterceptor.getClass());
                    if(interceptorCache.get(currentInterceptorCacheKey) == null){
                        interceptorCache.put(currentInterceptorCacheKey, new ArrayList<VcrudInterceptor>());
                    }
                    List<VcrudInterceptor> list = (List<VcrudInterceptor>) interceptorCache.get(currentInterceptorCacheKey);
                    list.add(vcrudInterceptor);
                    if(interceptorClass.isInstance(vcrudInterceptor)){
                        executeMethod(vcrudInterceptor, methodName, parametresCalsses, parametres);
                    }
                }
            }
        }

    }

    private void executeMethod(VcrudInterceptor interceptor,
                               String methodName,
                               Class<? extends Object>[] parametresCalsses,
                               Object[] parametres){
        try {
            Method methodToExecute = interceptor.getClass().getMethod(methodName, parametresCalsses);
            methodToExecute.invoke(interceptor,parametres);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void buildClassStructure(){
        if(classStructure == null){
            classStructure = new HashMap<>();
            for (Class<? extends VcrudItem> vcrudItem: interceptorRegistry.getMappings().keySet()){
                classStructure.put(vcrudItem, new ArrayList<Class<? extends VcrudItem>>());
                classStructure.get(vcrudItem).add(vcrudItem);
                for (Class<? extends VcrudItem> otherClasses: interceptorRegistry.getMappings().keySet()){
                    if (!vcrudItem.equals(otherClasses)  &&
                            otherClasses.isAssignableFrom(vcrudItem)){
                        classStructure.get(vcrudItem).add(otherClasses);
                    }
                }
                Collections.sort(classStructure.get(vcrudItem), new ClassComparatorByClassType());
            }
        }
    }

    private List<Class<? extends VcrudItem>> addElementToClassStructure(Class<? extends VcrudItem> theNewClass){
        classStructure.put(theNewClass, new ArrayList<Class<? extends VcrudItem>>());
        for (Class<? extends VcrudItem> otherClasses: interceptorRegistry.getMappings().keySet()){
            if (otherClasses.isAssignableFrom(theNewClass)){
                classStructure.get(theNewClass).add(otherClasses);
            }
        }
        Collections.sort(classStructure.get(theNewClass), new ClassComparatorByClassType());
        return classStructure.get(theNewClass);
    }

    private class InterceptorCacheKey{
        private Class<? extends VcrudItem> source;

        private Class<? extends VcrudInterceptor> interceptorClass;

        private InterceptorCacheKey(Class<? extends VcrudItem> source, Class<? extends VcrudInterceptor> interceptorClass) {
            this.source = source;
            this.interceptorClass = interceptorClass;
        }

        private Class<? extends VcrudItem> getSource() {
            return source;
        }

        private void setSource(Class<? extends VcrudItem> source) {
            this.source = source;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InterceptorCacheKey that = (InterceptorCacheKey) o;

            if (!interceptorClass.equals(that.interceptorClass)) return false;
            if (!source.equals(that.source)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = source.hashCode();
            result = 31 * result + interceptorClass.hashCode();
            return result;
        }

        private Class<? extends VcrudInterceptor> getInterceptorClass() {
            return interceptorClass;
        }

        private void setInterceptorClass(Class<? extends VcrudInterceptor> interceptorClass) {
            this.interceptorClass = interceptorClass;
        }
    }

}

