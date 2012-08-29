// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SndAircraft.java

package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.BaseObject;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;

// Referenced classes of package com.maddox.il2.objects.sounds:
//            MotorSound

public class SndAircraft extends com.maddox.il2.engine.ActorHMesh
{

    public com.maddox.sound.SoundFX getRootFX()
    {
        return sndRoot;
    }

    public void sfxInit(int i)
    {
        if(sndRoot != null)
        {
            com.maddox.rts.SectFile sectfile = new SectFile("presets/sounds/aircraft.misc.prs");
            java.lang.String s = i != 0 ? "e_" : "p_";
            sndGear = (new Sample(sectfile, s + "gear")).get();
            sndRoot.add(sndGear);
            if(!haveFlaps())
                s = "m_";
            sndFlaps = (new Sample(sectfile, s + "flaps")).get();
            sndRoot.add(sndFlaps);
            if(FM instanceof com.maddox.il2.fm.RealFlightModel)
            {
                sndAirBrake = (new Sample(sectfile, "air_brake")).get();
                sndRoot.add(sndAirBrake);
            }
        }
        if(prsHit == null)
            prsHit = new SoundPreset("hit.air");
    }

    protected boolean haveFlaps()
    {
        return true;
    }

    public void sfxCrash(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.sound.SoundFX soundfx = newSound("crash.cutoff", true);
        if(soundfx != null)
        {
            soundfx.setPosition(point3d);
            soundfx.setParent(sndRoot);
        }
    }

    public void sfxHit(float f, com.maddox.JGP.Point3d point3d)
    {
        if(sndRoot != null)
        {
            com.maddox.sound.SoundFX soundfx = newSound(prsHit, false, true);
            soundfx.setParent(sndRoot);
            soundfx.setPosition(point3d);
            soundfx.setUsrFlag(f <= 0.05F ? 0 : 1);
            soundfx.play();
        }
    }

    public void sfxTow()
    {
        dShake = 1.0F;
        com.maddox.sound.SoundFX soundfx = newSound("aircraft.tow", true);
        if(soundfx != null)
        {
            soundfx.setPosition(new Point3d(0.0D, 0.0D, -1D));
            soundfx.setParent(sndRoot);
        }
    }

    public void sfxWheels()
    {
        com.maddox.sound.SoundFX soundfx = newSound("aircraft.wheels", true);
        if(soundfx != null)
        {
            soundfx.setPosition(new Point3d(0.0D, 0.0D, -1.5D));
            soundfx.setParent(sndRoot);
        }
    }

    public void setDoorSnd(float f)
    {
        if(FM != null)
        {
            doorSndControl = f;
            if(doorSound == null && FM.CT.dvCockpitDoor > 0.0F)
            {
                byte byte0 = 1;
                float f1 = 1.0F / FM.CT.dvCockpitDoor;
                doorSndPos = new Point3d(-1D, 0.0D, 0.0D);
                doorSound = newSound("aircraft.door", false);
                if(doorSound != null)
                {
                    doorSound.setParent(getRootFX());
                    if(f1 <= 1.1F)
                        byte0 = 0;
                    else
                    if(f1 >= 1.8F)
                        byte0 = 2;
                    doorSound.setUsrFlag(byte0);
                }
            }
            if((f != 0.0F && doorPrev == 0.0F || f != 1.0F && doorPrev == 1.0F) && doorSound != null)
                doorSound.play(doorSndPos);
            doorPrev = f;
        }
    }

    public void enableDoorSnd(boolean flag)
    {
        doorEnabled = flag;
    }

    public void sfxSmokeState(int i, int j, boolean flag)
    {
        if(flag)
        {
            smf[i] |= 1 << j;
            if(smokeSound == null)
                smokeSound = newSound("objects.flame", true);
        } else
        {
            boolean flag1 = true;
            smf[i] &= ~(1 << j);
            for(int k = 0; k < 3; k++)
                flag1 = flag1 && smf[k] == 0;

            if(i == 0)
                flag1 = true;
            if(flag1 && smokeSound != null)
            {
                smokeSound.cancel();
                smokeSound = null;
            }
        }
    }

    public void sfxGear(boolean flag)
    {
        if(sndGear != null)
            sndGear.setPlay(flag);
    }

    public void sfxFlaps(boolean flag)
    {
        if(sndFlaps != null)
            sndFlaps.setPlay(flag);
    }

    public void sfxAirBrake()
    {
        if(sndAirBrake != null)
            sndAirBrake.play();
    }

    public void updateLLights()
    {
    }

    public void update(float f)
    {
        if(dShake > 0.0F)
        {
            dShake -= f / 1.7F;
            if(dShake <= 0.05F)
                dShake = 0.0F;
        }
        if(dWheels > 0.0F)
        {
            dWheels -= f;
            if(dWheels < 0.0F)
                dWheels = 0.0F;
        }
    }

