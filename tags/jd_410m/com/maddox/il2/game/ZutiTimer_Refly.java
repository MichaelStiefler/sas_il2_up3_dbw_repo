package com.maddox.il2.game;

import java.util.Timer;
import java.util.TimerTask;

public class ZutiTimer_Refly extends TimerTask
{
  private Timer timer = null;
  private int counter = 0;
  private float reflyDelay = 0.0F;

  public ZutiTimer_Refly(float paramFloat)
  {
    this.reflyDelay = paramFloat;

    this.timer = new Timer();
    this.timer.schedule(this, 0L, 1000L);
  }

  public void run()
  {
    try
    {
      if (this.counter > this.reflyDelay - 1.0F)
      {
        ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = true;
        stop();
      }
    }
    catch (Exception localException)
    {
    }

    this.counter += 1;
  }

  public void stop()
  {
    cancel();
  }

  public String getRemaimingTime()
  {
    int i = (int)this.reflyDelay - this.counter;
    int j = i / 60;
    int k = i % 60;

    String str1 = "";
    String str2 = "";

    if (j < 10)
      str1 = "0" + j;
    else {
      str1 = "" + j;
    }
    if (k < 10)
      str2 = "0" + k;
    else {
      str2 = "" + k;
    }
    return str1 + ":" + str2;
  }
}