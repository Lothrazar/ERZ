package elucent.roots.network;

import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.capability.mana.ManaProvider;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpellCastFX implements IMessage{
	static Random random = new Random();
	public float x = 0, y = 0, z = 0;
	public float lx = 0, ly = 0, lz = 0;
	public String name = "";
	
	public MessageSpellCastFX() {};
	
	public MessageSpellCastFX(String name, float x, float y, float z, Vec3d look){
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.lx = (float)look.xCoord;
		this.ly = (float)look.yCoord;
		this.lz = (float)look.zCoord;
	};
	
	@Override
	public void fromBytes(ByteBuf buf) {
		name = ByteBufUtils.readUTF8String(buf);
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
		lx = buf.readFloat();
		ly = buf.readFloat();
		lz = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf,name);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(lx);
		buf.writeFloat(ly);
		buf.writeFloat(lz);
	}
	
	public static class MessageHolder implements IMessageHandler<MessageSpellCastFX, IMessage>{


		@Override
		public IMessage onMessage( final MessageSpellCastFX message, final MessageContext ctx) {
			IThreadListener mainThread = (ctx.side.isClient())? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() 
            {
                @Override
                public void run() {
                	ComponentBase comp = ComponentManager.getComponentFromName(message.name);
					for (int i = 0 ; i < 90; i ++){
						double offX = random.nextFloat()*0.5-0.25;
						double offY = random.nextFloat()*0.5-0.25;
						double offZ = random.nextFloat()*0.5-0.25;
						double coeff = (offX+offY+offZ)/1.5+0.5;
						double dx = (message.lx+offX)*coeff;
						double dy = (message.ly+offY)*coeff;
						double dz = (message.lz+offZ)*coeff;
						if (random.nextBoolean()){
							Roots.proxy.spawnParticleMagicFX(Minecraft.getMinecraft().theWorld, message.x+dx, message.y+1.5+dy, message.z+dz, dx, dy, dz, comp.primaryColor.xCoord, comp.primaryColor.yCoord, comp.primaryColor.zCoord);
						}
						else {
							Roots.proxy.spawnParticleMagicFX(Minecraft.getMinecraft().theWorld, message.x+dx, message.y+1.5+dy, message.z+dz, dx, dy, dz, comp.secondaryColor.xCoord, comp.secondaryColor.yCoord, comp.secondaryColor.zCoord);
						}
					}
                }
            });
            return null;
		}

		
	}

}
