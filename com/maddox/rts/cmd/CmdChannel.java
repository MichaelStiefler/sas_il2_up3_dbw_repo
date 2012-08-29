package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdChannel extends Cmd
{
  public static final String DESTROY = "DESTROY";
  public static final String MAXSPEED = "SPEED";
  public static final String TIMEOUT = "TIMEOUT";
  public static final String STAT = "STAT";
  public static final String SOCKET = "SOCKET";
  private HashMap stat = new HashMap();

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    boolean bool = Cmd.exist(paramMap, "SOCKET");

    int i = -1;
    Object localObject;
    int j;
    if (Cmd.nargs(paramMap, "_$$") == 1) {
      i = Cmd.arg(paramMap, "_$$", 0, -1);
      if (i == -1) {
        ERR_HARD("Unknown number of channel");
        return null;
      }
      localObject = NetEnv.getChannel(i);
      if (localObject == null) {
        ERR_HARD("Channel: " + i + " not found");
        return null;
      }
      if (Cmd.exist(paramMap, "DESTROY")) {
        ((NetChannel)localObject).destroy("Connection lost.");
        return localObject;
      }if ((Cmd.exist(paramMap, "SPEED")) || (Cmd.exist(paramMap, "TIMEOUT")) || (Cmd.exist(paramMap, "STAT"))) {
        if (Cmd.nargs(paramMap, "SPEED") == 1) {
          double d = Cmd.arg(paramMap, "SPEED", 0, 1000) / 1000.0D;
          ((NetChannel)localObject).setMaxSpeed(d);
        }
        if (Cmd.nargs(paramMap, "TIMEOUT") == 1) {
          j = Cmd.arg(paramMap, "TIMEOUT", 0, 131);
          ((NetChannel)localObject).setMaxTimeout(j * 1000);
        }
        if (Cmd.nargs(paramMap, "STAT") == 1) {
          j = Cmd.arg(paramMap, "STAT", 0, -1) * 1000;
          Stat localStat;
          if (j <= 0) {
            localStat = (Stat)this.stat.get(localObject);
            if (localStat != null) localStat.destroy(); 
          }
          else {
            localStat = (Stat)this.stat.get(localObject);
            if (localStat == null) new Stat((NetChannel)localObject, j); else
              localStat.timeStep(j);
          }
        }
        return localObject;
      }
    }
    if (i != -1) {
      localObject = NetEnv.getChannel(i);
      if (localObject == null) {
        ERR_HARD("Channel: " + i + " not found");
        return null;
      }
      info((NetChannel)localObject, bool);
    } else {
      localObject = NetEnv.channels();
      j = ((List)localObject).size();
      for (int k = 0; k < j; k++) {
        NetChannel localNetChannel = (NetChannel)((List)localObject).get(k);
        info(localNetChannel, bool);
      }
    }
    return CmdEnv.RETURN_OK;
  }

  private void info(NetChannel paramNetChannel, boolean paramBoolean) {
    String str1 = "READY";
    if (paramNetChannel.isDestroyed()) str1 = "DESTROYED";
    else if (paramNetChannel.isDestroying()) str1 = "DESTROYING";
    else if (paramNetChannel.isIniting()) str1 = "INITING";
    String str2 = (paramNetChannel.isPublic() ? "P" : "p") + (paramNetChannel.isGlobal() ? "G" : "g") + (paramNetChannel.isRealTime() ? "T" : "t");
    INFO_HARD(" " + paramNetChannel.id() + ": [" + str1 + "." + str2 + "] ping: " + paramNetChannel.ping() + "ms timeout: " + paramNetChannel.getCurTimeout() / 1000 + "/" + paramNetChannel.getMaxTimeout() / 1000 + "s speed: " + (int)(paramNetChannel.getMaxSpeed() * 1000.0D) + "b/s");
    if (paramBoolean)
      INFO_HARD("    " + paramNetChannel.socket().getLocalAddress() + ":" + paramNetChannel.socket().getLocalPort() + (paramNetChannel.isInitRemote() ? " <- " : " -> ") + paramNetChannel.remoteAddress() + ":" + paramNetChannel.remotePort());
  }

  public CmdChannel()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("DESTROY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SPEED", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TIMEOUT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("STAT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("SOCKET", null);
    this._properties.put("NAME", "channel");
    this._levelAccess = 1;
  }

  class Stat
    implements MsgTimeOutListener
  {
    private int timeStep;
    private NetChannel ch;
    private long prevTime;
    private int statNumSendGMsgs;
    private int statSizeSendGMsgs;
    private int statNumSendFMsgs;
    private int statSizeSendFMsgs;
    private int statHSizeSendFMsgs;
    private int statNumFilteredMsgs;
    private int statSizeFilteredMsgs;
    private int statNumReseivedMsgs;
    private int statSizeReseivedMsgs;
    private int statHSizeReseivedMsgs;

    private void step()
    {
      long l = Time.currentReal();
      if (this.prevTime > 0L) {
        double d1 = (l - this.prevTime) / 1000.0D;
        if (d1 > 0.0D) {
          double d2 = (this.ch.statNumSendGMsgs - this.statNumSendGMsgs) / d1;
          double d3 = (this.ch.statSizeSendGMsgs - this.statSizeSendGMsgs) / d1;
          double d4 = (this.ch.statNumSendFMsgs - this.statNumSendFMsgs) / d1;
          double d5 = (this.ch.statSizeSendFMsgs - this.statSizeSendFMsgs) / d1;
          double d6 = (this.ch.statNumSendGMsgs + this.ch.statNumSendGMsgs - this.statNumSendGMsgs - this.statNumSendFMsgs) / d1;
          double d7 = (this.ch.statSizeSendGMsgs + 3 * this.ch.statNumSendGMsgs + this.ch.statSizeSendFMsgs + this.ch.statHSizeSendFMsgs - this.statSizeSendGMsgs - 3 * this.statNumSendGMsgs - this.statSizeSendFMsgs - this.statHSizeSendFMsgs) / d1;

          double d8 = (this.ch.statNumReseivedMsgs - this.statNumReseivedMsgs) / d1;
          double d9 = (this.ch.statSizeReseivedMsgs + this.ch.statHSizeReseivedMsgs - this.statSizeReseivedMsgs - this.statHSizeReseivedMsgs) / d1;

          System.out.println("ch " + this.ch.id() + ": ping: " + this.ch.ping() + "ms  > " + (int)d7 + "b/s  < " + (int)d9 + "b/s " + this.ch.gSendQueueLenght() + "/" + this.ch.gSendQueueSize());
        }
      }
      this.prevTime = l;
      this.statNumSendGMsgs = this.ch.statNumSendGMsgs;
      this.statSizeSendGMsgs = this.ch.statSizeSendGMsgs;
      this.statNumSendFMsgs = this.ch.statNumSendFMsgs;
      this.statSizeSendFMsgs = this.ch.statSizeSendFMsgs;
      this.statHSizeSendFMsgs = this.ch.statHSizeSendFMsgs;
      this.statNumFilteredMsgs = this.ch.statNumFilteredMsgs;
      this.statSizeFilteredMsgs = this.ch.statSizeFilteredMsgs;
      this.statNumReseivedMsgs = this.ch.statNumReseivedMsgs;
      this.statSizeReseivedMsgs = this.ch.statSizeReseivedMsgs;
      this.statHSizeReseivedMsgs = this.ch.statHSizeReseivedMsgs;
    }

    public void msgTimeOut(Object paramObject) {
      if (this.ch.isDestroying()) {
        destroy();
        return;
      }
      if (!CmdChannel.this.stat.containsKey(this.ch)) return;
      step();
      MsgTimeOut.post(64, Time.currentReal() + this.timeStep, this, null);
    }
    public void destroy() {
      CmdChannel.this.stat.remove(this.ch);
    }
    public void timeStep(int paramInt) { this.timeStep = paramInt;
    }

    public Stat(NetChannel paramInt, int arg3)
    {
      int i;
      this.timeStep = i;
      this.ch = paramInt;
      CmdChannel.this.stat.put(paramInt, this);
      step();
      MsgTimeOut.post(64, Time.currentReal() + i, this, null);
    }
  }
}