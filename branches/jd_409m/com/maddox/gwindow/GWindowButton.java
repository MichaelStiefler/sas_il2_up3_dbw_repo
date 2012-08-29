package com.maddox.gwindow;

public class GWindowButton extends GWindowDialogControl
{
  public boolean bDrawOnlyUP = false;
  public boolean bDrawActive = true;
  public boolean _bNoRender = false;

  public void render() {
    if (!this._bNoRender)
      lookAndFeel().render(this); 
  }

  public GSize getMinSize(GSize paramGSize) {
    return lookAndFeel().getMinSize(this, paramGSize);
  }
  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    return lookAndFeel().getClientRegion(this, paramGRegion, paramFloat);
  }

  public GWindowButton(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowButton(GWindow paramGWindow, String paramString1, String paramString2) {
    this.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(paramString1);
    this.jdField_toolTip_of_type_JavaLangString = paramString2;
    this.jdField_align_of_type_Int = 1;
    doNew(paramGWindow, 0.0F, 0.0F, 50.0F, 2.0F * paramGWindow.lookAndFeel().metric(), false);
  }

  public GWindowButton(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString1, String paramString2)
  {
    this.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(paramString1);
    this.jdField_toolTip_of_type_JavaLangString = paramString2;
    this.jdField_align_of_type_Int = 1;
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}