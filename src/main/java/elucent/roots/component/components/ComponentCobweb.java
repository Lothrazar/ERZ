package elucent.roots.component.components;

import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by Tilci02 on 15.7.2016.
 */
public class ComponentCobweb extends ComponentBase {
    public ComponentCobweb() {
        super("cobweb", "snare", Item.getItemFromBlock(Blocks.WEB), 8);
    }

    @Override
    public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getUniqueID() != caster.getUniqueID()) {
                    if (entities.get(i) instanceof EntityPlayer && !world.getMinecraftServer().isPVPEnabled()) {

                    } else {
                        PotionEffect eff = new PotionEffect(MobEffects.SLOWNESS, 40 * (int) potency, 4 + (int) potency);
                        entities.get(i).addPotionEffect(new PotionEffect(eff));
                    }
                }
            }
        }
    }
}


