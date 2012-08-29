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
import java.util.Map.Entry;

public class RadioChannel extends NetObj
  implements NetUpdate
{
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

  String name = null;
  NetMixer mixer = null;
  MixChannel lch = null;
  SoundReceiver och = null;
  boolean isActive = false;
  byte[] tmpBuf = new byte[64];
  HashMapExt chanMap = new HashMapExt();
  RadioChannelSpawn rcSpawn = null;
  int codecId = 0;
  int mixCnt;
  static int currentCodecId = 1;

  protected static RadioChannel activeChannel = null;

  protected boolean isHaveLocal()
  {
    NetServerParams localNetServerParams = Main.cur().netServerParams;
    if (localNetServerParams == null) return true;
    return (localNetServerParams.isMirror()) || (!localNetServerParams.isDedicated());
  }

  public RadioChannel(String paramString, RadioChannelSpawn paramRadioChannelSpawn, int paramInt)
  {
    super(null);
    this.codecId = paramInt;
    this.rcSpawn = paramRadioChannelSpawn;
    this.name = paramString;
    this.mixCnt = 2;
    init(isHaveLocal());
    if (tstMode > 0) System.out.println("Radio : new master - " + paramString + " Local = " + isHaveLocal() + " codec - " + getCodecName());
  }

  public RadioChannel(NetMsgInput paramNetMsgInput, int paramInt, RadioChannelSpawn paramRadioChannelSpawn)
  {
    super(null, paramNetMsgInput.channel(), paramInt);
    try
    {
      this.rcSpawn = paramRadioChannelSpawn;
      this.codecId = paramNetMsgInput.readInt();
      this.name = paramNetMsgInput.read255();
      boolean bool = paramNetMsgInput.readBoolean();
      this.mixCnt = 3;
      init(isHaveLocal());
      if (!checkCodecId(this.codecId)) this.codecId = 1;
      ChannelContext localChannelContext = new ChannelContext(this.mixer, false);
      localChannelContext.setActive(bool);
      this.chanMap.put(paramNetMsgInput.channel(), localChannelContext);
      if (tstMode > 0) System.out.println("Radio : new mirror - " + this.name + " Local = " + isHaveLocal() + " codec - " + getCodecName() + " act " + bool); 
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public static RadioChannel activeChannel()
  {
    return activeChannel;
  }

  protected static boolean checkCodecId(int paramInt)
  {
    if ((paramInt != 1) && (paramInt != 2)) {
      System.out.println("Invalid codec id : " + paramInt);
      return false;
    }
    return true;
  }

  public static int getCurrentCodec()
  {
    return currentCodecId;
  }

  public static void setCurrentCodec(int paramInt)
  {
    if (!checkCodecId(paramInt)) return;
    currentCodecId = paramInt;
  }

  public String getCodecName()
  {
    switch (this.codecId) { case 1:
      return "LPCM";
    case 2:
      return "HQ";
    }
    return "Unknown";
  }

  public static String getCurrentCodecName()
  {
    switch (currentCodecId) { case 1:
      return "LPCM";
    case 2:
      return "HQ";
    }
    return "Unknown";
  }

  protected void init(boolean paramBoolean)
  {
    this.mixer = new NetMixer(this.codecId);
    if (paramBoolean) {
      if (this.lch == null) this.lch = this.mixer.newChannel(false);
      if (this.och == null) this.och = new SoundReceiver(this.codecId);
    }
  }

  public void destroy()
  {
    if (!isDestroyed()) {
      super.destroy();
      if (this.rcSpawn != null) this.rcSpawn.onDestroyChannel(this);
      if (this.lch != null) {
        this.lch.destroy();
        this.lch = null;
      }
      if (this.och != null) {
        this.och.destroy();
        this.och = null;
      }
      Map.Entry localEntry = this.chanMap.nextEntry(null);
      while (localEntry != null) {
        ChannelContext localChannelContext = (ChannelContext)localEntry.getValue();
        localChannelContext.destroy();
        localEntry = this.chanMap.nextEntry(localEntry);
      }
      this.chanMap.clear();
      if (this.mixer != null) {
        this.mixer.destroy();
        this.mixer = null;
      }
      if (tstMode > 0) System.out.println("Radio : destroyed - " + this.name);
    }
  }

  protected void finalize()
  {
    destroy();
  }

  public void msgNetDelChannel(NetChannel paramNetChannel)
  {
    ChannelContext localChannelContext = (ChannelContext)this.chanMap.get(paramNetChannel);
    if (localChannelContext != null) {
      this.chanMap.remove(paramNetChannel);
      localChannelContext.destroy();
    }
  }

  public boolean getActState()
  {
    int i = 0;
    Map.Entry localEntry = this.chanMap.nextEntry(null);
    while (localEntry != null) {
      ChannelContext localChannelContext = (ChannelContext)localEntry.getValue();
      if (localChannelContext.isActive()) i++;
      localEntry = this.chanMap.nextEntry(localEntry);
    }
    return (i > 1) || (this.isActive);
  }

  protected void rcstat()
  {
    System.out.println();
    Map.Entry localEntry = this.chanMap.nextEntry(null);
    while (localEntry != null) {
      ChannelContext localChannelContext = (ChannelContext)localEntry.getValue();
      System.out.println("ACT -> " + localChannelContext.isActive());
      localEntry = this.chanMap.nextEntry(localEntry);
    }
  }

  private void doMsgReplayActive(boolean paramBoolean, NetChannel paramNetChannel)
  {
    ChannelContext localChannelContext = (ChannelContext)this.chanMap.get(paramNetChannel);
    if (localChannelContext != null) localChannelContext.setActive(paramBoolean); 
  }

  public boolean netInput(NetMsgInput paramNetMsgInput)
  {
    try
    {
      if (super.netInput(paramNetMsgInput)) return true;
      Object localObject1;
      Object localObject2;
      Object localObject3;
      if (paramNetMsgInput.isGuaranted()) {
        switch (paramNetMsgInput.readByte()) {
        case 3:
          doMsgReplayActive(paramNetMsgInput.readBoolean(), paramNetMsgInput.channel());

          break;
        case 1:
          boolean bool = paramNetMsgInput.readBoolean();

          localObject1 = (ChannelContext)this.chanMap.get(paramNetMsgInput.channel());
          if (localObject1 == null)
            System.out.println("Channel context not found !");
          else {
            ((ChannelContext)localObject1).setActive(bool);
          }
          if (!bool) bool = getActState();
          localObject2 = this.chanMap.nextEntry(null);
          while (localObject2 != null) {
            localObject3 = (NetChannel)((Map.Entry)localObject2).getKey();
            if ((localObject3 != paramNetMsgInput.channel()) && (!(localObject3 instanceof NetChannelInStream))) {
              NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
              localNetMsgGuaranted.writeByte(1);
              localNetMsgGuaranted.writeBoolean(bool);
              postTo((NetChannel)localObject3, localNetMsgGuaranted);
            }
            localObject2 = this.chanMap.nextEntry((Map.Entry)localObject2);
          }

          bool = getActState();

          if (!(paramNetMsgInput.channel() instanceof NetChannelInStream)) {
            localObject3 = new NetMsgGuaranted();
            ((NetMsgGuaranted)localObject3).writeByte(3);
            ((NetMsgGuaranted)localObject3).writeBoolean(bool);
            postTo(paramNetMsgInput.channel(), (NetMsgGuaranted)localObject3);
          }

          if (tstMode <= 0) break; System.out.println("activation : " + bool); break;
        case 4:
          setActive(paramNetMsgInput.readBoolean());

          break;
        case 2:
        default:
          System.out.println("Invalid radio message."); break;
        }
      }
      else if ((!tstLoop) && (Time.speed() == 1.0F)) {
        int i = paramNetMsgInput.available();
        paramNetMsgInput.read(this.tmpBuf, 0, i);
        if (useMixer()) {
          localObject1 = paramNetMsgInput.channel();
          localObject2 = (ChannelContext)this.chanMap.get(localObject1);
          if (localObject2 != null)
            if (((ChannelContext)localObject2).isActive()) {
              if ((tstMode == 2) && (i > 1)) System.out.println("DATA TO MIXER - " + i + " - " + this.chanMap.size());
              ((ChannelContext)localObject2).mc.put(this.tmpBuf, i);
            }
            else if ((tstMode == 2) && (i > 1)) { System.out.println("NOTACT"); }
        }
        else
        {
          if (this.och != null) {
            if (i > 0) this.och.put(this.tmpBuf, i);
            if ((tstMode == 2) && (i > 1)) System.out.println("DATA TO OUTPUT - " + i);
          }
          if ((i > 0) && (this.chanMap.size() == 2)) {
            localObject1 = this.chanMap.nextEntry(null);
            while (localObject1 != null) {
              localObject2 = (NetChannel)((Map.Entry)localObject1).getKey();
              if (localObject2 != paramNetMsgInput.channel()) {
                localObject3 = new NetMsgFiltered();
                ((NetMsgFiltered)localObject3).write(this.tmpBuf, 0, i);
                ((NetMsgFiltered)localObject3).setIncludeTime(false);
                postTo(Time.current(), (NetChannel)localObject2, (NetMsgFiltered)localObject3);
                break;
              }
              localObject1 = this.chanMap.nextEntry((Map.Entry)localObject1);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    return true;
  }

  protected boolean useMixer()
  {
    return (this.lch == null) || (this.chanMap.size() >= this.mixCnt);
  }

  public void netUpdate()
  {
    boolean bool = useMixer();
    try {
      do {
        int i = 1;
        if (tstMode == 3) {
          TextScr.output(100, 50, Integer.toString(this.lch.getDataLength()));
          if (bool) this.mixer.printState(80);
        }
        int j;
        Object localObject1;
        Object localObject2;
        if ((activeChannel == this) && (BaseObject.enabled)) {
          j = AudioDevice.getInputData(this.tmpBuf, this.tmpBuf.length);
          if (tstLoop) {
            if ((this.och != null) && 
              (j > 0)) {
              if ((tstMode == 2) && (j > 1)) System.out.println("DATA INPUT - " + j);
              this.och.put(this.tmpBuf, j);
              i = 0;
            }

          }
          else if (j > 0) {
            i = 0;
            if (bool) {
              if ((this.lch != null) && 
                (this.lch.isActive())) {
                if ((tstMode == 2) && (j > 1)) System.out.println("DATA FROM LOCAL - " + j);
                this.lch.put(this.tmpBuf, j);
              }
            }
            else {
              int k = 0;
              localObject1 = this.chanMap.nextEntry(null);
              while (localObject1 != null) {
                NetChannel localNetChannel2 = (NetChannel)((Map.Entry)localObject1).getKey();
                localObject2 = (ChannelContext)((Map.Entry)localObject1).getValue();
                if ((((ChannelContext)localObject2).isActive()) && (!(localNetChannel2 instanceof NetChannelInStream))) {
                  NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
                  localNetMsgFiltered.write(this.tmpBuf, 0, j);
                  localNetMsgFiltered.setIncludeTime(false);
                  postTo(Time.current(), localNetChannel2, localNetMsgFiltered);
                  k++;
                }
                localObject1 = this.chanMap.nextEntry((Map.Entry)localObject1);
              }
              if ((tstMode == 2) && (j > 1)) System.out.println("DATA FROM INP - " + j + " nc " + k);
            }
          }
        }

        if ((bool) && (!tstLoop)) {
          this.mixer.tick();
          if ((activeChannel == this) && 
            (this.lch != null) && (this.och != null)) {
            j = this.lch.get(this.tmpBuf, this.tmpBuf.length);
            if (j > 0) {
              if ((tstMode == 2) && (j > 1)) System.out.println("DATA TO CPP - " + j);
              this.och.put(this.tmpBuf, j);
              i = 0;
            }
          }

          Map.Entry localEntry = this.chanMap.nextEntry(null);
          while (localEntry != null) {
            NetChannel localNetChannel1 = (NetChannel)localEntry.getKey();
            localObject1 = (ChannelContext)localEntry.getValue();
            if (localObject1 != null) {
              int m = ((ChannelContext)localObject1).mc.get(this.tmpBuf, this.tmpBuf.length);
              if ((m > 0) && (!(localNetChannel1 instanceof NetChannelInStream))) {
                if ((tstMode == 2) && (m > 1)) System.out.println("DATA FROM MIX - " + m);
                localObject2 = new NetMsgFiltered();
                ((NetMsgFiltered)localObject2).write(this.tmpBuf, 0, m);
                ((NetMsgFiltered)localObject2).setIncludeTime(false);
                postTo(Time.current(), localNetChannel1, (NetMsgFiltered)localObject2);
                i = 0;
              }
            }
            localEntry = this.chanMap.nextEntry(localEntry);
          }
        }
        if (i != 0) break; 
      }while (this.codecId == 2);
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel)
    throws IOException
  {
    if ((paramNetChannel instanceof NetChannelInStream))
      return null;
    NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
    localNetMsgSpawn.writeInt(this.codecId);
    localNetMsgSpawn.write255(this.name);
    localNetMsgSpawn.writeBoolean(getActState());
    this.chanMap.put(paramNetChannel, new ChannelContext(this.mixer, paramNetChannel instanceof NetChannelOutStream));
    if ((activeChannel == this) && ((paramNetChannel instanceof NetChannelOutStream)))
      postTrackActive(true);
    if (tstMode > 0) System.out.println("Radio : replicate " + this.name);
    return localNetMsgSpawn;
  }

  public void setActive(boolean paramBoolean)
  {
    try {
      int i = activeChannel == this ? 1 : 0;

      this.isActive = paramBoolean;
      if (paramBoolean) {
        if ((activeChannel != null) && (activeChannel != this)) activeChannel.setActive(false);
        activeChannel = this;
        if (BaseObject.enabled) AudioDevice.setInput(this.codecId);
      }
      else if (activeChannel == this) { activeChannel = null;
      }

      if (this.lch != null) this.lch.setActive(paramBoolean);

      boolean bool = (useMixer()) || (this.isActive);
      NetMsgGuaranted localNetMsgGuaranted;
      if ((masterChannel() != null) && (!masterChannel().isDestroying()) && (!(masterChannel() instanceof NetChannelInStream)))
      {
        localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(1);
        localNetMsgGuaranted.writeBoolean(bool);
        postTo(masterChannel(), localNetMsgGuaranted);
      }
      if (countMirrors() > 0) {
        localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(1);
        localNetMsgGuaranted.writeBoolean(bool);
        post(localNetMsgGuaranted);
      }

      if (NetMissionTrack.isRecording()) {
        postTrackActive(paramBoolean);
      }
      if ((tstMode > 0) && (this.isActive != i))
        System.out.println("Radio channel " + this.name + (this.isActive ? " activated" : " off"));
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void postTrackActive(boolean paramBoolean) {
    new MsgAction(true, paramBoolean ? new Object() : null) {
      public void doAction(Object paramObject) { if (!NetMissionTrack.isRecording()) return;
        RadioChannel.this.doMsgReplayActive(paramObject != null, NetMissionTrack.netChannelOut());
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(4);
          localNetMsgGuaranted.writeBoolean(paramObject != null);
          RadioChannel.this.postTo(NetMissionTrack.netChannelOut(), localNetMsgGuaranted);
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
        } }
    };
  }

  public void printInfo() {
    String str = "  NUM CHS : " + this.chanMap.size();
    if (this.isActive) str = str + " - ACT "; else
      str = str + "       ";
    System.out.println(str + "[name : " + this.name + "]");
  }

  public static void hack()
  {
    if ((tstLoop) && (activeChannel != null)) activeChannel.netUpdate();
  }

  public static void printState()
  {
    if ((activeChannel != null) && 
      (activeChannel.mixer != null)) activeChannel.mixer.printState(50);
  }
}