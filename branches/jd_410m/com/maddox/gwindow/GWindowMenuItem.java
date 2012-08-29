package com.maddox.gwindow;

import java.io.PrintStream;

public class GWindowMenuItem extends GWindow
{
  public GWindowMenu subMenu;
  public boolean bEnable = true;
  public boolean bChecked = false;
  public GCaption cap;
  public String toolTip;

  public GWindowMenu menu()
  {
    return (GWindowMenu)this.parentWindow; } 
  public GWindowMenu subMenu() { return this.subMenu; }

  public void execute() {
    System.out.println("execute GWindowMenuItem: " + this.cap.caption);
  }

  public void mouseEnter() {
    super.mouseEnter();
    if (this.bEnable)
      menu().setSelected(this); 
  }

  public void checkEnabling() {
  }

  public void windowShown() {
    checkEnabling();
    super.windowShown();
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if ((!paramBoolean) && (paramInt == 0) && (this.bEnable) && (menu().selected == this))
      menu().doExecute(this);
  }

  public void select()
  {
    activateWindow();
    if (this.subMenu == null) return;
    lAF().soundPlay("MenuPoolDown");
    this.subMenu.setPos(this.win.dx, 0.0F);
    this.subMenu.activateWindow();
  }
  public void unSelect() {
    if (this.subMenu == null) return;
    lAF().soundPlay("MenuCloseUp");
    this.subMenu.setSelected(null);
    this.subMenu.hideWindow();
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    menu().keyboardKey(paramInt, paramBoolean);
  }
  public void keyboardChar(char paramChar) {
    menu().keyboardChar(paramChar);
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
    if (this.subMenu != null)
      this.subMenu.resolutionChanged();
    getMinSize();
  }

  public void created() {
    super.created();
  }

  public GWindowMenuItem(GWindowMenu paramGWindowMenu, String paramString1, String paramString2) {
    this.cap = new GCaption(paramString1);
    this.toolTip = paramString2;
    doNew(paramGWindowMenu, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }
}