package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLookAndFeel;

public class GUIButton extends GWindowDialogControl
{
  GTexRegion texUP;
  GTexRegion texDOWN;

  public boolean _notify(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 2)
      lAF().soundPlay("clickButton");
    return super._notify(paramInt1, paramInt2);
  }

  public void mouseClick(int paramInt, float paramFloat1, float paramFloat2) {
    super.mouseClick(paramInt, paramFloat1, paramFloat2);
  }

  public void render() {
    setCanvasColorWHITE();
    GTexRegion localGTexRegion = this.texUP;
    if (this.bDown) localGTexRegion = this.texDOWN;
    draw(0.0F, 0.0F, this.win.dx, this.win.dy, localGTexRegion);
  }

  public void setPosSize(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    GTexRegion localGTexRegion = this.texUP;

    float f1 = 1.0F;
    float f2 = localGTexRegion.dx;
    while (f2 / f1 > paramFloat3) f1 += 1.0F;
    f2 /= f1;
    f1 = 1.0F;
    float f3 = localGTexRegion.dy;
    while (f3 / f1 > paramFloat4) f1 += 1.0F;
    f3 /= f1;

    this.win.x = Math.round(paramFloat1);
    this.win.y = Math.round(paramFloat2);
    this.win.dx = Math.round(paramFloat3);
    this.win.dy = Math.round(paramFloat4);
  }

  public void setPosC(float paramFloat1, float paramFloat2)
  {
    super.setPos(paramFloat1 - this.win.dx / 2.0F, paramFloat2 - this.win.dy / 2.0F);
  }

  public void resolutionChanged() {
    this.win.dx = x1024(this.texUP.dx);
    this.win.dy = y1024(this.texUP.dy);
  }

  public void created() {
    resolutionChanged();
  }

  public GUIButton(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GUIButton Clone() {
    GUIButton localGUIButton = new GUIButton(this.parentWindow);
    localGUIButton.texUP = this.texUP;
    localGUIButton.texDOWN = this.texDOWN;
    return localGUIButton;
  }

  public GUIButton(GWindow paramGWindow, GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.texUP = new GTexRegion(paramGTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    this.texDOWN = new GTexRegion(paramGTexture, paramFloat1 + paramFloat3, paramFloat2, paramFloat3, paramFloat4);
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }
}