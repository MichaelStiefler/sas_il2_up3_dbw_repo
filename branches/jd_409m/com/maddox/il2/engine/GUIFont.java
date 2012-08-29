package com.maddox.il2.engine;

import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GFont.Loader;
import com.maddox.gwindow.GSize;

public class GUIFont extends GFont
{
  protected TTFont fnt = null;

  public void size(String paramString, GSize paramGSize) {
    if (this.fnt == null) {
      paramGSize.dx = 0.0F;
      paramGSize.dy = 1.0F;
      return;
    }
    if ((paramString == null) || (paramString.length() == 0)) {
      paramGSize.dx = 0.0F;
      paramGSize.dy = this.jdField_height_of_type_Float;
      return;
    }
    paramGSize.dx = this.fnt.width(paramString);
    paramGSize.dy = this.jdField_height_of_type_Float;
  }
  public void size(String paramString, int paramInt1, int paramInt2, GSize paramGSize) {
    if (this.fnt == null) {
      paramGSize.dx = 0.0F;
      paramGSize.dy = 1.0F;
      return;
    }
    if ((paramString == null) || (paramString.length() == 0)) {
      paramGSize.dx = 0.0F;
      paramGSize.dy = this.jdField_height_of_type_Float;
      return;
    }
    paramGSize.dx = this.fnt.width(paramString, paramInt1, paramInt2);
    paramGSize.dy = this.jdField_height_of_type_Float;
  }
  public void size(char[] paramArrayOfChar, int paramInt1, int paramInt2, GSize paramGSize) {
    if (this.fnt == null) {
      paramGSize.dx = 0.0F;
      paramGSize.dy = 1.0F;
      return;
    }
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
      paramGSize.dx = 0.0F;
      paramGSize.dy = this.jdField_height_of_type_Float;
      return;
    }
    paramGSize.dx = this.fnt.width(paramArrayOfChar, paramInt1, paramInt2);
    paramGSize.dy = this.jdField_height_of_type_Float;
  }

  public int len(String paramString, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    if ((this.fnt == null) || (paramString == null) || (paramString.length() == 0))
      return 0;
    if (paramBoolean1) {
      return this.fnt.len(paramString, paramFloat, paramBoolean2);
    }
    return this.fnt.lenEnd(paramString, paramFloat, paramBoolean2);
  }
  public int len(String paramString, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    if ((this.fnt == null) || (paramString == null) || (paramString.length() == 0))
      return 0;
    if (paramBoolean1) {
      return this.fnt.len(paramString, paramInt1, paramInt2, paramFloat, paramBoolean2);
    }
    return this.fnt.lenEnd(paramString, paramInt1, paramInt2, paramFloat, paramBoolean2);
  }
  public int len(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    if ((this.fnt == null) || (paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
      return 0;
    if (paramBoolean1) {
      return this.fnt.len(paramArrayOfChar, paramInt1, paramInt2, paramFloat, paramBoolean2);
    }
    return this.fnt.lenEnd(paramArrayOfChar, paramInt1, paramInt2, paramFloat, paramBoolean2);
  }

  public void resolutionChanged() {
    if (this.fnt != null) {
      this.jdField_height_of_type_Float = this.fnt.height();
      this.descender = this.fnt.descender();
    }
  }

  private GUIFont()
  {
  }

  GUIFont(1 param1)
  {
    this();
  }

  public static class _Loader extends GFont.Loader
  {
    public GFont load(String paramString)
    {
      GUIFont localGUIFont = new GUIFont(null);
      localGUIFont.fnt = TTFont.get(paramString);
      localGUIFont.resolutionChanged();
      return localGUIFont;
    }
  }
}