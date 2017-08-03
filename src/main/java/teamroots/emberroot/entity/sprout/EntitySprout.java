package teamroots.emberroot.entity.sprout;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import teamroots.emberroot.ConfigManager;
import teamroots.emberroot.Const;
import teamroots.emberroot.util.Misc;

public class EntitySprout extends EntityCreature {
	public static final DataParameter<Integer> variant = EntityDataManager.<Integer>createKey(EntitySprout.class, DataSerializers.VARINT);

	public SoundEvent ambientSound = new SoundEvent(new ResourceLocation(Const.MODID,"darkoAmbient"));
	public EntitySprout(World world){
		super(world);
		setSize(0.5f,1.0f);
		this.experienceValue = 3;
	}
	
	@Override
	protected void entityInit(){
		super.entityInit();
		this.getDataManager().register(variant, rand.nextInt(3));
	}

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }
	
	@Override
	public ResourceLocation getLootTable(){
		switch(getDataManager().get(EntitySprout.variant)){
			case 0: { return new ResourceLocation(Const.MODID,"entity/sprout_green");}
			case 1: { return new ResourceLocation(Const.MODID,"entity/sprout_tan");}
			case 2: { return new ResourceLocation(Const.MODID,"entity/sprout_red");}
			default: {return new ResourceLocation(Const.MODID,"entity/sprout_green");}
		}
	}
    
    @Override
    public void onUpdate(){
    	super.onUpdate();
    	if (world.isRemote){
    		if (Misc.random.nextInt(480) == 0 && ConfigManager.enableSilliness && Minecraft.getMinecraft().player.getGameProfile().getName().equalsIgnoreCase("Darkosto")){
    			world.playSound(Minecraft.getMinecraft().player, getPosition(), ambientSound, SoundCategory.NEUTRAL, 1.0f, 0.8f+0.4f*Misc.random.nextFloat());
    		}
        }
    	this.rotationYaw = this.rotationYawHead;
    }

    public float getEyeHeight()
    {
        return this.isChild() ? this.height : 1.3F;
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		getDataManager().set(variant, compound.getInteger("variant"));
		getDataManager().setDirty(variant);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("variant", getDataManager().get(variant));
	}
}