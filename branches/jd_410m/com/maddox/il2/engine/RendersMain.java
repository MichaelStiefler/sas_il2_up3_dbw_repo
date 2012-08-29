package com.maddox.il2.engine;

import com.maddox.opengl.GLContext;

public class RendersMain
{
  public static boolean bSwapBuffersResult = true;

  public static Render get(int paramInt)
  {
    return Engine.rendersMain().get(paramInt);
  }
  public static boolean isShow() {
    return Engine.rendersMain().isShow();
  }
  public static void setShow(boolean paramBoolean) {
    Engine.rendersMain().setShow(paramBoolean);
  }
  public static int frame() {
    return Engine.rendersMain().frame();
  }
  public static int width() {
    return Engine.rendersMain().width();
  }
  public static int height() {
    return Engine.rendersMain().height();
  }
  public static boolean isSaveAspect() {
    return Engine.rendersMain().isSaveAspect();
  }
  public static void setSaveAspect(boolean paramBoolean) {
    Engine.rendersMain().setSaveAspect(paramBoolean);
  }
  public static void getAspectViewPort(float[] paramArrayOfFloat) {
    Engine.rendersMain().getAspectViewPort(paramArrayOfFloat);
  }
  public static void getAspectViewPort(int[] paramArrayOfInt) {
    Engine.rendersMain().getAspectViewPort(paramArrayOfInt);
  }
  public static int getAspectViewPortWidth() {
    return Engine.rendersMain().getAspectViewPortWidth();
  }
  public static int getAspectViewPortHeight() {
    return Engine.rendersMain().getAspectViewPortHeight();
  }
  public static void getViewPort(float[] paramArrayOfFloat) {
    Engine.rendersMain().getViewPort(paramArrayOfFloat);
  }
  public static void getViewPort(int[] paramArrayOfInt) {
    Engine.rendersMain().getViewPort(paramArrayOfInt);
  }
  public static int getViewPortWidth() {
    return Engine.rendersMain().getViewPortWidth();
  }
  public static int getViewPortHeight() {
    return Engine.rendersMain().getViewPortHeight();
  }
  public static void setRenderFocus(Render paramRender) { Engine.rendersMain().setRenderFocus(paramRender); } 
  public static Render getRenderFocus() {
    return Engine.rendersMain().getRenderFocus();
  }
  public static GLContext glContext() {
    return Engine.rendersMain().glContext();
  }
  public static void setGlContext(GLContext paramGLContext) {
    Engine.rendersMain().setGlContext(paramGLContext);
  }
  public static boolean isTickPainting() {
    return Engine.rendersMain().isTickPainting();
  }
  public static void setTickPainting(boolean paramBoolean) {
    Engine.rendersMain().setTickPainting(paramBoolean);
  }
}