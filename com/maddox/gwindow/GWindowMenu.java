package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowMenu extends GWindow
{
  public ArrayList items = new ArrayList();
  public GWindowMenuItem selected;
  public int columns = 1;

  public GWindowMenuItem addItem(String paramString1, String paramString2) {
    GWindowMenuItem localGWindowMenuItem = new GWindowMenuItem(this, paramString1, paramString2);
    if ("-".equals(paramString1))
      localGWindowMenuItem.bEnable = false;
    return addItem(localGWindowMenuItem);
  }
  public GWindowMenuItem addItem(int paramInt, String paramString1, String paramString2) {
    GWindowMenuItem localGWindowMenuItem = new GWindowMenuItem(this, paramString1, paramString2);
    if ("-".equals(paramString1))
      localGWindowMenuItem.bEnable = false;
    return addItem(paramInt, localGWindowMenuItem);
  }
  public GWindowMenuItem addItem(GWindowMenuItem paramGWindowMenuItem) {
    this.items.add(paramGWindowMenuItem);
    computePosSize();
    return paramGWindowMenuItem;
  }
  public GWindowMenuItem addItem(int paramInt, GWindowMenuItem paramGWindowMenuItem) {
    this.items.add(paramInt, paramGWindowMenuItem);
    computePosSize();
    return paramGWindowMenuItem;
  }
  public GWindowMenuItem getItem(int paramInt) {
    return (GWindowMenuItem)this.items.get(paramInt);
  }
  public int size() {
    return this.items.size();
  }
  public void clearItems() {
    int i = this.items.size();
    if (i == 0) return;
    for (int j = 0; j < i; j++) {
      GWindowMenuItem localGWindowMenuItem = (GWindowMenuItem)this.items.get(j);
      localGWindowMenuItem.close(false);
    }
    this.items.clear();
    this.selected = null;
    close(false);
  }

  private void computePosSize() {
    GSize localGSize = getMinSize();
    setSize(localGSize.dx, localGSize.dy);
    int i = this.items.size();
    GRegion localGRegion = getClientRegion();
    float f1 = localGRegion.x;
    float f2 = localGRegion.y;
    float f3 = (localGRegion.dx - (this.win.dx - localGRegion.dx) * (this.columns - 1)) / this.columns;
    float f4 = f3 + (this.win.dx - localGRegion.dx);
    for (int j = 0; j < i; j++) {
      GWindowMenuItem localGWindowMenuItem = (GWindowMenuItem)this.items.get(j);
      localGSize = localGWindowMenuItem.getMinSize();
      localGWindowMenuItem.setSize(f3, localGSize.dy);
      localGWindowMenuItem.setPos(f1, f2);
      if (f2 + localGWindowMenuItem.win.dy > localGRegion.dy) {
        f2 = localGRegion.y;
        f1 += f4;
      } else {
        f2 += localGWindowMenuItem.win.dy;
      }
    }
  }

  public void resolutionChanged() {
    computePosSize();
  }

  public void setPos(float paramFloat1, float paramFloat2) {
    if (this.parentWindow != null) {
      GPoint localGPoint = this.parentWindow.windowToGlobal(paramFloat1, paramFloat2);
      float f1 = localGPoint.x;
      float f2 = localGPoint.y;
      if (f1 + this.win.dx > this.root.win.dx) f1 = this.root.win.dx - this.win.dx;
      if (f2 + this.win.dy > this.root.win.dy) f2 = this.root.win.dy - this.win.dy;
      if (f1 < 0.0F) f1 = 0.0F;
      if (f2 < 0.0F) f2 = 0.0F;
      localGPoint = this.parentWindow.globalToWindow(f1, f2);
      paramFloat1 = localGPoint.x;
      paramFloat2 = localGPoint.y;
    }
    super.setPos(paramFloat1, paramFloat2);
  }

  public void beforeExecute(GWindowMenuItem paramGWindowMenuItem) {
    if (paramGWindowMenuItem != null)
      lAF().soundPlay("WindowOpen");
    Object localObject;
    if ((this.parentWindow instanceof GWindowMenuItem)) {
      localObject = (GWindowMenuItem)this.parentWindow;
      ((GWindowMenuItem)localObject).menu().beforeExecute(null);
    } else if ((this.parentWindow instanceof GWindowMenuBarItem)) {
      localObject = (GWindowMenuBarItem)this.parentWindow;
      GWindowMenuBar localGWindowMenuBar = ((GWindowMenuBarItem)localObject).menuBar();
      localGWindowMenuBar.setOver(null);
      localGWindowMenuBar.setSelected(null);
    } else {
      close(false);
    }
    setSelected(null);
  }

  public void execute(GWindowMenuItem paramGWindowMenuItem) {
    paramGWindowMenuItem.execute();
  }

  public void doExecute(GWindowMenuItem paramGWindowMenuItem) {
    if (paramGWindowMenuItem.subMenu() == null) {
      beforeExecute(paramGWindowMenuItem);
      execute(paramGWindowMenuItem);
    } else {
      setSelected(paramGWindowMenuItem);
    }
  }

  public void windowShown() {
    super.windowShown();
    this.selected = null;
  }

  protected void setSelected(GWindowMenuItem paramGWindowMenuItem) {
    if (this.selected == paramGWindowMenuItem)
      return;
    if (this.selected != null)
      this.selected.unSelect();
    this.selected = paramGWindowMenuItem;
    if (paramGWindowMenuItem != null) {
      toolTip(paramGWindowMenuItem.toolTip);
      this.selected.select();
    } else {
      toolTip(null);
    }
  }

  protected void nextSelected(int paramInt1, int paramInt2) {
    int i = this.items.size();
    int j = i;
    while (j-- > 0) {
      GWindowMenuItem localGWindowMenuItem = (GWindowMenuItem)this.items.get(paramInt1);
      if (localGWindowMenuItem.bEnable) {
        setSelected(localGWindowMenuItem);
        return;
      }
      paramInt1 = (paramInt1 + paramInt2) % i;
    }
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    super.keyboardKey(paramInt, paramBoolean);
    int i = this.items.size();
    if ((!paramBoolean) && (i > 0))
    {
      int j;
      switch (paramInt) {
      case 27:
        if (this.selected == null) break;
        setSelected(null);
        return;
      case 10:
        if (this.selected != null)
          doExecute(this.selected);
        return;
      case 37:
        if (!(this.parentWindow instanceof GWindowMenuItem)) break;
        paramInt = 38; break;
      case 38:
        if (this.selected == null) {
          nextSelected(i - 1, -1);
        } else {
          j = this.items.indexOf(this.selected);
          if (j == 0) j = i;
          nextSelected(j - 1, -1);
        }
        return;
      case 39:
        if ((this.parentWindow instanceof GWindowMenuBarItem))
          break;
      case 40:
        if (this.selected == null) {
          nextSelected(0, 1);
        } else {
          j = this.items.indexOf(this.selected);
          if (j == i - 1) j = -1;
          nextSelected(j + 1, 1);
        }
        return;
      }

    }

    this.parentWindow.keyboardKey(paramInt, paramBoolean);
  }

  public void keyboardChar(char paramChar) {
    super.keyboardChar(paramChar);
    int i = this.items.size();
    for (int j = 0; j < i; j++) {
      GWindowMenuItem localGWindowMenuItem = (GWindowMenuItem)this.items.get(j);
      if (Character.toLowerCase(localGWindowMenuItem.cap.hotKey) == Character.toLowerCase(paramChar)) {
        doExecute(localGWindowMenuItem);
        break;
      }
    }
  }

  public void created() {
    super.created();
    this.bAlwaysOnTop = true;
    this.bClip = false;
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
}