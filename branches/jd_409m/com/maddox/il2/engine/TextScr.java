package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.opengl.Provider;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;

public class TextScr extends Render
{
  private HashMapInt context = new HashMapInt();
  private TTFont font = TTFont.font[1];
  private Color4f color = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
  private static TextScr scr;

  public void preRender()
  {
  }

  public void render()
  {
    Provider.setPauseProfile(true);
    HashMapIntEntry localHashMapIntEntry = this.context.nextEntry(null);
    while (localHashMapIntEntry != null) {
      int i = localHashMapIntEntry.getKey();
      TextScrItem localTextScrItem = (TextScrItem)localHashMapIntEntry.getValue();

      localTextScrItem.font.output(((int)(localTextScrItem.color.w * 255.0F) & 0xFF) << 24 | ((int)(localTextScrItem.color.x * 255.0F) & 0xFF) << 0 | ((int)(localTextScrItem.color.y * 255.0F) & 0xFF) << 8 | ((int)(localTextScrItem.color.z * 255.0F) & 0xFF) << 16, i >> 16, i & 0xFFFF, 0.0F, localTextScrItem.str);

      localHashMapIntEntry = this.context.nextEntry(localHashMapIntEntry);
    }
    this.context.clear();
    Provider.setPauseProfile(false);
  }

  public static void output(int paramInt1, int paramInt2, String paramString) {
    if ((paramString == null) || ("".equals(paramString)))
      scr.context.remove((paramInt1 & 0xFFFF) << 16 | paramInt2 & 0xFFFF);
    else
      scr.context.put((paramInt1 & 0xFFFF) << 16 | paramInt2 & 0xFFFF, new TextScrItem(scr.color, scr.font, paramString)); 
  }

  public static void output(float paramFloat1, float paramFloat2, String paramString) {
    if ((paramString == null) || ("".equals(paramString)))
      scr.context.remove(((int)paramFloat1 & 0xFFFF) << 16 | (int)paramFloat2 & 0xFFFF);
    else
      scr.context.put(((int)paramFloat1 & 0xFFFF) << 16 | (int)paramFloat2 & 0xFFFF, new TextScrItem(scr.color, scr.font, paramString));
  }

  public static TTFont font() {
    if (scr == null) {
      scr = new TextScr();
      CameraOrtho2D localCameraOrtho2D = new CameraOrtho2D();
      localCameraOrtho2D.set(0.0F, scr.getViewPortWidth(), 0.0F, scr.getViewPortHeight());
      scr.setCamera(localCameraOrtho2D);
      scr.setName("renderTextScr");
    }
    return scr.font;
  }
  public static void setFont(TTFont paramTTFont) {
    scr.font = paramTTFont;
  }
  public static void setFont(String paramString) {
    scr.font = TTFont.get(paramString);
  }

  public static void setColor(Color4f paramColor4f) {
    scr.color = new Color4f(paramColor4f);
  }
  public static Color4f color() {
    return new Color4f(scr.color);
  }

  private TextScr() {
    super(-1.0F);
    useClearDepth(false);
    useClearColor(false);
  }

  protected void contextResize(int paramInt1, int paramInt2)
  {
    super.contextResize(paramInt1, paramInt2);
    this.context.clear();
  }
  public static TextScr This() {
    return scr;
  }
}