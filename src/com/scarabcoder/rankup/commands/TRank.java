package com.scarabcoder.rankup.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.scarabcoder.rankup.main.PlayerTime;

public class TRank implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] args) {
		if(sender.hasPermission("timerankup.admin")){
			if(args.length > 1){
				Player p = Bukkit.getPlayer(args[1]);
				if(p == null){
					sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.RED + "Player not found/online");
				}else{
					if(args[0].equalsIgnoreCase("get")){
						sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.RESET + p.getName() + " has played " + new PlayerTime(p.getUniqueId().toString()).getTime() + " minutes.");
					}else if(args[0].equalsIgnoreCase("set")){
						if(args.length > 2){
							new PlayerTime(p.getUniqueId().toString()).setTime(Integer.parseInt(args[2]));
							sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.GREEN + "Set " + p.getName() + "'s minutes to " + args[2]);
						}else{
							sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.RED + "Format: /trank <get/set/add> <user> [amount]");
							
						}
					}else if(args[0].equalsIgnoreCase("add")){
						if(args.length > 2){
							new PlayerTime(p.getUniqueId().toString()).addTime(Integer.parseInt(args[2]));
							sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.GREEN + "Set " + p.getName() + "'s minutes to " + args[2]);
						}else{
							sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.RED + "Format: /trank <get/set/add> <user> [amount]");
							
						}
					}else{
						sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.RED + "Format: /trank <get/set/add> <user> [amount]");
	
					}
				}
			}else {
				sender.sendMessage(ChatColor.AQUA + "[TimeRankup] " + ChatColor.RED + "Format: /trank <get/set/add> <user> [amount]");
			}
		}
		
		return true;
	}
	
	
	
}
