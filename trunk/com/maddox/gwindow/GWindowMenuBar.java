package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowMenuBar extends GWindow
{
  public ArrayList items = new ArrayList();
  public GWindowMenuBarItem selected;
  public GWindowMenuBarItem over;
  public boolean bAltDown;

  public GWindowMenuBarItem addItem(String paramString1, String paramString2)
  {
    GWindowMenuBarItem localGWindowMenuBarItem = new GWindowMenuBarItem(this, paramString1, paramString2);
    return addItem(localGWindowMenuBarItem);
  }
  public GWindowMenuBarItem addItem(GWindowMenuBarItem paramGWindowMenuBarItem) {
    int i = this.items.size();
    GRegion localGRegion = getClientRegion();
    float f = localGRegion.x;
    if (i > 0) {
      for (int j = 0; j < i; j++) {
        GWindowMenuBarItem localGWindowMenuBarItem = (GWindowMenuBarItem)this.items.get(j);
        f += localGWindowMenuBarItem.win.dx;
      }
    }
    this.items.add(paramGWindowMenuBarItem);
    paramGWindowMenuBarItem.setPos(f, localGRegion.y);
    return paramGWindowMenuBarItem;
  }
  public GWindowMenuBarItem getItem(int paramInt) {
    return (GWindowMenuBarItem)this.items.get(paramInt);
  }
  public int countItems() { return this.items.size(); }

  public void resolutionChanged() {
    getMinSize();
    int i = this.items.size();
    GRegion localGRegion = getClientRegion();
    float f = localGRegion.x;
    if (i > 0)
      for (int j = 0; j < i; j++) {
        GWindowMenuBarItem localGWindowMenuBarItem = (GWindowMenuBarItem)this.items.get(j);
        localGWindowMenuBarItem.setPos(f, localGRegion.y);
        f += localGWindowMenuBarItem.win.dx;
      }
  }

  protected void setOver(GWindowMenuBarItem paramGWindowMenuBarItem)
  {
    if (paramGWindowMenuBarItem == null) {
      toolTip(null);
      this.over = null;
    } else {
      toolTip(paramGWindowMenuBarItem.toolTip);
      this.over = paramGWindowMenuBarItem;
    }
  }

  protected void setSelected(GWindowMenuBarItem paramGWindowMenuBarItem) {
    if (this.selected == paramGWindowMenuBarItem)
      return;
    if (this.selected != null)
      this.selected.unSelect();
    this.selected = paramGWindowMenuBarItem;
    if (paramGWindowMenuBarItem != null)
      this.selected.select();
  }

  public void msgMouseButton(boolean paramBoolean1, int paramInt, boolean paramBoolean2, float paramFloat1, float paramFloat2) {
    if (paramBoolean1) return;

    GWindow localGWindow1 = this.root.findWindowUnder(this.root.mousePos.x, this.root.mousePos.y);
    GWindow localGWindow2 = localGWindow1.getParent(GWindowMenuBar.class, false);
    if ((localGWindow2 != this) || ((!(localGWindow1 instanceof GWindowMenuBarItem)) && (!(localGWindow1 instanceof GWindowMenuBar)) && (!(localGWindow1 instanceof GWindowMenuItem)) && (!(localGWindow1 instanceof GWindowMenu))))
    {
      if (this.over != null) setOver(null);
      setSelected(null);
    }
  }

  public boolean hotKey(int paramInt, boolean paramBoolean) {
    if ((paramInt != 18) || (this.items.size() == 0))
      return false;
    this.bAltDown = paramBoolean;
    if (!this.bAltDown)
      return false;
    if ((paramBoolean) && ((this.over != null) || (this.selected != null))) {
      this.bAltDown = false;
      return false;
    }
    if ((this.parentWindow instanceof GWindowFramed)) {
      if (!this.parentWindow.isActivated())
        this.bAltDown = false;
    } else {
      GWindow localGWindow = this.root.checkKeyFocusWindow();
      if (localGWindow.getParent(GWindowFramed.class, false) != null)
        this.bAltDown = false;
    }
    return false;
  }
  public boolean hotKeyChar(char paramChar) {
    if ((!this.bAltDown) || (this.over != null) || (this.selected != null)) return false;
    int i = this.items.size();
    for (int j = 0; j < i; j++) {
      GWindowMenuBarItem localGWindowMenuBarItem = (GWindowMenuBarItem)this.items.get(j);
      if (Character.toLowerCase(localGWindowMenuBarItem.cap.hotKey) == Character.toLowerCase(paramChar)) {
        setOver(localGWindowMenuBarItem);
        setSelected(localGWindowMenuBarItem);
        return true;
      }
    }
    return false;
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    super.keyboardKey(paramInt, paramBoolean);
    if (paramBoolean) return;
    int i = this.items.size();
    if (i == 0) return;
    if (this.over == null) return;
    int j;
    switch (paramInt) {
    case 27:
      setSelected(null);
      break;
    case 10:
      setSelected(this.selected == null ? this.over : null);
      break;
    case 37:
      j = this.items.indexOf(this.over);
      if (j == 0) j = i;
      j--; setOver((GWindowMenuBarItem)this.items.get(j));
      if (this.selected != null) {
        setSelected(this.over);
      }
      break;
    case 39:
      j = this.items.indexOf(this.over);
      if (j == i - 1) j = -1;
      j++; setOver((GWindowMenuBarItem)this.items.get(j));
      if (this.selected != null) {
        setSelected(this.over);
      }
      break;
    }
  }

  public void created()
  {
    super.created();
    this.bAlwaysOnTop = true;
    this.bMouseListener = true;
    this.bAcceptsHotKeys = true;
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