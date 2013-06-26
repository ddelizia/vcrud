package org.ddelizia.vcrud.core.service.impl;

import au.com.bytecode.opencsv.CSVReader;
import org.ddelizia.vcrud.core.service.ImpExpService;
import org.ddelizia.vcrud.core.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */

@Service("org.ddelizia.vcrud.core.service.ImpExpService")
public class ImpExpServiceImpl implements ImpExpService {

    @Autowired
    private ModelService modelService;

    private final static String DELETE_TEXT="DELETE";
    private final static String INSERT_TEXT="INSERT";
    private final static String UPDATE_TEXT="UPDATE";
    private final static String UPDATE_INSERT_TEXT="UPDATE_INSERT";

    public void importData(Reader reader) throws IOException {
        CSVReader csvReader = new CSVReader(reader);
        String [] nextLine;
        String [] currentHeader;
        while ((nextLine = csvReader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println(nextLine[0] + nextLine[1] + "etc...");
            if (nextLine[0].contains(DELETE_TEXT)
                    || nextLine[0].contains(INSERT_TEXT)
                    || nextLine[0].contains(UPDATE_TEXT)
                    || nextLine[0].contains(UPDATE_INSERT_TEXT)){
                currentHeader=nextLine;
            }else {
                for (int i=0 ;i<nextLine.length ; i++){

                }
            }


        }
    }
}
