package io.github.badnotice.combatlog.manager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.github.badnotice.combatlog.model.Combat;

import java.util.Map;
import java.util.Optional;

public final class CombatManager {

    private final Map<String, Combat> combats = Maps.newConcurrentMap();

    public void insertCombat(Combat combat) {
        this.combats.put(combat.getPlayer(), combat);
    }

    public void removeCombat(Combat combat) {
        this.combats.remove(combat.getPlayer());
    }

    public Optional<Combat> findCombat(String playerName) {
        return Optional.ofNullable(this.combats.get(playerName));
    }

    public Map<String, Combat> getAllCombats() {
        return ImmutableMap.copyOf(this.combats);
    }

}
