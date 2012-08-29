// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Provider.java

package com.maddox.opengl;

import java.util.Vector;

// Referenced classes of package com.maddox.opengl:
//            ProviderException, GLCaps, GLProfile, gl

public final class Provider
{

    public Provider()
    {
    }

    public static final native boolean isGLloaded();

    public static final native boolean isGLUloaded();

    public static final java.lang.String GLname()
        throws com.maddox.opengl.ProviderException
    {
        if(!com.maddox.opengl.Provider.isGLloaded())
            throw new ProviderException("OpenGL provider NOT loaded");
        else
            return com.maddox.opengl.Provider.nGLname();
    }

    public static final java.lang.String GLUname()
        throws com.maddox.opengl.ProviderException
    {
        if(!com.maddox.opengl.Provider.isGLUloaded())
            throw new ProviderException("GLU provider NOT loaded");
        else
            return com.maddox.opengl.Provider.nGLUname();
    }

    public static final void GLload(java.lang.String s)
        throws com.maddox.opengl.ProviderException
    {
        if(countContexts != 0)
            throw new ProviderException("Contains created contexts");
        if(!com.maddox.opengl.Provider.nGLload(s))
            throw new ProviderException("file: " + s + " NOT found");
        else
            return;
    }

    public static final void GLUload(java.lang.String s)
        throws com.maddox.opengl.ProviderException
    {
        if(countContexts != 0)
            throw new ProviderException("Contains created contexts");
        if(!com.maddox.opengl.Provider.nGLUload(s))
            throw new ProviderException("file: " + s + " NOT found");
        else
            return;
    }

    public static final void GLunload()
        throws com.maddox.opengl.ProviderException
    {
        if(countContexts != 0)
            throw new ProviderException("Contains created contexts");
        if(!com.maddox.opengl.Provider.isGLloaded())
        {
            throw new ProviderException("OpenGL provider NOT loaded");
        } else
        {
            com.maddox.opengl.Provider.nGLunload();
            return;
        }
    }

    public static final void GLUunload()
        throws com.maddox.opengl.ProviderException
    {
        if(countContexts != 0)
            throw new ProviderException("Contains created contexts");
        if(!com.maddox.opengl.Provider.isGLUloaded())
        {
            throw new ProviderException("GLU provider NOT loaded");
        } else
        {
            com.maddox.opengl.Provider.nGLUunload();
            return;
        }
    }

    public static final com.maddox.opengl.GLCaps[] getGLCaps()
        throws com.maddox.opengl.ProviderException
    {
        if(!com.maddox.opengl.Provider.isGLloaded())
            throw new ProviderException("OpenGL provider NOT loaded");
        java.util.Vector vector = new Vector();
        int i = 1;
        do
        {
            com.maddox.opengl.GLCaps glcaps = new GLCaps();
            int ai[] = glcaps.getCaps();
            int j = com.maddox.opengl.Provider.nGetGLCaps(i, ai);
            if(j != 0)
            {
                if(j == 1)
                {
                    glcaps.setCaps(ai);
                    vector.addElement(glcaps);
                }
                i++;
            } else
            {
                com.maddox.opengl.GLCaps aglcaps[] = new com.maddox.opengl.GLCaps[vector.size()];
                vector.copyInto(aglcaps);
                return aglcaps;
            }
        } while(true);
    }

    public static java.lang.Object lockObject()
    {
        return lockObject;
    }

    public static native void setEnableEmptyPaint(boolean flag);

    public static native boolean isEnableEmptyPaint();

    public static native void setEnableProfile(boolean flag);

    public static native boolean isEnableProfile();

    public static native void setEnableBW(boolean flag);

    public static native boolean isEnableBW();

    public static void getProfile(com.maddox.opengl.GLProfile glprofile)
    {
        com.maddox.opengl.Provider.GetProfile(_prof);
        glprofile.frames = _prof[0];
        glprofile.allTextures = _prof[1];
        glprofile.allBytesTextures = _prof[2];
        glprofile.vertexes = _prof[3];
        glprofile.vertexesClip = _prof[4];
        glprofile.triangles = _prof[5];
        glprofile.trianglesClip = _prof[6];
        glprofile.bindTextures = _prof[7];
        glprofile.textures = _prof[8];
        glprofile.kBytesTextures = _prof[9];
    }

    private static native void GetProfile(int ai[]);

    public static void setEnablePausedProfile(boolean flag)
    {
        bEnablePausedProfile = flag;
    }

    public static boolean isEnablePausedProfile()
    {
        return bEnablePausedProfile;
    }

    public static void setPauseProfile(boolean flag)
    {
        if(bEnablePausedProfile)
            com.maddox.opengl.Provider.SetPauseProfile(flag);
    }

    private static native void SetPauseProfile(boolean flag);

    public static native boolean isPauseProfile();

    protected static void contextCreated()
    {
        countContexts++;
    }

    protected static void contextDestroyed()
    {
        countContexts--;
    }

    public static int countContexts()
    {
        return countContexts;
    }

    private static final native java.lang.String nGLname();

    private static final native java.lang.String nGLUname();

    private static final native boolean nGLload(java.lang.String s);

    private static final native boolean nGLUload(java.lang.String s);

    private static final native void nGLunload();

    private static final native void nGLUunload();

    private static final native int nGetGLCaps(int i, int ai[]);

    public static final java.lang.String GL_NOT_LOADED = "OpenGL provider NOT loaded";
    public static final java.lang.String GLU_NOT_LOADED = "GLU provider NOT loaded";
    public static final java.lang.String CREATED_CONTEXTS = "Contains created contexts";
    private static int _prof[] = new int[10];
    private static boolean bEnablePausedProfile = true;
    private static int countContexts = 0;
    private static java.lang.Object lockObject = new Object();

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
