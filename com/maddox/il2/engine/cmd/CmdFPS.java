package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TextScr;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdFPS extends Cmd
  implements MsgTimeOutListener
{
  private boolean bGo = false;
  private boolean bShow = false;
  private long timeStart;
  private int frameStart;
  private double fpsMin;
  private double fpsMax;
  private double fpsCur;
  private long timePrev;
  private int framePrev;
  private long logPeriod = 5000L;
  private long logPrintTime = -1L;
  public static final String LOG = "LOG";
  public static final String START = "START";
  public static final String STOP = "STOP";
  public static final String SHOW = "SHOW";
  public static final String HIDE = "HIDE";
  public static final String PERF = "PERF";
  private MsgTimeOut msg;

  public void msgTimeOut(Object paramObject)
  {
    this.msg.post();
    if (!this.bGo) return;
    long l = Time.real();
    int i = RendersMain.frame();
    if (l >= this.timePrev + 250L) {
      this.fpsCur = (1000.0D * (i - this.framePrev) / (l - this.timePrev));
      if (this.fpsMin > this.fpsCur)
        this.fpsMin = this.fpsCur;
      if (this.fpsMax < this.fpsCur)
        this.fpsMax = this.fpsCur;
      this.framePrev = i;
      this.timePrev = l;
    }
    if (this.framePrev == this.frameStart) {
      return;
    }
    if (this.bShow) {
      Render localRender = (Render)Actor.getByName("renderTextScr");
      if (localRender == null) return;
      TTFont localTTFont = TextScr.font();
      int j = localRender.getViewPortWidth();
      int k = localRender.getViewPortHeight();
      String str = "fps:" + fpsInfo();
      int m = (int)localTTFont.width(str);
      int n = k - localTTFont.height() - 5;
      int i1 = (j - m) / 2;
      TextScr.output(i1, n, str);
    }
    if ((this.logPeriod > 0L) && 
      (l >= this.logPrintTime + this.logPeriod)) {
      System.out.println("fps:" + fpsInfo());
      this.logPrintTime = l;
    }
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 0;
    checkMsg();

    if (paramMap.containsKey("SHOW")) {
      this.bShow = true;
      i = 1;
    } else if (paramMap.containsKey("HIDE")) {
      this.bShow = false;
      i = 1;
    }
    if (paramMap.containsKey("LOG")) {
      int j = arg(paramMap, "LOG", 0, 5);
      if (j < 0) j = 0;
      this.logPeriod = (j * 1000L);
      i = 1;
    }
    if (paramMap.containsKey("PERF")) {
      AudioDevice.setPerformInfo(true);
      i = 1;
    }
    if (paramMap.containsKey("START")) {
      if ((this.bGo) && (this.timeStart != this.timePrev) && (this.logPeriod == 0L))
        INFO_HARD("fps:" + fpsInfo());
      this.timeStart = Time.real();
      this.frameStart = RendersMain.frame();
      this.fpsMin = 1000000.0D;
      this.fpsMax = 0.0D;
      this.fpsCur = 0.0D;
      this.timePrev = this.timeStart;
      this.framePrev = this.frameStart;
      this.logPrintTime = this.timeStart;
      this.bGo = true;
      i = 1;
    } else if (paramMap.containsKey("STOP")) {
      if ((this.bGo) && (this.timeStart != this.timePrev) && (this.logPeriod == 0L))
        INFO_HARD("fps:" + fpsInfo());
      this.bGo = false;
      i = 1;
    }

    if (i == 0) {
      INFO_HARD("  LOG  " + this.logPeriod / 1000L);
      if (this.bShow) INFO_HARD("  SHOW"); else
        INFO_HARD("  HIDE");
      if (this.bGo) {
        if (this.timeStart != this.timePrev)
          INFO_HARD("  " + fpsInfo());
      }
      else INFO_HARD("  STOPPED");

    }

    return CmdEnv.RETURN_OK;
  }

  private String fpsInfo() {
    return "" + (int)Math.floor(this.fpsCur) + " avg:" + (int)Math.floor(1000.0D * (this.framePrev - this.frameStart) / (this.timePrev - this.timeStart)) + " max:" + (int)Math.floor(this.fpsMax) + " min:" + (int)Math.floor(this.fpsMin) + " #fr:" + (this.framePrev - this.frameStart);
  }

  private void checkMsg()
  {
    if (this.msg == null) {
      this.msg = new MsgTimeOut();
      this.msg.setListener(this);
      this.msg.setNotCleanAfterSend();
      this.msg.setFlags(72);
    }
    if (!this.msg.busy())
      this.msg.post();
  }

  public CmdFPS()
  {
    this.param.put("LOG", null);
    this.param.put("START", null);
    this.param.put("STOP", null);
    this.param.put("SHOW", null);
    this.param.put("HIDE", null);
    this.param.put("PERF", null);
    this._properties.put("NAME", "fps");
    this._levelAccess = 1;
  }
}