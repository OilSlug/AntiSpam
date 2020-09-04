package cc.oilslug.antispam.handler;

import cc.oilslug.antispam.AntiSpam;
import cc.oilslug.antispam.data.User;
import cc.oilslug.antispam.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class BukkitEventHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        AntiSpam.getInstance().getUserManager().addUser(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(AntiSpam.getInstance().getUserManager().getData(event.getPlayer()) == null){
            AntiSpam.getInstance().getUserManager().addUser(event.getPlayer());
        }

        final User user = AntiSpam.getInstance().getUserManager().getData(event.getPlayer());

        if((System.currentTimeMillis()-user.lastMessageTime) < 700 && AntiSpam.getInstance().time){
            flag(user, "time");
            event.setCancelled(true);
        }

        if(user.lastMessage != null && AntiSpam.getInstance().constant) {
            int counter = 0;
            String[] lastArgs = user.lastMessage.split(" ");
            for (int i = 0; i < lastArgs.length; i++) {
                if (event.getMessage().contains(lastArgs[i]) && !ChatUtil.isCommonWord(lastArgs[i])) {
                    counter++;
                }
            }
            if (counter > 2) {
                flag(user, "constant");
                event.setCancelled(true);
            }else if (event.getMessage().equalsIgnoreCase(user.lastMessage)) {
                flag(user, "constant");
                event.setCancelled(true);
            }
        }

        user.lastMessageTime = System.currentTimeMillis();
        user.lastMessage = event.getMessage();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(AntiSpam.getInstance().getUserManager().getData(event.getPlayer()) == null){
            AntiSpam.getInstance().getUserManager().addUser(event.getPlayer());
        }

        final User user = AntiSpam.getInstance().getUserManager().getData(event.getPlayer());

        if(System.currentTimeMillis()-user.lastCommandTime < 650 && AntiSpam.getInstance().command){
            flag(user, "command");
        }
        user.lastCommandTime = System.currentTimeMillis();
    }

    public void flag(User user, String check){
        user.offenceLevel++;
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.hasPermission("antispam.alerts")){
                player.sendMessage(ChatUtil.colour(AntiSpam.getInstance().alertMessage.replaceAll("%player%", user.getPlayer().getName()).replaceAll("%check%", check).replaceAll("%verbose%", "" + user.offenceLevel)));
            }
        }
        if(user.offenceLevel > AntiSpam.getInstance().maxVerbose && AntiSpam.getInstance().punishments){
            Bukkit.getScheduler().runTask(AntiSpam.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), AntiSpam.getInstance().banMessage.replaceAll("%player%", user.getPlayer().getName()));
                }
            });
        }
    }

}
