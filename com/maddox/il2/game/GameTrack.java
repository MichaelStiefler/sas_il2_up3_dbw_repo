// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GameTrack.java

package com.maddox.il2.game;

import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.Spawn;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.game:
//            Main3D, HUD, AircraftHotKeys, I18N

public class GameTrack extends com.maddox.rts.NetObj
{
    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                new GameTrack(netmsginput.channel(), i);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        SPAWN()
        {
        }
    }


    public com.maddox.rts.NetChannel channel()
    {
        return channel;
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
                com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
        byte byte0 = netmsginput.readByte();
        switch(byte0)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            com.maddox.il2.game.Main3D.cur3D().hud.netInputLog(byte0, netmsginput);
            break;

        case 4: // '\004'
            com.maddox.il2.objects.sounds.Voice.netInputPlay(netmsginput);
            break;

        case 5: // '\005'
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.fromTrackSign(netmsginput);
            break;

        default:
            return false;
        }
        return true;
    }

    public void startKeyPlay()
    {
        com.maddox.il2.game.Main3D.cur3D().startPlayRecordedMission();
        com.maddox.il2.game.Main3D.cur3D().keyRecord.startRecordingNet();
    }

    public void startKeyRecord(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
    {
        com.maddox.rts.InOutStreams inoutstreams;
        ((com.maddox.rts.NetChannelOutStream)channel).flush();
        inoutstreams = com.maddox.il2.net.NetMissionTrack.recordingStreams();
        if(inoutstreams == null)
            return;
        try
        {
            com.maddox.il2.game.Main3D.cur3D().saveRecordedStates0(inoutstreams);
            com.maddox.il2.game.Main3D.cur3D().saveRecordedStates1(inoutstreams);
            com.maddox.il2.game.Main3D.cur3D().saveRecordedStates2(inoutstreams);
            com.maddox.rts.NetChannelInStream.sendSyncMsg(channel);
            ((com.maddox.rts.NetChannelOutStream)channel)._putMessage(netmsgguaranted);
            ((com.maddox.rts.NetChannelOutStream)channel).flush();
            com.maddox.il2.game.Main3D.cur3D().keyRecord.startRecordingNet();
            java.lang.System.out.println("Start Recording: " + com.maddox.il2.net.NetMissionTrack.recordingFileName);
            java.lang.String s = com.maddox.il2.net.NetMissionTrack.recordingFileName;
            s = s.substring(s.indexOf("/") + 1);
            com.maddox.il2.game.HUD.log(0, com.maddox.il2.game.I18N.hud_log("StartRecording") + " " + s, false);
            channel.setMaxSpeed(com.maddox.il2.net.NetMissionTrack.getRecordSpeed());
            ((com.maddox.rts.NetChannelOutStream)channel).setCheckSpeed(true);
            com.maddox.il2.net.NetMissionTrack.startedRecording();
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
        return;
    }

    public GameTrack(com.maddox.rts.NetChannel netchannel)
    {
        super(null);
        channel = netchannel;
        com.maddox.il2.game.Main3D.cur3D().setGameTrackRecord(this);
    }

    public GameTrack(com.maddox.rts.NetChannel netchannel, int i)
    {
        super(null, netchannel, i);
        channel = netchannel;
        com.maddox.il2.game.Main3D.cur3D().setGameTrackPlay(this);
    }

    public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(channel == netchannel)
            destroy();
    }

    public void destroy()
    {
        com.maddox.il2.game.Main3D.cur3D().clearGameTrack(this);
        channel = null;
        super.destroy();
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        if(!(netchannel instanceof com.maddox.rts.NetChannelOutStream) || isMirror())
        {
            return null;
        } else
        {
            com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
            return netmsgspawn;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int SINGLE_VERSION = 129;
    public static final int NET_VERSION = 102;
    public static final int HUD_log_Integer = 0;
    public static final int HUD_log_Id = 1;
    public static final int HUD_log_RightBottom = 2;
    public static final int HUD_log_Center = 3;
    public static final int VOICE = 4;
    public static final int HOT_KEY_SIGHT = 5;
    private com.maddox.rts.NetChannel channel;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.game.GameTrack.class, new SPAWN());
    }

}
