package funkemunky.Daedalus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import funkemunky.Daedalus.Daedalus;
import funkemunky.Daedalus.utils.C;

public class AutobanCommand implements CommandExecutor {
	private Daedalus Daedalus;

	public AutobanCommand(Daedalus Daedalus) {
		this.Daedalus = Daedalus;
	}

	public boolean onCommand(final CommandSender sender, final Command command, final String alias,
			final String[] args) {
		if (!sender.hasPermission("daedalus.staff")) {
			sender.sendMessage(C.Red + "No permission.");
			return true;
		}
		if (args.length == 2) {
			final String type = args[0];
			final String playerName = args[1];
			final Player player = Bukkit.getServer().getPlayer(playerName);
			if (player == null || !player.isOnline()) {
				sender.sendMessage(C.Red + "This player does not exist.");
				return true;
			}
			if (this.Daedalus.getAutobanQueue().contains(player)) {
				@SuppressWarnings("unused")
				String lowerCase;
				switch (lowerCase = type.toLowerCase()) {
				case "cancel": {
					System.out.println("[" + player.getUniqueId().toString() + "] " + sender.getName()
							+ "'s auto-ban has been cancelled by " + sender.getName());
					Bukkit.broadcast(
							ChatColor.translateAlternateColorCodes('&',
									Daedalus.PREFIX + Daedalus.getConfig().getString("alerts.secondary")
											+ player.getName() + Daedalus.getConfig().getString("alerts.primary")
											+ "'s auto-ban has been cancelled by "
											+ Daedalus.getConfig().getString("alerts.secondary") + sender.getName()),
							"daedalus.staff");
					break;
				}
				case "ban": {
					if (this.Daedalus.getConfig().getBoolean("testmode") == true) {
						sender.sendMessage(ChatColor.RED + "Test mode is enabled therefore this is disabled!");
					} else {
						System.out.println("[" + player.getUniqueId().toString() + "] " + sender.getName()
								+ "'s auto-ban has been forced by " + sender.getName());
						Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&',
								Daedalus.PREFIX + Daedalus.getConfig().getString("alerts.secondary") + player.getName()
										+ Daedalus.getConfig().getString("alerts.primary")
										+ "'s auto-ban has been forced by "
										+ Daedalus.getConfig().getString("alerts.secondary") + sender.getName()),
								"daedalus.staff");
						this.Daedalus.autobanOver(player);
					}
					break;
				}
				default:
					break;
				}
				this.Daedalus.removeFromAutobanQueue(player);
				this.Daedalus.removeViolations(player);
			} else {
				sender.sendMessage(String.valueOf(C.Red) + "This player is not in the autoban queue!");
			}
		}
		return true;
	}
}