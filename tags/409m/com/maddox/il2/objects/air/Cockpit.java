// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cockpit.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.gl;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft

public abstract class Cockpit extends com.maddox.il2.engine.Actor
{
    public static class HookOnlyOrient extends com.maddox.il2.engine.Hook
    {

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            loc1.set(com.maddox.il2.objects.air.Cockpit.nullP, loc.getOrient());
        }

        public HookOnlyOrient()
        {
        }
    }

    class NullDraw extends com.maddox.il2.engine.ActorDraw
    {

        public void preRender()
        {
            if(!com.maddox.il2.engine.Actor.isValid(pos.base()))
                return;
            if(nullMesh == null)
                return;
            if(nullMeshIL2 == null)
                return;
            pos.getRender(__l);
            com.maddox.il2.game.Main3D.cur3D().cameraCockpit.pos.getRender(__p);
            __l.set(__p);
            if(bEnableRenderingBall)
                nullMesh.setPos(__l);
            else
                nullMeshIL2.setPos(__l);
            if(aircraft() != null)
                nullMesh.chunkSetAngles("Ball", aircraft().FM.getAOS(), -aircraft().FM.getAOA(), 0.0F);
            if(bEnableRenderingBall)
                iPreRender = nullMesh.preRender();
            else
                iPreRender = nullMeshIL2.preRender();
        }

        public void render()
        {
            if(iPreRender == 0)
                return;
            if(!com.maddox.il2.engine.Actor.isValid(pos.base()))
                return;
            if(nullMesh == null)
                return;
            if(nullMeshIL2 == null)
                return;
            if(bEnableRenderingBall)
                nullMesh.render();
            else
                nullMeshIL2.render();
        }

        private int iPreRender;

        NullDraw()
        {
            iPreRender = 0;
        }
    }

    class Draw extends com.maddox.il2.engine.ActorDraw
    {

        public void preRender()
        {
            if(!com.maddox.il2.engine.Actor.isValid(pos.base()))
                return;
            if(mesh == null)
            {
                return;
            } else
            {
                pos.getRender(__l);
                mesh.setPos(__l);
                reflectWorldToInstruments(com.maddox.rts.Time.tickOffset());
                iPreRender = mesh.preRender();
                return;
            }
        }

        public void render(boolean flag)
        {
            if(iPreRender == 0)
                return;
            if(!com.maddox.il2.engine.Actor.isValid(pos.base()))
                return;
            if(mesh == null)
                return;
            com.maddox.il2.engine.Render.currentCamera().activateWorldMode(0);
            com.maddox.opengl.gl.GetDoublev(2982, flag ? _modelMatrix3DMirror : _modelMatrix3D);
            if(flag)
                com.maddox.opengl.gl.GetDoublev(2983, _projMatrix3DMirror);
            com.maddox.il2.engine.Render.currentCamera().deactivateWorldMode();
            pos.getRender(__l);
            lightUpdate(__l, false);
            pos.base().pos.getRender(__p, __o);
            com.maddox.il2.engine.LightPoint.setOffset((float)__p.x, (float)__p.y, (float)__p.z);
            pos.base().pos.getRender(__l);
            pos.base().draw.lightUpdate(__l, false);
            com.maddox.il2.engine.Render.currentLightEnv().prepareForRender(__p, mesh.visibilityR());
            if(flag)
            {
                if(!bExistMirror)
                    java.lang.System.out.println("*** Internal error: mirr exist");
                if(!com.maddox.il2.game.Main3D.cur3D().isViewMirror())
                    java.lang.System.out.println("*** Internal error: mirr isview");
                java.lang.String s = nameOfActiveMirrorSurfaceChunk();
                java.lang.String s1 = nameOfActiveMirrorBaseChunk();
                if(s != null)
                {
                    mesh.setCurChunk(s);
                    mesh.chunkVisible(false);
                }
                if(s1 != null)
                {
                    mesh.setCurChunk(s1);
                    mesh.chunkVisible(true);
                }
                mesh.render();
                return;
            }
            if(!bExistMirror)
            {
                mesh.render();
                return;
            }
            boolean flag1 = com.maddox.il2.game.Main3D.cur3D().isViewMirror();
            java.lang.String s2 = nameOfActiveMirrorSurfaceChunk();
            java.lang.String s3 = nameOfActiveMirrorBaseChunk();
            if(flag1)
            {
                if(s2 != null)
                {
                    mesh.setCurChunk(s2);
                    mesh.chunkVisible(true);
                    mesh.renderChunkMirror(_modelMatrix3D, _modelMatrix3DMirror, _projMatrix3DMirror);
                    mesh.chunkVisible(false);
                }
                if(s3 != null)
                {
                    mesh.setCurChunk(s3);
                    mesh.chunkVisible(true);
                }
                mesh.render();
            } else
            {
                if(s2 != null)
                {
                    mesh.setCurChunk(s2);
                    mesh.chunkVisible(false);
                }
                if(s3 != null)
                {
                    mesh.setCurChunk(s3);
                    mesh.chunkVisible(false);
                }
                mesh.render();
            }
        }

        protected double _modelMatrix3D[];
        protected double _modelMatrix3DMirror[];
        protected double _projMatrix3DMirror[];
        private int iPreRender;

        Draw()
        {
            _modelMatrix3D = new double[16];
            _modelMatrix3DMirror = new double[16];
            _projMatrix3DMirror = new double[16];
            iPreRender = 0;
        }
    }

    static class HookCamera3DMirror extends com.maddox.il2.engine.HookRender
    {

        private float computeMirroredCamera(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1, com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1, int i, int j, float f, 
                com.maddox.il2.engine.Loc loc2, int ai[])
        {
            loc.getMatrix(cam2w);
            loc1.getMatrix(mir2w);
            cam2mir.set(mir2w);
            cam2mir.invert();
            cam2mir.mul(cam2mir, cam2w);
            cmm2mir.setIdentity();
            cmm2mir.m00 = 0.0D;
            cmm2mir.m10 = 0.0D;
            cmm2mir.m20 = 1.0D;
            cmm2mir.m01 = 1.0D;
            cmm2mir.m11 = 0.0D;
            cmm2mir.m21 = 0.0D;
            cmm2mir.m02 = 0.0D;
            cmm2mir.m12 = 1.0D;
            cmm2mir.m22 = 0.0D;
            cmm2mir.m03 = cam2mir.m03;
            cmm2mir.m13 = cam2mir.m13;
            cmm2mir.m23 = -cam2mir.m23;
            cmm2mir.m03 *= 0.45000000000000001D;
            cmm2mir.m13 *= 0.45000000000000001D;
            cmm2mir.m23 *= 0.45000000000000001D;
            cmm2w.mul(mir2w, cmm2mir);
            mir2cmm.set(cmm2mir);
            mir2cmm.invert();
            float f1 = (float)(-cmm2mir.m23);
            if(f1 <= 0.001F)
                return -1F;
            float f3;
            float f4;
            float f5;
            float f2 = f3 = f4 = f5 = 0.0F;
            for(int k = 0; k < 8; k++)
            {
                switch(k)
                {
                case 0: // '\0'
                    p.set(point3f.x, point3f.y, point3f.z);
                    break;

                case 1: // '\001'
                    p.set(point3f1.x, point3f.y, point3f.z);
                    break;

                case 2: // '\002'
                    p.set(point3f.x, point3f1.y, point3f.z);
                    break;

                case 3: // '\003'
                    p.set(point3f.x, point3f.y, point3f1.z);
                    break;

                case 4: // '\004'
                    p.set(point3f1.x, point3f1.y, point3f1.z);
                    break;

                case 5: // '\005'
                    p.set(point3f.x, point3f1.y, point3f1.z);
                    break;

                case 6: // '\006'
                    p.set(point3f1.x, point3f.y, point3f1.z);
                    break;

                case 7: // '\007'
                    p.set(point3f1.x, point3f1.y, point3f.z);
                    break;
                }
                mir2cmm.transform(p);
                float f6 = -(float)(((double)f1 * p.y) / p.x);
                float f8 = (float)(((double)f1 * p.z) / p.x);
                if(k == 0)
                {
                    f2 = f3 = f6;
                    f4 = f5 = f8;
                } else
                {
                    if(f6 < f2)
                        f2 = f6;
                    if(f6 > f3)
                        f3 = f6;
                    if(f8 < f4)
                        f4 = f8;
                    if(f8 > f5)
                        f5 = f8;
                }
            }

            float f7 = f3 - f2;
            float f9 = f5 - f4;
            if(f7 <= 0.001F || f9 <= 0.001F)
                return -1F;
            f2 -= 0.001F;
            f3 += 0.001F;
            f4 -= 0.001F;
            f5 += 0.001F;
            f7 = f3 - f2;
            f9 = f5 - f4;
            float f10 = f7 / (float)i;
            float f11 = f10 * f;
            float f12 = f11 * (float)j;
            if(f12 > f9)
            {
                f9 = f12;
                f4 = (f4 + f5) * 0.5F - f9 * 0.5F;
                f5 = f4 + f9;
            } else
            {
                f7 *= f9 / f12;
                f2 = (f2 + f3) * 0.5F - f7 * 0.5F;
                f3 = f2 + f7;
            }
            float f13 = 2.0F * java.lang.Math.max(java.lang.Math.abs(f2), java.lang.Math.abs(f3));
            float f14 = 2.0F * java.lang.Math.max(java.lang.Math.abs(f4), java.lang.Math.abs(f5));
            int l = (int)(0.5F + ((float)i * f13) / f7);
            int i1 = (int)(0.5F + ((float)j * f14) / f9);
            float f15 = 2.0F * com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.atan2(f13 * 0.5F, f1));
            int j1 = (int)(-((f2 - -f13 / 2.0F) / f13) * (float)l);
            int k1 = (int)(-((f4 - -f14 / 2.0F) / f14) * (float)i1);
            cmm2w.getEulers(Eul);
            Eul[0] *= -57.295777918682049D;
            Eul[1] *= -57.295777918682049D;
            Eul[2] *= 57.295777918682049D;
            loc2.set(cmm2w.m03, cmm2w.m13, cmm2w.m23, (float)Eul[0], (float)Eul[1], (float)Eul[2]);
            ai[0] = j1;
            ai[1] = k1;
            ai[2] = l;
            ai[3] = i1;
            resultNearClipDepth = f1;
            return f15;
        }

        public boolean computeRenderPos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            computePos(actor, loc, loc1, true);
            return true;
        }

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            computePos(actor, loc, loc1, false);
        }

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1, boolean flag)
        {
            if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
                return;
            if(!com.maddox.il2.game.Main3D.cur3D().cockpitCur.bExistMirror)
                return;
            if(!com.maddox.il2.game.Main3D.cur3D().isViewMirror())
                return;
            if(!com.maddox.il2.game.Main3D.cur3D().cockpitCur.isFocused())
                return;
            if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
                return;
            com.maddox.il2.engine.Loc loc2 = loc;
            com.maddox.il2.game.Main3D.cur3D().cockpitCur.mesh.setCurChunk(com.maddox.il2.game.Main3D.cur3D().cockpitCur.nameOfActiveMirrorSurfaceChunk());
            com.maddox.il2.game.Main3D.cur3D().cockpitCur.mesh.getChunkLocObj(mir2w_loc);
            if(flag)
            {
                if(bInCockpit)
                    com.maddox.il2.game.Main3D.cur3D().cockpitCur.pos.getRender(aLoc);
                else
                    com.maddox.il2.ai.World.getPlayerAircraft().pos.getRender(aLoc);
            } else
            if(bInCockpit)
                com.maddox.il2.game.Main3D.cur3D().cockpitCur.pos.getAbs(aLoc);
            else
                com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbs(aLoc);
            mir2w_loc.add(mir2w_loc, aLoc);
            com.maddox.il2.game.Main3D.cur3D().cockpitCur.mesh.getChunkCurVisBoundBox(mirLowP, mirHigP);
            float f = computeMirroredCamera(loc2, mir2w_loc, mirLowP, mirHigP, com.maddox.il2.game.Main3D.cur3D().mirrorWidth(), com.maddox.il2.game.Main3D.cur3D().mirrorHeight(), 1.0F, loc1, bInCockpit ? com.maddox.il2.game.Main3D.cur3D().cockpitCur.cockpit_renderwin : com.maddox.il2.game.Main3D.cur3D().cockpitCur.world_renderwin);
            if(!flag)
                return;
            if(bInCockpit)
            {
                com.maddox.il2.game.Main3D.cur3D().cameraCockpitMirror.set(f);
                com.maddox.il2.game.Main3D.cur3D().cameraCockpitMirror.ZNear = resultNearClipDepth;
            } else
            {
                com.maddox.il2.game.Main3D.cur3D().camera3DMirror.set(f);
                com.maddox.il2.game.Main3D.cur3D().camera3DMirror.ZNear = resultNearClipDepth;
            }
        }

        com.maddox.JGP.Matrix4d cam2w;
        com.maddox.JGP.Matrix4d mir2w;
        com.maddox.JGP.Matrix4d cam2mir;
        com.maddox.JGP.Point3d p;
        com.maddox.JGP.Vector3d X;
        com.maddox.JGP.Vector3d Y;
        com.maddox.JGP.Vector3d Z;
        com.maddox.JGP.Matrix4d cmm2w;
        com.maddox.JGP.Matrix4d cmm2mir;
        com.maddox.JGP.Matrix4d mir2cmm;
        double Eul[];
        com.maddox.JGP.Point3f mirLowP;
        com.maddox.JGP.Point3f mirHigP;
        float resultNearClipDepth;
        com.maddox.il2.engine.Loc mir2w_loc;
        com.maddox.il2.engine.Loc aLoc;
        boolean bInCockpit;

        public HookCamera3DMirror(boolean flag)
        {
            cam2w = new Matrix4d();
            mir2w = new Matrix4d();
            cam2mir = new Matrix4d();
            p = new Point3d();
            X = new Vector3d();
            Y = new Vector3d();
            Z = new Vector3d();
            cmm2w = new Matrix4d();
            cmm2mir = new Matrix4d();
            mir2cmm = new Matrix4d();
            Eul = new double[3];
            mirLowP = new Point3f();
            mirHigP = new Point3f();
            mir2w_loc = new Loc();
            aLoc = new Loc();
            bInCockpit = flag;
        }
    }

    public static class Camera3DMirror extends com.maddox.il2.engine.Camera3D
    {

        public boolean activate(float f, int i, int j, int k, int l, int i1, int j1, 
                int k1, int l1, int i2, int j2)
        {
            if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.game.Main3D.cur3D().cockpitCur))
                return super.activate(f, i, j, k, l, i1, j1, k1, l1, i2, j2);
            pos.getRender(com.maddox.il2.engine.Actor._tmpLoc);
            int ai[] = com.maddox.il2.game.Main3D.cur3D().cockpitCur.world_renderwin;
            if(this == com.maddox.il2.game.Main3D.cur3D().cameraCockpitMirror)
                ai = com.maddox.il2.game.Main3D.cur3D().cockpitCur.cockpit_renderwin;
            return super.activate(f, i, j, com.maddox.il2.game.Main3D.cur3D().mirrorX0() + ai[0], com.maddox.il2.game.Main3D.cur3D().mirrorY0() + ai[1], ai[2], ai[3], com.maddox.il2.game.Main3D.cur3D().mirrorX0(), com.maddox.il2.game.Main3D.cur3D().mirrorY0(), com.maddox.il2.game.Main3D.cur3D().mirrorWidth(), com.maddox.il2.game.Main3D.cur3D().mirrorHeight());
        }

        public Camera3DMirror()
        {
        }
    }


    protected void resetYPRmodifier()
    {
        ypr[0] = ypr[1] = ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F;
    }

    public int astatePilotIndx()
    {
        if(_astatePilotIndx == -1)
            _astatePilotIndx = com.maddox.rts.Property.intValue(getClass(), "astatePilotIndx", 0);
        return _astatePilotIndx;
    }

    public boolean isEnableHotKeysOnOutsideView()
    {
        return false;
    }

    public java.lang.String[] getHotKeyEnvs()
    {
        return null;
    }

    protected void initSounds()
    {
        if(sfxPreset == null)
            sfxPreset = new SoundPreset("aircraft.cockpit");
        sounds = new com.maddox.sound.SoundFX[18];
    }

    protected void sfxClick(int i)
    {
        sfxStart(i);
    }

    protected void sfxStart(int i)
    {
        if(sounds != null && sounds.length > i)
        {
            com.maddox.sound.SoundFX soundfx = sounds[i];
            if(soundfx == null)
            {
                soundfx = aircraft().newSound(sfxPreset, false, false);
                if(soundfx == null)
                    return;
                soundfx.setParent(aircraft().getRootFX());
                sounds[i] = soundfx;
                soundfx.setUsrFlag(i);
            }
            soundfx.play(sfxPos);
        }
    }

    protected void sfxStop(int i)
    {
        if(sounds != null && sounds.length > i && sounds[i] != null)
            sounds[i].stop();
    }

    protected void sfxSetAcoustics(com.maddox.sound.Acoustics acoustics)
    {
        for(int i = 0; i < sounds.length; i++)
            if(sounds[i] != null)
                sounds[i].setAcoustics(acoustics);

    }

    protected void loadBuzzerFX()
    {
        if(sounds != null)
        {
            com.maddox.sound.SoundFX soundfx = sounds[17];
            if(soundfx == null)
            {
                com.maddox.sound.SoundFX soundfx1 = aircraft().newSound(sfxPreset, false, false);
                if(soundfx1 != null)
                {
                    soundfx1.setParent(aircraft().getRootFX());
                    sounds[17] = soundfx1;
                    soundfx1.setUsrFlag(17);
                    soundfx1.setPosition(sfxPos);
                }
            }
        }
    }

    protected void buzzerFX(boolean flag)
    {
        com.maddox.sound.SoundFX soundfx = sounds[17];
        if(soundfx != null)
            soundfx.setPlay(flag);
    }

    public void onDoorMoved(float f)
    {
        if(acoustics != null && acoustics.globFX != null)
            acoustics.globFX.set(1.0F - f);
    }

    public void doToggleDim()
    {
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        sfxClick(9);
        for(int i = 0; i < acockpit.length; i++)
        {
            com.maddox.il2.objects.air.Cockpit cockpit = acockpit[i];
            if(com.maddox.il2.engine.Actor.isValid(cockpit))
                cockpit.toggleDim();
        }

    }

    public boolean isToggleDim()
    {
        return cockpitDimControl;
    }

    public void doToggleLight()
    {
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        sfxClick(1);
        for(int i = 0; i < acockpit.length; i++)
        {
            com.maddox.il2.objects.air.Cockpit cockpit = acockpit[i];
            if(com.maddox.il2.engine.Actor.isValid(cockpit))
                cockpit.toggleLight();
        }

    }

    public boolean isToggleLight()
    {
        return cockpitLightControl;
    }

    public void doReflectCockitState()
    {
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        for(int i = 0; i < acockpit.length; i++)
        {
            com.maddox.il2.objects.air.Cockpit cockpit = acockpit[i];
            if(com.maddox.il2.engine.Actor.isValid(cockpit))
                cockpit.reflectCockpitState();
        }

    }

    protected void setNightMats(boolean flag)
    {
        if(cockpitNightMats == null)
            return;
        for(int i = 0; i < cockpitNightMats.length; i++)
        {
            int j = mesh.materialFind(cockpitNightMats[i] + "_night");
            if(j < 0)
            {
                if(com.maddox.il2.ai.World.cur().isDebugFM())
                    java.lang.System.out.println(" * * * * * did not find " + cockpitNightMats[i] + "_night");
            } else
            {
                com.maddox.il2.engine.Mat mat = mesh.material(j);
                if(mat.isValidLayer(0))
                {
                    mat.setLayer(0);
                    mat.set((short)0, flag);
                } else
                if(com.maddox.il2.ai.World.cur().isDebugFM())
                    java.lang.System.out.println(" * * * * * " + cockpitNightMats[i] + "_night layer 0 invalid");
            }
        }

    }

    public void reflectWorldToInstruments(float f)
    {
    }

    public void toggleDim()
    {
    }

    public void toggleLight()
    {
    }

    public void reflectCockpitState()
    {
    }

    public boolean isNullShow()
    {
        return bNullShow;
    }

    public void setNullShow(boolean flag)
    {
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        for(int i = 0; i < acockpit.length; i++)
            acockpit[i]._setNullShow(flag);

        if(bFocus)
        {
            com.maddox.il2.objects.air.Aircraft aircraft1 = aircraft();
            if(com.maddox.il2.engine.Actor.isValid(aircraft1))
                aircraft1.drawing(!flag);
        }
    }

    protected void _setNullShow(boolean flag)
    {
        bNullShow = flag;
    }

    public boolean isEnableRendering()
    {
        return bEnableRendering;
    }

    public void setEnableRendering(boolean flag)
    {
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        for(int i = 0; i < acockpit.length; i++)
            acockpit[i]._setEnableRendering(flag);

    }

    protected void _setEnableRendering(boolean flag)
    {
        bEnableRendering = flag;
    }

    public boolean isEnableRenderingBall()
    {
        return bEnableRenderingBall;
    }

    public void setEnableRenderingBall(boolean flag)
    {
        com.maddox.il2.objects.air.Cockpit acockpit[] = com.maddox.il2.game.Main3D.cur3D().cockpits;
        for(int i = 0; i < acockpit.length; i++)
            acockpit[i]._setEnableRenderingBall(flag);

    }

    protected void _setEnableRenderingBall(boolean flag)
    {
        bEnableRenderingBall = flag;
    }

    public boolean isFocused()
    {
        return bFocus;
    }

    public boolean isEnableFocusing()
    {
        if(!com.maddox.il2.engine.Actor.isValid(aircraft()))
            return false;
        return !aircraft().FM.AS.isPilotParatrooper(astatePilotIndx());
    }

    public boolean focusEnter()
    {
        if(!isEnableFocusing())
            return false;
        if(bFocus)
            return true;
        if(!doFocusEnter())
        {
            return false;
        } else
        {
            bFocus = true;
            com.maddox.il2.game.Main3D.cur3D().enableCockpitHotKeys();
            return true;
        }
    }

    public void focusLeave()
    {
        if(!bFocus)
            return;
        doFocusLeave();
        bFocus = false;
        if(!isEnableHotKeysOnOutsideView())
            com.maddox.il2.game.Main3D.cur3D().disableCockpitHotKeys();
    }

    protected boolean doFocusEnter()
    {
        return true;
    }

    protected void doFocusLeave()
    {
    }

    public boolean existPadlock()
    {
        return false;
    }

    public boolean isPadlock()
    {
        return bFocus ? false : false;
    }

    public com.maddox.il2.engine.Actor getPadlockEnemy()
    {
        return null;
    }

    public boolean startPadlock(com.maddox.il2.engine.Actor actor)
    {
        return bFocus ? false : false;
    }

    public void stopPadlock()
    {
    }

    public void endPadlock()
    {
    }

    public void setPadlockForward(boolean flag)
    {
    }

    public boolean isToggleAim()
    {
        return bFocus ? false : false;
    }

    public void doToggleAim(boolean flag)
    {
    }

    public boolean isToggleUp()
    {
        return bFocus ? false : false;
    }

    public void doToggleUp(boolean flag)
    {
    }

    public java.lang.String nameOfActiveMirrorSurfaceChunk()
    {
        return "Mirror";
    }

    public java.lang.String nameOfActiveMirrorBaseChunk()
    {
        return "BaseMirror";
    }

    public static com.maddox.il2.engine.Hook getHookCamera3DMirror(boolean flag)
    {
        return new HookCamera3DMirror(flag);
    }

    public void grabMirrorFromScreen(int i, int j, int k, int l)
    {
        mirrorMat.grabFromScreen(i, j, k, l);
    }

    public void preRender(boolean flag)
    {
        if(!flag && bNullShow)
        {
            com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.World.getPlayerAircraft();
            if(com.maddox.il2.engine.Actor.isValid(aircraft1))
            {
                ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getRender(__l);
                ((com.maddox.il2.engine.Actor) (aircraft1)).draw.soundUpdate(aircraft1, __l);
                ((com.maddox.il2.objects.air.Aircraft)aircraft1).updateLLights();
            }
        }
        if(!bEnableRendering)
            return;
        if(bNullShow)
            drawNullMesh.preRender();
        else
            drawMesh.preRender();
    }

    public void render(boolean flag)
    {
        if(!bEnableRendering)
            return;
        if(bNullShow)
            drawNullMesh.render();
        else
            drawMesh.render(flag);
    }

    public com.maddox.il2.objects.air.Aircraft aircraft()
    {
        if(fm == null)
            return null;
        else
            return (com.maddox.il2.objects.air.Aircraft)fm.actor;
    }

    public boolean isExistMirror()
    {
        return bExistMirror;
    }

    public void destroy()
    {
        if(isFocused())
            com.maddox.il2.game.Main3D.cur3D().setView(fm.actor, true);
        fm = null;
        super.destroy();
    }

    public Cockpit(java.lang.String s, java.lang.String s1)
    {
        fm = null;
        cockpitDimControl = false;
        cockpitLightControl = false;
        cockpitNightMats = null;
        _astatePilotIndx = -1;
        sounds = null;
        sfxPos = new Point3d(1.0D, 0.0D, 0.0D);
        bNullShow = false;
        bEnableRendering = true;
        bEnableRenderingBall = true;
        bFocus = false;
        world_renderwin = new int[4];
        cockpit_renderwin = new int[4];
        drawMesh = new Draw();
        drawNullMesh = new NullDraw();
        bExistMirror = false;
        __l = new Loc();
        __p = new Point3d();
        __o = new Orient();
        fm = _newAircraft.FM;
        pos = new ActorPosMove(this);
        if(s != null)
        {
            mesh = new HierMesh(s);
            int i = mesh.materialFind("MIRROR");
            if(i != -1)
            {
                bExistMirror = true;
                mirrorMat = mesh.material(i);
            }
        }
        nullMesh = new HierMesh("3DO/Cockpit/Nill/hier.him");
        nullMeshIL2 = new Mesh("3DO/Cockpit/null/mono.sim");
        try
        {
            acoustics = new Acoustics(s1);
            acoustics.setParent(com.maddox.il2.engine.Engine.worldAcoustics());
            initSounds();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Cockpit Acoustics NOT initialized: " + exception.getMessage());
        }
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    protected float cvt(float f, float f1, float f2, float f3, float f4)
    {
        f = java.lang.Math.min(java.lang.Math.max(f, f1), f2);
        return f3 + ((f4 - f3) * (f - f1)) / (f2 - f1);
    }

    protected float interp(float f, float f1, float f2)
    {
        return f1 + (f - f1) * f2;
    }

    protected float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    protected float waypointAzimuth()
    {
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint == null)
        {
            return 0.0F;
        } else
        {
            waypoint.getP(P1);
            V.sub(P1, fm.Loc);
            return (float)(57.295779513082323D * java.lang.Math.atan2(V.x, V.y));
        }
    }

    public com.maddox.il2.fm.FlightModel fm;
    public boolean cockpitDimControl;
    public boolean cockpitLightControl;
    protected java.lang.String cockpitNightMats[];
    protected static float ypr[] = {
        0.0F, 0.0F, 0.0F
    };
    protected static float xyz[] = {
        0.0F, 0.0F, 0.0F
    };
    protected int _astatePilotIndx;
    public static final int SNDCLK_NULL = 0;
    public static final int SNDCLK_BUTTONDEPRESSED1 = 1;
    public static final int SNDCLK_BUTTONDEPRESSED2 = 2;
    public static final int SNDCLK_BUTTONDEPRESSED3 = 3;
    public static final int SNDCLK_TUMBLERFLIPPED1 = 4;
    public static final int SNDCLK_TUMBLERFLIPPED2 = 5;
    public static final int SNDCLK_TUMBLERFLIPPED3 = 6;
    public static final int SNDINF_RUSTYWHEELTURNING1 = 7;
    public static final int SNDINF_OILEDMETALWHEELWITHCHAIN1 = 8;
    public static final int SNDCLK_SMALLLEVERSLIDES1 = 9;
    public static final int SNDCLK_SMALLLEVERSLIDES2 = 10;
    public static final int SNDINF_LEVERSLIDES1 = 11;
    public static final int SNDINF_LEVERSLIDES2 = 12;
    public static final int SNDCLK_RUSTYLEVERSLIDES1 = 13;
    public static final int SNDCLK_SMALLVALVE1 = 14;
    public static final int SNDINF_SMALLVALVEWITHGASLEAK1 = 15;
    public static final int SNDINF_WORMGEARTURNS1 = 16;
    public static final int SNDINF_BUZZER_109 = 17;
    public static final int SND_COUNT = 18;
    protected static com.maddox.sound.SoundPreset sfxPreset = null;
    protected com.maddox.sound.SoundFX sounds[];
    protected com.maddox.JGP.Point3d sfxPos;
    private boolean bNullShow;
    private boolean bEnableRendering;
    private boolean bEnableRenderingBall;
    private boolean bFocus;
    private int world_renderwin[];
    private int cockpit_renderwin[];
    public com.maddox.il2.engine.HierMesh mesh;
    private com.maddox.il2.engine.HierMesh nullMesh;
    private com.maddox.il2.engine.Mesh nullMeshIL2;
    private com.maddox.il2.objects.air.Draw drawMesh;
    private com.maddox.il2.objects.air.NullDraw drawNullMesh;
    private boolean bExistMirror;
    private com.maddox.il2.engine.Mat mirrorMat;
    private com.maddox.il2.engine.Loc __l;
    private com.maddox.JGP.Point3d __p;
    private com.maddox.il2.engine.Orient __o;
    public static com.maddox.il2.objects.air.Aircraft _newAircraft = null;
    private static com.maddox.JGP.Point3d nullP = new Point3d();
    protected static com.maddox.JGP.Point3d P1 = new Point3d();
    protected static com.maddox.JGP.Vector3d V = new Vector3d();











}
