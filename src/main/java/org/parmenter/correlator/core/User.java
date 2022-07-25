package org.parmenter.correlator.core;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final String id;
    private final String name;
    private final  String email;
    private final  String role;

    public User(String name, String email, String role){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && name.equals(user.name) && email.equals(user.email) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role);
    }
}
