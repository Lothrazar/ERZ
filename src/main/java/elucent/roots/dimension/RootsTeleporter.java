package elucent.roots.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class RootsTeleporter extends Teleporter {

	public RootsTeleporter(WorldServer worldIn) {
		super(worldIn);
	}
	
	@Override
	public boolean makePortal(Entity entity){
		return false;
	}
	
	@Override
	public void placeInPortal(Entity entity, float rotationYaw){
		return;
	}
	
	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw){
		return false;
	}

}
