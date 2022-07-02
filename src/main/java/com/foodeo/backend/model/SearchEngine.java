package com.foodeo.backend.model;




import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class SearchEngine {

    @Id
    String id = "1";



    @Embedded
    Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public SearchEngine() {
        this.root = new Node();
    }

    public void add(String word, Long restaurantId) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (c == ' ') continue;
            if (!cur.child_map.containsKey(c)) {
                cur.child_map.put(c, new Node());
            }
            cur.insertRestaurant(restaurantId);
            cur = cur.child_map.get(c);
        }
        cur.insertRestaurant(restaurantId);
    }

    public List<Long> search(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (c == ' ') continue;
            if (!cur.child_map.containsKey(c)) {
                return null;
            }
            cur = cur.child_map.get(c);
        }
        return new ArrayList<Long>(cur.info_map.keySet());
    }

    public void remove(String word, Long restaurantId) {
        Node cur = root;
        if (word == null) return;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (c == ' ') continue;
            if (!cur.child_map.containsKey(c)) return;
            cur.deleteRestaurant(restaurantId);
            cur = cur.child_map.get(c);
        }
        cur.deleteRestaurant(restaurantId);
    }
}
