// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLInitCaps.java

package com.maddox.opengl;


// Referenced classes of package com.maddox.opengl:
//            GLCaps

public final class GLInitCaps extends com.maddox.opengl.GLCaps
{

    public GLInitCaps()
    {
    }

    public GLInitCaps(com.maddox.opengl.GLCaps glcaps)
    {
        super(glcaps);
    }

    public final void setDevice(int i)
    {
        deviceType = i;
    }

    public final void setDoubleBuffered(boolean flag)
    {
        doubleBuffered = flag ? 1 : 0;
    }

    public final void setStereo(boolean flag)
    {
        stereo = flag ? 1 : 0;
    }

    public final void setPixelType(int i)
    {
        pixelType = i;
    }

    public final void setColourBits(int i)
    {
        colourBits = i;
    }

    public final void setAccumBits(int i)
    {
        accumBits = i;
    }

    public final void setDepthBits(int i)
    {
        depthBits = i;
    }

    public final void setStencilBits(int i)
    {
        stencilBits = i;
    }

    public final void setAuxiliaryBuffers(int i)
    {
        auxiliaryBuffers = i;
    }
}
