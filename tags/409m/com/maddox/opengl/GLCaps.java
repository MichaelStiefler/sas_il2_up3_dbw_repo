// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLCaps.java

package com.maddox.opengl;


// Referenced classes of package com.maddox.opengl:
//            gl

public class GLCaps
{

    public GLCaps()
    {
        deviceType = 1;
        doubleBuffered = 1;
        stereo = 0;
        pixelType = 1;
        colourBits = 24;
        redBits = 0;
        redShift = 0;
        greenBits = 0;
        greenShift = 0;
        blueBits = 0;
        blueShift = 0;
        alphaBits = 0;
        alphaShift = 0;
        accumBits = 0;
        accumRedBits = 0;
        accumGreenBits = 0;
        accumBlueBits = 0;
        accumAlphaBits = 0;
        depthBits = 16;
        stencilBits = 0;
        auxiliaryBuffers = 0;
    }

    public GLCaps(com.maddox.opengl.GLCaps glcaps)
    {
        deviceType = 1;
        doubleBuffered = 1;
        stereo = 0;
        pixelType = 1;
        colourBits = 24;
        redBits = 0;
        redShift = 0;
        greenBits = 0;
        greenShift = 0;
        blueBits = 0;
        blueShift = 0;
        alphaBits = 0;
        alphaShift = 0;
        accumBits = 0;
        accumRedBits = 0;
        accumGreenBits = 0;
        accumBlueBits = 0;
        accumAlphaBits = 0;
        depthBits = 16;
        stencilBits = 0;
        auxiliaryBuffers = 0;
        pixelType = glcaps.pixelType;
        colourBits = glcaps.colourBits;
        redBits = glcaps.redBits;
        redShift = glcaps.redShift;
        greenBits = glcaps.greenBits;
        greenShift = glcaps.greenShift;
        blueBits = glcaps.blueBits;
        blueShift = glcaps.blueShift;
        alphaBits = glcaps.alphaBits;
        alphaShift = glcaps.alphaShift;
        accumBits = glcaps.accumBits;
        accumRedBits = glcaps.accumRedBits;
        accumGreenBits = glcaps.accumGreenBits;
        accumBlueBits = glcaps.accumBlueBits;
        accumAlphaBits = glcaps.accumAlphaBits;
        depthBits = glcaps.depthBits;
        stencilBits = glcaps.stencilBits;
        auxiliaryBuffers = glcaps.auxiliaryBuffers;
    }

    public final int getDevice()
    {
        return deviceType;
    }

    public final boolean isDoubleBuffered()
    {
        return doubleBuffered == 1;
    }

    public final boolean isStereo()
    {
        return stereo == 1;
    }

    public final int getPixelType()
    {
        return pixelType;
    }

    public final int getColourBits()
    {
        return colourBits;
    }

    public final int getRedBits()
    {
        return redBits;
    }

    public final int getRedShift()
    {
        return redShift;
    }

    public final int getGreenBits()
    {
        return greenBits;
    }

    public final int getGreenShift()
    {
        return greenShift;
    }

    public final int getBlueBits()
    {
        return blueBits;
    }

    public final int getBlueShift()
    {
        return blueShift;
    }

    public final int getAlphaBits()
    {
        return alphaBits;
    }

    public final int getAlphaShift()
    {
        return alphaShift;
    }

    public final int getAccumBits()
    {
        return accumBits;
    }

    public final int getAccumRedBits()
    {
        return accumRedBits;
    }

    public final int getAccumGreenBits()
    {
        return accumGreenBits;
    }

    public final int getAccumBlueBits()
    {
        return accumBlueBits;
    }

    public final int getAccumAlphaBits()
    {
        return accumAlphaBits;
    }

    public final int getDepthBits()
    {
        return depthBits;
    }

    public final int getStencilBits()
    {
        return stencilBits;
    }

    public final int getAuxiliaryBuffers()
    {
        return auxiliaryBuffers;
    }

    protected final int[] getCaps()
    {
        int ai[] = new int[21];
        ai[0] = deviceType;
        ai[1] = doubleBuffered;
        ai[2] = stereo;
        ai[3] = pixelType;
        ai[4] = colourBits;
        ai[5] = redBits;
        ai[6] = redShift;
        ai[7] = greenBits;
        ai[8] = greenShift;
        ai[9] = blueBits;
        ai[10] = blueShift;
        ai[11] = alphaBits;
        ai[12] = alphaShift;
        ai[13] = accumBits;
        ai[14] = accumRedBits;
        ai[15] = accumGreenBits;
        ai[16] = accumBlueBits;
        ai[17] = accumAlphaBits;
        ai[18] = depthBits;
        ai[19] = stencilBits;
        ai[20] = auxiliaryBuffers;
        return ai;
    }

    protected final void setCaps(int ai[])
    {
        deviceType = ai[0];
        doubleBuffered = ai[1];
        stereo = ai[2];
        pixelType = ai[3];
        colourBits = ai[4];
        redBits = ai[5];
        redShift = ai[6];
        greenBits = ai[7];
        greenShift = ai[8];
        blueBits = ai[9];
        blueShift = ai[10];
        alphaBits = ai[11];
        alphaShift = ai[12];
        accumBits = ai[13];
        accumRedBits = ai[14];
        accumGreenBits = ai[15];
        accumBlueBits = ai[16];
        accumAlphaBits = ai[17];
        depthBits = ai[18];
        stencilBits = ai[19];
        auxiliaryBuffers = ai[20];
    }

    public static final int DRAW_TO_WINDOW = 1;
    public static final int DRAW_TO_BITMAP = 0;
    protected int deviceType;
    public static final int DOUBLEBUFFER = 1;
    public static final int SINGLEBUFFER = 0;
    protected int doubleBuffered;
    public static final int STEREO = 1;
    public static final int STEREO_DONTCARE = 0;
    protected int stereo;
    public static final int TYPE_RGBA = 1;
    public static final int TYPE_COLOURINDEX = 0;
    protected int pixelType;
    protected int colourBits;
    protected int redBits;
    protected int redShift;
    protected int greenBits;
    protected int greenShift;
    protected int blueBits;
    protected int blueShift;
    protected int alphaBits;
    protected int alphaShift;
    protected int accumBits;
    protected int accumRedBits;
    protected int accumGreenBits;
    protected int accumBlueBits;
    protected int accumAlphaBits;
    protected int depthBits;
    protected int stencilBits;
    protected int auxiliaryBuffers;

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
