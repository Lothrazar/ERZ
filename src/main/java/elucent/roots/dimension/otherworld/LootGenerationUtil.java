package elucent.roots.dimension.otherworld;

import java.util.Random;

import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;

public class LootGenerationUtil {
	static Random random = new Random();
	public static void generateDungeonLoot(TileEntityChest chest){
		chest.setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON,random.nextLong());
		//chest.
	}
}
