// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMissionTrack.java

package com.maddox.il2.net;

import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameTrack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.HomePath;
import com.maddox.rts.InOutStreams;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.MsgNet;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

// Referenced classes of package com.maddox.il2.net:
//            NetFilesTrack, NetChannelListener

public class NetMissionTrack
{
    static class OutChannelCreater
        implements com.maddox.rts.MsgTimeOutListener
    {

        public void msgTimeOut(java.lang.Object obj)
        {
            if(obj != null && (obj instanceof com.maddox.rts.NetChannel))
            {
                com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)obj;
                if(netchannel.isDestroying())
                    return;
                int i = netchannel.state();
                switch(i)
                {
                case 2: // '\002'
                    try
                    {
                        netchannel.stopSortGuaranted();
                    }
                    catch(java.lang.Exception exception)
                    {
                        netchannel.destroy("Cycle inits");
                        com.maddox.il2.net.NetMissionTrack.printDebug(exception);
                        return;
                    }
                    netchannel.setStateInit(0);
                    if(com.maddox.il2.game.Main.cur().netChannelListener != null)
                        com.maddox.il2.game.Main.cur().netChannelListener.netChannelCreated(netchannel);
                    netchannel.userState = 1;
                    if(com.maddox.il2.game.Mission.cur() != null && com.maddox.il2.game.Mission.cur().netObj() != null)
                        com.maddox.rts.MsgNet.postRealNewChannel(com.maddox.il2.game.Mission.cur().netObj(), netchannel);
                    return;
                }
                return;
            } else
            {
                return;
            }
        }

        com.maddox.rts.MsgTimeOut ticker;

