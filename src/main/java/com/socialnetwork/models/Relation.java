package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@AllArgsConstructor
@Builder
public class Relation {
    private long senderId;
    private Set<Long> idSet;
    private int relationType;
}