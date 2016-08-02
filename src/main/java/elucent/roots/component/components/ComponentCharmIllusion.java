package elucent.roots.component.components;

import elucent.roots.EventManager;
import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 26.7.2016.
 */
public class ComponentCharmIllusion extends ComponentBase {

    public static boolean doStuff = false;

    public ComponentCharmIllusion() {
        super("illusion", "Illusion", RegistryManager.itemCharmIllusion, 16);
    }

    @Override
    public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if(!world.isRemote && !doStuff)
        {
            doStuff = true;
            EventManager.isEntityAttacked = false;
        }
    }
}
