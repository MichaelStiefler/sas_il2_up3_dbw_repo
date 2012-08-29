// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Icon.java

package com.maddox.opengl.util;

import com.maddox.TexImage.TexImage;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import java.io.PrintStream;

public class Icon
    implements com.maddox.opengl.MsgGLContextListener
{

    public void msgGLContext(int i)
    {
        if(i == 8 && tgaName != null && id != 0 && !loadTex(tgaName))
            java.lang.System.out.println("Icon not reloaded: " + tgaName);
    }

    private void resetID()
    {
        if(id != 0)
        {
            int ai[] = new int[1];
            ai[0] = id;
            com.maddox.opengl.gl.DeleteTextures(1, ai);
            id = 0;
        }
    }

    private boolean allocID()
    {
        if(id != 0)
            resetID();
        int ai[] = {
            0
        };
        com.maddox.opengl.gl.GenTextures(1, ai);
        id = ai[0];
        return id != 0;
    }

    private boolean loadTex(java.lang.String s)
    {
        com.maddox.TexImage.TexImage teximage = new TexImage();
        try
        {
            teximage.LoadTGA(s);
        }
        catch(java.lang.Exception exception)
        {
            return false;
        }
        if(!allocID())
        {
            return false;
        } else
        {
            com.maddox.opengl.gl.BindTexture(3553, id);
            com.maddox.opengl.gl.TexParameteri(3553, 10242, 10497);
            com.maddox.opengl.gl.TexParameteri(3553, 10243, 10497);
            com.maddox.opengl.gl.TexParameteri(3553, 10240, 9729);
            com.maddox.opengl.gl.TexParameteri(3553, 10241, 9729);
            com.maddox.opengl.gl.TexImage2D(3553, 0, teximage.type, teximage.sx, teximage.sy, 0, teximage.type, 5121, teximage.image);
            szx = teximage.sx;
            szy = teximage.sy;
            tgaName = s;
            return true;
        }
    }

    private void quad(float f, float f1, float f2, float f3, float f4)
    {
        com.maddox.opengl.gl.Begin(7);
        float f5 = 1.0F / ((float)szx + 1.0F);
        float f6 = 1.0F / ((float)szy + 1.0F);
        com.maddox.opengl.gl.TexCoord2f(f5, f6);
        com.maddox.opengl.gl.Vertex3f(f, f1 + f4, f2);
        com.maddox.opengl.gl.TexCoord2f(1.0F - f5, f6);
        com.maddox.opengl.gl.Vertex3f(f + f3, f1 + f4, f2);
        com.maddox.opengl.gl.TexCoord2f(1.0F - f5, 1.0F - f6);
        com.maddox.opengl.gl.Vertex3f(f + f3, f1, f2);
        com.maddox.opengl.gl.TexCoord2f(f5, 1.0F - f6);
        com.maddox.opengl.gl.Vertex3f(f, f1, f2);
        com.maddox.opengl.gl.End();
    }

    public void draw2D(float f, float f1, float f2, float f3, float f4)
    {
        if(id == 0)
        {
            return;
        } else
        {
            com.maddox.opengl.gl.BindTexture(3553, id);
            quad(f, f1, f2, f3, f4);
            return;
        }
    }

    public void draw3D(float f, float f1, float f2, float f3, float f4)
    {
        if(id == 0)
        {
            return;
        } else
        {
            com.maddox.opengl.gl.BindTexture(3553, id);
            quad(f - f3 / 2.0F, f1 - f4 / 2.0F, f2, f3, f4);
            return;
        }
    }

    public Icon(java.lang.String s)
    {
        id = 0;
        tgaName = null;
        id = 0;
        if(loadTex(s))
            com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        else
            java.lang.System.out.println("Icon not loaded: " + s);
    }

    public void finalize()
    {
        resetID();
    }

    private int id;
    private java.lang.String tgaName;
    private int szx;
    private int szy;
}
