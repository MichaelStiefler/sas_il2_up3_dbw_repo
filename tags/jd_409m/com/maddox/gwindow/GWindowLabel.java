package com.maddox.gwindow;

public class GWindowLabel extends GWindowDialogControl
{
  public boolean isMousePassThrough(float paramFloat1, float paramFloat2)
  {
    return true;
  }

  public void render() {
    lookAndFeel().render(this);
  }
  public GSize getMinSize(GSize paramGSize) {
    return lookAndFeel().getMinSize(this, paramGSize);
  }
  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    return lookAndFeel().getClientRegion(this, paramGRegion, paramFloat);
  }

  public GWindowLabel(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString1, String paramString2)
  {
    this.cap = new GCaption(paramString1);
    this.toolTip = paramString2;
    this.align = 0;
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}