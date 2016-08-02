package elucent.roots.network;

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

public class MessageUpdateMana implements IMessage{

	private NBTTagCompound tags;
	
	public MessageUpdateMana() {};
	
	public MessageUpdateMana(NBTTagCompound tag){
		this.tags = tag;
	};
	
	@Override
	public void fromBytes(ByteBuf buf) {
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, tags);
	}
	
	public static class CapsMessageHandler implements IMessageHandler<MessageUpdateMana, IMessage>{


		@Override
		public IMessage onMessage( final MessageUpdateMana message, final MessageContext ctx) {
			IThreadListener mainThread = (ctx.side.isClient())? Minecraft.getMinecraft() : (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() 
            {
                @Override
                public void run() {
                    ManaProvider.get(Minecraft.getMinecraft().thePlayer).loadNBTData(message.tags);
                }
            });
            return null;
		}

		
	}

}
