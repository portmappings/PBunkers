package me.portmapping.pbunkers.game.team;

import me.portmapping.pbunkers.utils.chat.CC;

public enum TeamColor {
    RED,BLUE,YELLOW,GREEN;

    public static String getChatColor(TeamColor color){
        switch (color){
            case RED:
                return CC.RED;
            case BLUE:
                return CC.BLUE;
            case YELLOW:
                return CC.YELLOW;
            case GREEN:
                return CC.GREEN;
        }
        return null;
    }
}
