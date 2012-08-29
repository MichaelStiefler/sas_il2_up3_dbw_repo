package com.maddox.opengl.util;

import com.maddox.TexImage.TexImage;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.gl;
import java.io.FileOutputStream;

public class ScrShot
{
  private String prefixName;
  private int shotNum = 0;
  private int dx;
  private int dy;
  private TexImage img = new TexImage();

  private void swapRB() {
    int i = this.dx * 3;
    for (int j = 0; j < this.dy; j++)
      for (int k = 0; k < i; k += 3) {
        int m = this.img.image(k, j);
        int n = this.img.image(k + 2, j);
        this.img.image(k, j, n);
        this.img.image(k + 2, j, m);
      }
  }

  public void grab()
  {
    String str;
    if (this.shotNum > 999) str = this.prefixName + this.shotNum + ".tga";
    else if (this.shotNum > 99) str = this.prefixName + "0" + this.shotNum + ".tga";
    else if (this.shotNum > 9) str = this.prefixName + "00" + this.shotNum + ".tga"; else
      str = this.prefixName + "000" + this.shotNum + ".tga";
    gl.ReadPixels(0, 0, this.dx, this.dy, 6407, 5121, this.img.image);
    try {
      FileOutputStream localFileOutputStream = new FileOutputStream(str);

      localFileOutputStream.write(0);
      localFileOutputStream.write(0);
      localFileOutputStream.write(2);
      localFileOutputStream.write(new byte[5]);
      localFileOutputStream.write(0); localFileOutputStream.write(0);
      localFileOutputStream.write(0); localFileOutputStream.write(0);
      localFileOutputStream.write((short)this.dx); localFileOutputStream.write((short)(this.dx >> 8));
      localFileOutputStream.write((short)this.dy); localFileOutputStream.write((short)(this.dy >> 8));
      localFileOutputStream.write((byte)(this.img.BPP * 8));
      localFileOutputStream.write(0);
      swapRB();
      localFileOutputStream.write(this.img.image, 0, this.dx * this.dy * this.img.BPP);

      localFileOutputStream.close(); } catch (Exception localException) {
    }
    this.shotNum += 1;
  }

  public ScrShot(String paramString) {
    this.dx = GLContext.getCurrent().width();
    this.dy = GLContext.getCurrent().height();
    this.prefixName = paramString;
    this.img.set(this.dx, this.dy, 6407);
  }
}