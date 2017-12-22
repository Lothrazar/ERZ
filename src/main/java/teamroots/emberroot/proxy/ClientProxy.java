package teamroots.emberroot.proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamroots.emberroot.entity.deer.ModelDeer;
import teamroots.emberroot.entity.fairy.ModelFairy;
import teamroots.emberroot.entity.golem.ModelGolem;
import teamroots.emberroot.entity.golem.RenderAncientGolem;
import teamroots.emberroot.entity.slime.ModelWaterSlime;
import teamroots.emberroot.entity.sprite.ModelSprite;
import teamroots.emberroot.entity.sprite.particle.ParticleMagicSparkle;
import teamroots.emberroot.entity.sprite.particle.ParticleMagicSparkleScalable;
import teamroots.emberroot.entity.spritegreater.ModelGreaterSprite;
import teamroots.emberroot.entity.spritegreater.ModelNull;
import teamroots.emberroot.entity.spriteguardian.ModelSpriteGuardianHead;
import teamroots.emberroot.entity.spriteguardian.ModelSpriteGuardianSegment;
import teamroots.emberroot.entity.spriteguardian.ModelSpriteGuardianSegmentFirst;
import teamroots.emberroot.entity.spriteguardian.ModelSpriteGuardianSegmentLarge;
import teamroots.emberroot.entity.spriteguardian.ModelSpriteGuardianTail;
import teamroots.emberroot.entity.spriteling.ModelSpriteling;
import teamroots.emberroot.entity.sprout.ModelSprout;
import teamroots.emberroot.entity.wolftimber.ModelTimberWolf;
import teamroots.emberroot.particle.ParticleRenderer;

public class ClientProxy extends CommonProxy {
  public static ParticleRenderer particleRenderer = new ParticleRenderer();
  // public static ParticleRendererGolem particleRendererGolem = new ParticleRendererGolem();
  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    ModelDeer.instance = new ModelDeer();
    ModelSprout.instance = new ModelSprout();
    ModelFairy.instance = new ModelFairy();
    ModelWaterSlime.instance = new ModelWaterSlime(16);
    RenderAncientGolem.model = new ModelGolem();
    ModelTimberWolf.instance = new ModelTimberWolf();
    ModelSprite.instance = new ModelSprite();
    ModelSpriteling.instance = new ModelSpriteling();
    ModelNull.instance = new ModelNull();
    ModelGreaterSprite.instance = new ModelGreaterSprite();
    ModelSpriteGuardianHead.instance = new ModelSpriteGuardianHead();
    ModelSpriteGuardianSegment.instance = new ModelSpriteGuardianSegment();
    ModelSpriteGuardianSegmentFirst.instance = new ModelSpriteGuardianSegmentFirst();
    ModelSpriteGuardianSegmentLarge.instance = new ModelSpriteGuardianSegmentLarge();
    ModelSpriteGuardianTail.instance = new ModelSpriteGuardianTail();
  }
  @Override
  public void spawnParticleMagicFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagic particle = new ParticleMagic(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicLine particle = new ParticleMagicLine(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicAltarLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicAltarLine particle = new ParticleMagicAltarLine(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicAltarFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicAltar particle = new ParticleMagicAltar(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicAltarBigFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicAltarBig particle = new ParticleMagicAltarBig(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicAura particle = new ParticleMagicAura(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicSparkleFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    ParticleMagicSparkle particle = new ParticleMagicSparkle(world, x, y, z, vx, vy, vz, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicSmallSparkleFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicSmallSparkle particle = new ParticleMagicSmallSparkle(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicSparklePulseFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    //    ParticleMagicSparklePulse particle = new ParticleMagicSparklePulse(world,x,y,z,vx,vy,vz,r,g,b);
    //    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
  @Override
  public void spawnParticleMagicSparkleScalableFX(World world, int lifetime, double x, double y, double z, double vx, double vy, double vz, float scale, double r, double g, double b) {
    ParticleMagicSparkleScalable particle = new ParticleMagicSparkleScalable(world, lifetime, x, y, z, vx, vy, vz, scale, r, g, b);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
}
