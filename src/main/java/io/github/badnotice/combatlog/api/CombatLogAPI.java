package io.github.badnotice.combatlog.api;

import com.google.common.collect.ImmutableSet;
import io.github.badnotice.combatlog.CombatLogPlugin;
import io.github.badnotice.combatlog.manager.CombatManager;
import io.github.badnotice.combatlog.model.Combat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CombatLogAPI {

    @Getter
    public static final CombatLogAPI instance = new CombatLogAPI();

    /**
     * Search id a player is in combat.
     *
     * @param name of player
     * @return {@link boolean} is it or not
     */
    public boolean isInCombat(String name) {
        CombatManager combatManager = CombatLogPlugin.getInstance().getCombatManager();

        Combat combat = combatManager.findCombat(name).orElse(null);
        return combat != null;
    }

    /**
     * Remove combat.
     *
     * @param combat of combat
     */
    public void removeCombat(Combat combat) {
        CombatManager combatManager = CombatLogPlugin.getInstance().getCombatManager();
        combatManager.removeCombat(combat);
    }

    /**
     * Search all combats to look one with the entered id.
     *
     * @param name of player
     * @return {@link Optional} with the combat found
     */
    public Optional<Combat> findCombatById(String name) {
        CombatManager combatManager = CombatLogPlugin.getInstance().getCombatManager();
        return combatManager.findCombat(name);
    }

    /**
     * Retrieve all combats registered.
     *
     * @return {@link Set} with combats
     */
    public Set<Combat> allCombats() {
        CombatManager combatManager = CombatLogPlugin.getInstance().getCombatManager();
        return ImmutableSet.copyOf(combatManager.getAllCombats().values());
    }

}
