package org.ddelizia.vcrud.core.test;

import junit.framework.TestCase;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.core.test.model.ModelFather;
import org.ddelizia.vcrud.core.test.model.ModelSon;
import org.ddelizia.vcrud.core.test.model.WrapperFather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/applicationContext-core.xml")
public class WrapperTest extends TestCase {

    private static String MODEL_SON_PROPERTY1="TEST1";
    private static String MODEL_SON_PROPERTY2="TEST2";
    private static String MODEL_FATHER_PROPERTY1="FATHER1";
    private static int MODEL_FATHER_LISTSIZE=2;


    private ModelFather buildModel(){
        ModelSon modelDB11= new ModelSon();
        modelDB11.setTestStringField1(MODEL_SON_PROPERTY1);

        ModelSon modelDB12= new ModelSon();
        modelDB12.setTestStringField1(MODEL_SON_PROPERTY2);

        ModelFather modelFather =new ModelFather();
        modelFather.setTestStringField(MODEL_FATHER_PROPERTY1);
        List<ModelSon> list = new ArrayList<ModelSon>();
        list.add(modelDB11);
        list.add(modelDB12);
        modelFather.setModelTests(list);

        return modelFather;
    }

	@Test
	public void wrap() {
		WrapperFather modelTest=new WrapperFather();
		modelTest.wrap(buildModel());
		assertTrue(modelTest.getTestStringField().equals(MODEL_FATHER_PROPERTY1));
        assertTrue(modelTest.getModelTests1().size()==MODEL_FATHER_LISTSIZE);
	}

}
