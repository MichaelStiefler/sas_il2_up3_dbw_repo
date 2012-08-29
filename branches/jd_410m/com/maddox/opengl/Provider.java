package com.maddox.opengl;

import java.util.Vector;

public final class Provider
{
  public static final String GL_NOT_LOADED = "OpenGL provider NOT loaded";
  public static final String GLU_NOT_LOADED = "GLU provider NOT loaded";
  public static final String CREATED_CONTEXTS = "Contains created contexts";
  private static int[] _prof = new int[10];

  private static boolean bEnablePausedProfile = true;

  private static int countContexts = 0;

  private static Object lockObject = new Object();

  public static final native boolean isGLloaded();

  public static final native boolean isGLUloaded();

  public static final String GLname()
    throws ProviderException
  {
    if (!isGLloaded())
      throw new ProviderException("OpenGL provider NOT loaded");
    return nGLname();
  }

  public static final String GLUname()
    throws ProviderException
  {
    if (!isGLUloaded())
      throw new ProviderException("GLU provider NOT loaded");
    return nGLUname();
  }

  public static final void GLload(String paramString)
    throws ProviderException
  {
    if (countContexts != 0)
      throw new ProviderException("Contains created contexts");
    if (!nGLload(paramString))
      throw new ProviderException("file: " + paramString + " NOT found");
  }

  public static final void GLUload(String paramString)
    throws ProviderException
  {
    if (countContexts != 0)
      throw new ProviderException("Contains created contexts");
    if (!nGLUload(paramString))
      throw new ProviderException("file: " + paramString + " NOT found");
  }

  public static final void GLunload()
    throws ProviderException
  {
    if (countContexts != 0)
      throw new ProviderException("Contains created contexts");
    if (!isGLloaded())
      throw new ProviderException("OpenGL provider NOT loaded");
    nGLunload();
  }

  public static final void GLUunload()
    throws ProviderException
  {
    if (countContexts != 0)
      throw new ProviderException("Contains created contexts");
    if (!isGLUloaded())
      throw new ProviderException("GLU provider NOT loaded");
    nGLUunload();
  }

  public static final GLCaps[] getGLCaps()
    throws ProviderException
  {
    if (!isGLloaded())
      throw new ProviderException("OpenGL provider NOT loaded");
    Vector localVector = new Vector();
    int i = 1;
    while (true) {
      localObject = new GLCaps();
      int[] arrayOfInt = ((GLCaps)localObject).getCaps();
      int j = nGetGLCaps(i, arrayOfInt);
      if (j == 0)
        break;
      if (j == 1) {
        ((GLCaps)localObject).setCaps(arrayOfInt);
        localVector.addElement(localObject);
      }
      i++;
    }
    Object localObject = new GLCaps[localVector.size()];
    localVector.copyInto(localObject);
    return (GLCaps)localObject;
  }
  public static Object lockObject() {
    return lockObject; } 
  public static native void setEnableEmptyPaint(boolean paramBoolean);

  public static native boolean isEnableEmptyPaint();

  public static native void setEnableProfile(boolean paramBoolean);

  public static native boolean isEnableProfile();

  public static native void setEnableBW(boolean paramBoolean);

  public static native boolean isEnableBW();

  public static void getProfile(GLProfile paramGLProfile) { GetProfile(_prof);
    paramGLProfile.frames = _prof[0];
    paramGLProfile.allTextures = _prof[1];
    paramGLProfile.allBytesTextures = _prof[2];
    paramGLProfile.vertexes = _prof[3];
    paramGLProfile.vertexesClip = _prof[4];
    paramGLProfile.triangles = _prof[5];
    paramGLProfile.trianglesClip = _prof[6];
    paramGLProfile.bindTextures = _prof[7];
    paramGLProfile.textures = _prof[8];
    paramGLProfile.kBytesTextures = _prof[9]; }

  private static native void GetProfile(int[] paramArrayOfInt);

  public static void setEnablePausedProfile(boolean paramBoolean) {
    bEnablePausedProfile = paramBoolean;
  }
  public static boolean isEnablePausedProfile() { return bEnablePausedProfile; }

  public static void setPauseProfile(boolean paramBoolean)
  {
    if (bEnablePausedProfile) SetPauseProfile(paramBoolean);  } 
  private static native void SetPauseProfile(boolean paramBoolean);

  public static native boolean isPauseProfile();

  protected static void contextCreated() { countContexts += 1; } 
  protected static void contextDestroyed() { countContexts -= 1; }

  public static int countContexts() {
    return countContexts; } 
  private static final native String nGLname();

  private static final native String nGLUname();

  private static final native boolean nGLload(String paramString);

  private static final native boolean nGLUload(String paramString);

  private static final native void nGLunload();

  private static final native void nGLUunload();

  private static final native int nGetGLCaps(int paramInt, int[] paramArrayOfInt);

  static { gl.loadNative();
  }
}