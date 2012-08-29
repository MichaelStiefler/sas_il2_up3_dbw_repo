// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitPilot.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            Cockpit, AircraftLH, Aircraft

public class CockpitPilot extends com.maddox.il2.objects.air.Cockpit
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(isPadlock())
                com.maddox.il2.engine.hotkey.HookPilot.current.checkPadlockState();
            return true;
        }

        Interpolater()
        {
        }
    }


    public boolean isEnableHotKeysOnOutsideView()
    {
        return true;
    }

    public java.lang.String[] getHotKeyEnvs()
    {
        return hotKeyEnvs;
    }

    private float ZNear(float f)
    {
        if(f < 0.0F)
        {
            return -1F;
        } else
        {
            com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Actor.getByName("camera");
            float f1 = camera3d.ZNear;
            camera3d.ZNear = f;
            return f1;
        }
    }

    protected boolean doFocusEnter()
    {
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        com.maddox.il2.objects.air.Aircraft aircraft = aircraft();
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        hookpilot.setCenter(cameraCenter);
        hookpilot.setAim(cameraAim);
        hookpilot.setUp(cameraUp);
        if(!com.maddox.il2.net.NetMissionTrack.isPlaying() || com.maddox.il2.net.NetMissionTrack.playingOriginalVersion() > 101)
        {
            hookpilot.setSteps(stepAzimut, stepTangage);
            hookpilot.setMinMax(minMaxAzimut, minTangage, maxTangage);
        } else
        {
            hookpilot.setSteps(45F, 30F);
            hookpilot.setMinMax(135F, -60F, 90F);
        }
        hookpilot.reset();
        hookpilot.use(true);
        aircraft.setAcoustics(acoustics);
        if(acoustics != null)
        {
            aircraft.enableDoorSnd(true);
            if(acoustics.getEnvNum() == 2)
                aircraft.setDoorSnd(1.0F);
        }
        main3d.camera3D.pos.setRel(new Point3d(), new Orient());
        main3d.camera3D.pos.setBase(aircraft, hookpilot, false);
        main3d.camera3D.pos.resetAsBase();
        pos.resetAsBase();
        aircraft().setMotorPos(main3d.camera3D.pos.getAbsPoint());
        main3d.cameraCockpit.pos.setRel(new Point3d(), new Orient());
        main3d.cameraCockpit.pos.setBase(this, hookpilot, false);
        main3d.cameraCockpit.pos.resetAsBase();
        main3d.overLoad.setShow(true);
        main3d.renderCockpit.setShow(true);
        aircraft.drawing(!isNullShow());
        saveZN = ZNear(com.maddox.il2.engine.hotkey.HookPilot.current.isAim() ? gsZN : normZN);
        bBeaconKeysEnabled = ((com.maddox.il2.objects.air.AircraftLH)aircraft()).bWantBeaconKeys;
        ((com.maddox.il2.objects.air.AircraftLH)aircraft()).bWantBeaconKeys = true;
        return true;
    }

    protected void doFocusLeave()
    {
        saveZN = ZNear(saveZN);
        ((com.maddox.il2.objects.air.AircraftLH)aircraft()).bWantBeaconKeys = bBeaconKeysEnabled;
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        com.maddox.il2.objects.air.Aircraft aircraft = aircraft();
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        hookpilot.use(false);
        main3d.camera3D.pos.setRel(new Point3d(), new Orient());
        main3d.camera3D.pos.setBase(null, null, false);
        main3d.cameraCockpit.pos.setRel(new Point3d(), new Orient());
        main3d.cameraCockpit.pos.setBase(null, null, false);
        main3d.overLoad.setShow(false);
        main3d.renderCockpit.setShow(false);
        if(com.maddox.il2.engine.Actor.isValid(aircraft))
            aircraft.drawing(true);
        if(aircraft != null)
            aircraft.setAcoustics(null);
        aircraft.enableDoorSnd(false);
        aircraft().setMotorPos(null);
    }

    public boolean existPadlock()
    {
        return true;
    }

    public boolean isPadlock()
    {
        if(!isFocused())
        {
            return false;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            return hookpilot.isPadlock();
        }
    }

    public com.maddox.il2.engine.Actor getPadlockEnemy()
    {
        if(!isFocused())
        {
            return null;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            return hookpilot.isPadlock() ? hookpilot.getEnemy() : null;
        }
    }

    public boolean startPadlock(com.maddox.il2.engine.Actor actor)
    {
        if(!isFocused())
        {
            return false;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            return hookpilot.startPadlock(actor);
        }
    }

    public void stopPadlock()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.stopPadlock();
            return;
        }
    }

    public void endPadlock()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.endPadlock();
            return;
        }
    }

    public void setPadlockForward(boolean flag)
    {
        if(!isFocused())
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.setForward(flag);
            return;
        }
    }

    public boolean isToggleAim()
    {
        if(!isFocused())
        {
            return false;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            return hookpilot.isAim();
        }
    }

    public void doToggleAim(boolean flag)
    {
        if(!isFocused())
            return;
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.doAim(flag);
        if(flag)
            ZNear(gsZN);
        else
            ZNear(normZN);
    }

    public boolean isToggleUp()
    {
        if(!isFocused())
        {
            return false;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            return hookpilot.isUp();
        }
    }

    public void doToggleUp(boolean flag)
    {
        if(!isFocused())
            return;
        if(flag && cameraUp == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doUp(flag);
            return;
        }
    }

    public CockpitPilot(java.lang.String s, java.lang.String s1)
    {
        super(s, s1);
        stepAzimut = 45F;
        stepTangage = 30F;
        minMaxAzimut = 145F;
        maxTangage = 90F;
        minTangage = -60F;
        cameraCenter = new Point3d();
        pictBall = 0.0D;
        oldBallTime = 0L;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "CAMERA");
        com.maddox.il2.engine.Loc loc = new Loc();
        hooknamed.computePos(this, pos.getAbs(), loc);
        loc.get(cameraCenter);
        try
        {
            com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(mesh, "CAMERAAIM");
            loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed1.computePos(this, pos.getAbs(), loc);
            cameraAim = new Point3d();
            loc.get(cameraAim);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        try
        {
            com.maddox.il2.engine.HookNamed hooknamed2 = new HookNamed(mesh, "CAMERAUP");
            loc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hooknamed2.computePos(this, pos.getAbs(), loc);
            cameraUp = new Point3d();
            loc.get(cameraUp);
        }
        catch(java.lang.Exception exception1) { }
        pos.setBase(aircraft(), new Cockpit.HookOnlyOrient(), false);
        interpPut(new Interpolater(), "CockpitPilot", com.maddox.rts.Time.current(), null);
        if(com.maddox.il2.engine.hotkey.HookPilot.current != null)
            com.maddox.il2.engine.hotkey.HookPilot.current.doUp(false);
        normZN = com.maddox.rts.Property.floatValue(getClass(), "normZN", -1F);
        gsZN = com.maddox.rts.Property.floatValue(getClass(), "gsZN", -1F);
    }

    protected float getBall(double d)
    {
        double d1 = 0.0D;
        long l = com.maddox.rts.Time.current();
        long l1 = l - oldBallTime;
        oldBallTime = l;
        if(l1 > 200L)
            l1 = 200L;
        double d4 = 0.00038000000000000002D * (double)l1;
        if(-fm.getBallAccel().z > 0.001D)
        {
            double d2 = java.lang.Math.toDegrees(java.lang.Math.atan2(fm.getBallAccel().y, -fm.getBallAccel().z));
            if(d2 > 20D)
                d2 = 20D;
            else
            if(d2 < -20D)
                d2 = -20D;
            pictBall = (1.0D - d4) * pictBall + d4 * d2;
        } else
        {
            double d3;
            if(pictBall > 0.0D)
                d3 = 20D;
            else
                d3 = -20D;
            pictBall = (1.0D - d4) * pictBall + d4 * d3;
        }
        if(pictBall > d)
            pictBall = d;
        else
        if(pictBall < -d)
            pictBall = -d;
        return (float)pictBall;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private java.lang.String hotKeyEnvs[] = {
        "pilot", "move"
    };
    protected float stepAzimut;
    protected float stepTangage;
    protected float minMaxAzimut;
    protected float maxTangage;
    protected float minTangage;
    protected com.maddox.JGP.Point3d cameraCenter;
    private com.maddox.JGP.Point3d cameraAim;
    private com.maddox.JGP.Point3d cameraUp;
    private boolean bBeaconKeysEnabled;
    private float saveZN;
    protected float normZN;
    protected float gsZN;
    private double pictBall;
    private long oldBallTime;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitPilot.class, "astatePilotIndx", 0);
    }
}
