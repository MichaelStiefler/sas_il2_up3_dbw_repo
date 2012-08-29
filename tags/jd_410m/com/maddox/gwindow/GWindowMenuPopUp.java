package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowMenuPopUp extends GWindowMenu
{
  public void beforeExecute(GWindowMenuItem paramGWindowMenuItem)
  {
    if (paramGWindowMenuItem != null) {
      lAF().soundPlay("WindowOpen");
    }
    close(false);
    setSelected(null);
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    super.keyboardKey(paramInt, paramBoolean);
    int i = this.items.size();
    if ((!paramBoolean) && (i > 0))
    {
      int j;
      switch (paramInt) {
      case 27:
        close(false);
        setSelected(null);
        break;
      case 10:
        if (this.selected == null) break;
        doExecute(this.selected); break;
      case 38:
        if (this.selected == null) {
          nextSelected(i - 1, -1);
        } else {
          j = this.items.indexOf(this.selected);
          if (j == 0) j = i;
          nextSelected(j - 1, -1);
        }
        break;
      case 40:
        if (this.selected == null) {
          nextSelected(0, 1);
        } else {
          j = this.items.indexOf(this.selected);
          if (j == i - 1) j = -1;
          nextSelected(j + 1, 1);
        }
        break;
      }
    }
  }

  public void msgMouseButton(boolean paramBoolean1, int paramInt, boolean paramBoolean2, float paramFloat1, float paramFloat2)
  {
    if (paramBoolean1) return;
    if (!paramBoolean2) return;
    GWindow localGWindow1 = this.root.findWindowUnder(this.root.mousePos.x, this.root.mousePos.y);
    GWindow localGWindow2 = localGWindow1.getParent(GWindowMenuPopUp.class, false);
    if (localGWindow2 != this) {
      close(false);
      setSelected(null);
    }
  }

  public void created() {
    super.created();
    this.bMouseListener = true;
    this.bAcceptsHotKeys = true;
  }
}