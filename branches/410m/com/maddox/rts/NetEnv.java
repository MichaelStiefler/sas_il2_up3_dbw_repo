// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetEnv.java

package com.maddox.rts;

import com.maddox.rts.net.NetFileServer;
import com.maddox.rts.net.NetFileServerDef;
import com.maddox.rts.net.NetFileTransport;
import com.maddox.util.HashMapInt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetChannel, NetException, NetSocketListener, NetSocket, 
//            NetControlLock, NetUpdate, NetObj, Destroy, 
//            NetExtPacket, MsgTimeOut, Listeners, NetPacket, 
//            NetMsgInput, NetMsgOutput, NetHost, MsgTimeOutListener, 
//            MsgAddListenerListener, MsgRemoveListenerListener, RTSConf, Time, 
//            MsgNetExt, NetChannelInStream, NetConnect, NetAddress, 
//            MsgAction

public class NetEnv
    implements com.maddox.rts.MsgTimeOutListener, com.maddox.rts.MsgAddListenerListener, com.maddox.rts.MsgRemoveListenerListener
{
    class ActionReceivedPacket extends com.maddox.rts.MsgAction
    {

        public void doAction()
        {
            do
            {
                if(inputPackets.size() <= 0)
                    break;
                com.maddox.rts.NetPacket netpacket = (com.maddox.rts.NetPacket)inputPackets.get(0);
                if(com.maddox.rts.NetEnv.bTestTransfer)
                {
                    int i = 0;
                    if(com.maddox.rts.NetEnv.testSpeed > 0.0F)
                        i = (int)((float)netpacket.getLength() / (com.maddox.rts.NetEnv.testSpeed / 1000F));
                    if(com.maddox.rts.NetEnv.testMaxLag > 0)
                        i += com.maddox.rts.NetEnv.testMinLag + (int)(java.lang.Math.random() * (double)(com.maddox.rts.NetEnv.testMaxLag - com.maddox.rts.NetEnv.testMinLag));
                    if(i > 0)
                    {
                        if(netpacket.time + (long)i < lastResTime)
                            i = (int)(lastResTime - netpacket.time);
                        if(com.maddox.rts.Time.currentReal() < netpacket.time + (long)i)
                        {
                            if(!busy())
                                post(64, this, 0.0050000000000000001D);
                            return;
                        }
                        if((float)java.lang.Math.random() < com.maddox.rts.NetEnv.testDown)
                        {
                            inputPackets.remove(0);
                            inputSockets.remove(0);
                            continue;
                        }
                        netpacket.time += i;
                        lastResTime = netpacket.time;
                    }
                }
                inputPackets.remove(0);
                com.maddox.rts.NetSocket netsocket = (com.maddox.rts.NetSocket)inputSockets.get(0);
                inputSockets.remove(0);
                int j = netpacket.getLength();
                if(j > 1 && netsocket.isOpen() && !receivedPacket(netsocket, netpacket, netpacket.time))
                    receivedExtPacket(netsocket, netpacket);
            } while(true);
        }

        public void activate()
        {
            if(!busy())
                post(64, this, 0.0D);
        }

        private long lastResTime;

        ActionReceivedPacket()
        {
            lastResTime = 0L;
        }
    }


    public static boolean isServer()
    {
        if(!com.maddox.rts.RTSConf.cur.netEnv.bActive)
            return true;
        else
            return com.maddox.rts.RTSConf.cur.netEnv.control != null && com.maddox.rts.RTSConf.cur.netEnv.control.isMaster();
    }

    public static boolean isActive()
    {
        return com.maddox.rts.RTSConf.cur.netEnv.bActive;
    }

    public static void active(boolean flag)
    {
        com.maddox.rts.RTSConf.cur.netEnv.bActive = flag;
        if(flag)
        {
            if(!com.maddox.rts.RTSConf.cur.netEnv.tickerRead.busy())
                com.maddox.rts.RTSConf.cur.netEnv.tickerRead.post();
            if(!com.maddox.rts.RTSConf.cur.netEnv.tickerWrite.busy())
                com.maddox.rts.RTSConf.cur.netEnv.tickerWrite.post();
        }
    }

    public static com.maddox.rts.NetHost host()
    {
        return com.maddox.rts.RTSConf.cur.netEnv.host;
    }

    public static java.util.List hosts()
    {
        return com.maddox.rts.RTSConf.cur.netEnv.hosts;
    }

    public static java.util.List socketsBlock()
    {
        return com.maddox.rts.RTSConf.cur.netEnv.socketsBlock;
    }

    public static java.util.List socketsNoBlock()
    {
        return com.maddox.rts.RTSConf.cur.netEnv.socketsNoBlock;
    }

    public static java.util.List channels()
    {
        return com.maddox.rts.RTSConf.cur.netEnv.channels;
    }

    public static com.maddox.rts.NetChannel getChannel(int i)
    {
        return (com.maddox.rts.NetChannel)com.maddox.rts.RTSConf.cur.netEnv.indxChannels.get(i);
    }

    public static void addSocket(com.maddox.rts.NetSocket netsocket)
    {
        com.maddox.rts.RTSConf.cur.netEnv._addSocket(netsocket);
    }

    private void _addSocket(com.maddox.rts.NetSocket netsocket)
    {
        if(!netsocket.isOpen())
            throw new NetException("Socket not opened");
        if(netsocket.isSupportedBlockOperation())
        {
            com.maddox.rts.NetSocketListener netsocketlistener = new NetSocketListener(netsocket);
            netsocketlistener.setPriority(java.lang.Thread.currentThread().getPriority() + 1);
            netsocketlistener.start();
            socketThreads.add(netsocketlistener);
            socketsBlock.add(netsocket);
        } else
        {
            socketsNoBlock.add(netsocket);
        }
    }

    public static boolean isExistExclusiveSocket()
    {
        return com.maddox.rts.NetEnv.getExclusiveSocket() != null;
    }

    public static com.maddox.rts.NetSocket getExclusiveSocket()
    {
        return com.maddox.rts.RTSConf.cur.netEnv._getExclusiveSocket();
    }

    private com.maddox.rts.NetSocket _getExclusiveSocket()
    {
        for(int i = 0; i < socketsNoBlock.size(); i++)
        {
            com.maddox.rts.NetSocket netsocket = (com.maddox.rts.NetSocket)socketsNoBlock.get(i);
            if(netsocket.isExclusive() && netsocket.isOpen())
                return netsocket;
        }

        for(int j = 0; j < socketsBlock.size(); j++)
        {
            com.maddox.rts.NetSocket netsocket1 = (com.maddox.rts.NetSocket)socketsBlock.get(j);
            if(netsocket1.isExclusive() && netsocket1.isOpen())
                return netsocket1;
        }

        return null;
    }

    public com.maddox.rts.NetChannel createChannel(int i, int j, int k, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int l, com.maddox.rts.NetConnect netconnect)
    {
        if((i & 4) != 0 && control != null && (control instanceof com.maddox.rts.NetControlLock))
            throw new NetException("Creating global channel is locked");
        if(indxChannels.containsKey(j))
            throw new NetException("Created channel used bad ID");
        if(socketsBlock.indexOf(netsocket) == -1 && socketsNoBlock.indexOf(netsocket) == -1)
        {
            throw new NetException("Socket NOT registered");
        } else
        {
            com.maddox.rts.NetChannel netchannel = new NetChannel(i, j, k, netsocket, netaddress, l, netconnect);
            indxChannels.put(j, netchannel);
            channels.add(netchannel);
            fileTransport.msgNetNewChannel(netchannel);
            return netchannel;
        }
    }

    public void addChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(netchannel.isGlobal() && control != null && (control instanceof com.maddox.rts.NetControlLock))
            throw new NetException("Creating global channel is locked");
        if(indxChannels.containsKey(netchannel.id()))
        {
            throw new NetException("Created channel used bad ID");
        } else
        {
            indxChannels.put(netchannel.id(), netchannel);
            channels.add(netchannel);
            fileTransport.msgNetNewChannel(netchannel);
            return;
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(bActive)
            if(obj == idReadStep)
            {
                int i = channels.size();
                for(int j = 0; j < i;)
                {
                    com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)channels.get(j);
                    if(netchannel.update())
                    {
                        j++;
                    } else
                    {
                        channels.remove(j);
                        indxChannels.remove(netchannel.id);
                        if(netchannel.socket() != null)
                            netchannel.socket().countChannels--;
                        i--;
                    }
                }

                i = socketsBlock.size();
                for(int k = 0; k < i;)
                {
                    com.maddox.rts.NetSocket netsocket = (com.maddox.rts.NetSocket)socketsBlock.get(k);
                    if(netsocket.maxChannels > 0 || netsocket.countChannels > 0)
                    {
                        k++;
                    } else
                    {
                        com.maddox.rts.NetSocketListener netsocketlistener = (com.maddox.rts.NetSocketListener)socketThreads.get(k);
                        socketThreads.remove(k);
                        netsocketlistener.bDoRun = false;
                        if(netsocket.isOpen())
                            try
                            {
                                netsocket.close();
                            }
                            catch(java.lang.Exception exception1) { }
                        socketsBlock.remove(k);
                        i--;
                    }
                }

                i = socketsNoBlock.size();
                for(int l = 0; l < i;)
                {
                    com.maddox.rts.NetSocket netsocket1 = (com.maddox.rts.NetSocket)socketsNoBlock.get(l);
                    if(netsocket1.maxChannels > 0 || netsocket1.countChannels > 0)
                    {
                        l++;
                    } else
                    {
                        if(netsocket1.isOpen())
                            try
                            {
                                netsocket1.close();
                            }
                            catch(java.lang.Exception exception) { }
                        socketsNoBlock.remove(l);
                        i--;
                    }
                }

                i = socketsNoBlock.size();
                for(int i1 = 0; i1 < i; i1++)
                {
                    com.maddox.rts.NetSocket netsocket2 = (com.maddox.rts.NetSocket)socketsNoBlock.get(i1);
                    for(packet.time = 0L; netsocket2.receive(packet); packet.time = 0L)
                    {
                        long l2 = packet.time;
                        if(l2 == 0L)
                            l2 = com.maddox.rts.Time.real();
                        int j2 = packet.getLength();
                        if(j2 > 1 && !receivedPacket(netsocket2, packet, l2))
                            receivedExtPacket(netsocket2, packet);
                    }

                }

                tickerRead.post();
            } else
            if(obj == idWriteStep)
            {
                boolean flag = false;
                int j1 = channels.size();
                long l1 = com.maddox.rts.Time.real();
                int k1 = 0;
                do
                {
                    if(k1 >= j1)
                        break;
                    com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)channels.get(k1);
                    if(netchannel1.isRequeredSendPacket(l1))
                    {
                        flag = true;
                        break;
                    }
                    k1++;
                } while(true);
                if(flag)
                {
                    int i2 = udatedObjects.size();
                    if(i2 > 0)
                    {
                        _updateObjects = udatedObjects.toArray(_updateObjects);
                        try
                        {
                            for(int k2 = 0; k2 < i2; k2++)
                            {
                                com.maddox.rts.NetUpdate netupdate = (com.maddox.rts.NetUpdate)_updateObjects[k2];
                                _updateObjects[k2] = null;
                                com.maddox.rts.NetObj netobj;
                                if((netupdate instanceof com.maddox.rts.NetObj) ? !(netobj = (com.maddox.rts.NetObj)netupdate).isDestroyed() && (netobj.superObj == null || !(netobj.superObj instanceof com.maddox.rts.Destroy) || !((com.maddox.rts.Destroy)netobj.superObj).isDestroyed()) : !(netupdate instanceof com.maddox.rts.Destroy) || !((com.maddox.rts.Destroy)netupdate).isDestroyed())
                                    netupdate.netUpdate();
                            }

                        }
                        catch(java.lang.Exception exception2)
                        {
                            java.lang.System.out.println(exception2.getMessage());
                            exception2.printStackTrace();
                        }
                    }
                    for(int i3 = 0; i3 < j1; i3++)
                    {
                        for(com.maddox.rts.NetChannel netchannel2 = (com.maddox.rts.NetChannel)channels.get(i3); netchannel2.sendPacket(msgOutput, packet););
                    }

                }
                sendExtPackets();
                tickerWrite.post();
            }
    }

    protected void listenerReceivedPacket(com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetPacket netpacket)
    {
        synchronized(inputAction)
        {
            inputPackets.add(netpacket);
            inputSockets.add(netsocket);
            inputAction.activate();
        }
    }

    private boolean receivedPacket(com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetPacket netpacket, long l)
    {
        if(netpacket.getLength() <= 6)
            return false;
        msgInput.setData(null, false, netpacket.getData(), netpacket.getOffset(), netpacket.getLength());
        com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)indxChannels.get(msgInput.readUnsignedShort());
        if(netchannel == null)
            return false;
        if(!netchannel.remoteAddress().equals(netpacket.getAddress()) || netchannel.remotePort() != netpacket.getPort())
            return false;
        msgInput.reset();
        return netchannel.receivePacket(msgInput, l);
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    public void postExt(byte abyte0[], int i, int j, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int k)
    {
        if(bActive)
            extPackets.add(new NetExtPacket(abyte0, i, j, netsocket, netaddress, k));
    }

    public void postExtUTF(byte byte0, java.lang.String s, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(bActive)
            try
            {
                msgOutput.clear();
                msgOutput.writeByte(byte0);
                msgOutput.writeUTF(s);
                extPackets.add(new NetExtPacket(msgOutput.data(), 0, msgOutput.dataLength(), netsocket, netaddress, i));
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
    }

    private void sendExtPackets()
    {
        int i = extPackets.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.NetExtPacket netextpacket = (com.maddox.rts.NetExtPacket)extPackets.get(j);
            try
            {
                msgOutput.clear();
                msgOutput.write(netextpacket.getBuf());
                packet.setLength(msgOutput.dataLength());
                packet.setAddress(netextpacket.getAddress());
                packet.setPort(netextpacket.getPort());
                netextpacket.getSocket().send(packet);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
            netextpacket.clear();
        }

        extPackets.clear();
    }

    private void receivedExtPacket(com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetPacket netpacket)
    {
        try
        {
            java.lang.Object aobj[] = listeners.get();
            if(aobj != null)
            {
                byte abyte0[] = new byte[netpacket.getLength()];
                java.lang.System.arraycopy(netpacket.getData(), netpacket.getOffset(), abyte0, 0, netpacket.getLength());
                com.maddox.rts.MsgNetExt.postReal(com.maddox.rts.Time.currentReal(), ((java.lang.Object) (aobj)), abyte0, netsocket, netpacket.getAddress(), netpacket.getPort());
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public java.lang.Object[] getListeners()
    {
        return listeners.get();
    }

    public void msgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        listeners.addListener(obj);
    }

    public void msgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        listeners.removeListener(obj);
    }

    public int nextIdChannel(boolean flag)
    {
        int i;
        do
        {
            i = nextId;
            if(flag)
                i |= 1;
            nextId = nextId + 2 & 0xffff;
        } while(indxChannels.containsKey(i));
        return i;
    }

    protected NetEnv()
    {
        hosts = new ArrayList();
        channels = new ArrayList();
        indxChannels = new HashMapInt();
        objects = new HashMapInt();
        nextFreeID = 255;
        udatedObjects = new ArrayList();
        socketsNoBlock = new ArrayList();
        socketsBlock = new ArrayList();
        socketThreads = new ArrayList();
        _updateObjects = new java.lang.Object[1];
        idReadStep = new Object();
        idWriteStep = new Object();
        extPackets = new ArrayList();
        bActive = false;
        nextId = 0;
        com.maddox.rts.RTSConf.cur.netEnv = this;
        com.maddox.rts.NetChannel.classInit();
        com.maddox.rts.NetChannelInStream.classInit();
        tickerRead = new MsgTimeOut(idReadStep);
        tickerRead.setNotCleanAfterSend();
        tickerRead.setListener(this);
        tickerRead.setFlags(72);
        tickerRead.setTickPos(0);
        tickerWrite = new MsgTimeOut(idWriteStep);
        tickerWrite.setNotCleanAfterSend();
        tickerWrite.setListener(this);
        tickerWrite.setFlags(104);
        tickerWrite.setTickPos(0x7ffffffe);
        listeners = new Listeners();
        packet = new NetPacket(new byte[2048], 0);
        msgInput = new NetMsgInput();
        msgOutput = new NetMsgOutput(packet.getData());
        inputPackets = new ArrayList();
        inputSockets = new ArrayList();
        inputAction = new ActionReceivedPacket();
        new NetHost("NoName");
        fileTransport = new NetFileTransport(6);
        fileServerDef = new NetFileServerDef(7);
    }

    public static com.maddox.rts.NetEnv cur()
    {
        return com.maddox.rts.RTSConf.cur.netEnv;
    }

    public static final int ADAPTER_READ_TICK_POS = 0;
    public static final int ADAPTER_WRITE_TICK_POS = 0x7ffffffe;
    public static final int ID_NULL = 0;
    public static final int ID_ASK_MESSAGE = 1;
    public static final int ID_NAK_MESSAGE = 2;
    public static final int ID_SPAWN = 3;
    public static final int ID_DESTROY = 4;
    public static final int ID_CHANNEL = 5;
    public static final int ID_FILE_TRANSPORT = 6;
    public static final int ID_FILE_SERVERDEF = 7;
    public static final int ID_STREAM_SYNC = 8;
    public static final int ID_KEYRECORD = 9;
    public static final int ID_BEGIN = 255;
    public static final int ID_END = 32767;
    public static final int MAX_LEN_MSG = 255;
    public com.maddox.rts.NetConnect connect;
    public com.maddox.rts.NetObj control;
    protected com.maddox.rts.NetHost host;
    protected java.util.List hosts;
    protected java.util.ArrayList channels;
    protected com.maddox.util.HashMapInt indxChannels;
    public com.maddox.util.HashMapInt objects;
    protected int nextFreeID;
    protected java.util.ArrayList udatedObjects;
    protected java.util.ArrayList socketsNoBlock;
    protected java.util.ArrayList socketsBlock;
    protected java.util.ArrayList socketThreads;
    public com.maddox.rts.net.NetFileTransport fileTransport;
    public com.maddox.rts.net.NetFileServer fileServerDef;
    private java.lang.Object _updateObjects[];
    private java.lang.Object idReadStep;
    private java.lang.Object idWriteStep;
    private java.util.ArrayList extPackets;
    private boolean bActive;
    private int nextId;
    private com.maddox.rts.MsgTimeOut tickerRead;
    private com.maddox.rts.MsgTimeOut tickerWrite;
    private com.maddox.rts.Listeners listeners;
    private com.maddox.rts.NetMsgInput msgInput;
    private com.maddox.rts.NetMsgOutput msgOutput;
    private com.maddox.rts.NetPacket packet;
    private java.util.List inputPackets;
    private java.util.List inputSockets;
    private com.maddox.rts.ActionReceivedPacket inputAction;
    public static boolean bTestTransfer = false;
    public static float testSpeed = 0.0F;
    public static int testMinLag = 0;
    public static int testMaxLag = 0;
    public static float testDown = 0.0F;
    public static boolean testLag = false;





}
