package me.portmapping.pbunkers.game.match.events;


import me.portmapping.pbunkers.game.match.Match;

public class MatchStartEvent extends MatchEvent {
    public MatchStartEvent(Match match){
        super(match);
    }
}
