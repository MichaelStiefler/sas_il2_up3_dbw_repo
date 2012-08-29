package com.maddox.gwindow;

public class GWindowFramed extends GWindow
{
  public String title;
  public GWindowMenuBar menuBar;
  public GWindow clientWindow;
  public GWindowFrameCloseBox closeBox;
  public boolean bMovable = true;
  public boolean bSizable = true;
  public static final int SIZING_NONE = 0;
  public static final int SIZING_MOVE = 1;
  public static final int SIZING_TL = 2;
  public static final int SIZING_T = 3;
  public static final int SIZING_TR = 4;
  public static final int SIZING_L = 5;
  public static final int SIZING_R = 6;
  public static final int SIZING_BL = 7;
  public static final int SIZING_B = 8;
  public static final int SIZING_BR = 9;
  public int sizingState = 0;

  private static GSize _newSize = new GSize();

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
    if (paramInt != 0) return;
    if (paramBoolean) {
      if (isMouseCaptured())
        return;
      int i = lookAndFeel().frameHitTest(this, paramFloat1, paramFloat2);
      if (i == 0) return;
      if (i == 9) {
        if (!this.bMovable) return;
        this.sizingState = 1;
        mouseCapture(true);
        this.jdField_mouseCursor_of_type_Int = 3;
        return;
      }
      if (!this.bSizable) return;
      switch (i) {
      case 1:
        this.sizingState = 2;
        this.jdField_mouseCursor_of_type_Int = 10;
        break;
      case 2:
        this.sizingState = 3;
        this.jdField_mouseCursor_of_type_Int = 9;
        break;
      case 3:
        this.sizingState = 4;
        this.jdField_mouseCursor_of_type_Int = 8;
        break;
      case 4:
        this.sizingState = 5;
        this.jdField_mouseCursor_of_type_Int = 11;
        break;
      case 5:
        this.sizingState = 6;
        this.jdField_mouseCursor_of_type_Int = 11;
        break;
      case 6:
        this.sizingState = 7;
        this.jdField_mouseCursor_of_type_Int = 8;
        break;
      case 7:
        this.sizingState = 8;
        this.jdField_mouseCursor_of_type_Int = 9;
        break;
      case 8:
        this.sizingState = 9;
        this.jdField_mouseCursor_of_type_Int = 10;
        break;
      default:
        return;
      }
      mouseCapture(true);
    }
    else if (isMouseCaptured()) {
      this.sizingState = 0;
      mouseCapture(false);
      this.jdField_mouseCursor_of_type_Int = 1;
    }
  }

  public void mouseMove(float paramFloat1, float paramFloat2)
  {
    super.mouseMove(paramFloat1, paramFloat2);
    GRegion localGRegion = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.getClientRegion();
    if ((this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.x < localGRegion.x) || (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.x >= localGRegion.x + localGRegion.dx) || (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.y < localGRegion.y) || (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mousePos.y >= localGRegion.y + localGRegion.dy))
    {
      return;
    }
    GSize localGSize = null;
    if ((this.sizingState != 1) && (this.sizingState != 0)) {
      _newSize.set(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy); localGSize = getMinSize();
    }
    switch (this.sizingState) {
    case 1:
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      return;
    case 0:
      int i = lookAndFeel().frameHitTest(this, paramFloat1, paramFloat2);
      this.jdField_mouseCursor_of_type_Int = 1;
      if (i == 0) return;
      if (i == 9) {
        if (!this.bMovable) return;
        this.jdField_mouseCursor_of_type_Int = 3;
        return;
      }
      if (!this.bSizable) return;
      switch (i) { case 1:
        this.jdField_mouseCursor_of_type_Int = 10; break;
      case 2:
        this.jdField_mouseCursor_of_type_Int = 9; break;
      case 3:
        this.jdField_mouseCursor_of_type_Int = 8; break;
      case 4:
        this.jdField_mouseCursor_of_type_Int = 11; break;
      case 5:
        this.jdField_mouseCursor_of_type_Int = 11; break;
      case 6:
        this.jdField_mouseCursor_of_type_Int = 8; break;
      case 7:
        this.jdField_mouseCursor_of_type_Int = 9; break;
      case 8:
        this.jdField_mouseCursor_of_type_Int = 10; break;
      }

      return;
    case 2:
      _newSize.add(-this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 3:
      _newSize.add(0.0F, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if (_newSize.dy < localGSize.dy) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 4:
      _newSize.add(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 5:
      _newSize.add(-this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, 0.0F);
      if (_newSize.dx < localGSize.dx) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y);
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 6:
      _newSize.add(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, 0.0F);
      if (_newSize.dx < localGSize.dx) break;
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 7:
      _newSize.add(-this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.x + this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.y);
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 8:
      _newSize.add(0.0F, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if (_newSize.dy < localGSize.dy) break;
      setSize(_newSize.dx, _newSize.dy);
      return;
    case 9:
      _newSize.add(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy);
      if ((_newSize.dx < localGSize.dx) || (_newSize.dy < localGSize.dy)) break;
      setSize(_newSize.dx, _newSize.dy);
      return;
    }

    this.sizingState = 0;
    mouseCapture(false);
    this.jdField_mouseCursor_of_type_Int = 1;
  }

  public void resized()
  {
    super.resized();
    GSize localGSize = getMinSize();
    if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx < localGSize.dx) this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = localGSize.dx;
    if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy < localGSize.dy) this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = localGSize.dy;
    if (this.menuBar != null) {
      float f1 = this.menuBar.getMinSize().dy;
      this.menuBar.setSize(getClientRegion().dx, f1);
      GRegion localGRegion2 = getClientRegion();
      this.menuBar.setPos(localGRegion2.x, localGRegion2.y - f1);
    }
    if (this.clientWindow != null) {
      GRegion localGRegion1 = getClientRegion();
      float f2 = localGRegion1.dx;
      float f3 = localGRegion1.dy;
      this.clientWindow.setPos(localGRegion1.x, localGRegion1.y);
      this.clientWindow.setSize(f2, f3);
    }
    if (this.closeBox != null)
      lookAndFeel().frameSetCloseBoxPos(this);
  }

  public void resolutionChanged() {
    if (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot == this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow)
      clampWin(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.getClientRegion());
    super.resolutionChanged();
  }

  public void clampWin(GRegion paramGRegion) {
    float f1 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.x;
    float f2 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.y;
    float f3 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    float f4 = this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    if (f1 + f3 > paramGRegion.x + paramGRegion.dx)
      f1 = paramGRegion.x + paramGRegion.dx - f3;
    if (f2 + f4 > paramGRegion.y + paramGRegion.dy)
      f2 = paramGRegion.y + paramGRegion.dy - f4;
    if (f1 < paramGRegion.x) {
      f1 = paramGRegion.x;
      if (f1 + f3 > paramGRegion.x + paramGRegion.dx)
        f3 = paramGRegion.x + paramGRegion.dx - f1;
    }
    if (f2 < paramGRegion.y) {
      f2 = paramGRegion.y;
      if (f2 + f4 > paramGRegion.y + paramGRegion.dy)
        f4 = paramGRegion.y + paramGRegion.dy - f2;
    }
    setPos(f1, f2);
    setSize(f3, f4);
  }

  public void created() {
    super.created();
    if (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot == this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow)
      clampWin(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.getClientRegion());
    resized();
  }

  public void afterCreated() {
    this.closeBox = ((GWindowFrameCloseBox)create(new GWindowFrameCloseBox()));
    lookAndFeel().frameSetCloseBoxPos(this);
  }

  public void render() {
    lookAndFeel().render(this);
  }
  public GSize getMinSize(GSize paramGSize) {
    return lookAndFeel().getMinSize(this, paramGSize);
  }
  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    return lookAndFeel().getClientRegion(this, paramGRegion, paramFloat);
  }
}