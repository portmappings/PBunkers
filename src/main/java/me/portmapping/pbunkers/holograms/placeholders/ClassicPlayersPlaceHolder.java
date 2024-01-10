package me.portmapping.pbunkers.holograms.placeholders;

import me.filoghost.holographicdisplays.api.placeholder.GlobalPlaceholderReplaceFunction;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.game.match.Match;
import org.jetbrains.annotations.Nullable;

public class ClassicPlayersPlaceHolder implements GlobalPlaceholderReplaceFunction {

    private final PBunkers plugin =  PBunkers.getInstance();

    @Override
    public @Nullable String getReplacement(@Nullable String s) {
        Match match = plugin.getMatchManager().getMatch("Classic");
        if(match == null){
            return String.valueOf(0);
        }else {
            return String.valueOf(match.getPlayerList().size());
        }
    }
}
