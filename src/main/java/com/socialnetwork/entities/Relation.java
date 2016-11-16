package com.socialnetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;


/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */

@Value
@AllArgsConstructor
@Builder
public class Relation {
    private long senderId;
    private Set<Long> idSet;
    private int relationType;
}