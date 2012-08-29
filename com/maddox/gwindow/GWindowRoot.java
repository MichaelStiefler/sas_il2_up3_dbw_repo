package com.maddox.gwindow;

import com.maddox.rts.Mouse;
import java.util.ArrayList;

public class GWindowRoot extends GWindow
{
  public static long TIME_DOUBLE_CLICK = 600L;

  public boolean bInRender = false;

  public float deltaTimeSec = 0.0F;
  public GCanvas C;
  public GWindowLookAndFeel lookAndFeel;
  public GFont[] textFonts = new GFont[8];

  public GCursor[] mouseCursors = new GCursor[16];
  public GCursor mouseOverCursor;
  public GWindowManager manager;
  public GWindow modalWindow;
  public boolean bMouseCapture = false;
  public GWindow mouseWindow;
  public GWindow mouseOldWindow;
  public GPoint mousePos = new GPoint();
  public float mousePosZ = 0.0F;

  public GPoint mouseOldPos = new GPoint();
  public float mouseOldPosZ = 0.0F;

  public GSize mouseStep = new GSize();
  public float mouseStepZ = 0.0F;

  public GSize mouseRelMove = new GSize();
  public float mouseRelMoveZ = 0.0F;

  public GWindow[] mouseWindowDown = new GWindow[7];

  protected GWindow[] mouseWindowUp = new GWindow[7];
  protected long[] mouseTimeUp = new long[7];
  public GWindow keyFocusWindow;
  protected boolean bDoCheckKeyFocusWindow = false;
  private GWindow _findWindowUnder;
  private GPoint _findTest = new GPoint();
  private GPoint _findOrg = new GPoint();
  private GRegion _findClip = new GRegion();

  protected ArrayList mouseListeners = new ArrayList();

  protected ArrayList keyListeners = new ArrayList();

  public final GWindow findWindowUnder(float paramFloat1, float paramFloat2)
  {
    this._findWindowUnder = this;
    this._findTest.set(paramFloat1, paramFloat2);
    this._findOrg.set(0.0F, 0.0F);
    this._findClip.set(this.jdField_win_of_type_ComMaddoxGwindowGRegion);
    findWindowUnder(this, paramFloat1, paramFloat2);
    return this._findWindowUnder;
  }

  private void findWindowUnder(GWindow paramGWindow, float paramFloat1, float paramFloat2) {
    GPoint localGPoint = this._findOrg;
    GRegion localGRegion = this._findClip;
    float f1 = localGPoint.x;
    float f2 = localGPoint.y;
    localGPoint.add(paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.x, paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.y);
    paramFloat1 -= paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.x; paramFloat2 -= paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.y;
    float f3 = localGRegion.x;
    float f4 = localGRegion.y;
    float f5 = localGRegion.dx;
    float f6 = localGRegion.dy;

    if (paramGWindow.bClip) {
      float f7 = localGRegion.x - localGPoint.x;
      if (f7 < 0.0F) {
        localGRegion.dx += f7; if (localGRegion.dx <= 0.0F) break label575; localGRegion.x = localGPoint.x;
        f7 = 0.0F;
      }
      f7 = localGRegion.dx + f7 - paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
      if (f7 > 0.0F) {
        localGRegion.dx -= f7; if (localGRegion.dx <= 0.0F) break label575; 
      }
      f7 = localGRegion.y - localGPoint.y;
      if (f7 < 0.0F) {
        localGRegion.dy += f7; if (localGRegion.dy <= 0.0F) break label575; localGRegion.y = localGPoint.y;
        f7 = 0.0F;
      }
      f7 = localGRegion.dy + f7 - paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
      if (f7 > 0.0F) {
        localGRegion.dy -= f7; if (localGRegion.dy <= 0.0F) break label575; 
      }
      if ((this._findTest.x >= localGRegion.x) && (this._findTest.x < localGRegion.x + localGRegion.dx) && (this._findTest.y >= localGRegion.y) && (this._findTest.y < localGRegion.y + localGRegion.dy) && (!paramGWindow.isMousePassThrough(paramFloat1, paramFloat2)))
      {
        this._findWindowUnder = paramGWindow;
      }
    } else {
      localGRegion.set(this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion);
      if ((this._findTest.x >= localGPoint.x) && (this._findTest.x < localGPoint.x + paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx) && (this._findTest.y >= localGPoint.y) && (this._findTest.y < localGPoint.y + paramGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) && (!paramGWindow.isMousePassThrough(paramFloat1, paramFloat2)))
      {
        this._findWindowUnder = paramGWindow;
      }
    }
    if (paramGWindow.jdField_childWindow_of_type_JavaUtilArrayList != null) {
      int i = paramGWindow.jdField_childWindow_of_type_JavaUtilArrayList.size();
      int j = 0;
      while (true) { GWindow localGWindow = (GWindow)paramGWindow.jdField_childWindow_of_type_JavaUtilArrayList.get(j);
        findWindowUnder(localGWindow, paramFloat1, paramFloat2);

        j++; if (j >= i) break;

      }

    }

    label575: localGRegion.set(f3, f4, f5, f6);
    localGPoint.set(f1, f2);
  }

