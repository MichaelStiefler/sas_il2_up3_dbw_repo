// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;

// Referenced classes of package com.maddox.il2.objects.air:
//            Cockpit, Aircraft

public class CockpitGunner extends com.maddox.il2.objects.air.Cockpit
    implements com.maddox.il2.engine.hotkey.HookGunner.Move
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
                interpTick();
            return true;
        }

        Interpolater()
        {
        }
    }


    public boolean isEnableHotKeysOnOutsideView()
    {
        return false;
    }

    public java.lang.String[] getHotKeyEnvs()
    {
        return hotKeyEnvs;
    }

    public com.maddox.il2.engine.hotkey.HookGunner hookGunner()
    {
        return hookGunner;
    }

    public com.maddox.il2.fm.Turret aiTurret()
    {
        if(_aiTuretNum == -1)
            _aiTuretNum = com.maddox.rts.Property.intValue(getClass(), "aiTuretNum", 0);
        return fm.turret[_aiTuretNum];
    }

    public int weaponControlNum()
    {
        if(_weaponControlNum == -1)
            _weaponControlNum = com.maddox.rts.Property.intValue(getClass(), "weaponControlNum", 10);
        return _weaponControlNum;
    }

    public boolean isRealMode()
    {
        return !aiTurret().bIsAIControlled;
    }

    public void setRealMode(boolean flag)
    {
        if(aiTurret().bIsAIControlled != flag)
            return;
        aiTurret().bIsAIControlled = !flag;
        aiTurret().target = null;
        fm.CT.WeaponControl[weaponControlNum()] = false;
        bGunFire = false;
        if(flag)
        {
            hookGunner().resetMove(0.0F, 0.0F);
        } else
        {
            aiTurret().tu[0] = 0.0F;
            aiTurret().tu[1] = 0.0F;
        }
    }

    public boolean isNetMirror()
    {
        return bNetMirror;
    }

    public void setNetMirror(boolean flag)
    {
        bNetMirror = flag;
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        if(isRealMode())
        {
            aiTurret().tu[0] = orient.getAzimut();
            aiTurret().tu[1] = orient.getTangage();
        }
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
    }

    public com.maddox.il2.engine.Hook getHookCameraGun()
    {
        if(!isRealMode())
        {
            _tmpOrient.set(aiTurret().tu[0], aiTurret().tu[1], 0.0F);
            moveGun(_tmpOrient);
        }
        return cameraHook;
    }

    public void doGunFire(boolean flag)
    {
        if(emitter != null && !emitter.haveBullets())
            bGunFire = false;
        else
            bGunFire = flag;
    }

    protected void doHitMasterAircraft(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.engine.Hook hook, java.lang.String s)
    {
        _tmpLoc1.set(0.10000000000000001D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        aircraft.pos.getAbs(_tmpLoc2);
        hook.computePos(aircraft, _tmpLoc2, _tmpLoc1);
        _tmpLoc1.get(_tmpP1);
        _tmpLoc1.set(48.899999999999999D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hook.computePos(aircraft, _tmpLoc2, _tmpLoc1);
        _tmpLoc1.get(_tmpP2);
        int i = aircraft.hierMesh().detectCollisionLineMulti(_tmpLoc2, _tmpP1, _tmpP2);
        if(i > 0)
        {
            com.maddox.il2.objects.weapons.Gun gun = aircraft.getGunByHookName(s);
            if(gun.getTickShot())
            {
                _tmpShot.powerType = 0;
                _tmpShot.mass = gun.bulletMassa();
                _tmpShot.p.interpolate(_tmpP1, _tmpP2, aircraft.hierMesh().collisionDistMulti(0));
                _tmpShot.v.x = (float)(_tmpP2.x - _tmpP1.x);
                _tmpShot.v.y = (float)(_tmpP2.y - _tmpP1.y);
                _tmpShot.v.z = (float)(_tmpP2.z - _tmpP1.z);
                _tmpShot.v.normalize();
                _tmpShot.v.scale(gun.bulletSpeed());
                aircraft.getSpeed(_tmpV1);
                _tmpShot.v.x += (float)_tmpV1.x;
                _tmpShot.v.y += (float)_tmpV1.y;
                _tmpShot.v.z += (float)_tmpV1.z;
                _tmpShot.tickOffset = 1.0D;
                _tmpShot.chunkName = "CF_D0";
                _tmpShot.initiator = fm.actor;
                aircraft.msgShot(_tmpShot);
            }
        }
    }

    protected void _setNullShow(boolean flag)
    {
    }

    protected void _setEnableRendering(boolean flag)
    {
    }

    protected void interpTick()
    {
    }

    protected boolean doFocusEnter()
    {
        com.maddox.il2.engine.hotkey.HookGunner hookgunner = hookGunner();
        com.maddox.il2.objects.air.Aircraft aircraft = aircraft();
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        hookgunner.setMover(this);
        hookgunner.reset();
        hookgunner.use(true);
        aircraft.setAcoustics(acoustics);
        if(acoustics != null)
        {
            aircraft.enableDoorSnd(true);
            if(acoustics.getEnvNum() == 2)
                aircraft.setDoorSnd(1.0F);
        }
        main3d.camera3D.pos.setRel(new Point3d(), new Orient());
        main3d.camera3D.pos.setBase(aircraft, hookgunner, false);
        main3d.camera3D.pos.resetAsBase();
        pos.resetAsBase();
        main3d.cameraCockpit.pos.setRel(new Point3d(), new Orient());
        main3d.cameraCockpit.pos.setBase(this, hookgunner, false);
        main3d.cameraCockpit.pos.resetAsBase();
        main3d.overLoad.setShow(true);
        main3d.renderCockpit.setShow(true);
        aircraft.drawing(!isNullShow());
        return true;
    }

    protected void doFocusLeave()
    {
        com.maddox.il2.engine.hotkey.HookGunner hookgunner = hookGunner();
        com.maddox.il2.objects.air.Aircraft aircraft = aircraft();
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        hookgunner.use(false);
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
    }

    public CockpitGunner(java.lang.String s, java.lang.String s1)
    {
        super(s, s1);
        bGunFire = false;
        _tmpOrient = new Orient();
        bNetMirror = false;
        _aiTuretNum = -1;
        _weaponControlNum = -1;
        cameraHook = new HookNamed(mesh, "CAMERA");
        pos.setBase(aircraft(), new Cockpit.HookOnlyOrient(), false);
        hookGunner = new HookGunner(com.maddox.il2.game.Main3D.cur3D().cameraCockpit, com.maddox.il2.game.Main3D.cur3D().camera3D);
        hookGunner().setMover(this);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        com.maddox.il2.objects.air.Aircraft aircraft = aircraft();
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = aircraft.FM.CT.Weapons[weaponControlNum()];
        if(abulletemitter != null)
            emitter = abulletemitter[0];
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private java.lang.String hotKeyEnvs[] = {
        "gunner"
    };
    private java.lang.String hotKeyEnvsAll[] = {
        "gunner", "pilot", "move"
    };
    protected com.maddox.il2.engine.Hook cameraHook;
    protected boolean bGunFire;
    protected com.maddox.il2.ai.BulletEmitter emitter;
    private com.maddox.il2.engine.Orient _tmpOrient;
    private com.maddox.il2.engine.hotkey.HookGunner hookGunner;
    private boolean bNetMirror;
    protected int _aiTuretNum;
    protected int _weaponControlNum;
    private static com.maddox.il2.engine.Loc _tmpLoc1 = new Loc();
    private static com.maddox.il2.engine.Loc _tmpLoc2 = new Loc();
    private static com.maddox.JGP.Point3d _tmpP1 = new Point3d();
    private static com.maddox.JGP.Point3d _tmpP2 = new Point3d();
    private static com.maddox.JGP.Vector3d _tmpV1 = new Vector3d();
    private static com.maddox.il2.ai.Shot _tmpShot = new Shot();

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitGunner.class, "astatePilotIndx", 1);
    }
}
