package com.in28minutes.rest.webservices.restfulwebservices.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class FilterBean {

    public <T> MappingJacksonValue applyFilter(T objToFilter, String filterId, String... exceptFilters) {

        HashSet<String> exceptFiltersList = new HashSet<>();
        for (String exceptFiltersAux: exceptFilters) {
            exceptFiltersList.add(exceptFiltersAux);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(exceptFiltersList);
        FilterProvider filters = new SimpleFilterProvider().addFilter(filterId, filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(objToFilter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
