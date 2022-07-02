package com.foodeo.backend.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Embeddable

public class Node {

    // childMap stores its descendent
    @ElementCollection(targetClass = Long.class)

    Map<Character, Node> child_map = new HashMap<>();
    // infoMap store restaurantId and count
    @ElementCollection(targetClass = Long.class)


    Map<Long, Integer> info_map = new HashMap<>();

    public Node() {
    }

    public void insertRestaurant(Long restaurantId) {
        if (!this.info_map.containsKey(restaurantId)) {
            this.info_map.put(restaurantId, 1);
        } else {
            int count = this.info_map.get(restaurantId);
            this.info_map.put(restaurantId, count + 1);
        }
    }

    public void deleteRestaurant(Long restaurantId) {
        if (this.info_map.containsKey(restaurantId)) {
            int count = this.info_map.get(restaurantId);
            if (count == 1) {
                this.info_map.remove(restaurantId);
            } else {
                this.info_map.put(restaurantId, count - 1);
            }
        }
    }
}
