// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ScrShot.java

package com.maddox.opengl.util;

import com.maddox.TexImage.TexImage;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.gl;
import java.io.FileOutputStream;

public class ScrShot
{

    private void swapRB()
    {
        int i = dx * 3;
        for(int j = 0; j < dy; j++)
        {
            for(int k = 0; k < i; k += 3)
            {
                byte byte0 = img.image(k, j);
                byte byte1 = img.image(k + 2, j);
                img.image(k, j, byte1);
                img.image(k + 2, j, byte0);
            }

        }

    }

    public void grab()
    {
        java.lang.String s;
        if(shotNum > 999)
            s = prefixName + shotNum + ".tga";
        else
        if(shotNum > 99)
            s = prefixName + "0" + shotNum + ".tga";
        else
        if(shotNum > 9)
            s = prefixName + "00" + shotNum + ".tga";
        else
            s = prefixName + "000" + shotNum + ".tga";
        com.maddox.opengl.gl.ReadPixels(0, 0, dx, dy, 6407, 5121, img.image);
        try
        {
            java.io.FileOutputStream fileoutputstream = new FileOutputStream(s);
            fileoutputstream.write(0);
            fileoutputstream.write(0);
            fileoutputstream.write(2);
            fileoutputstream.write(new byte[5]);
            fileoutputstream.write(0);
            fileoutputstream.write(0);
            fileoutputstream.write(0);
            fileoutputstream.write(0);
            fileoutputstream.write((short)dx);
            fileoutputstream.write((short)(dx >> 8));
            fileoutputstream.write((short)dy);
            fileoutputstream.write((short)(dy >> 8));
            fileoutputstream.write((byte)(img.BPP * 8));
            fileoutputstream.write(0);
            swapRB();
            fileoutputstream.write(img.image, 0, dx * dy * img.BPP);
            fileoutputstream.close();
        }
        catch(java.lang.Exception exception) { }
        shotNum++;
    }

    public ScrShot(java.lang.String s)
    {
        shotNum = 0;
        img = new TexImage();
        dx = com.maddox.opengl.GLContext.getCurrent().width();
        dy = com.maddox.opengl.GLContext.getCurrent().height();
        prefixName = s;
        img.set(dx, dy, 6407);
    }

    private java.lang.String prefixName;
    private int shotNum;
    private int dx;
    private int dy;
    private com.maddox.TexImage.TexImage img;
}
