package com.smart.dubai.bookstore.constants;

import java.util.HashMap;
import java.util.Map;

public enum BookType {
    FICTION(15),
    COMIC(0),
    FANTASY(10),
    CRIME(10),
    TECHNOLOGY(0),
    DRAMA(5),
    ROMANCE(20),
    WAR(25),
    OTHERS(30);

    private int discount;
    private static Map<String, Integer> map = new HashMap<>();

    private BookType(int discount) {
        this.discount = discount;
    }

    static {
        for (BookType bookType : BookType.values()) {
            map.put(bookType.name(), bookType.discount);
        }
    }

    public static Integer discountPercentage(String category) {
        return (Integer) map.get(category);
    }

}
