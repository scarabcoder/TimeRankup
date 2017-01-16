package com.scarabcoder.rankup.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.scarabcoder.rankup.commands.TRank;
import com.scarabcoder.rankup.loops.MinuteLoop;

public class Main extends JavaPlugin {
	
	public static File cfgFile;
	
	public void onEnable(){
		
		File cfgFile = new File(this.getDataFolder(), "config.json");
		Main.cfgFile = cfgFile;
		if(!cfgFile.exists()){
			this.getDataFolder().mkdirs();
			try {
				cfgFile.createNewFile();
				JsonObject defaultData = new JsonObject();
				JsonArray allData = new JsonArray();
				JsonObject permData = new JsonObject();
			
				permData.addProperty("time", 10);
				JsonArray array = new JsonArray();
				JsonArray rmv = new JsonArray();
				rmv.add(new JsonPrimitive("essentials.tp"));
				array.add(new JsonPrimitive("essentials.fly"));
				permData.addProperty("perm", "group.vip");
				permData.add("addPerms", array);
				permData.add("removePerms", rmv);
				permData.addProperty("message", "&6You've unlocked /fly for playing 10 minutes!");
				allData.add(permData);
				defaultData.add("perms", allData);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				FileUtils.writeStringToFile(cfgFile, gson.toJson(defaultData));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.getCommand("trank").setExecutor(new TRank());
		
		Bukkit.getScheduler().runTaskTimer(this, new MinuteLoop(), 0, 20 * 60);
		
		if(!new File("playerData.json").exists()){
			System.out.println("[TimeRankup] Players data file does not exist, creating now.");
			try {
				new File("playerData.json").createNewFile();
				FileUtils.writeStringToFile(new File("playerData.json"), "{}");
				System.out.println("[TimeRankup] Created data file \"playerData.json\".");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[TimeRankup] There was an error creating the players data file.");
			}
		}
		
		
	}
	
	
	public static JsonObject getPlayerData() {

		JsonParser parser = new JsonParser();
		try {
			JsonObject obj = parser.parse(FileUtils.readFileToString(new File("playerData.json"))).getAsJsonObject();
			return obj;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
