// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JoyFF.java

package com.maddox.rts;

import java.util.ArrayList;

// Referenced classes of package com.maddox.rts:
//            HomePath, RTS, MsgTimeOutListener, Time, 
//            MsgTimeOut

public class JoyFF
{
    public static class Effect
        implements com.maddox.rts.MsgTimeOutListener
    {

        public void msgTimeOut(java.lang.Object obj)
        {
            if(cppObj == 0)
                return;
            if(obj != lock)
                return;
            iterations--;
            if(iterations == 0)
            {
                return;
            } else
            {
                com.maddox.rts.JoyFF.StartEffect(cppObj);
                com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + (long)duration, this, lock);
                return;
            }
        }

        public void start(int i)
        {
            if(cppObj == 0)
                return;
            if(i < 0)
                i = -1;
            if(i == 0)
                return;
            iterations = i;
            lock = null;
            com.maddox.rts.JoyFF.StartEffect(cppObj);
            if(iterations != 1 && duration > 0)
            {
                lock = new Object();
                com.maddox.rts.MsgTimeOut.post(64, com.maddox.rts.Time.currentReal() + (long)duration, this, lock);
            }
        }

        public void stop()
        {
            iterations = 0;
            lock = null;
            if(cppObj == 0)
            {
                return;
            } else
            {
                com.maddox.rts.JoyFF.StopEffect(cppObj);
                return;
            }
        }

        public boolean isPlaying()
        {
            if(cppObj == 0)
                return false;
            if(iterations == 0)
                return false;
            if(iterations == 1 && lock == null)
                return com.maddox.rts.JoyFF.EffectIsPlaying(cppObj);
            else
                return true;
        }

        public void setDuration(int i)
        {
            if(cppObj == 0)
            {
                return;
            } else
            {
                duration = i;
                com.maddox.rts.JoyFF.SetEffectDuration(cppObj, i);
                return;
            }
        }

        public void setSamplePeriod(int i)
        {
            if(cppObj == 0)
            {
                return;
            } else
            {
                com.maddox.rts.JoyFF.SetEffectSamplePeriod(cppObj, i);
                return;
            }
        }

        public void setGain(float f)
        {
            if(cppObj == 0)
                return;
            if(f < 0.0F)
                f = 0.0F;
            if(f > 1.0F)
                f = 1.0F;
            com.maddox.rts.JoyFF.SetEffectGain(cppObj, (int)(f * 10000F));
        }

        public void setDirection(float f, float f1)
        {
            if(cppObj == 0)
                return;
            if(f < -1F)
                f = -1F;
            if(f > 1.0F)
                f = 1.0F;
            if(f1 < -1F)
                f1 = -1F;
            if(f1 > 1.0F)
                f1 = 1.0F;
            com.maddox.rts.JoyFF.SetEffectDirection(cppObj, (int)(f * 10000F), (int)(f1 * 10000F));
        }

        public void setOffset(float f, float f1)
        {
            if(cppObj == 0)
                return;
            if(f < -1F)
                f = -1F;
            if(f > 1.0F)
                f = 1.0F;
            if(f1 < -1F)
                f1 = -1F;
            if(f1 > 1.0F)
                f1 = 1.0F;
            com.maddox.rts.JoyFF.SetEffectOffset(cppObj, (int)(f * 10000F), (int)(f1 * 10000F));
        }

        public void setCoefficient(float f, float f1)
        {
            if(cppObj == 0)
                return;
            if(f < -1F)
                f = -1F;
            if(f > 1.0F)
                f = 1.0F;
            if(f1 < -1F)
                f1 = -1F;
            if(f1 > 1.0F)
                f1 = 1.0F;
            com.maddox.rts.JoyFF.SetEffectCoefficient(cppObj, (int)(f * 10000F), (int)(f1 * 10000F));
        }

        public void destroy()
        {
            if(cppObj == 0)
            {
                return;
            } else
            {
                com.maddox.rts.JoyFF.DelEffect(cppObj);
                cppObj = 0;
                com.maddox.rts.JoyFF.lst.remove(this);
                return;
            }
        }

