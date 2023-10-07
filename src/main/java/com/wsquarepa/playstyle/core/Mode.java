package com.wsquarepa.playstyle.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Arrays;
import java.util.Collection;

public enum Mode {
    DIFFICULT (
            Component.text("DIFFICULT", NamedTextColor.RED)
                    .append(Component.newline())
                    .append(Component.text("For the ones who want an extra challenge", NamedTextColor.GRAY))
                    .append(Component.newline())
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Receive 1.5x more damage", NamedTextColor.RED))
    ),
    NORMAL (
            Component.text("NORMAL", NamedTextColor.YELLOW)
                    .append(Component.newline())
                    .append(Component.text("For the ones who want to play the game as it is", NamedTextColor.GRAY))
                    .append(Component.newline())
                    .append(Component.newline())
                    .append(Component.text("This playstyle changes nothing. ", NamedTextColor.GRAY))
    ),
    RETAIN (
            Component.text("RETAIN", NamedTextColor.GREEN)
                    .append(Component.newline())
                    .append(Component.text("For the ones who want to keep their items on death", NamedTextColor.GRAY))
                    .append(Component.newline())
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Keep your items on death (to non-players)", NamedTextColor.GREEN))
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Keep your experience on death (to non-players)", NamedTextColor.GREEN))
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Cannot attack other players, but other players can attack you", NamedTextColor.RED))
    ),
    PEACEFUL (
            Component.text("PEACEFUL", NamedTextColor.AQUA)
                    .append(Component.newline())
                    .append(Component.text("For the ones who like peaceful mode", NamedTextColor.GRAY))
                    .append(Component.newline())
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Monsters won't target you", NamedTextColor.AQUA))
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Keep your items on death", NamedTextColor.AQUA))
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Keep your experience on death", NamedTextColor.AQUA))
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("PvP & PvE disabled", NamedTextColor.RED))
                    .append(Component.newline())
                    .append(Component.text("- ", NamedTextColor.GRAY))
                    .append(Component.text("Always glowing", NamedTextColor.RED))
    )
    ;

    public final Component description;

    Mode(Component desc) {
        this.description = desc;
    }

    public static Collection<String> getNames() {
        return Arrays.stream(Mode.values()).map(Enum::name).toList();
    }
    public static Collection<Mode> getModes() {
        return Arrays.stream(Mode.values()).toList();
    }

    public String getName() {
        return name();
    }

    public Component getDescription() {
        return description;
    }
}
