package br.com.itest.repository;

import br.com.itest.domain.Restaurant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;

/**
 * Created by damianijr on 10/25/16.
 */
@Component
public class RestaurantSpecifications {

    public Specification<Restaurant> whereCEP(final String cep) {
        if (cep == null || cep.trim().length() <= 0) {
            return null;
        }
        return new Specification<Restaurant>() {
            @Override
            public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path pathArea = root.join("areas").get("cep");
                return cb.equal(pathArea, cep);
            }
        };
    }
}
