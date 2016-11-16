package com.socialnetwork.dao.enums;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
public enum RelationType {
    NEUTRAL,
    REQUEST,
    INCOMING,
    FRIEND;
    public static RelationType getType(int type){
        if (type==0) return NEUTRAL;
        if (type==1) return REQUEST;
        if (type==2) return INCOMING;
        if (type==3) return FRIEND;
        return NEUTRAL;
    }
}