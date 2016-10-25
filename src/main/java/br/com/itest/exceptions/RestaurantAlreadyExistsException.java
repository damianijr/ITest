package br.com.itest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by damianijr on 10/25/16.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class RestaurantAlreadyExistsException extends RuntimeException {

    public RestaurantAlreadyExistsException(String name, String address) {
        super("Restaurant " + name + ", at " + address + " already exists");
    }
}
