package com.maddox.opengl;

public class GLCaps
{
  public static final int DRAW_TO_WINDOW = 1;
  public static final int DRAW_TO_BITMAP = 0;
  protected int deviceType = 1;
  public static final int DOUBLEBUFFER = 1;
  public static final int SINGLEBUFFER = 0;
  protected int doubleBuffered = 1;
  public static final int STEREO = 1;
  public static final int STEREO_DONTCARE = 0;
  protected int stereo = 0;
  public static final int TYPE_RGBA = 1;
  public static final int TYPE_COLOURINDEX = 0;
  protected int pixelType = 1;

  protected int colourBits = 24;
  protected int redBits = 0;
  protected int redShift = 0;
  protected int greenBits = 0;
  protected int greenShift = 0;
  protected int blueBits = 0;
  protected int blueShift = 0;
  protected int alphaBits = 0;
  protected int alphaShift = 0;
  protected int accumBits = 0;
  protected int accumRedBits = 0;
  protected int accumGreenBits = 0;
  protected int accumBlueBits = 0;
  protected int accumAlphaBits = 0;
  protected int depthBits = 16;
  protected int stencilBits = 0;
  protected int auxiliaryBuffers = 0;

  public GLCaps() {
  }
  public GLCaps(GLCaps paramGLCaps) {
    this.pixelType = paramGLCaps.pixelType;
    this.colourBits = paramGLCaps.colourBits;
    this.redBits = paramGLCaps.redBits;
    this.redShift = paramGLCaps.redShift;
    this.greenBits = paramGLCaps.greenBits;
    this.greenShift = paramGLCaps.greenShift;
    this.blueBits = paramGLCaps.blueBits;
    this.blueShift = paramGLCaps.blueShift;
    this.alphaBits = paramGLCaps.alphaBits;
    this.alphaShift = paramGLCaps.alphaShift;
    this.accumBits = paramGLCaps.accumBits;
    this.accumRedBits = paramGLCaps.accumRedBits;
    this.accumGreenBits = paramGLCaps.accumGreenBits;
    this.accumBlueBits = paramGLCaps.accumBlueBits;
    this.accumAlphaBits = paramGLCaps.accumAlphaBits;
    this.depthBits = paramGLCaps.depthBits;
    this.stencilBits = paramGLCaps.stencilBits;
    this.auxiliaryBuffers = paramGLCaps.auxiliaryBuffers;
  }

  public final int getDevice()
  {
    return this.deviceType;
  }

  public final boolean isDoubleBuffered()
  {
    return this.doubleBuffered == 1;
  }

  public final boolean isStereo()
  {
    return this.stereo == 1;
  }

  public final int getPixelType()
  {
    return this.pixelType;
  }

  public final int getColourBits()
  {
    return this.colourBits;
  }

  public final int getRedBits()
  {
    return this.redBits;
  }

  public final int getRedShift()
  {
    return this.redShift;
  }

  public final int getGreenBits()
  {
    return this.greenBits;
  }

  public final int getGreenShift()
  {
    return this.greenShift;
  }

  public final int getBlueBits()
  {
    return this.blueBits;
  }

  public final int getBlueShift()
  {
    return this.blueShift;
  }

  public final int getAlphaBits()
  {
    return this.alphaBits;
  }

  public final int getAlphaShift()
  {
    return this.alphaShift;
  }

  public final int getAccumBits()
  {
    return this.accumBits;
  }

  public final int getAccumRedBits()
  {
    return this.accumRedBits;
  }

  public final int getAccumGreenBits()
  {
    return this.accumGreenBits;
  }

  public final int getAccumBlueBits()
  {
    return this.accumBlueBits;
  }

  public final int getAccumAlphaBits()
  {
    return this.accumAlphaBits;
  }

  public final int getDepthBits()
  {
    return this.depthBits;
  }

  public final int getStencilBits()
  {
    return this.stencilBits;
  }

  public final int getAuxiliaryBuffers()
  {
    return this.auxiliaryBuffers;
  }
  protected final int[] getCaps() {
    int[] arrayOfInt = new int[21];
    arrayOfInt[0] = this.deviceType;
    arrayOfInt[1] = this.doubleBuffered;
    arrayOfInt[2] = this.stereo;
    arrayOfInt[3] = this.pixelType;
    arrayOfInt[4] = this.colourBits;
    arrayOfInt[5] = this.redBits;
    arrayOfInt[6] = this.redShift;
    arrayOfInt[7] = this.greenBits;
    arrayOfInt[8] = this.greenShift;
    arrayOfInt[9] = this.blueBits;
    arrayOfInt[10] = this.blueShift;
    arrayOfInt[11] = this.alphaBits;
    arrayOfInt[12] = this.alphaShift;
    arrayOfInt[13] = this.accumBits;
    arrayOfInt[14] = this.accumRedBits;
    arrayOfInt[15] = this.accumGreenBits;
    arrayOfInt[16] = this.accumBlueBits;
    arrayOfInt[17] = this.accumAlphaBits;
    arrayOfInt[18] = this.depthBits;
    arrayOfInt[19] = this.stencilBits;
    arrayOfInt[20] = this.auxiliaryBuffers;
    return arrayOfInt;
  }

  protected final void setCaps(int[] paramArrayOfInt) {
    this.deviceType = paramArrayOfInt[0];
    this.doubleBuffered = paramArrayOfInt[1];
    this.stereo = paramArrayOfInt[2];
    this.pixelType = paramArrayOfInt[3];
    this.colourBits = paramArrayOfInt[4];
    this.redBits = paramArrayOfInt[5];
    this.redShift = paramArrayOfInt[6];
    this.greenBits = paramArrayOfInt[7];
    this.greenShift = paramArrayOfInt[8];
    this.blueBits = paramArrayOfInt[9];
    this.blueShift = paramArrayOfInt[10];
    this.alphaBits = paramArrayOfInt[11];
    this.alphaShift = paramArrayOfInt[12];
    this.accumBits = paramArrayOfInt[13];
    this.accumRedBits = paramArrayOfInt[14];
    this.accumGreenBits = paramArrayOfInt[15];
    this.accumBlueBits = paramArrayOfInt[16];
    this.accumAlphaBits = paramArrayOfInt[17];
    this.depthBits = paramArrayOfInt[18];
    this.stencilBits = paramArrayOfInt[19];
    this.auxiliaryBuffers = paramArrayOfInt[20];
  }

  static {
    gl.loadNative();
  }
}