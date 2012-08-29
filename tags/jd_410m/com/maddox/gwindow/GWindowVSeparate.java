package com.maddox.gwindow;

public class GWindowVSeparate extends GWindow
{
  public void render()
  {
    lookAndFeel().render(this);
  }

  public boolean isMousePassThrough(float paramFloat1, float paramFloat2) {
    return true;
  }

  public void created() {
    this.bAlwaysBehind = true;
    this.bAcceptsKeyFocus = false;
    this.bTransient = true;
  }

  public GWindowVSeparate(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = paramGWindow.lookAndFeel().getVSeparateW() / paramGWindow.lookAndFeel().metric();
    doNew(paramGWindow, paramFloat1, paramFloat2, f, paramFloat3, true);
  }
}