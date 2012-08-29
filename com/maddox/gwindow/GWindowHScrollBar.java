package com.maddox.gwindow;

public class GWindowHScrollBar extends GWindowDialogControl
{
  public float posMin = 0.0F;
  public float posVisible = 0.2F;
  public float posMax = 1.0F - this.posVisible;
  public float pos = (this.posMax - this.posMin) / 2.0F;
  public float scroll = 0.1F;
  public LButton lButton;
  public RButton rButton;
  public MButton mButton;
  public static final int DOWN_NONE = 0;
  public static final int DOWN_MIN = 1;
  public static final int DOWN_MAX = 2;
  public int downState = 0;
  public float xM;
  public float dxM;
  private float timeout = 0.0F;
  private float timeoutScroll = 0.0F;
  public boolean bKeyDown = false;

  public float pos() { return this.pos; }

  public boolean scroll(float paramFloat) {
    return setPos(this.pos + paramFloat, true);
  }

  public boolean setPos(float paramFloat, boolean paramBoolean) {
    float f = this.pos;
    this.pos = paramFloat;
    resized();
    if (f != this.pos) {
      if (paramBoolean)
        notify(2, 0);
      return true;
    }
    return false;
  }

  public void setRange(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    this.posMin = paramFloat1;
    this.posMax = (paramFloat2 - paramFloat3);
    this.posVisible = paramFloat3;
    this.pos = paramFloat5;
    this.scroll = paramFloat4;
    resized();
  }

  public boolean checkRange() {
    if (this.pos > this.posMax) this.pos = this.posMax;
    if (this.pos < this.posMin) this.pos = this.posMin;
    boolean bool = this.posMin < this.posMax;
    if (bool != this.bEnable)
      _setEnable(bool);
    return this.bEnable;
  }

  public void _setEnable(boolean paramBoolean) {
    super.setEnable(paramBoolean);
    this.lButton.setEnable(paramBoolean);
    this.mButton.setEnable(paramBoolean);
    this.rButton.setEnable(paramBoolean);
  }

  public void setEnable(boolean paramBoolean) {
    if (paramBoolean != this.bEnable) {
      if ((!checkRange()) && (paramBoolean))
        return;
      _setEnable(paramBoolean);
    }
  }

  public void cancelAcceptsKeyFocus() {
    this.bAcceptsKeyFocus = false;
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    this.bKeyDown = paramBoolean;
    switch (paramInt) {
    case 37:
      if ((paramBoolean) && (this.bEnable)) {
        this.timeoutScroll = (-this.scroll);
        scroll(this.timeoutScroll);
      }
      return;
    case 39:
      if ((paramBoolean) && (this.bEnable)) {
        this.timeoutScroll = this.scroll;
        scroll(this.timeoutScroll);
      }
      return;
    case 33:
      if ((paramBoolean) && (this.bEnable)) {
        this.timeoutScroll = (-(this.posVisible - this.scroll));
        scroll(this.timeoutScroll);
      }
      return;
    case 34:
      if ((paramBoolean) && (this.bEnable)) {
        this.timeoutScroll = (this.posVisible - this.scroll);
        scroll(this.timeoutScroll);
      }
      return;
    case 36:
      if ((paramBoolean) && (this.bEnable)) setPos(this.posMin, true);
      this.bKeyDown = false;
      return;
    case 35:
      if ((paramBoolean) && (this.bEnable)) setPos(this.posMax, true);
      this.bKeyDown = false;
      return;
    case 38:
    }

    this.bKeyDown = false;
    super.keyboardKey(paramInt, paramBoolean);
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if ((paramInt != 0) || (!this.bEnable)) return;
    if (!paramBoolean) { this.downState = 0; return; }
    this.downState = (paramFloat1 <= this.xM ? 1 : 2);
    this.timeoutScroll = (this.downState == 1 ? -(this.posVisible - this.scroll) : this.posVisible - this.scroll);
    scroll(this.timeoutScroll);
    this.timeout = 0.5F;
  }
  public void preRender() {
    super.preRender();
    if ((this.bEnable) && ((this.bDown) || (this.bKeyDown))) {
      this.timeout -= this.root.deltaTimeSec;
      if (this.timeout <= 0.0F) {
        this.timeout = 0.1F;
        scroll(this.timeoutScroll);
      }
    }
  }

