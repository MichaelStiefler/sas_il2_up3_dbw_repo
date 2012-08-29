package com.maddox.opengl;

import com.maddox.rts.Listeners;
import com.maddox.rts.MainWindow;
import com.maddox.rts.MsgAddListenerListener;
import com.maddox.rts.MsgMainWindowListener;
import com.maddox.rts.MsgRemoveListenerListener;
import com.maddox.rts.RTSConf;

public class GLContext
  implements MsgAddListenerListener, MsgRemoveListenerListener, MsgMainWindowListener
{
  private static GLContext current = null;
  private GLCaps caps;
  private boolean bEnableMessages = true;
  private boolean bCreated;
  private boolean bDoubleBuffer;
  private int width;
  private int height;
  private int iWND;
  private int iDC;
  private int iRC;
  private Listeners listeners = new Listeners();
  private MsgGLContext msg = new MsgGLContext();
  private boolean bMain;

  public static boolean isValid(GLContext paramGLContext)
  {
    if (paramGLContext != null) return paramGLContext.isCreated();
    return false;
  }

  public GLContext(GLInitCaps paramGLInitCaps)
  {
    this.caps = paramGLInitCaps;
    this.bCreated = false;
    this.width = 0;
    this.height = 0;
  }

  public static Object lockObject()
  {
    return Provider.lockObject();
  }

  public static boolean makeCurrent(GLContext paramGLContext)
  {
    if (paramGLContext != null) {
      if (paramGLContext != current) {
        if ((paramGLContext.isCreated()) && (paramGLContext.MakeCurrent())) {
          current = paramGLContext;
        } else {
          if (current != null) {
            MakeNotCurrent();
            current = null;
          }
          return false;
        }
      }
    }
    else if (current != null) {
      MakeNotCurrent();
      current = null;
    }

    return true;
  }

  public static GLContext getCurrent() {
    return current;
  }
  public GLCaps getCaps() {
    return this.caps;
  }
  public boolean isCreated() {
    return this.bCreated;
  }
  public boolean isMain() { return this.bMain; }

  public int width() {
    return this.width;
  }
  public int height() {
    return this.height;
  }
  public int hWnd() { return this.iWND;
  }

  public void createWin32(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3)
    throws GLContextException
  {
    synchronized (lockObject()) {
      makeCurrent(null);

      if (isCreated()) {
        destroy();
      }
      this.width = paramInt2;
      this.height = paramInt3;

      int[] arrayOfInt1 = this.caps.getCaps();
      CreateWin32(paramInt1, arrayOfInt1);
      Provider.contextCreated();
      this.caps.setCaps(arrayOfInt1);
      this.bCreated = true;
      this.bDoubleBuffer = this.caps.isDoubleBuffered();
      if (paramBoolean)
        RTSConf.cur.mainWindow.msgAddListener(this, null);
      this.bMain = this.bMain;
      makeCurrent(this);

      int[] arrayOfInt2 = new int[1];
      arrayOfInt2[0] = 0;
      gl.GetIntegerv(3379, arrayOfInt2);
      if (arrayOfInt2[0] <= 256) {
        this.caps.stencilBits = 0;
      }
      sendAction(1);
    }
  }

  public void changeWin32(GLInitCaps paramGLInitCaps, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3)
    throws GLContextException
  {
    if (this.bCreated)
      destroy();
    this.caps = paramGLInitCaps;
    createWin32(paramInt1, paramBoolean, paramInt2, paramInt3);
  }

  public void destroy()
  {
    synchronized (lockObject()) {
      makeCurrent(null);

      if (!isCreated()) {
        return;
      }
      Destroy();
      Provider.contextDestroyed();

      this.bCreated = false;
      current = null;
      RTSConf.cur.mainWindow.msgRemoveListener(this, null);
      sendAction(2);
    }
  }

  public boolean swapBuffers()
  {
    if ((isCreated()) && (getCurrent() == this)) {
      if (this.bDoubleBuffer) {
        return SwapBuffers();
      }
      gl.Flush();
      return true;
    }
    return false;
  }

  public void setSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    sendAction(4);
  }

  public void msgMainWindow(int paramInt) {
    switch (paramInt) { case 2:
      destroy();
      break;
    case 4:
      setSize(RTSConf.cur.mainWindow.width(), RTSConf.cur.mainWindow.height());
      break;
    }
  }

  public Object[] getListeners()
  {
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
  public void setMessagesEnable(boolean paramBoolean) { this.bEnableMessages = paramBoolean; }

  public void sendAction(int paramInt) {
    if (!this.bEnableMessages) return;
    Object[] arrayOfObject = this.listeners.get();
    if (arrayOfObject == null) return;
    for (int i = 0; i < arrayOfObject.length; i++)
      this.msg.Send(paramInt, arrayOfObject[i]);
  }

  private GLContext()
  {
  }

  private native void CreateWin32(int paramInt, int[] paramArrayOfInt)
    throws GLContextException;

  private native void Change(int[] paramArrayOfInt)
    throws GLContextException;

  private native void Destroy();

  private native boolean SwapBuffers();

  private native boolean MakeCurrent();

  private static native void MakeNotCurrent();

  static
  {
    gl.loadNative();
  }
}