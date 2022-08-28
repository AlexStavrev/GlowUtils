package me.wrexbg.glowutils;

import org.bukkit.entity.Player;

import java.util.*;

class GlowEntriesManager {
    private final Map<Player, Set<Player>> glowEntries;

    protected GlowEntriesManager() {
        glowEntries = new HashMap<>();
    }

    protected boolean addEntry(GlowerRecipientRecord record) {
        Set<Player> glowers;
        if (glowEntries.containsKey(record.recipient())) {
            glowers = glowEntries.get(record.recipient());
            return glowers.add(record.glower());
        } else {
            glowers = new HashSet<>(Collections.singletonList(record.glower()));
            return (glowEntries.put(record.recipient(), glowers) != null);
        }
    }

    protected boolean removeEntry(GlowerRecipientRecord record) {
        if (glowEntries.containsKey(record.recipient())) {
            Set<Player> glowers = glowEntries.get(record.recipient());
            boolean result = glowers.remove(record.glower());
            if (glowers.isEmpty()) {
                glowEntries.remove(record.recipient());
            }
            return result;
        }
        return false;
    }

    protected boolean hasEntry(GlowerRecipientRecord record) {
        if (glowEntries.containsKey(record.recipient())) {
            Set<Player> glowers = glowEntries.get(record.recipient());
            return glowers.contains(record.glower());
        }
        return false;
    }
}
