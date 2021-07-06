package io.github.badnotice.combatlog.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public abstract class EventWrapper extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}

