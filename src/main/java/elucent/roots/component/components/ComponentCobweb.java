package elucent.roots.component.components;

import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentScore;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.UUID;

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
        	ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - (2.0+size), y - (2.0+size), z - (2.0+size), x + (2.0+size), y + (2.0+size), z + (2.0+size)));
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getUniqueID() != caster.getUniqueID()) {
                    int defatultEffectDuration = 40;
                    int defaultEffectAmplifier = 4;
                    int potencyForEffectDuration = defatultEffectDuration * (int)potency;
                    int potencyForEffectAmplifier = defaultEffectAmplifier * (int) potency;

                    PotionEffect eff = new PotionEffect(MobEffects.SLOWNESS, defatultEffectDuration + potencyForEffectDuration, defaultEffectAmplifier + potencyForEffectAmplifier);
                    entities.get(i).addPotionEffect(new PotionEffect(eff));
                }
            }
        }
    }

    @Override
    public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
    	if (type == EnumCastType.SPELL) {
            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - (2.0+size), y - (2.0+size), z - (2.0+size), x + (2.0+size), y + (2.0+size), z + (2.0+size)));
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getUniqueID() != casterId) {
                    int defatultEffectDuration = 40;
                    int defaultEffectAmplifier = 4;
                    int potencyForEffectDuration = defatultEffectDuration * (int)potency;
                    int potencyForEffectAmplifier = defaultEffectAmplifier * (int) potency;

                    PotionEffect eff = new PotionEffect(MobEffects.SLOWNESS, defatultEffectDuration + potencyForEffectDuration, defaultEffectAmplifier + potencyForEffectAmplifier);
                    entities.get(i).addPotionEffect(new PotionEffect(eff));
                }
            }
        }
    }
}


