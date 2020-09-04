package cc.oilslug.antispam;

import cc.oilslug.antispam.data.UserManager;
import cc.oilslug.antispam.handler.BukkitEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiSpam extends JavaPlugin {

    private static AntiSpam instance;
    private UserManager userManager;

    @Override
    public void onEnable() {
        instance = this;
        userManager = new UserManager();

        Bukkit.getPluginManager().registerEvents(new BukkitEventHandler(), this);

        saveResource("config.yml", false);
        alertMessage = getConfig().getString("alertMessage");
        banMessage = getConfig().getString("punishments.command");
        maxVerbose = getConfig().getInt("maxVerbose");

        punishments = getConfig().getBoolean("punishments.enabled");
        time = getConfig().getBoolean("checks.time.enabled");
        constant = getConfig().getBoolean("checks.constant.enabled");
        command = getConfig().getBoolean("checks.command.enabled");
    }

    public static AntiSpam getInstance() {
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    //Settings
    public String alertMessage = "";
    public String banMessage = "";
    public int maxVerbose = 10;

    public boolean punishments = true;
    public boolean time = true;
    public boolean constant = true;
    public boolean command = true;


}
