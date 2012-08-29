package com.maddox.rts;

public class BackgroundLoop
{
  protected BackgroundTask curTask;
  protected Listeners listeners = new Listeners();

  protected void step()
  {
    RTSConf.cur.loopMsgs();
  }

  protected void setThisAsCurrent()
  {
    BackgroundLoop localBackgroundLoop = RTSConf.cur.backgroundLoop;
    RTSConf.cur.backgroundLoop = this;
    if (localBackgroundLoop != null) {
      Object[] arrayOfObject = localBackgroundLoop.listeners.get();
      if (arrayOfObject != null) {
        for (int i = 0; i < arrayOfObject.length; i++) {
          localBackgroundLoop.listeners.removeListener(arrayOfObject[i]);
          this.listeners.addListener(arrayOfObject[i]);
        }
      }
      this.curTask = localBackgroundLoop.curTask;
    }
  }
}