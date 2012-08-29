package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;

public class GUILightBox extends GWindowCheckBox
{
  GTexRegion texUP;
  GTexRegion texDOWN;

  public void render()
  {
    setCanvasColorWHITE();
    GTexRegion localGTexRegion = this.texDOWN;
    if (this.bChecked) localGTexRegion = this.texUP;
    draw(0.0F, 0.0F, this.win.dx, this.win.dy, localGTexRegion);
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
    this.win.dx = x1024(this.texUP.dx);
    this.win.dy = y1024(this.texUP.dy);
  }

  public void created() {
    this.metricWin = null;
    this.bEnable = false;
    resolutionChanged();
  }

  public GUILightBox(GWindow paramGWindow, GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.texDOWN = new GTexRegion(paramGTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    this.texUP = new GTexRegion(paramGTexture, paramFloat1 + paramFloat3, paramFloat2, paramFloat3, paramFloat4);
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }
}