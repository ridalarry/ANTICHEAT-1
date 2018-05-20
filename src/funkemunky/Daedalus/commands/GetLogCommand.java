package funkemunky.Daedalus.commands;

import java.util.List;
import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import funkemunky.Daedalus.Daedalus;
import funkemunky.Daedalus.utils.C;
import org.apache.commons.io.FileUtils;

public class GetLogCommand implements CommandExecutor {

	private Daedalus Daedalus;

	public GetLogCommand(Daedalus Daedalus) {
		this.Daedalus = Daedalus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("daedalus.log") && !sender.hasPermission("daedalus.admin")
				&& !sender.getName().equalsIgnoreCase("funkemunky")) {
			sender.sendMessage(C.Red + "No permission.");
			return true;
		}

		if (args.length != 2) {
			sender.sendMessage(Daedalus.PREFIX + C.Red + "Usage: /getlog <name> <page>");
			return true;
		}

		String player = args[0];
		int page = Integer.parseInt(args[1]);
		String path = Daedalus.getDataFolder() + File.separator + "logs" + File.separator + args[0] + ".txt";
		File file = new File(path);
		if (!file.exists()) {
			sender.sendMessage(Daedalus.PREFIX + C.Red + "The player '" + C.Bold + player + C.Red
					+ "' does not have a ban log! This is CASE SENSITIVE!");
			return true;
		}
		try {
			@SuppressWarnings("deprecation")
			List<String> lines = FileUtils.readLines(file);
			if ((lines.size() / (page * 10)) < 1) {
				sender.sendMessage(Daedalus.PREFIX + C.Red + "There is no page " + page + " for this log!");
				return true;
			}
			sender.sendMessage(C.DGray + C.Strike + "----------------------------------------------------");
			sender.sendMessage(C.Gray + "Log for " + C.White + player + C.Red + " Page " + page);
			sender.sendMessage("");
			for (int i = (page - 1) * 10; (i) < page * 10; i++) {
				if (i < lines.size()) {
					sender.sendMessage(lines.get(i));
				}
			}
			sender.sendMessage(C.DGray + C.Strike + "----------------------------------------------------");
		} catch (Exception e) {
			sender.sendMessage(
					Daedalus.PREFIX + C.Red + "Unknown error occured when tryin to read file and upload to pastebin!");
			e.printStackTrace();
		}
		return true;
	}

}
