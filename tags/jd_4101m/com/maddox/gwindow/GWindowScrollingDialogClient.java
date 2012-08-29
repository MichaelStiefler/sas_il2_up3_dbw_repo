package com.maddox.gwindow;

public class GWindowScrollingDialogClient extends GWindowDialogClient
{
  public GWindowDialogClient fixed;
  public GWindowVScrollBar vScroll;
  public GWindowHScrollBar hScroll;
  public GWindowButton button;

  public void updateScrollsPos()
  {
    if (this.vScroll.isVisible()) this.vScroll.setPos(-this.fixed.win.y, false);
    if (this.hScroll.isVisible()) this.hScroll.setPos(-this.fixed.win.x, false); 
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if (paramInt != 0) return;
    mousePressed(paramBoolean);
  }
  public void mouseMove(float paramFloat1, float paramFloat2) {
    super.mouseMove(paramFloat1, paramFloat2);
    mouseMoved();
  }

  public void mousePressed(boolean paramBoolean) {
    if ((!this.vScroll.isVisible()) && (!this.hScroll.isVisible())) return;
    mouseCapture(paramBoolean);
  }

  public void mouseMoved() {
    if (this.fixed == null) return;
    if (isMouseCaptured()) {
      this.mouseCursor = 7;
      float f1 = this.fixed.win.x;
      float f2 = this.fixed.win.y;
      float f3;
      if (this.vScroll.isVisible()) {
        f2 += this.root.mouseStep.dy;
        if (f2 > 0.0F) f2 = 0.0F;
        f3 = this.win.dy;
        if (this.hScroll.isVisible()) f3 -= lookAndFeel().getHScrollBarH();
        if (f2 + this.fixed.win.dy < f3) f2 = f3 - this.fixed.win.dy;
      }
      if (this.hScroll.isVisible()) {
        f1 += this.root.mouseStep.dx;
        if (f1 > 0.0F) f1 = 0.0F;
        f3 = this.win.dx;
        if (this.vScroll.isVisible()) f3 -= lookAndFeel().getVScrollBarW();
        if (f1 + this.fixed.win.dx < f3) f1 = f3 - this.fixed.win.dx;
      }
      this.fixed.setPos(f1, f2);
      updateScrollsPos();
    }
    else if ((this.vScroll.isVisible()) || (this.hScroll.isVisible())) {
      this.mouseCursor = 3;
      this.fixed.mouseCursor = 3;
    } else {
      this.mouseCursor = 1;
      this.fixed.mouseCursor = 1;
    }
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 2) {
      if (paramGWindow == this.vScroll) {
        if (this.fixed != null) {
          this.fixed.setPos(this.fixed.win.x, -this.vScroll.pos());
          updateScrollsPos();
        }
        return true;
      }
      if (paramGWindow == this.hScroll) {
        if (this.fixed != null) {
          this.fixed.setPos(-this.hScroll.pos(), this.fixed.win.y);
          updateScrollsPos();
        }
        return true;
      }
    }
    if (paramInt1 == 17) {
      if ((this.vScroll.isVisible()) && (this.fixed != null))
        this.vScroll.scrollDz(this.root.mouseRelMoveZ);
      return true;
    }
    if (paramGWindow == this.fixed) {
      switch (paramInt1) {
      case 8:
        mouseMoved();
        return true;
      case 3:
        if (paramInt2 != 0) break;
        mousePressed(true);
        return true;
      case 4:
        if (paramInt2 != 0) break;
        mousePressed(false);
        return true;
      }

    }

    return super.notify(paramGWindow, paramInt1, paramInt2);
  }

  public void resized() {
    if (this.fixed == null) return;
    int i = 0;
    int j = 0;
    float f1 = (int)(this.win.dx + 0.5F);
    float f2 = (int)(this.win.dy + 0.5F);
    for (int k = 0; k < 2; k++) {
      f1 = this.win.dx;
      if (j != 0) f1 -= lookAndFeel().getVScrollBarW();
      f2 = this.win.dy;
      if (i != 0) f2 -= lookAndFeel().getHScrollBarH();
      i = this.fixed.win.dx > f1 ? 1 : 0;
      j = this.fixed.win.dy > f2 ? 1 : 0;
    }
    float f3 = this.fixed.win.x;
    float f4 = this.fixed.win.y;
    if (j != 0) {
      this.vScroll.setPos(this.win.dx - lookAndFeel().getVScrollBarW(), 0.0F);
      this.vScroll.setSize(lookAndFeel().getVScrollBarW(), f2);
      if (f4 + this.fixed.win.dy < f2)
        f4 = f2 - this.fixed.win.dy;
      this.vScroll.setRange(0.0F, this.fixed.win.dy, f2, lookAndFeel().metric(), -f4);
      this.vScroll.showWindow();
    } else {
      this.vScroll.hideWindow();
      f4 = 0.0F;
    }
    if (i != 0) {
      this.hScroll.setPos(0.0F, this.win.dy - lookAndFeel().getHScrollBarH());
      this.hScroll.setSize(f1, lookAndFeel().getHScrollBarH());
      if (f3 + this.fixed.win.dx < f1)
        f3 = f1 - this.fixed.win.dx;
      this.hScroll.setRange(0.0F, this.fixed.win.dx, f1, lookAndFeel().metric(), -f3);
      this.hScroll.showWindow();
    } else {
      this.hScroll.hideWindow();
      f3 = 0.0F;
    }
    this.fixed.setPos(f3, f4);
    if ((i != 0) && (j != 0)) {
      this.button.setPos(f1, f2);
      this.button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
      this.button.showWindow();
    } else {
      this.button.hideWindow();
    }
  }

  public GSize getMinSize(GSize paramGSize) {
    if (this.fixed != null)
      return this.fixed.getMinSize(paramGSize);
    return super.getMinSize(paramGSize);
  }

  public void afterCreated()
  {
    super.afterCreated();
    this.hScroll = new GWindowHScrollBar(this);
    this.vScroll = new GWindowVScrollBar(this);
    this.hScroll.bAlwaysOnTop = true;
    this.vScroll.bAlwaysOnTop = true;
    this.hScroll.hideWindow();
    this.vScroll.hideWindow();
    this.button = new GWindowButton(this);
    this.button.bAcceptsKeyFocus = false;
    this.button.bAlwaysOnTop = true;
    this.button.bDrawOnlyUP = true;
    this.button.bDrawActive = false;
    this.button.hideWindow();

    resized();
  }
  public GWindowScrollingDialogClient() {
  }

  public GWindowScrollingDialogClient(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }
}