package games.thefrakinship.discord.util;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import games.thefrakinship.discord.TheFrakinDiscordMod;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class DiscordUtil {
    public static JDA getJda() {
        return TheFrakinDiscordMod.getJda();
    }

    public static TextChannel getTextChannelById(String channelId) {
        try {
            return getJda().getTextChannelById(channelId);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static String translateEmotes(String messageToTranslate) {
        return translateEmotes(messageToTranslate, getJda().getEmotes());
    }

    public static String translateEmotes(String messageToTranslate, Guild guild) {
        return translateEmotes(messageToTranslate, guild.getEmotes());
    }

    public static String translateEmotes(String messageToTranslate, List<Emote> emotes) {
        for (Emote emote : emotes)
            messageToTranslate = messageToTranslate.replace(":" + emote.getName() + ":", emote.getAsMention());
        return messageToTranslate;
    }

    public static void queueMessage(TextChannel channel, String message) {
        if (channel == null) {
            TheFrakinDiscordMod.getLogger().debug("Tried sending a message to a null channel");
            return;
        }

        message = translateEmotes(message, channel.getGuild());
        if (StringUtils.isBlank(message))
            return;
        queueMessage(channel, new MessageBuilder().append(message).build(), false);
    }

    /**
     * Send the given message to the given channel
     * 
     * @param channel       The channel to send the message to
     * @param message       The message to send to the channel
     * @param allowMassPing Whether to deny @everyone/@here pings
     */
    public static void queueMessage(TextChannel channel, String message, boolean allowMassPing) {
        if (channel == null) {
            TheFrakinDiscordMod.getLogger().debug("Tried sending a message to a null channel");
            return;
        }

        message = translateEmotes(message, channel.getGuild());
        if (StringUtils.isBlank(message))
            return;
        queueMessage(channel, new MessageBuilder().append(message).build(), allowMassPing);
    }

    /**
     * Send the given message to the given channel
     * 
     * @param channel The channel to send the message to
     * @param message The message to send to the channel
     */
    public static void queueMessage(TextChannel channel, Message message) {
        queueMessage(channel, message, null);
    }

    /**
     * Send the given message to the given channel
     * 
     * @param channel       The channel to send the message to
     * @param message       The message to send to the channel
     * @param allowMassPing Whether to deny @everyone/@here pings
     */
    public static void queueMessage(TextChannel channel, Message message, boolean allowMassPing) {
        queueMessage(channel, message, null, allowMassPing);
    }

    /**
     * Send the given message to the given channel, optionally doing something with
     * the message via the given consumer
     * 
     * @param channel  The channel to send the message to
     * @param message  The message to send to the channel
     * @param consumer The consumer to handle the message
     */
    public static void queueMessage(TextChannel channel, String message, Consumer<Message> consumer) {
        message = translateEmotes(message, channel.getGuild());
        if (StringUtils.isBlank(message))
            return;
        queueMessage(channel, new MessageBuilder().append(message).build(), consumer);
    }

    /**
     * Send the given message to the given channel, optionally doing something with
     * the message via the given consumer
     * 
     * @param channel       The channel to send the message to
     * @param message       The message to send to the channel
     * @param consumer      The consumer to handle the message
     * @param allowMassPing Whether to deny @everyone/@here pings
     */
    public static void queueMessage(TextChannel channel, String message, Consumer<Message> consumer,
            boolean allowMassPing) {
        message = translateEmotes(message, channel.getGuild());
        queueMessage(channel, new MessageBuilder().append(message).build(), consumer, allowMassPing);
    }

    /**
     * Send the given message to the given channel, optionally doing something with
     * the message via the given consumer
     * 
     * @param channel  The channel to send the message to
     * @param message  The message to send to the channel
     * @param consumer The consumer to handle the message
     */
    public static void queueMessage(TextChannel channel, Message message, Consumer<Message> consumer) {
        queueMessage(channel, message, consumer, false);
    }

    /**
     * Send the given message to the given channel, optionally doing something with
     * the message via the given consumer
     * 
     * @param channel  The channel to send the message to
     * @param message  The message to send to the channel
     * @param consumer The consumer to handle the message
     */
    public static void queueMessage(TextChannel channel, Message message, Consumer<Message> consumer,
            boolean allowMassPing) {
        if (channel == null) {
            TheFrakinDiscordMod.getLogger().debug("Tried sending a message to a null channel");
            return;
        }

        try {
            MessageAction action = channel.sendMessage(message);
            if (allowMassPing)
                action = action.allowedMentions(EnumSet.allOf(Message.MentionType.class));
            // action.queue(sentMessage -> {
            // DiscordSRV.api.callEvent(new DiscordGuildMessageSentEvent(getJda(),
            // sentMessage));
            // if (consumer != null)
            // consumer.accept(sentMessage);
            // }, throwable -> DiscordSRV
            // .error("Failed to send message to channel " + channel + ": " +
            // throwable.getMessage()));
            action.queue();
        } catch (PermissionException e) {
            if (e.getPermission() != Permission.UNKNOWN) {
                TheFrakinDiscordMod.getLogger().warn("Could not send message in channel " + channel
                        + " because the bot does not have the \"" + e.getPermission().getName() + "\"permission");
            } else {
                TheFrakinDiscordMod.getLogger().warn(
                        "Could not send message in channel " + channel + " because \"" +
                                e.getMessage() + "\"");
            }
        } catch (IllegalStateException e) {
            TheFrakinDiscordMod.getLogger().error("Could not send message to channel " + channel + ": " +
                    e.getMessage());
        }
    }

    public static void formatMessage() {
        
    }
}
