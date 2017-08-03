package teamroots.emberroot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.capability.RootsCapabilityManager;
import teamroots.emberroot.proxy.CommonProxy; 

@Mod(modid = Roots.MODID, name = Roots.MODNAME, version = Roots.VERSION)
public class Roots
{
    public static final String MODID = "emberoot";
    public static final String MODNAME = "emberRoot";
    public static final String VERSION = "0.025";
	public static final String DEPENDENCIES = "";
	
    @SidedProxy(clientSide = "teamroots.emberroot.proxy.ClientProxy",serverSide = "teamroots.emberroot.proxy.ServerProxy")
    public static CommonProxy proxy;
	
	public static CreativeTabs tab = new CreativeTabs("roots") {
    	@Override
    	public String getTabLabel(){
    		return "roots";
    	}
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem(){
			return new ItemStack(Blocks.MOB_SPAWNER);
		}
	};
	
    @Instance(MODID)
    public static Roots instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new EventManager());
		MinecraftForge.EVENT_BUS.register(new ConfigManager());
		MinecraftForge.EVENT_BUS.register(new RegistryManager());
 
		MinecraftForge.EVENT_BUS.register(new RootsCapabilityManager());
        ConfigManager.init(event.getSuggestedConfigurationFile());
		proxy.preInit(event);
	}
	
 
}
