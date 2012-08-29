package com.maddox.gwindow;

public class GWindowEditControl extends GWindowEditBox
{
  public void render()
  {
    lookAndFeel().render(this);
    checkCaretTimeout();
  }

  public GWindowEditControl()
  {
  }

  public GWindowEditControl(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString)
  {
    this.toolTip = paramString;
    this.align = 0;
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}