        public OutChannelCreater()
        {
            ticker = new MsgTimeOut();
            ticker.setNotCleanAfterSend();
            ticker.setFlags(64);
            ticker.setListener(this);
            com.maddox.il2.net.NetMissionTrack.netOut.startSortGuaranted();
            com.maddox.util.HashMapInt hashmapint = com.maddox.rts.NetEnv.cur().objects;
            for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
            {
                com.maddox.rts.NetObj netobj = (com.maddox.rts.NetObj)hashmapintentry.getValue();
                if(!com.maddox.il2.net.NetMissionTrack.netOut.isMirrored(netobj) && !netobj.isCommon())
                    com.maddox.rts.MsgNet.postRealNewChannel(netobj, com.maddox.il2.net.NetMissionTrack.netOut);
            }

            com.maddox.il2.net.NetMissionTrack.netOut.setStateInit(2);
            com.maddox.rts.MsgNet.postRealNewChannel((com.maddox.rts.NetObj)com.maddox.rts.NetEnv.cur().objects.get(9), com.maddox.il2.net.NetMissionTrack.netOut);
            com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + 1L, this, com.maddox.il2.net.NetMissionTrack.netOut);
        }
    }


    public NetMissionTrack()
    {
    }

    public static com.maddox.rts.InOutStreams recordingStreams()
    {
        return io;
    }

    public static boolean isRecording()
    {
        return io != null;
    }

    public static boolean isQuickRecording()
    {
        return io != null && bQuick;
    }

    public static com.maddox.rts.NetChannel netChannelOut()
    {
        return netOut;
    }

    public static boolean isPlaying()
    {
        if(!(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D))
            return false;
        else
            return com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null;
    }

    public static float getRecordSpeed()
    {
        return recordSpeed;
    }

    public static int playingVersion()
    {
        return playingVersion;
    }

    public static int playingOriginalVersion()
    {
        return playingOriginalVersion;
    }

    public static void startPlaying(com.maddox.rts.InOutStreams inoutstreams, int i, int j)
    {
        try
        {
            java.io.InputStream inputstream = inoutstreams.openStream("speed");
            java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            recordSpeed = java.lang.Float.parseFloat(bufferedreader.readLine());
            inputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            recordSpeed = 100F;
        }
        playingVersion = i;
        playingOriginalVersion = j;
        netFilesTrackPlaying = new NetFilesTrack();
        netFilesTrackPlaying.startPlaying(inoutstreams);
    }

    public static void stopPlaying()
    {
        if(netFilesTrackPlaying == null)
        {
            return;
        } else
        {
            netFilesTrackPlaying.stopPlaying();
            netFilesTrackPlaying = null;
            return;
        }
    }

    public static void stopRecording()
    {
        if(!com.maddox.il2.net.NetMissionTrack.isRecording())
            return;
        try
        {
            bQuick = false;
            java.lang.String s = recordingFileName;
            s = s.substring(s.indexOf("/") + 1);
            if(bRecordStarting)
            {
                java.lang.System.out.println("Track NOT Saved");
                com.maddox.il2.game.HUD.log(0, com.maddox.il2.game.I18N.hud_log("TrackNotSaved"), false);
            } else
            {
                java.lang.System.out.println("Stop Recording: " + recordingFileName);
                com.maddox.il2.game.HUD.log(0, com.maddox.il2.game.I18N.hud_log("StopRecording") + " " + s, false);
            }
            netFilesTrackRecording.stopRecording();
            com.maddox.il2.game.Main3D.cur3D().keyRecord.stopRecording(true);
            com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
            com.maddox.il2.game.Main3D.cur3D().keyRecord.clearListExcludeCmdEnv();
            netOut.destroy();
            io.close();
            if(bRecordStarting)
            {
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(recordingFileName, 0));
                file.delete();
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetMissionTrack.printDebug(exception);
        }
        netFilesTrackRecording = null;
        netOut = null;
        io = null;
    }

    public static void startedRecording()
    {
        bRecordStarting = false;
    }

    public static void startRecording(java.lang.String s, float f)
    {
        com.maddox.il2.net.NetMissionTrack.stopRecording();
        countRecorded++;
        recordingFileName = s;
        recordSpeed = f;
        io = new InOutStreams();
        try
        {
            bRecordStarting = true;
            io.create(new File(s), 2, 32768);
            java.io.PrintWriter printwriter = new PrintWriter(io.createStream("speed"));
            printwriter.println(f);
            printwriter.flush();
            printwriter.close();
            printwriter = new PrintWriter(io.createStream("version"));
            printwriter.println(102);
            if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                printwriter.println(com.maddox.il2.net.NetMissionTrack.playingOriginalVersion());
            else
                printwriter.println(102);
            printwriter.flush();
            printwriter.close();
            java.io.OutputStream outputstream = io.createStream("traffic");
            netOut = new NetChannelOutStream(outputstream, 3);
            netOut.setMaxSpeed(100D);
            ((com.maddox.rts.NetChannelOutStream)netOut).setCheckSpeed(false);
            com.maddox.rts.RTSConf.cur.netEnv.addChannel(netOut);
            new GameTrack(netOut);
            new OutChannelCreater();
            com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
            com.maddox.il2.game.Main3D.cur3D().keyRecord.stopRecording(false);
            com.maddox.il2.game.Main3D.cur3D().keyRecord.addExcludeCmdEnv("pilot");
            com.maddox.il2.game.Main3D.cur3D().keyRecord.addExcludeCmdEnv("move");
            com.maddox.il2.game.Main3D.cur3D().keyRecord.addExcludeCmdEnv("gunner");
            netFilesTrackRecording = new NetFilesTrack();
            netFilesTrackRecording.startRecording();
        }
        catch(java.lang.Exception exception)
        {
            netOut = null;
            io = null;
            netFilesTrackRecording = null;
            com.maddox.il2.net.NetMissionTrack.printDebug(exception);
        }
    }

    public static void startQuickRecording()
    {
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(com.maddox.il2.net.NetMissionTrack.isRecording())
            return;
        float f = (float)com.maddox.il2.engine.Config.cur.netSpeed / 1000F + 5F;
        if(com.maddox.il2.net.NetMissionTrack.isPlaying())
            f = recordSpeed;
        java.lang.String s = com.maddox.il2.net.NetMissionTrack.quickFileName();
        com.maddox.il2.net.NetMissionTrack.startRecording(s, f);
        bQuick = true;
    }

    private static java.lang.String quickFileName()
    {
        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName("records", 0));
        java.lang.String as[] = file.list();
        if(as == null || as.length == 0)
            return "records/quick0000.ntrk";
        int i = 0;
        for(int j = 0; j < as.length; j++)
        {
            java.lang.String s = as[j];
            if(s != null)
            {
                s = s.toLowerCase();
                if(s.startsWith("quick") && s.length() == "quick0000.ntrk".length())
                {
                    java.lang.String s1 = s.substring(s.length() - "0000.ntrk".length(), s.length() - ".ntrk".length());
                    try
                    {
                        int k = java.lang.Integer.parseInt(s1);
                        if(i <= k)
                            i = k + 1;
                    }
                    catch(java.lang.Exception exception) { }
                }
            }
        }

        if(i > 999)
            return "records/quick" + i + ".ntrk";
        if(i > 99)
            return "records/quick0" + i + ".ntrk";
        if(i > 9)
            return "records/quick00" + i + ".ntrk";
        else
            return "records/quick000" + i + ".ntrk";
    }

    static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    protected static com.maddox.rts.InOutStreams io;
    protected static com.maddox.rts.NetChannel netOut;
    protected static com.maddox.il2.net.NetFilesTrack netFilesTrackRecording;
    protected static com.maddox.il2.net.NetFilesTrack netFilesTrackPlaying;
    public static long playingStartTime;
    public static java.lang.String recordingFileName;
    private static boolean bQuick;
    private static float recordSpeed;
    private static boolean bRecordStarting;
    public static int countRecorded;
    private static int playingVersion;
    private static int playingOriginalVersion;
}
