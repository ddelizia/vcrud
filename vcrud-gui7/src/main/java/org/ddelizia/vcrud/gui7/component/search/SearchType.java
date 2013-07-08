package org.ddelizia.vcrud.gui7.component.search;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SearchType {

    CONTAINS("contains"),
    EQUALS("equals"),
    GREATER("greater"),
    LESSER("lesser"),
    STARTS_WITH("startswith"),
    ENDS_WITH("endswith");

    private String code;

    private SearchType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