    public SndAircraft()
    {
        FM = null;
        sndRoot = null;
        doorSndControl = 0.0F;
        sndRelPos = new Point3d(3D, 0.0D, 0.0D);
        motorSnd = null;
        bDiving = false;
        sndDivingState = false;
        divState = 0.0F;
        divIncr = 0.0F;
        prevRt = 0L;
        sndGear = null;
        sndFlaps = null;
        sndAirBrake = null;
        smokeSound = null;
        doorSound = null;
        dShake = 0.0F;
        dWheels = 0.0F;
        prevWG = true;
        doorPrev = 0.0F;
        doorEnabled = false;
        doorSndPos = null;
        smf = new int[3];
        for(int i = 0; i < 3; i++)
            smf[i] = 0;

        draw = new com.maddox.il2.engine.ActorMeshDraw() {

            public int preRender(com.maddox.il2.engine.Actor actor)
            {
                updateLLights();
                return super.preRender(actor);
            }

            public void soundUpdate(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc)
            {
                if(!com.maddox.il2.engine.Config.cur.isSoundUse() || sndRoot == null)
                    return;
                if(FM != null)
                {
                    if(bDiving)
                    {
                        if(FM.CT.AirBrakeControl > 0.4F)
                        {
                            if(divState == 0.0F || divIncr < 0.0F)
                                divIncr = 0.3F;
                        } else
                        if(divState > 0.0F)
                            divIncr = -0.39F;
                        else
                            divIncr = 0.0F;
                        if(divState > 1.0F)
                        {
                            divIncr = 0.0F;
                            divState = 1.0F;
                        } else
                        if(divState < 0.0F)
                        {
                            divIncr = 0.0F;
                            divState = 0.0F;
                        }
                        sndRoot.setControl(102, divState);
                        long l = com.maddox.rts.Time.real();
                        if(divIncr != 0.0F)
                            divState+= = ((float)(l - prevRt) * divIncr) / 1000F;
                        prevRt = l;
                    } else
                    {
                        sndRoot.setControl(102, 0.0F);
                    }
                    if(FM instanceof com.maddox.il2.fm.RealFlightModel)
                    {
                        float f = ((com.maddox.il2.fm.RealFlightModel)FM).shakeLevel + dShake;
                        if(f > 1.0F)
                            f = 1.0F;
                        sndRoot.setControl(103, f);
                    }
                    sndRoot.setControl(104, (FM.Gears.gVelocity[0] <= 0.40000000596046448D || !FM.Gears.lgear) && (FM.Gears.gVelocity[1] <= 0.40000000596046448D || !FM.Gears.rgear) && FM.Gears.gVelocity[2] <= 0.40000000596046448D ? 0.0F : FM.CT.BrakeControl);
                    sndRoot.setControl(105, FM.Gears.getLandingState());
                    sndRoot.setControl(111, (float)FM.Vrel.length());
                    boolean flag = FM.Gears.getWheelsOnGround();
                    if(!prevWG && flag && dWheels == 0.0F)
                        sfxWheels();
                    if(flag)
                        dWheels = 4F;
                    prevWG = flag;
                }
                if(doorEnabled)
                    sndRoot.setControl(110, doorSndControl);
                else
                    sndRoot.setControl(110, 0.0F);
                if(motorSnd != null)
                {
                    for(int j = 0; j < motorSnd.length; j++)
                        motorSnd[j].update();

                }
                super.soundUpdate(actor, loc);
            }

        }
;
    }

    public boolean hasInternalSounds()
    {
        return false;
    }

    public void setMotorPos(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.JGP.Point3d point3d1 = null;
        if(point3d != null)
        {
            point3d1 = new Point3d(pos.getAbsPoint());
            point3d1.sub(point3d);
        }
        for(int i = 0; i < motorSnd.length; i++)
            motorSnd[i].setPos(point3d1);

    }

    protected void initSound(com.maddox.rts.SectFile sectfile)
    {
        if(!com.maddox.sound.BaseObject.isEnabled())
            return;
        if(this instanceof com.maddox.il2.objects.air.TypeSailPlane)
            sndRoot = newSound("aircraft.common_w", true);
        else
            sndRoot = newSound("aircraft.common", true);
        int i = FM.EI.getNum();
        motorSnd = new com.maddox.il2.objects.sounds.MotorSound[i];
        for(int j = 0; j < i; j++)
            motorSnd[j] = new MotorSound(FM.EI.engines[j], this);

        bDiving = sectfile.get("SOUND", "Diving", "").length() > 0;
        sfxInit(0);
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        super.destroy();
        if(sndRoot != null)
            sndRoot.cancel();
        if(sndGear != null)
            sndGear.cancel();
        if(sndFlaps != null)
            sndFlaps.cancel();
        if(sndAirBrake != null)
            sndAirBrake.cancel();
        if(smokeSound != null)
            smokeSound.cancel();
    }

    public com.maddox.il2.fm.FlightModel FM;
    protected com.maddox.sound.SoundFX sndRoot;
    protected float doorSndControl;
    protected com.maddox.JGP.Point3d sndRelPos;
    protected com.maddox.il2.objects.sounds.MotorSound motorSnd[];
    private static final float divSpeed = 0.3F;
    private boolean bDiving;
    private boolean sndDivingState;
    private float divState;
    private float divIncr;
    private static final float GV0 = 0.4F;
    private long prevRt;
    public static final int FEED_PNEUMATIC = 0;
    public static final int FEED_ELECTRIC = 1;
    public static final int FEED_HYDRAULIC = 2;
    protected com.maddox.sound.AudioStream sndGear;
    protected com.maddox.sound.AudioStream sndFlaps;
    protected com.maddox.sound.AudioStream sndAirBrake;
    protected com.maddox.sound.SoundFX smokeSound;
    protected com.maddox.sound.SoundFX doorSound;
    private float dShake;
    private float dWheels;
    private boolean prevWG;
    protected static com.maddox.sound.SoundPreset prsHit = null;
    private float doorPrev;
    private boolean doorEnabled;
    private com.maddox.JGP.Point3d doorSndPos;
    private int smf[];















}
