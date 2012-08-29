package com.maddox.gwindow;

public class GWindowHSliderInt extends GWindowDialogControl
{
  public int posStart = 0;
  public int posCount = 8;
  public int pos = this.posStart + this.posCount / 2;
  public boolean[] posEnable;
  public boolean bSlidingNotify = false;
  private int posSlidingSave;
  private float mouseSlidingX;
  public float xM;
  public float dxM;

  public int pos()
  {
    return this.pos;
  }
  public boolean setPos(int paramInt, boolean paramBoolean) {
    int i = this.pos;
    this.pos = paramInt;
    resized();
    if (i != this.pos) {
      if (paramBoolean)
        notify(2, 0);
      return true;
    }
    return false;
  }

  public void setRange(int paramInt1, int paramInt2, int paramInt3) {
    this.posStart = paramInt1;
    this.posCount = paramInt2;
    this.pos = paramInt3;
    resized();
  }

  public void checkRange() {
    if (this.pos < this.posStart) this.pos = this.posStart;
    if (this.pos >= this.posStart + this.posCount) this.pos = (this.posStart + this.posCount - 1);
    if (this.posEnable != null)
      for (int i = 0; i < this.posCount; i++) {
        if (this.posEnable[(this.pos - this.posStart)] != 0)
          return;
        if (this.pos == this.posStart)
          return;
        this.pos -= 1;
      }
  }

  public void keyboardKey(int paramInt, boolean paramBoolean)
  {
    switch (paramInt) {
    case 37:
      if ((paramBoolean) && (this.bEnable))
        setPos(this.pos - 1, true);
      return;
    case 39:
      if ((paramBoolean) && (this.bEnable))
        setPos(this.pos + 1, true);
      return;
    case 36:
      if ((paramBoolean) && (this.bEnable))
        setPos(this.posStart, true);
      return;
    case 35:
      if ((paramBoolean) && (this.bEnable))
        setPos(this.posStart + this.posCount - 1, true);
      return;
    case 38:
    }

    super.keyboardKey(paramInt, paramBoolean);
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if (!this.bEnable) { mouseCapture(false); return; }
    if (paramInt != 0) return;
    if ((isMouseCaptured()) && (!paramBoolean)) {
      mouseCapture(false);
      if ((!this.bSlidingNotify) && 
        (this.posSlidingSave != this.pos) && (this.bNotify)) {
        notify(2, 0);
      }
      return;
    }
    if (paramFloat1 < this.xM) {
      if (paramBoolean)
        setPos(this.pos - 1, true);
    } else if (paramFloat1 > this.xM + this.dxM) {
      if (paramBoolean)
        setPos(this.pos + 1, true);
    }
    else if (paramBoolean) {
      mouseCapture(true);
      this.posSlidingSave = this.pos;
      this.mouseSlidingX = paramFloat1;
    }
  }

  public void mouseMove(float paramFloat1, float paramFloat2)
  {
    if ((this.bEnable) && (isMouseCaptured()))
      setPos((int)((paramFloat1 - this.mouseSlidingX) / this.win.dx * this.posCount + this.posSlidingSave + 0.5F), this.bSlidingNotify);
  }

  public void render() {
    lookAndFeel().render(this);
  }

  public void resized() {
    checkRange();
    lookAndFeel().setupHSliderIntSizes(this);
  }

  public void created() {
    super.created();
    resized();
  }

  public GWindowHSliderInt(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowHSliderInt(GWindow paramGWindow, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.posStart = paramInt1;
    this.posCount = paramInt2;
    this.pos = paramInt3;
    float f = paramGWindow.lookAndFeel().getHSliderIntH() / paramGWindow.lookAndFeel().metric();
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, f, true);
  }
}