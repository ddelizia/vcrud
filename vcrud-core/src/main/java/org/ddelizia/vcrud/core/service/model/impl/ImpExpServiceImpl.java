package org.ddelizia.vcrud.core.service.model.impl;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.beanutils.PropertyUtils;
import org.ddelizia.vcrud.core.service.model.ImpExpService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */

@Service("org.ddelizia.vcrud.core.service.model.ImpExpService")
public class ImpExpServiceImpl implements ImpExpService {


    @Autowired
    private ModelService modelService;

    private final static String DELETE_TEXT="DELETE";
    private final static String INSERT_TEXT="INSERT";
    private final static String UPDATE_TEXT="UPDATE";
    private final static String UPDATE_INSERT_TEXT="UPDATE_INSERT";

    private final static String PROPERTY_ID_NAME = "vcrudIdField";

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ImpExpServiceImpl.class);

    @Override
    public void importData(Reader reader) throws IOException {
        CSVReader csvReader = new CSVReader(reader);
        String [] nextLine;
        String [] currentHeader = new String[0];
        String [] currentData;
        String operation = null;
        String className = null;
        while ((nextLine = csvReader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            LOGGER.info("Reading line: " + Arrays.toString(nextLine));
            if (nextLine[0].contains(DELETE_TEXT)
                    || nextLine[0].contains(INSERT_TEXT)
                    || nextLine[0].contains(UPDATE_TEXT)
                    || nextLine[0].contains(UPDATE_INSERT_TEXT)){

                currentHeader=nextLine;
                operation = currentHeader[0].substring(0, currentHeader[0].indexOf(" "));
                className = currentHeader[0].substring(currentHeader[0].indexOf(" "), currentHeader[0].length());

                LOGGER.info("Operation: " + operation);
            }else {
                currentData=nextLine;

                Type type = modelService.getModel(Type_.simpleClazzName.getName(), className.trim(), Type.class);

                try {
                    Class theClass = Class.forName(type.getClazz());
                    Object object = null;
                    if (operation.equals(INSERT_TEXT)){
                        LOGGER.debug("Creating new instance of class: " + theClass.getName());
                        object = theClass.newInstance();
                    }else if(operation.equals(UPDATE_TEXT) || operation.equals(UPDATE_INSERT_TEXT)){
                        Integer [] indexes = getIdsIndexs(currentData);
                        Map <String, Object> parametersObject = new HashMap<String, Object>();
                        for (int index: indexes){
                            String propertyAttributes[] = currentData[index].split(",");
                            HeaderObject headerObject = new HeaderObject(currentData[index]);
                            Map<String, Object> parameters = new HashMap<String, Object>();
                            parameters.put(Property_.type.getName(), type);
                            parameters.put(Property_.columnName.getName(), headerObject.getName());
                            Property property = modelService.getModel(parameters, Property.class);
                            parametersObject.put(property.getColumnName(), headerObject.createPropertyObject(propertyAttributes, property));
                        }
                        object = modelService.getModel(parametersObject,theClass);
                        LOGGER.info("Object in the db: " + object.toString());
                        if (object == null && operation.equals(UPDATE_INSERT_TEXT)){
                            LOGGER.info("Creating new instance of class: " + theClass.getName());
                            object = theClass.newInstance();
                        }
                    }

                    if (object != null){
                        for (int i=1 ;i<currentData.length ; i++){

                            HeaderObject headerObject = new HeaderObject(currentHeader[i]);

                            String propertyAttributes[] = currentData[i].split(",");

                            Map<String, Object> parameters = new HashMap<String, Object>();
                            parameters.put(Property_.type.getName(), type);
                            parameters.put(Property_.columnName.getName(), headerObject.getName());
                            Property property = modelService.getModel(parameters, Property.class);
                            PropertyUtils.setProperty(object, property.getColumnName(), headerObject.createPropertyObject(propertyAttributes, property));
                        }
                        modelService.rapidPersist((VcrudModel)object);
                    }


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InstantiationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }


        }
    }

    private class HeaderObject{
        private String name;
        private String [] attributes;
        private String rawValue;

        private HeaderObject(String rawValue){
            this.rawValue=rawValue;
            init();
        }

        private void init(){
            int firstIndexOfParentesis = rawValue.indexOf("[");

            String headerValue;
            String headerAttributes[]=new String[0];

            if (firstIndexOfParentesis>0){
                name = rawValue.substring(0, rawValue.indexOf("["));
                int start=rawValue.indexOf("[")+1;
                int end=rawValue.indexOf("]");
                attributes = rawValue.substring(start, end).split(",");
            }else {
                name = rawValue;
            }
        }

        private boolean evaluableAsId(){
            return Arrays.asList(attributes).contains(PROPERTY_ID_NAME);
        }

        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

        private String[] getAttributes() {
            return attributes;
        }

        private void setAttributes(String[] attributes) {
            this.attributes = attributes;
        }

        private String getRawValue() {
            return rawValue;
        }

        private void setRawValue(String rawValue) {
            this.rawValue = rawValue;
        }

        private Object createPropertyObject(String[] propertyAttributes, Property property) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

            Class propertyClass =  Class.forName(property.getClazz());

            //Instanciate property object
            Object propertyObject=null;
            if (propertyClass.getName().startsWith("java.lang")){
                Constructor constructor = propertyClass.getConstructor(String.class);
                propertyObject = constructor.newInstance(propertyAttributes[0]);
            }else if(modelService.getModel(Property_.clazz.getName(),propertyClass.getName(), Type.class)!=null){

                Type innerTypeData = modelService.getModel(Property_.clazz.getName(), propertyClass.getName(), Type.class);
                Class innerTypeClass =  Class.forName(innerTypeData.getClazz());

                //Inizialize object
                propertyObject = innerTypeClass.newInstance();

                Map <String,Object> attributeMap= new HashMap<String, Object>();
                for (int k=0;k< getAttributes().length; k++){
                    Class propertyAttributeClass = PropertyUtils.getPropertyType(propertyObject, getAttributes()[k]);
                    Constructor propertyAttributeConstructor = propertyAttributeClass.getConstructor(String.class);
                    Object propertyAttributeObject = propertyAttributeConstructor.newInstance(propertyAttributes[k]);
                    attributeMap.put( getAttributes()[k],propertyAttributeObject);

                }
                propertyObject= modelService.getModel(attributeMap, innerTypeClass);

            }
            return propertyObject;
        }
    }

    @Override
    public void importData(String fileName) {
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            importData(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void importStringData(String data) {
        StringReader stringReader = new StringReader(data);
        try {
            importData(stringReader);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Integer[] getIdsIndexs (String []strings){
        List<Integer> l = new LinkedList<Integer>();
        for(int i=0; i<strings.length; i++){
            HeaderObject headerObject = new HeaderObject(strings[i]);
            if (headerObject.evaluableAsId()){
                l.add(i);
            }
        }
        return l.toArray(new Integer[0]);
    }

    @Override
    public Writer exportData(VcrudModel vcrudModel) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
