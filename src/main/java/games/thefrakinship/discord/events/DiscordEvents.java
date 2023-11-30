package games.thefrakinship.discord.events;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import dev.vankka.mcdiscordreserializer.minecraft.MinecraftSerializer;
import games.thefrakinship.discord.TheFrakinDiscordConfig;
import games.thefrakinship.discord.TheFrakinDiscordMod;
import games.thefrakinship.discord.util.DiscordUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class DiscordEvents extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // if message is from null author or self do not process
        if ((event.getMember() == null && !event.isWebhookMessage()) || DiscordUtil.getJda() == null
                || event.getAuthor().equals(DiscordUtil.getJda().getSelfUser()))
            return;

        TextChannel discordChannel = event.getChannel();

        if (StringUtils.isBlank(event.getMessage().getContentRaw()) &&
                event.getMessage().getAttachments().isEmpty() &&
                event.getMessage().getStickers().isEmpty())
            return;
        // if (processPlayerListCommand(event, message)) return;
        // if (processConsoleCommand(event, event.getMessage().getContentRaw())) return;

        if (discordChannel.getId() == TheFrakinDiscordConfig.player_chat_channel_id.get()) {
            onPlayerChatMessageReceived(event);
        } else if (discordChannel.getId() == TheFrakinDiscordConfig.console_channel_id.get()) {
            onConsoleChatMessageReceived(event);
        }
    }

    private void onConsoleChatMessageReceived(GuildMessageReceivedEvent event) {
        // if (TheFrakinDiscordConfig.console_channel_id.get() == "000000000000000000"
        // ||
        // TheFrakinDiscordConfig.console_channel_id.get().isEmpty()) return;
    }

    private void onPlayerChatMessageReceived(GuildMessageReceivedEvent event) {
        if (!TheFrakinDiscordConfig.discord_to_minecraft_enabled.get() ||
                TheFrakinDiscordConfig.player_chat_channel_id.get() == "000000000000000000" ||
                TheFrakinDiscordConfig.player_chat_channel_id.get().isEmpty() ||
                (TheFrakinDiscordConfig.player_chat_block_webhooks.get() && event.isWebhookMessage()) ||
                (TheFrakinDiscordConfig.player_chat_block_bots.get() && event.getAuthor().isBot()
                        && !event.isWebhookMessage()))
            return;

        if (TheFrakinDiscordConfig.player_chat_require_linked_account.get()) {
            // TODO
        }

        if (event.getMember() != null) {
            List<String> authorRoles = event.getMember().getRoles().stream().map(role -> role.getId()).toList();

            Boolean sender_allowed = !TheFrakinDiscordConfig.player_chat_blocked_users.get()
                    .contains(event.getAuthor().getId());
            if (TheFrakinDiscordConfig.player_chat_blocked_users_as_allow_list.get()) {
                sender_allowed = !sender_allowed;
            }

            Boolean sender_role_allowed = !TheFrakinDiscordConfig.player_chat_blocked_roles.get().stream()
                    .anyMatch(id -> authorRoles.contains(id));
            if (TheFrakinDiscordConfig.player_chat_blocked_roles_as_allow_list.get()) {
                sender_role_allowed = !sender_role_allowed;
            }

            if (!(sender_allowed && sender_role_allowed)) {
                event.getMessage().addReaction("❌").queue();
                return;
            }
        }

        String message = event.getMessage().getContentRaw();

        // if there are attachments send them all as one message
        // if (!event.getMessage().getAttachments().isEmpty()) {
        // for (Message.Attachment attachment :
        // event.getMessage().getAttachments().subList(0,
        // Math.min(event.getMessage().getAttachments().size(), 3))) {
        // if (handleMessageAddons(event, preEvent, selectedRoles, topRole,
        // attachment.getUrl()))
        // return;
        // }
        // }

        // if there are stickers send them all as one message
        // if (!event.getMessage().getStickers().isEmpty()) {
        // for (MessageSticker sticker : event.getMessage().getStickers().subList(0,
        // Math.min(event.getMessage().getStickers().size(), 3))) {
        // if (handleMessageAddons(event, preEvent, selectedRoles, topRole,
        // sticker.getIconUrl()))
        // return;
        // }
        // }

        if (StringUtils.isBlank(message))
            return;

        for (String p : TheFrakinDiscordConfig.discord_to_minecraft_message_filters.get()) {
            if (Pattern.matches(p, message)) {
                event.getMessage().addReaction("❌").queue();
                return;
            }
        }

        if (message.length() > TheFrakinDiscordConfig.player_chat_truncate_length.get()) {
            event.getMessage().addReaction("❗").queue();
            message = message.substring(0, TheFrakinDiscordConfig.player_chat_truncate_length.get());
        }

        if (message == null)
            message = "<blank message>";

        message = MiniMessage.miniMessage().serialize(MinecraftSerializer.INSTANCE.serialize(message));
        // TODO: Translate mentions
        if (StringUtils.isBlank(message)) {
            TheFrakinDiscordMod.getLogger().debug("Ignoring message from " + event.getAuthor()
                    + " because it became completely blank after reserialization (emote filtering)");
            return;
        }

        String emojiBehavior = TheFrakinDiscordConfig.player_chat_emoji_behavior.get();
        if (emojiBehavior.equalsIgnoreCase("hide")) {
            // TODO: Strip emojis
        } else if (emojiBehavior.equalsIgnoreCase("name")) {
            // TOOD: Convert emojis to discord name
        }

        // String messageFormat =
        // TheFrakinDiscordConfig.discord_to_minecraft_message_format.get();

        ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastSystemMessage(
                (net.minecraft.network.chat.Component) MiniMessage.miniMessage().deserialize(message), true);
    }
}
