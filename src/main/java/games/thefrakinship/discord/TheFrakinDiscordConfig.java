package games.thefrakinship.discord;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = TheFrakinDiscordMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TheFrakinDiscordConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.ConfigValue<String> discordBotToken;

    public static ForgeConfigSpec.ConfigValue<String> discord_invite_link;
    public static ForgeConfigSpec.ConfigValue<String> avatar_url;

    public static ForgeConfigSpec.ConfigValue<String> bot_token;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> game_status;
    public static ForgeConfigSpec.ConfigValue<String> online_status;

    public static ForgeConfigSpec.IntValue status_update_rate_in_minutes;

    public static ForgeConfigSpec.BooleanValue require_linked_account_to_play;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> bypass_names;
    public static ForgeConfigSpec.BooleanValue vanilla_whitelist_bypass_check;
    public static ForgeConfigSpec.BooleanValue check_banned_players;
    public static ForgeConfigSpec.BooleanValue only_check_banned_players;
    public static ForgeConfigSpec.BooleanValue must_be_in_discord_server;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> account_linked_console_commands;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> account_unlinked_console_commands;
    public static ForgeConfigSpec.ConfigValue<String> linked_account_role_to_add;
    public static ForgeConfigSpec.BooleanValue allow_relink_by_sending_new_code;
    public static ForgeConfigSpec.BooleanValue use_private_message;
    public static ForgeConfigSpec.IntValue message_delete_in_seconds;

    public static ForgeConfigSpec.BooleanValue require_role_to_join;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> allow_listed_roles;
    public static ForgeConfigSpec.BooleanValue require_all_roles;

    public static ForgeConfigSpec.BooleanValue translate_mentions;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> allowed_mentions;
    public static ForgeConfigSpec.ConfigValue<String> prefix_required_to_process_message;
    public static ForgeConfigSpec.BooleanValue broadcast_discord_messages_to_console;

    public static ForgeConfigSpec.BooleanValue discord_to_minecraft_enabled;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> discord_to_minecraft_message_filters;
    public static ForgeConfigSpec.ConfigValue<String> discord_to_minecraft_message_format;
    public static ForgeConfigSpec.ConfigValue<String> discord_to_minecraft_message_reply_format;
    // public static ForgeConfigSpec.BooleanValue
    // discord_to_minecraft_prefix_acts_as_deny_list;

    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_enabled;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> minecraft_to_discord_message_filters;
    public static ForgeConfigSpec.ConfigValue<String> minecraft_to_discord_message_format;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_server_start_message;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_server_stop_message;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_player_join_message;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_player_first_join_message;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_player_leave_message;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_player_death_message;
    public static ForgeConfigSpec.BooleanValue minecraft_to_discord_player_achievement_message;

    // WebhookChatMessageDelivery
    public static ForgeConfigSpec.BooleanValue webhook_chat_message_delivery_enabled;
    public static ForgeConfigSpec.ConfigValue<String> webhook_chat_message_delivery_username_format;
    public static ForgeConfigSpec.ConfigValue<String> webhook_chat_message_delivery_message_format;
    public static ForgeConfigSpec.BooleanValue webhook_chat_message_delivery_username_from_discord;
    public static ForgeConfigSpec.BooleanValue webhook_chat_message_delivery_avatar_from_discord;

    // builder.push("PlayerChat");
    public static ForgeConfigSpec.ConfigValue<String> player_chat_channel_id;
    public static ForgeConfigSpec.ConfigValue<String> player_chat_channel_topic;
    public static ForgeConfigSpec.ConfigValue<String> player_chat_shutdown_topic;
    public static ForgeConfigSpec.IntValue player_chat_update_interval_in_minutes;
    public static ForgeConfigSpec.BooleanValue player_chat_require_linked_account;
    public static ForgeConfigSpec.BooleanValue player_chat_block_bots;
    public static ForgeConfigSpec.BooleanValue player_chat_block_webhooks;
    public static ForgeConfigSpec.BooleanValue player_chat_blocked_users_as_allow_list;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> player_chat_blocked_users;
    public static ForgeConfigSpec.BooleanValue player_chat_blocked_roles_as_allow_list;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> player_chat_blocked_roles;
    public static ForgeConfigSpec.ConfigValue<Integer> player_chat_truncate_length;
    public static ForgeConfigSpec.ConfigValue<String> player_chat_emoji_behavior;
    public static ForgeConfigSpec.ConfigValue<String> player_chat_emote_behavior;

    // builder.push("Console");
    public static ForgeConfigSpec.ConfigValue<String> console_channel_id;
    public static ForgeConfigSpec.ConfigValue<String> console_channel_topic;
    public static ForgeConfigSpec.ConfigValue<String> console_shutdown_topic;
    public static ForgeConfigSpec.IntValue console_update_interval_in_minutes;
    public static ForgeConfigSpec.BooleanValue console_require_linked_account;
    public static ForgeConfigSpec.BooleanValue console_block_bots;
    public static ForgeConfigSpec.BooleanValue console_block_webhooks;
    public static ForgeConfigSpec.BooleanValue console_blocked_users_as_allow_list;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> console_blocked_users;
    public static ForgeConfigSpec.BooleanValue console_blocked_roles_as_allow_list;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> console_blocked_roles;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        discord_invite_link = builder.define("discord_invite_link", "");
        avatar_url = builder.define("avatar_url",
                "https://crafatar.com/avatars/{uuid-nodashes}.png?size={size}&overlay#{texture}");
        builder.pop();

        builder.push("DiscordBot");
        bot_token = builder.define("bot_token", "CHANGEME");
        game_status = builder.defineList("game_status", Arrays.asList("Minecraft"), entry -> true);
        online_status = builder.defineInList("online_status", "ONLINE",
                Arrays.asList("ONLINE", "DND", "IDLE", "INVISIBLE"));
        status_update_rate_in_minutes = builder.defineInRange("status_update_rate_in_minutes", 2, 2, 60);
        builder.pop();

        builder.push("Linking");
        require_linked_account_to_play = builder.define("require_linked_account_to_play", false);
        bypass_names = builder.defineList("bypass_names", Arrays.asList("Scarsz", "Vankka"), entry -> true);
        vanilla_whitelist_bypass_check = builder.define("vanilla_whitelist_bypass_check", true);
        check_banned_players = builder.define("check_banned_players", false);
        only_check_banned_players = builder.define("only_check_banned_players", false);
        must_be_in_discord_server = builder.define("must_be_in_discord_server", true);
        account_linked_console_commands = builder.defineList("account_linked_console_commands",
                Arrays.asList("", "", ""), entry -> true);
        account_unlinked_console_commands = builder.defineList("account_unlinked_console_commands",
                Arrays.asList("", "", ""), entry -> true);
        linked_account_role_to_add = builder.define("linked_account_role_to_add", "Linked");
        allow_relink_by_sending_new_code = builder.define("allow_relink_by_sending_new_code", false);
        use_private_message = builder.define("use_private_message", true);
        message_delete_in_seconds = builder.defineInRange("message_delete_in_seconds", 0, 0, 300);

        builder.push("Roles");
        require_role_to_join = builder.define("require_role_to_join", false);
        allow_listed_roles = builder.defineList("allow_listed_roles", Arrays.asList(), entry -> true);
        require_all_roles = builder.define("require_all_roles", false);
        builder.pop();
        builder.pop();

        builder.push("Chat");
        translate_mentions = builder.define("translate_mentions", true);
        allowed_mentions = builder.defineList("allowed_mentions", Arrays.asList("user", "channel", "emote"),
                entry -> Arrays.asList("user", "channel", "emote", "role", "here", "everyone")
                        .contains(entry));
        // require_linked_account = builder.define("require_linked_account", false);
        // block_bots = builder.define("block_bots", false);
        // block_webhooks = builder.define("block_webhooks", true);
        // blocked_ids = builder.defineList("blocked_ids",
        // Arrays.asList("000000000000000000", "000000000000000000",
        // "000000000000000000"),
        // entry -> true);
        // blocked_roles_as_allow_list = builder.define("blocked_roles_as_allow_list",
        // false);
        // blocked_role_ids = builder.defineList("blocked_role_ids",
        // Arrays.asList("000000000000000000", "000000000000000000",
        // "000000000000000000"),
        // entry -> true);
        // roles_selection_as_allow_list =
        // builder.define("roles_selection_as_allow_list", false);
        // roles_selection = builder.defineList("roles_selection",
        // Arrays.asList("Don't show me!", "Misc role"), entry -> true);

        builder.push("DiscordToMinecraft");
        discord_to_minecraft_enabled = builder.define("enabled", true);
        discord_to_minecraft_message_filters = builder.defineList("message_filters",
                Arrays.asList(".*Online players\\\\(.*", ".*\\\\*\\\\*No online players\\\\*\\\\*.*"),
                entry -> true);
        discord_to_minecraft_message_format = builder.define("message_format", "<%name%>%reply% %message%");
        discord_to_minecraft_message_reply_format = builder.define("message_reply_format",
                " (replying to %name%)");
        // discord_to_minecraft_prefix_acts_as_deny_list =
        // builder.define("prefix_acts_as_deny_list", false);
        builder.pop();

        builder.push("MinecraftToDiscord");
        minecraft_to_discord_enabled = builder.define("enabled", true);
        minecraft_to_discord_message_filters = builder.defineList("message_filters", Arrays.asList(),
                entry -> true);
        minecraft_to_discord_message_format = builder.define("message_format", "%message%");
        minecraft_to_discord_server_start_message = builder.define("server_start_message", true);
        minecraft_to_discord_server_stop_message = builder.define("server_stop_message", true);
        minecraft_to_discord_player_join_message = builder.define("player_join_message", true);
        minecraft_to_discord_player_first_join_message = builder.define("player_first_join_message", true);
        minecraft_to_discord_player_leave_message = builder.define("player_leave_message", true);
        minecraft_to_discord_player_death_message = builder.define("player_death_message", true);
        minecraft_to_discord_player_achievement_message = builder.define("player_achievement_message", true);
        // builder.define("server_startup_message", ":white_check_mark: **Server has
        // started**");
        // builder.define("server_shutdown_message", ":octagonal_sign: **Server has
        // stopped**");
        builder.pop();

        builder.push("WebhookChatMessageDelivery");
        webhook_chat_message_delivery_enabled = builder.define("enabled", true);
        webhook_chat_message_delivery_username_format = builder.define("username_format", "%displayname%");
        webhook_chat_message_delivery_message_format = builder.define("message_format", "%message%");
        webhook_chat_message_delivery_username_from_discord = builder.define("username_from_discord", true);
        webhook_chat_message_delivery_avatar_from_discord = builder.define("avatar_from_discord", true);
        builder.pop();

        builder.push("Channels");
        builder.push("PlayerChat");
        player_chat_channel_id = builder.define("channel_id", "0000000000000000");
        player_chat_channel_topic = builder.define("channel_topic",
                "%playercount%/%playermax% players online | %totalplayers% unique players ever joined | Server online for %uptimemins% minutes | Last update: %date%");
        player_chat_shutdown_topic = builder.define("shutdown_topic",
                "Server is offline | %totalplayers% unique players ever joined");
        player_chat_update_interval_in_minutes = builder.defineInRange("update_interval_in_minutes", 10, 2, 60);
        player_chat_require_linked_account = builder.define("require_linked_account", false);
        player_chat_block_bots = builder.define("block_bots", false);
        player_chat_block_webhooks = builder.define("block_webhooks", true);
        player_chat_blocked_users_as_allow_list = builder.define("blocked_users_as_allow_list", false);
        player_chat_blocked_users = builder.defineList("blocked_users",
                Arrays.asList("000000000000000000", "000000000000000000", "000000000000000000"),
                entry -> true);
        player_chat_blocked_roles_as_allow_list = builder.define("blocked_roles_as_allow_list", false);
        player_chat_blocked_roles = builder.defineList("blocked_roles",
                Arrays.asList("000000000000000000", "000000000000000000", "000000000000000000"),
                entry -> true);
        player_chat_truncate_length = builder.define("truncate_length", 256);
        player_chat_emoji_behavior = builder.defineInList("emoji_behavior", "name",
                Arrays.asList("show", "name", "hide"));
        player_chat_emote_behavior = builder.defineInList("emote_behavior", "name", Arrays.asList("name", "hide"));
        builder.pop();
        builder.push("Console");
        console_channel_id = builder.define("channel_id", "0000000000000000");
        console_channel_topic = builder.define("channel_topic",
                "TPS: %tps% | Mem: %usedmemorygb%GB used/%freememorygb%GB free/%maxmemorygb%GB max | %serverversion%");
        console_shutdown_topic = builder.define("shutdown_topic", "Server is offline | %serverversion%");
        console_update_interval_in_minutes = builder.defineInRange("update_interval_in_minutes", 10, 2, 60);
        console_require_linked_account = builder.define("require_linked_account", false);
        console_block_bots = builder.define("block_bots", false);
        console_block_webhooks = builder.define("block_webhooks", true);
        console_blocked_users_as_allow_list = builder.define("blocked_users_as_allow_list", false);
        console_blocked_users = builder.defineList("blocked_users",
                Arrays.asList("000000000000000000", "000000000000000000", "000000000000000000"),
                entry -> true);
        console_blocked_roles_as_allow_list = builder.define("blocked_roles_as_allow_list", false);
        console_blocked_roles = builder.defineList("blocked_roles",
                Arrays.asList("000000000000000000", "000000000000000000", "000000000000000000"),
                entry -> true);
        builder.pop();
        builder.pop();

        // builder.push("Commands");
        // builder.define("enabled", true);
        // builder.define("notify_errors", true);
        // builder.define("prefix", "!c");
        // builder.defineList("role_allow_list", Arrays.asList("Developer", "Owner"),
        // entry -> true);
        // builder.defineList("command_allow_list", Arrays.asList("say", "lag", "tps"),
        // entry -> true);
        // builder.defineList("command_allow_list_bypass_roles",
        // Arrays.asList("Developer", "Owner"),
        // entry -> true);
        // builder.define("command_allow_list_acts_as_deny_list", false);
        // builder.define("response_expiration_in_seconds", 0);
        // builder.define("response_expiration_delete_request", true);
        // builder.define("permission_denied_message_format",
        // "**%user%**, you tried running a command. Unfortunately, there was an error:
        // %error%");
        // builder.pop();

        // builder.push("PlayerList");
        // builder.define("enabled", true);
        // builder.define("message", "playerlist");
        // builder.define("response_expiration_in_seconds", 0);
        // builder.define("response_expiration_delete_request", true);
        // builder.define("message_format_online_players", "**Online players
        // (%playercount%):**");
        // builder.define("message_format_no_online_players", "**No online players**");
        // builder.define("player_format", "%displayname%");
        // builder.define("all_players_separator", ", ");
        // builder.pop();
        // builder.pop();

        // builder.push("Console");
        // builder.define("log_refresh_rate_in_seconds", 5);
        // builder.define("usage_log", "Console-%date%.log");
        // builder.define("deny_list_acts_as_allow_list", false);
        // builder.defineList("deny_listed_commands", Arrays.asList("?", "op", "deop",
        // "execute"), entry -> true);
        // builder.defineList("filters", Arrays.asList(".*(?i)async chat thread.*",
        // ".*There are \\d+ (?:of a max of|out of maximum) \\d+ players online.*"),
        // entry -> true);
        // builder.defineList("levels", Arrays.asList("info", "warn", "error"), entry ->
        // true);
        // builder.define("use_code_blocks", true);
        // builder.define("block_bots", false);
        // builder.define("timestamp_format", "EEE HH:mm:ss");
        // builder.define("message_prefix", "[{date} {level}{name}]");
        // builder.define("message_suffix", "");
        // builder.define("message_padding", 0);
        // builder.pop();

        // builder.push("Voice");
        // builder.define("enabled", false);
        // builder.define("tick_speed", 5);
        // builder.define("voice_category_id", "0000000000000000");
        // builder.define("lobby_channel_id", "0000000000000000");
        // builder.define("mute_users_who_bypass_permissions_in_lobby", true);

        // builder.push("Network");
        // builder.define("vertical_strength", 40);
        // builder.define("horizontal_strength", 80);
        // builder.define("falloff", 5);
        // builder.define("allow_voice_activation_detection", true);
        // builder.pop();
        // builder.pop();

        // builder.push("ServerWatchdog");
        // builder.define("enabled", true);
        // builder.define("timeout_in_seconds", 30);
        // builder.define("message_count", 3);
        // builder.pop();
    }
}