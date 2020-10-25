package com.in28minutes.rest.webservices.restfulwebservices.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    private final FilterBean filterBean;

    public FilteringController(FilterBean filterBean){
        this.filterBean = filterBean;
    }

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        //Part of Dynamic Filter implementation is in the REST API (Controller) instead of the direct in the Bean
        //This could be another class to filter the results instead of direct in controller
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBean() {
        List<SomeBean> someBeans = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value12", "value22", "value32"));
        //Part of Dynamic Filter implementation is in the REST API (Controller) instead of the direct in the Bean
        //This could be another class to filter the results instead of direct in controller
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeans);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    /***************************************************************
     * Below we can find methods that is using the Spring injection
     * to use the same applyFilter method from FilterBean class
     * to execute the filter based on parameters instead of hard code
     * the filter inside controller
     ***************************************************************/

    @GetMapping("/filtering-function")
    public MappingJacksonValue retrieveSomeBeanFunction() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        String[] exceptFilters = new String[] {"field1", "field2"};

        return filterBean.applyFilter(someBean, "SomeBeanFilter", exceptFilters);
    }

    @GetMapping("/filtering-list-function")
    public MappingJacksonValue retrieveListOfSomeBeanFunction() {
        List<SomeBean> someBeans = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value12", "value22", "value32"));
        String[] exceptFilters = new String[] {"field2", "field3"};

        return filterBean.applyFilter(someBeans, "SomeBeanFilter", exceptFilters);
    }
}
