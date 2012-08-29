package com.maddox.opengl;

public final class GLInitCaps extends GLCaps
{
  public GLInitCaps()
  {
  }

  public GLInitCaps(GLCaps paramGLCaps)
  {
    super(paramGLCaps);
  }

  public final void setDevice(int paramInt)
  {
    this.deviceType = paramInt;
  }

  public final void setDoubleBuffered(boolean paramBoolean)
  {
    this.doubleBuffered = (paramBoolean ? 1 : 0);
  }

  public final void setStereo(boolean paramBoolean)
  {
    this.stereo = (paramBoolean ? 1 : 0);
  }

  public final void setPixelType(int paramInt)
  {
    this.pixelType = paramInt;
  }

  public final void setColourBits(int paramInt)
  {
    this.colourBits = paramInt;
  }

  public final void setAccumBits(int paramInt)
  {
    this.accumBits = paramInt;
  }

  public final void setDepthBits(int paramInt)
  {
    this.depthBits = paramInt;
  }

  public final void setStencilBits(int paramInt)
  {
    this.stencilBits = paramInt;
  }

  public final void setAuxiliaryBuffers(int paramInt)
  {
    this.auxiliaryBuffers = paramInt;
  }
}