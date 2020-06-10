package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    public RestTemplate restTemplate;

    //method to call the ratings data service
    @HystrixCommand(fallbackMethod = "getFallbackUserRating"
                /*,threadPoolKey = "userRatingInfoPool",
                threadPoolProperties = {
                        @HystrixProperty(name="coreSize",value="20"),
                        @HystrixProperty(name="maxQueueSize",value="10"),
                }*/
                /*
                 commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value= "2000"),
                        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value= "5"),
                        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value= "50"),
                        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliSeconds",value= "5000"),

                    }
                 */
    )
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(
                new Rating("0",0)
        ));

        return userRating;

    }
}
