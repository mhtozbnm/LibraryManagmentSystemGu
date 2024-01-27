package org.librarysystem.model;

import org.librarysystem.model.User;

public class Employee extends User {
    public Employee() {
    }

    public Employee(int id, String name, String uname, String password, String type) {
        super(id, name, uname, password, type);
    }
}
