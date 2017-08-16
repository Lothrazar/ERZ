package teamroots.emberroot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.cat.EntityWitherCat;
import teamroots.emberroot.entity.cat.RenderWitherCat;
import teamroots.emberroot.entity.creeper.EntityConcussionCreeper;
import teamroots.emberroot.entity.creeper.RenderConcussionCreeper;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.deer.RenderDeer;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.entity.endermini.RenderEnderminy;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.fairy.RenderFairy;
import teamroots.emberroot.entity.frozen.EntityFrozenKnight;
import teamroots.emberroot.entity.frozen.RenderFrozenKnight;
import teamroots.emberroot.entity.golem.DamageGolem;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.golem.EntityGolemLaser;
import teamroots.emberroot.entity.golem.RenderAncientGolem;
import teamroots.emberroot.entity.golem.RenderEmberPacket;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.hero.RenderFallenHero;
import teamroots.emberroot.entity.knight.EntityFallenKnight;
import teamroots.emberroot.entity.knight.RenderFallenKnight;
import teamroots.emberroot.entity.mount.EntityFallenMount;
import teamroots.emberroot.entity.mount.RenderFallenMount;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.owl.EntityOwlEgg;
import teamroots.emberroot.entity.owl.RenderEntityOwlEgg;
import teamroots.emberroot.entity.owl.RenderOwl;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.slime.RenderWaterSlime;
import teamroots.emberroot.entity.slimedirt.EntityDireSlime;
import teamroots.emberroot.entity.slimedirt.RenderDireSlime;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.sprite.RenderSprite;
import teamroots.emberroot.entity.spritegreater.EntityGreaterSprite;
import teamroots.emberroot.entity.spritegreater.EntitySpriteProjectile;
import teamroots.emberroot.entity.spritegreater.RenderGreaterSprite;
import teamroots.emberroot.entity.spritegreater.RenderSpriteProjectile;
import teamroots.emberroot.entity.spriteguardian.EntitySpriteGuardianBoss;
import teamroots.emberroot.entity.spriteguardian.RenderSpriteGuardian;
import teamroots.emberroot.entity.spriteling.EntitySpriteling;
import teamroots.emberroot.entity.spriteling.RenderSpriteling;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.sprout.RenderSprout;
import teamroots.emberroot.entity.witch.EntityWitherWitch;
import teamroots.emberroot.entity.witch.RenderWitherWitch;
import teamroots.emberroot.entity.wolfdire.EntityDireWolf;
import teamroots.emberroot.entity.wolfdire.RenderDirewolf;
import teamroots.emberroot.entity.wolftimber.EntityTimberWolf;
import teamroots.emberroot.entity.wolftimber.RenderTimberWolf;
import teamroots.emberroot.util.Util;

