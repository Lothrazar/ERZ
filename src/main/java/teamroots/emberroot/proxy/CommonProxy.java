package teamroots.emberroot.proxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import teamroots.emberroot.Const;
import teamroots.emberroot.RegistryManager;
import teamroots.emberroot.entity.golem.MessageGolemLaserFX;

public class CommonProxy {
  public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
  public void preInit(FMLPreInitializationEvent event) {
    RegistryManager.registerAll();
    int id = 0;
    INSTANCE.registerMessage(MessageGolemLaserFX.MessageHolder.class, MessageGolemLaserFX.class, id++, Side.CLIENT);
  }
  public void setInstantConfusionOnPlayer(EntityPlayer ent, int duration) {
    ent.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration, 1, false, true));
  }
  public void spawnParticleMagicSparkleScalableFX(World entityWorld, int i, double d, double e, double f, int j, int k, int l, float g, int m, int n, int o) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicAltarLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicAltarFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicAltarBigFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicSparkleFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicSmallSparkleFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicSparklePulseFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
  public void spawnParticleMagicSparkleScalableFX(World world, int lifetime, double x, double y, double z, double vx, double vy, double vz, float scale, double r, double g, double b) {
    // TODO Auto-generated method stub
  }
}
