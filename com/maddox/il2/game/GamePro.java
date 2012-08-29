package com.maddox.il2.game;

import com.maddox.rts.JoyDX;
import com.maddox.rts.KeyboardDX;
import com.maddox.rts.MouseDX;
import com.maddox.rts.MsgAction;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;

public final class GamePro
{
  private int[] array;
  private int[] carray;
  private int parray = 0;

  private int rndInt(int paramInt) {
    return (int)(Math.random() * paramInt);
  }

  private void step() {
    new MsgAction(64, 5.0D + Math.random() * 10.0D) {
      public void doAction() {
        GamePro.this.step();
      }
    };
    if ((Mission.cur() == null) || (Mission.cur().name() == null)) {
      int i = rndInt(24);
      this.array = new int[64];
      for (int j = 0; j < this.array.length; j++)
        this.array[j] = rndInt(300000);
      if (this.parray == 0)
        i = 0;
      int k = rndInt(300000);
      int m = 0;
      switch (i) {
      case 0:
        this.parray = Starforce.SFLB(i + 1, k, this.parray, this.array);
        this.carray = new int[64];
        for (int n = 0; n < this.carray.length; n++)
          this.array[n] = rndInt(300000);
        return;
      case 1:
        m = 19; break;
      case 2:
        m = 22; break;
      case 3:
        m = 38; break;
      case 4:
        m = 21; break;
      case 5:
        m = 77; break;
      case 6:
        m = 3; break;
      case 7:
        m = 7; break;
      case 8:
        m = 5; break;
      case 9:
        m = 24; break;
      case 10:
        m = 12; break;
      case 11:
        m = 17; break;
      case 12:
        m = 41; break;
      case 13:
        m = 9; break;
      case 14:
        m = 74; break;
      case 15:
        m = 44; break;
      case 16:
        m = 33; break;
      case 17:
        m = 8; break;
      case 18:
        m = 32; break;
      case 19:
        m = 11; break;
      case 20:
        m = 31; break;
      case 21:
        m = 22; break;
      case 22:
        m = 25; break;
      case 23:
        m = 1; break;
      case 24:
        m = 29; break;
      }

      m = (m + k) % 64;
      int i1 = Starforce.SFLB(i + 1, k, this.parray, this.array);
      int i2 = k + m;
      for (int i3 = 0; i3 < m; i3++)
        i2 += this.array[i3];
      this.carray[m] = i2;
      i2 = k + m;
      for (int i4 = 0; i4 < this.carray.length; i4++)
        i2 += this.carray[i4];
      if (i2 != i1)
      {
        new MsgAction(64, 8.0D + Math.random() * 10.0D) {
          public void doAction() {
            double d = Math.random();
            if (d < 0.025D)
              ((RTSConfWin)RTSConf.cur).joyDX.destroy();
            else if (d < 0.05D)
              ((RTSConfWin)RTSConf.cur).mouseDX.destroy();
            else if (d < 0.075D)
              ((RTSConfWin)RTSConf.cur).keyboardDX.destroy();
            else if (d < 0.1D)
              Main.doGameExit();
            else
              com.maddox.il2.fm.FlightModelMain.bCY_CRIT04 = true;
          }
        };
      }
    }
  }

  protected GamePro() {
    new MsgAction(64, 8.0D + Math.random() * 10.0D) {
      public void doAction() {
        GamePro.this.step();
      }
    };
  }
}