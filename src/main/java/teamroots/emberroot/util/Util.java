package teamroots.emberroot.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Util {
  public static Random random = new Random();
  public static ArrayList<IBlockState> oreList = new ArrayList<IBlockState>();
  public static ArrayList<Block> naturalBlocks = new ArrayList<Block>();
  public static double randomDouble(double min, double max) {
    double range = max - min;
    double scale = random.nextDouble() * range;
    double shifted = scale + min;
    return shifted;
  }
  public static float fastSin(float x) {
    if (x < -3.14159265) {
      x += 6.28318531;
    }
    else {
      if (x > 3.14159265) {
        x -= 6.28318531;
      }
    }
    if (x < 0) {
      return (float) (1.27323954 * x + .405284735 * x * x);
    }
    else {
      return (float) (1.27323954 * x - 0.405284735 * x * x);
    }
  }
  public static float fastCos(float x) {
    if (x < -3.14159265) {
      x += 6.28318531;
    }
    else {
      if (x > 3.14159265) {
        x -= 6.28318531;
      }
    }
    x += 1.57079632;
    if (x > 3.14159265) {
      x -= 6.28318531;
    }
    if (x < 0) {
      return (float) (1.27323954 * x + 0.405284735 * x * x);
    }
    else {
      return (float) (1.27323954 * x - 0.405284735 * x * x);
    }
  }
  public static boolean hasOreDictPrefix(ItemStack stack, String dict) {
    int[] ids = OreDictionary.getOreIDs(stack);
    for (int i = 0; i < ids.length; i++) {
      if (OreDictionary.getOreName(ids[i]).length() >= dict.length()) {
        if (OreDictionary.getOreName(ids[i]).substring(0, dict.length()).compareTo(dict.substring(0, dict.length())) == 0) { return true; }
      }
    }
    return false;
  }
  public static float yawDegreesBetweenPointsSafe(double posX, double posY, double posZ, double posX2, double posY2, double posZ2, double previousYaw) {
    float f = (float) ((180.0f * Math.atan2(posX2 - posX, posZ2 - posZ)) / (float) Math.PI);
    if (Math.abs(f - previousYaw) > 90) {
      if (f < previousYaw) {
        f += 360.0;
      }
      else {
        f -= 360.0;
      }
    }
    return f;
  }
  public static float yawDegreesBetweenPoints(double posX, double posY, double posZ, double posX2, double posY2, double posZ2) {
    float f = (float) ((180.0f * Math.atan2(posX2 - posX, posZ2 - posZ)) / (float) Math.PI);
    return f;
  }
  public static Vec3d lookVector(float rotYaw, float rotPitch) {
    return new Vec3d(
        Math.sin(rotYaw) * Math.cos(rotPitch),
        Math.sin(rotPitch),
        Math.cos(rotYaw) * Math.cos(rotPitch));
  }
  public static float interpolateYawDegrees(float angle1, float ratio1, float angle2, float ratio2) {
    if (Math.abs(angle1 - angle2) > 180) {
      if (angle2 > angle1) {
        angle2 -= 360;
      }
      else {
        angle1 -= 360;
      }
    }
    return angle1 * ratio1 + angle2 * ratio2;
  }
  public static float pitchDegreesBetweenPoints(double posX, double posY, double posZ, double posX2, double posY2, double posZ2) {
    return (float) Math.toDegrees(Math.atan2(posY2 - posY, Math.sqrt((posX2 - posX) * (posX2 - posX) + (posZ2 - posZ) * (posZ2 - posZ))));
  }
  public static double interpolate(float s, float e, float t) {
    double t2 = (1.0 - fastCos(t * 3.14159265358979323f)) / 2.0;
    return (s * (1.0 - t2) + (e) * t2);
  }
  public static BlockPos getRayTrace(World world, EntityPlayer player, int reachDistance) {
    double x = player.posX;
    double y = player.posY + player.getEyeHeight();
    double z = player.posZ;
    for (int i = 0; i < reachDistance * 4.0; i++) {
      x += player.getLookVec().x * 0.25;
      y += player.getLookVec().y * 0.25;
      z += player.getLookVec().z * 0.25;
      if (world.getBlockState(new BlockPos(x, y, z)).isFullCube()) { return new BlockPos(x, y, z); }
    }
    return new BlockPos(x, y, z);
  }
  public static BlockPos getRayTraceNonFull(World world, EntityPlayer player, int reachDistance) {
    double x = player.posX;
    double y = player.posY + player.getEyeHeight();
    double z = player.posZ;
    for (int i = 0; i < reachDistance * 4.0; i++) {
      x += player.getLookVec().x * 0.25;
      y += player.getLookVec().y * 0.25;
      z += player.getLookVec().z * 0.25;
      if (!world.isAirBlock(new BlockPos(x, y, z))) { return new BlockPos(x, y, z); }
    }
    return new BlockPos(x, y, z);
  }
  //	public static void addTickTracking(Entity entity){
  //		if (entity.getEntityData().hasKey(RootsNames.TAG_TRACK_TICKS)){
  //			entity.getEntityData().setInteger(RootsNames.TAG_TRACK_TICKS, entity.getEntityData().getInteger(RootsNames.TAG_TRACK_TICKS)+1);
  //		}
  //		else {
  //			entity.getEntityData().setInteger(RootsNames.TAG_TRACK_TICKS, 1);
  //		}
  //	}
  //	
  //	public static void decrementTickTracking(Entity entity){
  //		if (entity.getEntityData().hasKey(RootsNames.TAG_TRACK_TICKS)){
  //			entity.getEntityData().setInteger(RootsNames.TAG_TRACK_TICKS, entity.getEntityData().getInteger(RootsNames.TAG_TRACK_TICKS)-1);
  //			if (entity.getEntityData().getInteger(RootsNames.TAG_TRACK_TICKS) == 0){
  //				entity.removeTag(RootsNames.TAG_TRACK_TICKS);
  //			}
  //		}
  //	}
  public static Entity getRayTraceEntity(World world, EntityPlayer player, int reachDistance) {
    double x = player.posX;
    double y = player.posY + player.getEyeHeight();
    double z = player.posZ;
    for (int i = 0; i < reachDistance * 100.0; i++) {
      x += player.getLookVec().x * 0.01;
      y += player.getLookVec().y * 0.01;
      z += player.getLookVec().z * 0.01;
      List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - 0.1, y - 0.1, z - 0.1, x + 0.1, y + 0.1, z + 0.1));
      if (entities.size() > 0) {
        if (entities.get(0).getUniqueID() != player.getUniqueID()) { return entities.get(0); }
      }
    }
    return null;
  }
  public static void initOres() {
    oreList.add(Blocks.IRON_ORE.getDefaultState());
    oreList.add(Blocks.GOLD_ORE.getDefaultState());
    oreList.add(Blocks.DIAMOND_ORE.getDefaultState());
    oreList.add(Blocks.REDSTONE_ORE.getDefaultState());
    oreList.add(Blocks.LAPIS_ORE.getDefaultState());
    oreList.add(Blocks.COAL_ORE.getDefaultState());
  }
  public static void initNaturalBlocks() {
    naturalBlocks.add(Blocks.TALLGRASS);
    naturalBlocks.add(Blocks.GRASS);
    naturalBlocks.add(Blocks.GRASS_PATH);
    naturalBlocks.add(Blocks.LEAVES);
    naturalBlocks.add(Blocks.LOG);
    naturalBlocks.add(Blocks.LOG2);
    naturalBlocks.add(Blocks.PLANKS);
    naturalBlocks.add(Blocks.CACTUS);
    naturalBlocks.add(Blocks.WATERLILY);
    naturalBlocks.add(Blocks.WATER);
    naturalBlocks.add(Blocks.FLOWING_WATER);
    naturalBlocks.add(Blocks.RED_FLOWER);
    naturalBlocks.add(Blocks.YELLOW_FLOWER);
  }
  public static boolean containsItem(List<ItemStack> list, Item item) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        if (list.get(i).getItem() == item) { return true; }
      }
    }
    return false;
  }
  public static boolean containsItem(List<ItemStack> list, Block item) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        if (Block.getBlockFromItem(list.get(i).getItem()) == item) { return true; }
      }
    }
    return false;
  }
  public static boolean containsItem(List<ItemStack> list, Item item, int meta) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        if (list.get(i).getItem() == item && list.get(i).getMetadata() == meta) { return true; }
      }
    }
    return false;
  }
  public static boolean containsItem(List<ItemStack> list, Block item, int meta) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null) {
        if (Block.getBlockFromItem(list.get(i).getItem()) == item && list.get(i).getMetadata() == meta) { return true; }
      }
    }
    return false;
  }
  public static IBlockState getRandomOre() {
    return oreList.get(random.nextInt(oreList.size()));
  }
  public static boolean oreDictMatches(ItemStack stack1, ItemStack stack2) {
    if (OreDictionary.itemMatches(stack1, stack2, true)) {
      return true;
    }
    else {
      int[] oreIds = OreDictionary.getOreIDs(stack1);
      for (int i = 0; i < oreIds.length; i++) {
        if (OreDictionary.containsMatch(true, OreDictionary.getOres(OreDictionary.getOreName(oreIds[i])), stack2)) { return true; }
      }
    }
    return false;
  }
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  public static boolean isNaturalBlock(Block block) {
    for (int i = 0; i < naturalBlocks.size(); i++) {
      if (naturalBlocks.get(i) == block) { return true; }
    }
    return false;
  }
  public static boolean itemListsMatchWithSize(List<ItemStack> i1, List<ItemStack> i2) {
    ArrayList<ItemStack> recipe = new ArrayList<ItemStack>();
    ArrayList<ItemStack> available = new ArrayList<ItemStack>();
    recipe.addAll(i1);
    available.addAll(i2);
    for (int i = 0; i < recipe.size(); i++) {
      if (recipe.get(i) == null) {
        recipe.remove(i);
        i--;
      }
    }
    for (int i = 0; i < available.size(); i++) {
      if (available.get(i) == null) {
        available.remove(i);
        i--;
      }
    }
    if (available.size() == recipe.size()) {
      for (int j = 0; j < available.size(); j++) {
        boolean endIteration = false;
        for (int i = 0; i < recipe.size() && !endIteration; i++) {
          if (oreDictMatches(available.get(j), recipe.get(i))) {
            recipe.remove(i);
            endIteration = true;
          }
        }
      }
    }
    return recipe.size() == 0;
  }
  public static float fract(float f) {
    return f - (int) f;
  }
  public static double fract(double d) {
    return d - (int) d;
  }
  public static boolean itemListsMatch(List<ItemStack> i1, List<ItemStack> i2) {
    ArrayList<ItemStack> recipe = new ArrayList<ItemStack>();
    ArrayList<ItemStack> available = new ArrayList<ItemStack>();
    recipe.addAll(i1);
    available.addAll(i2);
    for (int i = 0; i < recipe.size(); i++) {
      if (recipe.get(i) == null) {
        recipe.remove(i);
        i--;
      }
    }
    for (int i = 0; i < available.size(); i++) {
      if (available.get(i) == null) {
        available.remove(i);
        i--;
      }
    }
    if (available.size() >= recipe.size()) {
      for (int j = 0; j < available.size(); j++) {
        boolean endIteration = false;
        for (int i = 0; i < recipe.size() && !endIteration; i++) {
          if (oreDictMatches(available.get(j), recipe.get(i))) {
            recipe.remove(i);
            endIteration = true;
          }
        }
      }
    }
    return recipe.size() == 0;
  }
  public static float getNatureAmount(IBlockState state) {
    if (state.getBlock() == Blocks.DIRT) { return 0.04f; }
    if (state.getBlock() == Blocks.GRASS) { return 0.16f; }
    if (state.getBlock() == Blocks.TALLGRASS) { return 0.24f; }
    if (state.getBlock() == Blocks.RED_FLOWER) { return 0.64f; }
    if (state.getBlock() == Blocks.YELLOW_FLOWER) { return 0.64f; }
    if (state.getBlock() == Blocks.DOUBLE_PLANT) { return 0.8f; }
    if (state.getBlock() == Blocks.WATER) { return 0.16f; }
    if (state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2) { return 0.32f; }
    if (state.getBlock() == Blocks.LOG || state.getBlock() == Blocks.LOG2) { return 0.24f; }
    if (state.getBlock() == Blocks.WATERLILY) { return 0.56f; }
    if (state.getBlock() == Blocks.CACTUS) { return 0.72f; }
    return 0;
  }
  public static EnumFacing getRayFace(World world, EntityPlayer player, int reachDistance) {
    double x = player.posX;
    double y = player.posY + player.getEyeHeight();
    double z = player.posZ;
    for (int i = 0; i < reachDistance * 4.0; i++) {
      x += player.getLookVec().x * 0.25;
      y += player.getLookVec().y * 0.25;
      z += player.getLookVec().z * 0.25;
      if (world.getBlockState(new BlockPos(x, y, z)).isFullCube()) {
        BlockPos pos = new BlockPos(x, y, z);
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 0.5;
        double centerZ = pos.getZ() + 0.5;
        double dx = Math.abs(x - centerX);
        double dy = Math.abs(y - centerY);
        double dz = Math.abs(z - centerZ);
        if (dx > dy && dx > dz) {
          if (x - centerX > 0) {
            return EnumFacing.EAST;
          }
          else {
            return EnumFacing.WEST;
          }
        }
        else if (dy > dz) {
          if (y - centerY > 0) {
            return EnumFacing.UP;
          }
          else {
            return EnumFacing.DOWN;
          }
        }
        else {
          if (z - centerZ > 0) {
            return EnumFacing.SOUTH;
          }
          else {
            return EnumFacing.NORTH;
          }
        }
      }
    }
    return EnumFacing.UP;
  }
}
