package com.maddox.gwindow;

public class GTexRegion extends GRegion
{
  public GTexture texture;

  public void set(GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.dx = paramFloat3;
    this.dy = paramFloat4;
    this.texture = paramGTexture;
  }

  public void set(GTexture paramGTexture, GPoint paramGPoint, GSize paramGSize) {
    super.set(paramGPoint, paramGSize);
    this.texture = paramGTexture;
  }

  public void set(GTexRegion paramGTexRegion) {
    super.set(paramGTexRegion);
    this.texture = paramGTexRegion.texture;
  }
  public GTexRegion() {
  }

  public GTexRegion(GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    set(paramGTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  public GTexRegion(GTexture paramGTexture, GPoint paramGPoint, GSize paramGSize) {
    set(paramGTexture, paramGPoint, paramGSize);
  }
  public GTexRegion(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    this.texture = GTexture.New(paramString);
  }
  public GTexRegion(String paramString, GPoint paramGPoint, GSize paramGSize) {
    set(paramGPoint, paramGSize);
    this.texture = GTexture.New(paramString);
  }
  public GTexRegion(GTexRegion paramGTexRegion) {
    set(paramGTexRegion);
  }
}