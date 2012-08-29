package com.maddox.TexImage;

import com.maddox.rts.SFSInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class TexImage
{
  public int type;
  public byte[] Palette;
  public int BytesPerEntry;
  public int BPP;
  public int sx;
  public int sy;
  public byte[] image;
  public static final int ALPHA = 6406;
  public static final int COLOR_INDEX = 6400;
  public static final int COLOR_INDEX8_EXT = 32997;
  public static final int LUMINANCE = 6409;
  public static final int RGB = 6407;
  public static final int RGB5 = 32848;
  public static final int RGB8 = 32849;
  public static final int RGBA = 6408;
  public static final int RGBA4 = 32854;
  public static final int RGB5_A1 = 32855;
  public static final int RGBA8 = 32856;
  private static int[] hiMask = { 248, 252, 248 };
  private static int[] erMask = { 7, 3, 7 };
  private static int RND;
  private static int[] error = new int[256];

  public TexImage()
  {
    set();
  }
  public TexImage(int paramInt1, int paramInt2, int paramInt3) {
    set(paramInt1, paramInt2, paramInt3);
  }

  public TexImage(TexImage paramTexImage) {
    set(paramTexImage);
  }

  public TexImage(TexImage paramTexImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    set(paramTexImage, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void set()
  {
    this.type = (this.sx = this.sy = 0); this.image = null;
  }

  public void set(int paramInt1, int paramInt2, int paramInt3) {
    set();
    switch (paramInt3) { default:
      return;
    case 1:
      this.type = 6409;
    case 6406:
    case 6409:
      this.BPP = 1;
      break;
    case 2:
      this.type = 32854;
    case 32848:
    case 32854:
    case 32855:
      this.BPP = 2;
      break;
    case 3:
      this.type = 6407;
    case 6407:
    case 32849:
      this.BPP = 3;
      break;
    case 4:
      this.type = 6408;
    case 6408:
    case 32856:
      this.BPP = 4;
    }

    if (this.type == 0) this.type = paramInt3;
    this.sx = paramInt1; this.sy = paramInt2;
    this.image = new byte[this.sx * this.sy * this.BPP];
  }

  public void set(TexImage paramTexImage) {
    this.type = paramTexImage.type;
    this.Palette = paramTexImage.Palette;
    this.BytesPerEntry = paramTexImage.BytesPerEntry;
    this.BPP = paramTexImage.BPP;
    this.sx = paramTexImage.sx;
    this.sy = paramTexImage.sy;
    if ((this.sx == 0) || (this.sy == 0) || (paramTexImage.image == null)) return;
    this.image = new byte[this.sx * this.sy * this.BPP];

    for (int i = 0; i < this.sy; i++)
    {
      System.arraycopy(paramTexImage.image, i * this.sx * this.BPP, this.image, i * this.sx * this.BPP, this.sx * this.BPP);
    }
  }

  public void set(TexImage paramTexImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.type = paramTexImage.type;
    this.Palette = paramTexImage.Palette;
    this.BytesPerEntry = paramTexImage.BytesPerEntry;
    this.BPP = paramTexImage.BPP;
    this.sx = paramInt3;
    this.sy = paramInt4;
    if ((this.sx == 0) || (this.sy == 0) || (paramTexImage.image == null)) return;
    this.image = new byte[paramInt3 * paramInt4 * this.BPP];

    for (int i = 0; i < paramInt4; i++)
    {
      System.arraycopy(paramTexImage.image, ((paramInt2 + i) * paramTexImage.sx + paramInt1) * this.BPP, this.image, i * this.sx * this.BPP, this.sx * this.BPP);
    }
  }

  public final byte I(int paramInt1, int paramInt2)
  {
    return image(paramInt1, paramInt2);
  }

  public final byte R(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP)];
  }

  public final byte G(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 1)];
  }

  public final byte B(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 2)];
  }

  public final byte A(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 3)];
  }

  public final int intI(int paramInt1, int paramInt2)
  {
    return image(paramInt1, paramInt2) & 0xFF;
  }

  public final int intR(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP)] & 0xFF;
  }

  public final int intG(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 1)] & 0xFF;
  }

  public final int intB(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 2)] & 0xFF;
  }

  public final int intA(int paramInt1, int paramInt2)
  {
    return this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 3)] & 0xFF;
  }

  public final void getPixel(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    int i = (paramInt2 * this.sx + paramInt1) * this.BPP;
    if ((i < 0) || (i + this.BPP >= this.image.length)) {
      switch (this.BPP) { case 4:
        paramArrayOfByte[3] = 25;
      case 3:
        paramArrayOfByte[2] = 25;
      case 2:
        paramArrayOfByte[1] = 25;
      case 1:
        paramArrayOfByte[0] = 25;
      }
      return;
    }
    switch (this.BPP) { case 4:
      paramArrayOfByte[3] = this.image[(i + 3)];
    case 3:
      paramArrayOfByte[2] = this.image[(i + 2)];
    case 2:
      paramArrayOfByte[1] = this.image[(i + 1)];
    case 1:
      paramArrayOfByte[0] = this.image[i];
    }
  }

  public final void I(int paramInt1, int paramInt2, int paramInt3)
  {
    image(paramInt1, paramInt2, paramInt3);
  }

  public final void R(int paramInt1, int paramInt2, int paramInt3)
  {
    this.image[((paramInt2 * this.sx + paramInt1) * this.BPP)] = (byte)paramInt3;
  }

  public final void G(int paramInt1, int paramInt2, int paramInt3)
  {
    this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 1)] = (byte)paramInt3;
  }

  public final void B(int paramInt1, int paramInt2, int paramInt3)
  {
    this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 2)] = (byte)paramInt3;
  }

  public final void A(int paramInt1, int paramInt2, int paramInt3)
  {
    this.image[((paramInt2 * this.sx + paramInt1) * this.BPP + 3)] = (byte)paramInt3;
  }

  public final byte image(int paramInt1, int paramInt2)
  {
    return this.image[(paramInt2 * this.sx * this.BPP + paramInt1)];
  }

  public final int intI(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 < 0.0F) paramFloat1 = 0.0F; else if (paramFloat1 > this.sx - 2) paramFloat1 = this.sx - 2;
    if (paramFloat2 < 0.0F) paramFloat2 = 0.0F; else if (paramFloat2 > this.sy - 2) paramFloat2 = this.sy - 2;
    if ((paramFloat1 <= -1.0F) && (paramFloat2 <= -1.0F)) {
      throw new RuntimeException("TexImage.intI(NaN,NaN);");
    }

    int i = (int)paramFloat1; int j = (int)paramFloat2 * this.sx * this.BPP;
    int k = this.image[(j + i)] & 0xFF;
    int m = this.image[(j + i + 1)] & 0xFF;
    int n = this.image[(j + this.sx + i)] & 0xFF;
    int i1 = this.image[(j + this.sx + i + 1)] & 0xFF;

    float f = paramFloat1 % 1.0F;
    k += (int)((m - k) * f);
    n += (int)((i1 - n) * f);

    f = paramFloat2 % 1.0F;
    return k + (int)((n - k) * f);
  }

  public final void image(int paramInt1, int paramInt2, int paramInt3)
  {
    this.image[(paramInt2 * this.sx * this.BPP + paramInt1)] = (byte)paramInt3;
  }

  public final void averageColor(float[] paramArrayOfFloat)
  {
    if (this.sx * this.sy <= 0) return;

    float[] arrayOfFloat = paramArrayOfFloat;
    if (this.BPP > 0) arrayOfFloat[0] = 0.0F;
    if (this.BPP > 1) arrayOfFloat[1] = 0.0F;
    if (this.BPP > 2) arrayOfFloat[2] = 0.0F;
    if (this.BPP > 3) arrayOfFloat[3] = 0.0F;
    for (int j = 0; j < this.sy; j++) for (int i = 0; i < this.sx; i++)
        if (this.BPP >= 1)
        {
          int tmp110_109 = ((j * this.sx + i) * this.BPP); int k = tmp110_109; arrayOfFloat[0] += (this.image[tmp110_109] & 0xFF);
          if (this.BPP > 1) arrayOfFloat[1] += (this.image[(k + 1)] & 0xFF);
          if (this.BPP > 2) arrayOfFloat[2] += (this.image[(k + 2)] & 0xFF);
          if (this.BPP <= 3) continue; arrayOfFloat[3] += (this.image[(k + 3)] & 0xFF);
        }
    float f = 0.003921569F / (this.sx * this.sy);
    if (this.BPP > 0) arrayOfFloat[0] *= f;
    if (this.BPP > 1) arrayOfFloat[1] *= f;
    if (this.BPP > 2) arrayOfFloat[2] *= f;
    if (this.BPP > 3) arrayOfFloat[3] *= f;
  }

  public void scaleHalf()
    throws Exception
  {
    int k;
    int m;
    TexImage localTexImage;
    int i1;
    int j;
    int n;
    int i;
    switch (this.type) { default:
      throw new Exception("scaleHalf(): type of image not supported");
    case 6407:
    case 6408:
    case 32849:
    case 32856:
      k = this.sx / 2; m = this.sy / 2;
      localTexImage = new TexImage(this, 0, 0, k, m);
      for (j = i1 = 0; j < m; i1 += 2) {
        for (i = n = 0; i < k; n += 2) {
          localTexImage.R(i, j, intR(n, i1) + intR(n + 1, i1) + intR(n, i1 + 1) + intR(n + 1, i1 + 1) >> 2);

          localTexImage.G(i, j, intG(n, i1) + intG(n + 1, i1) + intG(n, i1 + 1) + intG(n + 1, i1 + 1) >> 2);

          localTexImage.B(i, j, intB(n, i1) + intB(n + 1, i1) + intB(n, i1 + 1) + intB(n + 1, i1 + 1) >> 2);

          i++;
        }

        if (this.BPP > 3) for (i = n = 0; i < k; n += 2) {
            localTexImage.A(i, j, intA(n, i1) + intA(n + 1, i1) + intA(n, i1 + 1) + intA(n + 1, i1 + 1) >> 2);

            i++;
          }
        j++;
      }

      break;
    case 6406:
    case 6409:
      k = this.sx / 2; m = this.sy / 2;
      localTexImage = new TexImage(this, 0, 0, k, m);
      for (j = i1 = 0; j < m; i1 += 2) {
        for (i = n = 0; i < k; n += 2) {
          localTexImage.I(i, j, intI(n, i1) + intI(n + 1, i1) + intI(n, i1 + 1) + intI(n + 1, i1 + 1) >> 2);

          i++;
        }
        j++;
      }

    }

    this.image = localTexImage.image; localTexImage.image = null;
    this.sx = k; this.sy = m;
  }

  public void LoadTGA(InputStream paramInputStream) throws Exception {
    TexImageTGA localTexImageTGA = new TexImageTGA();
    try {
      localTexImageTGA.Load(paramInputStream, this);
    } catch (Exception localException) {
      this.type = (this.sx = this.sy = 0); this.image = null;
      throw localException;
    }
  }

  public void SaveTGA(OutputStream paramOutputStream) throws Exception {
    TexImageTGA localTexImageTGA = new TexImageTGA();

    localTexImageTGA.Save(paramOutputStream, this);
  }

  public void LoadTGA(String paramString) throws Exception {
    this.type = (this.sx = this.sy = 0); this.image = null;

    SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
    LoadTGA(localSFSInputStream);
  }

  public void SaveTGA(String paramString)
    throws Exception
  {
    FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
    try {
      SaveTGA(localFileOutputStream);
    } catch (Exception localException) {
      localFileOutputStream.close();
      File localFile = new File(paramString);
      localFile.delete();
      throw localException;
    }
  }

  public String toString()
  {
    String str;
    switch (this.type) { default:
      str = "Unknown" + this.type; break;
    case 6406:
      str = "ALPHA"; break;
    case 6400:
      str = "COLOR_INDEX"; break;
    case 32997:
      str = "COLOR_INDEX8_EXT"; break;
    case 6409:
      str = "LUMINANCE"; break;
    case 6407:
      str = "RGB"; break;
    case 32848:
      str = "RGB5"; break;
    case 32849:
      str = "RGB8"; break;
    case 6408:
      str = "RGBA"; break;
    case 32854:
      str = "RGBA4"; break;
    case 32855:
      str = "RGB5_A1"; break;
    case 32856:
      str = "RGBA8";
    }
    return new String("TexImage[" + this.sx + "x" + this.sy + "," + str + ",BPP=" + this.BPP + "]");
  }

  public void hicolorDither()
  {
    if (this.BPP != 3) return;

    if (error.length < this.sx) error = new int[this.sx];
    for (int i = 0; i < this.BPP; i++) {
      int i4 = hiMask[i];
      int i5 = erMask[i];
      for (int j = 0; j < this.sx; j++) error[j] = 0;
      int m = i;
      for (int k = 0; k < this.sy; k++) {
        int n = m;
        int i3;
        int i1 = i3 = 0;
        for (j = 0; j < this.sx; j++) {
          int i2 = (this.image[n] & 0xFF) + i1 + error[j];
          if (i2 > 255) i2 = 255;
          this.image[n] = (byte)(i2 & i4);
          i1 = i2 &= i5;
          int tmp161_160 = (i1 * 3 >> 3); i1 = tmp161_160; error[j] = (tmp161_160 + i3);
          i3 = i2 - i1 - i1 + (1262562454 >> RND & 0x1);
          if (++RND >= 31) RND = 0;
          n += this.BPP;
        }
        m += this.sx * this.BPP;
      }
    }
  }

  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    TexImage localTexImage = new TexImage();
    localTexImage.LoadTGA(paramArrayOfString.length > 0 ? paramArrayOfString[0] : "Test.TGA");
    System.out.println(localTexImage);

    System.out.println("OK");
  }
}