package com.maddox.il2.game;

import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.InOutStreams;
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

public class GameTrack extends NetObj
{
  public static final int SINGLE_VERSION = 129;
  public static final int NET_VERSION = 102;
  public static final int HUD_log_Integer = 0;
  public static final int HUD_log_Id = 1;
  public static final int HUD_log_RightBottom = 2;
  public static final int HUD_log_Center = 3;
  public static final int VOICE = 4;
  public static final int HOT_KEY_SIGHT = 5;
  private NetChannel channel;

  public NetChannel channel()
  {
    return this.channel;
  }
  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
    if (Main3D.cur3D().gameTrackRecord() != null)
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 0);
        Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
      } catch (Exception localException) {
      }
    int i = paramNetMsgInput.readByte();

    switch (i) {
    case 0:
    case 1:
    case 2:
    case 3:
      Main3D.cur3D().hud.netInputLog(i, paramNetMsgInput);
      break;
    case 4:
      Voice.netInputPlay(paramNetMsgInput);
      break;
    case 5:
      Main3D.cur3D().aircraftHotKeys.fromTrackSign(paramNetMsgInput);
      break;
    default:
      return false;
    }
    return true;
  }

  public void startKeyPlay() {
    Main3D.cur3D().startPlayRecordedMission();
    Main3D.cur3D().keyRecord.startRecordingNet();
  }

  public void startKeyRecord(NetMsgGuaranted paramNetMsgGuaranted) {
    try {
      ((NetChannelOutStream)this.channel).flush();

      InOutStreams localInOutStreams = NetMissionTrack.recordingStreams();
      if (localInOutStreams == null) return;
      Main3D.cur3D().saveRecordedStates0(localInOutStreams);
      Main3D.cur3D().saveRecordedStates1(localInOutStreams);
      Main3D.cur3D().saveRecordedStates2(localInOutStreams);

      NetChannelInStream.sendSyncMsg(this.channel);
      ((NetChannelOutStream)this.channel)._putMessage(paramNetMsgGuaranted);
      ((NetChannelOutStream)this.channel).flush();

      Main3D.cur3D().keyRecord.startRecordingNet();

      System.out.println("Start Recording: " + NetMissionTrack.recordingFileName);
      String str = NetMissionTrack.recordingFileName;
      str = str.substring(str.indexOf("/") + 1);
      HUD.log(0, I18N.hud_log("StartRecording") + " " + str, false);
      this.channel.setMaxSpeed(NetMissionTrack.getRecordSpeed());
      ((NetChannelOutStream)this.channel).setCheckSpeed(true);
      NetMissionTrack.startedRecording();
    }
    catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public GameTrack(NetChannel paramNetChannel) {
    super(null);
    this.channel = paramNetChannel;
    Main3D.cur3D().setGameTrackRecord(this);
  }

  public GameTrack(NetChannel paramNetChannel, int paramInt) {
    super(null, paramNetChannel, paramInt);
    this.channel = paramNetChannel;
    Main3D.cur3D().setGameTrackPlay(this);
  }

  public void msgNetDelChannel(NetChannel paramNetChannel) {
    if (this.channel == paramNetChannel)
      destroy();
  }

  public void destroy() {
    Main3D.cur3D().clearGameTrack(this);
    this.channel = null;
    super.destroy();
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException {
    if ((!(paramNetChannel instanceof NetChannelOutStream)) || (isMirror()))
      return null;
    NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
    return localNetMsgSpawn;
  }

  static
  {
    Spawn.add(GameTrack.class, new SPAWN());
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        new GameTrack(paramNetMsgInput.channel(), paramInt); } catch (Exception localException) {
        GameTrack.access$000(localException);
      }
    }
  }
}