package com.wsquarepa.playstyle.util;

import org.bukkit.entity.EntityType;

public class Util {
    private Util() {
    }

    public static String timeToString(long time) {
        // format to .. days, .. hours, .. minutes, .. seconds

        long seconds = time / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append(" days");
        }

        if (hours > 0) {
            if (days > 0) {
                builder.append(" ");
            }

            builder.append(hours).append(" hours");
        }

        if (minutes > 0) {
            if (days > 0 || hours > 0) {
                builder.append(" ");
            }

            builder.append(minutes).append(" minutes");
        }

        if (seconds > 0) {
            if (days > 0 || hours > 0 || minutes > 0) {
                builder.append(" ");
            }

            builder.append(seconds).append(" seconds");
        }

        return builder.toString();
    }

    public static boolean isPeacefulMob(EntityType type) {
        return type.equals(EntityType.ARMOR_STAND) ||
                type.equals(EntityType.BOAT) ||
                type.equals(EntityType.CHEST_BOAT) ||
                type.equals(EntityType.MINECART) ||
                type.equals(EntityType.MINECART_CHEST) ||
                type.equals(EntityType.MINECART_COMMAND) ||
                type.equals(EntityType.MINECART_FURNACE) ||
                type.equals(EntityType.MINECART_HOPPER) ||
                type.equals(EntityType.MINECART_MOB_SPAWNER) ||
                type.equals(EntityType.MINECART_TNT) ||
                type.equals(EntityType.ITEM_FRAME) ||
                type.equals(EntityType.LEASH_HITCH) ||
                type.equals(EntityType.PAINTING) ||
                type.equals(EntityType.FIREBALL) ||
                type.equals(EntityType.SHULKER_BULLET) ||
                type.equals(EntityType.GLOW_ITEM_FRAME);
    }
}
