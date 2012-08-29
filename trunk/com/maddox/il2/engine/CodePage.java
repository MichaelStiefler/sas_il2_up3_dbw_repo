package com.maddox.il2.engine;

public class CodePage extends FObj
{
  public CodePage(int paramInt)
  {
    super(paramInt);
  }

  public int translate(int paramInt) {
    return Translate(this.cppObj, paramInt);
  }

  public int size()
  {
    return Size(this.cppObj);
  }

  public String nameCP()
  {
    return NameCP(this.cppObj); } 
  public static native CodePage get(String paramString);

  public static native CodePage getApp();

  public static native void setApp(String paramString);

  public static native CodePage getSystemAnsi();

  public static native CodePage getSystemOem();

  private native int Translate(int paramInt1, int paramInt2);

  private native int Size(int paramInt);

  private native String NameCP(int paramInt);

  static { GObj.loadNative();
  }
}