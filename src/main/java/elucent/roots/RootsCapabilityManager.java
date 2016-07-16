package elucent.roots;

import elucent.roots.capability.DefaultManaCapability;
import elucent.roots.capability.IManaCapability;
import elucent.roots.capability.ManaCapabilityStorage;
import elucent.roots.capability.powers.DefaultPowerCapability;
import elucent.roots.capability.powers.IPowersCapability;
import elucent.roots.capability.powers.PowerCapabilityStorage;
import elucent.roots.capability.powers.PowerProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RootsCapabilityManager {
    @CapabilityInject(IManaCapability.class)
    public static final Capability<IManaCapability> manaCapability = null;
    
    public static void preInit(){
    	CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability.class);
    	CapabilityManager.INSTANCE.register(IPowersCapability.class, new PowerCapabilityStorage(), DefaultPowerCapability.class);
    }
	
	@SubscribeEvent
	public void onAddCapabilities(AttachCapabilitiesEvent.Entity e){
		class ManaCapabilityProvider implements ICapabilityProvider, INBTSerializable, IManaCapability {
            private EntityPlayer player;
            
            float mana = 40.0f;
            float maxMana = 40.0f;

            ManaCapabilityProvider(boolean isNew){
            }
            
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing){
                return manaCapability != null && capability == manaCapability;
            }
            
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing){
            	if (capability == manaCapability){
            		return manaCapability.cast(this);
            	}
            	return null;
            }

			@Override
			public float getMana() {
				return mana;
			}

			@Override
			public float getMaxMana() {
				return maxMana;
			}

			@Override
			public void setMana(float mana) {
				this.mana = mana;
				if (mana < 0){
					this.mana = 0;
				}
				if (mana > getMaxMana()){
					this.mana = getMaxMana();
				}
			}

			@Override
			public void setMaxMana(float maxMana) {
				this.maxMana = maxMana;
			}

			@Override
			public NBTBase serializeNBT() {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setFloat("maxMana", getMaxMana());
				tag.setFloat("mana", getMana());
				return tag;
			}

			@Override
			public void deserializeNBT(NBTBase nbt) {
				if (nbt instanceof NBTTagCompound){
					NBTTagCompound tag = (NBTTagCompound)nbt;
					//System.out.println("Loading NBT! Mana=" + tag.getFloat("mana") + "/" + tag.getFloat("maxMana"));
					if (tag.hasKey("maxMana")){
						setMaxMana(tag.getFloat("maxMana"));
						setMana(tag.getFloat("mana"));
						return;
					}
				}
				setMaxMana(40.0f);
				setMana(40.0f);
			}
        }
		
		if (e.getEntity() instanceof EntityPlayer){
			ManaCapabilityProvider provider = new ManaCapabilityProvider(!e.getEntity().hasCapability(manaCapability, null));
			e.addCapability(new ResourceLocation("roots:manaCapability"), provider);
			if(!e.getEntity().hasCapability(PowerProvider.powerCapability, null)){
				e.addCapability(new ResourceLocation("roots:powerCapability"), new PowerProvider(new DefaultPowerCapability()));
			}
		}
	}
}
