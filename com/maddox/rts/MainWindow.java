package com.maddox.rts;

public class MainWindow
  implements MsgAddListenerListener, MsgRemoveListenerListener
{
  protected int hWnd = 0;
  protected int cx;
  protected int cy;
  protected int cxFull;
  protected int cyFull;
  protected int posX;
  protected int posY;
  protected boolean bFocused;
  protected boolean bFullScreen;
  protected boolean bCreated;
  private boolean bEnableMessages = true;

  private Listeners listeners = new Listeners();
  private MsgMainWindow msg;

  public boolean isCreated()
  {
    return this.bCreated; } 
  public boolean isFullScreen() { return this.bFullScreen; } 
  public boolean isFocused() { return this.bFocused; } 
  public int width() { return this.cx; } 
  public int height() { return this.cy; } 
  public int widthFull() { return this.cxFull; } 
  public int heightFull() { return this.cyFull; } 
  public int posX() { return this.posX; } 
  public int posY() { return this.posY; }

  public static native int componentWnd(Object paramObject);

  public int hWnd() {
    return this.hWnd;
  }
  public int hContextWnd() { return this.hWnd; } 
  public void setFocus() {
  }
  public void setSize(int paramInt1, int paramInt2) {
  }
  public void setPosSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
  }

  public void setTitle(String paramString) {
  }

  public boolean isIconic() {
    return false;
  }
  public void showIconic() {
  }
  public void showNormal() {
  }

  public static MainWindow adapter() {
    return RTSConf.cur.mainWindow;
  }
  public Object[] getListeners() {
    return this.listeners.get();
  }
  public void msgAddListener(Object paramObject1, Object paramObject2) {
    this.listeners.addListener(paramObject1);
  }
  public void msgRemoveListener(Object paramObject1, Object paramObject2) {
    this.listeners.removeListener(paramObject1);
  }
  public boolean isMessagesEnable() {
    return this.bEnableMessages; } 
  public void setMessagesEnable(boolean paramBoolean) { this.bEnableMessages = paramBoolean;
  }

  public void SendAction(int paramInt)
  {
    if (!this.bEnableMessages) return;
    if (paramInt == 2) RTSConf.setRequestExitApp(true);

    Object[] arrayOfObject = this.listeners.get();
    if (arrayOfObject == null) return;
    if (this.msg == null) this.msg = new MsgMainWindow();
    for (int i = 0; i < arrayOfObject.length; i++)
      this.msg.Send(paramInt, arrayOfObject[i]);
  }

  protected MainWindow() {
    this.bCreated = false;
    this.bFullScreen = false;
  }
}