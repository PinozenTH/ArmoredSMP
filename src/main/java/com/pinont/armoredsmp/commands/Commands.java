package com.pinont.armoredsmp.commands;

import com.pinont.piXLib.commands.PiXCommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Commands extends PiXCommandHandler {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        sender.sendMessage("Hello, world!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return List.of();
    }
}
