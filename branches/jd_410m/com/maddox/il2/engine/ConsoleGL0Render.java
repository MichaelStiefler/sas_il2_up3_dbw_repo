package com.maddox.il2.engine;

import com.maddox.rts.Console;
import com.maddox.rts.MainWin32;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;
import com.maddox.rts.Time;
import java.util.List;
import java.util.Locale;

class ConsoleGL0Render extends Render
{
  public static final int COLOR = -1;
  private char[] buf = new char[''];
  private int[] ofs = new int[''];
  public String sstep = null;
  private int istep = 0;

  public void exlusiveDraw() {
    if ((RTSConf.cur instanceof RTSConfWin))
      ((MainWin32)RTSConf.cur.mainWindow).loopMsgs();
    GObj.DeleteCppObjects();
    renders().paint(this);
  }
  public void exlusiveDrawStep(String paramString, int paramInt) {
    this.sstep = paramString;
    this.istep = paramInt;
    renders().paint(this);
  }

  public void render()
  {
    int i;
    if (ConsoleGL0.backgroundMat != null) {
      drawTile(0.0F, 0.0F, RendersMain.getViewPortWidth(), RendersMain.getViewPortHeight(), 0.0F, ConsoleGL0.backgroundMat, -1, 0.0F, 1.0F, 1.0F, -1.0F);

      if (this.sstep != null) {
        float f1 = TTFont.font[2].width(this.sstep);

        i = -16776961;
        if ("ru".equalsIgnoreCase(RTSConf.cur.locale.getLanguage())) {
          TTFont.font[2].output(i, RendersMain.getViewPortWidth() * 0.083F, RendersMain.getViewPortHeight() * 0.12F, 0.0F, this.sstep);

          TTFont.font[2].output(i, RendersMain.getViewPortWidth() * 0.083F, RendersMain.getViewPortHeight() * 0.12F + TTFont.font[2].height() - TTFont.font[2].descender(), 0.0F, "V 4.10m");
        }
        else {
          TTFont.font[2].output(i, RendersMain.getViewPortWidth() * 0.02F, RendersMain.getViewPortHeight() * 0.17F, 0.0F, this.sstep);

          TTFont.font[2].output(i, RendersMain.getViewPortWidth() * 0.02F, RendersMain.getViewPortHeight() * 0.17F + TTFont.font[2].height() - TTFont.font[2].descender(), 0.0F, "V 4.10m");
        }
      }

      return;
    }
    if ((ConsoleGL0.bActive) || (ConsoleGL0.consoleListener != null)) {
      List localList = RTSConf.cur.console.historyOut();
      i = RTSConf.cur.console.startHistoryOut();
      if (!RTSConf.cur.console.isShowHistoryOut()) {
        localList = RTSConf.cur.console.historyCmd();
        i = RTSConf.cur.console.startHistoryCmd();
      }

      int j = getViewPortHeight() / ConsoleGL0.font.height() - 1;
      int k;
      int i1;
      if (ConsoleGL0.bActive) {
        k = RTSConf.cur.console.editBuf.length();
        String str1 = RTSConf.cur.console.getPrompt();
        int n = str1.length();
        i1 = 0;
        if (n != 0)
          str1.getChars(0, n, this.buf, 0);
        float f3;
        if (k + n > 0) {
          if (k + n > this.buf.length)
            this.buf = new char[k + n + 16];
          if (k != 0) {
            RTSConf.cur.console.editBuf.getChars(0, k, this.buf, n);
            f3 = getViewPortWidth() - ConsoleGL0.typeOffset;
            while (true) {
              float f4 = ConsoleGL0.font.width(this.buf, 0, RTSConf.cur.console.editPos - i1 + n);
              if (f4 < f3)
                break;
              i1++;
              RTSConf.cur.console.editBuf.getChars(i1, k, this.buf, n);
            }
          }
          ConsoleGL0.font.output(-1, ConsoleGL0.typeOffset, -ConsoleGL0.font.descender(), 0.0F, this.buf, 0, k - i1 + n);
        }
        if (Time.endReal() / 500L % 3L != 0L) {
          f3 = 0.0F;
          if (RTSConf.cur.console.editPos + n > 0)
            f3 = ConsoleGL0.font.width(this.buf, 0, RTSConf.cur.console.editPos - i1 + n);
          this.buf[0] = '|';
          ConsoleGL0.font.output(-1, ConsoleGL0.typeOffset + f3 - 1.0F, -ConsoleGL0.font.descender(), 0.0F, this.buf, 0, 1);
        }
      }
      String str2;
      if (RTSConf.cur.console.bWrap) {
        k = i;
        int m = 1;
        while ((j > 0) && (k < localList.size())) {
          str2 = (String)(String)localList.get(k);
          i1 = str2.length() - 1;
          while ((i1 >= 0) && (str2.charAt(i1) < ' ')) i1--;
          if (i1 > 0) {
            i1++;
            int i2 = 0;
            int i3 = 0;
            int i4 = i1;
            this.ofs[i2] = i3;
            while ((i4 > 0) && (ConsoleGL0.font.width(str2, i3, i4) > 0.0F)) {
              int i5 = i4;
              while (ConsoleGL0.font.width(str2, i3, i4) + ConsoleGL0.typeOffset > getViewPortWidth()) {
                while (true) { i4--; if (i4 <= 0) break; if (str2.charAt(i3 + i4) != ' ') continue; }
                if (i4 != 0) continue;
              }
              if (i4 == 0) {
                i4 = i5;
                while (ConsoleGL0.font.width(str2, i3, i4) + ConsoleGL0.typeOffset > getViewPortWidth()) {
                  i4--;
                  if (i4 != 0) continue;
                }
              }
              if (i2 + 1 >= this.ofs.length)
                break;
              i2++;
              if (i4 == 0) {
                this.ofs[i2] = i1;
                break;
              }
              i3 += i4;
              i4 = i1 - i3;
              this.ofs[i2] = i3;
            }
            while ((i2 > 0) && (j > 0)) {
              ConsoleGL0.font.output(-1, ConsoleGL0.typeOffset, ConsoleGL0.font.height() * m - ConsoleGL0.font.descender(), 0.0F, str2, this.ofs[(i2 - 1)], this.ofs[i2] - this.ofs[(i2 - 1)]);

              m++;
              i2--;
              j--;
            }
          }
          k++;
        }
      }
      else {
        if (j > localList.size() - i)
          j = localList.size() - i;
        for (k = 0; k < j; k++) {
          float f2 = ConsoleGL0.font.height() * (k + 1) - ConsoleGL0.font.descender();
          str2 = (String)(String)localList.get(k + i);
          i1 = str2.length() - 1;
          while ((i1 >= 0) && (str2.charAt(i1) < ' ')) i1--;
          if (i1 > 0)
            ConsoleGL0.font.output(-1, ConsoleGL0.typeOffset, f2, 0.0F, str2, 0, i1 + 1);
        }
      }
    }
  }

  public ConsoleGL0Render(float paramFloat) {
    super(paramFloat);
    useClearDepth(false);
    useClearColor(false);
  }
}