package br.com.itest.domain.handler;

import br.com.itest.domain.Restaurant;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by damianijr on 10/25/16.
 */
@Component
public class RestaurantHandler {

    /**
     * Sort restaurants by name and address.
     *
     * @param restaurants Restaurants to sort.
     * @return Restaurants ordened.
     */
    public List<Restaurant> sortByNameAndAddress(final List<Restaurant> restaurants) {
        final List<Restaurant> copy = new ArrayList<>(restaurants);
        Collections.sort(copy, Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getAddress));
        return copy;
    }
}
