package com.maddox.gwindow;

public class GWindowRootMenu extends GWindowRoot
{
  public GWindowStatusBar statusBar;
  public GWindowMenuBar menuBar;
  public GWindow clientWindow;

  public void toolTip(String paramString)
  {
    this.statusBar.setHelp(paramString);
  }

  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = paramFloat;
    if (this.menuBar.isVisible()) paramGRegion.y = (this.menuBar.win.dy + paramFloat); else
      paramGRegion.y = paramFloat;
    paramGRegion.dx = (this.win.dx - 2.0F * paramFloat);
    paramGRegion.dy = (this.win.dy - 2.0F * paramFloat);
    if (this.menuBar.isVisible()) paramGRegion.dy -= this.menuBar.win.dy;
    if (this.statusBar.isVisible()) paramGRegion.dy -= this.statusBar.win.dy;
    return paramGRegion;
  }

  public void resized() {
    super.resized();
    float f = this.menuBar.getMinSize().dy;
    this.menuBar.setPos(0.0F, 0.0F);
    this.menuBar.setSize(this.win.dx, f);

    f = this.statusBar.getMinSize().dy;
    this.statusBar.setPos(0.0F, this.win.dy - f);
    this.statusBar.setSize(this.win.dx, f);

    if (this.clientWindow != null)
      this.clientWindow.resized();
  }

  public void created() {
    super.created();
    this.statusBar = ((GWindowStatusBar)create(new GWindowStatusBar()));
    this.menuBar = ((GWindowMenuBar)create(new GWindowMenuBar()));
    resized();
  }
}