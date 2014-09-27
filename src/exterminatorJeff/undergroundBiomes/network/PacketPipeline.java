
package exterminatorJeff.undergroundBiomes.network;

import java.util.*;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import Zeno410Utils.Maybe;

/**
 * Packet pipeline class. Directs all registered packet data to be handled by the packets themselves.
 * @author sirgingalot
 * some code from: cpw]
 * Modified by Zeno410 due to unstable packet indexes
 */
@ChannelHandler.Sharable
public class PacketPipeline extends MessageToMessageCodec<FMLProxyPacket, AbstractPacket> {

    private EnumMap<Side, FMLEmbeddedChannel>           channels;
    private LinkedList<Channel> packetChannels          = new LinkedList<Channel>();
    private boolean                                     isPostInitialised = false;
    private byte nextChannel = 0;
    private PacketPipeline.Channel[] channelIndex = new PacketPipeline.Channel[256];

    /**
     * Register your packet with the pipeline. Discriminators are automatically set.
     *
     * @param clazz the class to register
     *
     * @return whether registration was successful. Failure may occur if 256 packets have been registered or if the registry already contains this packet
     */
    public <ChannelType> Maybe<Channel<ChannelType>> registerChannel(AbstractChannel<ChannelType> clazzParent) {

        if (this.packetChannels.size() >= 256) {
            // You should log here!!
            return Maybe.unknown();
        }

        if (this.isPostInitialised) {
            // You should log here!!
            return Maybe.unknown();
        }

        Channel<ChannelType> clazz = new Channel<ChannelType>(clazzParent,nextChannel++);

        if (this.packetChannels.contains(clazz)) {
            // You should log here!!
            return Maybe.unknown();
        }

        this.packetChannels.add(clazz);
        channelIndex[clazz.discriminator] = clazz;
        return Maybe.definitely(clazz);
    }

    public <ChannelType> byte discriminator(Channel<ChannelType> clazz) {
        byte discriminator = clazz.discriminator;
        return discriminator;

    }
    // In line encoding of the packet, including discriminator setting
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        Channel clazz = msg.channel();
        if (!this.packetChannels.contains(clazz)) {
            throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
        }

        byte discriminator = clazz.discriminator;
        buffer.writeByte(discriminator);
        msg.encodeInto(ctx, buffer);
        FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    // In line decoding and handling of the packet
    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.payload();
        byte discriminator = payload.readByte();
        Channel clazz = this.channelIndex[discriminator];
        if (clazz == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }

        AbstractPacket pkt = clazz.incomingPacket();
        //AtlasData.logger.info("decoding channel "+discriminator);
        pkt.decodeInto(ctx, payload.slice());

        EntityPlayer player;
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                player = this.getClientPlayer();
                pkt.handleClientSide(player);
                break;

            case SERVER:
                INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
                player = ((NetHandlerPlayServer) netHandler).playerEntity;
                pkt.handleServerSide(player);
                break;

            default:
        }

        out.add(pkt);
    }

    // Method to call from FMLInitializationEvent
    public void initialise() {
        this.channels = NetworkRegistry.INSTANCE.newChannel("UB", this);
    }

    // Method to call from FMLPostInitializationEvent
    // Ensures that packet discriminators are common between server and client by using logical sorting
    public void postInitialise() {
        if (this.isPostInitialised) {
            return;
        }

        this.isPostInitialised = true;
        Collections.sort(this.packetChannels, new Comparator<Channel>() {

            @Override
            public int compare(Channel clazz1, Channel clazz2) {
                int com = clazz1.hashCode()- clazz2.hashCode();
                return com;
            }
        });
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    /**
     * Send this message to everyone.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     */
    public void sendToAll(AbstractPacket message) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the specified player.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     * @param player  The player to send it to
     */
    public void sendTo(AbstractPacket message, EntityPlayerMP player) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within a certain range of a point.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     * @param point   The {@link cpw.mods.fml.common.network.NetworkRegistry.TargetPoint} around which to send
     */
    public void sendToAllAround(AbstractPacket message, NetworkRegistry.TargetPoint point) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within the supplied dimension.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message     The message to send
     * @param dimensionId The dimension id to target
     */
    public void sendToDimension(AbstractPacket message, int dimensionId) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the server.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     */
    public void sendToServer(AbstractPacket message) {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(message);
    }

    private class ChannelizedPacket<MessageType> extends AbstractPacket {
        private final AbstractMessage<MessageType> message;
        private final Channel<MessageType> channel;
        ChannelizedPacket(AbstractMessage<MessageType> message, Channel channel){
            this.message = message;
            this.channel = channel;
        }
            public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer){
                message.toBytes(buffer);
            }

            public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
                message.fromBytes(buffer);
            }

            public void handleClientSide(EntityPlayer player){
                channel.receiveClient(player,message.value);
            }

            public void handleServerSide(EntityPlayer player) {
                channel.receiveServer(player,message.value);
            }

            public Channel channel() {
                return channel;
            }

    }

    public final class Channel<Type> {
        final AbstractChannel<Type> source;
        final byte discriminator;
        Channel(AbstractChannel source, byte discriminator){
            this.source = source;
            this.discriminator = discriminator;
        }

        public final AbstractPacket incomingPacket() {
            return new ChannelizedPacket(source.incomingMessage(),this);
        }

        public final void sendTo(Type sent,EntityPlayerMP player) {
            //AtlasData.logger.info("send "+discriminator + " to " + player.getDisplayName()+ " " + sent.toString());
            PacketPipeline.this.sendTo(new ChannelizedPacket(source.outgoingMessage(sent),this),player);

        }

        public final void sendToAll(Type sent) {
            PacketPipeline.this.sendToAll(new ChannelizedPacket(source.outgoingMessage(sent),this));

        }

        public final void sendServer(Type sent) {
            //AtlasData.logger.info("send server "+discriminator + " " + sent.toString());
            sendToServer(new ChannelizedPacket(source.outgoingMessage(sent),this));

        }
        
        public final void receiveClient(EntityPlayer player,Type received) {
            source.receiveAsClient(received, player);
        }
        
        public final void receiveServer(EntityPlayer player,Type received) {
            source.receiveAsServer(received, player);
        }

    }
}