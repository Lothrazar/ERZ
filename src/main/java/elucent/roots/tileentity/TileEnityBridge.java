package elucent.roots.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Created by SirShadow for the mod Roots on 19.7.2016.
 */
public class TileEnityBridge extends TileEntity implements ITickable
{
    public int duration = 200;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setInteger("duration", duration);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        this.duration = tag.getInteger("duration");
    }


    @Override
    public void update()
    {
        if(worldObj.isRemote)
        {
            return;
        }

        duration--;


        if(duration <= 0)
        {
            worldObj.setBlockToAir(getPos());
        }
    }
}
