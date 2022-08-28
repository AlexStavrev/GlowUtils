package me.wrexbg.glowutils;

import org.bukkit.entity.Player;

import java.util.Set;

public class GlowUtilsAPI {
    private static GlowUtilsPlugin glowUtilsPlugin;

    protected static void setGlowUtilsPlugin(GlowUtilsPlugin plugin) {
        glowUtilsPlugin = plugin;
    }

    public static boolean makeGlow(Player glower, Player recipient) {
        return glowUtilsPlugin.makeGlow(glower, recipient);
    }

    public static boolean removeGlow(Player glower, Player recipient) {
        return glowUtilsPlugin.removeGlow(glower, recipient);
    }

    public static boolean isGlowing(Player glower, Player recipient) {
        return glowUtilsPlugin.isGlowing(glower, recipient);
    }

    public static boolean makeGlow(Set<Player> glowers, Set<Player> recipients) {
        return glowUtilsPlugin.makeGlow(glowers, recipients);
    }

    public static boolean removeGlow(Set<Player> glowers, Set<Player> recipients) {
        return glowUtilsPlugin.removeGlow(glowers, recipients);
    }

    public static boolean isGlowing(Set<Player> glowers, Set<Player> recipients) {
        return glowUtilsPlugin.isGlowing(glowers, recipients);
    }

    public static byte getPlayerByteMask(Player player) {
        byte mask = (byte)
                ((player.getFireTicks() > 0 ? 0x01 : 0x00)
                        | (player.isSneaking() ?      0x02 : 0x00)
                        | (player.isSprinting() ?     0x08 : 0x00)
                        | (player.isSwimming() ?      0x10 : 0x00)
                        | (player.isInvisible() ?     0x20 : 0x00)
                        | (player.isGlowing() ?       0x40 : 0x00)
                        | (player.isGliding() ?       0x80 : 0x00));
        return mask;
    }
}
