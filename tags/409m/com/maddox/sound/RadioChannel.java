// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RadioChannel.java

package com.maddox.sound;

import com.maddox.il2.engine.TextScr;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.netphone.MixChannel;
import com.maddox.netphone.NetMixer;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

// Referenced classes of package com.maddox.sound:
//            ChannelContext, SoundReceiver, RadioChannelSpawn, BaseObject, 
//            AudioDevice

public class RadioChannel extends com.maddox.rts.NetObj
    implements com.maddox.rts.NetUpdate
{

    protected boolean isHaveLocal()
    {
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams == null)
            return true;
        else
            return netserverparams.isMirror() || !netserverparams.isDedicated();
    }

    public RadioChannel(java.lang.String s, com.maddox.sound.RadioChannelSpawn radiochannelspawn, int i)
    {
        super(null);
        name = null;
        mixer = null;
        lch = null;
        och = null;
        isActive = false;
        tmpBuf = new byte[64];
        chanMap = new HashMapExt();
        rcSpawn = null;
        codecId = 0;
        codecId = i;
        rcSpawn = radiochannelspawn;
        name = s;
        mixCnt = 2;
        init(isHaveLocal());
        if(tstMode > 0)
            java.lang.System.out.println("Radio : new master - " + s + " Local = " + isHaveLocal() + " codec - " + getCodecName());
    }

    public RadioChannel(com.maddox.rts.NetMsgInput netmsginput, int i, com.maddox.sound.RadioChannelSpawn radiochannelspawn)
    {
        super(null, netmsginput.channel(), i);
        name = null;
        mixer = null;
        lch = null;
        och = null;
        isActive = false;
        tmpBuf = new byte[64];
        chanMap = new HashMapExt();
        rcSpawn = null;
        codecId = 0;
        try
        {
            rcSpawn = radiochannelspawn;
            codecId = netmsginput.readInt();
            name = netmsginput.read255();
            boolean flag = netmsginput.readBoolean();
            mixCnt = 3;
            init(isHaveLocal());
            if(!com.maddox.sound.RadioChannel.checkCodecId(codecId))
                codecId = 1;
            com.maddox.sound.ChannelContext channelcontext = new ChannelContext(mixer, false);
            channelcontext.setActive(flag);
            chanMap.put(netmsginput.channel(), channelcontext);
            if(tstMode > 0)
                java.lang.System.out.println("Radio : new mirror - " + name + " Local = " + isHaveLocal() + " codec - " + getCodecName() + " act " + flag);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public static com.maddox.sound.RadioChannel activeChannel()
    {
        return activeChannel;
    }

    protected static boolean checkCodecId(int i)
    {
        if(i != 1 && i != 2)
        {
            java.lang.System.out.println("Invalid codec id : " + i);
            return false;
        } else
        {
            return true;
        }
    }

    public static int getCurrentCodec()
    {
        return currentCodecId;
    }

    public static void setCurrentCodec(int i)
    {
        if(!com.maddox.sound.RadioChannel.checkCodecId(i))
        {
            return;
        } else
        {
            currentCodecId = i;
            return;
        }
    }

    public java.lang.String getCodecName()
    {
        switch(codecId)
        {
        case 1: // '\001'
            return "LPCM";

        case 2: // '\002'
            return "HQ";
        }
        return "Unknown";
    }

    public static java.lang.String getCurrentCodecName()
    {
        switch(currentCodecId)
        {
        case 1: // '\001'
            return "LPCM";

        case 2: // '\002'
            return "HQ";
        }
        return "Unknown";
    }

    protected void init(boolean flag)
    {
        mixer = new NetMixer(codecId);
        if(flag)
        {
            if(lch == null)
                lch = mixer.newChannel(false);
            if(och == null)
                och = new SoundReceiver(codecId);
        }
    }

    public void destroy()
    {
        if(!isDestroyed())
        {
            super.destroy();
            if(rcSpawn != null)
                rcSpawn.onDestroyChannel(this);
            if(lch != null)
            {
                lch.destroy();
                lch = null;
            }
            if(och != null)
            {
                och.destroy();
                och = null;
            }
            for(java.util.Map.Entry entry = chanMap.nextEntry(null); entry != null; entry = chanMap.nextEntry(entry))
            {
                com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)entry.getValue();
                channelcontext.destroy();
            }

            chanMap.clear();
            if(mixer != null)
            {
                mixer.destroy();
                mixer = null;
            }
            if(tstMode > 0)
                java.lang.System.out.println("Radio : destroyed - " + name);
        }
    }

    protected void finalize()
    {
        destroy();
    }

    public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)chanMap.get(netchannel);
        if(channelcontext != null)
        {
            chanMap.remove(netchannel);
            channelcontext.destroy();
        }
    }

    public boolean getActState()
    {
        int i = 0;
        for(java.util.Map.Entry entry = chanMap.nextEntry(null); entry != null; entry = chanMap.nextEntry(entry))
        {
            com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)entry.getValue();
            if(channelcontext.isActive())
                i++;
        }

        return i > 1 || isActive;
    }

    protected void rcstat()
    {
        java.lang.System.out.println();
        for(java.util.Map.Entry entry = chanMap.nextEntry(null); entry != null; entry = chanMap.nextEntry(entry))
        {
            com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)entry.getValue();
            java.lang.System.out.println("ACT -> " + channelcontext.isActive());
        }

    }

    private void doMsgReplayActive(boolean flag, com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)chanMap.get(netchannel);
        if(channelcontext != null)
            channelcontext.setActive(flag);
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
    {
        if(super.netInput(netmsginput))
            return true;
        try
        {
            if(netmsginput.isGuaranted())
                switch(netmsginput.readByte())
                {
                case 3: // '\003'
                    doMsgReplayActive(netmsginput.readBoolean(), netmsginput.channel());
                    break;

                case 1: // '\001'
                    boolean flag = netmsginput.readBoolean();
                    com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)chanMap.get(netmsginput.channel());
                    if(channelcontext == null)
                        java.lang.System.out.println("Channel context not found !");
                    else
                        channelcontext.setActive(flag);
                    if(!flag)
                        flag = getActState();
                    for(java.util.Map.Entry entry1 = chanMap.nextEntry(null); entry1 != null; entry1 = chanMap.nextEntry(entry1))
                    {
                        com.maddox.rts.NetChannel netchannel2 = (com.maddox.rts.NetChannel)entry1.getKey();
                        if(netchannel2 != netmsginput.channel() && !(netchannel2 instanceof com.maddox.rts.NetChannelInStream))
                        {
                            com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                            netmsgguaranted1.writeByte(1);
                            netmsgguaranted1.writeBoolean(flag);
                            postTo(netchannel2, netmsgguaranted1);
                        }
                    }

                    flag = getActState();
                    if(!(netmsginput.channel() instanceof com.maddox.rts.NetChannelInStream))
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                        netmsgguaranted.writeByte(3);
                        netmsgguaranted.writeBoolean(flag);
                        postTo(netmsginput.channel(), netmsgguaranted);
                    }
                    if(tstMode > 0)
                        java.lang.System.out.println("activation : " + flag);
                    break;

                case 4: // '\004'
                    setActive(netmsginput.readBoolean());
                    break;

                case 2: // '\002'
                default:
                    java.lang.System.out.println("Invalid radio message.");
                    break;
                }
            else
            if(!tstLoop && com.maddox.rts.Time.speed() == 1.0F)
            {
                int i = netmsginput.available();
                netmsginput.read(tmpBuf, 0, i);
                if(useMixer())
                {
                    com.maddox.rts.NetChannel netchannel = netmsginput.channel();
                    com.maddox.sound.ChannelContext channelcontext1 = (com.maddox.sound.ChannelContext)chanMap.get(netchannel);
                    if(channelcontext1 != null)
                        if(channelcontext1.isActive())
                        {
                            if(tstMode == 2 && i > 1)
                                java.lang.System.out.println("DATA TO MIXER - " + i + " - " + chanMap.size());
                            channelcontext1.mc.put(tmpBuf, i);
                        } else
                        if(tstMode == 2 && i > 1)
                            java.lang.System.out.println("NOTACT");
                } else
                {
                    if(och != null)
                    {
                        if(i > 0)
                            och.put(tmpBuf, i);
                        if(tstMode == 2 && i > 1)
                            java.lang.System.out.println("DATA TO OUTPUT - " + i);
                    }
                    if(i > 0 && chanMap.size() == 2)
                    {
                        for(java.util.Map.Entry entry = chanMap.nextEntry(null); entry != null; entry = chanMap.nextEntry(entry))
                        {
                            com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)entry.getKey();
                            if(netchannel1 == netmsginput.channel())
                                continue;
                            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
                            netmsgfiltered.write(tmpBuf, 0, i);
                            netmsgfiltered.setIncludeTime(false);
                            postTo(com.maddox.rts.Time.current(), netchannel1, netmsgfiltered);
                            break;
                        }

                    }
                }
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return true;
    }

    protected boolean useMixer()
    {
        return lch == null || chanMap.size() >= mixCnt;
    }

    public void netUpdate()
    {
        boolean flag = useMixer();
        boolean flag1;
        try
        {
            do
            {
                flag1 = true;
                if(tstMode == 3)
                {
                    com.maddox.il2.engine.TextScr.output(100, 50, java.lang.Integer.toString(lch.getDataLength()));
                    if(flag)
                        mixer.printState(80);
                }
                if(activeChannel == this && com.maddox.sound.BaseObject.enabled)
                {
                    int i = com.maddox.sound.AudioDevice.getInputData(tmpBuf, tmpBuf.length);
                    if(tstLoop)
                    {
                        if(och != null && i > 0)
                        {
                            if(tstMode == 2 && i > 1)
                                java.lang.System.out.println("DATA INPUT - " + i);
                            och.put(tmpBuf, i);
                            flag1 = false;
                        }
                    } else
                    if(i > 0)
                    {
                        flag1 = false;
                        if(flag)
                        {
                            if(lch != null && lch.isActive())
                            {
                                if(tstMode == 2 && i > 1)
                                    java.lang.System.out.println("DATA FROM LOCAL - " + i);
                                lch.put(tmpBuf, i);
                            }
                        } else
                        {
                            int k = 0;
                            for(java.util.Map.Entry entry1 = chanMap.nextEntry(null); entry1 != null; entry1 = chanMap.nextEntry(entry1))
                            {
                                com.maddox.rts.NetChannel netchannel1 = (com.maddox.rts.NetChannel)entry1.getKey();
                                com.maddox.sound.ChannelContext channelcontext1 = (com.maddox.sound.ChannelContext)entry1.getValue();
                                if(channelcontext1.isActive() && !(netchannel1 instanceof com.maddox.rts.NetChannelInStream))
                                {
                                    com.maddox.rts.NetMsgFiltered netmsgfiltered1 = new NetMsgFiltered();
                                    netmsgfiltered1.write(tmpBuf, 0, i);
                                    netmsgfiltered1.setIncludeTime(false);
                                    postTo(com.maddox.rts.Time.current(), netchannel1, netmsgfiltered1);
                                    k++;
                                }
                            }

                            if(tstMode == 2 && i > 1)
                                java.lang.System.out.println("DATA FROM INP - " + i + " nc " + k);
                        }
                    }
                }
                if(flag && !tstLoop)
                {
                    mixer.tick();
                    if(activeChannel == this && lch != null && och != null)
                    {
                        int j = lch.get(tmpBuf, tmpBuf.length);
                        if(j > 0)
                        {
                            if(tstMode == 2 && j > 1)
                                java.lang.System.out.println("DATA TO CPP - " + j);
                            och.put(tmpBuf, j);
                            flag1 = false;
                        }
                    }
                    for(java.util.Map.Entry entry = chanMap.nextEntry(null); entry != null; entry = chanMap.nextEntry(entry))
                    {
                        com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)entry.getKey();
                        com.maddox.sound.ChannelContext channelcontext = (com.maddox.sound.ChannelContext)entry.getValue();
                        if(channelcontext != null)
                        {
                            int l = channelcontext.mc.get(tmpBuf, tmpBuf.length);
                            if(l > 0 && !(netchannel instanceof com.maddox.rts.NetChannelInStream))
                            {
                                if(tstMode == 2 && l > 1)
                                    java.lang.System.out.println("DATA FROM MIX - " + l);
                                com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
                                netmsgfiltered.write(tmpBuf, 0, l);
                                netmsgfiltered.setIncludeTime(false);
                                postTo(com.maddox.rts.Time.current(), netchannel, netmsgfiltered);
                                flag1 = false;
                            }
                        }
                    }

                }
            } while(!flag1 && codecId == 2);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        if(netchannel instanceof com.maddox.rts.NetChannelInStream)
            return null;
        com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
        netmsgspawn.writeInt(codecId);
        netmsgspawn.write255(name);
        netmsgspawn.writeBoolean(getActState());
        chanMap.put(netchannel, new ChannelContext(mixer, netchannel instanceof com.maddox.rts.NetChannelOutStream));
        if(activeChannel == this && (netchannel instanceof com.maddox.rts.NetChannelOutStream))
            postTrackActive(true);
        if(tstMode > 0)
            java.lang.System.out.println("Radio : replicate " + name);
        return netmsgspawn;
    }

    public void setActive(boolean flag)
    {
        try
        {
            boolean flag1 = activeChannel == this;
            isActive = flag;
            if(flag)
            {
                if(activeChannel != null && activeChannel != this)
                    activeChannel.setActive(false);
                activeChannel = this;
                if(com.maddox.sound.BaseObject.enabled)
                    com.maddox.sound.AudioDevice.setInput(codecId);
            } else
            if(activeChannel == this)
                activeChannel = null;
            if(lch != null)
                lch.setActive(flag);
            boolean flag2 = useMixer() || isActive;
            if(masterChannel() != null && !masterChannel().isDestroying() && !(masterChannel() instanceof com.maddox.rts.NetChannelInStream))
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(1);
                netmsgguaranted.writeBoolean(flag2);
                postTo(masterChannel(), netmsgguaranted);
            }
            if(countMirrors() > 0)
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                netmsgguaranted1.writeByte(1);
                netmsgguaranted1.writeBoolean(flag2);
                post(netmsgguaranted1);
            }
            if(com.maddox.il2.net.NetMissionTrack.isRecording())
                postTrackActive(flag);
            if(tstMode > 0 && isActive != flag1)
                java.lang.System.out.println("Radio channel " + name + (isActive ? " activated" : " off"));
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void postTrackActive(boolean flag)
    {
        new com.maddox.rts.MsgAction(true, flag ? new Object() : null) {

            public void doAction(java.lang.Object obj)
            {
                if(!com.maddox.il2.net.NetMissionTrack.isRecording())
                    return;
                doMsgReplayActive(obj != null, com.maddox.il2.net.NetMissionTrack.netChannelOut());
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(4);
                    netmsgguaranted.writeBoolean(obj != null);
                    postTo(com.maddox.il2.net.NetMissionTrack.netChannelOut(), netmsgguaranted);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                }
            }

        }
