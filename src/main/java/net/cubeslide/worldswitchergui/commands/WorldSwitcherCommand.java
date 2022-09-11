package net.cubeslide.worldswitchergui.commands;

import net.cubeslide.worldswitchergui.WorldSwitcherGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WorldSwitcherCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final WorldSwitcherGUI worldSwitcherGUI = WorldSwitcherGUI.getInstance();
        if (!(sender instanceof Player)) {
            worldSwitcherGUI.getLogger().info("This Command can only be used by Players!");
            return true;
        }

        final Configuration configuration = worldSwitcherGUI.getConfig();

        final Player player = (Player) sender;

        if (configuration.getBoolean("WorldSwitcherGUI.checkPermissions") && !player.hasPermission("WorldSwitcherGUI.use")) {
            player.sendMessage(worldSwitcherGUI.getPrefix() + "§cYou don't have Permissions to use this Command!");
            return true;
        }

        final Inventory inventory = Bukkit.createInventory(player, 3 * 9, worldSwitcherGUI.getConfigString("Menu.title"));

        for (World world : Bukkit.getWorlds()) {

            final String worldName = world.getName();
            final int worldPlayerCount = world.getPlayers().size();
            final List<String> itemText = configuration.getStringList("Item.text");

            ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(itemText.get(0).replace("&", "§").replace("%world_name%", worldName).replace("%playercount%", String.valueOf(worldPlayerCount)));

            List<String> tempList = new ArrayList<>();
            for (int i = 1; i < itemText.size(); i++) {
                tempList.add(itemText.get(i).replace("&", "§").replace("%world_name%", worldName).replace("%playercount%", String.valueOf(worldPlayerCount)));
            }
            itemMeta.setLore(tempList);
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
        }

        player.openInventory(inventory);
        return true;
    }
}
