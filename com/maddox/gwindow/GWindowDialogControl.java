package com.maddox.gwindow;

public class GWindowDialogControl extends GWindow
{
  public boolean bEnable = true;
  public boolean bDown = false;
  public boolean bTabStop = false;
  public GCaption cap;
  public int align = 0;
  public int color = 0;
  public int font = 0;
  public String toolTip;

  public boolean isDefault()
  {
    if (!(this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow instanceof GWindowDialogClient))
      return false;
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow;
    return localGWindowDialogClient.defaultControl == this;
  }

  public boolean isEscape() {
    if (!(this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow instanceof GWindowDialogClient))
      return false;
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow;
    return localGWindowDialogClient.escapeControl == this;
  }
  public boolean isEnable() {
    return this.bEnable;
  }
  public void setEnable(boolean paramBoolean) {
    this.bEnable = paramBoolean;
  }
  public void checkEnabling() {
  }

  public void setToolTip(String paramString) {
    this.toolTip = paramString;
  }

  public boolean _notify(int paramInt1, int paramInt2) {
    return notify(paramInt1, paramInt2);
  }

  public void windowShown() {
    checkEnabling();
    super.windowShown();
  }

  public void resolutionChanged()
  {
    super.resolutionChanged();
    if ((this.cap != null) && (this.cap.offsetHotKey > 0))
      this.cap.offsetHotKey = -1;
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    super.keyboardKey(paramInt, paramBoolean);
    if (paramInt != 32) return;
    if (!this.bEnable) { this.bDown = false; return; }
    this.bDown = paramBoolean;
    if (!paramBoolean)
      _notify(2, 0);
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if (paramInt != 0) return;
    if (!this.bEnable) { this.bDown = false; return; }
    this.bDown = paramBoolean;
  }

  public void mouseClick(int paramInt, float paramFloat1, float paramFloat2) {
    super.mouseClick(paramInt, paramFloat1, paramFloat2);
    if (!this.bEnable) { this.bDown = false; return; }
    if (paramInt == 0)
      _notify(2, 0);
  }

  public void mouseEnter() {
    super.mouseEnter();
    toolTip(this.toolTip);
  }

  public void mouseLeave() {
    super.mouseLeave();
    toolTip(null);
  }

  public GWindowDialogControl() {
    this.jdField_bNotify_of_type_Boolean = true;
  }

  public GWindowDialogControl(GWindow paramGWindow) {
    this.jdField_bNotify_of_type_Boolean = true;
    doNew(paramGWindow, 0.0F, 0.0F, 12.0F, 12.0F, false);
  }

  public GWindowDialogControl(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.jdField_bNotify_of_type_Boolean = true;
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}