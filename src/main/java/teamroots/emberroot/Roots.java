package teamroots.emberroot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
import teamroots.emberroot.proxy.CommonProxy;

@Mod(modid = Const.MODID, name = Roots.MODNAME, version = Roots.VERSION)
public class Roots {
  public static final String MODNAME = "emberRoot";
  public static final String VERSION = "0.025";
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
  public static Roots instance;
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new EventManager());
    MinecraftForge.EVENT_BUS.register(new ConfigManager());
    MinecraftForge.EVENT_BUS.register(new RegistryManager());
    ConfigManager.init(event.getSuggestedConfigurationFile());
    proxy.preInit(event);
  }
  public static DamageSource damage_ember;
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
}
