package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.vaadin.ui.TabSheet;
import org.apache.commons.lang.StringUtils;
import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudProperty;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 7/06/13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public class DataTabs extends TabSheet {

    public static final String DEFAULT_TAB_NAME = "administration";

    private Class<? extends VcrudModel> clazz;

    private Map<String, List<Field>> fields;

    public DataTabs() {
        super();
        fields=new HashMap<String, List<Field>>();
        setSizeFull();
    }

    public void buildTabs(){
        Field[] allFields =clazz.getDeclaredFields();
        for(Field field : allFields){
            VcrudProperty vcrudProperty = field.getAnnotation(VcrudProperty.class);
            Transient aTransient = field.getAnnotation(Transient.class);
            if (aTransient!=null){
                if(vcrudProperty!=null && StringUtils.isNotEmpty(vcrudProperty.group())){
                    putField(vcrudProperty.group(),field);
                }else{
                    putField(DEFAULT_TAB_NAME,field);
                }
            }
        }
    }

    private void putField(String string, Field field){
        List<Field> listOfFields = fields.get(string);
        if(listOfFields==null){
            listOfFields=new ArrayList<Field>();
        }
        listOfFields.add(field);
        fields.put(string,listOfFields);
    }

    public Class<? extends VcrudModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends VcrudModel> clazz) {
        this.clazz = clazz;
    }
}
