package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.util.text.TextFormatting;

import elucent.roots.Roots;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntitySpritePlacator;
import elucent.roots.entity.ISprite;
import elucent.roots.item.DustPetal;
import elucent.roots.item.ItemStaff;
import elucent.roots.ritual.RitualBase;
import elucent.roots.ritual.RitualManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntitySpiritConduit extends TEBase implements ITickable {
	int ticker = 0;
	Random random = new Random();
	float angle = 0;
	float power = 0;
	boolean powered = false;
	float totalPower = 0;
	public TileEntitySpiritConduit(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		return tag;
	}

	@Override
	public void update() {
		angle += 24.0f;
		ticker ++;angle += 24.0f;
		ticker ++;
		if (getWorld().isRemote){
			if (getWorld().provider.getDimension() == Minecraft.getMinecraft().thePlayer.getEntityWorld().provider.getDimension()){
				if (ticker % 20 == 0){
					Roots.proxy.spawnParticleMagicAltarLineFX(getWorld(), getPos().getX()+0.5f+11f*(random.nextFloat()-0.5f), getPos().getY()+0.5f+3f*(random.nextFloat()-0.5f), getPos().getZ()+0.5f+11f*(random.nextFloat()-0.5f), getPos().getX()+0.5f, getPos().getY()+0.5f, getPos().getZ()+0.5f, 107, 255, 28);
				}
				if (ticker % 2 == 0){
					Roots.proxy.spawnParticleMagicSmallSparkleFX(getWorld(), getPos().getX()+0.125+random.nextFloat()*0.75, getPos().getY()+0.375+random.nextFloat()*0.75,getPos().getZ()+0.125+random.nextFloat()*0.75, 0, 0, 0, 107, 255, 28);
				}
			}
		}
		if (ticker % 150 == 0){
			this.power = 0;
			for (int i = -5; i < 6; i ++){
				for (int j = -5; j < 6; j ++){
					for (int k = -1; k < 2; k ++){
						BlockPos pos = getPos().add(i,k,j);
						if (getWorld().getBlockState(pos.up()).getBlock() != Blocks.AIR){
							pos = pos.up();
						}
						power += 4.0f*Util.getNatureAmount(getWorld().getBlockState(pos));
					}
				}
			}
			List<EntityLivingBase> entities = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPos().getX()-10.0,getPos().getY()-10.0,getPos().getZ()-10.0,getPos().getX()+11.0,getPos().getY()+11.0,getPos().getZ()+11.0));
			for (int i = 0; i < entities.size(); i ++){
				if (entities.get(i) instanceof ISprite){
					if (!((ISprite)entities.get(i)).getTargetPosition().equals(getPos())){
						((ISprite)entities.get(i)).setTargetPosition(getPos().up());
						Vec3d velocity = new Vec3d(entities.get(i).posX-(getPos().getX()+0.5),
								entities.get(i).posY-(getPos().getY()+0.5),
								entities.get(i).posZ-(getPos().getZ()+0.5));
						velocity = velocity.scale(1.0/40.0);
						if (getWorld().isRemote){
							if (getWorld().provider.getDimension() == Minecraft.getMinecraft().thePlayer.getEntityWorld().provider.getDimension()){
								Roots.proxy.spawnParticleMagicSparkleScalableFX(getWorld(), 40, getPos().getX()+0.5+0.1*(random.nextDouble()-0.5), getPos().getY()+0.5+0.1*(random.nextDouble()-0.5), getPos().getZ()+0.5+0.1*(random.nextDouble()-0.5), velocity.xCoord, velocity.yCoord, velocity.zCoord, 2.5f, 107, 255, 28);
							}
						}
					}
				}
			}
		}
	}
}
