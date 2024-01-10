package me.portmapping.pbunkers.utils;

import me.portmapping.pbunkers.utils.chat.CC;
import org.bukkit.Bukkit;

public class Logger {

    public static final String LINE_CONSOLE = CC.translate("&5=========================");

    public static void state(String state, int managers, int teams, int users) {
        print(LINE_CONSOLE);
        print("- &dLoading Azurite HCF...");
        print("- &d" + convert(state) + " &f" + managers + " &dmanagers.");
        print("- &d" + convert(state) + " &f" + teams + " &dteams.");
        print("- &d" + convert(state) + " &f" + users + " &dusers");
        print(LINE_CONSOLE);
        print("- &dAuthor&f: Keqno");
        print("- &dVersion&f: " + Bukkit.getPluginManager().getPlugin("Azurite").getDescription().getVersion());
        print("- &dState&f: " + state);
        print(LINE_CONSOLE);
    }

    public static void print(String... message) {
        for (String s : message) {
            Bukkit.getServer().getConsoleSender().sendMessage(CC.translate(s));
        }
    }

    private static String convert(String string) {
        return string.equalsIgnoreCase("enabled") ? "Loaded" : "Saved";
    }
}