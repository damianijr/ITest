package br.com.itest.domain.handler;

import br.com.itest.domain.Restaurant;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damianijr on 10/25/16.
 */
public class RestaurantHandlerTest {


    @Test
    public void testOrderByName() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1L, "Rango 2", "Endereco qualquer"));
        restaurants.add(new Restaurant(2L, "Rango 1", "Endereco qualquer"));

        List<Restaurant> ordered = new RestaurantHandler().sortByNameAndAddress(restaurants);

        Assert.assertEquals(ordered.get(0), restaurants.get(1));
    }

    @Test
    public void testOrderByAddress() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1L, "Rango Qualquer", "Endereco 2"));
        restaurants.add(new Restaurant(2L, "Rango Qualquer", "Endereco 1"));

        List<Restaurant> ordered = new RestaurantHandler().sortByNameAndAddress(restaurants);

        Assert.assertEquals(ordered.get(0), restaurants.get(1));
    }
}
