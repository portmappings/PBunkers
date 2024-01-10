package me.portmapping.pbunkers.holograms;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.portmapping.pbunkers.PBunkers;
import me.portmapping.pbunkers.holograms.placeholders.ClassicPlayersPlaceHolder;

public class PlaceholderManager {

    public PlaceholderManager(PBunkers instance){
        HolographicDisplaysAPI api = HolographicDisplaysAPI.get(instance);
        api.registerGlobalPlaceholder("arena-players-classic",20,new ClassicPlayersPlaceHolder());
    }
}
