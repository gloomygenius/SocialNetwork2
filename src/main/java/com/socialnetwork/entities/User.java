package com.socialnetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class User {
    private final long id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final int gender;
    private final int role;
}