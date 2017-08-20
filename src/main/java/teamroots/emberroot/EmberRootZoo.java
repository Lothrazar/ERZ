package teamroots.emberroot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.owl.ItemOwlEgg;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.spriteguardian.EntitySpriteGuardianBoss;
import teamroots.emberroot.entity.wolfdire.EntityDireWolf;
import teamroots.emberroot.proxy.CommonProxy;

@Mod(modid = Const.MODID, name = EmberRootZoo.MODNAME)
public class EmberRootZoo {
  public static final String MODNAME = "EmberRootZoo";
  public static final String DEPENDENCIES = "";
  @SidedProxy(clientSide = "teamroots." + Const.MODID + ".proxy.ClientProxy", serverSide = "teamroots." + Const.MODID + ".proxy.ServerProxy")
  public static CommonProxy proxy;
  public static CreativeTabs tab = new CreativeTabs(Const.MODID) {
    @Override
    public String getTabLabel() {
      return Const.MODID;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
      return new ItemStack(Blocks.MOB_SPAWNER);
    }
  };
  @Instance(Const.MODID)
  public static EmberRootZoo instance;
  public org.apache.logging.log4j.Logger logger;
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    this.logger = event.getModLog();
    ConfigManager.init(event.getSuggestedConfigurationFile());
    MinecraftForge.EVENT_BUS.register(new EventManager());
    MinecraftForge.EVENT_BUS.register(new ConfigManager());
    MinecraftForge.EVENT_BUS.register(EmberRootZoo.instance);
    RegistryManager registry = new RegistryManager();
    MinecraftForge.EVENT_BUS.register(registry);
    proxy.preInit(event);
    EmberRootZoo.itemOwlEgg = new ItemOwlEgg();
    registry.register(EmberRootZoo.itemOwlEgg);
    EntityOwl.SOUND_HOOT = registry.registerSound("owl.hoot_single");
    EntityOwl.SOUND_HOOT_DOUBLE = registry.registerSound("owl.hoot_double");
    EntityOwl.SOUND_HURT = registry.registerSound("owl.hurt");
    EntityDireWolf.SND_HURT = registry.registerSound("direwolf.hurt");
    EntityDireWolf.SND_HOWL = registry.registerSound("direwolf.howl");
    EntityDireWolf.SND_GROWL = registry.registerSound("direwolf.growl");
    EntityDireWolf.SND_DEATH = registry.registerSound("direwolf.death");
    EntitySprite.ambientSound = registry.registerSound("spiritambient");
    EntitySprite.hurtSound = registry.registerSound("spirithurt");
    EntitySprite.staffcast = registry.registerSound("staffcast");
    EntitySpriteGuardianBoss.ambientSound = registry.registerSound("bossambient");
    EntitySpriteGuardianBoss.hurtSound = registry.registerSound("bosshurt");
    EntitySpriteGuardianBoss.departureSound = registry.registerSound("bossdeath");
  }
  public static DamageSource damage_ember;
  public static Item itemOwlEgg;
}
