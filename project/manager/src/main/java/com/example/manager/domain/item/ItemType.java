package com.example.manager.domain.item;

public enum ItemType {
    HATE("HATE"), LIKE("LIKE");

    private final String symbol;

    ItemType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
