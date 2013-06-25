package org.ddelizia.vcrud.core.test.model;

import org.ddelizia.vcrud.core.annotation.VcrudItem;
import org.ddelizia.vcrud.core.annotation.VcrudProperty;
import org.ddelizia.vcrud.core.wrapper.WrapperableItem;

@VcrudItem
public class WrapperChild extends WrapperableItem<ModelSon>{
	
	@VcrudProperty(showOnCollection=true)
	private String testStringField1;

	@Override
	public void wrap(ModelSon source) {
        this.setTestStringField1(source.getTestStringField1());
	}

	@Override
	public ModelSon unwrap() {
		ModelSon mdb=new ModelSon();
		return mdb;
	}

	public String getTestStringField1() {
		return testStringField1;
	}

	public void setTestStringField1(String testStringField1) {
		this.testStringField1 = testStringField1;
	}
	
	

}
