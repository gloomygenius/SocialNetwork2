package com.socialnetwork.models;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@AllArgsConstructor
public class Dialog implements Comparable<Dialog> {
    private final long id;
    private final long creator;
    private String description;
    private Set<Long> participants;
    private boolean isPrivate;
    private LocalDateTime lastUpdate;

    @Override
    public int compareTo(Dialog compareDialog) {
        if (this.getLastUpdate().isAfter(compareDialog.getLastUpdate())) return -1;
        else return 1;
    }

    public long getRecipient(long sender) {
        if (sender == creator) {
            long recipient = 0;
            for (Long participant : participants) {
                recipient = participant;
            }
            return recipient;
        } else return creator;
    }
}