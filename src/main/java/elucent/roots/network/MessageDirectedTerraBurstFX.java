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

public class MessageDirectedTerraBurstFX implements IMessage{
	static Random random = new Random();

	public float x = 0, y = 0, z = 0;
	public float vx = 0, vy = 0, vz = 0;
	
	public MessageDirectedTerraBurstFX() {};
	
	public MessageDirectedTerraBurstFX(float x, float y, float z, float vx, float vy, float vz){
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
	};
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
		vx = buf.readFloat();
		vy = buf.readFloat();
		vz = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(vx);
		buf.writeFloat(vy);
		buf.writeFloat(vz);
	}
	
	public static class MessageHolder implements IMessageHandler<MessageDirectedTerraBurstFX, IMessage>{

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage( final MessageDirectedTerraBurstFX message, final MessageContext ctx) {
			IThreadListener mainThread = (ctx.side.isClient())? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() 
            {
                @Override
                public void run() {
		    		for (int j = 0; j < 20; j ++){
		    			float velocity = random.nextFloat();
						Roots.proxy.spawnParticleMagicSparkleFX(Minecraft.getMinecraft().theWorld, message.x, message.y, message.z, velocity*message.vx+Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), velocity*message.vy+Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), velocity*message.vz+Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
					}
                }
            });
            return null;
		}

		
	}

}
