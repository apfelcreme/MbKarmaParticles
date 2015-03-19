package io.github.apfelcreme.MbKarmaParticles;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class ParticleCloud {
	
	private Effect effect;
	private Player owner;
	private long effectDelay;
	private ParticleGroupTask parentGroup; 
	
	public ParticleCloud(Effect effect, Player owner, long effectDelay, ParticleGroupTask parentGroup) {
		this.effect = effect;
		this.owner = owner;
		this.effectDelay = effectDelay;
		this.parentGroup = parentGroup;
	}
	
	/**
	 * starts the effect
	 */
	public void play() {
//		final VanishPlugin vanishPlugin = MbKarmaParticles.getInstance()
//				.getPluginVanishNoPacket();
		if (effect == null) {
			return;
		}
		int data = 0;

		if (effect.equals(Effect.NOTE)) {
			data = (int) (Math.random() * 24);
		}
		if (effect.equals(Effect.COLOURED_DUST)) {
			data = (int) (Math.random() * 15);
		}
		if (effect.equals(Effect.PORTAL)) {
			// makes them move instead of just fall down
			data = 1;
		}
		owner.getWorld()
				.spigot()
				.playEffect(owner.getLocation(), effect, 0, 0,
						(float) (-1 + Math.random() * 2),
						(float) (Math.random() * 2),
						(float) (-1 + Math.random() * 2), data, 1, 50);
	}
	
	/**
	 * removes the particle cloud
	 */
	public void remove() {
		parentGroup.getParticleClouds().remove(owner);
	}

	/**
	 * @return the effect
	 */
	public Effect getEffect() {
		return effect;
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
	 * @return the parentGroup
	 */
	public ParticleGroupTask getParentGroup() {
		return parentGroup;
	}
	
}
