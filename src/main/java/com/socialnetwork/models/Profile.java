package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.util.Map;

@Value
@AllArgsConstructor
public class Profile {
    private final long id;
    private final String telephone;
    private final int gender;
    private final LocalDate birthday;
    private final String country;
    private final String city;
    private final String team;
    private final Map.Entry<String, String> seasons;
}