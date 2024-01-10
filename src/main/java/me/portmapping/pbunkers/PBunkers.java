package me.portmapping.pbunkers;

import lombok.Getter;
import me.portmapping.pbunkers.clients.ClientHook;
import me.portmapping.pbunkers.commands.ArenaCommand;
import me.portmapping.pbunkers.commands.MatchCommand;
import me.portmapping.pbunkers.commands.TeamCommand;
import me.portmapping.pbunkers.database.MongoHandler;
import me.portmapping.pbunkers.holograms.PlaceholderManager;
import me.portmapping.pbunkers.listeners.EntityListeners;
import me.portmapping.pbunkers.listeners.MatchListeners;
import me.portmapping.pbunkers.listeners.PlayerListeners;
import me.portmapping.pbunkers.managers.*;
import me.portmapping.pbunkers.providers.board.ScoreboardProvider;
import me.portmapping.pbunkers.providers.tab.TablistProvider;
import me.portmapping.pbunkers.scoreboard.Aether;
import me.portmapping.pbunkers.tablist.PBunkersTablist;
import me.portmapping.pbunkers.timer.TimerManager;
import me.portmapping.pbunkers.utils.TablistConfig;
import me.portmapping.pbunkers.utils.extra.Cooldown;
import me.portmapping.pbunkers.utils.file.FileConfig;
import me.portmapping.pbunkers.utils.menu.ButtonListener;
import me.portmapping.pbunkers.waypoints.WaypointManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;



@Getter

public final class PBunkers extends JavaPlugin {


    @Getter private static PBunkers instance;

    private List<Cooldown> cooldowns; // used for clearing cache/mem

    private MatchManager matchManager;
    private PlayerManager playerManager;
    private ArenaManager arenaManager;
    private TeamManager teamManager;
    private LobbyManager lobbyManager;
    private WaypointManager waypointManager;
    private StoreManager storeManager;

    private TimerManager timerManager;
    private PBunkersTablist bunkersTablist;

    private Aether aether;
    private FileConfig arenaConfig, settingsConfig, tablistConfig;


    private ClientHook clientHook;
    private MongoHandler mongoHandler;


    @Override
    public void onEnable() {
        instance = this;
        this.cooldowns = new ArrayList<>();
        this.arenaConfig = new FileConfig(this,"arenas.yml");
        this.settingsConfig = new FileConfig(this,"settings.yml");
        this.tablistConfig = new FileConfig(this,"tablist.yml");
        this.bunkersTablist = new PBunkersTablist(this,new TablistProvider(),0,5);
        this.playerManager = new PlayerManager(this);
        this.arenaManager = new ArenaManager(this);
        this.teamManager = new TeamManager(this);
        this.lobbyManager = new LobbyManager(this);
        this.matchManager = new MatchManager(this);
        this.storeManager = new StoreManager(this);
        this.timerManager = new TimerManager(this);
        this.waypointManager = new WaypointManager(this);
        this.aether = new Aether(this,new ScoreboardProvider(this));
        this.clientHook = new ClientHook(this);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"gamerule randomTickSpeed 550");




        getCommand("match").setExecutor(new MatchCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("faction").setExecutor(new TeamCommand());

        getServer().getPluginManager().registerEvents(new PlayerListeners(),this);
        getServer().getPluginManager().registerEvents(new ButtonListener(), this);
        getServer().getPluginManager().registerEvents(new MatchListeners(),this);
        getServer().getPluginManager().registerEvents(new EntityListeners(),this);



        TablistConfig.load(this.tablistConfig);

        new PlaceholderManager(this);


    }


    @Override
    public void onDisable() {
        this.arenaManager.saveAll();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kill @e[type=Villager]");

    }


}
