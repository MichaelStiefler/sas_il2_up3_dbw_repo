package com.maddox.TexImage;

import java.io.InputStream;
import java.io.OutputStream;

class TexImageTGA
{
  byte IDLength;
  byte ColorMapType;
  byte ImageType;
  byte[] ColorMapSpec = new byte[5];
  short ImgOriginX;
  short ImgOriginY;
  short ImgWidth;
  short ImgHeight;
  byte PixelDepth;
  byte ImgDescript;

  public final void Load(InputStream paramInputStream, TexImage paramTexImage)
    throws Exception
  {
    this.IDLength = (byte)paramInputStream.read();
    this.ColorMapType = (byte)paramInputStream.read();
    this.ImageType = (byte)paramInputStream.read();
    paramInputStream.read(this.ColorMapSpec);
    this.ImgOriginX = (short)paramInputStream.read();
    this.ImgOriginX = (short)(this.ImgOriginX | (short)paramInputStream.read() << 8);
    this.ImgOriginY = (short)paramInputStream.read();
    this.ImgOriginY = (short)(this.ImgOriginY | (short)paramInputStream.read() << 8);
    this.ImgWidth = (short)paramInputStream.read();
    this.ImgWidth = (short)(this.ImgWidth | (short)paramInputStream.read() << 8);
    this.ImgHeight = (short)paramInputStream.read();
    this.ImgHeight = (short)(this.ImgHeight | (short)paramInputStream.read() << 8);
    this.PixelDepth = (byte)paramInputStream.read();
    this.ImgDescript = (byte)paramInputStream.read();

    int i = this.ImgDescript & 0xF;

    if (i > 8) throw new Exception("Too much Alpha bits"); do {
      paramInputStream.read(); this.IDLength = (byte)(this.IDLength - 1); } while (this.IDLength > 0);

    switch (this.ImageType) { case 2:
    case 3:
      break;
    case 1:
      if (this.ColorMapType == 1) break;
    default:
      throw new Exception("This TGA file type not supported");
    }

    int j = 0;
    switch (this.PixelDepth) {
    case 8:
      if (this.ImageType == 1) {
        j = 6400;
        if ((this.ColorMapSpec[0] == 0) && (this.ColorMapSpec[1] == 0) && (this.ColorMapSpec[2] == 0) && (this.ColorMapSpec[3] == 1));
        switch (this.ColorMapSpec[4]) {
        case 24:
          paramTexImage.BytesPerEntry = 3;
          paramTexImage.Palette = new byte[768];
          paramInputStream.read(paramTexImage.Palette);
          SwapPal(paramTexImage.Palette);
          break;
        case 32:
          paramTexImage.BytesPerEntry = 4;
          paramTexImage.Palette = new byte[1024];
          paramInputStream.read(paramTexImage.Palette);
          SwapPal(paramTexImage.Palette);
          break;
        default:
          throw new Exception("Palette entry size must be 24 or 32 bits");

          throw new Exception("Invalid number of palette entries");
        }
      }
      else {
        switch (i) { case 0:
          j = 6409; break;
        case 8:
          j = 6406; }
      }
      break;
    case 16:
      switch (i) { case 0:
        j = 32848; break;
      case 1:
        j = 32855; break;
      case 4:
        j = 32854;
      case 2:
      case 3: } break;
    case 24:
      if (i != 0) break; j = 6407; break;
    case 32:
      if (i != 8) break; j = 6408;
    }
    if (j == 0) throw new Exception("This TGA file type not supported");
    paramTexImage.type = j;
    paramTexImage.sx = this.ImgWidth;
    paramTexImage.sy = this.ImgHeight;
    paramTexImage.BPP = (this.PixelDepth + 7 >> 3);

    int k = this.ImgWidth * this.ImgHeight * (this.PixelDepth + 7 >> 3);
    int m = this.ImgWidth * (this.PixelDepth + 7 >> 3);
    paramTexImage.image = new byte[this.ImgHeight * m];
    int n;
    if ((this.ImgDescript & 0x20) != 0)
      for (n = 0; n < this.ImgHeight; n++)
        paramInputStream.read(paramTexImage.image, n * paramTexImage.sx * paramTexImage.BPP, paramTexImage.sx * paramTexImage.BPP);
    else {
      for (n = this.ImgHeight - 1; n >= 0; n--)
        paramInputStream.read(paramTexImage.image, n * paramTexImage.sx * paramTexImage.BPP, paramTexImage.sx * paramTexImage.BPP);
    }
    this.ImgDescript = (byte)(this.ImgDescript & 0xFFFFFFDF);
    if (this.ImageType == 2) SwapRB(paramTexImage); 
  }

