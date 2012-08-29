// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ForceFeedback.java

package com.maddox.il2.objects.effects;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.JoyFF;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class ForceFeedback
{

    public static void fxTriggerShake(int i, boolean flag)
    {
        if(!bEnabled)
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
            return;
        if(triggerFXType[i] == 0)
        {
            com.maddox.il2.ai.World.cur();
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = com.maddox.il2.ai.World.getPlayerFM().CT.Weapons[i];
            if(abulletemitter == null)
                return;
            for(int j = 0; j < abulletemitter.length; j++)
            {
                if(abulletemitter[j] == null)
                    continue;
                if(abulletemitter[j] instanceof com.maddox.il2.engine.GunGeneric)
                {
                    float f = ((com.maddox.il2.engine.GunGeneric)abulletemitter[j]).prop.shotFreq;
                    if(f > 10.83F)
                    {
                        if(com.maddox.il2.ai.World.cur().isDebugFM())
                            java.lang.System.out.println("Trigger " + i + " assessed as MACHINEGUN..");
                        triggerFX[i] = new com.maddox.rts.JoyFF.Effect("ForceFeedback/machinegun.ffe");
                        triggerFXType[i] = 1;
                    } else
                    if(f > 4.16F)
                    {
                        if(com.maddox.il2.ai.World.cur().isDebugFM())
                            java.lang.System.out.println("Trigger " + i + " assessed as AUTOCANNON (" + (int)(f * 60F) + ")..");
                        triggerFX[i] = new com.maddox.rts.JoyFF.Effect("ForceFeedback/autocannon.ffe");
                        triggerFX[i].setSamplePeriod((int)(1.0F / f) * 1000);
                        triggerFXType[i] = 2;
                    } else
                    {
                        if(com.maddox.il2.ai.World.cur().isDebugFM())
                            java.lang.System.out.println("Trigger " + i + " assessed as MORTAR..");
                        triggerFX[i] = new com.maddox.rts.JoyFF.Effect("ForceFeedback/mortar.ffe");
                        triggerFXType[i] = 3;
                    }
                    break;
                }
                if(abulletemitter[j] instanceof com.maddox.il2.objects.weapons.BombGun)
                {
                    triggerFX[i] = new com.maddox.rts.JoyFF.Effect("ForceFeedback/bomb.ffe");
                    triggerFXType[i] = 5;
                    break;
                }
                if(!(abulletemitter[j] instanceof com.maddox.il2.objects.weapons.RocketGun))
                    continue;
                triggerFX[i] = new com.maddox.rts.JoyFF.Effect("ForceFeedback/bomb.ffe");
                triggerFXType[i] = 4;
                break;
            }

        }
        if(flag)
        {
            if(triggerFXType[i] == 3)
                triggerFX[i].stop();
            triggerFX[i].start(1);
        } else
        {
            triggerFX[i].stop();
        }
    }

    public static void fxPunch(float f, float f1, float f2)
    {
        if(!bEnabled)
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
            return;
        long l = com.maddox.rts.Time.real();
        if(l - punchPrevTime < 300L)
        {
            return;
        } else
        {
            punchPrevTime = l;
            punch.setDirection(f, f1);
            punch.setGain(f2);
            punch.start(1);
            return;
        }
    }

    public static void fxShake(float f)
    {
        if(!bEnabled)
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
            return;
        long l = com.maddox.rts.Time.real();
        if(l - shakePrevTime < 300L)
            return;
        shakePrevTime = l;
        if(java.lang.Math.abs(f - shakeGain) > 0.025F)
        {
            shake.setGain(f);
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("Shake gain = " + f);
            shake.setDirection(rnd.nextFloat(-0.5F, 0.5F), rnd.nextFloat(-0.5F, 0.5F));
            if(shakeGain == 0.0F)
                shake.start(1);
            if(f < 0.05F)
            {
                shake.stop();
                f = 0.0F;
            }
            shakeGain = f;
        }
    }

    public static void fxSetSpringZero(float f, float f1)
    {
        if(!bEnabled)
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
            return;
        if(java.lang.Math.abs(springX - f) > 0.1F && java.lang.Math.abs(springY - f1) > 0.1F)
        {
            spring.setOffset(f, f1);
            springX = f;
            springY = f1;
        }
    }

    public static void fxSetSpringGain(float f)
    {
        if(!bEnabled)
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
            return;
        if(java.lang.Math.abs(springGain - f) < 0.05F)
        {
            return;
        } else
        {
            springGain = f;
            spring.setCoefficient(f, f);
            return;
        }
    }

    private static void startStaticEffects()
    {
        if(bEnabled)
            return;
        com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringGain(0.0F);
        com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringZero(0.0F, 0.0F);
        spring.start(1);
        com.maddox.il2.objects.effects.ForceFeedback.fxShake(0.0F);
        for(int i = 0; i < triggerFXType.length; i++)
        {
            triggerFXType[i] = 0;
            if(triggerFX[i] != null)
                triggerFX[i].destroy();
            triggerFX[i] = null;
        }

        bEnabled = true;
    }

    private static void stopAllEffects()
    {
        if(!bEnabled)
            return;
        shake.stop();
        punch.stop();
        spring.stop();
        for(int i = 0; i < triggerFXType.length; i++)
            if(triggerFX[i] != null)
                triggerFX[i].stop();

        bEnabled = false;
    }

    private static void loadAllEffects()
    {
        if(spring != null)
        {
            return;
        } else
        {
            punch = new com.maddox.rts.JoyFF.Effect("ForceFeedback/punch.ffe");
            shake = new com.maddox.rts.JoyFF.Effect("ForceFeedback/shake.ffe");
            spring = new com.maddox.rts.JoyFF.Effect("ForceFeedback/spring.ffe");
            return;
        }
    }

    public static void startMission()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.ForceFeedback.startStaticEffects();
            return;
        }
    }

    public static void stopMission()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.ForceFeedback.stopAllEffects();
            return;
        }
    }

    public static void start()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.rts.JoyFF.isStarted())
            return;
        if(!com.maddox.rts.JoyFF.isEnable())
            return;
        if(!com.maddox.rts.JoyFF.isAttached())
        {
            return;
        } else
        {
            com.maddox.rts.JoyFF.start();
            com.maddox.rts.JoyFF.setAutoCenter(false);
            com.maddox.il2.objects.effects.ForceFeedback.loadAllEffects();
            return;
        }
    }

    public static void stop()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.rts.JoyFF.isStarted())
        {
            return;
        } else
        {
            com.maddox.rts.JoyFF.stop();
            return;
        }
    }

    private ForceFeedback()
    {
    }

    private static com.maddox.rts.JoyFF.Effect triggerFX[] = new com.maddox.rts.JoyFF.Effect[20];
    private static com.maddox.rts.JoyFF.Effect punch;
    private static com.maddox.rts.JoyFF.Effect shake;
    private static com.maddox.rts.JoyFF.Effect spring;
    private static float shakeGain = 0.0F;
    private static float springGain = 1.0F;
    private static float springX = 0.0F;
    private static float springY = 0.0F;
    private static int triggerFXType[] = new int[20];
    private static long punchPrevTime = 0L;
    private static long shakePrevTime = 0L;
    private static final java.lang.String pathToFXDir = "ForceFeedback/";
    private static final int FFT_NONE = 0;
    private static final int FFT_MACHINEGUN = 1;
    private static final int FFT_AUTOCANNON = 2;
    private static final int FFT_MORTAR = 3;
    private static final int FFT_ROCKET = 4;
    private static final int FFT_BOMB = 5;
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();
    private static boolean bEnabled = false;

}
