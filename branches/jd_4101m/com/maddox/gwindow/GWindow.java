package com.maddox.gwindow;

import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;

public class GWindow
{
  public static final float metricConst = 12.0F;
  public static final int MOUSE_CURSOR_NONE = 0;
  public static final int MOUSE_CURSOR_NORMAL = 1;
  public static final int MOUSE_CURSOR_CROSS = 2;
  public static final int MOUSE_CURSOR_HAND = 3;
  public static final int MOUSE_CURSOR_HELP = 4;
  public static final int MOUSE_CURSOR_IBEAM = 5;
  public static final int MOUSE_CURSOR_NO = 6;
  public static final int MOUSE_CURSOR_SIZEALL = 7;
  public static final int MOUSE_CURSOR_SIZENESW = 8;
  public static final int MOUSE_CURSOR_SIZENS = 9;
  public static final int MOUSE_CURSOR_SIZENWSE = 10;
  public static final int MOUSE_CURSOR_SIZEWE = 11;
  public static final int MOUSE_CURSOR_UP = 12;
  public static final int MOUSE_CURSOR_WAIT = 13;
  public static final int MOUSE_LEFT = 0;
  public static final int MOUSE_RIGHT = 1;
  public static final int MOUSE_MIDDLE = 2;
  public static final int MOUSE_FOUR = 3;
  public static final int FONT_NORMAL = 0;
  public static final int FONT_BOLD = 1;
  public static final int FONT_LARGE = 2;
  public static final int FONT_LARGEBOLD = 3;
  public static final int ALIGN_LEFT = 0;
  public static final int ALIGN_CENTER = 1;
  public static final int ALIGN_RIGHT = 2;
  public static final int CONTROL_TAB_STOP = 1;
  public static final int CONTROL_DEFAULT = 2;
  public static final int NOTIFY_MOVED = 0;
  public static final int NOTIFY_RESIZED = 1;
  public static final int NOTIFY_CHANGE = 2;
  public static final int NOTIFY_MOUSEDOWN = 3;
  public static final int NOTIFY_MOUSEUP = 4;
  public static final int NOTIFY_CLICK = 5;
  public static final int NOTIFY_DOUBLECLICK = 6;
  public static final int NOTIFY_MOUSEENTER = 7;
  public static final int NOTIFY_MOUSEMOVE = 8;
  public static final int NOTIFY_MOUSELEAVE = 9;
  public static final int NOTIFY_KEYDOWN = 10;
  public static final int NOTIFY_KEYUP = 11;
  public static final int NOTIFY_KEYCHAR = 12;
  public static final int NOTIFY_SHOWN = 13;
  public static final int NOTIFY_HIDDEN = 14;
  public static final int NOTIFY_KEYFOCUSENTER = 15;
  public static final int NOTIFY_KEYFOCUSEXIT = 16;
  public static final int NOTIFY_MOUSERELMOVE = 17;
  public static final int NOTIFY_USER = 20;
  public static final int HITTEST_NONE = 0;
  public static final int HITTEST_NW = 1;
  public static final int HITTEST_N = 2;
  public static final int HITTEST_NE = 3;
  public static final int HITTEST_W = 4;
  public static final int HITTEST_E = 5;
  public static final int HITTEST_SW = 6;
  public static final int HITTEST_S = 7;
  public static final int HITTEST_SE = 8;
  public static final int HITTEST_TITLE = 9;
  public boolean bVisible = false;

  public boolean bClip = true;

  public boolean bNotify = false;

  public boolean bAlwaysOnTop = false;

  public boolean bAlwaysBehind = false;

  public boolean bAcceptsKeyFocus = true;

  public boolean bAcceptsHotKeys = false;

  public boolean bTransient = false;

  public boolean bMouseListener = false;

  public boolean[] bEnableDoubleClick = new boolean[7];
  public GRegion win;
  public GRegion metricWin;
  public GWindow parentWindow;
  public ArrayList childWindow;
  public GWindow notifyWindow;
  public ArrayList notifyListeners;
  public GWindow activeWindow;
  public GWindowRoot root;
  public int resolutionChangeCounter;
  public int mouseCursor = 1;

  public static GSize _minSize = new GSize();

  public static GRegion _clientRegion = new GRegion();

  private static GPoint _mousePos = new GPoint();

  private static GPoint _winPos = new GPoint();

  private static GPoint _globalPos = new GPoint();

  private static ArrayList clipStack = new ArrayList();
  private static int clipStackPtr = 0;

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
  {
    return false;
  }

  public boolean notify(int paramInt1, int paramInt2)
  {
    if (!this.bNotify) return false;
    if ((this.notifyWindow != null) && 
      (this.notifyWindow.notify(this, paramInt1, paramInt2))) {
      return true;
    }
    if (this.notifyListeners != null) {
      for (int i = 0; i < this.notifyListeners.size(); i++) {
        GNotifyListenerDef localGNotifyListenerDef = (GNotifyListenerDef)this.notifyListeners.get(i);
        if (localGNotifyListenerDef.notify(this, paramInt1, paramInt2))
          return true;
      }
    }
    return false;
  }

  public void addNotifyListener(GNotifyListenerDef paramGNotifyListenerDef)
  {
    if (this.notifyListeners == null)
      this.notifyListeners = new ArrayList();
    int i = this.notifyListeners.indexOf(paramGNotifyListenerDef);
    if (i < 0)
      this.notifyListeners.add(paramGNotifyListenerDef);
  }

