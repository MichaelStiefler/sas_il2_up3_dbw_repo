package com.maddox.gwindow;

public abstract class GCanvas
{
  public GSize size;
  public GFont font;
  public GColor color = new GColor();

  public int alpha = 255;

  public GPoint cur = new GPoint();

  public GPoint org = new GPoint();
  public GRegion clip;

  public boolean preRender(GTexture paramGTexture, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public boolean draw(GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    return false;
  }
  public boolean preRender(GMesh paramGMesh, float paramFloat1, float paramFloat2) {
    return false;
  }

  public boolean draw(GMesh paramGMesh, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public boolean draw(String paramString)
  {
    return false; } 
  public boolean draw(String paramString, int paramInt1, int paramInt2) { return false; } 
  public boolean draw(char[] paramArrayOfChar, int paramInt1, int paramInt2) { return false; } 
  public void copyToClipboard(String paramString) {
  }
  public String pasteFromClipboard() { return ""; }

  protected GCanvas(GSize paramGSize) {
    this.size = new GSize(paramGSize);
    this.clip = new GRegion(0.0F, 0.0F, paramGSize.dx, paramGSize.dy);
  }
}