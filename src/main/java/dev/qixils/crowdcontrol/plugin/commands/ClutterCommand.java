package dev.qixils.crowdcontrol.plugin.commands;

import dev.qixils.crowdcontrol.plugin.ChatCommand;
import dev.qixils.crowdcontrol.plugin.CrowdControlPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class ClutterCommand extends ChatCommand {
    public ClutterCommand(CrowdControlPlugin plugin) {
        super(plugin);
    }

    @Override
    public int getCooldownSeconds() {
        return 60*2;
    }

    @Override
    public @NotNull String getCommand() {
        return "clutter";
    }

    @Override
    public boolean execute(String authorName, List<Player> players, String... args) {
        // swaps two random items in player's inventory
        for (Player player : players) {
            PlayerInventory inventory = player.getInventory();
            int maxSlots = inventory.getSize();
            Set<Integer> slots = Set.of(inventory.getHeldItemSlot(), rand.nextInt(maxSlots), rand.nextInt(maxSlots));
            for (int slot : slots) {
                ItemStack hand = inventory.getItem(slot);
                // lazy workaround to get a unique slot lmfao
                int destSlot = slot;
                while (destSlot == slot) {
                    destSlot = rand.nextInt(maxSlots);
                }
                ItemStack swap = inventory.getItem(destSlot);
                inventory.setItem(slot, swap);
                inventory.setItem(destSlot, hand);
            }
            player.updateInventory();
        }
        return true;
    }
}