  public void removeNotifyListener(GNotifyListenerDef paramGNotifyListenerDef)
  {
    if (this.notifyListeners == null) return;
    int i = this.notifyListeners.indexOf(paramGNotifyListenerDef);
    if (i >= 0)
      this.notifyListeners.remove(i);
  }

  public void beforeCreate()
  {
  }

  public void created()
  {
  }

  public void afterCreated()
  {
  }

  public void resolutionChanged()
  {
    resized();
  }

  public void doResolutionChanged() {
    if (this.metricWin != null) {
      this.win.x = lookAndFeel().metric(this.metricWin.x);
      this.win.y = lookAndFeel().metric(this.metricWin.y);
      this.win.dx = lookAndFeel().metric(this.metricWin.dx);
      this.win.dy = lookAndFeel().metric(this.metricWin.dy);
    }
    if (this.childWindow != null) {
      for (int i = 0; i < this.childWindow.size(); i++) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        localGWindow.doResolutionChanged();
      }
    }
    resolutionChanged();
    this.resolutionChangeCounter = this.root.resolutionChangeCounter;
  }

  public void setPosSize(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    setPos(paramFloat1, paramFloat2);
    setSize(paramFloat3, paramFloat4);
  }

  public void setPos(float paramFloat1, float paramFloat2)
  {
    paramFloat1 = Math.round(paramFloat1);
    paramFloat2 = Math.round(paramFloat2);
    if ((this.win.x != paramFloat1) || (this.win.y != paramFloat2)) {
      this.win.x = paramFloat1;
      this.win.y = paramFloat2;
      if (this.metricWin != null) {
        this.metricWin.x = (this.win.x / lookAndFeel().metric());
        this.metricWin.y = (this.win.y / lookAndFeel().metric());
      }
      moved();
    }
  }

  public void setSize(float paramFloat1, float paramFloat2)
  {
    paramFloat1 = Math.round(paramFloat1);
    paramFloat2 = Math.round(paramFloat2);
    if ((this.win.dx != paramFloat1) || (this.win.dy != paramFloat2)) {
      this.win.dx = paramFloat1;
      this.win.dy = paramFloat2;
      if (this.metricWin != null) {
        this.metricWin.dx = (this.win.dx / lookAndFeel().metric());
        this.metricWin.dy = (this.win.dy / lookAndFeel().metric());
      }
      resized();
    }
  }

  public void setMetric(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (this.metricWin == null)
      createMetricWin();
    this.metricWin.x = paramFloat1;
    this.metricWin.y = paramFloat2;
    this.metricWin.dx = paramFloat3;
    this.metricWin.dy = paramFloat4;
    setSize(lookAndFeel().metric(paramFloat3), lookAndFeel().metric(paramFloat4));
    setPos(lookAndFeel().metric(paramFloat1), lookAndFeel().metric(paramFloat2));
  }

  public void setMetricPos(float paramFloat1, float paramFloat2) {
    if (this.metricWin == null)
      createMetricWin();
    this.metricWin.x = paramFloat1;
    this.metricWin.y = paramFloat2;
    setPos(lookAndFeel().metric(paramFloat1), lookAndFeel().metric(paramFloat2));
  }

  public void setMetricSize(float paramFloat1, float paramFloat2) {
    if (this.metricWin == null)
      createMetricWin();
    this.metricWin.dx = paramFloat1;
    this.metricWin.dy = paramFloat2;
    setSize(lookAndFeel().metric(paramFloat1), lookAndFeel().metric(paramFloat2));
  }

  public void set1024PosSize(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    set1024Pos(paramFloat1, paramFloat2);
    set1024Size(paramFloat3, paramFloat4);
  }

  public void set1024Pos(float paramFloat1, float paramFloat2) {
    setPos(paramFloat1 * this.root.win.dx / 1024.0F, paramFloat2 * this.root.win.dy / 768.0F);
  }
  public void set1024Size(float paramFloat1, float paramFloat2) {
    setSize(paramFloat1 * this.root.win.dx / 1024.0F, paramFloat2 * this.root.win.dy / 768.0F);
  }
  public float x1024(float paramFloat) {
    return paramFloat * this.root.win.dx / 1024.0F; } 
  public float y1024(float paramFloat) { return paramFloat * this.root.win.dy / 768.0F; }

  public void createMetricWin() {
    this.metricWin = new GRegion(this.win.x / lookAndFeel().metric(), this.win.y / lookAndFeel().metric(), this.win.dx / lookAndFeel().metric(), this.win.dy / lookAndFeel().metric());
  }

  public void moved()
  {
    notify(0, 0);
  }

  public void resized() {
    notify(1, 0);
  }

  public GSize getMinSize()
  {
    return getMinSize(_minSize);
  }

  public GSize getMinSize(GSize paramGSize)
  {
    float f1 = 0.0F;
    float f2 = 0.0F;
    if (this.childWindow != null) {
      for (int i = 0; i < this.childWindow.size(); i++) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        localGWindow.getMinSize(paramGSize);
        if (paramGSize.dx > f1) f1 = paramGSize.dx;
        if (paramGSize.dy <= f2) continue; f2 = paramGSize.dy;
      }
    }
    paramGSize.set(f1, f2);
    return paramGSize;
  }

  public GRegion getClientRegion(float paramFloat)
  {
    return getClientRegion(_clientRegion, paramFloat);
  }

  public GRegion getClientRegion() {
    return getClientRegion(_clientRegion, 0.0F);
  }

  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (paramGRegion.y = paramFloat);
    paramGRegion.dx = (this.win.dx - 2.0F * paramFloat);
    paramGRegion.dy = (this.win.dy - 2.0F * paramFloat);
    return paramGRegion;
  }

  public void preRender()
  {
  }

  public void render()
  {
  }

  public void keyFocusEnter()
  {
    notify(15, 0);
  }

  public void keyboardKey(int paramInt, boolean paramBoolean) {
    notify(paramBoolean ? 10 : 11, paramInt);
  }

  public void keyboardChar(char paramChar) {
    notify(12, paramChar);
  }

  public void keyFocusExit() {
    notify(16, 0);
  }

  public boolean isKeyFocus() {
    return this == this.root.keyFocusWindow;
  }

  public void setKeyFocus() {
    this.activeWindow = null;
    activateWindow(false);
    this.root.doCheckKeyFocusWindow();
  }

  public void setAcceptsKeyFocus()
  {
    if (this.bAcceptsKeyFocus) return;
    this.bAcceptsKeyFocus = true;
    if (this != this.root)
      this.parentWindow.setAcceptsKeyFocus();
  }

  public void cancelAcceptsKeyFocus()
  {
    if (this.childWindow != null) {
      for (int i = 0; i < this.childWindow.size(); i++) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        localGWindow.cancelAcceptsKeyFocus();
      }
    }
    this.bAcceptsKeyFocus = false;
  }

  public boolean hotKey(int paramInt, boolean paramBoolean)
  {
    return false;
  }

  public boolean hotKeyChar(char paramChar) {
    return false;
  }

  public void setAcceptsHotKeys(boolean paramBoolean) {
    if ((paramBoolean) && (!this.bAcceptsHotKeys) && (this.bVisible))
      this.root.registerKeyListener(this);
    if ((!paramBoolean) && (this.bAcceptsHotKeys) && (this.bVisible))
      this.root.unRegisterKeyListener(this);
    this.bAcceptsHotKeys = paramBoolean;
  }
  public void joyButton(int paramInt1, int paramInt2, boolean paramBoolean) {
  }
  public void joyMove(int paramInt1, int paramInt2, int paramInt3) {
  }
  public void joyPov(int paramInt1, int paramInt2) {
  }
  public void joyPoll() {
  }
  public void mouseCapture(boolean paramBoolean) { if (paramBoolean) {
      if ((this.root.mouseWindow == this) && (this.root.bMouseCapture))
        return;
      this.root.bMouseCapture = true;
      this.root.mouseWindow = this;
      this.root.doMouseAbsMove(this.root.mousePos.x, this.root.mousePos.y, this.root.mousePosZ);
    } else if ((this.root.mouseWindow == this) && (this.root.bMouseCapture)) {
      this.root.bMouseCapture = false;
      this.root.doMouseAbsMove(this.root.mousePos.x, this.root.mousePos.y, this.root.mousePosZ);
    } }

  public boolean isMouseOver()
  {
    return this.root.mouseWindow == this;
  }

  public boolean isMouseCaptured() {
    return (this.root.bMouseCapture) && (this.root.mouseWindow == this);
  }

  public boolean isMouseCapturedAny() {
    return this.root.bMouseCapture;
  }

  public boolean isMouseDown(int paramInt) {
    return this.root.mouseWindowDown[paramInt] == this;
  }

  public boolean isMouseDownAny(int paramInt) {
    return this.root.mouseWindowDown[paramInt] != null;
  }

  public void mouseEnter() {
    notify(7, 0);
  }

  public void mouseMove(float paramFloat1, float paramFloat2) {
    notify(8, 0);
  }

  public void mouseRelMove(float paramFloat1, float paramFloat2, float paramFloat3) {
    notify(17, 0);
  }

  public void mouseClick(int paramInt, float paramFloat1, float paramFloat2) {
    notify(5, paramInt);
  }

  public void mouseDoubleClick(int paramInt, float paramFloat1, float paramFloat2) {
    notify(6, paramInt);
  }

  public void mouseLeave() {
    notify(9, 0);
  }

  public boolean isActivated()
  {
    if (this.parentWindow.activeWindow != this) return false;
    return this.parentWindow.isActivated();
  }

  public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if (paramBoolean) notify(3, paramInt); else
      notify(4, paramInt);
  }

  protected void _mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    GWindow localGWindow;
    GPoint localGPoint;
    if (paramBoolean) {
      activateWindow(false);
      if (this.root.mouseWindowDown[paramInt] != null) {
        localGWindow = this.root.mouseWindowDown[paramInt];
        localGPoint = localGWindow.getMouseXY();
        localGWindow.mouseButton(paramInt, false, localGPoint.x, localGPoint.y);
      }
      this.root.mouseWindowDown[paramInt] = this;
    } else {
      if (this.root.mouseWindowDown[paramInt] == this) {
        if ((paramFloat1 >= 0.0F) && (paramFloat1 < this.win.dx) && (paramFloat2 >= 0.0F) && (paramFloat2 < this.win.dy)) {
          if ((this.bEnableDoubleClick[paramInt] != 0) && (this.root.mouseWindowUp[paramInt] == this) && (Time.currentReal() < this.root.mouseTimeUp[paramInt] + GWindowRoot.TIME_DOUBLE_CLICK))
          {
            this.root.mouseTimeUp[paramInt] = 0L;
            mouseDoubleClick(paramInt, paramFloat1, paramFloat2);
          } else {
            this.root.mouseWindowUp[paramInt] = this;
            this.root.mouseTimeUp[paramInt] = Time.currentReal();
            mouseClick(paramInt, paramFloat1, paramFloat2);
          }
        }
        else this.root.mouseWindowUp[paramInt] = null;
      }
      else if (this.root.mouseWindowDown[paramInt] != null) {
        localGWindow = this.root.mouseWindowDown[paramInt];
        localGPoint = localGWindow.getMouseXY();
        localGWindow.mouseButton(paramInt, false, localGPoint.x, localGPoint.y);
      }
      this.root.mouseWindowDown[paramInt] = null;
    }

    mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
  }

  public void msgMouseButton(boolean paramBoolean1, int paramInt, boolean paramBoolean2, float paramFloat1, float paramFloat2)
  {
  }

  public void msgMouseMove(boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
  }

  public void setMouseListener(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.bMouseListener) && (this.bVisible))
      this.root.registerMouseListener(this);
    if ((!paramBoolean) && (this.bMouseListener) && (this.bVisible))
      this.root.unRegisterMouseListener(this);
    this.bMouseListener = paramBoolean;
  }

  public boolean isMousePassThrough(float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public final GPoint getMouseXY() {
    GWindow localGWindow = this;
    _mousePos.set(this.root.mousePos);
    while (localGWindow != this.root) {
      _mousePos.sub(localGWindow.win.x, localGWindow.win.y);
      localGWindow = localGWindow.parentWindow;
    }
    return _mousePos;
  }

  public final GPoint globalToWindow(float paramFloat1, float paramFloat2)
  {
    GWindow localGWindow = this;
    _winPos.set(paramFloat1, paramFloat2);
    while (localGWindow != this.root) {
      _winPos.sub(localGWindow.win.x, localGWindow.win.y);
      localGWindow = localGWindow.parentWindow;
    }
    return _winPos;
  }

  public final GPoint windowToGlobal(float paramFloat1, float paramFloat2)
  {
    GWindow localGWindow = this;
    _globalPos.set(paramFloat1, paramFloat2);
    while (localGWindow != this.root) {
      _globalPos.add(localGWindow.win.x, localGWindow.win.y);
      localGWindow = localGWindow.parentWindow;
    }
    return _globalPos;
  }

  public final GWindow getParent(Class paramClass, boolean paramBoolean)
  {
    return getParent(this, paramClass, paramBoolean, false);
  }

  public final GWindow getParent(GWindow paramGWindow, Class paramClass, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramGWindow = paramGWindow.parentWindow;
    if (paramGWindow == null) return null;
    while (paramGWindow != this.root) {
      if (paramBoolean1 ? paramClass == paramGWindow.getClass() : paramClass.isInstance(paramGWindow)) {
        if (paramBoolean2) {
          if (isFindTestOk(paramGWindow, true))
            return paramGWindow;
        }
        else return paramGWindow;
      }

      paramGWindow = paramGWindow.parentWindow;
    }
    return null;
  }

  public final GWindow getChild(Class paramClass, boolean paramBoolean)
  {
    return getChild(this, paramClass, paramBoolean, false);
  }

  public final GWindow getChild(GWindow paramGWindow, Class paramClass, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramGWindow.childWindow == null)
      return null;
    for (int i = paramGWindow.childWindow.size() - 1; i >= 0; i--) {
      GWindow localGWindow = (GWindow)paramGWindow.childWindow.get(i);
      if (paramBoolean1 ? paramClass == localGWindow.getClass() : paramClass.isInstance(localGWindow)) {
        if (paramBoolean2) {
          if (isFindTestOk(localGWindow, true))
            return localGWindow;
        }
        else return localGWindow;
      }

      localGWindow = getChild(localGWindow, paramClass, paramBoolean1, paramBoolean2);
      if (localGWindow != null) {
        if (paramBoolean2) {
          if (isFindTestOk(localGWindow, false))
            return localGWindow;
        }
        else return localGWindow;
      }
    }

    return null;
  }

  public boolean isFindTestOk(GWindow paramGWindow, boolean paramBoolean)
  {
    return true;
  }

  public void doRender(boolean paramBoolean) {
    if (this.resolutionChangeCounter != this.root.resolutionChangeCounter) {
      doResolutionChanged();
      this.resolutionChangeCounter = this.root.resolutionChangeCounter;
    }

    GPoint localGPoint = this.root.C.org;
    GRegion localGRegion = this.root.C.clip;
    float f1 = localGPoint.x;
    float f2 = localGPoint.y;
    localGPoint.add(this.win.x, this.win.y);
    float f3 = localGRegion.x;
    float f4 = localGRegion.y;
    float f5 = localGRegion.dx;
    float f6 = localGRegion.dy;

    if (this.bClip) {
      float f7 = localGRegion.x - localGPoint.x;
      if (f7 < 0.0F) {
        localGRegion.dx += f7; if (localGRegion.dx <= 0.0F) break label351; localGRegion.x = localGPoint.x;
        f7 = 0.0F;
      }
      f7 = localGRegion.dx + f7 - this.win.dx;
      if (f7 > 0.0F) {
        localGRegion.dx -= f7; if (localGRegion.dx <= 0.0F) break label351; 
      }
      f7 = localGRegion.y - localGPoint.y;
      if (f7 < 0.0F) {
        localGRegion.dy += f7; if (localGRegion.dy <= 0.0F) break label351; localGRegion.y = localGPoint.y;
        f7 = 0.0F;
      }
      f7 = localGRegion.dy + f7 - this.win.dy;
      if (f7 > 0.0F) {
        localGRegion.dy -= f7; if (localGRegion.dy <= 0.0F) break label351; 
      }
    } else {
      localGRegion.set(this.root.win);
    }

    if (paramBoolean) render(); else
      preRender();
    if (this.childWindow != null) {
      doChildrensRender(paramBoolean);
    }

    label351: localGRegion.set(f3, f4, f5, f6);
    localGPoint.set(f1, f2);
  }

  public void doChildrensRender(boolean paramBoolean) {
    for (int i = 0; i < this.childWindow.size(); i++) {
      GWindow localGWindow = (GWindow)this.childWindow.get(i);
      localGWindow.doRender(paramBoolean);
    }
  }

  public void popClip()
  {
    if (clipStackPtr <= 0) {
      System.err.println("GWindow clip stack underflow");
      return;
    }
    ClipStackItem localClipStackItem = (ClipStackItem)clipStack.get(--clipStackPtr);
    GPoint localGPoint = this.root.C.org;
    GRegion localGRegion = this.root.C.clip;
    localGRegion.set(localClipStackItem.clipx, localClipStackItem.clipy, localClipStackItem.clipdx, localClipStackItem.clipdy);
    localGPoint.set(localClipStackItem.orgx, localClipStackItem.orgy);
  }

  protected void pushClip() {
    if (clipStack.size() == clipStackPtr) {
      clipStack.add(new ClipStackItem());
    }
    ClipStackItem localClipStackItem = (ClipStackItem)clipStack.get(clipStackPtr++);
    GPoint localGPoint = this.root.C.org;
    GRegion localGRegion = this.root.C.clip;
    localClipStackItem.orgx = localGPoint.x;
    localClipStackItem.orgy = localGPoint.y;
    localClipStackItem.clipx = localGRegion.x;
    localClipStackItem.clipy = localGRegion.y;
    localClipStackItem.clipdx = localGRegion.dx;
    localClipStackItem.clipdy = localGRegion.dy;
  }

  public boolean pushClipRegion(GRegion paramGRegion, boolean paramBoolean, float paramFloat) {
    pushClip();
    GPoint localGPoint = this.root.C.org;
    GRegion localGRegion = this.root.C.clip;
    localGPoint.add(paramGRegion.x + paramFloat, paramGRegion.y + paramFloat);

    if (paramBoolean) {
      float f = localGRegion.x - localGPoint.x;
      if (f < 0.0F) {
        localGRegion.dx += f; if (localGRegion.dx <= 0.0F) break label284; localGRegion.x = localGPoint.x;
        f = 0.0F;
      }
      f = localGRegion.dx + f - (paramGRegion.dx - 2.0F * paramFloat);
      if (f > 0.0F) {
        localGRegion.dx -= f; if (localGRegion.dx <= 0.0F) break label284; 
      }
      f = localGRegion.y - localGPoint.y;
      if (f < 0.0F) {
        localGRegion.dy += f; if (localGRegion.dy <= 0.0F) break label284; localGRegion.y = localGPoint.y;
        f = 0.0F;
      }
      f = localGRegion.dy + f - (paramGRegion.dy - 2.0F * paramFloat);
      if (f > 0.0F) {
        localGRegion.dy -= f; if (localGRegion.dy <= 0.0F) break label284; 
      }
    } else {
      localGRegion.set(this.root.win);
    }
    return true;

    label284: popClip();
    return false;
  }

  public boolean pushClipRegion(GRegion paramGRegion, float paramFloat) {
    return pushClipRegion(paramGRegion, this.bClip, paramFloat);
  }
  public boolean pushClipRegion(GRegion paramGRegion, boolean paramBoolean) {
    return pushClipRegion(paramGRegion, paramBoolean, 0.0F);
  }
  public boolean pushClipRegion(GRegion paramGRegion) {
    return pushClipRegion(paramGRegion, this.bClip, 0.0F);
  }

  public GWindowLookAndFeel lookAndFeel() {
    return this.root.lookAndFeel;
  }
  public GWindowLookAndFeel lAF() {
    return this.root.lookAndFeel;
  }

  public void setCanvasColor(GColor paramGColor) {
    this.root.C.color.color = paramGColor.color;
  }
  public void setCanvasColor(int paramInt) {
    this.root.C.color.color = paramInt;
  }
  public void setCanvasColorWHITE() {
    this.root.C.color.color = 16777215;
  }
  public void setCanvasColorBLACK() {
    this.root.C.color.color = 0;
  }

  public void draw(float paramFloat1, float paramFloat2, GTexture paramGTexture)
  {
    draw(paramFloat1, paramFloat2, paramGTexture.size.dx, paramGTexture.size.dy, paramGTexture, 0.0F, 0.0F, paramGTexture.size.dx, paramGTexture.size.dy);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GTexture paramGTexture)
  {
    draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramGTexture, 0.0F, 0.0F, paramGTexture.size.dx, paramGTexture.size.dy);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GTexture paramGTexture, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    localGCanvas.draw(paramGTexture, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
  }

  public void draw(float paramFloat1, float paramFloat2, GMesh paramGMesh)
  {
    draw(paramFloat1, paramFloat2, paramGMesh.size.dx, paramGMesh.size.dy, paramGMesh);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GMesh paramGMesh)
  {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    localGCanvas.draw(paramGMesh, paramFloat3, paramFloat4);
  }

  public void draw(float paramFloat1, float paramFloat2, GTexture paramGTexture, GRegion paramGRegion)
  {
    draw(paramFloat1, paramFloat2, paramGRegion.dx, paramGRegion.dy, paramGTexture, paramGRegion.x, paramGRegion.y, paramGRegion.dx, paramGRegion.dy);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GTexture paramGTexture, GRegion paramGRegion) {
    draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramGTexture, paramGRegion.x, paramGRegion.y, paramGRegion.dx, paramGRegion.dy);
  }

  public void draw(float paramFloat1, float paramFloat2, GTexRegion paramGTexRegion)
  {
    draw(paramFloat1, paramFloat2, paramGTexRegion.dx, paramGTexRegion.dy, paramGTexRegion.texture, paramGTexRegion.x, paramGTexRegion.y, paramGTexRegion.dx, paramGTexRegion.dy);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GTexRegion paramGTexRegion) {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    localGCanvas.draw(paramGTexRegion.texture, paramFloat3, paramFloat4, paramGTexRegion.x, paramGTexRegion.y, paramGTexRegion.dx, paramGTexRegion.dy);
  }

  public void setCanvasFont(int paramInt) {
    this.root.C.font = this.root.textFonts[paramInt];
  }

  public void draw(float paramFloat1, float paramFloat2, String paramString)
  {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    localGCanvas.draw(paramString);
  }

  public void draw(float paramFloat1, float paramFloat2, String paramString, int paramInt1, int paramInt2) {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    localGCanvas.draw(paramString, paramInt1, paramInt2);
  }

  public void draw(float paramFloat1, float paramFloat2, char[] paramArrayOfChar, int paramInt1, int paramInt2) {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    localGCanvas.draw(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString, int paramInt) {
    draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramString, 0, paramString.length(), paramInt);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    int i = computeLines(paramString, paramInt1, paramInt2, paramFloat3);
    if (i > paramInt3)
      i = paramInt3;
    GFont localGFont = this.root.C.font;
    float f = localGFont.height;
    paramFloat2 += (paramFloat4 - f * i) / 2.0F;
    drawLines(paramFloat1, paramFloat2, paramString, paramInt1, paramInt2, paramFloat3, f, i);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, String paramString)
  {
    draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt, paramString, 0, paramString.length());
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, String paramString, int paramInt2, int paramInt3) {
    if (paramInt3 == 0) return;
    GFont localGFont = this.root.C.font;
    GSize localGSize = localGFont.size(paramString, paramInt2, paramInt3);
    paramFloat2 += (paramFloat4 - localGSize.dy) / 2.0F;
    if (paramInt1 == 2)
      paramFloat1 += paramFloat3 - localGSize.dx;
    else if (paramInt1 == 1) {
      paramFloat1 += (paramFloat3 - localGSize.dx) / 2.0F;
    }
    draw(paramFloat1, paramFloat2, paramString, paramInt2, paramInt3);
  }

  public void draw(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3) {
    if (paramInt3 == 0) return;
    GFont localGFont = this.root.C.font;
    GSize localGSize = localGFont.size(paramArrayOfChar, paramInt2, paramInt3);
    paramFloat2 += (paramFloat4 - localGSize.dy) / 2.0F;
    if (paramInt1 == 2)
      paramFloat1 += paramFloat3 - localGSize.dx;
    else if (paramInt1 == 1) {
      paramFloat1 += (paramFloat3 - localGSize.dx) / 2.0F;
    }
    draw(paramFloat1, paramFloat2, paramArrayOfChar, paramInt2, paramInt3);
  }

  public int computeLines(String[] paramArrayOfString, float paramFloat) {
    int i = 0;
    for (int j = 0; j < paramArrayOfString.length; j++) {
      String str = paramArrayOfString[j];
      if (str == null)
        break;
      i += computeLines(str, 0, str.length(), paramFloat);
    }
    return i;
  }

  public int computeLines(String paramString, int paramInt1, int paramInt2, float paramFloat) {
    GFont localGFont = this.root.C.font;
    int i = 0;
    while (paramInt2 > 0) {
      int j = paramInt2;
      int k = paramString.indexOf('\n', paramInt1);
      if (k >= 0) j = k - paramInt1;
      if (j > 0) {
        while (j > 0) {
          int m = localGFont.len(paramString, paramInt1, j, paramFloat, true, true);
          if (m == 0)
            m = localGFont.len(paramString, paramInt1, j, paramFloat, true, false);
          if (m == 0) {
            return i;
          }
          i++; paramInt2 -= m; paramInt1 += m; j -= m;
          while ((j > 0) && 
            (paramString.charAt(paramInt1) == ' ')) {
            paramInt1++; paramInt2--; j--;
          }
        }
        if (k >= 0) {
          paramInt1++; paramInt2--;
        }
      } else {
        i++; paramInt2--; paramInt1++;
      }
    }
    return i;
  }

  public int drawLines(float paramFloat1, float paramFloat2, String[] paramArrayOfString, float paramFloat3, float paramFloat4) {
    return drawLines(paramFloat1, paramFloat2, paramArrayOfString, paramFloat3, paramFloat4, -1);
  }

  public int drawLines(float paramFloat1, float paramFloat2, String[] paramArrayOfString, float paramFloat3, float paramFloat4, int paramInt) {
    int i = 0;
    for (int j = 0; (j < paramArrayOfString.length) && (paramInt != 0); j++) {
      String str = paramArrayOfString[j];
      if (str == null)
        break;
      int k = drawLines(paramFloat1, paramFloat2, str, 0, str.length(), paramFloat3, paramFloat4, paramInt);
      i += k;
      paramInt -= k;
      paramFloat2 += k * paramFloat4;
    }
    return i;
  }

  public int drawLines(float paramFloat1, float paramFloat2, String paramString, int paramInt1, int paramInt2, float paramFloat3, float paramFloat4) {
    return drawLines(paramFloat1, paramFloat2, paramString, paramInt1, paramInt2, paramFloat3, paramFloat4, -1);
  }

  public int drawLines(float paramFloat1, float paramFloat2, String paramString, int paramInt1, int paramInt2, float paramFloat3, float paramFloat4, int paramInt3) {
    GCanvas localGCanvas = this.root.C;
    localGCanvas.cur.x = (paramFloat1 + localGCanvas.org.x);
    localGCanvas.cur.y = (paramFloat2 + localGCanvas.org.y);
    GFont localGFont = localGCanvas.font;
    int i = 0;
    while ((paramInt2 > 0) && (paramInt3 != 0)) {
      int j = paramInt2;
      int k = paramString.indexOf('\n', paramInt1);
      if (k >= 0) j = k - paramInt1;
      if (j > 0) {
        while ((j > 0) && (paramInt3 != 0)) {
          int m = localGFont.len(paramString, paramInt1, j, paramFloat3, true, true);
          if (m == 0)
            m = localGFont.len(paramString, paramInt1, j, paramFloat3, true, false);
          if (m == 0) {
            return i;
          }
          localGCanvas.draw(paramString, paramInt1, m);
          localGCanvas.cur.y += paramFloat4;
          i++; paramInt2 -= m; paramInt1 += m; j -= m;
          paramInt3--;
          while ((j > 0) && 
            (paramString.charAt(paramInt1) == ' ')) {
            paramInt1++; paramInt2--; j--;
          }
        }
        if (k >= 0) {
          paramInt1++; paramInt2--;
        }
      } else {
        localGCanvas.cur.y += paramFloat4;
        i++; paramInt2--; paramInt1++;
        paramInt3--;
      }
    }
    return i;
  }

  public void toolTip(String paramString) {
    if (this != this.root)
      this.parentWindow.toolTip(paramString);
  }

  public void windowShown()
  {
    notify(13, 0);
    if (this.childWindow != null)
      for (int i = 0; i < this.childWindow.size(); i++) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        localGWindow.windowShown();
      }
  }

  public void windowHidden()
  {
    notify(14, 0);
    if (this.childWindow != null)
      for (int i = 0; i < this.childWindow.size(); i++) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        localGWindow.windowHidden();
      }
  }

  public boolean isVisible()
  {
    if (this == this.root)
      return true;
    if (!this.bVisible)
      return false;
    return this.parentWindow.isVisible();
  }

  public boolean isWaitModal()
  {
    GWindow localGWindow1 = this.root.modalWindow;
    if ((localGWindow1 == null) || (!localGWindow1.bVisible))
      return false;
    GWindow localGWindow2 = this;
    while (localGWindow2 != this.root) {
      if (localGWindow2 == localGWindow1)
        return false;
      localGWindow2 = localGWindow2.parentWindow;
    }
    return true;
  }

  public final void bringToFront()
  {
    if (this == this.root)
      return;
    if ((!this.bAlwaysBehind) && (!isWaitModal())) {
      int i = this.parentWindow.activeWindow == this ? 1 : 0;
      this.parentWindow.hideChildWindow(this);
      this.parentWindow.showChildWindow(this, false);
      if (i != 0) this.parentWindow.activeWindow = this;
    }
    this.parentWindow.bringToFront();
  }

  public void showModal()
  {
    this.root.modalWindow = null;
    showWindow();
    bringToFront();
    activateWindow(false);
    this.root.modalWindow = this;
  }

  public void activateWindow()
  {
    if (!this.bVisible) showWindow();
    activateWindow(false);
  }

  public void showWindow()
  {
    this.parentWindow.showChildWindow(this, false);
    windowShown();
  }

  public void hideWindow() {
    windowHidden();
    this.parentWindow.hideChildWindow(this);
  }

  public void close(boolean paramBoolean) {
    if (this.childWindow != null) {
      for (int i = 0; i < this.childWindow.size(); i++) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        localGWindow.close(true);
      }
    }
    if (!paramBoolean)
      hideWindow();
    if (this == this.root.modalWindow)
      this.root.modalWindow = null;
    if ((this.root.bMouseCapture) && (this.root.mouseWindow == this)) {
      this.root.bMouseCapture = false;
      this.root.doMouseAbsMove(this.root.mousePos.x, this.root.mousePos.y, this.root.mousePosZ);
    }
  }

  public void setParent(GWindow paramGWindow)
  {
    int i = 0;
    if (this.bVisible) {
      i = this.parentWindow.activeWindow == this ? 1 : 0;
      this.parentWindow.hideChildWindow(this);
    }
    this.parentWindow = paramGWindow;
    if (this.bVisible) {
      this.parentWindow.showChildWindow(this, false);
      if (i != 0) this.parentWindow.activeWindow = this; 
    }
  }

  protected void activateWindow(boolean paramBoolean)
  {
    this.root.bDoCheckKeyFocusWindow = true;
    if (this == this.root) {
      return;
    }
    if (isWaitModal()) {
      return;
    }
    if (!this.bAlwaysBehind) {
      int i = this.parentWindow.activeWindow == this ? 1 : 0;
      this.parentWindow.hideChildWindow(this);
      this.parentWindow.showChildWindow(this, false);
      if (i != 0) this.parentWindow.activeWindow = this;
    }

    if ((this.bTransient) || (paramBoolean)) {
      this.parentWindow.activateWindow(true);
    } else {
      this.parentWindow.activeWindow = this;
      this.parentWindow.activateWindow(false);
    }
  }

  protected GWindow checkKeyFocusWindow() {
    if (this.childWindow == null)
      return this;
    if ((this.activeWindow != null) && (this.activeWindow.bAcceptsKeyFocus))
      return this.activeWindow.checkKeyFocusWindow();
    return this;
  }

  protected void showChildWindow(GWindow paramGWindow, boolean paramBoolean) {
    if (paramGWindow.bVisible) return;
    paramGWindow.bVisible = true;
    if (paramGWindow.bAcceptsHotKeys)
      this.root.registerKeyListener(paramGWindow);
    if (paramGWindow.bMouseListener)
      this.root.registerMouseListener(paramGWindow);
    if (paramBoolean) {
      this.childWindow.add(0, paramGWindow);
    }
    else if (this.childWindow.size() == 0) {
      this.childWindow.add(paramGWindow);
    } else {
      for (int i = this.childWindow.size() - 1; i >= 0; i--) {
        GWindow localGWindow = (GWindow)this.childWindow.get(i);
        if ((paramGWindow.bAlwaysOnTop) || (!localGWindow.bAlwaysOnTop)) {
          this.childWindow.add(i + 1, paramGWindow);
          return;
        }
      }
      this.childWindow.add(0, paramGWindow);
    }
  }

  protected void hideChildWindow(GWindow paramGWindow)
  {
    if (!paramGWindow.bVisible)
      return;
    paramGWindow.bVisible = false;
    if (paramGWindow.bAcceptsHotKeys)
      this.root.unRegisterKeyListener(paramGWindow);
    if (paramGWindow.bMouseListener)
      this.root.unRegisterMouseListener(paramGWindow);
    int i = this.childWindow.indexOf(paramGWindow);
    if (i >= 0)
      this.childWindow.remove(i);
    if (this.activeWindow == paramGWindow) {
      this.activeWindow = null;
      this.root.bDoCheckKeyFocusWindow = true;
    }
  }

  protected GWindow doNew(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    if (paramBoolean) {
      this.metricWin = new GRegion(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      this.win = new GRegion(paramGWindow.lookAndFeel().metric(this.metricWin.x), paramGWindow.lookAndFeel().metric(this.metricWin.y), paramGWindow.lookAndFeel().metric(this.metricWin.dx), paramGWindow.lookAndFeel().metric(this.metricWin.dy));
    }
    else
    {
      this.win = new GRegion((int)(paramFloat1 + 0.5F), (int)(paramFloat2 + 0.5F), (int)(paramFloat3 + 0.5F), (int)(paramFloat4 + 0.5F));
    }
    this.root = paramGWindow.root;
    this.parentWindow = paramGWindow;
    if (paramGWindow.childWindow == null) {
      paramGWindow.childWindow = new ArrayList();
    }
    if (this.notifyWindow == null)
      this.notifyWindow = paramGWindow;
    this.activeWindow = null;

    created();

    paramGWindow.showChildWindow(this, false);

    afterCreated();
    return this;
  }

  protected GWindow doNew(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, paramGWindow.win.dx, paramGWindow.win.dy, false);
    return this;
  }
  public GWindow create(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean, GWindow paramGWindow) {
    return paramGWindow.doNew(this, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
  }
  public GWindow create(GWindow paramGWindow) {
    return paramGWindow.doNew(this, 0.0F, 0.0F, this.win.dx, this.win.dy, false);
  }
  public GWindow(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
  }
  public GWindow(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, paramGWindow.win.dx, paramGWindow.win.dy, false);
  }

  public GWindow() {
    beforeCreate();
  }

  protected static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  class ClipStackItem
  {
    float orgx;
    float orgy;
    float clipx;
    float clipy;
    float clipdx;
    float clipdy;

    ClipStackItem()
    {
    }
  }
}