package teamroots.emberroot;
import java.util.Iterator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.entity.cat.EntityWitherCat;
import teamroots.emberroot.entity.creeper.EntityConcussionCreeper;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.frozen.EntityFrozenKnight;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.knight.EntityFallenKnight;
import teamroots.emberroot.entity.mount.EntityFallenMount;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.owl.ItemOwlEgg;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.slimedirt.EntityDireSlime;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.spritegreater.EntityGreaterSprite;
import teamroots.emberroot.entity.spriteguardian.EntitySpriteGuardianBoss;
import teamroots.emberroot.entity.spriteling.EntitySpriteling;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.witch.EntityWitherWitch;
import teamroots.emberroot.entity.wolfdire.EntityDireWolf;
import teamroots.emberroot.entity.wolftimber.EntityTimberWolf;
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
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list) {
      Iterator<ItemStack> i = list.iterator();
      while (i.hasNext()) {
        Item s = i.next().getItem(); // must be called before you can call i.remove()
        if (s == Items.SPAWN_EGG) {
          i.remove();
        }
      }
      //now add
      list.add(addEntity(EntityDeer.NAME)); 
      list.add(addEntity(EntityFairy.NAME));
      list.add(addEntity(EntityRainbowSlime.NAME));
      list.add(addEntity(EntityAncientGolem.NAME));
      list.add(addEntity(EntityFallenHero.NAME));
      list.add(addEntity(EntityConcussionCreeper.NAME));
      list.add(addEntity(EntityOwl.NAME));
      list.add(addEntity(EntityDireSlime.NAME));
      list.add(addEntity(EntityDireWolf.NAME));
      list.add(addEntity(EntityWitherCat.NAME));
      list.add(addEntity(EntityWitherWitch.NAME));
      list.add(addEntity(EntityEnderminy.NAME));
      list.add(addEntity(EntityFallenKnight.NAME));
      list.add(addEntity(EntityFallenMount.NAME));
      list.add(addEntity(EntityTimberWolf.NAME));
      list.add(addEntity(EntitySprite.NAME));
      list.add(addEntity(EntitySpriteling.NAME));
      list.add(addEntity(EntityGreaterSprite.NAME));
      list.add(addEntity(EntitySpriteGuardianBoss.NAME));
      list.add(addEntity(EntityFrozenKnight.NAME));
    }
    private ItemStack addEntity(String name) {
      ItemStack s = new ItemStack(Items.SPAWN_EGG);
      ItemMonsterPlacer.applyEntityIdToItemStack(s, new ResourceLocation(Const.MODID, name));
      return s;
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
  private static boolean doLog = false;
  public static void log(String string) {
    if (doLog == false) {
      return;
    }
    System.out.println("[EmberRootZoo]" + string);
  }
}
