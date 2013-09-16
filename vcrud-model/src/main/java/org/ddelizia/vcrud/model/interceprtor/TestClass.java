package org.ddelizia.vcrud.model.interceprtor;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernatePersistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Map;

public class TestClass extends HibernatePersistence {
    private Interceptor interceptor;
    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        Ejb3Configuration cfg = new Ejb3Configuration();
        Ejb3Configuration configured = cfg.configure( info, map );
        postprocessConfiguration(info, map, configured);
        return configured != null ? configured.buildEntityManagerFactory() : null;
    }

    @Override
    public EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties) {
        Ejb3Configuration cfg = new Ejb3Configuration();
        //Ejb3Configuration configured = cfg.configure( info, map );
        //postprocessConfiguration(info, map, configured);
        return null;
    }



    protected void postprocessConfiguration(PersistenceUnitInfo info, Map map, Ejb3Configuration configured) {
        if (this.interceptor != null)
        {
            if (configured.getInterceptor()==null || EmptyInterceptor.class.equals(configured.getInterceptor().getClass()))
            {
                configured.setInterceptor(this.interceptor);
            }
            else
            {
                throw new IllegalStateException("Hibernate interceptor already set in persistence.xml ("+configured.getInterceptor()+")");
            }
        }
    }

    public TestClass(){
        System.out.println("LALALAL-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("LALALAL-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("LALALAL-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("LALALAL-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("LALALAL-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("LALALAL-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }
} 