package elucent.roots.network;

import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.capability.mana.ManaProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageTerraBurstFX implements IMessage{
	static Random random = new Random();

	public float x = 0, y = 0, z = 0;
	
	public MessageTerraBurstFX() {};
	
	public MessageTerraBurstFX(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	};
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
	}
	
	public static class MessageHolder implements IMessageHandler<MessageTerraBurstFX, IMessage>{

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage( final MessageTerraBurstFX message, final MessageContext ctx) {
			IThreadListener mainThread = (ctx.side.isClient())? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() 
            {
                @Override
                public void run() {
		    		for (int j = 0; j < 20; j ++){
						Roots.proxy.spawnParticleMagicSparkleFX(Minecraft.getMinecraft().theWorld, message.x, message.y, message.z, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
					}
                }
            });
            return null;
		}

		
	}

}
