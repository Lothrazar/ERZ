package elucent.roots;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigManager {

	public static Configuration config;

	//WORLD
	public static int oldRootDropChance, verdantSprigDropChance, infernalStemDropChance, dragonsEyeDropChance, berriesDropChance;
	//CLIENT
	public static boolean showTabletWave;
	//SPELLS
	public static String[] disabledComponents;
	public static int chargeTicks, staffUses, efficiencyBonus;
	public static int manaBarOffset;
	public static boolean disablePVP;

	public static void init(File configFile)
	{
		if(config == null)
		{
			config = new Configuration(configFile);
			load();
		}
	}
	
	public static void load()
	{
		config.addCustomCategoryComment(Configuration.CATEGORY_CLIENT, "Settings that affect clientside graphical preferences.");
		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "Settings related to actual gameplay-affecting features.");
		oldRootDropChance = config.getInt("oldRootDropChance", Configuration.CATEGORY_GENERAL, 40, 0, 32767, "Old Roots will drop from tall grass with a 1/oldRootDropChance probability.");
		verdantSprigDropChance = config.getInt("verdantSprigDropChance", Configuration.CATEGORY_GENERAL, 30, 0, 32767, "Verdant Sprigs will drop from grown crops with a 1/verdantSprigDropChance probability.");
		infernalStemDropChance = config.getInt("infernalBulbDropChance", Configuration.CATEGORY_GENERAL, 20, 0, 32767, "Infernal Bulbs will drop from nether wart with a 1/infernalBulbDropChance probability.");
		dragonsEyeDropChance = config.getInt("dragonsEyeDropChance", Configuration.CATEGORY_GENERAL, 10, 0, 32767, "Dragon's Eyes will drop from chorus flowers with a 1/dragonsEyeDropChance probability.");
		berriesDropChance = config.getInt("berriesDropChance", Configuration.CATEGORY_GENERAL, 25, 0, 32767, "Berries will drop from oak leaves with a 1/berriesDropChance probability.");
		showTabletWave = config.getBoolean("showTabletWave", Configuration.CATEGORY_CLIENT, true, "Toggles the wave effect in the Runic Tablet GUI.");
		chargeTicks = config.getInt("staffChargeTicks", Configuration.CATEGORY_GENERAL, 20, 1, 32767, "The number of ticks required to prepare a spell with a staff.");
		manaBarOffset = config.getInt("manaBarOffset", Configuration.CATEGORY_CLIENT, 59, 0, 32767, "The number of pixels above the bottom of the screen that the mana bar should be rendered. If it's conflicting with a bar from another mod, raising it by 10 will normally position it right.");
		staffUses = config.getInt("staffUses", Configuration.CATEGORY_GENERAL, 65, 0, 32767, "The number of uses an unmodified staff will have upon being crafted.");
		efficiencyBonus = config.getInt("efficiencyBonusUses", Configuration.CATEGORY_GENERAL, 32, 0, 32767, "The number of additional uses each efficiency modifier gives.");
		disabledComponents = config.getStringList("disabledComponents", Configuration.CATEGORY_GENERAL, new String[]{
				"<example>", "<another example>"
		}, "A string list of all disabled components. Valid component names include: \"allium\", \"apple\", \"azurebluet\", \"blueorchid\", \"chorus\", \"dandelion\", \"flareorchid\", \"lilac\", \"lilypad\", \"midnightbloom\", \"netherwart\", \"orangetulip\", \"oxeyedaisy\", \"peony\", \"pinktulip\", \"poisonouspotato\", \"poppy\", \"radiantdaisy\", \"redtulip\", \"rosebush\", \"sunflower\", \"whitetulip\"");
		disablePVP = config.getBoolean("disablePVP", "spells", false, "Whether or not damaging spells can affect players.");

		if (config.hasChanged())
		{
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.getModID().equalsIgnoreCase(Roots.MODID))
		{
			load();
		}
	}
}
