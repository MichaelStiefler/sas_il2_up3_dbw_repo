package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowDialogClient extends GWindowClient
{
  public GWindowDialogControl defaultControl;
  public GWindowDialogControl escapeControl;
  public ArrayList tabs = new ArrayList();

  public GWindowDialogControl addLabel(GWindowDialogControl paramGWindowDialogControl) {
    paramGWindowDialogControl.bAcceptsKeyFocus = false;
    paramGWindowDialogControl.bTransient = true;
    return paramGWindowDialogControl;
  }

  public GWindowDialogControl addDefault(GWindowDialogControl paramGWindowDialogControl) {
    return addControl(3, paramGWindowDialogControl);
  }

  public GWindowDialogControl addControl(GWindowDialogControl paramGWindowDialogControl) {
    return addControl(1, paramGWindowDialogControl);
  }
  public GWindowDialogControl addEscape(GWindowDialogControl paramGWindowDialogControl) {
    GWindowDialogControl localGWindowDialogControl = addControl(1, paramGWindowDialogControl);
    setEscape(localGWindowDialogControl);
    return localGWindowDialogControl;
  }

  public GWindowDialogControl addControl(int paramInt, GWindowDialogControl paramGWindowDialogControl) {
    int i = this.tabs.indexOf(paramGWindowDialogControl);
    if (i >= 0)
      this.tabs.remove(i);
    this.tabs.add(paramGWindowDialogControl);
    paramGWindowDialogControl.bTabStop = ((paramInt & 0x1) != 0);
    if ((paramInt & 0x2) != 0) this.defaultControl = paramGWindowDialogControl;
    setDefault(this.defaultControl);
    return paramGWindowDialogControl;
  }

  public void removeControl(GWindowDialogControl paramGWindowDialogControl) {
    int i = this.tabs.indexOf(paramGWindowDialogControl);
    if (i >= 0)
      this.tabs.remove(i);
    if (paramGWindowDialogControl == this.defaultControl)
      this.defaultControl = null;
    if (paramGWindowDialogControl == this.escapeControl)
      this.escapeControl = null;
  }

  public void setDefault(GWindowDialogControl paramGWindowDialogControl) {
    this.defaultControl = paramGWindowDialogControl;
    if ((this.defaultControl != null) && (this.defaultControl.bEnable))
      this.defaultControl.activateWindow();
  }

  public void setEscape(GWindowDialogControl paramGWindowDialogControl) {
    this.escapeControl = paramGWindowDialogControl;
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    super.keyboardKey(paramInt, paramBoolean);
    doNotify(paramBoolean ? 10 : 11, paramInt);
  }
  public void keyboardChar(char paramChar) {
    super.keyboardChar(paramChar);
    doNotify(12, paramChar);
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
    if (super.notify(paramGWindow, paramInt1, paramInt2))
      return true;
    return doNotify(paramInt1, paramInt2);
  }

  public boolean doNotify(int paramInt1, int paramInt2) {
    if (paramInt1 == 17) {
      return notify(paramInt1, paramInt2);
    }
    if (this.root.bMouseCapture)
      return false;
    int k;
    if (paramInt1 == 12) {
      char c = (char)paramInt2;
      int j = this.tabs.size();
      for (k = 0; k < j; k++) {
        GWindowDialogControl localGWindowDialogControl1 = (GWindowDialogControl)this.tabs.get(k);
        if ((!localGWindowDialogControl1.bEnable) || (!localGWindowDialogControl1.bVisible) || (localGWindowDialogControl1.cap == null) || (!localGWindowDialogControl1.bAcceptsKeyFocus) || (localGWindowDialogControl1.bTransient) || (Character.toLowerCase(localGWindowDialogControl1.cap.hotKey) != Character.toLowerCase(c))) {
          continue;
        }
        localGWindowDialogControl1.activateWindow();
        localGWindowDialogControl1.notify(2, 0);
        return true;
      }

      return false;
    }
    if (paramInt1 != 11)
      return false;
    int i = paramInt2;

    if (i == 10) {
      if ((this.defaultControl != null) && (this.defaultControl.bEnable) && (this.defaultControl.bVisible))
      {
        this.defaultControl.activateWindow();
        this.defaultControl.notify(2, 0);
      }
      return false;
    }
    if (i == 27) {
      if ((this.escapeControl != null) && (this.escapeControl.bEnable) && (this.escapeControl.bVisible))
      {
        this.escapeControl.activateWindow();
        this.escapeControl.notify(2, 0);
      }
      return false;
    }

    GWindow localGWindow = this.activeWindow;
    GWindowDialogControl localGWindowDialogControl2;
    GWindowDialogControl localGWindowDialogControl4;
    if (i == 9) {
      k = this.tabs.indexOf(localGWindow);
      if (k < 0)
        return false;
      localGWindowDialogControl2 = this.tabs.size();
      for (int m = 0; m < localGWindowDialogControl2; m++) {
        k++; localGWindowDialogControl4 = (GWindowDialogControl)this.tabs.get(k % localGWindowDialogControl2);
        if ((!localGWindowDialogControl4.bEnable) || (!localGWindowDialogControl4.bVisible) || (!localGWindowDialogControl4.bTabStop) || (!localGWindowDialogControl4.bAcceptsKeyFocus) || (localGWindowDialogControl4.bTransient))
          continue;
        localGWindowDialogControl4.activateWindow();
        return true;
      }

      return false;
    }
    GWindowDialogControl localGWindowDialogControl3;
    int n;
    GWindowDialogControl localGWindowDialogControl5;
    if ((i == 39) || (i == 40)) {
      k = this.tabs.size();
      localGWindowDialogControl2 = this.tabs.indexOf(localGWindow);
      if (localGWindowDialogControl2 < 0)
        return false;
      localGWindowDialogControl3 = (GWindowDialogControl)this.tabs.get(localGWindowDialogControl2);
      localGWindowDialogControl4 = localGWindowDialogControl2;
      while (k-- > 0) {
        n = (localGWindowDialogControl4 + 1) % this.tabs.size();
        localGWindowDialogControl5 = (GWindowDialogControl)this.tabs.get(n);
        if ((!localGWindowDialogControl3.bTabStop) && ((localGWindowDialogControl3.bTabStop) || (localGWindowDialogControl5.bTabStop)))
          break;
        if ((localGWindowDialogControl5.bEnable) && (localGWindowDialogControl5.bVisible) && (localGWindowDialogControl5.bAcceptsKeyFocus) && (!localGWindowDialogControl5.bTransient))
        {
          localGWindowDialogControl5.activateWindow();
          break;
        }

      }

      return true;
    }
    if ((i == 37) || (i == 38)) {
      k = this.tabs.size();
      localGWindowDialogControl2 = this.tabs.indexOf(localGWindow);
      if (localGWindowDialogControl2 < 0)
        return false;
      localGWindowDialogControl3 = (GWindowDialogControl)this.tabs.get(localGWindowDialogControl2);
      n = localGWindowDialogControl2;
      while (k-- > 0) {
        n = (n + this.tabs.size() - 1) % this.tabs.size();
        localGWindowDialogControl5 = (GWindowDialogControl)this.tabs.get(n);
        if (((!localGWindowDialogControl3.bTabStop) || (!localGWindowDialogControl5.bTabStop)) && (localGWindowDialogControl3.bTabStop))
          break;
        if ((localGWindowDialogControl5.bEnable) && (localGWindowDialogControl5.bVisible) && (localGWindowDialogControl5.bAcceptsKeyFocus) && (!localGWindowDialogControl5.bTransient))
        {
          localGWindowDialogControl5.activateWindow();
          break;
        }

      }

      return true;
    }

    return false;
  }

  public void created()
  {
    this.bNotify = true;
  }
}