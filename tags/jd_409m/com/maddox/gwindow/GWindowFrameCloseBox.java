package com.maddox.gwindow;

public class GWindowFrameCloseBox extends GWindowButtonTexture
{
  public void created()
  {
    lookAndFeel().setupFrameCloseBox(this);
    this.toolTip = lAF().i18n("Close_window");
  }

  public void mouseClick(int paramInt, float paramFloat1, float paramFloat2) {
    if (paramInt == 0)
      this.parentWindow.close(false);
  }

  public GWindowFrameCloseBox() {
    this.bNotify = false;
    this.bAcceptsKeyFocus = false;
    this.bTransient = true;
  }
}