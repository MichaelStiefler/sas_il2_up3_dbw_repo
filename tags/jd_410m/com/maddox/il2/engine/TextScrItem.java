package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;

class TextScrItem
{
  Color4f color;
  TTFont font;
  String str;

  public TextScrItem(Color4f paramColor4f, TTFont paramTTFont, String paramString)
  {
    this.color = paramColor4f;
    this.font = paramTTFont;
    this.str = paramString;
  }
}