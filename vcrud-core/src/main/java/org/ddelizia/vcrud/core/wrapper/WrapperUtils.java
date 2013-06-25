package org.ddelizia.vcrud.core.wrapper;

import java.util.List;

public class WrapperUtils {

	public static <F extends WrapperableItem, M extends Object> WrapperList wrapList(Class<F> finalClass, List<M> dbModel){
		WrapperList<F> wrapperList=new WrapperList<F>();
		
		for (Object model : dbModel) {
			try {
				F currentItem = finalClass.newInstance();
                currentItem.wrap(model);
                wrapperList.add(currentItem);
				
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		
		return wrapperList;
	}
}
