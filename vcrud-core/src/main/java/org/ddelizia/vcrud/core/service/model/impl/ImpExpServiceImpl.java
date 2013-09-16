package org.ddelizia.vcrud.core.service.model.impl;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.beanutils.PropertyUtils;
import org.ddelizia.vcrud.core.service.model.ImpExpService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.model.*;
import org.ddelizia.vcrud.model.system.*;
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
        CSVReader csvReader = new CSVReader(reader,';');
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
                    boolean isUpdate = false;
                    Class theClass = Class.forName(type.getClazz());
                    Object object = null;
                    if (operation.equals(INSERT_TEXT)){
                        LOGGER.debug("Creating new instance of class: " + theClass.getName());
                        object = theClass.newInstance();
                    }else if(operation.equals(UPDATE_TEXT) || operation.equals(UPDATE_INSERT_TEXT)){
                        Integer [] indexes = getIdsIndexs(currentHeader,type);
                        Map <String, Object> parametersObject = new HashMap<String, Object>();
                        for (int index: indexes){
                            HeaderObject headerObject = new HeaderObject(currentHeader[index], type);
                            parametersObject.put(headerObject.getName(), headerObject.createPropertyObject(currentData[index]));
                        }
                        object = modelService.getModel(parametersObject,theClass);
                        LOGGER.info("Object in the db: " + object);
                        if (object == null && operation.equals(UPDATE_INSERT_TEXT)){
                            LOGGER.info("Creating new instance of class: " + theClass.getName());
                            object = theClass.newInstance();
                        }
                    }

                    if (object != null){
                        for (int i=1 ;i<currentData.length ; i++){
                            HeaderObject headerObject = new HeaderObject(currentHeader[i], type);

                            PropertyUtils.setProperty(object, headerObject.getName(), headerObject.createPropertyObject(currentData[i]));
                        }
                        modelService.rapidMerge((VcrudModel)object);
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

    private Integer[] getIdsIndexs (String []strings, Type type){
        List<Integer> l = new LinkedList<Integer>();
        for(int i=1; i<strings.length; i++){
            HeaderObject headerObject = new HeaderObject(strings[i], type);
            if (headerObject.evaluableAsId()){
                l.add(i);
            }
        }
        return l.toArray(new Integer[0]);
    }

    private class HeaderObject{
        private String name;
        private String [] attributes = new String[0];
        private String rawValue;
        private String [] labels = new String[0];
        private Type type;
        private Property property;

        private HeaderObject(String rawValue, Type type){
            this.rawValue=rawValue;
            this.type=type;
            init();
        }

        private void init(){
            int firstIndexOfAttributes = rawValue.indexOf("[");
            int firstIndexOfLabels = rawValue.indexOf("{");

            String headerValue;
            String headerAttributes[]=new String[0];

            if (firstIndexOfAttributes>0 ||
                    firstIndexOfLabels>0
            ){
                if (firstIndexOfAttributes>0 && firstIndexOfLabels>0){
                    name = rawValue.substring(0, Math.min(firstIndexOfAttributes,firstIndexOfLabels));
                }else if (firstIndexOfAttributes>0){
                    name = rawValue.substring(0, firstIndexOfAttributes);
                }else if (firstIndexOfLabels>0){
                    name = rawValue.substring(0, firstIndexOfLabels);
                }

                if (firstIndexOfAttributes>0){
                    int startAttributes=rawValue.indexOf("[")+1;
                    int endAttributes=rawValue.indexOf("]");
                    attributes = rawValue.substring(startAttributes, endAttributes).split(",");
                }
                if (firstIndexOfLabels>0){
                    int startLabel=rawValue.indexOf("{")+1;
                    int endLabel=rawValue.indexOf("}");
                    labels = rawValue.substring(startLabel, endLabel).split(",");
                }
            }else {
                name = rawValue;
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(Property_.type.getName(), type);
            parameters.put(Property_.columnName.getName(), getName());
            property = modelService.getModel(parameters, Property.class);
        }

        private boolean evaluableAsId(){
            return Arrays.asList(labels).contains(PROPERTY_ID_NAME);
        }

        private boolean containsLabel(String label){
            return Arrays.asList(labels).contains(label);
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

        private Object createPropertyObject(String data) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

            Class propertyClass =  Class.forName(property.getClazz());

            //Instanciate property object
            Object propertyObject=null;
            if (propertyClass.getName().startsWith("java.lang")){
                Constructor constructor = propertyClass.getConstructor(String.class);
                propertyObject = constructor.newInstance(data);
            }else if(modelService.getModel(Property_.clazz.getName(),propertyClass.getName(), Type.class)!=null){

                Type innerTypeData = modelService.getModel(Property_.clazz.getName(), propertyClass.getName(), Type.class);
                Class innerTypeClass =  Class.forName(innerTypeData.getClazz());

                //Inizialize object
                propertyObject = innerTypeClass.newInstance();
                String [] dataAttrs = data.split(",");
                Map <String,Object> attributeMap= new HashMap<String, Object>();

                for (int k=0;k< getAttributes().length; k++){
                    Class propertyAttributeClass = PropertyUtils.getPropertyType(propertyObject, getAttributes()[k]);
                    Constructor propertyAttributeConstructor = propertyAttributeClass.getConstructor(String.class);
                    Object propertyAttributeObject = propertyAttributeConstructor.newInstance(dataAttrs[k]);
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

    @Override
    public Writer exportData(VcrudModel vcrudModel) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
