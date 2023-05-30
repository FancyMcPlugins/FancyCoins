package de.oliver.fancyeconomy.commands;

import de.oliver.fancyeconomy.FancyEconomy;
import de.oliver.fancyeconomy.currencies.Currency;
import de.oliver.fancyeconomy.currencies.CurrencyPlayerManager;
import de.oliver.fancyeconomy.currencies.CurrencyRegistry;
import de.oliver.fancylib.MessageHelper;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.command.CommandSender;

@Command("fancyeconomy")
@Permission("fancyeconomy.admin")
public class FancyEconomyCMD {

    @Default
    public static void info(CommandSender player) {
        MessageHelper.info(player, " --- FancyEconomy Info ---");
        MessageHelper.info(player, "/FancyEconomy reload - plugin config reload");
        MessageHelper.info(player, "/FancyEconomy version - checks for a new version of the plugin");
        MessageHelper.info(player, "/FancyEconomy currencies - shows a list of all currencies");
    }

    @Subcommand("version")
    @Permission("fancyeconomy.admin")
    public static void version(CommandSender player) {
        MessageHelper.info(player, "<i>Checking version, please wait...</i>");
        FancyEconomy.getInstance().getScheduler().runTaskAsynchronously(() -> {
            ComparableVersion newestVersion = FancyEconomy.getInstance().getVersionFetcher().getNewestVersion();
            ComparableVersion currentVersion = new ComparableVersion(FancyEconomy.getInstance().getDescription().getVersion());
            if (newestVersion == null) {
                MessageHelper.error(player, "Could not find latest version");
            } else if (newestVersion.compareTo(currentVersion) > 0) {
                MessageHelper.warning(player, "You are using an outdated version of the FancyEconomy Plugin");
                MessageHelper.warning(player, "[!] Please download the newest version (" + newestVersion + "): <click:open_url:'" + FancyEconomy.getInstance().getVersionFetcher().getDownloadUrl() + "'><u>click here</u></click>");
            } else {
                MessageHelper.success(player, "You are using the latest version of the FancyEconomy Plugin (" + currentVersion + ")");
            }
        });
    }

    @Subcommand("reload")
    @Permission("fancyeconomy.admin")
    public static void reload(CommandSender player) {
        FancyEconomy.getInstance().getFancyEconomyConfig().reload();
        CurrencyPlayerManager.loadPlayersFromDatabase();
        MessageHelper.success(player, "Reloaded the config");
    }

    @Subcommand("currencies")
    @Permission("fancyeconomy.admin")
    public static void currencies(CommandSender player){
        Currency defaultCurrency = CurrencyRegistry.getDefaultCurrency();
        MessageHelper.info(player, "<b>List of all currencies:");
        for (Currency currency : CurrencyRegistry.CURRENCIES) {
            MessageHelper.info(player, " - " + currency.name() + " (" + currency.symbol() + ")" + (currency == defaultCurrency ? " <gray>[default]" : ""));
        }
    }
}
