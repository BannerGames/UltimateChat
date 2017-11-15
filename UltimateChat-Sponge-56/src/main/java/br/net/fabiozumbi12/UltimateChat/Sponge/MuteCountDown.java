package br.net.fabiozumbi12.UltimateChat.Sponge;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class MuteCountDown implements Consumer<Task> {	
	int time;
	String p;
	MuteCountDown(String p, int t){
		this.p = p;
		this.time = t*60;
	}
	
	@Override
	public void accept(Task t) {
		if (UChat.get().timeMute.containsKey(p)) {
			time = UChat.get().timeMute.get(p)-1;
		}
		if (time > 0) {
			UChat.get().timeMute.put(p, time);
		} else {
			UChat.get().timeMute.remove(p);				
			if (UChat.get().mutes.contains(p)){
				UChat.get().mutes.remove(p);
				UChat.get().unMuteInAllChannels(p);
				if (Sponge.getServer().getPlayer(p).isPresent()){
					UChat.get().getLang().sendMessage(Sponge.getServer().getPlayer(p).get(), UChat.get().getLang().get("channel.player.unmuted.all"));
				}
			}			
			t.cancel();
		}
	}
}