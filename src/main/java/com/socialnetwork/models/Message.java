package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class Message implements Comparable<Message> {
    private final long id;
    private final long sender;
    private final long dialog;
    private final String message;
    private final LocalDateTime time;
    private final boolean isRead;

    @Override
    public int compareTo(Message compareMsg) {
        if (this.getTime().isAfter(compareMsg.getTime())) return 1;
        else return -1;
    }
}