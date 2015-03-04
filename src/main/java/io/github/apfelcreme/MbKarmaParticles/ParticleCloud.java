package io.github.apfelcreme.MbKarmaParticles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishPlugin;

public class ParticleCloud {
	
	private Effect effect;
	private Player owner;
	private long effectDelay;
	private Boolean isVisible;
	
	private int taskId;
	private Location location;

	public ParticleCloud(Effect effect, Player owner, long effectDelay, Boolean isVisible) {
		this.effect = effect;
		this.owner = owner;
		this.effectDelay = effectDelay;
		this.isVisible = isVisible;
	}
	
	/**
	 * starts the effect
	 */
	public void apply() {
		final VanishPlugin vanishPlugin = MbKarmaParticles.getInstance().getPluginVanishNoPacket();
		if (effect == null) {
			return;
		}
		taskId = MbKarmaParticles.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(MbKarmaParticles.getInstance(), new Runnable() {
			public void run() {
				if (isVisible) {
					if (vanishPlugin != null) { 
						if (vanishPlugin.getManager().isVanished(owner)) {
							return;
						}
					}
					int data = 0;
					
					if (effect.equals(Effect.NOTE)) {
						data = (int)(Math.random()*24);
					}
					if (effect.equals(Effect.COLOURED_DUST)) {
						data = (int)(Math.random()*15);
					}
					if (effect.equals(Effect.PORTAL)) {
						// makes them move instead of just fall down
						data = 1;
					}
					owner.getWorld().spigot().playEffect(
							owner.getLocation(), effect, 0, 0, 
							(float)(-1+Math.random()*2), 
							(float)(Math.random()*2), 
							(float)(-1+Math.random()*2)
							, data, 1, 50
					);
				}
				location = owner.getLocation();
			}}, 0, effectDelay);
	}
	
	/**
	 * removes the particle cloud
	 */
	public void remove() {
		MbKarmaParticles.getInstance().getServer().getScheduler().cancelTask(taskId);
		MbKarmaParticles.getInstance().getParticleClouds().remove(this);
	}
	
	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the effectDelay
	 */
	public long getEffectDelay() {
		return effectDelay;
	}

	/**
	 * @param effectDelay the effectDelay to set
	 */
	public void setEffectDelay(long effectDelay) {
		this.effectDelay = effectDelay;
	}

	/**
	 * @return the isVisible
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
