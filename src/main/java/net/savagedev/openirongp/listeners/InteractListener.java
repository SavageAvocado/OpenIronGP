package net.savagedev.openirongp.listeners;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class InteractListener implements Listener {
    private final DataStore gpDataStore = GriefPrevention.instance.dataStore;

    @EventHandler
    public void on(final PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        final Block block = e.getClickedBlock();
        if (block == null || !this.isValid(block)) {
            return;
        }

        final Claim claim = this.gpDataStore.getClaimAt(block.getLocation(), false, null);
        if (claim == null) {
            return;
        }

        final Player player = e.getPlayer();
        if (claim.allowBreak(player, block.getType()) != null) {
            return;
        }

        final Openable openable = (Openable) block.getBlockData();
        final Location location = player.getLocation();

        if (openable.isOpen()) {
            location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
            openable.setOpen(false);
        } else {
            location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
            openable.setOpen(true);
        }
    }

    private boolean isValid(final Block block) {
        final Material material = block.getType();
        if (material == Material.IRON_DOOR || material == Material.IRON_TRAPDOOR) {
            return block.getBlockData() instanceof Openable;
        }
        return false;
    }
}
