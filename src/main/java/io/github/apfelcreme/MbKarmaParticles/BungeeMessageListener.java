package io.github.apfelcreme.MbKarmaParticles;

import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitTask;
import org.kitteh.vanish.VanishPlugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class BungeeMessageListener implements PluginMessageListener {

	public void onPluginMessageReceived(String channel, Player player,
			byte[] message) {
		if (!channel.equals("Karma")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("EffectChange")) {
			Player changedPlayer = MbKarmaParticles.getInstance().getServer()
					.getPlayer(UUID.fromString(in.readUTF()));
			String effectString = in.readUTF();
			long effectDelay = in.readLong();
			Boolean isVisible = in.readBoolean();
			Effect effect = Effect.valueOf(effectString.toUpperCase());
			createNewParticleCloud(changedPlayer, effect, effectDelay, isVisible);
			
		} else if (subchannel.equals("ToggleParticleVisibility")) {
			Player changedPlayer = MbKarmaParticles.getInstance().getServer()
					.getPlayer(UUID.fromString(in.readUTF()));
			String effectString = in.readUTF();
			long effectDelay = in.readLong();
			Boolean isVisible = in.readBoolean();
			Effect effect = Effect.valueOf(effectString.toUpperCase());
			if (isVisible) {
				createNewParticleCloud(changedPlayer, effect, effectDelay, isVisible);
			} else {
				deleteParticleCloud(changedPlayer);
			}

		}
	}
	
	/**
	 * creates a new {@link ParticleCloud} and a new Display-Task for its Effect if there isnt one yet
	 * @param player
	 * @param effect
	 * @param effectDelay
	 * @param isVisible
	 */
	public static void createNewParticleCloud(Player player, Effect effect,
			long effectDelay, Boolean isVisible) {
		if (isVisible) {
			if (!((VanishPlugin) MbKarmaParticles.getInstance().getServer()
					.getPluginManager().getPlugin("VanishNoPacket"))
					.getManager().isVanished(player)) {
				
				//remove the old particle cloud first if there is one
				ParticleCloud particleCloud = MbKarmaParticles.getInstance().getParticleCloud(player);
				if (particleCloud != null) {
					particleCloud.remove();
				}				
				ParticleGroupTask particleGroupTask = MbKarmaParticles
						.getInstance().getParticleGroupTasks().get(effect);
				
				/*
				 * add a new task for the effect of the particle cloud (if there
				 * isn't one). every effect has its own task that plays the
				 * particles. this is necessary as the effects have different
				 * delays. so the effect "Flames" will be created if a player
				 * changed his particles to Flames. if another player does that
				 * too the ParticleCloud is being played by the same task as
				 * well. to remove a ParticleCloud just remove it from the tasks
				 * ParticleCloud-list
				 */
				if (particleGroupTask == null) {
					particleGroupTask = new ParticleGroupTask(effect);
					BukkitTask task = MbKarmaParticles
							.getInstance()
							.getServer()
							.getScheduler()
							.runTaskTimerAsynchronously(
									MbKarmaParticles.getInstance(),
									particleGroupTask, 0, effectDelay);
					particleGroupTask.setTask(task);
					MbKarmaParticles.getInstance().getParticleGroupTasks()
							.put(effect, particleGroupTask);
				} 
				particleCloud = new ParticleCloud(effect, player,
						effectDelay, particleGroupTask);
				particleGroupTask.getParticleClouds()
						.put(player, particleCloud);
			}
		}
	}
	
	/**
	 * delets the players {@link ParticleCloud}
	 * 
	 * @param player
	 */
	public static void deleteParticleCloud(Player player) {
		ParticleCloud particleCloud = MbKarmaParticles.getInstance()
				.getParticleCloud(player);
		if (particleCloud != null) {
			particleCloud.remove();

			// remove the task if the removed particleCloud was the last one
			if (particleCloud.getParentGroup().getParticleClouds().size() == 0) {
				particleCloud.getParentGroup().getTask().cancel();
				MbKarmaParticles.getInstance().getParticleGroupTasks()
						.remove(particleCloud.getParentGroup().getEffect());
			}

		}
	}

}
