package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgFlags;

public class GUISwitchBox extends GWindowCheckBox
{
  public CfgFlags cfg;
  public int iFlag;
  public boolean bUpdate;
  public boolean bChanged;
  public static GTexRegion texUp;
  public static GTexRegion texDown;

  private static void init()
  {
    if (Config.isUSE_RENDER()) {
      if (texDown != null)
        return;
      GTexture localGTexture = GTexture.New("GUI/game/buttons.mat");
      texDown = new GTexRegion(localGTexture, 0.0F, 176.0F, 48.0F, 80.0F);
      texUp = new GTexRegion(localGTexture, 96.0F, 176.0F, 48.0F, 80.0F);
    }
  }

  public void render() {
    if (!isEnable()) return;
    setCanvasColorWHITE();
    if (isChecked()) draw(0.0F, 0.0F, this.win.dx, this.win.dy, texUp); else
      draw(0.0F, 0.0F, this.win.dx, this.win.dy, texDown);
  }

  public GSize getMinSize(GSize paramGSize) {
    paramGSize.dx = this.win.dx;
    paramGSize.dy = this.win.dy;
    return paramGSize;
  }

  public void setPosC(float paramFloat1, float paramFloat2) {
    super.setPos(paramFloat1 - this.win.dx / 2.0F, paramFloat2 - this.win.dy / 2.0F);
  }

  public void resolutionChanged() {
    this.win.dx = x1024(48.0F);
    this.win.dy = y1024(80.0F);
  }

  public void created() {
    this.metricWin = null;
    resolutionChanged();
  }

  public void update() {
    refresh();
  }

  public void refresh() {
    this.cfg.reset();
    setChecked(this.cfg.get(this.iFlag), false);
    setEnable(this.cfg.isEnabledFlag(this.iFlag));
  }

  public boolean _notify(int paramInt1, int paramInt2) {
    if (paramInt1 == 2) {
      this.bChecked = (!this.bChecked);
      if (this.cfg != null) {
        if (this.bUpdate) {
          this.cfg.set(this.iFlag, isChecked());
          int i = this.cfg.apply(this.iFlag);
          this.cfg.reset();
          this.cfg.applyExtends(i);
          boolean bool = this.cfg.get(this.iFlag);
          if (bool != isChecked())
            setChecked(bool, false);
        } else {
          this.bChanged = true;
        }
      }
    }
    return super.notify(paramInt1, paramInt2);
  }

  public GUISwitchBox(GWindow paramGWindow) {
    super(paramGWindow, 0.0F, 0.0F, (String)null);
    init();
  }

  public GUISwitchBox(GWindow paramGWindow, CfgFlags paramCfgFlags, int paramInt, boolean paramBoolean) {
    this(paramGWindow);
    init();
    this.cfg = paramCfgFlags;
    this.iFlag = paramInt;
    this.bUpdate = paramBoolean;
    this.bChanged = false;
    setChecked(paramCfgFlags.get(paramInt), false);
    setEnable(paramCfgFlags.isEnabledFlag(paramInt));
  }
}