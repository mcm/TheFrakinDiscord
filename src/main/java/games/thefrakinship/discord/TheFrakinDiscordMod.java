package games.thefrakinship.discord;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.neovisionaries.ws.client.DualStackMode;
import com.neovisionaries.ws.client.WebSocketFactory;

import games.thefrakinship.discord.events.DiscordEvents;
import games.thefrakinship.discord.util.DiscordUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheFrakinDiscordMod.MOD_ID)
public class TheFrakinDiscordMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "thefrakindiscord";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static JDA jda;

    public TheFrakinDiscordMod() {
        // IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Server lifecycle events
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,
                TheFrakinDiscordConfig.GENERAL_SPEC);
    }

    public static JDA getJda() {
        return jda;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        try {
            jda = JDABuilder.create(Arrays.asList(GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_EMOJIS,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.DIRECT_MESSAGES))
                    // we disable anything that isn't enabled (everything is enabled by default)
                    .disableCache(Arrays.stream(CacheFlag.values())
                            .filter(cacheFlag -> Arrays.asList(CacheFlag.MEMBER_OVERRIDES,
                                    CacheFlag.VOICE_STATE,
                                    CacheFlag.EMOTE).contains(cacheFlag))
                            .collect(Collectors.toList()))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    // .setCallbackPool(callbackThreadPool, false)
                    // .setGatewayPool(gatewayThreadPool, true)
                    // .setRateLimitPool(rateLimitThreadPool, true)
                    .setWebsocketFactory(new WebSocketFactory()
                            .setDualStackMode(DualStackMode.IPV4_ONLY))
                    // .setHttpClient(httpClient)
                    .setAutoReconnect(true)
                    .setBulkDeleteSplittingEnabled(false)
                    .setEnableShutdownHook(false)
                    .setToken(TheFrakinDiscordConfig.bot_token.get())
                    .addEventListeners(new DiscordEvents())
                    // .addEventListeners(new DiscordBanListener())
                    // .addEventListeners(new DiscordChatListener())
                    // .addEventListeners(new DiscordConsoleListener())
                    // .addEventListeners(new DiscordAccountLinkListener())
                    // .addEventListeners(new DiscordDisconnectListener())
                    // .addEventListeners(api)
                    // .addEventListeners(groupSynchronizationManager)
                    .setContextEnabled(false)
                    .build();

            jda.awaitReady(); // let JDA be assigned as soon as we can, but wait until it's ready

            for (Guild guild : jda.getGuilds()) {
                guild.retrieveOwner(true).queue();
                guild.loadMembers()
                        .onSuccess(members -> LOGGER.debug("Loaded " + members.size() + " members in guild " + guild))
                        .onError(throwable -> LOGGER.error("Failed to retrieve members of guild " + guild, throwable))
                        .get(); // block DiscordSRV startup until members are loaded
            }

            // DiscordUtil.queueMessage(
            // DiscordUtil.getTextChannelById(TheFrakinDiscordConfig.player_chat_channel_id.get()),
            // ":white_check_mark: **Server has started**",
            // true);
        } catch (LoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            if (e instanceof IllegalStateException && e.getMessage().equals("Was shutdown trying to await status")) {
                // already logged by JDA
                return;
            }
            LOGGER.error("An unknown error occurred building JDA...", e);
            return;
        }
    }
}
