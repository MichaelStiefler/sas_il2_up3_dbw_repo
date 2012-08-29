package com.maddox.gwindow;

public class GWindowCheckBox extends GWindowDialogControl
{
  public boolean bChecked = false;

  public boolean isChecked() { return this.bChecked; }

  public void setChecked(boolean paramBoolean1, boolean paramBoolean2) {
    if (this.bChecked == paramBoolean1)
      return;
    if (paramBoolean2) _notify(2, 0); else
      this.bChecked = paramBoolean1;
  }

  public boolean _notify(int paramInt1, int paramInt2) {
    if (paramInt1 == 2) {
      this.bChecked = (!this.bChecked);
    }
    return notify(paramInt1, paramInt2);
  }

  public boolean notify(int paramInt1, int paramInt2) {
    if (paramInt1 == 2) {
      lAF().soundPlay("clickCheckBox");
    }
    return super.notify(paramInt1, paramInt2);
  }

  public void render() {
    lookAndFeel().render(this);
  }
  public GSize getMinSize(GSize paramGSize) {
    return lookAndFeel().getMinSize(this, paramGSize);
  }

  public void resolutionChanged() {
    _setSize();
  }

  private void _setSize() {
    GSize localGSize = getMinSize();
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = localGSize.dx;
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = localGSize.dy;
    if (this.jdField_metricWin_of_type_ComMaddoxGwindowGRegion != null) {
      this.jdField_metricWin_of_type_ComMaddoxGwindowGRegion.dx = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / lookAndFeel().metric());
      this.jdField_metricWin_of_type_ComMaddoxGwindowGRegion.dy = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / lookAndFeel().metric());
    }
  }

  public void created() {
    super.created();
    _setSize();
  }
  public GWindowCheckBox() {
  }

  public GWindowCheckBox(GWindow paramGWindow, float paramFloat1, float paramFloat2, String paramString) {
    this.toolTip = paramString;
    this.align = 0;
    doNew(paramGWindow, paramFloat1, paramFloat2, 1.0F, 1.0F, true);
  }
}