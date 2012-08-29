// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.il2.objects.sounds.SndAircraft;
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

    private float ZNear(float f)
    {
        if(f < 0.0F)
        {
            return -1F;
        } else
        {
            com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Actor.getByName("camera");
            float f1 = ((com.maddox.il2.engine.Camera) (camera3d)).ZNear;
            camera3d.ZNear = f;
            return f1;
        }
    }

    public com.maddox.il2.engine.hotkey.HookGunner hookGunner()
    {
        return hookGunner;
    }

    public com.maddox.il2.fm.Turret aiTurret()
    {
        if(_aiTuretNum == -1)
            _aiTuretNum = com.maddox.rts.Property.intValue(((java.lang.Object)this).getClass(), "aiTuretNum", 0);
        return super.fm.turret[_aiTuretNum];
    }

    public int weaponControlNum()
    {
        if(_weaponControlNum == -1)
            _weaponControlNum = com.maddox.rts.Property.intValue(((java.lang.Object)this).getClass(), "weaponControlNum", 10);
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
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = false;
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
        ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbs(_tmpLoc2);
        hook.computePos(((com.maddox.il2.engine.Actor) (aircraft)), _tmpLoc2, _tmpLoc1);
        _tmpLoc1.get(((com.maddox.JGP.Tuple3d) (_tmpP1)));
        _tmpLoc1.set(48.899999999999999D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hook.computePos(((com.maddox.il2.engine.Actor) (aircraft)), _tmpLoc2, _tmpLoc1);
        _tmpLoc1.get(((com.maddox.JGP.Tuple3d) (_tmpP2)));
        int i = ((com.maddox.il2.engine.ActorHMesh) (aircraft)).hierMesh().detectCollisionLineMulti(_tmpLoc2, _tmpP1, _tmpP2);
        if(i > 0)
        {
            com.maddox.il2.objects.weapons.Gun gun = aircraft.getGunByHookName(s);
            if(((com.maddox.il2.engine.GunGeneric) (gun)).getTickShot())
            {
                _tmpShot.powerType = 0;
                _tmpShot.mass = ((com.maddox.il2.engine.GunGeneric) (gun)).bulletMassa();
                ((com.maddox.JGP.Tuple3d) (_tmpShot.p)).interpolate(((com.maddox.JGP.Tuple3d) (_tmpP1)), ((com.maddox.JGP.Tuple3d) (_tmpP2)), ((com.maddox.il2.engine.Mesh) (((com.maddox.il2.engine.ActorHMesh) (aircraft)).hierMesh())).collisionDistMulti(0));
                _tmpShot.v.x = (float)(((com.maddox.JGP.Tuple3d) (_tmpP2)).x - ((com.maddox.JGP.Tuple3d) (_tmpP1)).x);
                _tmpShot.v.y = (float)(((com.maddox.JGP.Tuple3d) (_tmpP2)).y - ((com.maddox.JGP.Tuple3d) (_tmpP1)).y);
                _tmpShot.v.z = (float)(((com.maddox.JGP.Tuple3d) (_tmpP2)).z - ((com.maddox.JGP.Tuple3d) (_tmpP1)).z);
                _tmpShot.v.normalize();
                ((com.maddox.JGP.Tuple3d) (_tmpShot.v)).scale(((com.maddox.il2.engine.GunGeneric) (gun)).bulletSpeed());
                aircraft.getSpeed(_tmpV1);
                _tmpShot.v.x += (float)((com.maddox.JGP.Tuple3d) (_tmpV1)).x;
                _tmpShot.v.y += (float)((com.maddox.JGP.Tuple3d) (_tmpV1)).y;
                _tmpShot.v.z += (float)((com.maddox.JGP.Tuple3d) (_tmpV1)).z;
                _tmpShot.tickOffset = 1.0D;
                _tmpShot.chunkName = "CF_D0";
                _tmpShot.initiator = ((com.maddox.il2.engine.Interpolate) (super.fm)).actor;
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
        com.maddox.il2.objects.air.Aircraft aircraft = ((com.maddox.il2.objects.air.Cockpit)this).aircraft();
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        hookgunner.setMover(((com.maddox.il2.engine.hotkey.HookGunner.Move) (this)));
        hookgunner.reset();
        hookgunner.use(true);
        ((com.maddox.il2.engine.Actor) (aircraft)).setAcoustics(((com.maddox.il2.engine.Actor)this).acoustics);
        if(((com.maddox.il2.engine.Actor)this).acoustics != null)
        {
            ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).enableDoorSnd(true);
            if(((com.maddox.il2.engine.Actor)this).acoustics.getEnvNum() == 2)
                ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).setDoorSnd(1.0F);
        }
        ((com.maddox.il2.engine.Actor) (main3d.camera3D)).pos.setRel(new Point3d(), new Orient());
        ((com.maddox.il2.engine.Actor) (main3d.camera3D)).pos.setBase(((com.maddox.il2.engine.Actor) (aircraft)), ((com.maddox.il2.engine.Hook) (hookgunner)), false);
        ((com.maddox.il2.engine.Actor) (main3d.camera3D)).pos.resetAsBase();
        ((com.maddox.il2.engine.Actor)this).pos.resetAsBase();
        ((com.maddox.il2.engine.Actor) (main3d.cameraCockpit)).pos.setRel(new Point3d(), new Orient());
        ((com.maddox.il2.engine.Actor) (main3d.cameraCockpit)).pos.setBase(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (hookgunner)), false);
        ((com.maddox.il2.engine.Actor) (main3d.cameraCockpit)).pos.resetAsBase();
        main3d.overLoad.setShow(true);
        ((com.maddox.il2.engine.Render) (main3d.renderCockpit)).setShow(true);
        ((com.maddox.il2.engine.Actor) (aircraft)).drawing(!((com.maddox.il2.objects.air.Cockpit)this).isNullShow());
        saveZN = ZNear(com.maddox.il2.engine.hotkey.HookPilot.current.isAim() ? gsZN : normZN);
        return true;
    }

    protected void doFocusLeave()
    {
        saveZN = ZNear(saveZN);
        com.maddox.il2.engine.hotkey.HookGunner hookgunner = hookGunner();
        com.maddox.il2.objects.air.Aircraft aircraft = ((com.maddox.il2.objects.air.Cockpit)this).aircraft();
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        hookgunner.use(false);
        ((com.maddox.il2.engine.Actor) (main3d.camera3D)).pos.setRel(new Point3d(), new Orient());
        ((com.maddox.il2.engine.Actor) (main3d.camera3D)).pos.setBase(((com.maddox.il2.engine.Actor) (null)), ((com.maddox.il2.engine.Hook) (null)), false);
        ((com.maddox.il2.engine.Actor) (main3d.cameraCockpit)).pos.setRel(new Point3d(), new Orient());
        ((com.maddox.il2.engine.Actor) (main3d.cameraCockpit)).pos.setBase(((com.maddox.il2.engine.Actor) (null)), ((com.maddox.il2.engine.Hook) (null)), false);
        main3d.overLoad.setShow(false);
        ((com.maddox.il2.engine.Render) (main3d.renderCockpit)).setShow(false);
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (aircraft))))
            ((com.maddox.il2.engine.Actor) (aircraft)).drawing(true);
        if(aircraft != null)
        {
            if((double)((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).CT.getCockpitDoor() < 0.5D)
                ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).setDoorSnd(0.0F);
            else
                ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).setDoorSnd(1.0F);
            ((com.maddox.il2.engine.Actor) (aircraft)).setAcoustics(((com.maddox.sound.Acoustics) (null)));
        }
    }

    public CockpitGunner(java.lang.String s, java.lang.String s1)
    {
        super(s, s1);
        bGunFire = false;
        _tmpOrient = new Orient();
        bNetMirror = false;
        _aiTuretNum = -1;
        _weaponControlNum = -1;
        cameraHook = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.Mesh) (super.mesh)), "CAMERA")));
        ((com.maddox.il2.engine.Actor)this).pos.setBase(((com.maddox.il2.engine.Actor) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())), ((com.maddox.il2.engine.Hook) (new Cockpit.HookOnlyOrient())), false);
        hookGunner = new HookGunner(((com.maddox.il2.engine.Actor) (com.maddox.il2.game.Main3D.cur3D().cameraCockpit)), ((com.maddox.il2.engine.Actor) (com.maddox.il2.game.Main3D.cur3D().camera3D)));
        hookGunner().setMover(((com.maddox.il2.engine.hotkey.HookGunner.Move) (this)));
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        com.maddox.il2.objects.air.Aircraft aircraft = ((com.maddox.il2.objects.air.Cockpit)this).aircraft();
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).CT.Weapons[weaponControlNum()];
        if(abulletemitter != null)
            emitter = abulletemitter[0];
        normZN = com.maddox.rts.Property.floatValue(((java.lang.Object)this).getClass(), "normZN", -1F);
        gsZN = com.maddox.rts.Property.floatValue(((java.lang.Object)this).getClass(), "gsZN", -1F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return class1;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
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
    private float saveZN;
    protected float normZN;
    protected float gsZN;
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
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitGunner.class)))), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitGunner.class)))), "weaponControlNum", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitGunner.class)))), "astatePilotIndx", 1);
    }
}
