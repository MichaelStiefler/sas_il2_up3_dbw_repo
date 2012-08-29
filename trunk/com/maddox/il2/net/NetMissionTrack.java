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
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class NetMissionTrack
{
  protected static InOutStreams io;
  protected static NetChannel netOut;
  protected static NetFilesTrack netFilesTrackRecording;
  protected static NetFilesTrack netFilesTrackPlaying;
  public static long playingStartTime;
  public static String recordingFileName;
  private static boolean bQuick;
  private static float recordSpeed;
  private static boolean bRecordStarting;
  public static int countRecorded;
  private static int playingVersion;
  private static int playingOriginalVersion;

  public static InOutStreams recordingStreams()
  {
    return io;
  }
  public static boolean isRecording() {
    return io != null;
  }
  public static boolean isQuickRecording() {
    return (io != null) && (bQuick);
  }

  public static NetChannel netChannelOut() {
    return netOut;
  }

  public static boolean isPlaying() {
    if (!(Main.cur() instanceof Main3D)) return false;
    return Main3D.cur3D().playRecordedStreams() != null;
  }
  public static float getRecordSpeed() {
    return recordSpeed;
  }
  public static int playingVersion() { return playingVersion; } 
  public static int playingOriginalVersion() {
    return playingOriginalVersion;
  }
  public static void startPlaying(InOutStreams paramInOutStreams, int paramInt1, int paramInt2) {
    try {
      InputStream localInputStream = paramInOutStreams.openStream("speed");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
      recordSpeed = Float.parseFloat(localBufferedReader.readLine());
      localInputStream.close();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      recordSpeed = 100.0F;
    }
    playingVersion = paramInt1;
    playingOriginalVersion = paramInt2;
    netFilesTrackPlaying = new NetFilesTrack();
    netFilesTrackPlaying.startPlaying(paramInOutStreams);
  }
  public static void stopPlaying() {
    if (netFilesTrackPlaying == null) return;
    netFilesTrackPlaying.stopPlaying();
    netFilesTrackPlaying = null;
  }

  public static void stopRecording() {
    if (!isRecording()) return; try
    {
      bQuick = false;
      String str = recordingFileName;
      str = str.substring(str.indexOf("/") + 1);
      if (bRecordStarting) {
        System.out.println("Track NOT Saved");
        HUD.log(0, I18N.hud_log("TrackNotSaved"), false);
      } else {
        System.out.println("Stop Recording: " + recordingFileName);
        HUD.log(0, I18N.hud_log("StopRecording") + " " + str, false);
      }
      netFilesTrackRecording.stopRecording();
      Main3D.cur3D().keyRecord.stopRecording(true);
      Main3D.cur3D().keyRecord.clearRecorded();
      Main3D.cur3D().keyRecord.clearListExcludeCmdEnv();
      netOut.destroy();
      io.close();
      if (bRecordStarting) {
        File localFile = new File(HomePath.toFileSystemName(recordingFileName, 0));
        localFile.delete();
      }
    } catch (Exception localException) {
      printDebug(localException);
    }netFilesTrackRecording = null;
    netOut = null;
    io = null;
  }

  public static void startedRecording() {
    bRecordStarting = false;
  }
  public static void startRecording(String paramString, float paramFloat) {
    stopRecording();
    countRecorded += 1;
    recordingFileName = paramString;
    recordSpeed = paramFloat;
    io = new InOutStreams();
    try {
      bRecordStarting = true;
      io.create(new File(paramString), 2, 32768);
      PrintWriter localPrintWriter = new PrintWriter(io.createStream("speed"));
      localPrintWriter.println(paramFloat);
      localPrintWriter.flush(); localPrintWriter.close();
      localPrintWriter = new PrintWriter(io.createStream("version"));
      localPrintWriter.println(103);
      if (isPlaying())
        localPrintWriter.println(playingOriginalVersion());
      else
        localPrintWriter.println(103);
      localPrintWriter.flush(); localPrintWriter.close();
      OutputStream localOutputStream = io.createStream("traffic");
      netOut = new NetChannelOutStream(localOutputStream, 3);
      netOut.setMaxSpeed(100.0D);
      ((NetChannelOutStream)netOut).setCheckSpeed(false);
      RTSConf.cur.netEnv.addChannel(netOut);
      new GameTrack(netOut);
      new OutChannelCreater();
      Main3D.cur3D().keyRecord.clearRecorded();
      Main3D.cur3D().keyRecord.stopRecording(false);
      Main3D.cur3D().keyRecord.addExcludeCmdEnv("pilot");
      Main3D.cur3D().keyRecord.addExcludeCmdEnv("move");
      Main3D.cur3D().keyRecord.addExcludeCmdEnv("gunner");
      netFilesTrackRecording = new NetFilesTrack();
      netFilesTrackRecording.startRecording();
    } catch (Exception localException) {
      netOut = null;
      io = null;
      netFilesTrackRecording = null;
      printDebug(localException);
    }
  }

  public static void startQuickRecording() {
    if (!Mission.isPlaying()) return;
    if (isRecording()) return;
    float f = Config.cur.netSpeed / 1000.0F + 5.0F;
    if (isPlaying())
      f = recordSpeed;
    String str = quickFileName();

    startRecording(str, f);
    bQuick = true;
  }

  private static String quickFileName() {
    File localFile = new File(HomePath.toFileSystemName("records", 0));
    String[] arrayOfString = localFile.list();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
      return "records/quick0000.ntrk";
    int i = 0;
    for (int j = 0; j < arrayOfString.length; j++) {
      String str1 = arrayOfString[j];
      if (str1 != null) {
        str1 = str1.toLowerCase();
        if ((str1.startsWith("quick")) && (str1.length() == "quick0000.ntrk".length())) {
          String str2 = str1.substring(str1.length() - "0000.ntrk".length(), str1.length() - ".ntrk".length());
          try {
            int k = Integer.parseInt(str2);
            if (i <= k)
              i = k + 1; 
          } catch (Exception localException) {
          }
        }
      }
    }
    if (i > 999) return "records/quick" + i + ".ntrk";
    if (i > 99) return "records/quick0" + i + ".ntrk";
    if (i > 9) return "records/quick00" + i + ".ntrk";
    return "records/quick000" + i + ".ntrk";
  }

  static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  static class OutChannelCreater
    implements MsgTimeOutListener
  {
    MsgTimeOut ticker;

    public void msgTimeOut(Object paramObject)
    {
      if ((paramObject != null) && ((paramObject instanceof NetChannel))) {
        NetChannel localNetChannel = (NetChannel)paramObject;
        if (localNetChannel.isDestroying()) return;
        int i = localNetChannel.state();
        switch (i) {
        case 2:
          try {
            localNetChannel.stopSortGuaranted();
          } catch (Exception localException) {
            localNetChannel.destroy("Cycle inits");
            NetMissionTrack.printDebug(localException);
            return;
          }
          localNetChannel.setStateInit(0);
          if (Main.cur().netChannelListener != null)
            Main.cur().netChannelListener.netChannelCreated(localNetChannel);
          localNetChannel.userState = 1;
          if ((Mission.cur() != null) && (Mission.cur().netObj() != null))
            MsgNet.postRealNewChannel(Mission.cur().netObj(), localNetChannel);
          return;
        }
        return;
      }
    }

    public OutChannelCreater() {
      this.ticker = new MsgTimeOut();
      this.ticker.setNotCleanAfterSend();
      this.ticker.setFlags(64);
      this.ticker.setListener(this);

      NetMissionTrack.netOut.startSortGuaranted();
      HashMapInt localHashMapInt = NetEnv.cur().objects;
      HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);
      while (localHashMapIntEntry != null) {
        NetObj localNetObj = (NetObj)localHashMapIntEntry.getValue();
        if ((!NetMissionTrack.netOut.isMirrored(localNetObj)) && (!localNetObj.isCommon()))
          MsgNet.postRealNewChannel(localNetObj, NetMissionTrack.netOut);
        localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
      }
      NetMissionTrack.netOut.setStateInit(2);
      MsgNet.postRealNewChannel((NetObj)NetEnv.cur().objects.get(9), NetMissionTrack.netOut);
      MsgTimeOut.post(64, Time.currentReal() + 1L, this, NetMissionTrack.netOut);
    }
  }
}