        public int cppObj;
        java.lang.String fileName;
        int duration;
        int iterations;
        java.lang.Object lock;

        public Effect(java.lang.String s)
        {
            fileName = s;
            if(!com.maddox.rts.JoyFF.bAttached)
                return;
            if(!com.maddox.rts.JoyFF.bStarted)
                return;
            cppObj = com.maddox.rts.JoyFF.NewEffect(com.maddox.rts.HomePath.toFileSystemName(fileName, 0));
            if(cppObj == 0)
            {
                return;
            } else
            {
                duration = com.maddox.rts.JoyFF.GetEffectDuration(cppObj);
                com.maddox.rts.JoyFF.lst.add(this);
                return;
            }
        }
    }


    public static boolean isAttached()
    {
        return bAttached;
    }

    public static boolean isEnable()
    {
        return bEnable;
    }

    public static void setEnable(boolean flag)
    {
        bEnable = flag;
    }

    public static boolean isStarted()
    {
        return bStarted;
    }

    public static void start()
    {
        if(!bAttached)
            return;
        if(bStarted)
            return;
        bStarted = com.maddox.rts.JoyFF.Start(3);
        if(bStarted)
        {
            com.maddox.rts.JoyFF.setAutoCenter(bAutoCenter);
            int i = lst.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.Effect effect = (com.maddox.rts.Effect)lst.get(j);
                effect.cppObj = com.maddox.rts.JoyFF.NewEffect(com.maddox.rts.HomePath.toFileSystemName(com.maddox.rts.HomePath.get(0), effect.fileName, 0));
                if(effect.cppObj == 0)
                    return;
                effect.duration = com.maddox.rts.JoyFF.GetEffectDuration(effect.cppObj);
            }

        }
    }

    public static void stop()
    {
        if(!bAttached)
            return;
        if(!bStarted)
            return;
        int i = lst.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.Effect effect = (com.maddox.rts.Effect)lst.get(j);
            if(effect.cppObj != 0)
            {
                com.maddox.rts.JoyFF.DelEffect(effect.cppObj);
                effect.cppObj = 0;
                effect.iterations = 0;
                effect.lock = null;
            }
        }

        bStarted = false;
        com.maddox.rts.JoyFF.Stop();
    }

    public static void setAutoCenter(boolean flag)
    {
        if(!bAttached)
            return;
        bAutoCenter = flag;
        if(!bStarted)
        {
            return;
        } else
        {
            com.maddox.rts.JoyFF.SetAutoCenter(flag);
            return;
        }
    }

    private JoyFF()
    {
        bAttached = com.maddox.rts.JoyFF.IsAttached();
    }

    private static final native boolean IsAttached();

    private static final native boolean Start(int i);

    private static final native void Stop();

    private static final native void SetAutoCenter(boolean flag);

    private static final native int NewEffect(java.lang.String s);

    private static final native void DelEffect(int i);

    private static final native int GetEffectDuration(int i);

    private static final native void StartEffect(int i);

    private static final native void StopEffect(int i);

    private static final native boolean EffectIsPlaying(int i);

    private static final native void SetEffectDuration(int i, int j);

    private static final native void SetEffectSamplePeriod(int i, int j);

    private static final native void SetEffectGain(int i, int j);

    private static final native void SetEffectDirection(int i, int j, int k);

    private static final native void SetEffectOffset(int i, int j, int k);

    private static final native void SetEffectCoefficient(int i, int j, int k);

    public static final int NONEXCLUSIVE_BACKGROUND = 0;
    public static final int NONEXCLUSIVE_FOREGROUND = 1;
    public static final int EXCLUSIVE_BACKGROUND = 2;
    public static final int EXCLUSIVE_FOREGROUND = 3;
    private static com.maddox.rts.JoyFF cur = new JoyFF();
    private static java.util.ArrayList lst = new ArrayList();
    private static boolean bAttached = false;
    private static boolean bEnable = false;
    private static boolean bStarted = false;
    private static boolean bAutoCenter = true;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }















}
