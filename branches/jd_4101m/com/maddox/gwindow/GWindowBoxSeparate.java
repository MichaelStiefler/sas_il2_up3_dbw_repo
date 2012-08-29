package com.maddox.gwindow;

public class GWindowBoxSeparate extends GWindow
{
  public GWindow exclude;
  public float border = 0.0F;

  public void render() {
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

  public GWindowBoxSeparate(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}