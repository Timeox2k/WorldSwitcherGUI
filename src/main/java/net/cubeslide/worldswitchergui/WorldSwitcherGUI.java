package net.cubeslide.worldswitchergui;

import net.cubeslide.worldswitchergui.commands.WorldSwitcherCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class WorldSwitcherGUI extends JavaPlugin implements Listener {

    private static WorldSwitcherGUI instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(this, this);

        getCommand("worlds").setExecutor(new WorldSwitcherCommand());

        getConfig().addDefault("Menu.title", "&aWorld&6Switcher&dGUI");
        getConfig().addDefault("Plugin.prefix", "&aWorld&6Switcher&dGUI §7>");
        getConfig().addDefault("Item.text", Arrays.asList("&3%world_name%", "&ePlayers&7: &3%playercount%", "", "&cClick to join"));
        getConfig().addDefault("WorldSwitcherGUI.checkPermissions", true);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        if (!event.getView().getTitle().equalsIgnoreCase(getConfigString("Menu.title"))) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        String worldName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName().replace(getConfig().getStringList("Item.text").get(0), ""));

        final Player player = (Player) event.getWhoClicked();

        player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        player.sendMessage(WorldSwitcherGUI.getPrefix() + " §aYou have been teleported to the World: §2" + worldName);
        event.setCancelled(true);
        player.closeInventory();
    }

    public static String getPrefix() {
        return getConfigString("Plugin.prefix");
    }

    public static String getConfigString(String path) {
        try {
            return getInstance().getConfig().getString(path).replace("&", "§");
        } catch (Exception exception) {
            return "null";
        }
    }


    public static WorldSwitcherGUI getInstance() {
        return instance;
    }

}
