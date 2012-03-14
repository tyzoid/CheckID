package tk.tyzoid.plugins.CheckID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PListener implements Listener {
	private final CheckID plugin;
	private String pluginname;
	
	PListener(CheckID instance){
		plugin = instance;
		pluginname = plugin.pluginname;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    	String[] split = event.getMessage().split(" ");
    	//String command = event.getMessage();
		Player player = event.getPlayer();
		
		//the /foo command
		String[] checkidCommands = plugin.settings.getProperty("command-CheckID").split(",");
		boolean checkidCommandUsed = false;
		
		if(player.hasPermission("checkid.check") || player.isOp()){
			for(int i = 0; i < checkidCommands.length && !checkidCommandUsed; i++){
					checkidCommandUsed = split[0].equalsIgnoreCase(checkidCommands[i]);
			}
		} else {
			boolean usedCommand = false;
			for(int i = 0; i < checkidCommands.length && !usedCommand; i++){
				usedCommand = split[0].equalsIgnoreCase(checkidCommands[i]);
			}
			if(usedCommand){
				player.sendMessage("§b[" + pluginname + "] §cYou do not have permissions to use that command.");
			}
		}
		
		
		if(checkidCommandUsed){
			int id = player.getItemInHand().getTypeId();
			player.sendMessage("§b[" + pluginname + "] §aYou are currently holding block ID " + id);
			event.setCancelled(true);
			
			return;
		}
	}
}
