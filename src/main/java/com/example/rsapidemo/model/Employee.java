package com.example.rsapidemo.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@Getter @Setter
public class Employee implements Serializable {

    public enum Colors {RED, BLUE}

    public static List<Colors> NOCOLORS = new ArrayList<Colors>();

    private static final long serialVersionUID = 1L;
    private @NonNull String id;
    private @NonNull String firstName;
    private @NonNull String lastName;
    private @NonNull String email;
    private /*@AllArgsConstructor.Exclude*/ List<Colors> favoriteColors;
}