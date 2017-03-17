package pixelplus.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import pixelplus.block.tile.TileEntityDriver;

public class DriverUpdatePacket extends AbstractPacketSided
{
    public BlockPos pos;
    public int energy;

    public DriverUpdatePacket()
    {

    }

    public DriverUpdatePacket(BlockPos pos, int energy)
    {
        this.pos = pos;
        this.energy = energy;
    }

    @Override
    public void handleClientThreaded(NetHandlerPlayClient client)
    {
        TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(pos);
        if(tile instanceof TileEntityDriver)
        {
            if(((TileEntityDriver)tile).indicatesPower())
                ((TileEntityDriver)tile).setAvailableEnergy(energy);
        }
    }

    @Override
    public void handleServerThreaded(NetHandlerPlayServer server)
    {
        throw new UnsupportedOperationException("Clientside only");
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = readPos(buf);
        energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        writePos(pos, buf);
        buf.writeInt(energy);
    }
}
