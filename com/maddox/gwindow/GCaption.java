package com.maddox.gwindow;

public class GCaption
{
  public String caption;
  public int iHotKey;
  public int offsetHotKey;
  public char hotKey;

  public void draw(GWindow paramGWindow, float paramFloat1, float paramFloat2, GFont paramGFont)
  {
    draw(paramGWindow, paramFloat1, paramFloat2, paramGFont, true);
  }

  public void draw(GWindow paramGWindow, float paramFloat1, float paramFloat2, GFont paramGFont, boolean paramBoolean) {
    paramGWindow.root.C.font = paramGFont;
    paramGWindow.draw(paramFloat1, paramFloat2, this.caption);
    if ((this.hotKey != 0) && (paramBoolean))
      paramGWindow.draw(paramFloat1 + offsetHotKey(paramGFont), paramFloat2, "_");
  }

  public int offsetHotKey(GFont paramGFont)
  {
    if (this.offsetHotKey < 0)
      this.offsetHotKey = (int)paramGFont.size(this.caption, 0, this.iHotKey).dx;
    return this.offsetHotKey;
  }

  public void set(String paramString) {
    int i = paramString.indexOf('&');
    if (i == 0) {
      this.caption = paramString.substring(1);
      this.iHotKey = 0;
      this.hotKey = paramString.charAt(1);
    } else if (i > 0) {
      this.caption = (paramString.substring(0, i) + paramString.substring(i + 1));
      this.iHotKey = i;
      this.hotKey = paramString.charAt(i + 1);
      this.offsetHotKey = -1;
    } else {
      this.caption = paramString;
      this.iHotKey = 0;
      this.hotKey = '\000';
      this.offsetHotKey = 0;
    }
  }

  public GCaption(String paramString) {
    set(paramString);
  }
}