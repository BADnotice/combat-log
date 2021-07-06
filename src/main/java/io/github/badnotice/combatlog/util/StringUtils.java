package io.github.badnotice.combatlog.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    public static String fixEspaces(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }

}
