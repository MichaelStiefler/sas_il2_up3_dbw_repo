package com.maddox.rts;

public final class SFS
{
  public static final int FLAG_SYSTEM_BUFFERING = 0;
  public static final int FLAG_NO_BUFFERING = 1;
  public static final int FLAG_INTERNAL_BUFFERING = 2;
  public static final int FLAG_MAPPING = 3;

  public static final void mount(String paramString)
    throws SFSException
  {
    mount(paramString, 0);
  }

  public static final native void mount(String paramString, int paramInt) throws SFSException;

  public static final void mountAs(String paramString1, String paramString2) throws SFSException {
    mountAs(paramString1, paramString2, 0); } 
  public static final native void mountAs(String paramString1, String paramString2, int paramInt) throws SFSException;

  public static final native void unMount(String paramString) throws SFSException;

  public static final native void unMountPath(String paramString) throws SFSException;

  public static final native void setCacheBlockSize(boolean paramBoolean, int paramInt);

  public static final native int getCacheBlockSize(boolean paramBoolean);

  public static final native void setCacheSize(boolean paramBoolean, int paramInt);

  public static final native int getCacheSize(boolean paramBoolean);

  static { RTS.loadNative();
  }
}