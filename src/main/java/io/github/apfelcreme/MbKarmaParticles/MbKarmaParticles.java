package io.github.apfelcreme.MbKarmaParticles;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MbKarmaParticles 
 * Copyright (C) 2015 Lord36 aka Apfelcreme
 * 
 * This program is free software;
 * you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Lord36 aka Apfelcreme
 * 
 */
public class MbKarmaParticles extends JavaPlugin {
	
	private HashMap<Effect, ParticleGroupTask> particleGroupTasks;
	private HashMap<Player, ParticleCloud> vanishedPlayers;
	
	public static MbKarmaParticles getInstance() {
		return (MbKarmaParticles) Bukkit.getServer().getPluginManager()
				.getPlugin("MbKarmaParticles");
	}

	@Override
	public void onEnable() {
		particleGroupTasks = new HashMap<Effect, ParticleGroupTask>();
		vanishedPlayers = new HashMap<Player, ParticleCloud>();
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(),
				this);
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "Karma", new BungeeMessageListener());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Karma");
		
		if (getServer().getPluginManager().getPlugin("VanishNoPacket") != null) {
			getServer().getPluginManager().registerEvents(new PlayerVanishListener(), this);
		} else {
			getLogger().severe("Plugin VanishNoPacket nicht gefunden!");
		}
	}
	

	/**
	 * @return the particleClouds
	 */
	public HashMap<Effect, ParticleGroupTask> getParticleGroupTasks() {
		return particleGroupTasks;
	}

	/**
	 * @return the vanishedPlayers
	 */
	public HashMap<Player, ParticleCloud> getVanishedPlayers() {
		return vanishedPlayers;
	}
	
	/**
	 * returns a ParticleCloud attatched to a player if there is one
	 * @param player
	 * @return
	 */
	public ParticleCloud getParticleCloud(Player player) {
		for (Entry<Effect, ParticleGroupTask> e: particleGroupTasks.entrySet()) {
			if (e.getValue().getParticleClouds().containsKey(player)) {
				return e.getValue().getParticleClouds().get(player);
			}
		}
		return null;
	}
}
