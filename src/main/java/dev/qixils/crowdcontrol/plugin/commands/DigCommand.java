package dev.qixils.crowdcontrol.plugin.commands;

import dev.qixils.crowdcontrol.plugin.ChatCommand;
import dev.qixils.crowdcontrol.plugin.CrowdControlPlugin;
import dev.qixils.crowdcontrol.plugin.utils.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DigCommand extends ChatCommand {
    private final static double RADIUS = .5D;
    public DigCommand(CrowdControlPlugin plugin) {
        super(plugin);
    }

    @Override
    public int getCooldownSeconds() {
        return (int) (60*2.5);
    }

    @Override
    public @NotNull String getCommand() {
        return "dig";
    }

    @Override
    public boolean execute(String authorName, List<Player> players, String... args) {
        Set<Block> blocks = new HashSet<>();
        int depth = -(2 + rand.nextInt(4));
        for (Player player : players) {
            for (double x = -RADIUS; x <= RADIUS; ++x) {
                for (int y = depth; y < 0; ++y) {
                    for (double z = -RADIUS; z <= RADIUS; ++z) {
                        Block block = player.getLocation().add(x, y, z).getBlock();
                        if (BlockUtil.STONES_SET.contains(block.getType())) {
                            blocks.add(block);
                        }
                    }
                }
            }
        }
        if (blocks.isEmpty()) {return false;}
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Block block : blocks) {
                    block.setType(Material.AIR);
                }
            }
        }.runTask(plugin);
        return true;
    }
}