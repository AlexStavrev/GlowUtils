package me.wrexbg.glowutils.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.WrappedDataWatcherObject;

import me.wrexbg.glowutils.GlowUtilsAPI;
import me.wrexbg.glowutils.GlowUtilsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MetadataPacketListener {
    public MetadataPacketListener() {
    }

    public void addPacketListener(GlowUtilsPlugin plugin, ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent metadataPacketReceiveEvent) {
                PacketContainer metadataPacket = metadataPacketReceiveEvent.getPacket();
                int playerId = metadataPacket.getIntegers().read(0);
                Player recipient = metadataPacketReceiveEvent.getPlayer();
                Player glower = getPlayerById(playerId);
                if (glower != null && GlowUtilsAPI.isGlowing(glower, recipient)) {
                    WrappedDataWatcher watcher = new WrappedDataWatcher(glower);
                    byte byteMask = (byte) (GlowUtilsAPI.getPlayerByteMask(glower) | (byte) (0x40));
                    watcher.setObject(
                            new WrappedDataWatcherObject(0, Registry.get(Byte.class)),
                            byteMask
                    );
                    metadataPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
                }
            }

            private Player getPlayerById(int entityID) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (player.getEntityId() == entityID) {
                        return player;
                    }
                }
                return null;
            }
        });
    }
}
