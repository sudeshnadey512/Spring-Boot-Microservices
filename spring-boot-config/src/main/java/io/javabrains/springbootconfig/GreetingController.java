package io.javabrains.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {

    //getting the value of the key which is written in application.properties
    @Value("${my.greeting}")
    private  String greetingMessage;

    //setting the message here
    @Value("Hi static message")
    private  String staticMessage;

    //setting a default value
    @Value("${my.hello: hello value}")
    private  String helloMessage;

    //getting list of values
    @Value("${my.list.values}")
    private List<String> listValue;

    //getting database credential
    @Value("#{${dbValues}}")
    private Map<String,String> dbValues;

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    private Environment environment;

    @GetMapping("/envDetails")
    public String envDetails(){
        return environment.toString();
    }

    @GetMapping("/dbSettings")
    public String dbSettingValues(){
        return (dbSettings.getConnection() + "," + dbSettings.getHost() +"," + dbSettings.getPort());
    }

    @GetMapping("/listValues")
    public String listValues(){
        return (greetingMessage+" "+staticMessage+" "+getHelloMessage()+" "+listValue+"----"+dbValues);
    }

    @GetMapping("/greeting")
    public String greeting(){
        return greetingMessage;
    }

    @GetMapping("/hello")
    public String getHelloMessage(){
        return helloMessage;
    }

    @GetMapping("/staticMessage")
    public String getStaticMessage(){
        return staticMessage;
    }
}
