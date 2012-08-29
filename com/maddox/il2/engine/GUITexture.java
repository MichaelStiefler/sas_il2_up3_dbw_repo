// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUITexture.java

package com.maddox.il2.engine;

import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;

// Referenced classes of package com.maddox.il2.engine:
//            Mat

public class GUITexture extends com.maddox.gwindow.GTexture
{
    public static class _Loader extends com.maddox.gwindow.GTexture.Loader
    {

        public com.maddox.gwindow.GTexture load(java.lang.String s)
        {
            com.maddox.il2.engine.GUITexture guitexture = new GUITexture();
            guitexture.mat = com.maddox.il2.engine.Mat.New(s);
            if(guitexture.mat != null)
            {
                guitexture.mat.setLayer(0);
                guitexture.size = new GSize(guitexture.mat.get((byte)2), guitexture.mat.get((byte)3));
                guitexture.scalex = 1.0F / guitexture.size.dx;
                guitexture.scaley = 1.0F / guitexture.size.dy;
            } else
            {
                guitexture.size = new GSize(1.0F, 1.0F);
            }
            return guitexture;
        }

        public _Loader()
        {
        }
    }


    private GUITexture()
    {
        mat = null;
        scalex = 1.0F;
        scaley = 1.0F;
    }


    public com.maddox.il2.engine.Mat mat;
    protected float scalex;
    protected float scaley;
}
