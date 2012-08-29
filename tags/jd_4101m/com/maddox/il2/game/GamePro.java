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
      j = rndInt(300000);
      int k = 0;
      switch (i) {
      case 0:
        this.parray = Starforce.SFLB(i + 1, j, this.parray, this.array);
        this.carray = new int[64];
        for (m = 0; m < this.carray.length; m++)
          this.array[m] = rndInt(300000);
        return;
      case 1:
        k = 19; break;
      case 2:
        k = 22; break;
      case 3:
        k = 38; break;
      case 4:
        k = 21; break;
      case 5:
        k = 77; break;
      case 6:
        k = 3; break;
      case 7:
        k = 7; break;
      case 8:
        k = 5; break;
      case 9:
        k = 24; break;
      case 10:
        k = 12; break;
      case 11:
        k = 17; break;
      case 12:
        k = 41; break;
      case 13:
        k = 9; break;
      case 14:
        k = 74; break;
      case 15:
        k = 44; break;
      case 16:
        k = 33; break;
      case 17:
        k = 8; break;
      case 18:
        k = 32; break;
      case 19:
        k = 11; break;
      case 20:
        k = 31; break;
      case 21:
        k = 22; break;
      case 22:
        k = 25; break;
      case 23:
        k = 1; break;
      case 24:
        k = 29; break;
      }

      k = (k + j) % 64;
      int m = Starforce.SFLB(i + 1, j, this.parray, this.array);
      int n = j + k;
      for (int i1 = 0; i1 < k; i1++)
        n += this.array[i1];
      this.carray[k] = n;
      n = j + k;
      for (i1 = 0; i1 < this.carray.length; i1++)
        n += this.carray[i1];
      if (n != m)
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