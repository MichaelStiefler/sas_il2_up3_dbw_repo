package com.maddox.gwindow;

public class GCursorTexRegion extends GCursor
{
  public GTexture tex;
  public float x;
  public float y;
  public float dx;
  public float dy;
  public float hotX;
  public float hotY;

  public void preRender(GWindowRoot paramGWindowRoot)
  {
    paramGWindowRoot.C.cur.x = (paramGWindowRoot.mousePos.x - this.hotX);
    paramGWindowRoot.C.cur.y = (paramGWindowRoot.mousePos.y - this.hotY);
    paramGWindowRoot.C.preRender(this.tex, this.dx, this.dy);
  }

  public void render(GWindowRoot paramGWindowRoot)
  {
    paramGWindowRoot.C.cur.x = (paramGWindowRoot.mousePos.x - this.hotX);
    paramGWindowRoot.C.cur.y = (paramGWindowRoot.mousePos.y - this.hotY);
    paramGWindowRoot.C.color.setWhite();
    paramGWindowRoot.C.draw(this.tex, this.dx, this.dy, this.x, this.y, this.dx, this.dy);
  }

  public GCursorTexRegion(GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
  {
    this.tex = paramGTexture;
    this.hotX = paramFloat5;
    this.hotY = paramFloat6;
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.dx = paramFloat3;
    this.dy = paramFloat4;
    this.nativeCursor = paramInt;
  }

  public GCursorTexRegion(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt) {
    this(GTexture.New(paramString), paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt);
  }
}