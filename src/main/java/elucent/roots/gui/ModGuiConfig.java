package elucent.roots.gui;

import elucent.roots.ConfigManager;
import elucent.roots.Roots;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SirShadow on 19. 08. 2016.
 */
public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig(GuiScreen parent) {
        super(parent, getConfigElements(), Roots.MODID, false, false,GuiConfig.getAbridgedConfigPath(ConfigManager.config.toString()) );
    }

    /** Compiles a list of config elements */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        //Add categories to config GUI
        list.add(categoryElement(Configuration.CATEGORY_GENERAL, "General", "Geneeral"));
        list.add(categoryElement(Configuration.CATEGORY_CLIENT,"Client","Clieent"));
        list.add(categoryElement(ConfigManager.CATEGORY_TALISMAN,"Talisman","Talisman"));
        return list;
    }

    /** Creates a button linking to another screen where all options of the category are available */
    private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key, new ConfigElement(ConfigManager.config.getCategory(category)).getChildElements());
    }
}
