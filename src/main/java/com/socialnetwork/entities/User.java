package com.socialnetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class User implements Comparable<User>{
    private final long id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final int gender;
    private final int role;

    @Override
    public int compareTo(User user) {
        if (this.firstName.compareTo(user.firstName)!=0) return this.firstName.compareTo(user.firstName);
        if (this.lastName.compareTo(user.lastName)!=0) return this.lastName.compareTo(user.lastName);
        Long id=this.getId();
        return id.compareTo(user.getId());
    }
}