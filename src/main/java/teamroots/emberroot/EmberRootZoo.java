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
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
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
import teamroots.emberroot.entity.witch.EntityWitherWitch;
import teamroots.emberroot.entity.wolfdire.EntityDireWolf;
import teamroots.emberroot.entity.wolftimber.EntityTimberWolf;
import teamroots.emberroot.proxy.CommonProxy;

@Mod(modid = Const.MODID, name = EmberRootZoo.MODNAME, updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/ERZ/master/update.json")
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

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    //loot tables
    //TODO: less copy/paste way 
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/owl"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprout_red"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprout_orange"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprout_yellow"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprout_green"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprout_blue"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprout_purple"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/creeper_concussion"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/deer"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/golem_red"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/golem_orange"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/golem_yellow"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/golem_green"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/golem_blue"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/golem_purple"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/hero"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/slime_blue"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/slime_grey"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/slime_white"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/slime_purple"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/slime_red"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/skeleton_frozen"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/mount_fallen"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/wolf_dire"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/cat_wither"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/slime_block"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/ender_mini"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_green"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_purple"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_pink"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_orange"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_blue"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_yellow"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/fairy_red"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprite_normal"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprite_mini"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/witch"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprite_greater"));
    LootTableList.register(new ResourceLocation(Const.MODID, "entity/sprite_boss"));
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
