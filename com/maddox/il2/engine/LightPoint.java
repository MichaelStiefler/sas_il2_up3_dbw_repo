// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LightPoint.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;

// Referenced classes of package com.maddox.il2.engine:
//            GObjException, Config, GObj

public class LightPoint
    implements com.maddox.rts.Destroy
{

    public LightPoint()
    {
        I = 0.0F;
        R = 0.0F;
        IX = 0;
        IY = 0;
        cppObj = 0;
        stamp = 0;
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        cppObj = com.maddox.il2.engine.LightPoint.New();
        if(cppObj == 0)
        {
            throw new GObjException("LightPoint not created");
        } else
        {
            setPos(0.0D, 0.0D, 0.0D);
            return;
        }
    }

    public void setPos(double d, double d1, double d2)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            IX = (int)d;
            IY = (int)d1;
            com.maddox.il2.engine.LightPoint.setPos(cppObj, (float)d, (float)d1, (float)d2);
            return;
        }
    }

    public void setPos(double ad[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            IX = (int)ad[0];
            IY = (int)ad[1];
            com.maddox.il2.engine.LightPoint.setPos(cppObj, (float)ad[0], (float)ad[1], (float)ad[2]);
            return;
        }
    }

    public void setPos(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            IX = (int)point3d.x;
            IY = (int)point3d.y;
            com.maddox.il2.engine.LightPoint.setPos(cppObj, (float)point3d.x, (float)point3d.y, (float)point3d.z);
            return;
        }
    }

    public void getPos(double ad[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.getPos(cppObj, tmpf);
            ad[0] = tmpf[0];
            ad[1] = tmpf[1];
            ad[2] = tmpf[2];
            return;
        }
    }

    public void getPos(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.getPos(cppObj, tmpf);
            point3d.x = tmpf[0];
            point3d.y = tmpf[1];
            point3d.z = tmpf[2];
            return;
        }
    }

    public void setColor(float f, float f1, float f2)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.setColor(cppObj, f, f1, f2);
            return;
        }
    }

    public void setColor(float af[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.setColor(cppObj, af[0], af[1], af[2]);
            return;
        }
    }

    public void setColor(com.maddox.JGP.Color3f color3f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.setColor(cppObj, color3f.x, color3f.y, color3f.z);
            return;
        }
    }

    public void getColor(float af[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.getColor(cppObj, af);
            return;
        }
    }

    public void getColor(com.maddox.JGP.Color3f color3f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.getColor(cppObj, tmpf);
            color3f.x = tmpf[0];
            color3f.y = tmpf[1];
            color3f.z = tmpf[2];
            return;
        }
    }

    public void setEmit(float f, float f1)
    {
        I = f;
        R = f1;
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.setEmit(cppObj, f, f1);
            return;
        }
    }

    public void getEmit(float af[])
    {
        af[0] = I;
        af[1] = R;
    }

    public float getI()
    {
        return I;
    }

    public float getR()
    {
        return R;
    }

    public boolean isDestroyed()
    {
        return cppObj == 0;
    }

    public void destroy()
    {
        if(!isDestroyed())
            com.maddox.il2.engine.LightPoint.Finalize(cppObj);
        cppObj = 0;
    }

    public void addToRender()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(cppObj == 0)
        {
            return;
        } else
        {
            com.maddox.il2.engine.LightPoint.addToRender(cppObj);
            return;
        }
    }

    private static native void setPos(int i, float f, float f1, float f2);

    private static native void setColor(int i, float f, float f1, float f2);

    private static native void setEmit(int i, float f, float f1);

    private static native void getPos(int i, float af[]);

    private static native void getColor(int i, float af[]);

    private static native void getEmit(int i, float af[]);

    public static native void setOffset(float f, float f1, float f2);

    private static native void addToRender(int i);

    public static native void clearRender();

    protected void finalize()
    {
        if(cppObj != 0)
            com.maddox.il2.engine.LightPoint.Finalize(cppObj);
        cppObj = 0;
    }

    private static native void Finalize(int i);

    private static native int New();

    protected float I;
    protected float R;
    protected int IX;
    protected int IY;
    protected int cppObj;
    protected int stamp;
    protected static int curStamp = 0;
    private static float tmpf[] = new float[3];

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
