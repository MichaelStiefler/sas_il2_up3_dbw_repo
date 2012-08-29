package com.maddox.gwindow;

public class GWindowButtonTexture extends GWindowDialogControl
{
  public boolean bStrech = true;
  public GTexRegion texUP;
  public GTexRegion texDOWN;
  public GTexRegion texDISABLE;
  public GTexRegion texOVER;

  public void render()
  {
    lookAndFeel().render(this);
  }

  public GSize getMinSize(GSize paramGSize) {
    paramGSize.set(this.texUP.dx, this.texUP.dy);
    return paramGSize;
  }

  public GWindowButtonTexture()
  {
  }

  public GWindowButtonTexture(GWindow paramGWindow) {
    super(paramGWindow);
  }
}