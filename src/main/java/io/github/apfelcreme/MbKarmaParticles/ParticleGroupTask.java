package io.github.apfelcreme.MbKarmaParticles;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ParticleGroupTask implements Runnable {

	private Effect effect;
	private HashMap<Player, ParticleCloud> particleClouds;
	private BukkitTask task;
	
	public ParticleGroupTask(Effect effect) {
		this.effect = effect;
		particleClouds = new HashMap<Player, ParticleCloud>();
	}
	
	public void run() {
		for (Entry<Player, ParticleCloud> entry: particleClouds.entrySet()) {
			entry.getValue().play();
		}
	}

	/**
	 * @return the effect
	 */
	public Effect getEffect() {
		return effect;
	}

	/**
	 * @return the particleClouds
	 */
	public HashMap<Player, ParticleCloud> getParticleClouds() {
		return particleClouds;
	}

	/**
	 * @return the taskId
	 */
	public BukkitTask getTask() {
		return task;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTask(BukkitTask task) {
		this.task = task;
	}

}
