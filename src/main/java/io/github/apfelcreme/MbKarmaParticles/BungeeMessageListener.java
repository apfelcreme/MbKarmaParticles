package io.github.apfelcreme.MbKarmaParticles;

import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

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
			String effect = in.readUTF();
			long effectDelay = in.readLong();
			Boolean isVisible = in.readBoolean();
			if (MbKarmaParticles.getInstance().getParticleClouds().get(changedPlayer) != null) {
				MbKarmaParticles.getInstance().getParticleClouds().get(changedPlayer).remove();
			}
			ParticleCloud particleCloud = new ParticleCloud(Effect.valueOf(effect.toUpperCase()), changedPlayer, effectDelay, isVisible);
			particleCloud.apply();
			MbKarmaParticles.getInstance().getParticleClouds().put(changedPlayer, particleCloud);
		} 
	}
}
