
package elucent.roots;

import elucent.roots.capability.mana.ManaProvider;
import elucent.roots.component.components.ComponentCharmIllusion;
import elucent.roots.entity.EntityHomingProjectile;
import elucent.roots.entity.ISprite;
import elucent.roots.event.SpellCastEvent;
import elucent.roots.item.IManaRelatedItem;
import elucent.roots.item.ItemCrystalStaff;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventManager {
	Random random = new Random();
	@SubscribeEvent
	public void onBlockHarvested(HarvestDropsEvent event){
		if (event.getHarvester() != null){
			if (!(event.getHarvester() instanceof FakePlayer)){

				Block block = event.getState().getBlock();
				if (block == Blocks.TALLGRASS){
					if (random.nextInt(ConfigManager.oldRootDropChance) == 0){
						event.getDrops().add(new ItemStack(RegistryManager.oldRoot,1));
					}
				}
				if (block instanceof BlockCrops){
					if (((BlockCrops)block).isMaxAge(event.getState())){
						if (random.nextInt(ConfigManager.verdantSprigDropChance) == 0){
							event.getDrops().add(new ItemStack(RegistryManager.verdantSprig,1));
						}
					}
				}
				if (block == Blocks.NETHER_WART){
					if (((BlockNetherWart) block).getMetaFromState(event.getState()) != 0){
						if (random.nextInt(ConfigManager.infernalStemDropChance) == 0){
							event.getDrops().add(new ItemStack(RegistryManager.infernalStem,1));
						}
					}
				}
				if (block == Blocks.CHORUS_FLOWER){
					if (random.nextInt(ConfigManager.dragonsEyeDropChance) == 0){
						event.getDrops().clear();
						event.getDrops().add(new ItemStack(RegistryManager.dragonsEye,1));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRightClickEntity(PlayerInteractEvent.EntityInteract event){
		if (event.getEntityPlayer().getHeldItem(event.getHand()) != null){
			if (event.getEntityPlayer().getHeldItem(event.getHand()).getItem() == RegistryManager.debugWand){
				if (event.getTarget() instanceof ISprite){
					System.out.println(((ISprite)event.getTarget()).getHappiness());
				}
			}
		}
		if (event.getTarget() instanceof EntitySkeleton){
			if (event.getEntityPlayer().getHeldItem(event.getHand()) != null){
				if (event.getEntityPlayer().getHeldItem(event.getHand()).getItem() == RegistryManager.infernalStem){
					event.getEntityPlayer().getHeldItem(event.getHand()).stackSize --;
					((EntitySkeleton)event.getTarget()).func_189768_a(SkeletonType.WITHER);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingTarget(LivingSetAttackTargetEvent event){
		if (event.getTarget() instanceof EntityPlayer && event.getEntityLiving() instanceof EntityMob){
			if (event.getEntity().getEntityData().hasKey(RootsNames.TAG_DONT_TARGET_PLAYERS)){
				if (event.getTarget().getUniqueID().getMostSignificantBits() == event.getEntity().getEntityData().getLong(RootsNames.TAG_DONT_TARGET_PLAYERS)){
					((EntityMob)event.getEntityLiving()).setAttackTarget(null);
					List<EntityLivingBase> targets = event.getEntity().getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(event.getEntity().posX-4.0,event.getEntity().posY-4.0,event.getEntity().posZ-4.0, event.getEntity().posX+4.0,event.getEntity().posY+4.0,event.getEntity().posZ+4.0));
					if (targets.size() > 0){
						((EntityMob)event.getEntityLiving()).setAttackTarget(targets.get(random.nextInt(targets.size())));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onSpellCast(SpellCastEvent event){
		EntityPlayer player = event.getPlayer();
		for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
			if (player.inventory.getStackInSlot(i) != null){
				if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.amuletConserving){
					if (player.inventory.getStackInSlot(i).hasTagCompound()){
						player.inventory.getStackInSlot(i).getTagCompound().setInteger("charge", (int) (player.inventory.getStackInSlot(i).getTagCompound().getInteger("charge")+(4+2*event.getPotency())));
						return;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onItemPickup(PlayerEvent.ItemPickupEvent event){
		if (event.pickedUp != null){
			if (event.player != null){
				if (event.pickedUp.getEntityItem().getItem() == RegistryManager.dustPetal){
					if (!event.player.hasAchievement(RegistryManager.achieveDust)){
						PlayerManager.addAchievement(event.player, RegistryManager.achieveDust);
					}
				}
				if (event.pickedUp.getEntityItem().getItem() == Item.getItemFromBlock(RegistryManager.altar)){
					if (!event.player.hasAchievement(RegistryManager.achieveAltar)){
						PlayerManager.addAchievement(event.player, RegistryManager.achieveAltar);
					}
				}
				if (event.pickedUp.getEntityItem().getItem() == Item.getItemFromBlock(RegistryManager.standingStoneT2)){
					if (!event.player.hasAchievement(RegistryManager.achieveStandingStone)){
						PlayerManager.addAchievement(event.player, RegistryManager.achieveStandingStone);
					}
				}
				if (event.pickedUp.getEntityItem().getItem() == RegistryManager.crystalStaff){
					ArrayList<String> spells = new ArrayList<String>();
					spells.add(ItemCrystalStaff.getEffect(event.pickedUp.getEntityItem(),1));
					spells.add(ItemCrystalStaff.getEffect(event.pickedUp.getEntityItem(),2));
					spells.add(ItemCrystalStaff.getEffect(event.pickedUp.getEntityItem(),3));
					spells.add(ItemCrystalStaff.getEffect(event.pickedUp.getEntityItem(),4));
					if (spells.contains("netherwart") && spells.contains("dandelion") && spells.contains("blueorchid") && spells.contains("lilypad")){
						if (!event.player.hasAchievement(RegistryManager.achieveSpellElements)){
							PlayerManager.addAchievement(event.player, RegistryManager.achieveSpellElements);
						}
					}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void drawQuad(VertexBuffer vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV){
		float f = 0.00390625F;
        float f1 = 0.00390625F;
        vertexbuffer.pos((double)(x1 + 0.0F), (double)(y1 + 0.0F), (double)0).tex((double)((float)(minU + 0) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        vertexbuffer.pos((double)(x2 + 0.0F), (double)(y2 + 0.0F), (double)0).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        vertexbuffer.pos((double)(x3 + 0.0F), (double)(y3 + 0.0F), (double)0).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(x4 + 0.0F), (double)(y4 + 0.0F), (double)0).tex((double)((float)(minU + 0) * f), (double)((float)(minV + 0) * f1)).endVertex();
    }
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGameOverlayRender(RenderGameOverlayEvent.Post e){
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		boolean showBar = false;
		if (player.getHeldItemMainhand() != null){
			if (player.getHeldItemMainhand().getItem() instanceof IManaRelatedItem){
				showBar = true;
			}
		}
		if (player.getHeldItemOffhand() != null){
			if (player.getHeldItemOffhand().getItem() instanceof IManaRelatedItem){
				showBar = true;
			}
		}
		if (player.capabilities.isCreativeMode){
			showBar = false;
		}
		if (showBar){
			if (ManaProvider.get(player).getMana() > 0){
				if (e.getType() == ElementType.TEXT){
					GlStateManager.disableDepth();
					GlStateManager.disableCull();
					GlStateManager.pushMatrix();
					Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/manaBar.png"));
					
					Tessellator tess = Tessellator.getInstance();
					VertexBuffer b = tess.getBuffer();
					int w = e.getResolution().getScaledWidth();
					int h = e.getResolution().getScaledHeight();
					GlStateManager.color(1f, 1f, 1f, 1f);
					
					int manaNumber = Math.round(ManaProvider.get(player).getMana());
					int maxManaNumber = Math.round(ManaProvider.get(player).getMaxMana());
					
					int offsetX = 0;
					
					b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
					while (maxManaNumber > 0){
						this.drawQuad(b, w/2+10+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-ConfigManager.manaBarOffset, w/2+10+offsetX, h-ConfigManager.manaBarOffset, 0, 0, 9, 9);
						if (maxManaNumber > 4){
							maxManaNumber -= 4;
							offsetX += 8;
						}
						else {
							maxManaNumber = 0;
						}
					}
					offsetX = 0;
					while (manaNumber > 0){
						if (manaNumber > 4){
							this.drawQuad(b, w/2+10+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-ConfigManager.manaBarOffset, w/2+10+offsetX, h-ConfigManager.manaBarOffset, 0, 16, 9, 9);
							manaNumber -= 4;
							offsetX += 8;
						}
						else {
							if (manaNumber == 4){
								this.drawQuad(b, w/2+10+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-ConfigManager.manaBarOffset, w/2+10+offsetX, h-ConfigManager.manaBarOffset, 0, 16, 9, 9);
							}
							if (manaNumber == 3){
								this.drawQuad(b, w/2+10+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-ConfigManager.manaBarOffset, w/2+10+offsetX, h-ConfigManager.manaBarOffset, 16, 16, 9, 9);
							}
							if (manaNumber == 2){
								this.drawQuad(b, w/2+10+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-ConfigManager.manaBarOffset, w/2+10+offsetX, h-ConfigManager.manaBarOffset, 32, 16, 9, 9);
							}
							if (manaNumber == 1){
								this.drawQuad(b, w/2+10+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-(ConfigManager.manaBarOffset-9), w/2+19+offsetX, h-ConfigManager.manaBarOffset, w/2+10+offsetX, h-ConfigManager.manaBarOffset, 48, 16, 9, 9);
							}
							manaNumber = 0;
						}
					}
					tess.draw();
					
					GlStateManager.popMatrix();
					GlStateManager.enableCull();
					GlStateManager.enableDepth();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onItemCraft(ItemCraftedEvent event){
		if (event.player != null){
			if (event.crafting != null){
				if (event.crafting.getItem() == Item.getItemFromBlock(RegistryManager.altar)){
					if (!event.player.hasAchievement(RegistryManager.achieveAltar)){
						PlayerManager.addAchievement(event.player, RegistryManager.achieveAltar);
					}
				}
				if (event.crafting.getItem() == Item.getItemFromBlock(RegistryManager.standingStoneT2)){
					if (!event.player.hasAchievement(RegistryManager.achieveStandingStone)){
						PlayerManager.addAchievement(event.player, RegistryManager.achieveStandingStone);
					}
				}
			}
		}
	}

	public static int timer = 200, defaultTime = 200;
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingTick(LivingUpdateEvent event){
		if (event.getEntityLiving() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (PlayerManager.getPersistedPlayerTag(player).hasKey(RootsNames.TAG_RITUAL_POWER_COOLDOWN)){
				if (PlayerManager.getPersistedPlayerTag(player).getInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN) > 0){
					PlayerManager.getPersistedPlayerTag(player).setInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN, PlayerManager.getPersistedPlayerTag(player).getInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN)-1);
				}
				if (PlayerManager.getPersistedPlayerTag(player).getInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN) < 0){
					PlayerManager.getPersistedPlayerTag(player).setInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN, 0);
				}
			}
			if (event.getEntityLiving().ticksExisted % 5 == 0){
				if (event.getEntityLiving().hasCapability(ManaProvider.manaCapability, null)){
					if (event.getEntityLiving().getCapability(ManaProvider.manaCapability, null).getMaxMana() == 0){
						event.getEntityLiving().getCapability(ManaProvider.manaCapability, null).setMaxMana(40.0f);
					}
					ManaProvider.get(player).setMana(player, ManaProvider.get(player).getMana()+1.0f);
				}
			}
		}
		if (event.getEntityLiving().getEntityData().hasKey(RootsNames.TAG_TRACK_TICKS)){
			if (event.getEntityLiving().getEntityData().hasKey(RootsNames.TAG_SPELL_SKIP_TICKS)){
				if (event.getEntityLiving().getEntityData().getInteger(RootsNames.TAG_SPELL_SKIP_TICKS) > 0){
					if (event.getEntityLiving().getHealth() <= 0){
						if (event.getEntityLiving().getLastAttacker() instanceof EntityPlayer){
							if (!((EntityPlayer)event.getEntityLiving().getLastAttacker()).hasAchievement(RegistryManager.achieveTimeStop)){
								PlayerManager.addAchievement((EntityPlayer)event.getEntityLiving().getLastAttacker(), RegistryManager.achieveTimeStop);
							}
						}
					}
					event.getEntityLiving().getEntityData().setInteger(RootsNames.TAG_SPELL_SKIP_TICKS, event.getEntityLiving().getEntityData().getInteger(RootsNames.TAG_SPELL_SKIP_TICKS)-1);
					if (event.getEntityLiving().getEntityData().getInteger(RootsNames.TAG_SPELL_SKIP_TICKS) <= 0){
						event.getEntityLiving().getEntityData().removeTag(RootsNames.TAG_SPELL_SKIP_TICKS);
						Util.decrementTickTracking(event.getEntityLiving());
					}
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event){
		if (event.getEntityLiving().getEntityData().hasKey(RootsNames.TAG_SKIP_ITEM_DROPS)){
			if (!event.getEntityLiving().getEntityData().getBoolean(RootsNames.TAG_SKIP_ITEM_DROPS)){
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingXP(LivingExperienceDropEvent event){
		if (event.getEntityLiving().getEntityData().hasKey(RootsNames.TAG_SKIP_ITEM_DROPS)){
			if (!event.getEntityLiving().getEntityData().getBoolean(RootsNames.TAG_SKIP_ITEM_DROPS)){
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onChat(ServerChatEvent event){
		if (event.getComponent().getFormattedText().equals("ZA WARUDO")){
			EntityPlayerMP player = event.getPlayer();
			if (player.getGameProfile().getName().compareToIgnoreCase("LuckyLucyF") == 0 || player.getGameProfile().getName().compareToIgnoreCase("Elucent") == 0 || player.getGameProfile().getName().compareToIgnoreCase("ShadowGamerXY") == 0 || player.getGameProfile().getName().compareToIgnoreCase("werty1124") == 0){
				for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
					if (player.inventory.getStackInSlot(i) != null){
						if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.ancientHourglass){
							player.inventory.getStackInSlot(i).stackSize --;
							if (player.inventory.getStackInSlot(i).stackSize <= 0){
								player.inventory.removeStackFromSlot(i);
							}
							List<Entity> entities = player.getEntityWorld().getLoadedEntityList();
							for (int j = 0; j < entities.size(); j ++){
								boolean canFreeze = true;
								if (entities.get(j) instanceof EntityPlayer){
									EntityPlayer temp = ((EntityPlayer)entities.get(j));
									if (temp.getGameProfile().getName().compareToIgnoreCase("LuckyLucyF") == 0 || temp.getGameProfile().getName().compareToIgnoreCase("Elucent") == 0 || temp.getGameProfile().getName().compareToIgnoreCase("ShadowGamerXY") == 0 || temp.getGameProfile().getName().compareToIgnoreCase("werty1124") == 0){
										canFreeze = false;
									}									
								}
								if (canFreeze){
									Util.addTickTracking(entities.get(j));
									entities.get(j).getEntityData().setInteger(RootsNames.TAG_SPELL_SKIP_TICKS, 400);
								}
							}	
							player.getEntityWorld().playSound(player.posX, player.posY, player.posZ, SoundType.GLASS.getBreakSound(), SoundCategory.BLOCKS, random.nextFloat()*0.1f+0.95f, random.nextFloat()*0.1f+0.95f, false);
							return;
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDeathHighPriority(LivingDeathEvent event){
		if (event.getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.getEntity();
			for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
				if (player.inventory.getStackInSlot(i) != null){
					if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.ancientHourglass){
						player.setHealth(1.0f);
						event.setCanceled(true);
						player.inventory.getStackInSlot(i).stackSize --;
						if (player.inventory.getStackInSlot(i).stackSize <= 0){
							player.inventory.removeStackFromSlot(i);
						}
						List<EntityLivingBase> entities = player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX-32.0,player.posY-32.0,player.posZ-32.0,player.posX+32.0,player.posY+32.0,player.posZ+32.0));
						for (int j = 0; j < entities.size(); j ++){
							Util.addTickTracking(entities.get(j));
							entities.get(j).getEntityData().setInteger(RootsNames.TAG_SPELL_SKIP_TICKS, 200);
						}	
						player.getEntityWorld().playSound(player.posX, player.posY, player.posZ, SoundType.GLASS.getBreakSound(), SoundCategory.BLOCKS, random.nextFloat()*0.1f+0.95f, random.nextFloat()*0.1f+0.95f, false);
						for (int j = 0; j < 20; j ++){
							Roots.proxy.spawnParticleMagicSparkleFX(player.getEntityWorld(), event.getEntityLiving().posX, event.getEntityLiving().posY+event.getEntityLiving().height/2.0f, event.getEntityLiving().posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
						}
						return;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event){
		if (event.getSource().getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.getSource().getEntity();
			for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
				if (player.inventory.getStackInSlot(i) != null){
					if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.talismanPursuit && !player.getEntityWorld().isRemote){
						if (event.getEntityLiving() instanceof EntityAnimal){
							List<EntityAnimal> animals = event.getEntity().getEntityWorld().getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(event.getEntity().posX-10.0,event.getEntity().posY-10.0,event.getEntity().posZ-10.0,event.getEntity().posX+10.0,event.getEntity().posY+10.0,event.getEntity().posZ+10.0));
							ArrayList<EntityAnimal> trimmedAnimals = new ArrayList<EntityAnimal>();
							for (int j = 0; j < animals.size(); j ++){
								if (animals.get(j).getUniqueID().compareTo(event.getEntityLiving().getUniqueID()) != 0){
									trimmedAnimals.add(animals.get(j));
								}
							}
							if (trimmedAnimals.size() > 0){
								EntityHomingProjectile proj = new EntityHomingProjectile(player.getEntityWorld());
								proj.setPosition(event.getEntity().posX, event.getEntity().posY+event.getEntity().height/2.0f, event.getEntity().posZ);
								proj.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(event.getEntity().getPosition()), null);
								proj.setVelocity(random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5);
				    			proj.initSpecial(trimmedAnimals.get(0), 4.0f, new Vec3d(234,41,255));
								player.getEntityWorld().spawnEntityInWorld(proj);
							}
						}
						if (event.getEntityLiving() instanceof EntityMob){
							List<EntityMob> mobs = event.getEntity().getEntityWorld().getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(event.getEntity().posX-10.0,event.getEntity().posY-10.0,event.getEntity().posZ-10.0,event.getEntity().posX+10.0,event.getEntity().posY+10.0,event.getEntity().posZ+10.0));
							ArrayList<EntityMob> trimmedMobs = new ArrayList<EntityMob>();
							for (int j = 0; j < mobs.size(); j ++){
								if (mobs.get(j).getUniqueID().compareTo(event.getEntityLiving().getUniqueID()) != 0){
									trimmedMobs.add(mobs.get(j));
								}
							}
							if (trimmedMobs.size() > 0){
								EntityHomingProjectile proj = new EntityHomingProjectile(player.getEntityWorld());
								proj.setPosition(event.getEntity().posX, event.getEntity().posY+event.getEntity().height/2.0f, event.getEntity().posZ);
								proj.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(event.getEntity().getPosition()), null);
								proj.setVelocity(random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5);
				    			proj.initSpecial(trimmedMobs.get(0), 4.0f, new Vec3d(234,41,255));
								player.getEntityWorld().spawnEntityInWorld(proj);
							}
						}
						if (event.getEntityLiving() instanceof EntityPlayer){
							List<EntityPlayer> players = event.getEntity().getEntityWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getEntity().posX-10.0,event.getEntity().posY-10.0,event.getEntity().posZ-10.0,event.getEntity().posX+10.0,event.getEntity().posY+10.0,event.getEntity().posZ+10.0));
							ArrayList<EntityPlayer> trimmedPlayers = new ArrayList<EntityPlayer>();
							for (int j = 0; j < players.size(); j ++){
								if (players.get(j).getUniqueID().compareTo(player.getUniqueID()) != 0){
									trimmedPlayers.add(players.get(j));
								}
							}
							if (trimmedPlayers.size() > 0){
								EntityHomingProjectile proj = new EntityHomingProjectile(player.getEntityWorld());
								proj.setPosition(event.getEntity().posX, event.getEntity().posY+event.getEntity().height/2.0f, event.getEntity().posZ);
								proj.onInitialSpawn(player.getEntityWorld().getDifficultyForLocation(event.getEntity().getPosition()), null);
								proj.setVelocity(random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5);
				    			proj.initSpecial(trimmedPlayers.get(0), 4.0f, new Vec3d(234,41,255));
								player.getEntityWorld().spawnEntityInWorld(proj);
							}
						}
						return;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDamage(LivingHurtEvent event){
		if (event.getSource().getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.getSource().getEntity();
			for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
				if (player.inventory.getStackInSlot(i) != null){
					if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.amuletConserving){
						if (player.inventory.getStackInSlot(i).getTagCompound().getInteger("charge") > 40){
							player.inventory.getStackInSlot(i).getTagCompound().setInteger("charge",0);
							event.getEntityLiving().setHealth(event.getEntityLiving().getHealth()-2.0f);
							for (int j = 0; j < 20; j ++){
								Roots.proxy.spawnParticleMagicSparkleFX(player.getEntityWorld(), event.getEntityLiving().posX, event.getEntityLiving().posY+event.getEntityLiving().height/2.0f, event.getEntityLiving().posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
							}
							return;
						}
					}
				}
			}
		}
		if (event.getEntityLiving() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			if (event.getSource().isExplosion() || event.getSource().isFireDamage() || event.getSource().isMagicDamage()){
				for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
					if (player.inventory.getStackInSlot(i) != null){
						if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.talismanHunger){
							if (player.inventory.getStackInSlot(i).hasTagCompound()){
								if (player.inventory.getStackInSlot(i).getTagCompound().getInteger("timer") == 0){
									event.setCanceled(true);
									player.inventory.getStackInSlot(i).getTagCompound().setInteger("timer", ConfigManager.hungerTalismanTimer);
									return;
								}
							}
						}
					}
				}
			}
			if (event.getAmount() > 6.0f){
				for (int i = 0; i < player.inventory.getSizeInventory(); i ++){
					if (player.inventory.getStackInSlot(i) != null){
						if (player.inventory.getStackInSlot(i).getItem() == RegistryManager.amuletDischarge){
							if (player.inventory.getStackInSlot(i).hasTagCompound()){
								if (player.inventory.getStackInSlot(i).getTagCompound().getInteger("timer") == 0){
									List<EntityLivingBase> entities = player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX-4.0,player.posY-4.0,player.posZ-4.0,player.posX+4.0,player.posY+4.0,player.posZ+4.0));
									for (int j = 0; j < entities.size(); j ++){
										if (entities.get(j).getUniqueID().compareTo(player.getUniqueID()) != 0){
											entities.get(j).attackEntityFrom(DamageSource.generic, 3.0f);
										}
									}
									for (double j = 0; j < 360; j += 30){
										double dx = Math.sin(Math.toRadians(j));
										double dz = Math.cos(Math.toRadians(j));
										Roots.proxy.spawnParticleMagicSmallSparkleFX(player.getEntityWorld(), player.posX+dx, player.posY+1.0, player.posZ+dz, 0.025f*dx, 0, 0.025f*dz, 76, 230, 0);
									}
									player.inventory.getStackInSlot(i).getTagCompound().setInteger("timer", ConfigManager.dischargeAmuletTimer);
									return;
								}
							}
						}
					}
				}
			}
		}
		
		if (event.getEntityLiving().getEntityData().hasKey(RootsNames.TAG_SPELL_VULNERABILITY)){
			event.setAmount((float) (event.getAmount()*(1.0+event.getEntityLiving().getEntityData().getDouble("RMOD_vuln"))));
			event.getEntityLiving().getEntityData().removeTag(RootsNames.TAG_SPELL_VULNERABILITY);
		}
		
		if (event.getEntityLiving().getEntityData().hasKey(RootsNames.TAG_SPELL_THORNS_DAMAGE) && event.getSource().getEntity() instanceof EntityLivingBase){
			((EntityLivingBase)event.getSource().getEntity()).attackEntityFrom(DamageSource.cactus, event.getEntityLiving().getEntityData().getFloat(RootsNames.TAG_SPELL_THORNS_DAMAGE));
			event.getEntityLiving().getEntityData().removeTag(RootsNames.TAG_SPELL_THORNS_DAMAGE);
			Util.decrementTickTracking(event.getEntityLiving());
		}	
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent event){
		ResourceLocation magicParticleRL = new ResourceLocation("roots:entity/magicParticle");
		event.getMap().registerSprite(magicParticleRL);
		ResourceLocation sparkleRL = new ResourceLocation("roots:entity/sparkle");
		event.getMap().registerSprite(sparkleRL);
	}

	@SubscribeEvent
	public void onUpdateAttack(LivingEvent.LivingUpdateEvent event)
	{
		onLivingAttack(event);
	}
	
	@SubscribeEvent
	public void onTargetEvent(LivingSetAttackTargetEvent event)
	{
		setTarget(event);
	}

	private void onLivingAttack(LivingEvent event) {
		if (!(event.getEntity() instanceof EntityLiving))
			return;
		EntityLiving entity = (EntityLiving) event.getEntity();

		if (entity.getAttackTarget() == null || !(entity.getAttackTarget() instanceof EntityPlayer))
			return;
		if (!entity.worldObj.isRemote && ComponentCharmIllusion.doStuff && timer != defaultTime && timer > 0 && !isEntityAttacked) {
			entity.setAttackTarget(null);
			entity.setRevengeTarget(null);
		}
	}

	public static boolean isEntityAttacked = false;

	@SubscribeEvent
	public void onAttackEvent(LivingHurtEvent event) {
		isEntityAttacked = true;
	}


	private void setTarget(LivingSetAttackTargetEvent event) {
		if (event.getTarget() == null)
			return;
		if (!(event.getTarget() instanceof EntityPlayer))
			return;
		if (!(event.getEntity() instanceof EntityLiving))
			return;

			EntityLiving entity = (EntityLiving) event.getEntity();

		if (!entity.worldObj.isRemote && ComponentCharmIllusion.doStuff && timer != defaultTime && timer > 0 && !isEntityAttacked) {
			entity.setAttackTarget(null);
			entity.setRevengeTarget(null);
		}
	}

	@SubscribeEvent
	public void timer(TickEvent.WorldTickEvent event) {
		if (ComponentCharmIllusion.doStuff == true) ;
		{
			if (timer > 0) {
				timer--;
			}
			if (ComponentCharmIllusion.doStuff == false) {
				timer = defaultTime;
			}
		}
		if(timer == 0)
		{
			ComponentCharmIllusion.doStuff = false;
		}
		//DebugOnly
		/*if (timer > 0 && timer != defaultTime) {
			System.out.println(timer);
		}*/
	}
}
