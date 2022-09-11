package net.cubeslide.worldswitchergui;

import net.cubeslide.worldswitchergui.commands.WorldSwitcherCommand;
import net.cubeslide.worldswitchergui.listener.PlayerEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class WorldSwitcherGUI extends JavaPlugin {

    private static WorldSwitcherGUI instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);

        getCommand("worlds").setExecutor(new WorldSwitcherCommand());

        getConfig().addDefault("Menu.title", "&aWorld&6Switcher&dGUI");
        getConfig().addDefault("Plugin.prefix", "&aWorld&6Switcher&dGUI §7>");
        getConfig().addDefault("Item.text", Arrays.asList("&3%world_name%", "&ePlayers&7: &3%playercount%", "", "&cClick to join"));
        getConfig().addDefault("WorldSwitcherGUI.checkPermissions", true);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static String getPrefix() {
        return getInstance().getConfig().getString("Plugin.prefix").replace("&", "§");
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
