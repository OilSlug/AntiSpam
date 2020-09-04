package cc.oilslug.antispam.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class UserManager {

    private final ArrayList<User> users;

    public UserManager(){
        this.users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(Player player){
        if(getData(player) == null) users.add(new User(player));
    }

    public User getData(Player player){
        for(User user : users){
            if(user.getPlayer().getUniqueId() == player.getUniqueId())return user;
        }
        return null;
    }

}
