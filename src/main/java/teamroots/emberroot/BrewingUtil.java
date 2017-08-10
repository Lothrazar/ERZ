package teamroots.emberroot;
 
import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

public class BrewingUtil {
  public static ItemStack getEmptyPotion(boolean isSplash){
    //in 1.11.2 brewing must start with potiontypes water
    //this was created to mimic vanilla ItemPotion.getDefaultInstance()
    ItemStack res;
    if(isSplash) {
      res = PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.WATER);
    } else {
      res = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
    }
    return res;
  }
  public static ItemStack createHarmingPotion(boolean isAugmented, boolean isSplash) {
    ItemStack res = getEmptyPotion(isSplash);
    PotionUtils.addPotionToItemStack(res, PotionTypes.HARMING);
    return res;            
  }
 

  public static ItemStack createHealthPotion(boolean isProlonged, boolean isAugmented, boolean isSplash) {
    ItemStack res = getEmptyPotion(isSplash);
    if(isProlonged || isAugmented) {
      PotionUtils.addPotionToItemStack(res, PotionTypes.STRONG_HEALING);
    } else {
      PotionUtils.addPotionToItemStack(res, PotionTypes.HEALING);
    }
    return res;
  }

  public static ItemStack createRegenerationPotion(boolean isProlonged, boolean isAugmented, boolean isSplash) {
    ItemStack res = getEmptyPotion(isSplash);
    if(isAugmented) {
      PotionUtils.addPotionToItemStack(res, PotionTypes.STRONG_REGENERATION);
    } else if(isProlonged) {
      PotionUtils.addPotionToItemStack(res, PotionTypes.LONG_REGENERATION);
    } else {
      PotionUtils.addPotionToItemStack(res, PotionTypes.REGENERATION);
    }
    return res;
  }

  public static void writeCustomEffectToNBT(PotionEffect effect, ItemStack stack) {
    NBTTagCompound nbt;
    if(stack.hasTagCompound()) {
      nbt = stack.getTagCompound();
    } else {
      nbt = new NBTTagCompound();
      stack.setTagCompound(nbt);
    }    
    writeCustomEffectToNBT(effect, nbt);    
  }

  public static void writeCustomEffectToNBT(PotionEffect effect, NBTTagCompound nbt) {
    NBTTagCompound effectNBT = new NBTTagCompound();        
    effect.writeCustomPotionEffectToNBT(effectNBT);
    
    NBTTagList effectList = new NBTTagList();
    effectList.appendTag(effectNBT);            
    
    nbt.setTag("CustomPotionEffects", effectList);
  }
  public static ItemStack createWitherPotion(boolean b, boolean c) {
     Set<PotionEffect> customPotionEffects = Sets.<PotionEffect>newHashSet();

ItemStack stuff = new ItemStack(Items.POTIONITEM);
    customPotionEffects.add(new PotionEffect( MobEffects.WITHER,44,44  ));
 
    return stuff;
  }

}
