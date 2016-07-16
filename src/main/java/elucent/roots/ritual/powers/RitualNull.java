package elucent.roots.ritual.powers;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class RitualNull extends RitualPower{
	Random random = new Random();
	public RitualNull(){
		super("none",EnumPowerType.TYPE_TARGET_ENTITY, 0);
	}
	
	@Override
	public void onRightClickEntity(EntityPlayer player, World world, Entity entity){
	}
}
