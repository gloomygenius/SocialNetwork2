package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class User {
    private final long id;
    private final String email;
    private final String password;
    private final String first_name;
    private final String last_name;
}