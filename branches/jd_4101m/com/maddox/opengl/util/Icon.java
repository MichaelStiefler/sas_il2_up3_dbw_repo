package com.maddox.opengl.util;

import com.maddox.TexImage.TexImage;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import java.io.PrintStream;

public class Icon
  implements MsgGLContextListener
{
  private int id = 0;
  private String tgaName = null;
  private int szx;
  private int szy;

  public void msgGLContext(int paramInt)
  {
    if ((paramInt == 8) && (this.tgaName != null) && (this.id != 0) && 
      (!loadTex(this.tgaName)))
      System.out.println("Icon not reloaded: " + this.tgaName);
  }

  private void resetID()
  {
    if (this.id != 0) {
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = this.id;
      gl.DeleteTextures(1, arrayOfInt);
      this.id = 0;
    }
  }

  private boolean allocID() {
    if (this.id != 0) resetID();
    int[] arrayOfInt = { 0 };
    gl.GenTextures(1, arrayOfInt);
    this.id = arrayOfInt[0];
    return this.id != 0;
  }
  private boolean loadTex(String paramString) {
    TexImage localTexImage = new TexImage();
    try {
      localTexImage.LoadTGA(paramString);
    } catch (Exception localException) {
      return false;
    }
    if (!allocID()) return false;
    gl.BindTexture(3553, this.id);
    gl.TexParameteri(3553, 10242, 10497);
    gl.TexParameteri(3553, 10243, 10497);
    gl.TexParameteri(3553, 10240, 9729);
    gl.TexParameteri(3553, 10241, 9729);
    gl.TexImage2D(3553, 0, localTexImage.type, localTexImage.sx, localTexImage.sy, 0, localTexImage.type, 5121, localTexImage.image);

    this.szx = localTexImage.sx;
    this.szy = localTexImage.sy;
    this.tgaName = paramString;
    return true;
  }
  private void quad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    gl.Begin(7);
    float f1 = 1.0F / (this.szx + 1.0F);
    float f2 = 1.0F / (this.szy + 1.0F);
    gl.TexCoord2f(f1, f2); gl.Vertex3f(paramFloat1, paramFloat2 + paramFloat5, paramFloat3);
    gl.TexCoord2f(1.0F - f1, f2); gl.Vertex3f(paramFloat1 + paramFloat4, paramFloat2 + paramFloat5, paramFloat3);
    gl.TexCoord2f(1.0F - f1, 1.0F - f2); gl.Vertex3f(paramFloat1 + paramFloat4, paramFloat2, paramFloat3);
    gl.TexCoord2f(f1, 1.0F - f2); gl.Vertex3f(paramFloat1, paramFloat2, paramFloat3);
    gl.End();
  }

  public void draw2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    if (this.id == 0) return;
    gl.BindTexture(3553, this.id);
    quad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  public void draw3D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    if (this.id == 0) return;
    gl.BindTexture(3553, this.id);
    quad(paramFloat1 - paramFloat4 / 2.0F, paramFloat2 - paramFloat5 / 2.0F, paramFloat3, paramFloat4, paramFloat5);
  }

  public Icon(String paramString)
  {
    this.id = 0;
    if (loadTex(paramString))
      GLContext.getCurrent().msgAddListener(this, null);
    else
      System.out.println("Icon not loaded: " + paramString);
  }

  public void finalize()
  {
    resetID();
  }
}