package com.maddox.gwindow;

public class GWindowFileSaveAs extends GWindowFileBox
{
  public GWindowFileSaveAs(GWindow paramGWindow, boolean paramBoolean, String paramString, GFileFilter[] paramArrayOfGFileFilter)
  {
    super(paramGWindow, paramBoolean, paramGWindow.lAF().i18n("Save_As"), paramString, paramArrayOfGFileFilter);
    this.jdField_wOk_of_type_ComMaddoxGwindowGWindowButton.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(lAF().i18n("&Save"));
  }

  public GWindowFileSaveAs(GWindow paramGWindow, boolean paramBoolean, String paramString1, String paramString2, GFileFilter[] paramArrayOfGFileFilter) {
    super(paramGWindow, paramBoolean, paramString1, paramString2, paramArrayOfGFileFilter);
    this.jdField_wOk_of_type_ComMaddoxGwindowGWindowButton.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(lAF().i18n("&Save"));
  }
}