  protected void doPreRender()
  {
    super.doRender(false);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
    if (this.manager.bMouseActive) {
      GCursor localGCursor = this.mouseOverCursor;
      if (localGCursor == null)
        localGCursor = this.mouseCursors[this.mouseWindow.mouseCursor];
      if ((localGCursor != null) && 
        (!Mouse.adapter().isExistMouseCursorAdapter()))
        localGCursor.preRender(this);
    }
  }

  protected void doRender()
  {
    super.doRender(true);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
    if (this.manager.bMouseActive) {
      GCursor localGCursor = this.mouseOverCursor;
      if (localGCursor == null)
        localGCursor = this.mouseCursors[this.mouseWindow.mouseCursor];
      if (localGCursor != null)
        if (Mouse.adapter().isExistMouseCursorAdapter())
          Mouse.adapter().setMouseCursor(localGCursor.nativeCursor);
        else
          localGCursor.render(this);
    }
  }

  protected void doMouseMove(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mouseRelMove.set(paramFloat1, paramFloat2);
    this.mouseRelMoveZ = paramFloat3;
    if (this.mouseWindow != null)
      this.mouseWindow.mouseRelMove(paramFloat1, paramFloat2, paramFloat3);
  }

  protected void doMouseAbsMove(float paramFloat1, float paramFloat2, float paramFloat3) {
    this.mouseOldPos.set(this.mousePos);
    this.mouseOldPosZ = this.mousePosZ;
    this.mousePos.set(paramFloat1, paramFloat2);
    this.mousePosZ = paramFloat3;
    this.mouseStep.set(this.mousePos.x - this.mouseOldPos.x, this.mousePos.y - this.mouseOldPos.y);
    this.mouseStepZ = (this.mousePosZ - this.mouseOldPosZ);
    GWindow localGWindow1;
    for (int i = 0; i < this.mouseListeners.size(); i++) {
      localGWindow1 = (GWindow)this.mouseListeners.get(i);
      if (!localGWindow1.isWaitModal())
        localGWindow1.msgMouseMove(true, paramFloat1, paramFloat2);
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();

    if (!this.bMouseCapture) {
      localGWindow1 = findWindowUnder(paramFloat1, paramFloat2);
      if (localGWindow1.isWaitModal())
        localGWindow1 = this.modalWindow;
    } else {
      localGWindow1 = this.mouseWindow;
    }
    GPoint localGPoint;
    if (localGWindow1 != this.mouseWindow) {
      this.mouseOldWindow = this.mouseWindow;
      this.mouseWindow = localGWindow1;
      this.mouseOldWindow.mouseLeave();
      this.mouseWindow.mouseEnter();
      localGPoint = this.mouseWindow.getMouseXY();
      this.mouseWindow.mouseMove(localGPoint.x, localGPoint.y);
    } else if ((this.mousePos.x != this.mouseOldPos.x) || (this.mousePos.y != this.mouseOldPos.y)) {
      localGPoint = this.mouseWindow.getMouseXY();
      this.mouseWindow.mouseMove(localGPoint.x, localGPoint.y);
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
    for (int j = 0; j < this.mouseListeners.size(); j++) {
      GWindow localGWindow2 = (GWindow)this.mouseListeners.get(j);
      if (!localGWindow2.isWaitModal())
        localGWindow2.msgMouseMove(false, paramFloat1, paramFloat2);
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  protected void doMouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    for (int i = 0; i < this.mouseListeners.size(); i++) {
      localObject = (GWindow)this.mouseListeners.get(i);
      if (!((GWindow)localObject).isWaitModal())
        ((GWindow)localObject).msgMouseButton(true, paramInt, paramBoolean, paramFloat1, paramFloat2);
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();

    if (!this.bMouseCapture) {
      localObject = findWindowUnder(paramFloat1, paramFloat2);
      if (((GWindow)localObject).isWaitModal())
        localObject = this.modalWindow;
      if (localObject != this.mouseWindow) {
        this.mouseOldWindow = this.mouseWindow;
        this.mouseWindow = ((GWindow)localObject);
        this.mouseOldWindow.mouseLeave();
        this.mouseWindow.mouseEnter();
        if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
      }
    }
    Object localObject = this.mouseWindow.getMouseXY();
    this.mouseWindow._mouseButton(paramInt, paramBoolean, ((GPoint)localObject).x, ((GPoint)localObject).y);

    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();

    for (int j = 0; j < this.mouseListeners.size(); j++) {
      GWindow localGWindow = (GWindow)this.mouseListeners.get(j);
      if (!localGWindow.isWaitModal())
        localGWindow.msgMouseButton(false, paramInt, paramBoolean, paramFloat1, paramFloat2);
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
  }

  public void registerMouseListener(GWindow paramGWindow)
  {
    if (!this.mouseListeners.contains(paramGWindow))
      this.mouseListeners.add(paramGWindow);
  }

  public void unRegisterMouseListener(GWindow paramGWindow) {
    int i = this.mouseListeners.indexOf(paramGWindow);
    if (i >= 0)
      this.mouseListeners.remove(i);
  }

  public void registerKeyListener(GWindow paramGWindow)
  {
    if (!this.keyListeners.contains(paramGWindow))
      this.keyListeners.add(paramGWindow);
  }

  public void unRegisterKeyListener(GWindow paramGWindow) {
    int i = this.keyListeners.indexOf(paramGWindow);
    if (i >= 0)
      this.keyListeners.remove(i);
  }

  protected void doCheckKeyFocusWindow()
  {
    this.bDoCheckKeyFocusWindow = false;
    GWindow localGWindow = checkKeyFocusWindow();
    if (localGWindow.isWaitModal())
      return;
    if (localGWindow != this.keyFocusWindow) {
      this.keyFocusWindow.keyFocusExit();
      this.keyFocusWindow = localGWindow;
      this.keyFocusWindow.keyFocusEnter();
    }
  }

  protected void doKeyboardKey(int paramInt, boolean paramBoolean) {
    for (int i = this.keyListeners.size() - 1; i >= 0; i--) {
      GWindow localGWindow = (GWindow)this.keyListeners.get(i);
      if ((!localGWindow.isWaitModal()) && 
        (localGWindow.hotKey(paramInt, paramBoolean)))
        return;
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
    this.keyFocusWindow.keyboardKey(paramInt, paramBoolean);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  protected void doKeyboardChar(char paramChar) {
    for (int i = this.keyListeners.size() - 1; i >= 0; i--) {
      GWindow localGWindow = (GWindow)this.keyListeners.get(i);
      if ((!localGWindow.isWaitModal()) && 
        (localGWindow.hotKeyChar(paramChar)))
        return;
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();
    this.keyFocusWindow.keyboardChar(paramChar);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  protected void doJoyButton(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.keyFocusWindow.joyButton(paramInt1, paramInt2, paramBoolean);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  protected void doJoyMove(int paramInt1, int paramInt2, int paramInt3) {
    this.keyFocusWindow.joyMove(paramInt1, paramInt2, paramInt3);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  protected void doJoyPov(int paramInt1, int paramInt2) {
    this.keyFocusWindow.joyPov(paramInt1, paramInt2);
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  protected void doJoyPoll() {
    this.keyFocusWindow.joyPoll();
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow(); 
  }

  public boolean isActivated() {
    return true;
  }
  public void showWindow() {
  }
  public void hideWindow() {  }

  public void doResolutionChanged() { this.jdField_resolutionChangeCounter_of_type_Int += 1;
    if (this.lookAndFeel != null)
      this.lookAndFeel.resolutionChanged(this);
    resolutionChanged();
    if (this.jdField_childWindow_of_type_JavaUtilArrayList != null) {
      for (int i = 0; i < this.jdField_childWindow_of_type_JavaUtilArrayList.size(); i++) {
        GWindow localGWindow = (GWindow)this.jdField_childWindow_of_type_JavaUtilArrayList.get(i);
        localGWindow.doResolutionChanged();
      }
    }
    if (this.bDoCheckKeyFocusWindow) doCheckKeyFocusWindow();  }

  public void created()
  {
  }

  public GWindowRoot() {
    this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot = this;
    this.mouseOldWindow = this;
    this.mouseWindow = this;
    this.keyFocusWindow = this;
    this.jdField_resolutionChangeCounter_of_type_Int = 1;
  }
}