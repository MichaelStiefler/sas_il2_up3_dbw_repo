// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitMig_17Radar.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Aircraft, AircraftLH, Cockpit

public class CockpitMig_17Radar extends com.maddox.il2.objects.air.CockpitPilot
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
            viewDX = main3d.renderHUD.getViewPortWidth();
            viewDY = main3d.renderHUD.getViewPortHeight();
            if(hookpilot.isPadlock())
                hookpilot.stopPadlock();
            hookpilot.reset();
            enter();
            go_top();
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            leave();
            super.doFocusLeave();
            return;
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
    }

    private void leave()
    {
        if(!bEntered)
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            return;
        }
    }

    private void go_top()
    {
        bBAiming = false;
        com.maddox.rts.CmdEnv.top().exec("fov 43.2");
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
    }

    private void go_bottom()
    {
        bBAiming = true;
        com.maddox.rts.CmdEnv.top().exec("fov 23.913");
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        hookpilot.reset();
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.setSimpleUse(false);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
    }

    public void destroy()
    {
        super.destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(!isFocused())
            return;
        if(isToggleAim() == flag)
            return;
        else
            return;
    }

    public CockpitMig_17Radar()
    {
        super("3DO/Cockpit/Radars/Izumrud.him", "He111");
        FOV = 1.0D;
        ScX = 0.0099999997764825821D;
        ScY = 0.0099999997764825821D;
        ScZ = 0.0010000000474974513D;
        FOrigX = 0.0F;
        FOrigY = 0.0F;
        nTgts = 10;
        RRange = 12000F;
        RClose = 5F;
        radarPlane = new ArrayList();
        bEntered = false;
        bBAiming = false;
    }

    public void reflectWorldToInstruments(float f0)
    {
        draw();
    }

    public void draw()
    {
        try
        {
            com.maddox.il2.objects.air.Aircraft ownaircraft = com.maddox.il2.ai.World.getPlayerAircraft();
            if(!com.maddox.il2.game.Mission.isNet() && com.maddox.il2.engine.Actor.isValid(ownaircraft) && com.maddox.il2.engine.Actor.isAlive(ownaircraft))
            {
                int ti = (int)((com.maddox.rts.Time.current() % 1000L) / 500L);
                if(ti != to)
                {
                    to = ti;
                    com.maddox.JGP.Point3d pointAC = ((com.maddox.il2.engine.Actor) (ownaircraft)).pos.getAbsPoint();
                    com.maddox.il2.engine.Orient orientAC = ((com.maddox.il2.engine.Actor) (ownaircraft)).pos.getAbsOrient();
                    radarPlane.clear();
                    java.util.List list = com.maddox.il2.engine.Engine.targets();
                    int i = list.size();
                    for(int j = 0; j < i; j++)
                    {
                        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
                        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft) || actor == com.maddox.il2.ai.World.getPlayerAircraft())
                            continue;
                        com.maddox.JGP.Point3d pointOrtho = new Point3d();
                        pointOrtho.set(actor.pos.getAbsPoint());
                        pointOrtho.sub(pointAC);
                        orientAC.transformInv(pointOrtho);
                        if(((com.maddox.JGP.Tuple3d) (pointOrtho)).x > (double)RClose && ((com.maddox.JGP.Tuple3d) (pointOrtho)).x < (double)RRange)
                            radarPlane.add(pointOrtho);
                    }

                }
                int i = radarPlane.size();
                int nt = 0;
                for(int j = 0; j < i; j++)
                {
                    double x = ((com.maddox.JGP.Tuple3d) ((com.maddox.JGP.Point3d)radarPlane.get(j))).x;
                    if(x <= (double)RClose || nt > nTgts)
                        continue;
                    FOV = 26D / x;
                    double NewX = -((com.maddox.JGP.Tuple3d) ((com.maddox.JGP.Point3d)radarPlane.get(j))).y * FOV;
                    double NewY = ((com.maddox.JGP.Tuple3d) ((com.maddox.JGP.Point3d)radarPlane.get(j))).z * FOV;
                    float f = FOrigX + (float)(NewX * ScX);
                    float f1 = FOrigY + (float)(NewY * ScY);
                    nt++;
                    java.lang.String t = "R-Signal" + nt;
                    super.mesh.setCurChunk(t);
                    super.mesh.setScaleXYZ(0.5F, 0.5F, 0.5F);
                    resetYPRmodifier();
                    com.maddox.il2.objects.air.Cockpit.xyz[0] = -f;
                    com.maddox.il2.objects.air.Cockpit.xyz[2] = f1;
                    super.mesh.chunkSetLocate(com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                    super.mesh.render();
                    if(!super.mesh.isChunkVisible(t))
                        super.mesh.chunkVisible(t, true);
                }

                for(int j = nt + 1; j <= nTgts; j++)
                {
                    java.lang.String t = "R-Signal" + j;
                    if(super.mesh.isChunkVisible(t))
                        super.mesh.chunkVisible(t, false);
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void initDashBoard()
    {
        spriteLeft = com.maddox.il2.engine.Mat.New("gui/game/hud/hudleft.mat");
        spriteRight = com.maddox.il2.engine.Mat.New("gui/game/hud/hudright.mat");
        meshNeedle1 = new Mesh("gui/game/hud/needle1/mono.sim");
        meshNeedle2 = new Mesh("gui/game/hud/needle2/mono.sim");
        meshNeedle3 = new Mesh("gui/game/hud/needle3/mono.sim");
        meshNeedle4 = new Mesh("gui/game/hud/needle4/mono.sim");
        meshNeedle5 = new Mesh("gui/game/hud/needle5/mono.sim");
        meshNeedle6 = new Mesh("gui/game/hud/needle6/mono.sim");
        meshNeedleMask = new Mesh("gui/game/hud/needlemask/mono.sim");
        fntLcd = com.maddox.il2.engine.TTFont.get("lcdnova");
        setScales();
    }

    private void setScales()
    {
        float f = viewDX;
        float f1 = f / 1024F;
        meshNeedle1.setScale(140F * f1);
        meshNeedle2.setScale(140F * f1);
        meshNeedle3.setScale(75F * f1);
        meshNeedle4.setScale(100F * f1);
        meshNeedle5.setScale(90F * f1);
        meshNeedle6.setScale(150F * f1);
        meshNeedleMask.setScale(150F * f1);
    }

    public void CockpitBF_110G_Gunner()
    {
        bNeedSetUp = true;
        bAiming = true;
        ObserverHook = null;
        hook1 = null;
        hook2 = null;
        super.cockpitNightMats = (new java.lang.String[] {
            "cadran1", "radio", "bague2"
        });
        setNightMats(false);
        interpPut(new CockpitPilot.Interpolater(this), null, com.maddox.rts.Time.current(), null);
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(super.mesh, "LAMPHOOK01");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(126F, 232F, 245F);
        light1.light.setEmit(0.0F, 0.0F);
        super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        ObserverHook = new HookNamed(super.mesh, "CAMERAAIM");
        com.maddox.il2.objects.air.AircraftLH.printCompassHeading = true;
        bBeaconKeysEnabled = ((com.maddox.il2.objects.air.AircraftLH)aircraft()).bWantBeaconKeys;
        ((com.maddox.il2.objects.air.AircraftLH)aircraft()).bWantBeaconKeys = true;
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    private int viewDX;
    private int viewDY;
    private com.maddox.il2.engine.Mat spriteLeft;
    private com.maddox.il2.engine.Mat spriteRight;
    private com.maddox.il2.engine.Mesh meshNeedle1;
    private com.maddox.il2.engine.Mesh meshNeedle2;
    private com.maddox.il2.engine.Mesh meshNeedle3;
    private com.maddox.il2.engine.Mesh meshNeedle4;
    private com.maddox.il2.engine.Mesh meshNeedle5;
    private com.maddox.il2.engine.Mesh meshNeedle6;
    private com.maddox.il2.engine.Mesh meshNeedleMask;
    private com.maddox.il2.engine.TTFont fntLcd;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.LightPointActor light1;
    private boolean bAiming;
    private com.maddox.il2.engine.Hook ObserverHook;
    private boolean bBeaconKeysEnabled;
    private float saveFov;
    private float aAim;
    private float tAim;
    private float aDiv;
    private float tDiv;
    private boolean bEntered;
    private boolean bBAiming;
    private int to;
    double FOV;
    double ScX;
    double ScY;
    double ScZ;
    float FOrigX;
    float FOrigY;
    int nTgts;
    float RRange;
    float RClose;
    private java.util.ArrayList radarPlane;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.CockpitMig_17Radar.class;
        com.maddox.rts.Property.set(class1, "astatePilotIndx", 0);
    }
}
