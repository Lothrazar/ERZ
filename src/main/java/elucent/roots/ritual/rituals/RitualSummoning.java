package elucent.roots.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.entity.EntitySummoner;
import elucent.roots.ritual.RitualBase;

public class RitualSummoning extends RitualBase {
	public Class result = null;
	
	public RitualSummoning setResult(Class entity){
		this.result = entity;
		return this;
	}

	public RitualSummoning(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		if (Util.itemListsMatchWithSize(inventory, this.ingredients)){
			EntitySummoner summoner = new EntitySummoner(world,result,pos.getX()+0.5,pos.getY()+2.5,pos.getZ()+0.5,color,secondaryColor,false);
			world.spawnEntityInWorld(summoner);
			inventory.clear();
			world.getTileEntity(pos).markDirty();
		}
	}
}
