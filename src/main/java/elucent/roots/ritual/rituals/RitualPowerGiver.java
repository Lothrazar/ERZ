package elucent.roots.ritual.rituals;

import java.util.List;
import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.RootsNames;
import elucent.roots.ritual.RitualBase;
import elucent.roots.ritual.RitualPower;
import elucent.roots.ritual.powers.RitualPowerFlare;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPowerGiver extends RitualBase {
	Random random = new Random();
	public RitualPower power = null;
	
	public RitualPowerGiver(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	public RitualPowerGiver setPower(RitualPower power){
		this.power = power;
		return this;
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		inventory.clear();
		List<EntityPlayer> players = (List<EntityPlayer>)world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX()-2,pos.getY()-2,pos.getZ()-2,pos.getX()+3,pos.getY()+3,pos.getZ()+3));
		if (players.size() > 0){
			for (int i = 0; i < players.size(); i ++){
				if (!players.get(i).getEntityData().hasKey(RootsNames.TAG_HAS_RITUAL_POWER)){
					players.get(i).getEntityData().setBoolean(RootsNames.TAG_HAS_RITUAL_POWER,true);
					players.get(i).getEntityData().setInteger(power.getTagName(), 20);
					for (int j = 0; j < 40; j ++){
						Roots.proxy.spawnParticleMagicFX(world, players.get(i).posX, players.get(i).posY, players.get(i).posZ, Math.pow(1.5f*(random.nextFloat()-0.5f),3.0), Math.pow(1.5f*(random.nextFloat()-0.5f),3.0), Math.pow(1.5f*(random.nextFloat()-0.5f),3.0), color.xCoord, color.yCoord, color.zCoord);
					}
				}
			}
		}
	}
}
