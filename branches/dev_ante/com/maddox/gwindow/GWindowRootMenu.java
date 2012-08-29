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
    if (this.menuBar.isVisible()) paramGRegion.y = (this.menuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy + paramFloat); else
      paramGRegion.y = paramFloat;
    paramGRegion.dx = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * paramFloat);
    paramGRegion.dy = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 2.0F * paramFloat);
    if (this.menuBar.isVisible()) paramGRegion.dy -= this.menuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    if (this.statusBar.isVisible()) paramGRegion.dy -= this.statusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    return paramGRegion;
  }

  public void resized() {
    super.resized();
    float f = this.menuBar.getMinSize().dy;
    this.menuBar.setPos(0.0F, 0.0F);
    this.menuBar.setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f);

    f = this.statusBar.getMinSize().dy;
    this.statusBar.setPos(0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f);
    this.statusBar.setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f);

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