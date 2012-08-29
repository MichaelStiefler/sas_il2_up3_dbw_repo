// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TexImageTGA.java

package com.maddox.TexImage;

import java.io.InputStream;
import java.io.OutputStream;

// Referenced classes of package com.maddox.TexImage:
//            TexImage

class TexImageTGA
{

    TexImageTGA()
    {
        ColorMapSpec = new byte[5];
    }

    public final void Load(java.io.InputStream inputstream, com.maddox.TexImage.TexImage teximage)
        throws java.lang.Exception
    {
        IDLength = (byte)inputstream.read();
        ColorMapType = (byte)inputstream.read();
        ImageType = (byte)inputstream.read();
        inputstream.read(ColorMapSpec);
        ImgOriginX = (short)inputstream.read();
        ImgOriginX |= (short)inputstream.read() << 8;
        ImgOriginY = (short)inputstream.read();
        ImgOriginY |= (short)inputstream.read() << 8;
        ImgWidth = (short)inputstream.read();
        ImgWidth |= (short)inputstream.read() << 8;
        ImgHeight = (short)inputstream.read();
        ImgHeight |= (short)inputstream.read() << 8;
        PixelDepth = (byte)inputstream.read();
        ImgDescript = (byte)inputstream.read();
        int i = ImgDescript & 0xf;
        if(i > 8)
            throw new Exception("Too much Alpha bits");
        for(; IDLength > 0; IDLength--)
            inputstream.read();

        switch(ImageType)
        {
        case 2: // '\002'
        case 3: // '\003'
            break;

        case 1: // '\001'
            if(ColorMapType == 1)
                break;
            // fall through

        default:
            throw new Exception("This TGA file type not supported");
        }
        int j = 0;
        switch(PixelDepth)
        {
        case 8: // '\b'
            if(ImageType == 1)
            {
                j = 6400;
                if(ColorMapSpec[0] == 0 && ColorMapSpec[1] == 0 && ColorMapSpec[2] == 0 && ColorMapSpec[3] == 1)
                    switch(ColorMapSpec[4])
                    {
                    case 24: // '\030'
                        teximage.BytesPerEntry = 3;
                        teximage.Palette = new byte[768];
                        inputstream.read(teximage.Palette);
                        SwapPal(teximage.Palette);
                        break;

                    case 32: // ' '
                        teximage.BytesPerEntry = 4;
                        teximage.Palette = new byte[1024];
                        inputstream.read(teximage.Palette);
                        SwapPal(teximage.Palette);
                        break;

                    default:
                        throw new Exception("Palette entry size must be 24 or 32 bits");
                    }
                else
                    throw new Exception("Invalid number of palette entries");
            } else
            {
                switch(i)
                {
                case 0: // '\0'
                    j = 6409;
                    break;

                case 8: // '\b'
                    j = 6406;
                    break;
                }
            }
            break;

        case 16: // '\020'
            switch(i)
            {
            case 0: // '\0'
                j = 32848;
                break;

            case 1: // '\001'
                j = 32855;
                break;

            case 4: // '\004'
                j = 32854;
                break;
            }
            break;

        case 24: // '\030'
            if(i == 0)
                j = 6407;
            break;

        case 32: // ' '
            if(i == 8)
                j = 6408;
            break;
        }
        if(j == 0)
            throw new Exception("This TGA file type not supported");
        teximage.type = j;
        teximage.sx = ImgWidth;
        teximage.sy = ImgHeight;
        teximage.BPP = PixelDepth + 7 >> 3;
        int k = ImgWidth * ImgHeight * (PixelDepth + 7 >> 3);
        int l = ImgWidth * (PixelDepth + 7 >> 3);
        teximage.image = new byte[ImgHeight * l];
        if((ImgDescript & 0x20) != 0)
        {
            for(int i1 = 0; i1 < ImgHeight; i1++)
                inputstream.read(teximage.image, i1 * teximage.sx * teximage.BPP, teximage.sx * teximage.BPP);

        } else
        {
            for(int j1 = ImgHeight - 1; j1 >= 0; j1--)
                inputstream.read(teximage.image, j1 * teximage.sx * teximage.BPP, teximage.sx * teximage.BPP);

        }
        ImgDescript &= 0xdf;
        if(ImageType == 2)
            SwapRB(teximage);
    }

    public final void Save(java.io.OutputStream outputstream, com.maddox.TexImage.TexImage teximage)
        throws java.lang.Exception
    {
        if(teximage.BytesPerEntry != 0)
            throw new Exception("Paletted TGA file save not supported");
        IDLength = 0;
        ColorMapType = 0;
        ImageType = (byte)(teximage.BPP != 1 ? 2 : 3);
        ImgOriginX = 0;
        ImgOriginY = 0;
        ImgWidth = (short)teximage.sx;
        ImgHeight = (short)teximage.sy;
        PixelDepth = (byte)(teximage.BPP * 8);
        ImgDescript = 0;
        switch(teximage.type)
        {
        case 6408: 
            ImgDescript = 8;
            break;

        case 32854: 
            ImgDescript = 4;
            break;

        case 32855: 
            ImgDescript = 1;
            break;

        case 32856: 
            ImgDescript = 8;
            break;
        }
        outputstream.write(IDLength);
        outputstream.write(ColorMapType);
        outputstream.write(ImageType);
        outputstream.write(ColorMapSpec);
        outputstream.write(ImgOriginX);
        outputstream.write(ImgOriginX >> 8);
        outputstream.write(ImgOriginY);
        outputstream.write(ImgOriginY >> 8);
        outputstream.write(ImgWidth);
        outputstream.write(ImgWidth >> 8);
        outputstream.write(ImgHeight);
        outputstream.write(ImgHeight >> 8);
        outputstream.write(PixelDepth);
        outputstream.write(ImgDescript);
        if(ImageType == 2)
            SwapRB(teximage);
        for(int i = ImgHeight - 1; i >= 0; i--)
            outputstream.write(teximage.image, i * teximage.sx * teximage.BPP, teximage.sx * teximage.BPP);

        if(ImageType == 2)
            SwapRB(teximage);
    }

