package me.wrexbg.glowutils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.wrexbg.glowutils.listeners.MetadataPacketListener;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class GlowUtilsPlugin extends JavaPlugin {
    private ProtocolManager protocolManager;
    private GlowEntriesManager glowEntriesManager;

    @Override
    public void onEnable() {
        setProtocolManager(ProtocolLibrary.getProtocolManager());
        setGlowEntriesManager(new GlowEntriesManager());
        GlowUtilsAPI.setGlowUtilsPlugin(this);
        MetadataPacketListener metadataPacketListener = new MetadataPacketListener();
        metadataPacketListener.addPacketListener(this, getProtocolManager());
    }

    protected ProtocolManager getProtocolManager() {
        return protocolManager;
    }
    protected void setProtocolManager(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }
    protected GlowEntriesManager getGlowEntriesManager() {
        return glowEntriesManager;
    }
    protected void setGlowEntriesManager(GlowEntriesManager glowEntriesManager) {
        this.glowEntriesManager = glowEntriesManager;
    }

    protected boolean makeGlow(Player glower, Player recipient) {
        PacketContainer metadataPacket = getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
        WrappedDataWatcher watcher = new WrappedDataWatcher(glower);
        byte byteMask =  (byte) (GlowUtilsAPI.getPlayerByteMask(glower) | (byte) (0x40));
        watcher.setObject(
                new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)),
                byteMask
        );
        metadataPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        metadataPacket.getIntegers().write(0, glower.getEntityId());
        try {
            getProtocolManager().sendServerPacket(recipient, metadataPacket);
            return getGlowEntriesManager().addEntry(new GlowerRecipientRecord(glower, recipient));
        } catch (InvocationTargetException exception) {
            getLogger().warning(String.format("Failed to send metadata packet to %s | ID: %d while sending glow", recipient.getName(), recipient.getEntityId()));
            exception.printStackTrace();
            return false;
        }
    }

    protected boolean makeGlow(Set<Player> glowers, Set<Player> recipients) {
        boolean result = true;
        for(Player glower : glowers) {
            for (Player recipient : recipients) {
                if(!makeGlow(glower, recipient) && result) {
                    result = false;
                }
            }
        }
        return result;
    }

    protected boolean removeGlow(Player glower, Player recipient) {
        PacketContainer metadataPacket = getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
        WrappedDataWatcher watcher = new WrappedDataWatcher(glower);
        byte byteMask = GlowUtilsAPI.getPlayerByteMask(glower);
        watcher.setObject(
                new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)),
                byteMask
        );
        metadataPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        metadataPacket.getIntegers().write(0, glower.getEntityId());
        try {
            boolean result = getGlowEntriesManager().removeEntry(new GlowerRecipientRecord(glower, recipient));
            getProtocolManager().sendServerPacket(recipient, metadataPacket);
            return result;
        } catch (InvocationTargetException exception) {
            getLogger().warning(String.format("Failed to send metadata packet to %s | ID: %d while removing glow", recipient.getName(), recipient.getEntityId()));
            exception.printStackTrace();
            return false;
        }
    }

    protected boolean removeGlow(Set<Player> glowers, Set<Player> recipients) {
        boolean result = true;
        for(Player glower : glowers) {
            for (Player recipient : recipients) {
                if(!removeGlow(glower, recipient) && result) {
                    result = false;
                }
            }
        }
        return result;
    }

    protected boolean isGlowing(Player glower, Player recipient) {
        return getGlowEntriesManager().hasEntry(new GlowerRecipientRecord(glower, recipient));
    }

    protected boolean isGlowing(Set<Player> glowers, Set<Player> recipients) {
        for(Player glower : glowers) {
            for (Player recipient : recipients) {
                if (!this.isGlowing(glower, recipient)) {
                    return false;
                }
            }
        }
        return true;
    }
}