package com.maddox.gwindow;

public class GWindowVScrollBar extends GWindowDialogControl
{
  public float posMin = 0.0F;
  public float posVisible = 0.2F;
  public float posMax = 1.0F - this.posVisible;
  public float pos = (this.posMax - this.posMin) / 2.0F;
  public float scroll = 0.1F;
  public UButton uButton;
  public DButton dButton;
  public MButton mButton;
  public static final int DOWN_NONE = 0;
  public static final int DOWN_MIN = 1;
  public static final int DOWN_MAX = 2;
  public int downState = 0;
  public float yM;
  public float dyM;
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
    if (bool != this.jdField_bEnable_of_type_Boolean)
      _setEnable(bool);
    return this.jdField_bEnable_of_type_Boolean;
  }

  public void _setEnable(boolean paramBoolean) {
    super.setEnable(paramBoolean);
    this.uButton.setEnable(paramBoolean);
    this.mButton.setEnable(paramBoolean);
    this.dButton.setEnable(paramBoolean);
  }

  public void setEnable(boolean paramBoolean) {
    if (paramBoolean != this.jdField_bEnable_of_type_Boolean) {
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
    case 38:
      if ((paramBoolean) && (this.jdField_bEnable_of_type_Boolean)) {
        this.timeoutScroll = (-this.scroll);
        scroll(this.timeoutScroll);
      }
      return;
    case 40:
      if ((paramBoolean) && (this.jdField_bEnable_of_type_Boolean)) {
        this.timeoutScroll = this.scroll;
        scroll(this.timeoutScroll);
      }
      return;
    case 33:
      if ((paramBoolean) && (this.jdField_bEnable_of_type_Boolean)) {
        this.timeoutScroll = (-(this.posVisible - this.scroll));
        scroll(this.timeoutScroll);
      }
      return;
    case 34:
      if ((paramBoolean) && (this.jdField_bEnable_of_type_Boolean)) {
        this.timeoutScroll = (this.posVisible - this.scroll);
        scroll(this.timeoutScroll);
      }
      return;
    case 36:
      if ((paramBoolean) && (this.jdField_bEnable_of_type_Boolean)) setPos(this.posMin, true);
      this.bKeyDown = false;
      return;
    case 35:
      if ((paramBoolean) && (this.jdField_bEnable_of_type_Boolean)) setPos(this.posMax, true);
      this.bKeyDown = false;
      return;
    case 37:
    case 39:
    }
    this.bKeyDown = false;
    super.keyboardKey(paramInt, paramBoolean);
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if ((paramInt != 0) || (!this.jdField_bEnable_of_type_Boolean)) return;
    if (!paramBoolean) { this.downState = 0; return; }
    this.downState = (paramFloat2 <= this.yM ? 1 : 2);
    this.timeoutScroll = (this.downState == 1 ? -(this.posVisible - this.scroll) : this.posVisible - this.scroll);
    scroll(this.timeoutScroll);
    this.timeout = 0.5F;
  }

  public void scrollDz(float paramFloat) {
    if (paramFloat == 0.0F) return;
    scroll(-this.scroll * paramFloat / 5.0F);
  }

  public boolean notify(int paramInt1, int paramInt2) {
    if (paramInt1 == 17) {
      scrollDz(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseRelMoveZ);
      return true;
    }
    return super.notify(paramInt1, paramInt2);
  }

  public void preRender() {
    super.preRender();
    if ((this.jdField_bEnable_of_type_Boolean) && ((this.bDown) || (this.bKeyDown))) {
      this.timeout -= this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec;
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
    lookAndFeel().setupVScrollBarSizes(this);
  }

  public void created() {
    super.created();
  }

  public void afterCreated() {
    super.afterCreated();
    this.uButton = new UButton(this);
    this.mButton = new MButton(this);
    this.dButton = new DButton(this);
    resized();
  }

  public void resolutionChanged() {
    boolean bool = this.jdField_bEnable_of_type_Boolean;
    resized();
    if (!bool) setEnable(false); 
  }

  public GWindowVScrollBar(GWindow paramGWindow)
  {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowVScrollBar(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    this.posMin = paramFloat1;
    this.posVisible = paramFloat3;
    this.posMax = (paramFloat2 - paramFloat3);
    this.pos = ((this.posMax + this.posMin) / 2.0F);
    this.scroll = paramFloat4;
    float f = paramGWindow.lookAndFeel().getVScrollBarW() / paramGWindow.lookAndFeel().metric();
    doNew(paramGWindow, paramFloat5, paramFloat6, f, paramFloat7, true);
  }

  public class MButton extends GWindowButton
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if (paramInt != 0) return;
      if (!this.jdField_bEnable_of_type_Boolean) { mouseCapture(false); return; }
      mouseCapture(paramBoolean);
    }
    public void mouseMove(float paramFloat1, float paramFloat2) {
      if ((this.jdField_bEnable_of_type_Boolean) && (isMouseCaptured())) {
        GWindowVScrollBar localGWindowVScrollBar = (GWindowVScrollBar)this.parentWindow;
        GWindowVScrollBar.this.scroll(this.root.mouseStep.dy * (GWindowVScrollBar.this.posMax - GWindowVScrollBar.this.posMin) / (localGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGWindowVScrollBar.uButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGWindowVScrollBar.uButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy));
      }
    }

    public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
      super.mouseRelMove(paramFloat1, paramFloat2, paramFloat3);
      GWindowVScrollBar.this.scrollDz(paramFloat3);
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

  public class DButton extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.jdField_bEnable_of_type_Boolean)) return;
      GWindowVScrollBar.this.scroll(GWindowVScrollBar.this.scroll);
      GWindowVScrollBar.access$002(GWindowVScrollBar.this, 0.5F);
    }
    public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
      super.mouseRelMove(paramFloat1, paramFloat2, paramFloat3);
      GWindowVScrollBar.this.scrollDz(paramFloat3);
    }
    public void preRender() {
      super.preRender();
      if ((this.bDown) && (this.jdField_bEnable_of_type_Boolean)) {
        GWindowVScrollBar.access$024(GWindowVScrollBar.this, this.root.deltaTimeSec);
        if (GWindowVScrollBar.this.timeout <= 0.0F) {
          GWindowVScrollBar.access$002(GWindowVScrollBar.this, 0.1F);
          GWindowVScrollBar.this.scroll(GWindowVScrollBar.this.scroll);
        }
      }
    }

    public void created() {
      this.bAcceptsKeyFocus = false;
      lookAndFeel().setupScrollButtonDOWN(this);
    }
    public DButton(GWindow arg2) { super();
    }
  }

  public class UButton extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.jdField_bEnable_of_type_Boolean)) return;
      GWindowVScrollBar.this.scroll(-GWindowVScrollBar.this.scroll);
      GWindowVScrollBar.access$002(GWindowVScrollBar.this, 0.5F);
    }
    public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
      super.mouseRelMove(paramFloat1, paramFloat2, paramFloat3);
      GWindowVScrollBar.this.scrollDz(paramFloat3);
    }
    public void preRender() {
      super.preRender();
      if ((this.bDown) && (this.jdField_bEnable_of_type_Boolean)) {
        GWindowVScrollBar.access$024(GWindowVScrollBar.this, this.root.deltaTimeSec);
        if (GWindowVScrollBar.this.timeout <= 0.0F) {
          GWindowVScrollBar.access$002(GWindowVScrollBar.this, 0.1F);
          GWindowVScrollBar.this.scroll(-GWindowVScrollBar.this.scroll);
        }
      }
    }

    public void created() {
      this.bAcceptsKeyFocus = false;
      lookAndFeel().setupScrollButtonUP(this);
    }
    public UButton(GWindow arg2) { super();
    }
  }
}