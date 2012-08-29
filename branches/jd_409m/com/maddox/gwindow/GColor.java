package com.maddox.gwindow;

public class GColor
{
  public static final GColor Brass = new GColor(199, 179, 148);
  public static final GColor White = new GColor(255, 255, 255);
  public static final GColor Line = new GColor(168, 164, 0);
  public static final GColor Gray = new GColor(207, 208, 208);
  public static final GColor Black = new GColor(0, 0, 0);
  public static final GColor Red = new GColor(255, 0, 0);
  public static final GColor Green = new GColor(0, 255, 0);
  public static final GColor Blue = new GColor(0, 0, 255);
  public static final GColor Yellow = new GColor(255, 255, 0);
  public static final int WHITE = 16777215;
  public static final int BLACK = 0;
  public int color = 16777215;

  public int r() { return this.color & 0xFF; } 
  public int g() { return this.color >> 8 & 0xFF; } 
  public int b() { return this.color >> 16 & 0xFF; }

  public void get(GColor paramGColor) {
    paramGColor.color = this.color;
  }
  public void setWhite() {
    this.color = 16777215;
  }
  public void setBlack() {
    this.color = 0;
  }
  public void set(int paramInt1, int paramInt2, int paramInt3) {
    this.color = (paramInt1 & 0xFF | (paramInt2 & 0xFF) << 8 | (paramInt3 & 0xFF) << 16);
  }
  public void set(GColor paramGColor) {
    this.color = paramGColor.color;
  }

  public GColor(int paramInt1, int paramInt2, int paramInt3) {
    set(paramInt1, paramInt2, paramInt3);
  }
  public GColor(GColor paramGColor) {
    set(paramGColor);
  }

  public GColor()
  {
  }
}