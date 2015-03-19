package io.github.apfelcreme.MbKarmaParticles;

import java.util.Map.Entry;

import org.bukkit.Effect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent e) {

		ParticleGroupTask particleGroupTask = null;
		for (Entry<Effect, ParticleGroupTask> entryTask : MbKarmaParticles
				.getInstance().getParticleGroupTasks().entrySet()) {
			entryTask.getValue().getParticleClouds().remove(e.getPlayer());
			particleGroupTask = entryTask.getValue();
		}
		if (particleGroupTask != null) {
			if (particleGroupTask.getParticleClouds().size() == 0) {
				particleGroupTask.getTask().cancel();
				MbKarmaParticles.getInstance().getParticleGroupTasks().remove(particleGroupTask.getEffect());
			}
		}
	}

}
