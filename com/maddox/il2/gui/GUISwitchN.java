package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.rts.CfgInt;

public class GUISwitchN extends GWindowDialogControl
{
  public float texDx;
  public float texDy;
  public int states;
  public int state;
  public int[] pos;
  public boolean[] enable;
  public float angle0;
  public float angleStep;
  public CfgInt cfg;
  public boolean bUpdate;
  public boolean bChanged;

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;

    paramFloat1 -= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 2.0F;
    paramFloat2 -= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F;
    float f1 = (float)(Math.atan2(paramFloat2, paramFloat1) * 180.0D / 3.141592653589793D);
    if (f1 < 0.0F) f1 += 360.0F;
    float f2 = this.angleStep / 2.0F;
    for (int i = 0; i < this.pos.length; i++)
      if ((this.enable == null) || (this.enable[i] != 0)) {
        int j = this.pos[i];
        float f3 = this.angle0 + j * this.angleStep;
        float f4 = f3 - f2;
        float f5 = f3 + f2;
        if (f5 > 360.0F) {
          f4 -= 360.0F;
          f5 -= 360.0F;
        }
        if ((f1 >= f4) && (f1 <= f5)) {
          setState(i, true);
          lAF().soundPlay("clickSwitch");
          return;
        }
      }
  }

  public void setState(int paramInt, boolean paramBoolean)
  {
    if (this.state == paramInt) return;
    this.state = paramInt;
    if (paramBoolean)
      notify(2, this.state); 
  }

  public int getState() {
    return this.state;
  }
  public void render() {
    setCanvasColorWHITE();
  }

  public GSize getMinSize(GSize paramGSize) {
    paramGSize.dx = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    paramGSize.dy = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    return paramGSize;
  }

  public void setPosC(float paramFloat1, float paramFloat2) {
    super.setPos(paramFloat1 - this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 2.0F, paramFloat2 - this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F);
  }

  public void resolutionChanged() {
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = x1024(this.texDx);
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = y1024(this.texDy);
    if (this.cfg != null)
      refresh();
  }

  public void created() {
    resolutionChanged();
  }

  public GUISwitchN(GWindow paramGWindow, float paramFloat1, float paramFloat2, int[] paramArrayOfInt, boolean[] paramArrayOfBoolean)
  {
    super(paramGWindow);
    this.pos = paramArrayOfInt;
    this.enable = paramArrayOfBoolean;
    this.angle0 = paramFloat1;
    this.angleStep = paramFloat2;
    this.state = 0;
    this.states = paramArrayOfInt.length;
  }

  public void update() {
    refresh();
  }

  public void refresh() {
    this.cfg.reset();
    int i = this.cfg.countStates();
    int j = this.cfg.firstState();
    if (this.enable == null)
      this.enable = new boolean[i];
    for (int k = 0; k < i; k++) {
      this.enable[k] = this.cfg.isEnabledState(k + j);
    }
    setEnable(this.cfg.isEnabled());
  }

  public boolean notify(int paramInt1, int paramInt2) {
    if (this.cfg == null) {
      return super.notify(paramInt1, paramInt2);
    }
    if (paramInt1 == 2) {
      int i = this.cfg.get() - this.cfg.firstState();
      if (i != this.state) {
        if (this.bUpdate) {
          this.cfg.set(this.state + this.cfg.firstState());
          int j = this.cfg.apply();
          this.cfg.reset();
          this.cfg.applyExtends(j);
          int k = this.cfg.get() - this.cfg.firstState();
          if (k != this.state)
            setState(k, false);
        } else {
          this.bChanged = true;
        }
      }
    }
    return super.notify(paramInt1, paramInt2);
  }

  public GUISwitchN(GWindow paramGWindow, float paramFloat1, float paramFloat2, int[] paramArrayOfInt, CfgInt paramCfgInt, boolean paramBoolean)
  {
    this(paramGWindow, paramFloat1, paramFloat2, paramArrayOfInt, null);
    this.cfg = paramCfgInt;
    this.bUpdate = paramBoolean;
    refresh();
    int i = paramCfgInt.firstState();
    setState(paramCfgInt.get() - i, false);
    setEnable(paramCfgInt.isEnabled());
  }
}