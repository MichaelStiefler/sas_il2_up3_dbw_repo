package com.maddox.gwindow;

public class GWindowClient extends GWindow
{
  public void close(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.parentWindow.close(paramBoolean);
    super.close(paramBoolean);
  }

  public GSize getMinSize(GSize paramGSize) {
    paramGSize.dx = 50.0F;
    paramGSize.dy = 50.0F;
    return paramGSize;
  }

  public void render() {
    lookAndFeel().render(this);
  }
}