package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    //@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World Bean");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello world, %s", name));
    }

    /*
    //Pass Accept-Language to every method? is that a good practice?
    //Nope, so, this is why I changed to the method bellow without the Locale Param

    @GetMapping(path = "/hello-world-i18n")
    public String helloWorldI18n(@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return messageSource.getMessage("good.morning.message", null, locale);
    }*/

    //TODO use LocalecontextHolder in each part of my code, is that a good practice?
    //Maybe it is, maybe is not. What I believe can do is create a method that we can send the message ("good.morning.message") and get back the "formatted" message with locale then we send it back to client.
    @GetMapping(path = "/hello-world-i18n")
    public String helloWorldI18n(){
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

}
