package br.com.itest.service;

import br.com.itest.domain.DeliveryArea;
import br.com.itest.domain.Restaurant;
import br.com.itest.exceptions.RestaurantAlreadyExistsException;
import br.com.itest.exceptions.RestaurantNotFoundException;
import br.com.itest.repository.RestaurantCriteriaData;
import br.com.itest.repository.RestaurantLock;
import br.com.itest.repository.RestaurantRepository;
import br.com.itest.repository.RestaurantSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by damianijr on 10/25/16.
 */
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantSpecifications restaurantSpecifications;

    @Autowired
    private RestaurantLock restaurantLock;

    /**
     * Save a new restaurant.
     * @param restaurant New restaurant.
     * @return Restaurant saved.
     */
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * Save a new restaurant thread safe.
     * @param restaurant New restaurant.
     */
    public Restaurant saveThreadSafe(Restaurant restaurant) {
        final String mutex = restaurant.getName() + restaurant.getAddress();
        synchronized (mutex) {
            if (findByNameAndAddress(restaurant.getName(), restaurant.getAddress()).isPresent()) {
                throw new RestaurantAlreadyExistsException(restaurant.getName(), restaurant.getAddress());
            }
            Restaurant saved = save(restaurant);
            return saved;
        }
    }

    /**
     * Save a new delivery area supported by restaurant.
     * @param restaurantId Restaurant ID.
     * @param deliveryArea Delivery area.
     * @return Delivery area saved.
     */
    public DeliveryArea saveDeliveryArea(Long restaurantId, DeliveryArea deliveryArea) {
        Restaurant restaurant = this.restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        restaurant.addDeliveryArea(deliveryArea);
        restaurantRepository.save(restaurant);
        return deliveryArea;
    }

    /**
     * Find restaurant by name and address.
     * @param name Restaurants name.
     * @param address Restaurants address.
     * @return Restaurant founded or null.
     */
    public Optional<Restaurant> findByNameAndAddress(String name, String address) {
        return restaurantRepository.findByNameAndAddress(name, address);
    }

    /**
     * Save a new restaurant prevent concurrent request using redis for pessimist lock.
     * @param restaurant New restaurant.
     * @return Restaurant saved.
     */
    public Restaurant saveDistributedSafe(Restaurant restaurant) {
        // Check if this restaurant already saving
        if (restaurantLock.isLocked(restaurant) || findByNameAndAddress(restaurant.getName(), restaurant.getAddress()).isPresent()) {
            throw new RestaurantAlreadyExistsException(restaurant.getName(), restaurant.getAddress());
        }

        try {
            // Lock restaurant
            restaurantLock.lock(restaurant);

            // Persist
            return this.restaurantRepository.save(restaurant);
        }
        finally {
            // release lock
            restaurantLock.unlock(restaurant);
        }
    }

    /**
     * Find restaurants by criteria.
     * @param criteria Criteria.
     * @return Restaurants founded.
     */
    public Iterable<Restaurant> find(RestaurantCriteriaData criteria) {
        Specification<Restaurant> specification = restaurantSpecifications.whereCEP(criteria.getCep());
        return this.restaurantRepository.findAll(specification);
    }
}
