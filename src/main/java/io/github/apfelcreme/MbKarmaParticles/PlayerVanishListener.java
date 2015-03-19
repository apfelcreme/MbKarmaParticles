package io.github.apfelcreme.MbKarmaParticles;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

public class PlayerVanishListener implements Listener {

	@EventHandler
	public void onVanishChange(VanishStatusChangeEvent e) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("PlayerVanished");
			out.writeUTF(e.getPlayer().getUniqueId().toString());
			out.writeBoolean(!e.isVanishing());
		} catch (IOException e1) {
		}
		e.getPlayer().sendPluginMessage(MbKarmaParticles.getInstance(),
				"Karma", b.toByteArray());
		
		//remove the old one
		ParticleCloud particleCloud = MbKarmaParticles.getInstance()
				.getParticleCloud(e.getPlayer());
		if (particleCloud != null) {
			MbKarmaParticles.getInstance().getVanishedPlayers()
					.put(e.getPlayer(), particleCloud);
			particleCloud.remove();
		}

	}
}
