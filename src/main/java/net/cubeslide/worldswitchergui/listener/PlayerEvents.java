package net.cubeslide.worldswitchergui.listener;

import net.cubeslide.worldswitchergui.WorldSwitcherGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        final WorldSwitcherGUI worldSwitcherGUI = WorldSwitcherGUI.getInstance();
        if (!event.getView().getTitle().equalsIgnoreCase(worldSwitcherGUI.getConfigString("Menu.title"))) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        String worldName = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName().replace(worldSwitcherGUI.getConfig().getStringList("Item.text").get(0), ""));

        final Player player = (Player) event.getWhoClicked();

        player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        player.sendMessage(WorldSwitcherGUI.getPrefix() + " §aYou have been teleported to the World: §2" + worldName);
        event.setCancelled(true);
        player.closeInventory();
    }

}
