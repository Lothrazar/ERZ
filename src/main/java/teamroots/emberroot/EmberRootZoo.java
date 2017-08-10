package teamroots.emberroot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.owl.ItemOwlEgg;
import teamroots.emberroot.entity.wolf.EntityDireWolf;
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
    MinecraftForge.EVENT_BUS.register(new RegistryManager());
    MinecraftForge.EVENT_BUS.register(EmberRootZoo.instance);
    proxy.preInit(event);
    EmberRootZoo.itemOwlEgg = new ItemOwlEgg();
    EmberRootZoo.instance.register(EmberRootZoo.itemOwlEgg);
    EmberRootZoo.instance.register(EntityOwl.SOUND_HOOT);
    EmberRootZoo.instance.register(EntityOwl.SOUND_HOOT_DOUBLE);
    EmberRootZoo.instance.register(EntityOwl.SOUND_HURT);
    EmberRootZoo.instance.register(EntityDireWolf.SND_HURT);
    EmberRootZoo.instance.register(EntityDireWolf.SND_HOWL);
    EmberRootZoo.instance.register(EntityDireWolf.SND_GROWL);
    EmberRootZoo.instance.register(EntityDireWolf.SND_DEATH);
  }
  public static DamageSource damage_ember;
  public static Item itemOwlEgg;
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  private void addRecipes() {
    //    ResourceLocation rl;
    //    //OreDictionary.registerOre("sand", new ItemStack(Blocks.SAND, 1, OreDictionary.WILDCARD_VALUE));//sand is already in dict by default
    //    if (Config.confusingChargeEnabled) {
    //      ItemStack cc = new ItemStack(blockConfusingCharge);
    //      rl = new ResourceLocation(MODID, "recipe" + resourceLocationCounter);
    //      addRecipe(new ShapedOreRecipe(rl, cc, "csc", "sgs", "csc", 'c', itemConfusingDust, 's', "sand", 'g', Items.GUNPOWDER), rl);
    //      resourceLocationCounter++;
    //    }
    //    if (Config.enderChargeEnabled) {
    //      ItemStack cc = new ItemStack(blockEnderCharge);
    //      rl = new ResourceLocation(MODID, "recipe" + resourceLocationCounter);
    //      addRecipe(new ShapedOreRecipe(rl, cc, "csc", "sgs", "csc", 'c', itemEnderFragment, 's', "sand", 'g', Items.GUNPOWDER), rl);
    //      resourceLocationCounter++;
    //    }
    //    if (Config.concussionChargeEnabled) {
    //      ItemStack cc = new ItemStack(blockConcussionCharge);
    //      rl = new ResourceLocation(MODID, "recipe" + resourceLocationCounter);
    //      addRecipe(new ShapedOreRecipe(rl, cc, "eee", "sgs", "ccc", 'c', itemConfusingDust, 'e', itemEnderFragment, 's', "sand", 'g', Items.GUNPOWDER), rl);
    //      resourceLocationCounter++;
    //    }
    //    rl = new ResourceLocation(MODID, "recipe" + resourceLocationCounter);
    //    addRecipe(new ShapedOreRecipe(rl, new ItemStack(Items.ENDER_PEARL), " f ", "fff", " f ", 'f', itemEnderFragment), rl);
    //    resourceLocationCounter++;
  }
  private int resourceLocationCounter = 0;
  private List<IRecipe> recipes = new ArrayList<IRecipe>();
  List<Item> items = new ArrayList<Item>();
  private List<Block> blocks = new ArrayList<Block>();
  private List<Enchantment> enchants = new ArrayList<Enchantment>();
  private List<SoundEvent> sounds = new ArrayList<SoundEvent>();
  private List<Potion> potionlist = new ArrayList<Potion>();
  private List<PotionType> potiontype = new ArrayList<PotionType>();
  private void addRecipe(IRecipe r, ResourceLocation rl) {
    r.setRegistryName(rl);
    this.recipes.add(r);
  }
  public void register(Item r) {
    this.items.add(r);
  }
  public void register(Block r) {
    this.blocks.add(r);
  }
  public void register(Enchantment r) {
    this.enchants.add(r);
  }
  public void register(SoundEvent r) {
    r.setRegistryName(r.getSoundName());
    this.sounds.add(r);
  }
  public void register(Potion r) {
    this.potionlist.add(r);
  }
  public void register(PotionType r) {
    this.potiontype.add(r);
  }
  @SubscribeEvent
  public void onRegisterRecipe(RegistryEvent.Register<IRecipe> event) {
    event.getRegistry().registerAll(this.recipes.toArray(new IRecipe[0]));
  }
  @SubscribeEvent
  public void onRegisterItems(RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(this.items.toArray(new Item[0]));
    addRecipes();
  }
  @SubscribeEvent
  public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().registerAll(this.blocks.toArray(new Block[0]));
  }
  @SubscribeEvent
  public void onRegisterEnchantments(RegistryEvent.Register<Enchantment> event) {
    event.getRegistry().registerAll(this.enchants.toArray(new Enchantment[0]));
  }
  @SubscribeEvent
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    event.getRegistry().registerAll(this.sounds.toArray(new SoundEvent[0]));
  }
  @SubscribeEvent
  public void onRegisterPotion(RegistryEvent.Register<Potion> event) {
    event.getRegistry().registerAll(this.potionlist.toArray(new Potion[0]));
  }
  @SubscribeEvent
  public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {
    event.getRegistry().registerAll(this.potiontype.toArray(new PotionType[0]));
  }
}
