package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class Profile {
    private final long id;
    private final String telephone;
    private final LocalDate birthday;
    private final String country;
    private final String city;
    private final String university;
    private final int team;
    private final int position;
    private final String about;
}