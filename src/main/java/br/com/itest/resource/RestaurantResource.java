package br.com.itest.resource;

import br.com.itest.domain.DeliveryArea;
import br.com.itest.domain.Restaurant;
import br.com.itest.exceptions.RestaurantAlreadyExistsException;
import br.com.itest.exceptions.RestaurantNotFoundException;
import br.com.itest.repository.RestaurantCriteriaData;
import br.com.itest.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by damianijr on 10/25/16.
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantResource {

    @Autowired
    private RestaurantService restaurantService;


    /**
     * Endpoint to create a new restaurant.
     * @param restaurant New restaurant.
     * @return Response status and restaurant content.
     */
    @PutMapping
    public ResponseEntity<Restaurant> create(@RequestBody @Valid final Restaurant restaurant) {
        final Restaurant saved = this.restaurantService.save(restaurant);
        return new ResponseEntity<Restaurant>(saved, HttpStatus.CREATED);
    }


    /**
     * Endpoint to create a new restaurant prevent concurrent requests.
     * @param restaurant New restaurant.
     * @return Response status and restaurant content.
     */
    @PutMapping("/sync")
    public ResponseEntity<Restaurant> createSync(@RequestBody @Valid final Restaurant restaurant) {
        final Restaurant saved = this.restaurantService.saveThreadSafe(restaurant);
        return new ResponseEntity<Restaurant>(saved, HttpStatus.CREATED);
    }

    /**
     * Endpoint to create a new restaurant prevent concurrent requests even in a distributed env.
     * @param restaurant New restaurant.
     * @return Response status and restaurant content.
     */
    @PutMapping("/syncDistributed")
    public ResponseEntity<Restaurant> createSyncDistributed(@RequestBody @Valid final Restaurant restaurant) {
        final Restaurant saved = this.restaurantService.saveDistributedSafe(restaurant);
        return new ResponseEntity<Restaurant>(saved, HttpStatus.CREATED);
    }

    /**
     * Endpoint to create a new delivery area in a restaurant.
     * @param restaurantId Restaurant ID.
     * @param deliveryArea Delivery area.
     */
    @PutMapping(value = "{restaurantId}/delivery")
    public ResponseEntity<DeliveryArea> createDeliveryArea(@PathVariable final Long restaurantId, @RequestBody @Valid final DeliveryArea deliveryArea) {
        DeliveryArea saved = restaurantService.saveDeliveryArea(restaurantId, deliveryArea);
        return new ResponseEntity<DeliveryArea>(saved, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all restaurants registered.
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<Restaurant>> get(final RestaurantCriteriaData criteria) {
        Iterable<Restaurant> restaurants = restaurantService.find(criteria);
        return new ResponseEntity<Iterable<Restaurant>>(restaurants ,HttpStatus.OK);
    }
}
