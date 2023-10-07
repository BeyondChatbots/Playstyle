package com.wsquarepa.playstyle.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.wsquarepa.playstyle.core.Mode;
import com.wsquarepa.playstyle.core.User;
import com.wsquarepa.playstyle.util.StoreManager;
import com.wsquarepa.playstyle.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@CommandAlias("playstyle|ps")
@CommandPermission("playstyle.use")
public class MainCommand extends BaseCommand {
    private final HashMap<UUID, Long> confirmations = new HashMap<>();

    @Default
    public void onDefault(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
            return;
        }

        // list all available modes
        Collection<Mode> modes = Mode.getModes();

        // send message to player
        Component message = Component.text("Available modes: ", NamedTextColor.GRAY);
        message = message.append(Component.newline());
        message = message.append(Component.text("Hover over a mode to see its description", NamedTextColor.GRAY));
        message = message.append(Component.newline());
        message = message.append(Component.text("Click on a mode to set it", NamedTextColor.GRAY));
        message = message.append(Component.newline());
        message = message.append(Component.newline());

        for (Mode mode : modes) {
            message = message.append(Component.text("- ", NamedTextColor.GRAY))
                    .append(
                            Component.text(mode.getName(),
                                            (mode.equals(StoreManager.getStorage().getUser(player.getUniqueId()).getMode()) ?
                                                    NamedTextColor.AQUA : NamedTextColor.WHITE))
                                    .hoverEvent(mode.getDescription())
                                    .clickEvent(ClickEvent.runCommand("/playstyle set " + mode.getName()))
                    )
                    .append(Component.newline());
        }

        sender.sendMessage(message);
    }

    @Subcommand("set")
    @CommandCompletion("@modes")
    public void onSet(CommandSender sender, Mode mode) {
        // send message to player
        Component message = Component.text("Are you sure?", NamedTextColor.RED)
                        .append(Component.newline())
                        .append(Component.text("This will change your playstyle to ", NamedTextColor.GRAY))
                        .append(Component.text(mode.getName(), NamedTextColor.WHITE))
                        .append(Component.newline())
                        .append(Component.text("You won't be able to change it again for 24 hours.", NamedTextColor.GRAY))
                        .append(Component.newline())
                        .append(Component.newline())
                        .append(Component.text("Click ", NamedTextColor.GRAY))
                        .append(Component.text("here", NamedTextColor.WHITE)
                                .clickEvent(ClickEvent.runCommand("/playstyle confirm " + mode.getName())))
                        .append(Component.text(" to confirm.", NamedTextColor.GRAY));

        confirmations.put(((Player) sender).getUniqueId(), System.currentTimeMillis());

        sender.sendMessage(message);
    }

    @Subcommand("confirm")
    @CommandCompletion("@modes")
    public void onConfirm(CommandSender sender, Mode mode) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
            return;
        }

        if (!confirmations.containsKey(player.getUniqueId())) {
            sender.sendMessage(Component.text("Use this command properly.", NamedTextColor.RED)
                    .append(Component.newline())
                    .append(Component.text("Use ", NamedTextColor.GRAY))
                    .append(Component.text("/playstyle", NamedTextColor.WHITE)
                            .hoverEvent(Component.text("Execute /playstyle", NamedTextColor.GRAY)
                                    .clickEvent(ClickEvent.runCommand("/playstyle")))
                            .append(Component.text(" first.", NamedTextColor.GRAY)))
            );
            return;
        }

        long time = confirmations.get(player.getUniqueId());
        long timeLeftToConfirm = System.currentTimeMillis() - time;
        if (timeLeftToConfirm > 1000 * 60) {
            sender.sendMessage(Component.text("Confirmation expired.", NamedTextColor.RED));
            return;
        }

        confirmations.remove(player.getUniqueId());

        User user = StoreManager.getStorage().getUser(player.getUniqueId());
        if (!user.canChange()) {
            long timeLeft = user.getNextChange() - System.currentTimeMillis();
            String formattedTimeLeft = Util.timeToString(timeLeft);

            Component message = Component.text("You can't change your playstyle yet.", NamedTextColor.RED)
                    .append(Component.newline())
                    .append(Component.text("You can change it again in ", NamedTextColor.GRAY))
                    .append(Component.text(formattedTimeLeft, NamedTextColor.WHITE))
                    .append(Component.text(".", NamedTextColor.GRAY));

            sender.sendMessage(message);
            return;
        }

        if (mode.equals(user.getMode())) {
            sender.sendMessage(Component.text("You are already in this mode.", NamedTextColor.RED));
            return;
        }

        user.setMode(mode);

        // send message to player
        Component message = Component.text("Your playstyle has been changed to ", NamedTextColor.GRAY)
                .append(Component.text(mode.getName(), NamedTextColor.WHITE))
                .append(Component.text(".", NamedTextColor.GRAY));
        sender.sendMessage(message);
    }
}
