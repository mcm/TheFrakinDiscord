package games.thefrakinship.discord.events;

import games.thefrakinship.discord.TheFrakinDiscordConfig;
import games.thefrakinship.discord.TheFrakinDiscordMod;
import games.thefrakinship.discord.util.DiscordUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TheFrakinDiscordMod.MOD_ID, bus = Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ServerLifecycleEvents {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        // Send a discord message
        if (TheFrakinDiscordConfig.minecraft_to_discord_enabled.get()) {
            if (TheFrakinDiscordConfig.minecraft_to_discord_server_start_message.get()) {
                DiscordUtil.queueMessage(
                        DiscordUtil.getTextChannelById(TheFrakinDiscordConfig.player_chat_channel_id.get()),
                        ":white_check_mark: **Server has started**",
                        true);
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        // Send a discord message
        if (TheFrakinDiscordConfig.minecraft_to_discord_enabled.get()) {
            if (TheFrakinDiscordConfig.minecraft_to_discord_server_stop_message.get()) {
                DiscordUtil.queueMessage(
                        DiscordUtil.getTextChannelById(TheFrakinDiscordConfig.player_chat_channel_id.get()),
                        ":octagonal_sign: **Server has stopped**",
                        true);
            }
        }
    }
}
