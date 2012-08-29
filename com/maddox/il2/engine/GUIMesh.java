// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIMesh.java

package com.maddox.il2.engine;

import com.maddox.gwindow.GMesh;
import com.maddox.gwindow.GSize;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.engine:
//            Mesh

public class GUIMesh extends com.maddox.gwindow.GMesh
{
    public static class _Loader extends com.maddox.gwindow.GMesh.Loader
    {

        public com.maddox.gwindow.GMesh load(java.lang.String s)
        {
            com.maddox.il2.engine.GUIMesh guimesh = new GUIMesh();
            guimesh.size = new GSize(1.0F, 1.0F);
            try
            {
                guimesh.mesh = new Mesh(s);
                int i = guimesh.mesh.frames();
                if(i > 1)
                {
                    guimesh.mesh.setFrame(0);
                    guimesh.mesh.getBoundBox(guimesh.boundBox);
                    for(int j = 1; j < i; j++)
                    {
                        guimesh.mesh.setFrame(j);
                        guimesh.mesh.getBoundBox(com.maddox.il2.engine.GUIMesh._box);
                        if(guimesh.boundBox[0] > com.maddox.il2.engine.GUIMesh._box[0])
                            guimesh.boundBox[0] = com.maddox.il2.engine.GUIMesh._box[0];
                        if(guimesh.boundBox[1] > com.maddox.il2.engine.GUIMesh._box[1])
                            guimesh.boundBox[1] = com.maddox.il2.engine.GUIMesh._box[1];
                        if(guimesh.boundBox[2] > com.maddox.il2.engine.GUIMesh._box[2])
                            guimesh.boundBox[2] = com.maddox.il2.engine.GUIMesh._box[2];
                        if(guimesh.boundBox[3] < com.maddox.il2.engine.GUIMesh._box[3])
                            guimesh.boundBox[3] = com.maddox.il2.engine.GUIMesh._box[3];
                        if(guimesh.boundBox[4] < com.maddox.il2.engine.GUIMesh._box[4])
                            guimesh.boundBox[4] = com.maddox.il2.engine.GUIMesh._box[4];
                        if(guimesh.boundBox[5] < com.maddox.il2.engine.GUIMesh._box[5])
                            guimesh.boundBox[5] = com.maddox.il2.engine.GUIMesh._box[5];
                    }

                    guimesh.mesh.setFrame(0);
                } else
                {
                    guimesh.mesh.getBoundBox(guimesh.boundBox);
                }
                guimesh.size.set(guimesh.boundBox[3] - guimesh.boundBox[0], guimesh.boundBox[4] - guimesh.boundBox[1]);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
            return guimesh;
        }

        public com.maddox.gwindow.GMesh loadShared(java.lang.String s)
        {
            java.lang.Object obj = com.maddox.il2.engine.GUIMesh._shared.get(s);
            if(obj != null)
            {
                return (com.maddox.gwindow.GMesh)obj;
            } else
            {
                com.maddox.gwindow.GMesh gmesh = load(s);
                com.maddox.il2.engine.GUIMesh._shared.put(s, gmesh);
                return gmesh;
            }
        }

        public _Loader()
        {
        }
    }


    private GUIMesh()
    {
        mesh = null;
    }


    public com.maddox.il2.engine.Mesh mesh;
    public float boundBox[] = {
        0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F
    };
    private static com.maddox.util.HashMapExt _shared = new HashMapExt();
    private static float _box[] = {
        0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F
    };



}
