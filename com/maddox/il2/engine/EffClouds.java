// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EffClouds.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;
import com.maddox.sound.SoundFX;

// Referenced classes of package com.maddox.il2.engine:
//            GObj, GObjException, Engine, Landscape, 
//            LandConf

public class EffClouds extends com.maddox.il2.engine.GObj
    implements com.maddox.rts.Destroy
{

    public boolean isShow()
    {
        return bShow;
    }

    public void setShow(boolean flag)
    {
        bShow = flag;
    }

    public int type()
    {
        return type;
    }

    public float height()
    {
        return height;
    }

    public void soundUpdate(double d)
    {
        if(sound != null)
        {
            float f = (float)d;
            float f1 = 1.0F;
            float f2 = 200F;
            if(f > height)
            {
                f -= height;
                if(f < f2)
                    f1 = 1.0F - f / f2;
                else
                    f1 = 0.0F;
            }
            sound.setVolume(f1 * vmax);
        }
    }

    protected void setRainSound(int i)
    {
        int j = com.maddox.il2.engine.Engine.land().config.month;
        boolean flag = j <= 10 && j >= 4 && i >= 5;
        if(flag)
        {
            if(sound == null)
                sound = new SoundFX("objects.rain");
            if(sound != null)
            {
                sound.setUsrFlag(i - 5);
                sound.play();
            }
        } else
        if(sound != null)
        {
            sound.clear();
            sound.cancel();
            sound = null;
        }
    }

    public void setType(int i)
    {
        if(cppObj == 0)
        {
            setRainSound(0);
            return;
        } else
        {
            type = i;
            SetType(cppObj, i);
            setRainSound(i);
            return;
        }
    }

    public void setHeight(float f)
    {
        if(cppObj == 0)
        {
            return;
        } else
        {
            height = f;
            SetHeight(cppObj, f);
            return;
        }
    }

    public boolean getRandomCloudPos(com.maddox.JGP.Point3d point3d)
    {
        if(cppObj == 0)
            return false;
        boolean flag = GetRandomCloudPos(cppObj, farr3);
        if(!flag)
        {
            return false;
        } else
        {
            point3d.x = farr3[0];
            point3d.y = farr3[1];
            point3d.z = farr3[2];
            return true;
        }
    }

    public float getVisibility(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        return GetVisibility(cppObj, (float)point3d.x, (float)point3d.y, (float)point3d.z, (float)point3d1.x, (float)point3d1.y, (float)point3d1.z);
    }

    public int preRender()
    {
        if(!bShow || cppObj == 0)
            return 0;
        else
            return PreRender(cppObj);
    }

    public void render()
    {
        if(!bShow || cppObj == 0)
        {
            return;
        } else
        {
            Render(cppObj);
            return;
        }
    }

    public void destroy()
    {
        setRainSound(0);
        if(!isDestroyed())
        {
            Destroy(cppObj);
            cppObj = 0;
        }
    }

    public boolean isDestroyed()
    {
        return cppObj == 0;
    }

    public EffClouds(boolean flag, int i, float f)
    {
        super(0);
        bShow = true;
        sound = null;
        vmax = 1.0F;
        height = f;
        type = i;
        int j = i;
        if(flag)
            j |= 0x10;
        cppObj = Load(j, f);
        if(cppObj == 0)
        {
            throw new GObjException("EffClouds not created");
        } else
        {
            setRainSound(i);
            return;
        }
    }

    public EffClouds(int i)
    {
        super(i);
        bShow = true;
        sound = null;
        vmax = 1.0F;
    }

    private native int Load(int i, float f);

    private native void Destroy(int i);

    private native void SetType(int i, int j);

    private native void SetHeight(int i, float f);

    private native boolean GetRandomCloudPos(int i, float af[]);

    private native float GetVisibility(int i, float f, float f1, float f2, float f3, float f4, float f5);

    private native int PreRender(int i);

    private native void Render(int i);

    private boolean bShow;
    private int type;
    private float height;
    protected com.maddox.sound.SoundFX sound;
    protected float vmax;
    private static float farr3[] = new float[3];

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
