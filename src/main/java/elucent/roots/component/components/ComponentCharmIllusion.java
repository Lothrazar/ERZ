package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.UUID;

import elucent.roots.EventManager;
import elucent.roots.RegistryManager;
import elucent.roots.RootsNames;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 26.7.2016.
 */
public class ComponentCharmIllusion extends ComponentBase {

    public ComponentCharmIllusion() {
        super("illusion", "Illusion", RegistryManager.itemCharmIllusion, 16);
    }

    @Override
    public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    	Util.addTickTracking(caster);
        caster.getEntityData().setInteger(RootsNames.TAG_SPELL_ILLUSION, 200);
    }

    @Override
    public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
        if (type == EnumCastType.SPELL) {
            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - 4.0, y - 4.0, z - 4.0, x + 4.0, y + 4.0, z + 4.0));
            for (int i = 0; i < entities.size(); i++) {
            	if (entities.get(i).getUniqueID().compareTo(casterId) == 0){
            		Util.addTickTracking(entities.get(i));
            		entities.get(i).getEntityData().setInteger(RootsNames.TAG_SPELL_ILLUSION, 200);
            		return;
            	}
            }
            if (entities.size() > 0){
        		Util.addTickTracking(entities.get(0));
        		entities.get(0).getEntityData().setInteger(RootsNames.TAG_SPELL_ILLUSION, 200);
            }
        }
    }
}