;
    }

    public void printInfo()
    {
        java.lang.String s = "  NUM CHS : " + chanMap.size();
        if(isActive)
            s = s + " - ACT ";
        else
            s = s + "       ";
        java.lang.System.out.println(s + "[name : " + name + "]");
    }

    public static void hack()
    {
        if(tstLoop && activeChannel != null)
            activeChannel.netUpdate();
    }

    public static void printState()
    {
        if(activeChannel != null && activeChannel.mixer != null)
            activeChannel.mixer.printState(50);
    }

    protected static final byte MSG_SET_ACTIVE = 1;
    protected static final byte MSG_KILL = 2;
    protected static final byte MSG_REPLY_ACTIVE = 3;
    protected static final byte MSG_TRACK_ACTIVE = 4;
    protected static final int DefaultMixNum = 3;
    public static final int TST_MODE_BRIEF = 1;
    public static final int TST_MODE_BLOCKS = 2;
    public static final int TST_MODE_MIXER = 3;
    public static final boolean tstMixer = false;
    public static int tstMode = 0;
    public static boolean tstLoop = false;
    java.lang.String name;
    com.maddox.netphone.NetMixer mixer;
    com.maddox.netphone.MixChannel lch;
    com.maddox.sound.SoundReceiver och;
    boolean isActive;
    byte tmpBuf[];
    com.maddox.util.HashMapExt chanMap;
    com.maddox.sound.RadioChannelSpawn rcSpawn;
    int codecId;
    int mixCnt;
    static int currentCodecId = 1;
    protected static com.maddox.sound.RadioChannel activeChannel = null;


}
