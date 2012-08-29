package com.maddox.rts;

import com.maddox.util.HashMapInt;
import com.maddox.util.SharedTokenizer;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class KeyRecord
  implements MsgHotKeyCmdListener, MsgTimeOutListener
{
  private LinkedList playLst = new LinkedList();
  private LinkedList recordLst = new LinkedList();
  private HashMap excludeCmdEnv = new HashMap();
  private HashMapInt excludePrevCmd = new HashMapInt();

  private boolean bPlay = false;
  private boolean bSave = false;
  private boolean bNet = false;
  private KeyRecordCallback callBack;
  private boolean bFirstHotCmd = true;
  private KeyRecordFinger fingerCalculator;
  private FingerPlayer fingerPlayer;
  private int[] fingers = new int[9];
  private int fingersSize = 0;
  private HotPlayer hotPlayer;
  private MsgTimeOut tickMsg = new MsgTimeOut();
  private NetKeyRecord net;
  public static final int ArgNONE = -1;
  private int curPlayArg0 = -1;
  private int curPlayArg1 = -1;
  private String curPlaySArg0 = null;
  private String curPlaySArg1 = null;

  private boolean bEnablePlayArgs = true;

  private int prevRecordId = 0;
  private int prevRecordStart = 0;
  private int prevRecordArg0 = -1;
  private int prevRecordArg1 = -1;
  private int curRecordArg0 = -1;
  private int curRecordArg1 = -1;
  private String curRecordSArg0 = null;
  private String curRecordSArg1 = null;

  public void addExcludeCmdEnv(String paramString)
  {
    this.excludeCmdEnv.put(paramString, null);
  }
  public void clearListExcludeCmdEnv() {
    this.excludeCmdEnv.clear();
  }

  public void addExcludePrevCmd(int paramInt) {
    this.excludePrevCmd.put(paramInt, null);
  }

  public void setEnablePlayArgs(boolean paramBoolean)
  {
    this.bEnablePlayArgs = paramBoolean;
  }
  public boolean isEnablePlayArgs() { return (this.bEnablePlayArgs) || (this.bFirstHotCmd); }

  public boolean isExistPlayArgs() {
    return ((this.bEnablePlayArgs) || (this.bFirstHotCmd)) && (_isExistPlayArgs());
  }
  public boolean _isExistPlayArgs() {
    return (this.curPlayArg0 != -1) || (this.curPlayArg1 != -1);
  }

  public boolean isExistPlaySArgs() {
    return ((this.bEnablePlayArgs) || (this.bFirstHotCmd)) && (_isExistPlaySArgs());
  }
  public boolean _isExistPlaySArgs() {
    return (this.curPlaySArg0 != null) || (this.curPlaySArg1 != null);
  }
  public int getPlayArg0() { return this.bEnablePlayArgs ? this.curPlayArg0 : -1; } 
  public int getPlayArg1() { return this.bEnablePlayArgs ? this.curPlayArg1 : -1; } 
  public String getPlaySArg0() { return this.bEnablePlayArgs ? this.curPlaySArg0 : null; } 
  public String getPlaySArg1() { return this.bEnablePlayArgs ? this.curPlaySArg1 : null; } 
  public int _getPlayArg0() {
    return this.curPlayArg0; } 
  public int _getPlayArg1() { return this.curPlayArg1; } 
  public String _getPlaySArg0() { return this.curPlaySArg0; } 
  public String _getPlaySArg1() { return this.curPlaySArg1;
  }

  public void msgTimeOut(Object paramObject)
  {
    try
    {
      long l1 = Time.current() + 2 * Time.tickLen();
      long l2 = l1;
      int i = this.playLst.size() > 0 ? 1 : 0;
      while (this.playLst.size() > 0) {
        MsgEvent localMsgEvent = (MsgEvent)this.playLst.getFirst();
        if (localMsgEvent.time > l1)
          break;
        l2 = localMsgEvent.time;
        if (localMsgEvent.id == 0) {
          if (this.fingerPlayer != null)
            MsgTimeOut.post(localMsgEvent.time, -2147483647, this.fingerPlayer, localMsgEvent);
        } else {
          HotKeyCmd localHotKeyCmd = HotKeyCmd.getByRecordedId(localMsgEvent.id);
          if (localHotKeyCmd != null)
            MsgTimeOut.post(localMsgEvent.time, this.hotPlayer, localMsgEvent);
        }
        this.playLst.removeFirst();
      }
      if (this.playLst.size() > 0) {
        this.tickMsg.setFlags(24);
        this.tickMsg.post();
      } else if (i != 0) {
        this.tickMsg.setFlags(0);
        this.tickMsg.setTime(l2);
        this.tickMsg.post();
      }
      else if (this.callBack != null) {
        this.callBack.playRecordedEnded();
      }
    } catch (Exception localException) {
      if (this.callBack != null)
        this.callBack.playRecordedEnded();
    }
  }

  public void setCurRecordArgs(NetObj paramNetObj1, NetObj paramNetObj2)
  {
    if (paramNetObj1 != null)
      this.curRecordArg0 = paramNetObj1.idLocal();
    if (paramNetObj2 != null)
      this.curRecordArg1 = paramNetObj2.idLocal(); 
  }

  public void setCurRecordArg0(NetObj paramNetObj) {
    if (paramNetObj != null)
      this.curRecordArg0 = paramNetObj.idLocal(); 
  }

  public void setCurRecordArg1(NetObj paramNetObj) {
    if (paramNetObj != null)
      this.curRecordArg1 = paramNetObj.idLocal();
  }

  public void setCurRecordSArgs(String paramString1, String paramString2) {
    this.curRecordSArg0 = paramString1;
    this.curRecordSArg1 = paramString2;
  }
  public void setCurRecordSArg0(String paramString) {
    this.curRecordSArg0 = paramString;
  }
  public void setCurRecordSArg1(String paramString) {
    this.curRecordSArg1 = paramString;
  }

  public void msgHotKeyCmd(HotKeyCmd paramHotKeyCmd, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramHotKeyCmd.recordId() != 0) {
      if (this.excludeCmdEnv.containsKey(paramHotKeyCmd.hotKeyCmdEnv().name())) return;
      Object localObject;
      if ((paramHotKeyCmd instanceof HotKeyCmdMouseMove)) {
        if ((!this.bSave) || (paramBoolean2)) return;
        localObject = (HotKeyCmdMouseMove)paramHotKeyCmd;
        saveEvent(new MsgEvent(Time.current(), paramHotKeyCmd.recordId(), ((HotKeyCmdMouseMove)localObject)._dx, ((HotKeyCmdMouseMove)localObject)._dy));
      } else if ((paramHotKeyCmd instanceof HotKeyCmdTrackIRAngles)) {
        if ((!this.bSave) || (paramBoolean2)) return;
        localObject = (HotKeyCmdTrackIRAngles)paramHotKeyCmd;
        saveEvent(new MsgEvent(Time.current(), paramHotKeyCmd.recordId(), (int)(((HotKeyCmdTrackIRAngles)localObject)._yaw * 100.0F), (int)(((HotKeyCmdTrackIRAngles)localObject)._pitch * 100.0F), (int)(((HotKeyCmdTrackIRAngles)localObject)._roll * 100.0F)));
      } else if ((paramHotKeyCmd instanceof HotKeyCmdMove)) {
        if ((!this.bSave) || (paramBoolean2)) return;
        localObject = (HotKeyCmdMove)paramHotKeyCmd;
        saveEvent(new MsgEvent(Time.current(), paramHotKeyCmd.recordId(), ((HotKeyCmdMove)localObject)._mov));
      } else if ((paramHotKeyCmd instanceof HotKeyCmdRedirect)) {
        if ((!this.bSave) || (paramBoolean2)) return;
        localObject = (HotKeyCmdRedirect)paramHotKeyCmd;
        saveEvent(new MsgEvent(Time.current(), paramHotKeyCmd.recordId(), ((HotKeyCmdRedirect)localObject).idRedirect(), localObject._r[0], localObject._r[1], localObject._r[2], localObject._r[3], localObject._r[4], localObject._r[5], localObject._r[6], localObject._r[7]));
      }
      else if (paramBoolean2) {
        this.curRecordArg0 = -1;
        this.curRecordArg1 = -1;
        this.curRecordSArg0 = null;
        this.curRecordSArg1 = null;
      } else {
        localObject = null;
        if ((this.curRecordArg0 != -1) || (this.curRecordArg1 != -1)) {
          if (!this.excludePrevCmd.containsKey(paramHotKeyCmd.recordId())) {
            this.prevRecordId = paramHotKeyCmd.recordId();
            this.prevRecordStart = (paramBoolean1 ? 1 : 0);
            this.prevRecordArg0 = this.curRecordArg0;
            this.prevRecordArg1 = this.curRecordArg1;
          }
          if (this.bSave)
            saveEvent(localObject = new MsgEvent(Time.current(), paramHotKeyCmd.recordId(), paramBoolean1 ? 1 : 0, this.curRecordArg0, this.curRecordArg1));
        } else if (this.bSave) {
          saveEvent(localObject = new MsgEvent(Time.current(), paramHotKeyCmd.recordId(), paramBoolean1 ? 1 : 0));
        }
        if (localObject != null) {
          ((MsgEvent)localObject).arg0 = this.curRecordSArg0;
          ((MsgEvent)localObject).arg1 = this.curRecordSArg1;
        }
      }
    }
  }

  public boolean startPlay(KeyRecordCallback paramKeyRecordCallback)
  {
    this.callBack = paramKeyRecordCallback;
    Keyboard.adapter()._clear();
    Mouse.adapter()._clear();
    Joy.adapter()._clear();

    this.hotPlayer = new HotPlayer();
    this.bPlay = true;
    return this.bPlay;
  }

  public boolean startPlay(SectFile paramSectFile, int paramInt1, int paramInt2, KeyRecordCallback paramKeyRecordCallback) {
    this.callBack = paramKeyRecordCallback;
    this.bPlay = false;
    this.playLst.clear();

    int i = paramSectFile.vars(paramInt1);
    long l1 = 0L;
    String str = null;
    for (int j = paramInt2; j < i; j++) {
      str = paramSectFile.line(paramInt1, j);
      SharedTokenizer.set(str);
      if (SharedTokenizer.hasMoreTokens()) {
        MsgEvent localMsgEvent = new MsgEvent();
        long l2 = SharedTokenizer.next(0);
        l1 += l2;
        localMsgEvent.time = l1;
        this.playLst.add(parseEvent(localMsgEvent));
      }
    }
    if (this.playLst.size() > 0) {
      Keyboard.adapter()._clear();
      Mouse.adapter()._clear();
      Joy.adapter()._clear();

      this.tickMsg.remove();
      this.tickMsg.setFlags(0);
      this.tickMsg.setTime(0L);
      this.tickMsg.post();
      this.hotPlayer = new HotPlayer();
    }
    this.bPlay = true;
    return this.bPlay;
  }
  private MsgEvent parseEvent(MsgEvent paramMsgEvent) {
    try {
      SharedTokenizer._nextWord(); paramMsgEvent.id = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p1 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p2 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p3 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p4 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p5 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p6 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p7 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p8 = SharedTokenizer._getInt();
      SharedTokenizer._nextWord(); paramMsgEvent.p9 = SharedTokenizer._getInt();
    } catch (Exception localException) {
      paramMsgEvent.arg0 = SharedTokenizer._getString();
      SharedTokenizer._nextWord();
      paramMsgEvent.arg1 = SharedTokenizer._getString();
    }
    return paramMsgEvent;
  }

  public void stopPlay()
  {
    this.bPlay = false;
    this.callBack = null;
    this.hotPlayer = null;
    this.playLst.clear();
  }
  public boolean isPlaying() {
    return this.bPlay;
  }
  public boolean isRecording() { return this.bSave; }

  public boolean isContainRecorded() {
    return this.recordLst.size() > 0;
  }

  public void clearRecorded() {
    this.recordLst.clear();
  }
  public void clearPrevStates() {
    this.prevRecordId = 0;
  }

  private void saveEvent(MsgEvent paramMsgEvent) {
    if (this.bNet)
      this.net.post(paramMsgEvent);
    else
      this.recordLst.add(paramMsgEvent);
  }

  public void startRecordingNet() {
    this.bSave = true;
    this.bNet = true;
    this.fingerPlayer = null;
    if (this.prevRecordId != 0) {
      saveEvent(new MsgEvent(Time.current(), this.prevRecordId, 1, this.prevRecordArg0, this.prevRecordArg1));
      saveEvent(new MsgEvent(Time.current(), this.prevRecordId, 0, this.prevRecordArg0, this.prevRecordArg1));
    }
  }

  public void startRecording() {
    this.bSave = true;
    this.bNet = false;
    if (this.fingerCalculator != null) {
      this.fingerPlayer = new FingerPlayer();
      MsgTimeOut.post(Time.current(), -2147483647, this.fingerPlayer, null);
    }
  }

  public void stopRecording(boolean paramBoolean) {
    if (paramBoolean)
      saveEvent(new MsgEvent(Time.current(), -1, 0));
    this.bSave = false;
    this.fingerPlayer = null;
  }

  public void setFingerCalculator(KeyRecordFinger paramKeyRecordFinger) {
    this.fingerCalculator = paramKeyRecordFinger;
    this.fingersSize = this.fingerCalculator.countSaveFingers();
  }

  public boolean saveRecorded(SectFile paramSectFile, int paramInt) {
    if (this.recordLst.size() > 0) {
      try {
        long l1 = 0L;
        Iterator localIterator = this.recordLst.iterator();
        while (localIterator.hasNext()) {
          MsgEvent localMsgEvent = (MsgEvent)localIterator.next();
          long l2 = localMsgEvent.time - l1;
          l1 = localMsgEvent.time;
          String str = null;
          if (localMsgEvent.p9 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3 + " " + localMsgEvent.p4 + " " + localMsgEvent.p5 + " " + localMsgEvent.p6 + " " + localMsgEvent.p7 + " " + localMsgEvent.p8 + " " + localMsgEvent.p9;
          else if (localMsgEvent.p8 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3 + " " + localMsgEvent.p4 + " " + localMsgEvent.p5 + " " + localMsgEvent.p6 + " " + localMsgEvent.p7 + " " + localMsgEvent.p8;
          else if (localMsgEvent.p7 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3 + " " + localMsgEvent.p4 + " " + localMsgEvent.p5 + " " + localMsgEvent.p6 + " " + localMsgEvent.p7;
          else if (localMsgEvent.p6 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3 + " " + localMsgEvent.p4 + " " + localMsgEvent.p5 + " " + localMsgEvent.p6;
          else if (localMsgEvent.p5 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3 + " " + localMsgEvent.p4 + " " + localMsgEvent.p5;
          else if (localMsgEvent.p4 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3 + " " + localMsgEvent.p4;
          else if (localMsgEvent.p3 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2 + " " + localMsgEvent.p3;
          else if (localMsgEvent.p2 != -1)
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1 + " " + localMsgEvent.p2;
          else
            str = "" + l2 + " " + localMsgEvent.id + " " + localMsgEvent.p1;
          if ((localMsgEvent.arg0 == null) && (localMsgEvent.arg1 == null))
            paramSectFile.lineAdd(paramInt, str);
          else if (localMsgEvent.arg1 == null)
            paramSectFile.lineAdd(paramInt, str + " " + localMsgEvent.arg0);
          else
            paramSectFile.lineAdd(paramInt, str + " " + localMsgEvent.arg0 + " " + localMsgEvent.arg1);
        }
      }
      catch (Exception localException) {
        System.out.println("Track file saved failed: " + localException.getMessage());

        return false;
      }
    }
    return true;
  }

  public KeyRecord()
  {
    this.tickMsg.setListener(this);
    this.tickMsg.setNotCleanAfterSend();
    this.tickMsg.setFlags(24);
    RTSConf.cur.hotKeyCmdEnvs.msgAddListener(this, null);
    this.net = new NetKeyRecord(9);
  }

  class NetKeyRecord extends NetObj
  {
    NetChannelOutStream out;

    public void post(MsgEvent paramMsgEvent)
    {
      if (!KeyRecord.this.isRecording()) return;
      if ((this.out == null) || (this.out.isDestroying())) return; try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeInt(paramMsgEvent.id);
        if (paramMsgEvent.p9 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p4);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p5);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p6);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p7);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p8);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p9);
        } else if (paramMsgEvent.p8 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p4);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p5);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p6);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p7);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p8);
        } else if (paramMsgEvent.p7 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p4);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p5);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p6);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p7);
        } else if (paramMsgEvent.p6 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p4);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p5);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p6);
        } else if (paramMsgEvent.p5 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p4);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p5);
        } else if (paramMsgEvent.p4 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p4);
        } else if (paramMsgEvent.p3 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p3);
        } else if (paramMsgEvent.p2 != -1) {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
          localNetMsgGuaranted.writeInt(paramMsgEvent.p2);
        } else {
          localNetMsgGuaranted.writeInt(paramMsgEvent.p1);
        }
        postTo(this.out, localNetMsgGuaranted);
        this.out.flush(); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (!KeyRecord.this.isPlaying()) return true;
      MsgEvent localMsgEvent = new MsgEvent();

      localMsgEvent.time = Time.current();
      localMsgEvent.id = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
        localMsgEvent.p1 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
          localMsgEvent.p2 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
            localMsgEvent.p3 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
              localMsgEvent.p4 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
                localMsgEvent.p5 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
                  localMsgEvent.p6 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
                    localMsgEvent.p7 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) {
                      localMsgEvent.p8 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() != 0) { localMsgEvent.p9 = paramNetMsgInput.readInt(); if (paramNetMsgInput.available() == 0); }
                    }
                  }
                }
              }
            }
          }
        }
      }
      KeyRecord.this.hotPlayer.msgTimeOut(localMsgEvent);
      return true;
    }

    public void msgNetNewChannel(NetChannel paramNetChannel) {
      if ((paramNetChannel instanceof NetChannelOutStream))
        this.out = ((NetChannelOutStream)paramNetChannel); 
    }

    public void msgNetDelChannel(NetChannel paramNetChannel) {
      if (paramNetChannel == this.out)
        this.out = null;
      else if (((paramNetChannel instanceof NetChannelInStream)) && 
        (KeyRecord.this.isPlaying()) && 
        (KeyRecord.this.callBack != null))
        KeyRecord.this.callBack.playRecordedEnded(); 
    }

    public NetKeyRecord(int arg2) {
      super(i);
    }
  }

  class HotPlayer
    implements MsgTimeOutListener
  {
    public void msgTimeOut(Object paramObject)
    {
      if (KeyRecord.this.hotPlayer != this) return;
      MsgEvent localMsgEvent = (MsgEvent)paramObject;
      HotKeyCmd localHotKeyCmd = HotKeyCmd.getByRecordedId(localMsgEvent.id);
      if (localHotKeyCmd == null) return;
      Object localObject;
      if ((localHotKeyCmd instanceof HotKeyCmdMouseMove)) {
        if (!localHotKeyCmd.hotKeyCmdEnv().hotKeyEnv().isEnabled()) {
          localObject = (HotKeyCmdMouseMove)localHotKeyCmd;
          ((HotKeyCmdMouseMove)localObject)._exec(localMsgEvent.p1, localMsgEvent.p2, 0);
        }
      } else if ((localHotKeyCmd instanceof HotKeyCmdTrackIRAngles)) {
        if (!localHotKeyCmd.hotKeyCmdEnv().hotKeyEnv().isEnabled()) {
          localObject = (HotKeyCmdTrackIRAngles)localHotKeyCmd;
          ((HotKeyCmdTrackIRAngles)localObject)._exec(localMsgEvent.p1 / 100.0F, localMsgEvent.p2 / 100.0F, localMsgEvent.p3 / 100.0F);
        }
      } else if ((localHotKeyCmd instanceof HotKeyCmdMove)) {
        if (!localHotKeyCmd.hotKeyCmdEnv().hotKeyEnv().isEnabled()) {
          localObject = (HotKeyCmdMove)localHotKeyCmd;
          ((HotKeyCmdMove)localObject)._exec(localMsgEvent.p1);
        }
      } else if ((localHotKeyCmd instanceof HotKeyCmdRedirect)) {
        if (!localHotKeyCmd.hotKeyCmdEnv().hotKeyEnv().isEnabled()) {
          localObject = (HotKeyCmdRedirect)localHotKeyCmd;
          ((HotKeyCmdRedirect)localObject)._exec(localMsgEvent.p2, localMsgEvent.p3, localMsgEvent.p4, localMsgEvent.p5, localMsgEvent.p6, localMsgEvent.p7, localMsgEvent.p8, localMsgEvent.p9);
        }
      } else if ((!localHotKeyCmd.hotKeyCmdEnv().hotKeyEnv().isEnabled()) || (KeyRecord.this.bFirstHotCmd)) {
        KeyRecord.access$902(KeyRecord.this, localMsgEvent.p2);
        KeyRecord.access$1002(KeyRecord.this, localMsgEvent.p3);
        KeyRecord.access$1102(KeyRecord.this, localMsgEvent.arg0);
        KeyRecord.access$1202(KeyRecord.this, localMsgEvent.arg1);
        if ((KeyRecord.this.bFirstHotCmd) && (KeyRecord.this.callBack != null)) {
          KeyRecord.this.callBack.doFirstHotCmd(true);
          localHotKeyCmd._exec(localMsgEvent.p1 == 1);
          KeyRecord.this.callBack.doFirstHotCmd(false);
        } else {
          localHotKeyCmd._exec(localMsgEvent.p1 == 1);
        }
        if ((KeyRecord.this.bFirstHotCmd) && (localMsgEvent.p1 == 1))
          localHotKeyCmd._exec(false);
        KeyRecord.access$802(KeyRecord.this, false);
      }
    }

    public HotPlayer() {
      KeyRecord.access$802(KeyRecord.this, true);
    }
  }

  class FingerPlayer
    implements MsgTimeOutListener
  {
    FingerPlayer()
    {
    }

    public void msgTimeOut(Object paramObject)
    {
      if (KeyRecord.this.fingerCalculator == null) return;
      if (KeyRecord.this.fingerPlayer != this) return;
      Object localObject;
      if (paramObject == null)
      {
        if (KeyRecord.this.bSave) {
          localObject = KeyRecord.this.fingerCalculator.calculateFingers();
          KeyRecord.this.recordLst.add(new MsgEvent(Time.current(), 0, localObject, KeyRecord.this.fingersSize));
          MsgTimeOut.post(Time.current() + KeyRecord.this.fingerCalculator.checkPeriod(), -2147483647, this, null);
        }

      }
      else if (KeyRecord.this.bPlay) {
        localObject = (MsgEvent)paramObject;

        KeyRecord.this.fingers[0] = ((MsgEvent)localObject).p1; if (KeyRecord.this.fingersSize > 1) {
          KeyRecord.this.fingers[1] = ((MsgEvent)localObject).p2; if (KeyRecord.this.fingersSize > 2) {
            KeyRecord.this.fingers[2] = ((MsgEvent)localObject).p3; if (KeyRecord.this.fingersSize > 3) {
              KeyRecord.this.fingers[3] = ((MsgEvent)localObject).p4; if (KeyRecord.this.fingersSize > 4) {
                KeyRecord.this.fingers[4] = ((MsgEvent)localObject).p5; if (KeyRecord.this.fingersSize > 5) {
                  KeyRecord.this.fingers[5] = ((MsgEvent)localObject).p6; if (KeyRecord.this.fingersSize > 6) {
                    KeyRecord.this.fingers[6] = ((MsgEvent)localObject).p7; if (KeyRecord.this.fingersSize > 7) {
                      KeyRecord.this.fingers[7] = ((MsgEvent)localObject).p8; if (KeyRecord.this.fingersSize > 8)
                        KeyRecord.this.fingers[8] = ((MsgEvent)localObject).p9; 
                    }
                  }
                }
              }
            }
          }
        }
        KeyRecord.this.fingerCalculator.checkFingers(KeyRecord.this.fingers);
      }
    }
  }
}