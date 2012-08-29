package com.maddox.gwindow;

public abstract class GWindowFileBoxExec
{
  public boolean isCloseBox()
  {
    return true; } 
  public boolean isChangedBox() { return false; } 
  public boolean isReturnResult() { return true; } 
  public void exec(GWindowFileBox paramGWindowFileBox, String paramString) {
    paramGWindowFileBox.endExec();
  }
}