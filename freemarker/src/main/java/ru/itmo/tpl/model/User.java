package ru.itmo.tpl.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private final Colour colour;

    public User(long id, String handle, String name, Colour colour) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.colour = colour;
    }

    public long getId() {
        return id;
    }

    public Colour getColor() { return colour; }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }
}
