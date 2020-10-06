package net.savagedev.openirongp;

import net.savagedev.openirongp.listeners.InteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public class OpenIronGP extends JavaPlugin {
    @Override
    public void onEnable() {
        this.initListeners();
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new InteractListener(), this);
    }
}
