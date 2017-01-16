package com.scarabcoder.rankup.loops;

import java.io.IOException;
import java.util.Iterator;

import net.md_5.bungee.api.ChatColor;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scarabcoder.rankup.main.Main;
import com.scarabcoder.rankup.main.PlayerTime;

public class MinuteLoop implements Runnable{

	@Override
	public void run() {
		JsonParser parser = new JsonParser();
		try {
			String data = FileUtils.readFileToString(Main.cfgFile);
			JsonObject obj = parser.parse(data).getAsJsonObject();
			JsonArray perms = obj.get("perms").getAsJsonArray();
			

			for(Player p : Bukkit.getServer().getOnlinePlayers()){
				PlayerTime time = new PlayerTime(p.getUniqueId().toString());
				time.addTime(1);
				PermissionUser user = PermissionsEx.getUser(p);
				Iterator<JsonElement> it = perms.iterator();
				int x = 0;
				while(it.hasNext()){
					x++;
					JsonObject permData = it.next().getAsJsonObject();
					if(user.has(permData.get("perm").getAsString())){
						if(time.getTime() >= permData.get("time").getAsInt()){
							JsonArray addPerms = permData.get("addPerms").getAsJsonArray();
							Iterator<JsonElement> permIt = addPerms.iterator();
							while(permIt.hasNext()){
								String perm = permIt.next().getAsString();
								user.addPermission(perm);
							}
							
							JsonArray rmPerms = permData.get("removePerms").getAsJsonArray();
							Iterator<JsonElement> rmIt = rmPerms.iterator();
							while(rmIt.hasNext()){
								String perm = rmIt.next().getAsString();
								user.removePermission(perm);
							}
							if(!time.getAchieved().contains(x)){
								System.out.println("[TimeRankup] " + p.getName() + " has achieved a time rank.");
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', permData.get("message").getAsString()));
								time.addAchieved(x);
							}
							
						}
					}
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
