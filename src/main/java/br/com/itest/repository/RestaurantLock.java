package br.com.itest.repository;

import br.com.itest.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Lock for restaurant operations.
 */
@Component
public class RestaurantLock {

    /**
     * Timeout for lock.
     */
    public static final int LOCK_TIMEOUT_MILISECONDS = 5000;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Check if the restaurant is locked.
     * @param restaurant Restaurant to check.
     * @return true if restaurant locked, false otherwise.
     */
    public boolean isLocked(Restaurant restaurant) {
        return redisTemplate.opsForValue().get(key(restaurant)) != null;
    }

    /**
     * Lock restaurant.
     * @param restaurant Restaurant to lock.
     */
    public void lock(Restaurant restaurant) {
        redisTemplate.opsForValue().set(key(restaurant), Boolean.TRUE.toString(), LOCK_TIMEOUT_MILISECONDS, TimeUnit.MILLISECONDS);

    }

    /**
     * Unlock restaurant.
     * @param restaurant Restaurant to unlock.
     */
    public void unlock(Restaurant restaurant) {
        redisTemplate.opsForValue().getOperations().delete(key(restaurant));
    }

    /**
     * Build a restaurant key.
     * @param restaurant Restaurant.
     * @return Key.
     */
    private String key(Restaurant restaurant) {
        return restaurant.getName() + restaurant.getAddress();
    }
}