public class RegistryManager {
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  public static void registerAll() {
    registerDamageSources();
    registerEntities();
    registerSpawning();
  }
  public static void registerSpawning() {
    List<Biome> allBiomes = new ArrayList<Biome>();
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.COOL)) {
      allBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.DESERT)) {
      allBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.ICY)) {
      allBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.WARM)) {
      allBiomes.add(b.biome);
    }
    allBiomes.addAll(BiomeManager.oceanBiomes);
    for (ConfigSpawnEntity cfg : ConfigManager.entityConfigs) {
      if (cfg.settings.weightedProb <= 0 || cfg.settings.max <= 0) {
        continue;
      }
      if (cfg.settings.useAllBiomes) {
        EntityRegistry.addSpawn(cfg.entityClass, cfg.settings.weightedProb, cfg.settings.min, cfg.settings.max, cfg.typeOfCreature, allBiomes.toArray(new Biome[0]));
      }
      else {
        EntityRegistry.addSpawn(cfg.entityClass, cfg.settings.weightedProb, cfg.settings.min, cfg.settings.max, cfg.typeOfCreature, cfg.getBiomeFilter());
      }
    }
  }
  public static void registerEntities() {
    int id = 0;
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityDeer.NAME), EntityDeer.class, EntityDeer.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityDeer.NAME), intColor(159, 132, 88), intColor(94, 77, 51));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntitySprout.NAME), EntitySprout.class, EntitySprout.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntitySprout.NAME), intColor(136, 189, 33), intColor(165, 232, 42));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFairy.NAME), EntityFairy.class, EntityFairy.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFairy.NAME), intColor(255, 208, 255), intColor(209, 255, 173));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityRainbowSlime.NAME), EntityRainbowSlime.class, EntityRainbowSlime.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityRainbowSlime.NAME), intColor(111, 208, 22), intColor(111, 255, 173));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "ember_projectile"), EntityGolemLaser.class, "ember_projectile", id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityAncientGolem.NAME), EntityAncientGolem.class, EntityAncientGolem.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityAncientGolem.NAME), intColor(48, 38, 35), intColor(79, 66, 61));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFallenHero.NAME), EntityFallenHero.class, EntityFallenHero.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFallenHero.NAME), intColor(159, 255, 222), intColor(222, 111, 51));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityConcussionCreeper.NAME), EntityConcussionCreeper.class, EntityConcussionCreeper.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityConcussionCreeper.NAME), 0x56FF8E, 0xFF0A22);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityOwl.NAME), EntityOwl.class, EntityOwl.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityOwl.NAME), 0xC17949, 0xFFDDC6);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityDireSlime.NAME), EntityDireSlime.class, EntityDireSlime.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityDireSlime.NAME), 0xb9855c, 0x593d29);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "EntityOwlEgg"), EntityOwlEgg.class, "EntityOwlEgg", id++, EmberRootZoo.instance, 64, 10, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityDireWolf.NAME), EntityDireWolf.class, EntityDireWolf.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityDireWolf.NAME), 0x606060, 0xA0A0A0);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityWitherCat.NAME), EntityWitherCat.class, EntityWitherCat.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityWitherCat.NAME), 0x303030, 0xFFFFFF);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityWitherWitch.NAME), EntityWitherWitch.class, EntityWitherWitch.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityWitherWitch.NAME), 0x26520D, 0x905E43);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityEnderminy.NAME), EntityEnderminy.class, EntityEnderminy.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityEnderminy.NAME), 0x27624D, 0x212121);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFallenKnight.NAME), EntityFallenKnight.class, EntityFallenKnight.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFallenKnight.NAME), 0x365A25, 0xA0A0A0);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFallenMount.NAME), EntityFallenMount.class, EntityFallenMount.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFallenMount.NAME), 0x365A25, 0xA0A0A0);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityTimberWolf.NAME), EntityTimberWolf.class, EntityTimberWolf.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityTimberWolf.NAME), 0x696969, 0xA5A5A5);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntitySprite.NAME), EntitySprite.class, EntitySprite.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntitySprite.NAME), Util.intColor(130, 255, 60), Util.intColor(130, 255, 60));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntitySpriteling.NAME), EntitySpriteling.class, EntitySpriteling.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntitySpriteling.NAME), Util.intColor(130, 255, 60), Util.intColor(130, 255, 60));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntitySpriteProjectile.NAME), EntitySpriteProjectile.class, EntitySpriteProjectile.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityGreaterSprite.NAME), EntityGreaterSprite.class, EntityGreaterSprite.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityGreaterSprite.NAME), Util.intColor(130, 255, 60), Util.intColor(130, 255, 60));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntitySpriteGuardianBoss.NAME), EntitySpriteGuardianBoss.class, EntitySpriteGuardianBoss.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntitySpriteGuardianBoss.NAME), Util.intColor(120, 245, 50), Util.intColor(160, 255, 60));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFrozenKnight.NAME), EntityFrozenKnight.class, EntityFrozenKnight.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFrozenKnight.NAME), intColor(87,58,134), 0xA0A0A0);
   
  }
  public static void registerDamageSources() {
    EmberRootZoo.damage_ember = new DamageGolem();
  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerRendering(ModelRegistryEvent event) {
    //item hax
    for (Item item : items) {
      String name = Const.MODID + ":" + item.getUnlocalizedName().replaceAll("item.", "");
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }
    RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySprout.class, new RenderSprout.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderFairy.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityRainbowSlime.class, new RenderWaterSlime.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityGolemLaser.class, new RenderEmberPacket(Minecraft.getMinecraft().getRenderManager()));
    RenderingRegistry.registerEntityRenderingHandler(EntityAncientGolem.class, new RenderAncientGolem.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenHero.class, new RenderFallenHero.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityConcussionCreeper.class, new RenderConcussionCreeper.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityDireSlime.class, new RenderDireSlime.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityOwl.class, new RenderOwl.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityDireWolf.class, new RenderDirewolf.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCat.class, new RenderWitherCat.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityWitherWitch.class, new RenderWitherWitch.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityEnderminy.class, new RenderEnderminy.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenKnight.class, new RenderFallenKnight.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenMount.class, new RenderFallenMount.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityOwlEgg.class, new RenderEntityOwlEgg.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityTimberWolf.class, new RenderTimberWolf.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySprite.class, new RenderSprite.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySpriteling.class, new RenderSpriteling.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityGreaterSprite.class, new RenderGreaterSprite.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySpriteProjectile.class, new RenderSpriteProjectile.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySpriteGuardianBoss.class, new RenderSpriteGuardian.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFrozenKnight.class, new RenderFrozenKnight.Factory());
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