    private void SwapY(com.maddox.TexImage.TexImage teximage)
    {
        int i = 0;
        for(int j = teximage.sy - 1; i < j; j--)
        {
            for(int k = 0; k < teximage.sx; k++)
            {
                byte byte0 = teximage.image(k, i);
                teximage.image(k, i, teximage.image(k, j));
                teximage.image(k, j, byte0);
            }

            i++;
        }

    }

    private void SwapPal(byte abyte0[])
    {
        byte byte0;
        switch(abyte0.length)
        {
        case 1024: 
            byte0 = 4;
            break;

        case 768: 
            byte0 = 3;
            break;

        default:
            return;
        }
        for(int i = 0; i < 256 * byte0; i += byte0)
        {
            byte byte1 = abyte0[i];
            abyte0[i] = abyte0[i + 2];
            abyte0[i + 2] = byte1;
        }

    }

    private void SwapRB(com.maddox.TexImage.TexImage teximage)
    {
        switch(teximage.type)
        {
        default:
            break;

        case 6407: 
        case 32849: 
            int i = teximage.sx * 3;
            for(int j1 = 0; j1 < teximage.sy; j1++)
            {
                for(int k2 = 0; k2 < i; k2 += 3)
                {
                    byte byte0 = teximage.image(k2, j1);
                    teximage.image(k2, j1, teximage.image(k2 + 2, j1));
                    teximage.image(k2 + 2, j1, byte0);
                }

            }

            break;

        case 6408: 
        case 32856: 
            int j = teximage.sx * 4;
            for(int k1 = 0; k1 < teximage.sy; k1++)
            {
                for(int l2 = 0; l2 < j; l2 += 4)
                {
                    byte byte1 = teximage.image(l2, k1);
                    teximage.image(l2, k1, teximage.image(l2 + 2, k1));
                    teximage.image(l2 + 2, k1, byte1);
                }

            }

            break;

        case 32848: 
            int k = teximage.sx * 2;
            for(int l1 = 0; l1 < teximage.sy; l1++)
            {
                for(int i3 = 0; i3 < k; i3 += 2)
                {
                    byte byte2 = (byte)(teximage.image(i3 + 1, l1) >> 3 & 0x1f);
                    byte byte5 = (byte)(teximage.image(i3, l1) << 3);
                    teximage.image(i3, l1, teximage.image(i3, l1) & 0xe0);
                    teximage.image(i3, l1, teximage.image(i3, l1) | byte2);
                    teximage.image(i3 + 1, l1, teximage.image(i3 + 1, l1) & 7);
                    teximage.image(i3 + 1, l1, teximage.image(i3 + 1, l1) | byte5);
                }

            }

            break;

        case 32854: 
            int l = teximage.sx * 2;
            for(int i2 = 0; i2 < teximage.sy; i2++)
            {
                for(int j3 = 0; j3 < l; j3 += 2)
                {
                    byte byte3 = (byte)(teximage.image(j3 + 1, i2) >> 4 & 0xf);
                    byte byte6 = (byte)(teximage.image(j3, i2) << 4);
                    teximage.image(j3, i2, teximage.image(j3, i2) & 0xf0);
                    teximage.image(j3, i2, teximage.image(j3, i2) | byte3);
                    teximage.image(j3 + 1, i2, teximage.image(j3 + 1, i2) & 0xf);
                    teximage.image(j3 + 1, i2, teximage.image(j3 + 1, i2) | byte6);
                }

            }

            break;

        case 32855: 
            int i1 = teximage.sx * 2;
            for(int j2 = 0; j2 < teximage.sy; j2++)
            {
                for(int k3 = 0; k3 < i1; k3 += 2)
                {
                    byte byte4 = (byte)(teximage.image(k3 + 1, j2) >> 2 & 0x1f);
                    byte byte7 = (byte)(teximage.image(k3, j2) << 2 & 0x7c);
                    teximage.image(k3, j2, teximage.image(k3, j2) & 0xe0);
                    teximage.image(k3, j2, teximage.image(k3, j2) | byte4);
                    teximage.image(k3 + 1, j2, teximage.image(k3 + 1, j2) & 0x83);
                    teximage.image(k3 + 1, j2, teximage.image(k3 + 1, j2) | byte7);
                }

            }

            break;
        }
    }

    byte IDLength;
    byte ColorMapType;
    byte ImageType;
    byte ColorMapSpec[];
    short ImgOriginX;
    short ImgOriginY;
    short ImgWidth;
    short ImgHeight;
    byte PixelDepth;
    byte ImgDescript;
}
