package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;


/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */

@Value
@AllArgsConstructor
@Builder
public class Relation {
    long senderId;
    HashMap<Long, Integer> relationMap;
}