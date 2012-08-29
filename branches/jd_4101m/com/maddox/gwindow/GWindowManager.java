package com.maddox.gwindow;

public abstract class GWindowManager
{
  protected boolean bTimeActive = false;
  protected boolean bKeyboardActive = false;
  protected boolean bMouseActive = false;
  protected boolean bJoyActive = false;

  protected boolean bTimeGameActive = true;
  protected boolean bKeyboardGameActive = true;
  protected boolean bMouseGameActive = true;
  protected boolean bJoyGameActive = true;
  public GWindowRoot root;

  public boolean isTimeActive()
  {
    return this.bTimeActive; } 
  public boolean isKeyboardActive() { return this.bKeyboardActive; } 
  public boolean isMouseActive() { return this.bMouseActive; } 
  public boolean isJoyActive() { return this.bJoyActive; } 
  public boolean isTimeGameActive() {
    return this.bTimeGameActive; } 
  public boolean isKeyboardGameActive() { return this.bKeyboardGameActive; } 
  public boolean isMouseGameActive() { return this.bMouseGameActive; } 
  public boolean isJoyGameActive() { return this.bJoyGameActive; } 
  public void setTimeGameActive(boolean paramBoolean) {
    this.bTimeGameActive = paramBoolean; } 
  public void setKeyboardGameActive(boolean paramBoolean) { this.bKeyboardGameActive = paramBoolean; } 
  public void setMouseGameActive(boolean paramBoolean) { this.bMouseGameActive = paramBoolean; } 
  public void setJoyGameActive(boolean paramBoolean) { this.bJoyGameActive = paramBoolean; } 
  public void activateTime(boolean paramBoolean) {
    this.bTimeActive = paramBoolean; } 
  public void activateKeyboard(boolean paramBoolean) { this.bKeyboardActive = paramBoolean; } 
  public void activateMouse(boolean paramBoolean) { this.bMouseActive = paramBoolean; } 
  public void activateJoy(boolean paramBoolean) { this.bJoyActive = paramBoolean; }

  public void unActivateAll() {
    activateTime(false);
    activateKeyboard(false);
    activateMouse(false);
    activateJoy(false);
  }

  public void doPreRender(float paramFloat)
  {
    this.root.deltaTimeSec = paramFloat;
    this.root.doPreRender();
  }
  public void doRender() {
    this.root.bInRender = true;
    this.root.doRender();
    this.root.bInRender = false;
  }
  public void doCanvasResize(int paramInt1, int paramInt2) {
    this.root.C.size.set(paramInt1, paramInt2);
    this.root.C.clip.set(0.0F, 0.0F, paramInt1, paramInt2);
    this.root.win.set(0.0F, 0.0F, paramInt1, paramInt2);
    this.root.doResolutionChanged();
  }

  public void doMouseButton(int paramInt, boolean paramBoolean) {
    if (!this.bMouseActive) return;
    this.root.doMouseButton(paramInt, paramBoolean, this.root.mousePos.x, this.root.mousePos.y);
  }
  public void doMouseMove(int paramInt1, int paramInt2, int paramInt3) {
    if (!this.bMouseActive) return;
    this.root.doMouseMove(paramInt1, paramInt2, paramInt3);
  }
  public void doMouseAbsMove(int paramInt1, int paramInt2, int paramInt3) {
    if (!this.bMouseActive) return;
    this.root.doMouseAbsMove(paramInt1, paramInt2, paramInt3);
  }

  public void doKeyboardKey(int paramInt, boolean paramBoolean) {
    if (!this.bKeyboardActive) return;
    this.root.doKeyboardKey(paramInt, paramBoolean);
  }
  public void doKeyboardChar(char paramChar) {
    if (!this.bKeyboardActive) return;
    this.root.doKeyboardChar(paramChar);
  }

  public void doJoyButton(int paramInt1, int paramInt2, boolean paramBoolean) {
    if (!this.bJoyActive) return;
    this.root.doJoyButton(paramInt1, paramInt2, paramBoolean);
  }
  public void doJoyMove(int paramInt1, int paramInt2, int paramInt3) {
    if (!this.bJoyActive) return;
    this.root.doJoyMove(paramInt1, paramInt2, paramInt3);
  }
  public void doJoyPov(int paramInt1, int paramInt2) {
    if (!this.bJoyActive) return;
    this.root.doJoyPov(paramInt1, paramInt2);
  }
  public void doJoyPoll() {
    if (!this.bJoyActive) return;
    this.root.doJoyPoll();
  }

  public void doCreate(GCanvas paramGCanvas, GWindowRoot paramGWindowRoot, GWindowLookAndFeel paramGWindowLookAndFeel) {
    this.root = paramGWindowRoot;
    paramGWindowRoot.beforeCreate();
    paramGWindowRoot.C = paramGCanvas;
    paramGWindowRoot.manager = this;
    paramGWindowRoot.win = new GRegion(0.0F, 0.0F, paramGCanvas.size.dx, paramGCanvas.size.dy);
    paramGWindowRoot.lookAndFeel = paramGWindowLookAndFeel;
    paramGWindowLookAndFeel.init(paramGWindowRoot);
    paramGWindowRoot.created();
  }
}