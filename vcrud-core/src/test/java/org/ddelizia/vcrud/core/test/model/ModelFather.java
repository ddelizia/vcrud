package org.ddelizia.vcrud.core.test.model;

import java.util.List;

public class ModelFather {
	
	private String testStringField;
	
	private List<ModelSon> modelTests;

	public String getTestStringField() {
		return testStringField;
	}

	public void setTestStringField(String testStringField) {
		this.testStringField = testStringField;
	}

	public List<ModelSon> getModelTests() {
		return modelTests;
	}

	public void setModelTests(List<ModelSon> modelTests) {
		this.modelTests = modelTests;
	}
	

}
