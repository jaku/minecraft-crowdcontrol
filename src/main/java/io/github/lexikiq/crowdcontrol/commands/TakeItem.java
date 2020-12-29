package io.github.lexikiq.crowdcontrol.commands;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import io.github.lexikiq.crowdcontrol.ChatCommand;
import io.github.lexikiq.crowdcontrol.CrowdControl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class TakeItem extends ChatCommand {
    public TakeItem(CrowdControl plugin) {
        super(plugin);
    }

    @Override
    public int getCooldownSeconds() {
        return 60*5;
    }

    @Override
    public @NotNull String getCommand() {
        return "take";
    }

    @Override
    public boolean execute(ChannelMessageEvent event, Collection<? extends Player> players, String... args) {
        if (args.length == 0) {
            return false;
        }
        Material item = GiveItem.getItemByEvent(args);
        if (item == null) {
            return false;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : players) {
                    for (ItemStack itemStack : player.getInventory()) {
                        if (itemStack == null) {
                            continue;
                        }
                        if (itemStack.getType() == item) {
                            itemStack.setAmount(itemStack.getAmount()-1);
                            break;
                        }
                    }
                    player.updateInventory();
                    player.openInventory(player.getInventory());
                }
            }
        }.runTask(plugin);
        return true;
    }
}
