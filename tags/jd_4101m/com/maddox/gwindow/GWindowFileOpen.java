package com.maddox.gwindow;

public class GWindowFileOpen extends GWindowFileBox
{
  public GWindowFileOpen(GWindow paramGWindow, boolean paramBoolean, String paramString, GFileFilter[] paramArrayOfGFileFilter)
  {
    super(paramGWindow, paramBoolean, paramGWindow.lAF().i18n("Open_file"), paramString, paramArrayOfGFileFilter);
  }

  public GWindowFileOpen(GWindow paramGWindow, boolean paramBoolean, String paramString1, String paramString2, GFileFilter[] paramArrayOfGFileFilter) {
    super(paramGWindow, paramBoolean, paramString1, paramString2, paramArrayOfGFileFilter);
  }
}