  public void render()
  {
    lookAndFeel().render(this);
  }

  public void resized() {
    checkRange();
    lookAndFeel().setupHScrollBarSizes(this);
  }

  public void created() {
    super.created();
  }

  public void afterCreated() {
    super.afterCreated();
    this.lButton = new LButton(this);
    this.mButton = new MButton(this);
    this.rButton = new RButton(this);
    resized();
  }

  public void resolutionChanged() {
    boolean bool = this.bEnable;
    resized();
    if (!bool) setEnable(false); 
  }

  public GWindowHScrollBar(GWindow paramGWindow)
  {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowHScrollBar(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    this.posMin = paramFloat1;
    this.posVisible = paramFloat3;
    this.posMax = (paramFloat2 - paramFloat3);
    this.pos = ((this.posMax + this.posMin) / 2.0F);
    this.scroll = paramFloat4;
    float f = paramGWindow.lookAndFeel().getHScrollBarH() / paramGWindow.lookAndFeel().metric();
    doNew(paramGWindow, paramFloat5, paramFloat6, paramFloat7, f, true);
  }

  public class MButton extends GWindowButton
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if (paramInt != 0) return;
      if (!this.bEnable) { mouseCapture(false); return; }
      mouseCapture(paramBoolean);
    }
    public void mouseMove(float paramFloat1, float paramFloat2) {
      if ((this.bEnable) && (isMouseCaptured())) {
        GWindowHScrollBar localGWindowHScrollBar = (GWindowHScrollBar)this.parentWindow;
        GWindowHScrollBar.this.scroll(this.root.mouseStep.dx * (GWindowHScrollBar.this.posMax - GWindowHScrollBar.this.posMin) / (localGWindowHScrollBar.win.dx - localGWindowHScrollBar.lButton.win.dx - localGWindowHScrollBar.lButton.win.dx - this.win.dx));
      }
    }

    public void created() {
      this.bAcceptsKeyFocus = false;
      this.bDrawOnlyUP = true;
      this.bDrawActive = false;
    }
    public MButton(GWindow arg2) {
      super();
    }
  }

  public class RButton extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;
      GWindowHScrollBar.this.scroll(GWindowHScrollBar.this.scroll);
      GWindowHScrollBar.access$002(GWindowHScrollBar.this, 0.5F);
    }
    public void preRender() {
      super.preRender();
      if ((this.bDown) && (this.bEnable)) {
        GWindowHScrollBar.access$024(GWindowHScrollBar.this, this.root.deltaTimeSec);
        if (GWindowHScrollBar.this.timeout <= 0.0F) {
          GWindowHScrollBar.access$002(GWindowHScrollBar.this, 0.1F);
          GWindowHScrollBar.this.scroll(GWindowHScrollBar.this.scroll);
        }
      }
    }

    public void created() {
      this.bAcceptsKeyFocus = false;
      lookAndFeel().setupScrollButtonRIGHT(this);
    }
    public RButton(GWindow arg2) { super();
    }
  }

  public class LButton extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;
      GWindowHScrollBar.this.scroll(-GWindowHScrollBar.this.scroll);
      GWindowHScrollBar.access$002(GWindowHScrollBar.this, 0.5F);
    }
    public void preRender() {
      super.preRender();
      if ((this.bDown) && (this.bEnable)) {
        GWindowHScrollBar.access$024(GWindowHScrollBar.this, this.root.deltaTimeSec);
        if (GWindowHScrollBar.this.timeout <= 0.0F) {
          GWindowHScrollBar.access$002(GWindowHScrollBar.this, 0.1F);
          GWindowHScrollBar.this.scroll(-GWindowHScrollBar.this.scroll);
        }
      }
    }

    public void created() {
      this.bAcceptsKeyFocus = false;
      lookAndFeel().setupScrollButtonLEFT(this);
    }
    public LButton(GWindow arg2) { super();
    }
  }
}