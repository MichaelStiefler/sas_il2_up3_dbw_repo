package com.maddox.gwindow;

public class GBevel
{
  public GRegion TL = new GRegion();
  public GRegion T = new GRegion();
  public GRegion TR = new GRegion();

  public GRegion L = new GRegion();
  public GRegion R = new GRegion();

  public GRegion BL = new GRegion();
  public GRegion B = new GRegion();
  public GRegion BR = new GRegion();
  public GRegion Area = new GRegion();

  public void set(GRegion paramGRegion1, GRegion paramGRegion2) {
    this.TL.x = paramGRegion1.x;
    this.TL.y = paramGRegion1.y;
    this.TL.dx = (paramGRegion2.x - paramGRegion1.x);
    this.TL.dy = (paramGRegion2.y - paramGRegion1.y);
    this.T.x = (paramGRegion1.x + this.TL.dx);
    this.T.y = paramGRegion1.y;
    this.T.dx = paramGRegion2.dx;
    this.T.dy = this.TL.dy;
    this.TR.x = (paramGRegion2.x + paramGRegion2.dx);
    this.TR.y = paramGRegion1.y;
    this.TR.dx = (paramGRegion1.dx - paramGRegion2.dx - this.TL.dx);
    this.TR.dy = this.TL.dy;
    this.L.x = paramGRegion1.x;
    this.L.y = (paramGRegion1.y + this.TL.dy);
    this.L.dx = this.TL.dx;
    this.L.dy = paramGRegion2.dy;
    this.R.x = this.TR.x;
    this.R.y = (paramGRegion1.y + this.TR.dy);
    this.R.dx = this.TR.dx;
    this.R.dy = paramGRegion2.dy;
    this.BL.x = paramGRegion1.x;
    this.BL.y = (paramGRegion2.y + paramGRegion2.dy);
    this.BL.dx = this.TL.dx;
    this.BL.dy = (paramGRegion1.dy - paramGRegion2.dy - this.TL.dy);
    this.B.x = (paramGRegion1.x + this.BL.dx);
    this.B.y = this.BL.y;
    this.B.dx = paramGRegion2.dx;
    this.B.dy = this.BL.dy;
    this.BR.x = this.TR.x;
    this.BR.y = this.BL.y;
    this.BR.dx = this.TR.dx;
    this.BR.dy = this.BL.dy;
    this.Area.set(paramGRegion2);
  }
}