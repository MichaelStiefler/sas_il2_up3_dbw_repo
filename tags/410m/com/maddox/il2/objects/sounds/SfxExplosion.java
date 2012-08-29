// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SfxExplosion.java

package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;

public class SfxExplosion
{

    public SfxExplosion()
    {
    }

    private static boolean init()
    {
        if(crashTank != null)
            return true;
        crashTank = new SoundPreset("crash.tank");
        crashAir = new SoundPreset("crash.air");
        explodeZenitka = new SoundPreset("explode.zenitka");
        explodeBullet = new SoundPreset("explode.bullet");
        explodeShell = new SoundPreset("explode.shell");
        explodeBig = new SoundPreset("explode.big");
        explodeMiddle = new SoundPreset("explode.middle");
        return true;
        java.lang.Exception exception;
        exception;
        return false;
    }

    public static void crashTank(com.maddox.JGP.Point3d point3d, int i)
    {
        if(!com.maddox.il2.objects.sounds.SfxExplosion.init())
        {
            return;
        } else
        {
            com.maddox.sound.SoundFX soundfx = new SoundFX(crashTank);
            soundfx.setPosition(point3d);
            soundfx.play();
            return;
        }
    }

    public static void crashAir(com.maddox.JGP.Point3d point3d, int i)
    {
        if(!com.maddox.il2.objects.sounds.SfxExplosion.init())
        {
            return;
        } else
        {
            com.maddox.sound.SoundFX soundfx = new SoundFX(crashAir);
            soundfx.setPosition(point3d);
            soundfx.setUsrFlag(i);
            soundfx.play();
            return;
        }
    }

    public static void crashParts(com.maddox.JGP.Point3d point3d, int i)
    {
    }

    public static void zenitka(com.maddox.JGP.Point3d point3d, int i)
    {
        if(!com.maddox.il2.objects.sounds.SfxExplosion.init())
        {
            return;
        } else
        {
            com.maddox.sound.SoundFX soundfx = new SoundFX(explodeZenitka);
            soundfx.setPosition(point3d);
            soundfx.setUsrFlag(i <= 1 ? 0 : 1);
            soundfx.play();
            return;
        }
    }

    public static void shell(com.maddox.JGP.Point3d point3d, int i, float f, int j, float f1)
    {
        if(!com.maddox.il2.objects.sounds.SfxExplosion.init())
            return;
        if(f < 0.001F)
            return;
        com.maddox.sound.SoundFX soundfx;
        if(f < 0.02F)
            soundfx = new SoundFX(explodeBullet);
        else
        if(f < 0.5F)
            soundfx = new SoundFX(explodeShell);
        else
        if(f < 50F)
            soundfx = new SoundFX(explodeMiddle);
        else
            soundfx = new SoundFX(explodeBig);
        soundfx.setPosition(point3d);
        soundfx.setUsrFlag(i);
        soundfx.play();
    }

    public static void building(com.maddox.JGP.Point3d point3d, int i, float af[])
    {
    }

    public static void bridge(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, float f)
    {
    }

    public static void wagon(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, float f, int i)
    {
    }

    private static com.maddox.sound.SoundPreset crashTank;
    private static com.maddox.sound.SoundPreset crashAir;
    private static com.maddox.sound.SoundPreset explodeZenitka;
    private static com.maddox.sound.SoundPreset explodeBullet;
    private static com.maddox.sound.SoundPreset explodeShell;
    private static com.maddox.sound.SoundPreset explodeBig;
    private static com.maddox.sound.SoundPreset explodeMiddle;
}
