package com.maddox.gwindow;

public class GWindowMenuBarItem extends GWindow
{
  public GWindowMenu subMenu;
  public GCaption cap;
  public String toolTip;

  public GWindowMenuBar menuBar()
  {
    return (GWindowMenuBar)this.parentWindow; } 
  public GWindowMenu subMenu() { return this.subMenu; }

  public void mouseEnter() {
    super.mouseEnter();
    menuBar().setOver(this);
    if (menuBar().selected != null)
      menuBar().setSelected(this); 
  }

  public void mouseLeave() {
    super.mouseLeave();
    if (menuBar().over == this)
      menuBar().setOver(menuBar().selected); 
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if ((paramBoolean) && (paramInt == 0)) {
      menuBar().setOver(this);
      menuBar().setSelected(menuBar().selected == this ? null : this);
    }
  }

  public void select() {
    activateWindow();
    if (this.subMenu == null) return;
    lAF().soundPlay("MenuPoolDown");
    this.subMenu.setPos(0.0F, this.win.dy);
    this.subMenu.activateWindow();
  }
  public void unSelect() {
    if (this.subMenu == null) return;
    lAF().soundPlay("MenuCloseUp");
    this.subMenu.setSelected(null);
    this.subMenu.hideWindow();
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    menuBar().keyboardKey(paramInt, paramBoolean);
  }
  public void keyboardChar(char paramChar) {
    menuBar().keyboardChar(paramChar);
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

  public void resolutionChanged() {
    if ((this.cap != null) && (this.cap.offsetHotKey > 0))
      this.cap.offsetHotKey = -1;
    GSize localGSize = getMinSize();
    this.win.dx = localGSize.dx;
    this.win.dy = localGSize.dy;
    if (this.subMenu != null)
      this.subMenu.resolutionChanged();
  }

  public void created() {
    super.created();
    GSize localGSize = getMinSize();
    this.win.dx = localGSize.dx;
    this.win.dy = localGSize.dy;
  }

  public GWindowMenuBarItem(GWindowMenuBar paramGWindowMenuBar, String paramString1, String paramString2) {
    this.cap = new GCaption(paramString1);
    this.toolTip = paramString2;
    doNew(paramGWindowMenuBar, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }
}