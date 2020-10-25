package com.in28minutes.rest.webservices.restfulwebservices.filter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Static filtering:
 * To ignore a field and dont send this field anymore when calling the api, it could be done as the way below or the @JsonIgnore in
 * in wach field (what I prefer as dont need to hard code any field name.
**/
//@JsonIgnoreProperties(value = {"field1", "field2"})

/**
 * Dinamic filtering:
 * To ignore a field and dont send this field anymore when calling the api, it could be done as the way below as you are applying
 * a filter on each API that uses this class instead of creating a filter that is used for each API (what sometimes is not a good approach)
 **/
@JsonFilter("SomeBeanFilter")
public class SomeBean {

    private String field1;
    private String field2;

    //@JsonIgnore
    private String field3;

    public SomeBean(String field1, String field2, String field3) {
        super();
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }
}
