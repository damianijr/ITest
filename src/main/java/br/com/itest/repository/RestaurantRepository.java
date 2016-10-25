package br.com.itest.repository;

import br.com.itest.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by damianijr on 10/25/16.
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

    Optional<Restaurant> findById(Long id);

    Optional<Restaurant> findByNameAndAddress(String name, String address);
}
