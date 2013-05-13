package org.ddelizia.vcrud.core.test.model;

import org.ddelizia.vcrud.core.annotation.VcrudDynamicValue;
import org.ddelizia.vcrud.core.annotation.VcrudItem;
import org.ddelizia.vcrud.core.annotation.VcrudProperty;
import org.ddelizia.vcrud.core.wrapper.WrapperList;
import org.ddelizia.vcrud.core.wrapper.WrapperUtils;
import org.ddelizia.vcrud.core.wrapper.WrapperableItem;

@VcrudItem
public class WrapperFather extends WrapperableItem<ModelFather> {
	
	@VcrudProperty(showOnCollection=true)
	private String testStringField;
	
	@VcrudProperty
	private WrapperList<WrapperChild> modelTests1;
	
	@VcrudDynamicValue
	public String calculatedValue(){
		return "Hola";
	}

	@Override
	public void wrap(ModelFather source) {
		this.setTestStringField(source.getTestStringField());

        this.setModelTests1(
                WrapperUtils.wrapList(WrapperChild.class, source.getModelTests())
        );
	}

	@Override
	public ModelFather unwrap() {
		ModelFather mdb=new ModelFather();
		return mdb;
	}

	public String getTestStringField() {
		return testStringField;
	}

	public void setTestStringField(String testStringField) {
		this.testStringField = testStringField;
	}

	public WrapperList<WrapperChild> getModelTests1() {
		return modelTests1;
	}

	public void setModelTests1(WrapperList<WrapperChild> modelTests1) {
		this.modelTests1 = modelTests1;
	}

	
	

}
