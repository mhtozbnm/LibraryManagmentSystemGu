package org.librarysystem;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.librarysystem.model.Employee;
import org.librarysystem.view.EmployeeGui;
import org.librarysystem.view.LoginGui;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        FlatMacLightLaf.setup();
        new LoginGui();
    }
}