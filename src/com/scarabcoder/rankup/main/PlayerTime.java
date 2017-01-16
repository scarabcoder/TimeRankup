package com.scarabcoder.rankup.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class PlayerTime {
	
	private String id;
	
	
	public PlayerTime(String id){
		this.id = id;
		JsonObject obj = Main.getPlayerData();
		if(obj.get(id) == null){
			JsonObject player = new JsonObject();
			player.addProperty("time", 0);
			obj.add(id, player);
			obj.get(id).getAsJsonObject().add("achieved", new JsonArray());
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				FileUtils.writeStringToFile(new File("playerData.json"), gson.toJson(obj));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addAchieved(int x){
		JsonObject obj = Main.getPlayerData();
		JsonObject pData = obj.get(id).getAsJsonObject();
		JsonArray achieved = pData.get("achieved").getAsJsonArray();
		achieved.add(new JsonPrimitive(x));
		pData.add("achieved", achieved);
		System.out.println(pData.toString());
		obj.add(id, pData);
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileUtils.writeStringToFile(new File("playerData.json"), gson.toJson(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Integer> getAchieved(){
		List<Integer> ints = new ArrayList<Integer>();
		JsonObject pData = Main.getPlayerData().get(id).getAsJsonObject();
		Iterator<JsonElement> achieved = pData.get("achieved").getAsJsonArray().iterator();
		while(achieved.hasNext()){
			ints.add(achieved.next().getAsInt());
		}
		return ints;
	}
	
	public int getTime(){
		
		System.out.println(Main.getPlayerData().toString());
			
		return Main.getPlayerData()
				.get(id)
				.getAsJsonObject()
				.get("time")
				.getAsInt();
		
	}
	
	public void addTime(int minutes){
		setTime(getTime() + minutes);
	}
	
	public void setTime(int minutes){
		JsonObject obj = Main.getPlayerData();
		obj.get(id).getAsJsonObject().addProperty("time", minutes);
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileUtils.writeStringToFile(new File("playerData.json"), gson.toJson(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
