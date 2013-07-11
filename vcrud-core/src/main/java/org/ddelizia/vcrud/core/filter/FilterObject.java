package org.ddelizia.vcrud.core.filter;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 11/07/13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class FilterObject {

    private String field;
    private Class aClass;
    private FilterType searchType;
    private Object[] input;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public FilterType getSearchType() {
        return searchType;
    }

    public void setSearchType(FilterType searchType) {
        this.searchType = searchType;
    }

    public Object[] getInput() {
        return input;
    }

    public void setInput(Object[] input) {
        this.input = input;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
