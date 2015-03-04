package io.github.apfelcreme.MbKarmaParticles;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.VanishPlugin;

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
	
	private VanishPlugin pluginVanishNoPacket;
	
	private HashMap<Player, ParticleCloud> particleClouds;
	
	public static MbKarmaParticles getInstance() {
		return (MbKarmaParticles) Bukkit.getServer().getPluginManager()
				.getPlugin("MbKarmaParticles");
	}

	@Override
	public void onEnable() {
		particleClouds = new HashMap<Player, ParticleCloud>();
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(),
				this);
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "Karma", new BungeeMessageListener());
		if (getServer().getPluginManager().getPlugin("VanishNoPacket") != null) {
			pluginVanishNoPacket = (VanishPlugin) getServer().getPluginManager().getPlugin("VanishNoPacket");
		} 
	}
	
	/**
	 * @return the pluginVanishNoPacket
	 */
	public VanishPlugin getPluginVanishNoPacket() {
		return pluginVanishNoPacket;
	}

	/**
	 * @return the particleClouds
	 */
	public HashMap<Player, ParticleCloud> getParticleClouds() {
		return particleClouds;
	}
}