  public final void Save(OutputStream paramOutputStream, TexImage paramTexImage) throws Exception
  {
    if (paramTexImage.BytesPerEntry != 0) {
      throw new Exception("Paletted TGA file save not supported");
    }

    this.IDLength = 0;
    this.ColorMapType = 0;
    this.ImageType = (byte)(paramTexImage.BPP == 1 ? 3 : 2);
    this.ImgOriginX = 0;
    this.ImgOriginY = 0;
    this.ImgWidth = (short)paramTexImage.sx;
    this.ImgHeight = (short)paramTexImage.sy;
    this.PixelDepth = (byte)(paramTexImage.BPP * 8);
    this.ImgDescript = 0;
    switch (paramTexImage.type) { case 6408:
      this.ImgDescript = 8; break;
    case 32854:
      this.ImgDescript = 4; break;
    case 32855:
      this.ImgDescript = 1; break;
    case 32856:
      this.ImgDescript = 8;
    }
    paramOutputStream.write(this.IDLength);
    paramOutputStream.write(this.ColorMapType);
    paramOutputStream.write(this.ImageType);
    paramOutputStream.write(this.ColorMapSpec);
    paramOutputStream.write(this.ImgOriginX); paramOutputStream.write(this.ImgOriginX >> 8);
    paramOutputStream.write(this.ImgOriginY); paramOutputStream.write(this.ImgOriginY >> 8);
    paramOutputStream.write(this.ImgWidth); paramOutputStream.write(this.ImgWidth >> 8);
    paramOutputStream.write(this.ImgHeight); paramOutputStream.write(this.ImgHeight >> 8);
    paramOutputStream.write(this.PixelDepth);
    paramOutputStream.write(this.ImgDescript);
    if (this.ImageType == 2) SwapRB(paramTexImage);
    for (int i = this.ImgHeight - 1; i >= 0; i--)
      paramOutputStream.write(paramTexImage.image, i * paramTexImage.sx * paramTexImage.BPP, paramTexImage.sx * paramTexImage.BPP);
    if (this.ImageType == 2) SwapRB(paramTexImage);
  }

  private void SwapY(TexImage paramTexImage)
  {
    int i = 0; for (int j = paramTexImage.sy - 1; i < j; j--) {
      for (int k = 0; k < paramTexImage.sx; k++) {
        int m = paramTexImage.image(k, i);
        paramTexImage.image(k, i, paramTexImage.image(k, j));
        paramTexImage.image(k, j, m);
      }
      i++;
    }
  }

  private void SwapPal(byte[] paramArrayOfByte)
  {
    int i;
    switch (paramArrayOfByte.length) { case 1024:
      i = 4; break;
    case 768:
      i = 3; break;
    default:
      return;
    }
    for (int j = 0; j < 256 * i; j += i) {
      int k = paramArrayOfByte[j]; paramArrayOfByte[j] = paramArrayOfByte[(j + 2)]; paramArrayOfByte[(j + 2)] = k;
    }
  }

  private void SwapRB(TexImage paramTexImage)
  {
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    switch (paramTexImage.type) {
    case 6407:
    case 32849:
      i = paramTexImage.sx * 3;
      for (j = 0; j < paramTexImage.sy; j++) {
        for (k = 0; k < i; k += 3) {
          m = paramTexImage.image(k, j);
          paramTexImage.image(k, j, paramTexImage.image(k + 2, j));
          paramTexImage.image(k + 2, j, m);
        }
      }
      break;
    case 6408:
    case 32856:
      i = paramTexImage.sx * 4;
      for (j = 0; j < paramTexImage.sy; j++) {
        for (k = 0; k < i; k += 4) {
          m = paramTexImage.image(k, j);
          paramTexImage.image(k, j, paramTexImage.image(k + 2, j));
          paramTexImage.image(k + 2, j, m);
        }
      }
      break;
    case 32848:
      i = paramTexImage.sx * 2;
      for (j = 0; j < paramTexImage.sy; j++) {
        for (k = 0; k < i; k += 2) {
          n = (byte)(paramTexImage.image(k + 1, j) >> 3 & 0x1F);
          i1 = (byte)(paramTexImage.image(k, j) << 3);
          paramTexImage.image(k, j, paramTexImage.image(k, j) & 0xE0);
          paramTexImage.image(k, j, paramTexImage.image(k, j) | n);
          paramTexImage.image(k + 1, j, paramTexImage.image(k + 1, j) & 0x7);
          paramTexImage.image(k + 1, j, paramTexImage.image(k + 1, j) | i1);
        }
      }
      break;
    case 32854:
      i = paramTexImage.sx * 2;
      for (j = 0; j < paramTexImage.sy; j++) {
        for (k = 0; k < i; k += 2) {
          n = (byte)(paramTexImage.image(k + 1, j) >> 4 & 0xF);
          i1 = (byte)(paramTexImage.image(k, j) << 4);
          paramTexImage.image(k, j, paramTexImage.image(k, j) & 0xF0);
          paramTexImage.image(k, j, paramTexImage.image(k, j) | n);
          paramTexImage.image(k + 1, j, paramTexImage.image(k + 1, j) & 0xF);
          paramTexImage.image(k + 1, j, paramTexImage.image(k + 1, j) | i1);
        }
      }
      break;
    case 32855:
      i = paramTexImage.sx * 2;
      for (j = 0; j < paramTexImage.sy; j++)
        for (k = 0; k < i; k += 2) {
          n = (byte)(paramTexImage.image(k + 1, j) >> 2 & 0x1F);
          i1 = (byte)(paramTexImage.image(k, j) << 2 & 0x7C);
          paramTexImage.image(k, j, paramTexImage.image(k, j) & 0xE0);
          paramTexImage.image(k, j, paramTexImage.image(k, j) | n);
          paramTexImage.image(k + 1, j, paramTexImage.image(k + 1, j) & 0x83);
          paramTexImage.image(k + 1, j, paramTexImage.image(k + 1, j) | i1);
        }
    }
  }
}