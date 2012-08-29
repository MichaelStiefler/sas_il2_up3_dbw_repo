package com.maddox.gwindow;

public class GCursorTexture extends GCursor
{
  public GTexture tex;
  public float hotX;
  public float hotY;

  public void preRender(GWindowRoot paramGWindowRoot)
  {
    paramGWindowRoot.C.cur.x = (paramGWindowRoot.mousePos.x - this.hotX);
    paramGWindowRoot.C.cur.y = (paramGWindowRoot.mousePos.y - this.hotY);
    paramGWindowRoot.C.preRender(this.tex, this.tex.size.dx, this.tex.size.dy);
  }

  public void render(GWindowRoot paramGWindowRoot)
  {
    paramGWindowRoot.C.cur.x = (paramGWindowRoot.mousePos.x - this.hotX);
    paramGWindowRoot.C.cur.y = (paramGWindowRoot.mousePos.y - this.hotY);
    paramGWindowRoot.C.color.setWhite();
    paramGWindowRoot.C.draw(this.tex, this.tex.size.dx, this.tex.size.dy, 0.0F, 0.0F, this.tex.size.dx, this.tex.size.dy);
  }

  public GCursorTexture(GTexture paramGTexture, float paramFloat1, float paramFloat2, int paramInt) {
    this.tex = paramGTexture;
    this.hotX = paramFloat1;
    this.hotY = paramFloat2;
    this.nativeCursor = paramInt;
  }
  public GCursorTexture(String paramString, float paramFloat1, float paramFloat2, int paramInt) {
    this(GTexture.New(paramString), paramFloat1, paramFloat2, paramInt);
  }
}