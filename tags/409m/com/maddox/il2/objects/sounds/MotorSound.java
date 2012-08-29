// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MotorSound.java

package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.il2.fm.FmSounds;
import com.maddox.il2.fm.Motor;
import com.maddox.sound.BaseObject;
import com.maddox.sound.SamplePool;
import com.maddox.sound.SoundFX;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.sounds:
//            SndAircraft

public class MotorSound
    implements com.maddox.il2.fm.FmSounds
{

    public MotorSound(com.maddox.il2.fm.Motor motor1, com.maddox.il2.objects.sounds.SndAircraft sndaircraft)
    {
        owner = null;
        motor = null;
        sndStartState = 0;
        dmgRnd = new Random();
        nextTime = 0L;
        prevTime = 0L;
        tDmg = 0.5F;
        kDmg = 0.0F;
        sndMotor = null;
        sndProp = null;
        spStart = null;
        spEnd = null;
        prevState = 0;
        bRunning = false;
        owner = sndaircraft;
        motor = motor1;
        if(!com.maddox.sound.BaseObject.isEnabled())
            return;
        java.lang.String s = motor1.soundName;
        java.lang.String s1 = motor1.propName;
        java.lang.String s2 = motor1.startStopName;
        if(s == null)
            s = "db605";
        s = "motor." + s;
        if(s2 == null)
            s2 = "std_p";
        else
        if(s2.compareToIgnoreCase("none") == 0)
            s2 = null;
        if(s2 != null)
        {
            spStart = new SamplePool("motor." + s2 + ".start.begin");
            spEnd = new SamplePool("motor." + s2 + ".start.end");
        }
        sndMotor = sndaircraft.newSound(s, true);
        if(sndMotor != null)
        {
            sndMotor.setParent(sndaircraft.getRootFX());
            sndMotor.setPosition(motor1.getEnginePos());
            sndMotor.setControl(500, 0.0F);
        }
        if(s1 != null && !"".equals(s1))
        {
            s1 = "propeller." + s1;
            sndProp = sndaircraft.newSound(s1, true);
            if(sndProp != null)
            {
                sndProp.setParent(sndaircraft.getRootFX());
                sndProp.setPosition(motor1.getEnginePos());
            }
        }
        motor1.isnd = this;
    }

    public void setPos(com.maddox.JGP.Point3d point3d)
    {
    }

    public void onEngineState(int i)
    {
        if(sndMotor != null)
        {
            if(i != prevState)
            {
                if(i == 2 && spStart != null)
                    sndMotor.play(spStart);
                if(i == 4 && spEnd != null)
                    sndMotor.play(spEnd);
            }
            if(i > 2 && i <= 6)
            {
                if(i == 3)
                {
                    sndMotor.setControl(500, 1.0F);
                    sndMotor.setControl(501, 10F);
                }
                if(i == 5)
                    sndMotor.setControl(501, 20F);
                if(i == 6)
                {
                    sndMotor.setControl(500, 1.0F);
                    bRunning = true;
                }
            }
            sndMotor.setControl(106, i <= 1 || i >= 4 ? 0.0F : 1.0F);
            sndMotor.setControl(112, i);
        }
        prevState = i;
    }

    public void update()
    {
label0:
        {
            float f;
            boolean flag;
label1:
            {
label2:
                {
                    f = motor.getRPM();
                    if(sndMotor == null)
                        break label0;
                    int i = motor.getType();
                    com.maddox.il2.fm.Motor _tmp = motor;
                    if(i != 0)
                    {
                        com.maddox.il2.fm.Motor _tmp1 = motor;
                        if(i != 1)
                            break label2;
                    }
                    flag = true;
                    break label1;
                }
                flag = false;
            }
            float f2 = motor.getReadyness();
            float f3 = f;
            if(f2 < 0.0F)
                f2 = 0.0F;
            f2 = 1.0F - f2;
            if(flag && prevState == 0)
                bRunning = f > 5F;
            if(bRunning)
            {
                if(!flag && f < 60F && prevState == 0)
                    bRunning = false;
                if(f < 1200F)
                {
                    sndMotor.setControl(501, f / 30F);
                    sndMotor.setControl(502, f2 * 0.7F);
                    if(f > 400F)
                        sndMotor.setControl(503, (0.8F * (1200F - f)) / 800F);
                    else
                        sndMotor.setControl(503, 0.8F);
                } else
                {
                    sndMotor.setControl(501, 0.0F);
                }
            } else
            {
                sndMotor.setControl(501, 0.0F);
            }
            float f4 = f;
            if(f2 > 0.0F)
                f4 *= 1.0F - f2 * 0.15F * dmgRnd.nextFloat();
            sndMotor.setControl(100, f4);
            sndMotor.setControl(101, f2);
        }
        if(sndProp != null)
        {
            float f1 = motor.getPropRPM();
            sndProp.setControl(100, f1);
        }
    }

    protected com.maddox.il2.objects.sounds.SndAircraft owner;
    protected com.maddox.il2.fm.Motor motor;
    protected int sndStartState;
    protected java.util.Random dmgRnd;
    protected long nextTime;
    protected long prevTime;
    protected float tDmg;
    protected float kDmg;
    protected com.maddox.sound.SoundFX sndMotor;
    protected com.maddox.sound.SoundFX sndProp;
    protected com.maddox.sound.SamplePool spStart;
    protected com.maddox.sound.SamplePool spEnd;
    private int prevState;
    private boolean bRunning;
}
