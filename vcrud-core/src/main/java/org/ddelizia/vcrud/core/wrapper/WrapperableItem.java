package org.ddelizia.vcrud.core.wrapper;

import java.lang.reflect.ParameterizedType;


public abstract class WrapperableItem <SOURCE>{
	
	private Class<SOURCE> clazz;
	
	@SuppressWarnings("unchecked")
	public WrapperableItem(){
		this.clazz = (Class<SOURCE>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public abstract void wrap (SOURCE source);
	
	public abstract SOURCE unwrap ();
	
	public Class<SOURCE> getClazz(){
		return clazz;
	}

}
