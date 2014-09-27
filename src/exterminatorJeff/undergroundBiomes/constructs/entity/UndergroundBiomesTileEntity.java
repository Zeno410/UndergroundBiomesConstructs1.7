/**
 *
 * @author Zeno410
 * from tutorial by WiduX
 */
package exterminatorJeff.undergroundBiomes.constructs.entity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import Zeno410Utils.Zeno410Logger;
import java.util.logging.Logger;

import java.util.Set;

public class UndergroundBiomesTileEntity extends TileEntity {
    public static String IndexName = "index";
    private int masterIndex;
    //public static Logger logger = new Zeno410Logger("TileEntity").logger();

    public UndergroundBiomesTileEntity() {

    }
        // must be no parameters for Forge

    public void setZCoord(int zCoord) {
        this.zCoord = zCoord;
    }

    @Override
    public boolean canUpdate() {return false;}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {


        Set tags = nbt.func_150296_c();
        for (Object object: tags) {
            //logger.info(object.toString() + " " + object.hashCode());
        }
        super.readFromNBT(nbt);
        masterIndex = nbt.getInteger(IndexName);}

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        //logger.info("writing "+masterIndex + " " +  this.worldObj.toString()+ " " +  this.xCoord + " " +  this.yCoord + " " +  this.zCoord);
        super.writeToNBT(nbt);
        nbt.setInteger(IndexName, masterIndex);
    }

    @Override
    public Packet getDescriptionPacket() {
       NBTTagCompound tileTag = new NBTTagCompound();
       this.writeToNBT(tileTag);
       return new S35PacketUpdateTileEntity(this.xCoord,this.yCoord, this.zCoord,0,tileTag);
    }


    public void onDataPacket(INetHandler net, S35PacketUpdateTileEntity packet) {
       this.readFromNBT(packet.func_148857_g());
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
       this.readFromNBT(packet.func_148857_g());
    }
    
    public final int masterIndex() {return masterIndex;}

    public final void setMasterIndex(int index) {
        masterIndex = index;
        this.markDirty();
    }
}
