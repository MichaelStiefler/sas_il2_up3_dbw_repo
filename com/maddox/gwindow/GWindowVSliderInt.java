package com.maddox.gwindow;

public class GWindowVSliderInt extends GWindowDialogControl
{
  public int posStart = 0;
  public int posCount = 8;
  public int pos = this.posStart + this.posCount / 2;
  public boolean[] posEnable;
  public boolean bSlidingNotify = false;
  private int posSlidingSave;
  private float mouseSlidingY;
  public float yM;
  public float dyM;

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

  public void setScrollPos(boolean paramBoolean1, boolean paramBoolean2) {
    setPos(this.pos + (paramBoolean1 ? 1 : -1), paramBoolean2);
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
    case 40:
      if ((paramBoolean) && (this.bEnable))
        setScrollPos(false, true);
      return;
    case 38:
      if ((paramBoolean) && (this.bEnable))
        setScrollPos(true, true);
      return;
    case 35:
      if ((paramBoolean) && (this.bEnable))
        setPos(this.posStart, true);
      return;
    case 36:
      if ((paramBoolean) && (this.bEnable))
        setPos(this.posStart + this.posCount - 1, true);
      return;
    case 37:
    case 39:
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
    if (paramFloat2 < this.yM) {
      if (paramBoolean)
        setScrollPos(true, true);
    } else if (paramFloat2 > this.yM + this.dyM) {
      if (paramBoolean)
        setScrollPos(false, true);
    }
    else if (paramBoolean) {
      mouseCapture(true);
      this.posSlidingSave = this.pos;
      this.mouseSlidingY = paramFloat2;
    }
  }

  public void mouseMove(float paramFloat1, float paramFloat2)
  {
    if ((this.bEnable) && (isMouseCaptured()))
      setPos((int)((this.mouseSlidingY - paramFloat2) / this.win.dy * this.posCount + this.posSlidingSave + 0.5F), this.bSlidingNotify); 
  }

  public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
    if ((this.bEnable) && (!isMouseCaptured()) && (this.root.mouseRelMoveZ != 0.0F))
      setScrollPos(this.root.mouseRelMoveZ > 0.0F, true);
  }

  public void render()
  {
    lookAndFeel().render(this);
  }

  public void resized() {
    checkRange();
    lookAndFeel().setupVSliderIntSizes(this);
  }

  public void created() {
    super.created();
    resized();
  }

  public GWindowVSliderInt(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowVSliderInt(GWindow paramGWindow, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.posStart = paramInt1;
    this.posCount = paramInt2;
    this.pos = paramInt3;
    float f = paramGWindow.lookAndFeel().getVSliderIntW() / paramGWindow.lookAndFeel().metric();
    doNew(paramGWindow, paramFloat1, paramFloat2, f, paramFloat3, true);
  }
}