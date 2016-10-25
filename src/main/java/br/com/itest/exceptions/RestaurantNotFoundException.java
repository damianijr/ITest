package br.com.itest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/**
 * Created by damianijr on 10/25/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long restaurantId) {
        super("Restaurant [" + restaurantId + "] not found.");
    }
}
