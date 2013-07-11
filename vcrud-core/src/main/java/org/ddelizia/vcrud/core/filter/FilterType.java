package org.ddelizia.vcrud.core.filter;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public enum FilterType {

    LESS_THAN("LessThan"," <operator1",1),
    GRATERT_THAN("GreaterThan"," >operator1",1),
    LESS_THAN_OR_EQUAL_TO("LessThanOrEqualTo"," <=operator1",1),
    GRATERT_THAN_OR_EQUAL_TO("LessThanOrEqualTo"," >=operator1",1),
    EQUAL_TO("Equal"," ==operator1",1),
    NOT_EQUAL("NotEqual"," !=operator1",1),
    BETWEEN("Between"," BETWEEN operator1 AND operator2",2);

    private String code;
    private String sqlOperator;
    private int numberOfInputs;

    public static final String INPUT_LABEL = "operator";
    public static final int MAX_PARAMS = 2;

    private FilterType(String code, String sqlOperator, int numberOfInputs) {
        this.code = code;
        this.sqlOperator=sqlOperator;
        this.numberOfInputs=numberOfInputs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSqlOperator() {
        return sqlOperator;
    }

    public void setSqlOperator(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }
}
