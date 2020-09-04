package cc.oilslug.antispam.data;

import org.bukkit.entity.Player;

public class User {

    private Player player;

    public int offenceLevel;
    public long lastMessageTime, lastCommandTime;
    public String lastMessage;

    public User(Player player){
        this.player = player;
        this.offenceLevel = 0;
    }

    public Player getPlayer() {
        return player;
    }
}
