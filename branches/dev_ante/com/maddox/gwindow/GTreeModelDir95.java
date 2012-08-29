package com.maddox.gwindow;

public class GTreeModelDir95 extends GTreeModelDir
{
  private GTexRegion iconNormal;
  private GTexRegion iconSelect;

  public GTexRegion getIcon(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      if (this.iconSelect == null)
        this.iconSelect = new GTexRegion("GUI/win95/cursorss.mat", 32.0F, 96.0F, 32.0F, 32.0F);
      return this.iconSelect;
    }
    if (this.iconNormal == null)
      this.iconNormal = new GTexRegion("GUI/win95/cursorss.mat", 64.0F, 96.0F, 32.0F, 32.0F);
    return this.iconNormal;
  }

  public GTreeModelDir95(String paramString) {
    super(paramString);
  }
}