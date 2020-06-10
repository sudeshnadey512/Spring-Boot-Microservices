package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    public RestTemplate restTemplate;

    //method to call the movie info service after getting the value from ratings data service
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem"
                    /*, threadPoolKey = "movieInfoPool",
                    threadPoolProperties = {
                        @HystrixProperty(name="coreSize",value="20"),
                        @HystrixProperty(name="maxQueueSize",value="10"),
                    }*/
                    /*  commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value= "2000"),
                        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value= "6"),
                        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value= "40"),
                        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliSeconds",value= "50000"),

                     }*/
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating){

        return  new CatalogItem("Movie name not found","----",rating.getRating());
    }

}
