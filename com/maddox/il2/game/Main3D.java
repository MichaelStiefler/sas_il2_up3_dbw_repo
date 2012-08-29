// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Main3D.java

package com.maddox.il2.game;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.builder.Builder;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorLandMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSoundListener;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.ConsoleGL0;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TTFontTransform;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.engine.hotkey.FreeFly;
import com.maddox.il2.engine.hotkey.FreeFlyXYZ;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.engine.hotkey.HookViewEnemy;
import com.maddox.il2.engine.hotkey.HookViewFly;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIBWDemoPlay;
import com.maddox.il2.gui.GUIBuilder;
import com.maddox.il2.gui.GUIRecordPlay;
import com.maddox.il2.gui.GUITrainingPlay;
import com.maddox.il2.net.Connect;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.effects.Cinema;
import com.maddox.il2.objects.effects.DarkerNight;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.il2.objects.effects.SunFlare;
import com.maddox.il2.objects.effects.SunGlare;
import com.maddox.il2.objects.effects.Zip;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.TestRunway;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.lights.SearchlightGeneric;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.il2.objects.weapons.Rocket;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.gl;
import com.maddox.opengl.util.ScrShot;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.Finger;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.InOutStreams;
import com.maddox.rts.IniFile;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.KeyRecordCallback;
import com.maddox.rts.Keyboard;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.rts.cmd.CmdSFS;
import com.maddox.sound.AudioDevice;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.maddox.il2.game:
//            Main, HUD, TimeSkip, AircraftHotKeys, 
//            Selector, I18N, HookKeys, DeviceLink, 
//            Mission, GameState, GameTrack, DotRange

public class Main3D extends com.maddox.il2.game.Main
{
    class CmdExit extends com.maddox.rts.Cmd
    {

        public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
        {
            com.maddox.il2.game.Main.doGameExit();
            return null;
        }

        CmdExit()
        {
        }
    }

    class CmdScreenSequence extends com.maddox.rts.Cmd
    {

        public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
        {
            if(screenSequence == null)
                screenSequence = new ScreenSequence();
            screenSequence.doSave();
            return null;
        }

        CmdScreenSequence()
        {
        }
    }

    class ScreenSequence extends com.maddox.il2.engine.Actor
    {
        class Interpolater extends com.maddox.il2.engine.Interpolate
        {

            public boolean tick()
            {
                if(bSave)
                    shot.grab();
                return true;
            }

            Interpolater()
            {
            }
        }


        public void doSave()
        {
            bSave = !bSave;
        }

        public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
        {
            return this;
        }

        protected void createActorHashCode()
        {
            makeActorRealHashCode();
        }

        boolean bSave;
        com.maddox.opengl.util.ScrShot shot;

        public ScreenSequence()
        {
            bSave = false;
            shot = new ScrShot("s");
            flags |= 0x4000;
            interpPut(new Interpolater(), "grabber", com.maddox.rts.Time.current(), null);
        }
    }

    private static class TransformMirror extends com.maddox.il2.engine.TTFontTransform
    {

        public void get(float f, float f1, float af[])
        {
            af[0] = (x0 + dx) - f;
            af[1] = y0 + f1;
            af[2] = z0;
        }

        public void set(float f, float f1, float f2, float f3)
        {
            x0 = f;
            y0 = f1;
            z0 = f2;
            dx = f3;
        }

        private float x0;
        private float y0;
        private float dx;
        private float z0;

        private TransformMirror()
        {
        }

    }

    class FarActorFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        private java.lang.String lenToString(int i)
        {
            java.lang.String s;
            if(i >= 1000)
                s = i / 1000 + ".";
            else
                s = ".";
            i %= 1000;
            if(i < 10)
                return s + "00";
            if(i < 100)
                return s + "0" + i / 10;
            else
                return s + i / 10;
        }

        private void drawPointer(int i, double d, int j, int k)
        {
            double d1 = java.lang.Math.atan2(k, j);
            double d2 = java.lang.Math.atan2(p2d.y - (double)k, p2d.x - (double)j);
            if(p2d.z > 1.0D)
                if(d2 > 0.0D)
                    d2 = -3.1415926535897931D + d2;
                else
                    d2 = 3.1415926535897931D + d2;
            double d3;
            double d4;
            if(d2 >= 0.0D)
            {
                if(d2 <= d1)
                {
                    d3 = j;
                    d4 = java.lang.Math.tan(d2) * (double)j;
                } else
                if(d2 <= 3.1415926535897931D - d1)
                {
                    d4 = k;
                    d3 = java.lang.Math.tan(1.5707963267948966D - d2) * (double)k;
                } else
                {
                    d3 = -j;
                    d4 = -java.lang.Math.tan(d2) * (double)j;
                }
            } else
            if(d2 >= -d1)
            {
                d3 = j;
                d4 = java.lang.Math.tan(d2) * (double)j;
            } else
            if(d2 >= -3.1415926535897931D + d1)
            {
                d4 = -k;
                d3 = -java.lang.Math.tan(1.5707963267948966D - d2) * (double)k;
            } else
            {
                d3 = -j;
                d4 = -java.lang.Math.tan(d2) * (double)j;
            }
            d3 += j;
            d4 += k;
            com.maddox.il2.game.HUD.addPointer((float)d3, (float)d4, com.maddox.il2.ai.Army.color(i), (float)((1.0D - d / iconDrawMax) * 0.80000000000000004D + 0.20000000000000001D), (float)d2);
        }

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(actor == iconFarViewActor)
                return false;
            if(d <= 5D)
                return false;
            int i = actor.getArmy();
            if(i == 0 && !isBomb(actor))
                return false;
            com.maddox.il2.game.DotRange dotrange = i != com.maddox.il2.ai.World.getPlayerArmy() ? dotRangeFoe : dotRangeFriendly;
            double d1 = 1.0D;
            double d2 = 0.078D + (double)(1.2F / com.maddox.il2.game.Main3D.FOVX);
            if(com.maddox.il2.game.Main3D.FOVX < 24F)
                d2 = 0.16D;
            if((actor instanceof com.maddox.il2.engine.ActorMesh) && ((com.maddox.il2.engine.ActorMesh)actor).mesh() != null)
            {
                float f = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR();
                if(f > 0.0F)
                    if(f > 100F)
                    {
                        float f1 = ((com.maddox.il2.engine.ActorMesh)actor).collisionR();
                        if(f1 > 0.0F)
                            d1 = (double)f1 * d2;
                    } else
                    {
                        d1 = (double)f * d2;
                    }
            }
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                if(d1 < 0.65000000000000002D)
                    d1 = 0.65000000000000002D;
                if(d1 > 2.2000000000000002D)
                    d1 = 2.2000000000000002D;
            } else
            {
                d1 *= 1.2D;
            }
            iconDrawMax = dotrange.dot(d1);
            if(d > iconDrawMax)
                return false;
            actor.pos.getRender(p3d);
            if(!project2d_cam(p3d, p2d))
                return false;
            if(p2d.z > 1.0D || p2d.x < iconClipX0 || p2d.x > iconClipX1 || p2d.y < iconClipY0 || p2d.y > iconClipY1)
            {
                if(bRenderMirror)
                    return false;
                if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
                    return false;
                if(isViewInsideShow())
                    return false;
                if(com.maddox.il2.ai.World.cur().diffCur.No_Icons)
                    return false;
                if(iRenderIndx == 0)
                    drawPointer(i, d, render2D.getViewPortWidth() / 2, render2D.getViewPortHeight() / 2);
                return false;
            }
            int j = (int)(p2d.x - 1.0D);
            int k = (int)(p2d.y - 0.5D);
            int l = 0x7f7f7f;
            int i1 = 255;
            int j1 = 0;
            if(bEnableFog)
            {
                com.maddox.il2.engine.Render.enableFog(true);
                l = com.maddox.il2.engine.Landscape.getFogRGBA((float)p3d.x, (float)p3d.y, (float)p3d.z);
                j1 = l >>> 24;
                i1 -= j1;
                com.maddox.il2.engine.Render.enableFog(false);
            }
            int k1 = ((int)(dotrange.alphaDot(d * 2.2000000000000002D, d1) * 255D) & 0xff) << 24;
            int l1 = ((int)(dotrange.alphaDot(d, d1) * 255D) & 0xff) << 24;
            int i2 = k1 | (com.maddox.il2.game.Main3D.iconFarColor & 0xff) * i1 + (l & 0xff) * j1 >>> 8 | ((com.maddox.il2.game.Main3D.iconFarColor >>> 8 & 0xff) * i1 + (l >>> 8 & 0xff) * j1 >>> 8) << 8 | ((com.maddox.il2.game.Main3D.iconFarColor >>> 16 & 0xff) * i1 + (l >>> 16 & 0xff) * j1 >>> 8) << 16;
            int j2 = l1 | (com.maddox.il2.game.Main3D.iconFarColor >>> 1 & 0x3f) * i1 + (l >>> 0 & 0xff) * j1 >>> 8 | ((com.maddox.il2.game.Main3D.iconFarColor >>> 9 & 0x3f) * i1 + (l >>> 8 & 0xff) * j1 >>> 8) << 8 | ((com.maddox.il2.game.Main3D.iconFarColor >>> 17 & 0x3f) * i1 + (l >>> 16 & 0xff) * j1 >>> 8) << 16;
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                if(d > iconAirDrawMin)
                {
                    com.maddox.il2.engine.Render.drawTile(j, (float)k + 1.0F, 2.0F, 1.0F, (float)(-p2d.z), iconFarMat, i2, 0.0F, 1.0F, 1.0F, -1F);
                    com.maddox.il2.engine.Render.drawTile(j, k, 2.0F, 1.0F, (float)(-p2d.z), iconFarMat, j2, 0.0F, 1.0F, 1.0F, -1F);
                }
            } else
            if(isBomb(actor))
            {
                if(d > iconSmallDrawMin)
                {
                    com.maddox.il2.engine.Render.drawTile(j, (float)k + 1.0F, 1.0F, 1.0F, (float)(-p2d.z), iconFarMat, i2, 0.0F, 1.0F, 1.0F, -1F);
                    com.maddox.il2.engine.Render.drawTile(j, k, 1.0F, 1.0F, (float)(-p2d.z), iconFarMat, j2, 0.0F, 1.0F, 1.0F, -1F);
                }
            } else
            if(d > iconGroundDrawMin)
            {
                com.maddox.il2.engine.Render.drawTile(j, (float)k + 1.0F, 2.0F, 1.0F, (float)(-p2d.z), iconFarMat, i2, 0.0F, 1.0F, 1.0F, -1F);
                com.maddox.il2.engine.Render.drawTile(j, k, 2.0F, 1.0F, (float)(-p2d.z), iconFarMat, j2, 0.0F, 1.0F, 1.0F, -1F);
            }
            k += 8;
            if(actor == iconFarPadlockActor && iconTypes() != 0)
            {
                iconFarPadlockItem.set(dotrange.colorIcon(d, i, i1), j, k, 0, (float)(-p2d.z), "");
                iconFarPadlockItem.bGround = !(actor instanceof com.maddox.il2.objects.air.Aircraft);
                if(com.maddox.il2.ai.World.cur().diffCur.No_Icons)
                {
                    int k2 = ((int)(dotrange.alphaIcon(d) * (double)i1) & 0xff) << 24;
                    iconFarPadlockItem.color = k2 | 0xff00;
                }
            }
            if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
                return false;
            if(com.maddox.il2.ai.World.cur().diffCur.No_Icons)
                return false;
            if(iconTypes() == 0)
                return false;
            if(d >= dotrange.icon())
                return false;
            java.lang.String s = null;
            java.lang.String s1 = null;
            java.lang.String s2 = null;
            switch(iconTypes())
            {
            case 3: // '\003'
            default:
                if(d <= dotrange.type())
                {
                    s = com.maddox.rts.Property.stringValue(actor.getClass(), iconFarFinger, null);
                    if(s == null)
                    {
                        s = actor.getClass().getName();
                        int l2 = s.lastIndexOf('.');
                        s = s.substring(l2 + 1);
                        com.maddox.rts.Property.set(actor.getClass(), "iconFar_shortClassName", s);
                    }
                }
                // fall through

            case 2: // '\002'
                if(d <= dotrange.id())
                    s1 = ((com.maddox.il2.objects.air.Aircraft)actor).typedName();
                if(com.maddox.il2.game.Mission.isNet() && ((com.maddox.il2.objects.air.NetAircraft)actor).isNetPlayer() && i == com.maddox.il2.ai.World.getPlayerArmy() && d <= dotrange.name())
                {
                    com.maddox.il2.net.NetUser netuser = ((com.maddox.il2.objects.air.NetAircraft)actor).netUser();
                    if(s1 != null)
                        s1 = s1 + " " + netuser.uniqueName();
                    else
                        s1 = netuser.uniqueName();
                }
                break;

            case 1: // '\001'
                break;
            }
            if(d <= dotrange.range())
                s2 = lenToString((int)d);
            java.lang.String s3 = null;
            if(s2 != null)
                s3 = s2;
            if(s != null)
                if(s3 != null)
                    s3 = s3 + " " + s;
                else
                    s3 = s;
            if(s1 != null)
                if(s3 != null)
                    s3 = s3 + ":" + s1;
                else
                    s3 = s1;
            if(s3 != null)
                insertFarActorItem(dotrange.colorIcon(d, i, i1), j, k, (float)(-p2d.z), s3);
            return false;
        }

        com.maddox.JGP.Point3d p3d;
        com.maddox.JGP.Point3d p2d;
        com.maddox.JGP.Point3d camp;

        FarActorFilter()
        {
            p3d = new Point3d();
            p2d = new Point3d();
            camp = new Point3d();
        }
    }

    static class FarActorItem
    {

        public void set(int i, int j, int k, int l, float f, java.lang.String s)
        {
            color = i;
            x = j;
            y = k;
            dx = l;
            z = f;
            str = s;
        }

        public int color;
        public int x;
        public int y;
        public int dx;
        public float z;
        public java.lang.String str;
        public boolean bGround;

        public FarActorItem(int i, int j, int k, int l, float f, java.lang.String s)
        {
            set(i, j, k, l, f, s);
        }
    }

    public class RenderMap2D extends com.maddox.il2.engine.Render
    {

        protected void contextResize(int i, int j)
        {
            super.contextResize(i, j);
            renderMap2DcontextResize(i, j);
            if(land2DText != null)
                land2DText.contextResized();
        }

        public void preRender()
        {
            preRenderMap2D();
            if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
            {
                com.maddox.il2.gui.GUIBuilder guibuilder = (com.maddox.il2.gui.GUIBuilder)com.maddox.il2.game.Main.state();
                guibuilder.builder.preRenderMap2D();
            }
        }

        public void render()
        {
            if(land2D != null)
                land2D.render();
            if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
            {
                com.maddox.il2.gui.GUIBuilder guibuilder = (com.maddox.il2.gui.GUIBuilder)com.maddox.il2.game.Main.state();
                guibuilder.builder.renderMap2D();
            } else
            if(land2DText != null)
                land2DText.render();
            renderMap2D();
        }

        public RenderMap2D(float f)
        {
            this(com.maddox.il2.engine.Engine.rendersMain(), f);
        }

        public RenderMap2D(com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            useClearDepth(false);
            setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
        }
    }

    public class RenderHUD extends com.maddox.il2.engine.Render
    {

        protected void contextResize(int i, int j)
        {
            super.contextResize(i, j);
            renderHUDcontextResize(i, j);
            hud.contextResize(i, j);
            renders().setCommonClearColor(viewPort[0] != 0.0F || viewPort[1] != 0.0F);
        }

        public void preRender()
        {
            hud.preRender();
            preRenderHUD();
        }

        public void render()
        {
            hud.render();
            renderHUD();
        }

        public RenderHUD(float f)
        {
            this(com.maddox.il2.engine.Engine.rendersMain(), f);
        }

        public RenderHUD(com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            useClearDepth(false);
            useClearColor(false);
        }
    }

    public class RenderCockpitMirror extends com.maddox.il2.engine.Render
    {

        protected void contextResize(int i, int j)
        {
            setViewPort(new int[] {
                mirrorX0(), mirrorY0(), mirrorWidth(), mirrorHeight()
            });
            if(camera != null)
                ((com.maddox.il2.engine.Camera3D)camera).set(((com.maddox.il2.engine.Camera3D)camera).FOV(), (float)mirrorWidth() / (float)mirrorHeight());
        }

        public boolean isShow()
        {
            if(viewMirror > 0 && renderCockpit.isShow())
                return cockpitCur.isExistMirror();
            else
                return false;
        }

        public void preRender()
        {
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isFocused())
                cockpitCur.preRender(true);
        }

        public void render()
        {
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isFocused())
            {
                cockpitCur.render(true);
                com.maddox.il2.engine.Render.flush();
                cockpitCur.grabMirrorFromScreen(mirrorX0(), mirrorY0(), mirrorWidth(), mirrorHeight());
            }
        }

        public RenderCockpitMirror(float f)
        {
            super(com.maddox.il2.engine.Engine.rendersMain(), f);
            useClearDepth(true);
            useClearColor(false);
            contextResized();
        }
    }

    public class RenderCockpit extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            iRenderIndx = _indx;
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isFocused())
                cockpitCur.preRender(false);
            iRenderIndx = 0;
        }

        public void render()
        {
            iRenderIndx = _indx;
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isFocused())
                cockpitCur.render(false);
            iRenderIndx = 0;
        }

        public void getAspectViewPort(float af[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(af);
                return;
            } else
            {
                _getAspectViewPort(_indx, af);
                return;
            }
        }

        public void getAspectViewPort(int ai[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(ai);
                return;
            } else
            {
                _getAspectViewPort(_indx, ai);
                return;
            }
        }

        public boolean isShow()
        {
            if(_indx == 0)
                return super.isShow();
            else
                return com.maddox.il2.engine.Config.cur.isUse3Renders() && renderCockpit.isShow();
        }

        int _indx;

        public RenderCockpit(int i, float f)
        {
            this(i, com.maddox.il2.engine.Engine.rendersMain(), f);
        }

        public RenderCockpit(int i, com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            _indx = 0;
            _indx = i;
            useClearDepth(true);
            useClearColor(false);
            contextResized();
        }
    }

    public class Render2DMirror extends com.maddox.il2.engine.Render
    {

        protected void contextResize(int i, int j)
        {
            setViewPort(new int[] {
                mirrorX0(), mirrorY0(), mirrorWidth(), mirrorHeight()
            });
            if(camera != null)
                ((com.maddox.il2.engine.CameraOrtho2D)camera).set(0.0F, mirrorWidth(), 0.0F, mirrorHeight());
        }

        public boolean isShow()
        {
            return viewMirror > 0 && render3D0.isShow() && !isViewOutside() && cockpitCur.isExistMirror();
        }

        public void render()
        {
            bRenderMirror = true;
            if(bEnableFog)
                com.maddox.il2.engine.Render.enableFog(false);
            drawFarActors();
            if(bEnableFog)
                com.maddox.il2.engine.Render.enableFog(true);
            bRenderMirror = false;
        }

        public Render2DMirror(float f)
        {
            super(com.maddox.il2.engine.Engine.rendersMain(), f);
            useClearDepth(false);
            useClearColor(false);
            contextResized();
        }
    }

    public class Render3D1Mirror extends com.maddox.il2.game.Render3DMirror
    {

        public void render()
        {
            bRenderMirror = true;
            doRender3D1(this);
            bRenderMirror = false;
        }

        public Render3D1Mirror(float f)
        {
            super(f);
            useClearColor(false);
            useClearDepth(false);
            useClearStencil(false);
        }
    }

    public class Render3D0Mirror extends com.maddox.il2.game.Render3DMirror
    {

        public void preRender()
        {
            bRenderMirror = true;
            doPreRender3D(this);
            bRenderMirror = false;
        }

        public void render()
        {
            camera.activateWorldMode(0);
            com.maddox.opengl.gl.GetDoublev(2982, _modelMatrix3DMirror);
            com.maddox.opengl.gl.GetDoublev(2983, _projMatrix3DMirror);
            com.maddox.opengl.gl.GetIntegerv(2978, _viewportMirror);
            camera.deactivateWorldMode();
            bRenderMirror = true;
            doRender3D0(this);
            bRenderMirror = false;
        }

        public Render3D0Mirror(float f)
        {
            super(f);
            setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
            useClearStencil(true);
        }
    }

    public class Render3DMirror extends com.maddox.il2.engine.Render
    {

        protected void contextResize(int i, int j)
        {
            setViewPort(new int[] {
                mirrorX0(), mirrorY0(), mirrorWidth(), mirrorHeight()
            });
            if(camera != null)
                ((com.maddox.il2.engine.Camera3D)camera).set(((com.maddox.il2.engine.Camera3D)camera).FOV(), (float)mirrorWidth() / (float)mirrorHeight());
        }

        public boolean isShow()
        {
            return viewMirror > 0 && render3D0.isShow() && !isViewOutside() && cockpitCur.isExistMirror();
        }

        public Render3DMirror(float f)
        {
            super(com.maddox.il2.engine.Engine.rendersMain(), f);
            contextResized();
        }
    }

    public class Render2D extends com.maddox.il2.engine.Render
    {

        public void render()
        {
            iRenderIndx = _indx;
            if(bEnableFog)
                com.maddox.il2.engine.Render.enableFog(false);
            drawFarActors();
            if(bEnableFog)
                com.maddox.il2.engine.Render.enableFog(true);
            iRenderIndx = 0;
        }

        public void getAspectViewPort(float af[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(af);
                return;
            } else
            {
                _getAspectViewPort(_indx, af);
                return;
            }
        }

        public void getAspectViewPort(int ai[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(ai);
                return;
            } else
            {
                _getAspectViewPort(_indx, ai);
                return;
            }
        }

        public boolean isShow()
        {
            if(_indx == 0)
                return super.isShow();
            if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
                return false;
            else
                return com.maddox.il2.engine.Config.cur.isUse3Renders() && render2D.isShow();
        }

        int _indx;

        public Render2D(int i, float f)
        {
            this(i, com.maddox.il2.engine.Engine.rendersMain(), f);
        }

        public Render2D(int i, com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            _indx = 0;
            _indx = i;
            useClearDepth(false);
            useClearColor(false);
            contextResized();
        }
    }

    public class Render3D1 extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            if(_indx == 0)
                drawTime();
        }

        public void render()
        {
            iRenderIndx = _indx;
            doRender3D1(this);
            if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18 && iRenderIndx == 0)
            {
                com.maddox.il2.gui.GUIBuilder guibuilder = (com.maddox.il2.gui.GUIBuilder)com.maddox.il2.game.Main.state();
                guibuilder.builder.render3D();
            }
            iRenderIndx = 0;
        }

        public void getAspectViewPort(float af[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(af);
                return;
            } else
            {
                _getAspectViewPort(_indx, af);
                return;
            }
        }

        public void getAspectViewPort(int ai[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(ai);
                return;
            } else
            {
                _getAspectViewPort(_indx, ai);
                return;
            }
        }

        public boolean isShow()
        {
            if(_indx == 0)
                return super.isShow();
            if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
                return false;
            else
                return com.maddox.il2.engine.Config.cur.isUse3Renders() && render3D1.isShow();
        }

        int _indx;

        public Render3D1(int i, float f)
        {
            this(i, com.maddox.il2.engine.Engine.rendersMain(), f);
        }

        public Render3D1(int i, com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            _indx = 0;
            _indx = i;
            useClearColor(false);
            useClearDepth(false);
            useClearStencil(false);
            contextResized();
        }
    }

    public class Render3D0 extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            iRenderIndx = _indx;
            if(_indx == 0)
                shadowPairsClear();
            doPreRender3D(this);
            iRenderIndx = 0;
        }

        public void render()
        {
            iRenderIndx = _indx;
            camera.activateWorldMode(0);
            com.maddox.opengl.gl.GetDoublev(2982, _modelMatrix3D[iRenderIndx]);
            com.maddox.opengl.gl.GetDoublev(2983, _projMatrix3D[iRenderIndx]);
            com.maddox.opengl.gl.GetIntegerv(2978, _viewport[iRenderIndx]);
            camera.deactivateWorldMode();
            doRender3D0(this);
            iRenderIndx = 0;
        }

        public void getAspectViewPort(float af[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(af);
                return;
            } else
            {
                _getAspectViewPort(_indx, af);
                return;
            }
        }

        public void getAspectViewPort(int ai[])
        {
            if(_indx == 0)
            {
                super.getAspectViewPort(ai);
                return;
            } else
            {
                _getAspectViewPort(_indx, ai);
                return;
            }
        }

        public boolean isShow()
        {
            if(_indx == 0)
                return super.isShow();
            if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
                return false;
            else
                return com.maddox.il2.engine.Config.cur.isUse3Renders() && render3D0.isShow();
        }

        int _indx;

        public Render3D0(int i, float f)
        {
            this(i, com.maddox.il2.engine.Engine.rendersMain(), f);
        }

        public Render3D0(int i, com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            _indx = 0;
            _indx = i;
            setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
            useClearStencil(true);
            contextResized();
        }
    }

    class DrwArray
    {

        java.util.ArrayList drwSolidPlate;
        java.util.ArrayList drwTranspPlate;
        java.util.ArrayList drwShadowPlate;
        java.util.ArrayList drwSolid;
        java.util.ArrayList drwTransp;
        java.util.ArrayList drwShadow;

        DrwArray()
        {
            drwSolidPlate = new ArrayList();
            drwTranspPlate = new ArrayList();
            drwShadowPlate = new ArrayList();
            drwSolid = new ArrayList();
            drwTransp = new ArrayList();
            drwShadow = new ArrayList();
        }
    }

    class ShadowPairsFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(actor != shadowPairsCur1 && (actor instanceof com.maddox.il2.engine.ActorHMesh) && d <= com.maddox.il2.game.Main3D.shadowPairsR2 && ((com.maddox.il2.engine.ActorHMesh)actor).hierMesh() != null)
            {
                shadowPairsList2.add(shadowPairsCur1.hierMesh());
                shadowPairsList2.add(((com.maddox.il2.engine.ActorHMesh)actor).hierMesh());
            }
            return false;
        }

        ShadowPairsFilter()
        {
        }
    }

    public class Render3D0R extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            if(com.maddox.il2.engine.Engine.land().ObjectsReflections_Begin(0) == 1)
            {
                getCamera().pos.getRender(__p, __o);
                boolean flag = true;
                if(com.maddox.il2.engine.Landscape.isExistMeshs())
                {
                    drwSolid.clear();
                    drwTransp.clear();
                    com.maddox.il2.ai.World _tmp = com.maddox.il2.engine.Engine.cur.world;
                    com.maddox.il2.engine.Engine.drawEnv().preRender(__p.x, __p.y, __p.z, com.maddox.il2.ai.World.MaxVisualDistance * 0.5F, 1, drwSolidL, drwTranspL, null, flag);
                    int i = drwSolidL.size();
                    for(int j = 0; j < i; j++)
                    {
                        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)drwSolidL.get(j);
                        if(actor instanceof com.maddox.il2.engine.ActorLandMesh)
                            drwSolid.add(actor);
                    }

                    i = drwTranspL.size();
                    for(int k = 0; k < i; k++)
                    {
                        com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)drwTranspL.get(k);
                        if(actor1 instanceof com.maddox.il2.engine.ActorLandMesh)
                            drwTransp.add(actor1);
                    }

                    flag = false;
                }
                com.maddox.il2.ai.World _tmp1 = com.maddox.il2.engine.Engine.cur.world;
                com.maddox.il2.engine.Engine.drawEnv().preRender(__p.x, __p.y, __p.z, com.maddox.il2.ai.World.MaxVisualDistance * 0.5F, 14, drwSolid, drwTransp, null, flag);
                com.maddox.il2.engine.Engine.land().ObjectsReflections_End();
            }
        }

        public void render()
        {
            if(com.maddox.il2.engine.Engine.land().ObjectsReflections_Begin(1) == 1)
            {
                draw(drwSolid, drwTransp);
                com.maddox.il2.engine.Engine.land().ObjectsReflections_End();
            }
        }

        public boolean isShow()
        {
            return bDrawLand && render3D0.isShow();
        }

        java.util.ArrayList drwSolidL;
        java.util.ArrayList drwTranspL;
        java.util.ArrayList drwSolid;
        java.util.ArrayList drwTransp;

        public Render3D0R(float f)
        {
            super(com.maddox.il2.engine.Engine.rendersMain(), f);
            drwSolidL = new ArrayList();
            drwTranspL = new ArrayList();
            drwSolid = new ArrayList();
            drwTransp = new ArrayList();
        }
    }

    public static class HookReflection extends com.maddox.il2.engine.HookRender
    {

        public boolean computeRenderPos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            computePos(actor, loc, loc1);
            return true;
        }

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
            com.maddox.JGP.Point3d point3d = loc.getPoint();
            com.maddox.il2.engine.Orient orient = loc.getOrient();
            loc1.set(loc);
        }

        public HookReflection()
        {
        }
    }

    public static class Camera3DR extends com.maddox.il2.engine.Camera3D
    {

        public boolean activate(float f, int i, int j, int k, int l, int i1, int j1, 
                int k1, int l1, int i2, int j2)
        {
            com.maddox.il2.engine.Engine.land().ObjectsReflections_Begin(0);
            return super.activate(f, i / 2, j / 2, k, l, i1 / 2, j1 / 2, k1, l1, i2 / 2, j2 / 2);
        }

        public Camera3DR()
        {
        }
    }

    public class NetCamera3D extends com.maddox.il2.engine.Camera3D
    {

        public void set(float f)
        {
            super.set(f);
            _camera3D[1].set(f);
            _camera3D[1].pos.setRel(new Orient(-f, 0.0F, 0.0F));
            _camera3D[2].set(f);
            _camera3D[2].pos.setRel(new Orient(f, 0.0F, 0.0F));
            _cameraCockpit[1].set(f);
            _cameraCockpit[1].pos.setRel(new Orient(-f, 0.0F, 0.0F));
            _cameraCockpit[2].set(f);
            _cameraCockpit[2].pos.setRel(new Orient(f, 0.0F, 0.0F));
            camera3DR.set(f);
        }

        public NetCamera3D()
        {
        }
    }


    public Main3D()
    {
        bDrawIfNotFocused = false;
        bUseStartLog = false;
        bUseGUI = true;
        bShowStartIntro = false;
        _overLoad = new com.maddox.il2.objects.effects.OverLoad[3];
        _sunGlare = new com.maddox.il2.objects.effects.SunGlare[3];
        _lightsGlare = new com.maddox.il2.objects.effects.LightsGlare[3];
        _sunFlare = new com.maddox.il2.objects.effects.SunFlare[3];
        _sunFlareRender = new com.maddox.il2.engine.Render[3];
        bViewFly = false;
        bViewEnemy = false;
        bEnableFog = true;
        bDrawLand = false;
        bDrawClouds = false;
        _cinema = new com.maddox.il2.objects.effects.Cinema[3];
        _render3D0 = new com.maddox.il2.game.Render3D0[3];
        _render3D1 = new com.maddox.il2.game.Render3D1[3];
        _camera3D = new com.maddox.il2.engine.Camera3D[3];
        _render2D = new com.maddox.il2.game.Render2D[3];
        _camera2D = new com.maddox.il2.engine.CameraOrtho2D[3];
        _renderCockpit = new com.maddox.il2.game.RenderCockpit[3];
        _cameraCockpit = new com.maddox.il2.engine.Camera3D[3];
        viewMirror = 2;
        iconTypes = 3;
        bLoadRecordedStates1Before = false;
        bRenderMirror = false;
        iRenderIndx = 0;
        _modelMatrix3D = new double[3][16];
        _projMatrix3D = new double[3][16];
        _viewport = new int[3][4];
        _modelMatrix3DMirror = new double[16];
        _projMatrix3DMirror = new double[16];
        _viewportMirror = new int[4];
        _dIn = new double[4];
        _dOut = new double[4];
        shadowPairsList1 = new ArrayList();
        shadowPairsMap1 = new HashMap();
        shadowPairsCur1 = null;
        shadowPairsList2 = new ArrayList();
        shadowPairsFilter = new ShadowPairsFilter();
        drwMirror = new DrwArray();
        __l = new Loc();
        __p = new Point3d();
        __o = new Orient();
        __v = new Vector3d();
        bShowTime = false;
        iconGroundDrawMin = 200D;
        iconSmallDrawMin = 100D;
        iconAirDrawMin = 1000D;
        iconDrawMax = 10000D;
        iconFarPadlockItem = new FarActorItem(0, 0, 0, 0, 0.0F, null);
        farActorFilter = new FarActorFilter();
        line3XYZ = new float[9];
        _lineP = new Point3d();
        _lineO = new Orient();
        transformMirror = new TransformMirror();
        lastTimeScreenShot = 0L;
        scrShot = null;
    }

    public static com.maddox.il2.game.Main3D cur3D()
    {
        return (com.maddox.il2.game.Main3D)com.maddox.il2.game.Main3D.cur();
    }

    public boolean isUseStartLog()
    {
        return bUseStartLog;
    }

    public boolean isShowStartIntro()
    {
        return bShowStartIntro;
    }

    public boolean isDrawLand()
    {
        return bDrawLand;
    }

    public void setDrawLand(boolean flag)
    {
        bDrawLand = flag;
    }

    public boolean isDemoPlaying()
    {
        if(playRecordedStreams != null)
            return true;
        if(keyRecord == null)
            return false;
        else
            return keyRecord.isPlaying();
    }

    public com.maddox.il2.engine.Actor viewActor()
    {
        return camera3D.pos.base();
    }

    public boolean isViewInsideShow()
    {
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return true;
        else
            return !cockpitCur.isNullShow();
    }

    public boolean isEnableRenderingCockpit()
    {
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return true;
        else
            return cockpitCur.isEnableRendering();
    }

    public boolean isViewOutside()
    {
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return true;
        else
            return !cockpitCur.isFocused();
    }

    public boolean isViewPadlock()
    {
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return false;
        else
            return cockpitCur.isPadlock();
    }

    public com.maddox.il2.engine.Actor getViewPadlockEnemy()
    {
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return null;
        else
            return cockpitCur.getPadlockEnemy();
    }

    public void setViewInsideShow(boolean flag)
    {
        if(isViewOutside() || isViewInsideShow() == flag)
            return;
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
        {
            return;
        } else
        {
            cockpitCur.setNullShow(!flag);
            return;
        }
    }

    public void setEnableRenderingCockpit(boolean flag)
    {
        if(isViewOutside() || isEnableRenderingCockpit() == flag)
            return;
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
        {
            return;
        } else
        {
            cockpitCur.setEnableRendering(flag);
            return;
        }
    }

    private void endViewFly()
    {
        if(!bViewFly)
        {
            return;
        } else
        {
            hookViewFly.use(false);
            com.maddox.il2.engine.Engine.soundListener().setUseBaseSpeed(true);
            bViewFly = false;
            return;
        }
    }

    private void endViewEnemy()
    {
        if(!bViewEnemy)
        {
            return;
        } else
        {
            hookViewEnemy.stop();
            bViewEnemy = false;
            return;
        }
    }

    private void endView()
    {
        if(!isViewOutside())
            return;
        if(bViewFly || bViewEnemy)
        {
            return;
        } else
        {
            hookView.use(false);
            return;
        }
    }

    private void endViewInside()
    {
        if(isViewOutside())
        {
            return;
        } else
        {
            cockpitCur.focusLeave();
            return;
        }
    }

    public void setViewFly(com.maddox.il2.engine.Actor actor)
    {
        endView();
        endViewEnemy();
        endViewInside();
        com.maddox.il2.game.Selector.resetGame();
        hookViewFly.use(true);
        bViewFly = true;
        camera3D.pos.setRel(new Point3d(), new Orient());
        camera3D.pos.changeBase(actor, hookViewFly, false);
        camera3D.pos.resetAsBase();
        com.maddox.il2.engine.Engine.soundListener().setUseBaseSpeed(false);
    }

    private void setViewSomebody(com.maddox.il2.engine.Actor actor)
    {
        endView();
        endViewFly();
        endViewInside();
        bViewEnemy = true;
        camera3D.pos.setRel(new Point3d(), new Orient());
        camera3D.pos.changeBase(actor, hookViewEnemy, false);
        camera3D.pos.resetAsBase();
        if(actor instanceof com.maddox.il2.objects.ActorViewPoint)
            ((com.maddox.il2.objects.ActorViewPoint)actor).setViewActor(hookViewEnemy.enemy());
    }

    public void setViewEnemy(com.maddox.il2.engine.Actor actor, boolean flag, boolean flag1)
    {
        com.maddox.il2.engine.Actor actor1 = com.maddox.il2.game.Selector.look(flag, flag1, camera3D, actor.getArmy(), -1, actor, true);
        if(!hookViewEnemy.start(actor, actor1, flag1, true))
        {
            if(bViewEnemy)
                setView(actor);
            return;
        } else
        {
            setViewSomebody(actor);
            return;
        }
    }

    public void setViewFriend(com.maddox.il2.engine.Actor actor, boolean flag, boolean flag1)
    {
        com.maddox.il2.engine.Actor actor1 = com.maddox.il2.game.Selector.look(flag, flag1, camera3D, -1, actor.getArmy(), actor, true);
        if(!hookViewEnemy.start(actor, actor1, flag1, false))
        {
            if(bViewEnemy)
                setView(actor);
            return;
        } else
        {
            setViewSomebody(actor);
            return;
        }
    }

    public void setViewPadlock(boolean flag, boolean flag1)
    {
        if(isViewOutside())
            return;
        if(!cockpitCur.existPadlock())
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        com.maddox.il2.engine.Actor actor;
        if(com.maddox.il2.ai.World.cur().diffCur.No_Icons)
            actor = com.maddox.il2.game.Selector.look(true, flag1, camera3D, -1, -1, aircraft, false);
        else
        if(flag)
            actor = com.maddox.il2.game.Selector.look(true, flag1, camera3D, -1, aircraft.getArmy(), aircraft, false);
        else
            actor = com.maddox.il2.game.Selector.look(true, flag1, camera3D, aircraft.getArmy(), -1, aircraft, false);
        if(actor == null || actor == aircraft)
            return;
        if(!cockpitCur.startPadlock(actor))
            return;
        else
            return;
    }

    public void setViewEndPadlock()
    {
        if(isViewOutside())
            return;
        if(!cockpitCur.existPadlock())
        {
            return;
        } else
        {
            cockpitCur.endPadlock();
            return;
        }
    }

    public void setViewNextPadlock(boolean flag)
    {
        if(isViewOutside())
            return;
        if(!cockpitCur.existPadlock())
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.game.Selector.next(flag);
        if(actor == null)
            return;
        if(!cockpitCur.startPadlock(actor))
            return;
        else
            return;
    }

    public void setViewPadlockForward(boolean flag)
    {
        if(isViewPadlock())
            cockpitCur.setPadlockForward(flag);
    }

    public void setViewInside()
    {
        if(!isViewOutside() && !isViewPadlock())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return;
        if(!cockpitCur.isEnableFocusing())
        {
            return;
        } else
        {
            endView();
            endViewFly();
            endViewEnemy();
            endViewInside();
            cockpitCur.focusEnter();
            return;
        }
    }

    public void setViewFlow30(com.maddox.il2.engine.Actor actor)
    {
        setView(actor, true);
        hookView.set(actor, 30F, -30F);
        camera3D.pos.resetAsBase();
    }

    public void setViewFlow10(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        hookView.setFollow(flag);
        setView(actor, true);
        hookView.set(actor, 10F, -10F);
        camera3D.pos.resetAsBase();
    }

    public void setView(com.maddox.il2.engine.Actor actor)
    {
        setView(actor, false);
    }

    public void setView(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(viewActor() != actor || flag || bViewFly || bViewEnemy)
        {
            endViewFly();
            endViewEnemy();
            endViewInside();
            com.maddox.il2.game.Selector.resetGame();
            hookView.use(true);
            camera3D.pos.setRel(new Point3d(), new Orient());
            camera3D.pos.changeBase(actor, hookView, false);
            camera3D.pos.resetAsBase();
        }
    }

    public int cockpitCurIndx()
    {
        if(cockpits == null || cockpitCur == null)
            return -1;
        for(int i = 0; i < cockpits.length; i++)
            if(cockpitCur == cockpits[i])
                return i;

        return -1;
    }

    public void beginStep(int i)
    {
        if(!bUseStartLog)
            if(i >= 0)
                com.maddox.il2.engine.ConsoleGL0.exclusiveDrawStep(com.maddox.il2.game.I18N.gui("main.loading") + " " + i + "%", i);
            else
                com.maddox.il2.engine.ConsoleGL0.exclusiveDrawStep(null, -1);
    }

    public boolean beginApp(java.lang.String s, java.lang.String s1, int i)
    {
        com.maddox.il2.engine.Config.cur.mainSection = s1;
        com.maddox.il2.engine.Engine.cur = new Engine();
        com.maddox.il2.engine.Config.typeProvider();
        com.maddox.opengl.GLContext glcontext = com.maddox.il2.engine.Config.cur.createGlContext(com.maddox.il2.engine.Config.cur.ini.get(com.maddox.il2.engine.Config.cur.mainSection, "title", "il2"));
        return beginApp(glcontext, i);
    }

    public boolean beginApp(com.maddox.opengl.GLContext glcontext, int i)
    {
        com.maddox.il2.engine.Config.typeGlStrings();
        com.maddox.il2.engine.Config.cur.typeContextSettings(glcontext);
        bDrawIfNotFocused = com.maddox.il2.engine.Config.cur.ini.get("window", "DrawIfNotFocused", bDrawIfNotFocused);
        bShowStartIntro = com.maddox.il2.engine.Config.cur.ini.get("game", "intro", bShowStartIntro);
        com.maddox.rts.RTSConf.cur.start();
        com.maddox.il2.objects.air.PaintScheme.init();
        com.maddox.rts.NetEnv.cur().connect = new Connect();
        com.maddox.rts.NetEnv.cur();
        com.maddox.rts.NetEnv.host().destroy();
        new NetUser("No Name");
        com.maddox.rts.NetEnv.active(true);
        com.maddox.il2.engine.Config.cur.beginSound();
        com.maddox.il2.engine.RenderContext.activate(glcontext);
        com.maddox.il2.engine.RendersMain.setSaveAspect(true);
        com.maddox.il2.engine.RendersMain.setGlContext(glcontext);
        com.maddox.il2.engine.RendersMain.setTickPainting(true);
        com.maddox.il2.engine.TTFont.font[0] = com.maddox.il2.engine.TTFont.get("arialSmall");
        com.maddox.il2.engine.TTFont.font[1] = com.maddox.il2.engine.TTFont.get("arial10");
        com.maddox.il2.engine.TTFont.font[2] = com.maddox.il2.engine.TTFont.get("arialb12");
        com.maddox.il2.engine.TTFont.font[3] = com.maddox.il2.engine.TTFont.get("courSmall");
        com.maddox.il2.engine.ConsoleGL0.init("Console", i);
        bUseStartLog = com.maddox.il2.engine.Config.cur.ini.get("Console", "UseStartLog", bUseStartLog);
        if(bUseStartLog)
            com.maddox.il2.engine.ConsoleGL0.exclusiveDraw(true);
        else
            com.maddox.il2.engine.ConsoleGL0.exclusiveDraw("gui/background0.mat");
        beginStep(5);
        com.maddox.rts.CmdEnv.top().exec("CmdLoad com.maddox.rts.cmd.CmdLoad");
        com.maddox.rts.CmdEnv.top().exec("load com.maddox.rts.cmd.CmdFile PARAM CURENV on");
        com.maddox.rts.cmd.CmdSFS.bMountError = false;
        com.maddox.rts.CmdEnv.top().exec("file .rc");
        if(com.maddox.rts.cmd.CmdSFS.bMountError)
            return false;
        beginStep(10);
        try
        {
            com.maddox.il2.engine.Engine.setWorldAcoustics("Landscape");
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("World Acoustics NOT initialized: " + exception.getMessage());
            return false;
        }
        com.maddox.il2.engine.Engine.soundListener().initDraw();
        beginStep(15);
        com.maddox.il2.ai.Regiment.loadAll();
        preloadNetClasses();
        beginStep(20);
        preloadAirClasses();
        beginStep(25);
        preloadChiefClasses();
        beginStep(30);
        preloadStationaryClasses();
        preload();
        beginStep(35);
        camera3D = new NetCamera3D();
        camera3D.setName("camera");
        camera3D.set(FOVX, 1.2F, 48000F);
        render3D0 = new Render3D0(0, 1.0F);
        render3D0.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        render3D0.setName("render3D0");
        render3D0.setCamera(camera3D);
        com.maddox.il2.engine.Engine.lightEnv().sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(0.0F, 1.0F, -1F);
        vector3f.normalize();
        com.maddox.il2.engine.Engine.lightEnv().sun().set(vector3f);
        _camera3D[0] = camera3D;
        _render3D0[0] = render3D0;
        for(int j = 1; j < 3; j++)
        {
            _camera3D[j] = new Camera3D();
            _camera3D[j].set(FOVX, 1.2F, 48000F);
            _camera3D[j].pos.setBase(camera3D, null, false);
            _render3D0[j] = new Render3D0(j, 1.0F - (float)j * 0.001F);
            _render3D0[j].setSaveAspect(true);
            _render3D0[j].setCamera(_camera3D[j]);
        }

        _camera3D[1].pos.setRel(new Orient(-FOVX, 0.0F, 0.0F));
        _camera3D[2].pos.setRel(new Orient(FOVX, 0.0F, 0.0F));
        render3D1 = new Render3D1(0, 0.9F);
        render3D1.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        render3D1.setName("render3D1");
        render3D1.setCamera(camera3D);
        for(int k = 1; k < 3; k++)
        {
            _render3D1[k] = new Render3D1(k, 0.9F - (float)k * 0.001F);
            _render3D1[k].setSaveAspect(true);
            _render3D1[k].setCamera(_camera3D[k]);
        }

        camera3DR = new Camera3DR();
        camera3DR.setName("cameraR");
        camera3DR.set(FOVX, 1.2F, 48000F);
        camera3DR.pos.setBase(camera3D, new HookReflection(), false);
        render3D0R = new Render3D0R(1.1F);
        render3D0R.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        render3D0R.setName("render3D0R");
        render3D0R.setCamera(camera3DR);
        com.maddox.il2.engine.Engine.soundListener().pos.setBase(camera3D, null, false);
        com.maddox.il2.engine.TextScr.font();
        camera2D = new CameraOrtho2D();
        camera2D.setName("camera2D");
        render2D = new Render2D(0, 0.95F);
        render2D.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        render2D.setName("render2D");
        render2D.setCamera(camera2D);
        camera2D.set(0.0F, render2D.getViewPortWidth(), 0.0F, render2D.getViewPortHeight());
        camera2D.set(0.0F, 1.0F);
        render2D.setShow(true);
        _camera2D[0] = camera2D;
        _render2D[0] = render2D;
        for(int l = 1; l < 3; l++)
        {
            _camera2D[l] = new CameraOrtho2D();
            _render2D[l] = new Render2D(l, 0.95F - (float)l * 0.001F);
            _render2D[l].setSaveAspect(true);
            _render2D[l].setCamera(_camera2D[l]);
            _camera2D[l].set(0.0F, _render2D[l].getViewPortWidth(), 0.0F, _render2D[l].getViewPortHeight());
            _camera2D[l].set(0.0F, 1.0F);
        }

        camera3DMirror = new com.maddox.il2.objects.air.Cockpit.Camera3DMirror();
        camera3DMirror.setName("cameraMirror");
        camera3DMirror.set(FOVX, 1.2F, 48000F);
        camera3DMirror.pos.setBase(camera3D, com.maddox.il2.objects.air.Cockpit.getHookCamera3DMirror(false), false);
        render3D0Mirror = new Render3D0Mirror(1.8F);
        render3D0Mirror.setName("render3D0Mirror");
        render3D0Mirror.setCamera(camera3DMirror);
        render3D1Mirror = new Render3D1Mirror(1.78F);
        render3D1Mirror.setName("render3D1Mirror");
        render3D1Mirror.setCamera(camera3DMirror);
        camera2DMirror = new CameraOrtho2D();
        camera2DMirror.setName("camera2DMirror");
        render2DMirror = new Render2DMirror(1.79F);
        render2DMirror.setName("render2DMirror");
        render2DMirror.setCamera(camera2DMirror);
        camera2DMirror.set(0.0F, render2DMirror.getViewPortWidth(), 0.0F, render2DMirror.getViewPortHeight());
        camera2DMirror.set(0.0F, 1.0F);
        cameraCockpit = new Camera3D();
        cameraCockpit.setName("cameraCockpit");
        cameraCockpit.set(FOVX, 0.05F, 12.5F);
        renderCockpit = new RenderCockpit(0, 0.5F);
        renderCockpit.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        renderCockpit.setName("renderCockpit");
        renderCockpit.setCamera(cameraCockpit);
        renderCockpit.setShow(false);
        _cameraCockpit[0] = cameraCockpit;
        _renderCockpit[0] = renderCockpit;
        for(int i1 = 1; i1 < 3; i1++)
        {
            _cameraCockpit[i1] = new Camera3D();
            _cameraCockpit[i1].set(FOVX, 0.05F, 12.5F);
            _cameraCockpit[i1].pos.setBase(cameraCockpit, null, false);
            _renderCockpit[i1] = new RenderCockpit(i1, 0.5F - (float)i1 * 0.001F);
            _renderCockpit[i1].setSaveAspect(true);
            _renderCockpit[i1].setCamera(_cameraCockpit[i1]);
        }

        _cameraCockpit[1].pos.setRel(new Orient(-FOVX, 0.0F, 0.0F));
        _cameraCockpit[2].pos.setRel(new Orient(FOVX, 0.0F, 0.0F));
        cameraCockpitMirror = new com.maddox.il2.objects.air.Cockpit.Camera3DMirror();
        cameraCockpitMirror.pos.setBase(cameraCockpit, com.maddox.il2.objects.air.Cockpit.getHookCamera3DMirror(true), false);
        cameraCockpitMirror.setName("cameraCockpitMirror");
        cameraCockpitMirror.set(FOVX, 0.05F, 12.5F);
        renderCockpitMirror = new RenderCockpitMirror(1.77F);
        renderCockpitMirror.setName("renderCockpitMirror");
        renderCockpitMirror.setCamera(cameraCockpitMirror);
        cameraHUD = new CameraOrtho2D();
        cameraHUD.setName("cameraHUD");
        renderHUD = new RenderHUD(0.3F);
        renderHUD.setName("renderHUD");
        renderHUD.setCamera(cameraHUD);
        cameraHUD.set(0.0F, renderHUD.getViewPortWidth(), 0.0F, renderHUD.getViewPortHeight());
        cameraHUD.set(-1000F, 1000F);
        renderHUD.setShow(true);
        com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
        renderHUD.setLightEnv(lightenvxy);
        lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        vector3f = new Vector3f(0.0F, 1.0F, -1F);
        vector3f.normalize();
        lightenvxy.sun().set(vector3f);
        hud = new HUD();
        renderHUD.contextResized();
        drawFarActorsInit();
        cameraMap2D = new CameraOrtho2D();
        cameraMap2D.setName("cameraMap2D");
        renderMap2D = new RenderMap2D(0.2F);
        renderMap2D.setName("renderMap2D");
        renderMap2D.setCamera(cameraMap2D);
        cameraMap2D.set(0.0F, renderMap2D.getViewPortWidth(), 0.0F, renderMap2D.getViewPortHeight());
        renderMap2D.setShow(false);
        beginStep(40);
        _sunFlareRender[0] = com.maddox.il2.objects.effects.SunFlare.newRender(0, 0.19F, _camera3D[0]);
        _sunFlareRender[0].setName("renderSunFlare");
        _sunFlareRender[0].setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        _sunFlareRender[0].setShow(false);
        for(int j1 = 1; j1 < 3; j1++)
        {
            _sunFlareRender[j1] = com.maddox.il2.objects.effects.SunFlare.newRender(j1, 0.19F, _camera3D[j1]);
            _sunFlareRender[j1].setSaveAspect(true);
            _sunFlareRender[j1].setShow(false);
        }

        lightsGlare = new LightsGlare(0, 0.17F);
        lightsGlare.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        lightsGlare.setCamera(new CameraOrtho2D());
        lightsGlare.setShow(false);
        _lightsGlare[0] = lightsGlare;
        for(int k1 = 1; k1 < 3; k1++)
        {
            _lightsGlare[k1] = new LightsGlare(k1, 0.17F - (float)k1 * 0.001F);
            _lightsGlare[k1].setSaveAspect(true);
            _lightsGlare[k1].setCamera(new CameraOrtho2D());
        }

        sunGlare = new SunGlare(0, 0.15F);
        sunGlare.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        sunGlare.setCamera(new CameraOrtho2D());
        sunGlare.setShow(false);
        _sunGlare[0] = sunGlare;
        for(int l1 = 1; l1 < 3; l1++)
        {
            _sunGlare[l1] = new SunGlare(l1, 0.15F - (float)l1 * 0.001F);
            _sunGlare[l1].setSaveAspect(true);
            _sunGlare[l1].setCamera(new CameraOrtho2D());
        }

        overLoad = new OverLoad(0, 0.1F);
        overLoad.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        overLoad.setCamera(new CameraOrtho2D());
        overLoad.setShow(false);
        _overLoad[0] = overLoad;
        for(int i2 = 1; i2 < 3; i2++)
        {
            _overLoad[i2] = new OverLoad(i2, 0.1F - (float)i2 * 0.001F);
            _overLoad[i2].setSaveAspect(true);
            _overLoad[i2].setCamera(new CameraOrtho2D());
        }

        darkerNight = new DarkerNight(0, 0.5F);
        darkerNight.setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        darkerNight.setCamera(new CameraOrtho2D());
        darkerNight.setShow(true);
        _cinema[0] = new Cinema(0, 0.09F);
        _cinema[0].setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        _cinema[0].setCamera(new CameraOrtho2D());
        _cinema[0].setShow(false);
        for(int j2 = 1; j2 < 3; j2++)
        {
            _cinema[j2] = new Cinema(j2, 0.09F - (float)j2 * 0.001F);
            _cinema[j2].setSaveAspect(true);
            _cinema[j2].setCamera(new CameraOrtho2D());
            _cinema[j2].setShow(false);
        }

        timeSkip = new TimeSkip(-1.1F);
        com.maddox.rts.HotKeyEnv.fromIni("hotkeys", com.maddox.il2.engine.Config.cur.ini, com.maddox.il2.engine.Config.cur.ini.get(com.maddox.il2.engine.Config.cur.mainSection, "hotkeys", "hotkeys"));
        com.maddox.il2.engine.hotkey.FreeFly.init("FreeFly");
        com.maddox.il2.engine.hotkey.FreeFlyXYZ.init("FreeFlyXYZ");
        hookView = new HookView("HookView");
        hookView.setCamera(camera3D);
        hookViewFly = new HookViewFly("HookViewFly");
        hookViewEnemy = new HookViewEnemy();
        hookViewEnemy.setCamera(camera3D);
        com.maddox.il2.engine.hotkey.HookPilot.New();
        com.maddox.il2.engine.hotkey.HookPilot.current.setTarget(cameraCockpit);
        com.maddox.il2.engine.hotkey.HookPilot.current.setTarget2(camera3D);
        com.maddox.il2.game.HookKeys.New();
        aircraftHotKeys = new AircraftHotKeys();
        beginStep(45);
        com.maddox.rts.HotKeyCmdEnv.enable("default", false);
        com.maddox.rts.HotKeyCmdEnv.enable("Console", false);
        com.maddox.rts.HotKeyCmdEnv.enable("hotkeys", false);
        com.maddox.rts.HotKeyCmdEnv.enable("HookView", false);
        com.maddox.rts.HotKeyCmdEnv.enable("PanView", false);
        com.maddox.rts.HotKeyCmdEnv.enable("SnapView", false);
        com.maddox.rts.HotKeyCmdEnv.enable("pilot", false);
        com.maddox.rts.HotKeyCmdEnv.enable("move", false);
        com.maddox.rts.HotKeyCmdEnv.enable("gunner", false);
        com.maddox.rts.HotKeyEnv.enable("pilot", false);
        com.maddox.rts.HotKeyEnv.enable("move", false);
        com.maddox.rts.HotKeyEnv.enable("gunner", false);
        com.maddox.rts.HotKeyCmdEnv.enable("misc", false);
        com.maddox.rts.HotKeyCmdEnv.enable("$$$misc", true);
        com.maddox.rts.HotKeyEnv.enable("$$$misc", true);
        com.maddox.rts.HotKeyCmdEnv.enable("orders", false);
        com.maddox.rts.HotKeyCmdEnv.enable("aircraftView", false);
        com.maddox.rts.HotKeyCmdEnv.enable("timeCompression", false);
        com.maddox.rts.HotKeyCmdEnv.enable("gui", false);
        com.maddox.rts.HotKeyCmdEnv.enable("builder", false);
        com.maddox.rts.HotKeyCmdEnv.enable("MouseXYZ", false);
        com.maddox.rts.HotKeyCmdEnv.enable("FreeFly", false);
        com.maddox.rts.HotKeyCmdEnv.enable("FreeFlyXYZ", false);
        com.maddox.il2.ai.World.cur().userCfg = com.maddox.il2.ai.UserCfg.loadCurrent();
        com.maddox.il2.ai.World.cur().setUserCovers();
        ordersTree = new OrdersTree(true);
        beginStep(50);
        if(bUseGUI)
        {
            guiManager = com.maddox.il2.gui.GUI.create("gui");
            keyRecord = new KeyRecord();
            keyRecord.addExcludePrevCmd(278);
            com.maddox.rts.Keyboard.adapter().setKeyEnable(27);
        }
        beginStep(90);
        initHotKeys();
        com.maddox.il2.objects.sounds.Voice.setEnableVoices(!com.maddox.il2.engine.Config.cur.ini.get("game", "NoChatter", false));
        beginStep(95);
        viewSet_Load();
        com.maddox.il2.game.DeviceLink.start();
        onBeginApp();
        com.maddox.rts.Time.setPause(false);
        com.maddox.rts.RTSConf.cur.loopMsgs();
        com.maddox.rts.Time.setPause(true);
        new com.maddox.rts.MsgAction(64, 1.0D + java.lang.Math.random() * 10D) {

            public void doAction()
            {
                try
                {
                    java.lang.Class.forName("fbapi");
                    com.maddox.il2.game.Main.doGameExit();
                }
                catch(java.lang.Throwable throwable) { }
            }

        }
;
        bDrawClouds = true;
        com.maddox.il2.engine.TextScr.setColor(new Color4f(1.0F, 0.0F, 0.0F, 1.0F));
        com.maddox.rts.RTSConf.cur.console.getEnv().exec("file rcu");
        beginStep(-1);
        createConsoleServer();
        return true;
    }

    public void setSaveAspect(boolean flag)
    {
        if(com.maddox.il2.engine.Config.cur.windowSaveAspect == flag)
        {
            return;
        } else
        {
            render3D0.setSaveAspect(flag);
            render3D1.setSaveAspect(flag);
            render2D.setSaveAspect(flag);
            renderCockpit.setSaveAspect(flag);
            _sunFlareRender[0].setSaveAspect(flag);
            lightsGlare.setSaveAspect(flag);
            sunGlare.setSaveAspect(flag);
            overLoad.setSaveAspect(flag);
            darkerNight.setSaveAspect(flag);
            _cinema[0].setSaveAspect(flag);
            com.maddox.il2.engine.Config.cur.windowSaveAspect = flag;
            return;
        }
    }

    public static void menuMusicPlay()
    {
        com.maddox.il2.game.Main3D.menuMusicPlay(_sLastMusic);
    }

    public static void menuMusicPlay(java.lang.String s)
    {
        s = com.maddox.il2.ai.Regiment.getCountryFromBranch(s);
        _sLastMusic = s;
        com.maddox.rts.CmdEnv.top().exec("music FILE music/menu/" + _sLastMusic);
    }

    public void viewSet_Load()
    {
        int i = com.maddox.il2.engine.Config.cur.ini.get("game", "viewSet", 0);
        viewSet_Set(i);
        iconTypes = com.maddox.il2.engine.Config.cur.ini.get("game", "iconTypes", 3, 0, 3);
    }

    public void viewSet_Save()
    {
        if(aircraftHotKeys != null)
        {
            com.maddox.il2.engine.Config.cur.ini.set("game", "viewSet", viewSet_Get());
            com.maddox.il2.engine.Config.cur.ini.set("game", "iconTypes", iconTypes());
        }
    }

    protected int viewSet_Get()
    {
        int i = 0;
        if(com.maddox.il2.game.HookKeys.current != null && com.maddox.il2.game.HookKeys.current.isPanView())
            i |= 1;
        if(com.maddox.il2.engine.hotkey.HookPilot.current != null && com.maddox.il2.engine.hotkey.HookPilot.current.isAim())
            i |= 2;
        i |= (viewMirror & 3) << 2;
        if(!aircraftHotKeys.isAutoAutopilot())
            i |= 0x10;
        i |= (com.maddox.il2.game.HUD.drawSpeed() & 3) << 5;
        return i;
    }

    private void viewSet_Set(int i)
    {
        com.maddox.il2.game.HookKeys.current.setMode((i & 1) != 0);
        com.maddox.il2.engine.hotkey.HookPilot.current.doAim((i & 2) != 0);
        viewMirror = i >> 2 & 3;
        aircraftHotKeys.setAutoAutopilot((i & 0x10) == 0);
        com.maddox.il2.game.HUD.setDrawSpeed(i >> 5 & 3);
    }

    public boolean isViewMirror()
    {
        return viewMirror > 0;
    }

    public int iconTypes()
    {
        return iconTypes;
    }

    protected void changeIconTypes()
    {
        iconTypes = (iconTypes + 1) % 4;
    }

    public void disableAllHotKeyCmdEnv()
    {
        java.util.List list = com.maddox.rts.HotKeyCmdEnv.allEnv();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = (com.maddox.rts.HotKeyCmdEnv)list.get(j);
            hotkeycmdenv.enable(false);
        }

        com.maddox.rts.HotKeyCmdEnv.enable("hotkeys", true);
        com.maddox.rts.HotKeyCmdEnv.enable("$$$misc", true);
    }

    public void enableHotKeyCmdEnvs(java.lang.String as[])
    {
        for(int i = 0; i < as.length; i++)
            com.maddox.rts.HotKeyCmdEnv.enable(as[i], true);

    }

    public void enableOnlyHotKeyCmdEnvs(java.lang.String as[])
    {
        disableAllHotKeyCmdEnv();
        enableHotKeyCmdEnvs(as);
    }

    public void enableOnlyHotKeyCmdEnv(java.lang.String s)
    {
        disableAllHotKeyCmdEnv();
        com.maddox.rts.HotKeyCmdEnv.enable(s, true);
    }

    public void enableGameHotKeyCmdEnvs()
    {
        enableHotKeyCmdEnvs(gameHotKeyCmdEnvs);
    }

    public void enableOnlyGameHotKeyCmdEnvs()
    {
        enableOnlyHotKeyCmdEnvs(gameHotKeyCmdEnvs);
    }

    public void enableCockpitHotKeys()
    {
        if(isDemoPlaying())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return;
        java.lang.String as[] = cockpitCur.getHotKeyEnvs();
        if(as == null)
            return;
        for(int i = 0; i < as.length; i++)
            if(as[i] != null)
                com.maddox.rts.HotKeyEnv.enable(as[i], true);

    }

    public void disableCockpitHotKeys()
    {
        if(isDemoPlaying())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(cockpitCur))
            return;
        java.lang.String as[] = cockpitCur.getHotKeyEnvs();
        if(as == null)
            return;
        for(int i = 0; i < as.length; i++)
            if(as[i] != null)
                com.maddox.rts.HotKeyEnv.enable(as[i], false);

    }

    private void _disableCockpitsHotKeys()
    {
        com.maddox.rts.HotKeyEnv.enable("pilot", false);
        com.maddox.rts.HotKeyEnv.enable("move", false);
        com.maddox.rts.HotKeyEnv.enable("gunner", false);
    }

    public void disableCockpitsHotKeys()
    {
        if(isDemoPlaying())
        {
            return;
        } else
        {
            _disableCockpitsHotKeys();
            return;
        }
    }

    public void resetGameClear()
    {
        com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.resetGame();
        disableCockpitsHotKeys();
        camera3D.pos.changeBase(null, null, false);
        camera3D.pos.setAbs(new Point3d(), new Orient());
        com.maddox.il2.engine.hotkey.FreeFly.adapter().resetGame();
        if(com.maddox.il2.engine.hotkey.HookPilot.current != null)
        {
            com.maddox.il2.engine.hotkey.HookPilot.current.use(false);
            com.maddox.il2.engine.hotkey.HookPilot.current.resetGame();
        }
        com.maddox.il2.engine.hotkey.HookGunner.resetGame();
        if(com.maddox.il2.game.HookKeys.current != null)
            com.maddox.il2.game.HookKeys.current.resetGame();
        hookViewFly.reset();
        hookViewEnemy.reset();
        hookView.reset();
        hookView.resetGame();
        hookViewEnemy.resetGame();
        overLoad.setShow(false);
        for(int i = 0; i < 3; i++)
        {
            _lightsGlare[i].setShow(false);
            _lightsGlare[i].resetGame();
            _sunGlare[i].setShow(false);
            _sunGlare[i].resetGame();
        }

        com.maddox.il2.game.Selector.resetGame();
        hud.resetGame();
        aircraftHotKeys.resetGame();
        bViewFly = false;
        bViewEnemy = false;
        ordersTree.resetGameClear();
        if(clouds != null)
        {
            clouds.destroy();
            clouds = null;
        }
        if(zip != null)
        {
            zip.destroy();
            zip = null;
        }
        sunFlareDestroy();
        if(com.maddox.il2.engine.Actor.isValid(spritesFog))
            spritesFog.destroy();
        spritesFog = null;
        if(land2D != null)
        {
            if(!land2D.isDestroyed())
                land2D.destroy();
            land2D = null;
        }
        if(land2DText != null)
        {
            if(!land2DText.isDestroyed())
                land2DText.destroy();
            land2DText = null;
        }
        if(cockpits != null)
        {
            for(int j = 0; j < cockpits.length; j++)
            {
                if(com.maddox.il2.engine.Actor.isValid(cockpits[j]))
                    cockpits[j].destroy();
                cockpits[j] = null;
            }

            cockpits = null;
        }
        cockpitCur = null;
        super.resetGameClear();
    }

    public void resetGameCreate()
    {
        super.resetGameCreate();
        com.maddox.il2.engine.Engine.soundListener().pos.setBase(camera3D, null, false);
        com.maddox.il2.engine.Engine.soundListener().setUseBaseSpeed(true);
    }

    public void resetUserClear()
    {
        com.maddox.il2.ai.World.cur().resetUser();
        aircraftHotKeys.resetUser();
        if(cockpits != null)
        {
            for(int i = 0; i < cockpits.length; i++)
            {
                if(com.maddox.il2.engine.Actor.isValid(cockpits[i]))
                    cockpits[i].destroy();
                cockpits[i] = null;
            }

            cockpits = null;
        }
        cockpitCur = null;
        super.resetUserClear();
    }

    public void sunFlareCreate()
    {
        sunFlareDestroy();
        for(int i = 0; i < 3; i++)
            _sunFlare[i] = new SunFlare(_sunFlareRender[i]);

    }

    public void sunFlareDestroy()
    {
        for(int i = 0; i < 3; i++)
        {
            if(com.maddox.il2.engine.Actor.isValid(_sunFlare[i]))
                _sunFlare[i].destroy();
            _sunFlare[i] = null;
        }

    }

    public void sunFlareShow(boolean flag)
    {
        for(int i = 0; i < 3; i++)
            _sunFlareRender[i].setShow(flag);

    }

    public com.maddox.rts.KeyRecordCallback playRecordedMissionCallback()
    {
        return playRecordedMissionCallback;
    }

    public com.maddox.rts.InOutStreams playRecordedStreams()
    {
        return playRecordedStreams;
    }

    com.maddox.rts.NetChannelInStream playRecordedNetChannelIn()
    {
        return playRecordedNetChannelIn;
    }

    public com.maddox.il2.game.GameTrack gameTrackRecord()
    {
        return gameTrackRecord;
    }

    public void setGameTrackRecord(com.maddox.il2.game.GameTrack gametrack)
    {
        gameTrackRecord = gametrack;
    }

    public com.maddox.il2.game.GameTrack gameTrackPlay()
    {
        return gameTrackPlay;
    }

    public void setGameTrackPlay(com.maddox.il2.game.GameTrack gametrack)
    {
        gameTrackPlay = gametrack;
    }

    public void clearGameTrack(com.maddox.il2.game.GameTrack gametrack)
    {
        if(gametrack == gameTrackRecord)
            gameTrackRecord = null;
        if(gametrack == gameTrackPlay)
            gameTrackPlay = null;
    }

    public java.lang.String playRecordedMission(java.lang.String s)
    {
        playBatchCurRecord = -1;
        playEndBatch = true;
        playRecordedStreams = null;
        return playRecordedMission(s, true);
    }

    public java.lang.String playRecordedMission(java.lang.String s, boolean flag)
    {
        playRecordedFile = s;
        if(playRecordedMissionCallback == null)
            playRecordedMissionCallback = new com.maddox.rts.KeyRecordCallback() {

                public void playRecordedEnded()
                {
                    if(this != playRecordedMissionCallback)
                        return;
                    com.maddox.il2.game.GameState gamestate = com.maddox.il2.game.Main.state();
                    if(gamestate instanceof com.maddox.il2.gui.GUIRecordPlay)
                    {
                        com.maddox.il2.gui.GUIRecordPlay guirecordplay = (com.maddox.il2.gui.GUIRecordPlay)gamestate;
                        guirecordplay.doReplayMission(playRecordedFile, playEndBatch);
                    } else
                    if(gamestate instanceof com.maddox.il2.gui.GUITrainingPlay)
                    {
                        com.maddox.il2.gui.GUITrainingPlay guitrainingplay = (com.maddox.il2.gui.GUITrainingPlay)gamestate;
                        guitrainingplay.doQuitMission();
                        guitrainingplay.doExit();
                    } else
                    if(gamestate instanceof com.maddox.il2.gui.GUIBWDemoPlay)
                    {
                        com.maddox.il2.gui.GUIBWDemoPlay guibwdemoplay = (com.maddox.il2.gui.GUIBWDemoPlay)gamestate;
                        guibwdemoplay.doQuitMission();
                    }
                }

                public void doFirstHotCmd(boolean flag1)
                {
                    if(playRecordedStreams != null)
                    {
                        com.maddox.il2.game.AircraftHotKeys _tmp = aircraftHotKeys;
                        com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd = flag1;
                        loadRecordedStates1(flag1);
                        if(!flag1)
                            loadRecordedStates2();
                    }
                }

            }
;
        if(playRecordedStreams != null)
        {
            try
            {
                playRecordedStreams.close();
            }
            catch(java.lang.Exception exception) { }
            playRecordedStreams = null;
            com.maddox.il2.net.NetMissionTrack.stopPlaying();
        }
        if(playRecordedNetChannelIn != null)
            playRecordedNetChannelIn.destroy();
        playRecordedNetChannelIn = null;
        if(com.maddox.rts.InOutStreams.isExistAndValid(new File(s)))
            return playNetRecordedMission(s, flag);
        java.lang.String s1 = s;
        com.maddox.rts.SectFile sectfile = new SectFile(s, 0, false);
        int i = sectfile.sectionIndex("batch");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            if(j <= 0)
                return "Track file '" + s + "' is empty";
            playEndBatch = playBatchCurRecord != -1 && playBatchCurRecord == j - 2;
            if(j == 1)
                playEndBatch = true;
            playBatchCurRecord++;
            if(playBatchCurRecord >= j)
                playBatchCurRecord = 0;
            s1 = "Records/" + sectfile.line(i, playBatchCurRecord);
            if(com.maddox.rts.InOutStreams.isExistAndValid(new File(s1)))
                return playNetRecordedMission(s1, flag);
            sectfile = new SectFile(s1, 0, false);
        } else
        {
            playEndBatch = true;
        }
        i = sectfile.sectionIndex("$$$record");
        if(i < 0)
            return "Track file '" + s1 + "' not included section [$$$record]";
        if(sectfile.vars(i) <= 10)
            return "Track file '" + s1 + "' is empty";
        int k = java.lang.Integer.parseInt(sectfile.var(i, 0));
        if(k != 130)
            return "Track file '" + s1 + "' version is not supported";
        int l = java.lang.Integer.parseInt(sectfile.var(i, 1));
        float f = java.lang.Float.parseFloat(sectfile.var(i, 2));
        float f1 = java.lang.Float.parseFloat(sectfile.var(i, 3));
        float f2 = java.lang.Float.parseFloat(sectfile.var(i, 4));
        float f3 = java.lang.Float.parseFloat(sectfile.var(i, 5));
        float f4 = java.lang.Float.parseFloat(sectfile.var(i, 6));
        int i1 = java.lang.Integer.parseInt(sectfile.var(i, 7));
        int j1 = java.lang.Integer.parseInt(sectfile.var(i, 8));
        long l1 = java.lang.Long.parseLong(sectfile.var(i, 9));
        long l2 = sectfile.fingerExcludeSectPrefix("$$$");
        l2 = com.maddox.rts.Finger.incLong(l2, l);
        l2 = com.maddox.rts.Finger.incLong(l2, f);
        l2 = com.maddox.rts.Finger.incLong(l2, f1);
        l2 = com.maddox.rts.Finger.incLong(l2, f2);
        l2 = com.maddox.rts.Finger.incLong(l2, f3);
        l2 = com.maddox.rts.Finger.incLong(l2, f4);
        l2 = com.maddox.rts.Finger.incLong(l2, i1);
        l2 = com.maddox.rts.Finger.incLong(l2, j1);
        if(l1 != l2)
            return "Track file '" + s1 + "' is changed";
        com.maddox.il2.ai.World.cur().diffCur.set(l);
        com.maddox.il2.ai.World.cur().diffCur.Cockpit_Always_On = false;
        com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views = false;
        com.maddox.il2.ai.World.cur().diffCur.No_Padlock = false;
        com.maddox.il2.ai.World.cur().userCoverMashineGun = f;
        com.maddox.il2.ai.World.cur().userCoverCannon = f1;
        com.maddox.il2.ai.World.cur().userCoverRocket = f2;
        com.maddox.il2.ai.World.cur().userRocketDelay = f3;
        com.maddox.il2.ai.World.cur().userBombDelay = f4;
        viewSet_Set(i1);
        iconTypes = j1;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
        {
            new NetServerParams();
            com.maddox.il2.game.Main.cur().netServerParams.setMode(2);
            new NetLocalControl();
        }
        try
        {
            com.maddox.il2.game.Mission.loadFromSect(sectfile, true);
        }
        catch(java.lang.Exception exception1)
        {
            java.lang.System.out.println(exception1.getMessage());
            exception1.printStackTrace();
            return "Track file '" + s1 + "' load failed: " + exception1.getMessage();
        }
        playRecordedSect = sectfile;
        playRecorderIndx = i;
        playRecordedPlayFile = s1;
        if(flag)
            doRecordedPlayFirst();
        return null;
    }

    private java.lang.String playNetRecordedMission(java.lang.String s, boolean flag)
    {
        int i;
        int j;
        playRecordedStreams = new InOutStreams();
        playRecordedStreams.open(new File(s), false);
        java.io.InputStream inputstream = playRecordedStreams.openStream("version");
        java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
        i = java.lang.Integer.parseInt(bufferedreader.readLine());
        j = i;
        if(i >= 103)
            j = java.lang.Integer.parseInt(bufferedreader.readLine());
        bufferedreader.close();
        if(i == 100 || i == 101 || i == 102 || i == 103)
            break MISSING_BLOCK_LABEL_163;
        try
        {
            playRecordedStreams.close();
        }
        catch(java.lang.Exception exception2) { }
        playRecordedStreams = null;
        return "Track file '" + s + "' version is not supported";
        try
        {
            loadRecordedStates0();
            java.io.InputStream inputstream1 = playRecordedStreams.openStream("traffic");
            if(inputstream1 == null)
                throw new Exception("Stream 'traffic' not found.");
            playRecordedNetChannelIn = new NetChannelInStream(inputstream1, 1);
            com.maddox.rts.RTSConf.cur.netEnv.addChannel(playRecordedNetChannelIn);
            playRecordedNetChannelIn.setStateInit(0);
            playRecordedNetChannelIn.userState = 1;
            com.maddox.il2.net.NetMissionTrack.startPlaying(playRecordedStreams, i, j);
            if(flag)
                doRecordedPlayFirst();
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
            if(playRecordedStreams != null)
                try
                {
                    playRecordedStreams.close();
                }
                catch(java.lang.Exception exception1) { }
            playRecordedStreams = null;
            return "Track file '" + s + "' load failed: " + exception.getMessage();
        }
        return null;
    }

    private void doRecordedPlayFirst()
    {
        _disableCockpitsHotKeys();
        com.maddox.rts.HotKeyEnv.enable("misc", false);
        com.maddox.rts.HotKeyEnv.enable("orders", false);
        com.maddox.rts.HotKeyEnv.enable("timeCompression", false);
        com.maddox.rts.HotKeyEnv.enable("aircraftView", false);
        com.maddox.rts.HotKeyEnv.enable("HookView", false);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
    }

    public java.lang.String startPlayRecordedMission()
    {
        if(playRecordedStreams != null)
            keyRecord.startPlay(playRecordedMissionCallback);
        else
        if(!keyRecord.startPlay(playRecordedSect, playRecorderIndx, 10, playRecordedMissionCallback))
            return "Track file '" + playRecordedPlayFile + "' load failed";
        return null;
    }

    public void stopPlayRecordedMission()
    {
        playRecordedSect = null;
        if(keyRecord.isPlaying())
            keyRecord.stopPlay();
        if(playRecordedStreams != null)
        {
            try
            {
                playRecordedStreams.close();
            }
            catch(java.lang.Exception exception) { }
            playRecordedStreams = null;
            com.maddox.il2.net.NetMissionTrack.stopPlaying();
        }
        if(playRecordedNetChannelIn != null)
            playRecordedNetChannelIn.destroy();
        playRecordedNetChannelIn = null;
        _disableCockpitsHotKeys();
        com.maddox.rts.HotKeyEnv.enable("misc", true);
        com.maddox.rts.HotKeyEnv.enable("orders", true);
        com.maddox.rts.HotKeyEnv.enable("timeCompression", true);
        com.maddox.rts.HotKeyEnv.enable("aircraftView", true);
        com.maddox.rts.HotKeyEnv.enable("HookView", true);
        com.maddox.rts.HotKeyEnv.enable("PanView", true);
        com.maddox.rts.HotKeyEnv.enable("SnapView", true);
    }

    public void flyRecordedMission()
    {
        if(keyRecord.isPlaying())
        {
            keyRecord.stopPlay();
            playRecordedMissionCallback = null;
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur))
                com.maddox.rts.HotKeyCmd.exec("misc", "cockpitEnter" + cockpitCurIndx());
            enableCockpitHotKeys();
            com.maddox.rts.HotKeyEnv.enable("misc", true);
            com.maddox.rts.HotKeyEnv.enable("orders", true);
            com.maddox.rts.HotKeyEnv.enable("timeCompression", true);
            com.maddox.rts.HotKeyEnv.enable("aircraftView", true);
            com.maddox.rts.HotKeyEnv.enable("HookView", true);
            com.maddox.rts.HotKeyEnv.enable("PanView", true);
            com.maddox.rts.HotKeyEnv.enable("SnapView", true);
            com.maddox.il2.objects.effects.ForceFeedback.startMission();
        }
    }

    public boolean saveRecordedMission(java.lang.String s)
    {
        if(mission == null)
            return false;
        if(mission.isDestroyed())
            return false;
        if(!keyRecord.isContainRecorded())
            return false;
        com.maddox.rts.SectFile sectfile;
        sectfile = mission.sectFile();
        int i = sectfile.sectionIndex("$$$record");
        if(i >= 0)
            sectfile.sectionClear(i);
        else
            i = sectfile.sectionAdd("$$$record");
        sectfile.lineAdd(i, "130", "");
        long l = com.maddox.rts.Finger.incLong(mission.finger(), 130);
        int j = com.maddox.il2.ai.World.cur().diffCur.get();
        sectfile.lineAdd(i, "" + j, "");
        l = com.maddox.rts.Finger.incLong(mission.finger(), j);
        sectfile.lineAdd(i, "" + com.maddox.il2.ai.World.cur().userCoverMashineGun, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.ai.World.cur().userCoverMashineGun);
        sectfile.lineAdd(i, "" + com.maddox.il2.ai.World.cur().userCoverCannon, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.ai.World.cur().userCoverCannon);
        sectfile.lineAdd(i, "" + com.maddox.il2.ai.World.cur().userCoverRocket, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.ai.World.cur().userCoverRocket);
        sectfile.lineAdd(i, "" + com.maddox.il2.ai.World.cur().userRocketDelay, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.ai.World.cur().userRocketDelay);
        sectfile.lineAdd(i, "" + com.maddox.il2.ai.World.cur().userBombDelay, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.ai.World.cur().userBombDelay);
        sectfile.lineAdd(i, "" + com.maddox.il2.game.Mission.viewSet, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.game.Mission.viewSet);
        sectfile.lineAdd(i, "" + com.maddox.il2.game.Mission.iconTypes, "");
        l = com.maddox.rts.Finger.incLong(l, com.maddox.il2.game.Mission.iconTypes);
        sectfile.lineAdd(i, "" + l, "");
        keyRecord.saveRecorded(sectfile, i);
        return sectfile.saveFile(s);
        java.lang.Exception exception;
        exception;
        return false;
    }

    public boolean saveRecordedStates0(com.maddox.rts.InOutStreams inoutstreams)
    {
        java.io.PrintWriter printwriter = new PrintWriter(inoutstreams.createStream("states0"));
        printwriter.println(com.maddox.il2.ai.World.cur().diffCur.get());
        printwriter.println(com.maddox.il2.ai.World.cur().userCoverMashineGun);
        printwriter.println(com.maddox.il2.ai.World.cur().userCoverCannon);
        printwriter.println(com.maddox.il2.ai.World.cur().userCoverRocket);
        printwriter.println(com.maddox.il2.ai.World.cur().userRocketDelay);
        printwriter.println(com.maddox.il2.ai.World.cur().userBombDelay);
        printwriter.println(viewSet_Get());
        printwriter.println(iconTypes);
        printwriter.println(isViewOutside() ? "0" : "1");
        printwriter.println(FOVX);
        printwriter.flush();
        printwriter.close();
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    public boolean saveRecordedStates1(com.maddox.rts.InOutStreams inoutstreams)
    {
        java.io.PrintWriter printwriter = new PrintWriter(inoutstreams.createStream("states1"));
        com.maddox.il2.engine.hotkey.HookView.cur().saveRecordedStates(printwriter);
        com.maddox.il2.engine.hotkey.HookPilot.cur().saveRecordedStates(printwriter);
        com.maddox.il2.engine.hotkey.HookGunner.saveRecordedStates(printwriter);
        printwriter.flush();
        printwriter.close();
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    public boolean saveRecordedStates2(com.maddox.rts.InOutStreams inoutstreams)
    {
        java.io.PrintWriter printwriter = new PrintWriter(inoutstreams.createStream("states2"));
        printwriter.println(FOVX);
        int i = 0;
        if(hud.bDrawDashBoard)
            i |= 1;
        if(isViewInsideShow())
            i |= 2;
        if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isToggleAim())
            i |= 4;
        com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
        if(flightmodel != null && flightmodel.AS.bShowSmokesOn)
            i |= 8;
        if(isEnableRenderingCockpit())
            i |= 0x10;
        if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isToggleUp())
            i |= 0x20;
        if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isToggleDim())
            i |= 0x40;
        if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && cockpitCur.isToggleLight())
            i |= 0x80;
        if(com.maddox.il2.engine.Actor.isValid(cockpitCur) && !cockpitCur.isEnableRenderingBall())
            i |= 0x100;
        printwriter.println(i);
        printwriter.flush();
        printwriter.close();
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    public void loadRecordedStates0()
    {
        try
        {
            java.io.InputStream inputstream = playRecordedStreams.openStream("states0");
            java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            com.maddox.il2.ai.World.cur().diffCur.set(java.lang.Integer.parseInt(bufferedreader.readLine()));
            com.maddox.il2.ai.World.cur().diffCur.Cockpit_Always_On = false;
            com.maddox.il2.ai.World.cur().diffCur.No_Outside_Views = false;
            com.maddox.il2.ai.World.cur().diffCur.No_Padlock = false;
            com.maddox.il2.ai.World.cur().userCoverMashineGun = java.lang.Float.parseFloat(bufferedreader.readLine());
            com.maddox.il2.ai.World.cur().userCoverCannon = java.lang.Float.parseFloat(bufferedreader.readLine());
            com.maddox.il2.ai.World.cur().userCoverRocket = java.lang.Float.parseFloat(bufferedreader.readLine());
            com.maddox.il2.ai.World.cur().userRocketDelay = java.lang.Float.parseFloat(bufferedreader.readLine());
            com.maddox.il2.ai.World.cur().userBombDelay = java.lang.Float.parseFloat(bufferedreader.readLine());
            viewSet_Set(java.lang.Integer.parseInt(bufferedreader.readLine()));
            iconTypes = java.lang.Integer.parseInt(bufferedreader.readLine());
            bLoadRecordedStates1Before = java.lang.Integer.parseInt(bufferedreader.readLine()) == 1;
            float f = java.lang.Float.parseFloat(bufferedreader.readLine());
            if(f != FOVX)
                com.maddox.rts.CmdEnv.top().exec("fov " + f);
            inputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void loadRecordedStates1(boolean flag)
    {
        if(flag == bLoadRecordedStates1Before)
            try
            {
                java.io.InputStream inputstream = playRecordedStreams.openStream("states1");
                java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
                com.maddox.il2.engine.hotkey.HookView.cur().loadRecordedStates(bufferedreader);
                com.maddox.il2.engine.hotkey.HookPilot.cur().loadRecordedStates(bufferedreader);
                com.maddox.il2.engine.hotkey.HookGunner.loadRecordedStates(bufferedreader);
                inputstream.close();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
    }

    public void loadRecordedStates2()
    {
        try
        {
            java.io.InputStream inputstream = playRecordedStreams.openStream("states2");
            java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            float f = java.lang.Float.parseFloat(bufferedreader.readLine());
            if(f != FOVX)
                com.maddox.rts.CmdEnv.top().exec("fov " + f);
            int i = java.lang.Integer.parseInt(bufferedreader.readLine());
            hud.bDrawDashBoard = (i & 1) != 0;
            setViewInsideShow((i & 2) != 0);
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur))
                cockpitCur.doToggleAim((i & 4) != 0);
            com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
            if(flightmodel != null)
                flightmodel.AS.setAirShowState((i & 8) != 0);
            if(com.maddox.il2.engine.Actor.isValid(cockpitCur))
            {
                setEnableRenderingCockpit((i & 0x10) != 0);
                cockpitCur.doToggleUp((i & 0x20) != 0);
                if((i & 0x40) != 0 && !cockpitCur.isToggleDim())
                    cockpitCur.doToggleDim();
                if((i & 0x80) != 0 && !cockpitCur.isToggleLight())
                    cockpitCur.doToggleLight();
                cockpitCur.setEnableRenderingBall((i & 0x100) == 0);
            }
            inputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setRenderIndx(int i)
    {
        iRenderIndx = i;
    }

    public int getRenderIndx()
    {
        return iRenderIndx;
    }

    private void transform_point(double ad[], double ad1[], double ad2[])
    {
        ad[0] = ad1[0] * ad2[0] + ad1[4] * ad2[1] + ad1[8] * ad2[2] + ad1[12] * ad2[3];
        ad[1] = ad1[1] * ad2[0] + ad1[5] * ad2[1] + ad1[9] * ad2[2] + ad1[13] * ad2[3];
        ad[2] = ad1[2] * ad2[0] + ad1[6] * ad2[1] + ad1[10] * ad2[2] + ad1[14] * ad2[3];
        ad[3] = ad1[3] * ad2[0] + ad1[7] * ad2[1] + ad1[11] * ad2[2] + ad1[15] * ad2[3];
    }

    public boolean project2d(double d, double d1, double d2, com.maddox.JGP.Point3d point3d)
    {
        _dIn[0] = d;
        _dIn[1] = d1;
        _dIn[2] = d2;
        _dIn[3] = 1.0D;
        if(bRenderMirror)
        {
            transform_point(_dOut, _modelMatrix3DMirror, _dIn);
            transform_point(_dIn, _projMatrix3DMirror, _dOut);
        } else
        {
            transform_point(_dOut, _modelMatrix3D[iRenderIndx], _dIn);
            transform_point(_dIn, _projMatrix3D[iRenderIndx], _dOut);
        }
        if(_dIn[3] == 0.0D)
        {
            java.lang.System.out.println("BAD glu.Project: " + d + " " + d1 + " " + d2);
            return false;
        }
        _dIn[0] /= _dIn[3];
        _dIn[1] /= _dIn[3];
        _dIn[2] /= _dIn[3];
        if(bRenderMirror)
        {
            point3d.x = (double)_viewportMirror[0] + ((1.0D + _dIn[0]) * (double)_viewportMirror[2]) / 2D;
            point3d.y = (double)_viewportMirror[1] + ((1.0D + _dIn[1]) * (double)_viewportMirror[3]) / 2D;
        } else
        {
            point3d.x = (double)_viewport[iRenderIndx][0] + ((1.0D + _dIn[0]) * (double)_viewport[iRenderIndx][2]) / 2D;
            point3d.y = (double)_viewport[iRenderIndx][1] + ((1.0D + _dIn[1]) * (double)_viewport[iRenderIndx][3]) / 2D;
        }
        point3d.z = (1.0D + _dIn[2]) / 2D;
        return true;
    }

    public boolean project2d_cam(double d, double d1, double d2, com.maddox.JGP.Point3d point3d)
    {
        if(!project2d(d, d1, d2, point3d))
            return false;
        if(bRenderMirror)
        {
            point3d.x -= _viewportMirror[0];
            point3d.y -= _viewportMirror[1];
        } else
        {
            point3d.x -= _viewport[iRenderIndx][0];
            point3d.y -= _viewport[iRenderIndx][1];
        }
        return true;
    }

    public boolean project2d_norm(double d, double d1, double d2, com.maddox.JGP.Point3d point3d)
    {
        _dIn[0] = d;
        _dIn[1] = d1;
        _dIn[2] = d2;
        _dIn[3] = 1.0D;
        if(bRenderMirror)
        {
            transform_point(_dOut, _modelMatrix3DMirror, _dIn);
            transform_point(_dIn, _projMatrix3DMirror, _dOut);
        } else
        {
            transform_point(_dOut, _modelMatrix3D[iRenderIndx], _dIn);
            transform_point(_dIn, _projMatrix3D[iRenderIndx], _dOut);
        }
        if(_dIn[3] == 0.0D)
        {
            java.lang.System.out.println("BAD glu.Project2: " + d + " " + d1 + " " + d2);
            return false;
        } else
        {
            double d3 = 1.0D / _dIn[3];
            point3d.x = _dIn[0] * d3;
            point3d.y = _dIn[1] * d3;
            point3d.z = _dIn[2] * d3;
            return true;
        }
    }

    public boolean project2d(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        return project2d(point3d.x, point3d.y, point3d.z, point3d1);
    }

    public boolean project2d_cam(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        return project2d_cam(point3d.x, point3d.y, point3d.z, point3d1);
    }

    private void shadowPairsClear()
    {
        shadowPairsList1.clear();
        shadowPairsMap1.clear();
        shadowPairsList2.clear();
        shadowPairsCur1 = null;
    }

    private void shadowPairsAdd(java.util.ArrayList arraylist)
    {
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            java.lang.Object obj = arraylist.get(j);
            if(!(obj instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (obj instanceof com.maddox.il2.objects.ships.TestRunway))
                continue;
            com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric = (com.maddox.il2.objects.ships.BigshipGeneric)obj;
            if(com.maddox.il2.engine.Actor.isValid(bigshipgeneric.getAirport()) && !shadowPairsMap1.containsKey(bigshipgeneric))
            {
                shadowPairsList1.add(bigshipgeneric);
                shadowPairsMap1.put(bigshipgeneric, null);
            }
        }

    }

    private void shadowPairsRender()
    {
        int i = shadowPairsList1.size();
        if(i == 0)
            return;
        for(int j = 0; j < i; j++)
        {
            shadowPairsCur1 = (com.maddox.il2.objects.ships.BigshipGeneric)shadowPairsList1.get(j);
            com.maddox.JGP.Point3d point3d = shadowPairsCur1.pos.getAbsPoint();
            double d = point3d.x - shadowPairsR;
            double d1 = point3d.y - shadowPairsR;
            double d2 = point3d.x + shadowPairsR;
            double d3 = point3d.y + shadowPairsR;
            com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, d, d1, d2, d3, 14, shadowPairsFilter);
        }

        if(shadowPairsList2.size() == 0)
        {
            return;
        } else
        {
            com.maddox.il2.engine.HierMesh.renderShadowPairs(shadowPairsList2);
            return;
        }
    }

    private void doPreRender3D(com.maddox.il2.engine.Render render)
    {
        render.useClearColor(!bDrawLand || (com.maddox.il2.engine.RenderContext.texGetFlags() & 0x20) != 0);
        render.getCamera().pos.getRender(__p, __o);
        if(!bRenderMirror && iRenderIndx == 0)
        {
            com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.lightPlanesBySearchlights();
            com.maddox.il2.engine.Actor actor = render.getCamera().pos.base();
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                actor.getSpeed(__v);
                com.maddox.il2.engine.Camera.SetTargetSpeed((float)__v.x, (float)__v.y, (float)__v.z);
            } else
            {
                com.maddox.il2.engine.Camera.SetTargetSpeed(0.0F, 0.0F, 0.0F);
            }
        }
        com.maddox.il2.engine.Render.enableFog(bEnableFog);
        if(bDrawClouds && clouds != null)
            clouds.preRender();
        if(bDrawLand)
            com.maddox.il2.engine.Engine.land().preRender((float)__p.z, false);
        darkerNight.preRender();
        com.maddox.il2.game.DrwArray drwarray = bRenderMirror ? drwMirror : drwMaster[iRenderIndx];
        com.maddox.il2.ai.World _tmp = com.maddox.il2.engine.Engine.cur.world;
        com.maddox.il2.engine.Engine.drawEnv().preRender(__p.x, __p.y, __p.z, com.maddox.il2.ai.World.MaxVisualDistance, 4, drwarray.drwSolid, drwarray.drwTransp, drwarray.drwShadow, true);
        com.maddox.il2.ai.World _tmp1 = com.maddox.il2.engine.Engine.cur.world;
        com.maddox.il2.engine.Engine.drawEnv().preRender(__p.x, __p.y, __p.z, com.maddox.il2.ai.World.MaxLongVisualDistance, 8, drwarray.drwSolid, drwarray.drwTransp, drwarray.drwShadow, false);
        if(!bRenderMirror)
        {
            shadowPairsAdd(drwarray.drwSolid);
            shadowPairsAdd(drwarray.drwTransp);
        }
        if(!bRenderMirror || viewMirror > 1)
        {
            com.maddox.il2.ai.World _tmp2 = com.maddox.il2.engine.Engine.cur.world;
            com.maddox.il2.engine.Engine.drawEnv().preRender(__p.x, __p.y, __p.z, com.maddox.il2.ai.World.MaxStaticVisualDistance, 2, drwarray.drwSolid, drwarray.drwTransp, drwarray.drwShadow, false);
            com.maddox.il2.ai.World _tmp3 = com.maddox.il2.engine.Engine.cur.world;
            com.maddox.il2.engine.Engine.drawEnv().preRender(__p.x, __p.y, __p.z, com.maddox.il2.ai.World.MaxPlateVisualDistance, 1, drwarray.drwSolidPlate, drwarray.drwTranspPlate, drwarray.drwShadowPlate, true);
        }
        com.maddox.il2.engine.BulletGeneric.preRenderAll();
        if(bEnableFog)
            com.maddox.il2.engine.Render.enableFog(false);
    }

    private void doRender3D0(com.maddox.il2.engine.Render render)
    {
        boolean flag = false;
        com.maddox.il2.engine.Render.enableFog(bEnableFog);
        if(bDrawLand)
        {
            com.maddox.il2.engine.Engine.lightEnv().prepareForRender(camera3D.pos.getAbsPoint(), 8000F);
            flag = com.maddox.il2.engine.Engine.land().render0(bRenderMirror) != 2;
            com.maddox.il2.engine.LightPoint.clearRender();
        }
        if(flag && bEnableFog)
            com.maddox.il2.engine.Render.enableFog(false);
        com.maddox.il2.game.DrwArray drwarray = bRenderMirror ? drwMirror : drwMaster[iRenderIndx];
        plateToRenderArray(drwarray.drwSolidPlate, drwarray.drwSolid);
        plateToRenderArray(drwarray.drwTranspPlate, drwarray.drwTransp);
        plateToRenderArray(drwarray.drwShadowPlate, drwarray.drwShadow);
        com.maddox.il2.engine.MeshShared.renderArray(true);
        render.drawShadow(drwarray.drwShadow);
        if(flag && bEnableFog)
            com.maddox.il2.engine.Render.enableFog(true);
        if(bDrawLand)
            com.maddox.il2.engine.Engine.land().render1(bRenderMirror);
        int i = com.maddox.opengl.gl.GetError();
        if(i != 0)
            java.lang.System.out.println("***( GL error: " + i + " (render3d0)");
    }

    private void doRender3D1(com.maddox.il2.engine.Render render)
    {
        if(bDrawClouds && clouds != null && com.maddox.il2.engine.RenderContext.cfgSky.get() > 0)
        {
            com.maddox.il2.engine.Engine.lightEnv().prepareForRender(camera3D.pos.getAbsPoint(), (float)com.maddox.il2.engine.RenderContext.cfgSky.get() * 4000F);
            com.maddox.il2.objects.vehicles.lights.SearchlightGeneric.lightCloudsBySearchlights();
            clouds.render();
            com.maddox.il2.engine.LightPoint.clearRender();
        }
        com.maddox.il2.game.DrwArray drwarray = bRenderMirror ? drwMirror : drwMaster[iRenderIndx];
        render.draw(drwarray.drwSolid, drwarray.drwTransp);
        if(!bRenderMirror)
            shadowPairsRender();
        com.maddox.il2.engine.BulletGeneric.renderAll();
        if(bEnableFog)
        {
            com.maddox.il2.engine.Render.flush();
            com.maddox.il2.engine.Render.enableFog(false);
        }
        darkerNight.render();
    }

    private void plateToRenderArray(java.util.ArrayList arraylist, java.util.ArrayList arraylist1)
    {
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(j);
            if(actor instanceof com.maddox.il2.engine.ActorLandMesh)
            {
                arraylist1.add(actor);
                continue;
            }
            if((actor instanceof com.maddox.il2.engine.ActorMesh) && (((com.maddox.il2.engine.ActorMesh)actor).mesh() instanceof com.maddox.il2.engine.MeshShared))
            {
                actor.pos.getRender(__l);
                if(!((com.maddox.il2.engine.MeshShared)((com.maddox.il2.engine.ActorMesh)actor).mesh()).putToRenderArray(__l))
                    actor.draw.render(actor);
            } else
            {
                actor.draw.render(actor);
            }
        }

        arraylist.clear();
    }

    public void _getAspectViewPort(int i, float af[])
    {
        af[0] = i != 1 ? 0.6666667F : 0.0F;
        af[1] = 0.0F;
        af[2] = 0.3333333F;
        af[3] = 1.0F;
    }

    public void _getAspectViewPort(int i, int ai[])
    {
        ai[0] = i != 1 ? (2 * com.maddox.il2.engine.RendersMain.width()) / 3 : 0;
        ai[1] = 0;
        ai[2] = com.maddox.il2.engine.RendersMain.width() / 3;
        ai[3] = com.maddox.il2.engine.RendersMain.height();
    }

    private void drawTime()
    {
        if(!hud.bDrawAllMessages)
            return;
        if(bShowTime || isDemoPlaying())
        {
            int i = com.maddox.il2.engine.TextScr.This().getViewPortWidth();
            long l = com.maddox.rts.Time.current();
            if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                l -= com.maddox.il2.net.NetMissionTrack.playingStartTime;
            int j = (int)((l / 1000L) % 60L);
            int k = (int)(l / 1000L / 60L);
            if(j > 9)
                com.maddox.il2.engine.TextScr.output(i - com.maddox.il2.engine.TextScr.font().height() * 3, 5, "" + k + ":" + j);
            else
                com.maddox.il2.engine.TextScr.output(i - com.maddox.il2.engine.TextScr.font().height() * 3, 5, "" + k + ":0" + j);
        }
    }

    public int mirrorX0()
    {
        return render3D0.getViewPortX0();
    }

    public int mirrorY0()
    {
        return 0;
    }

    public int mirrorWidth()
    {
        return 256;
    }

    public int mirrorHeight()
    {
        return 256;
    }

    public void preRenderHUD()
    {
    }

    public void renderHUD()
    {
    }

    public void renderHUDcontextResize(int i, int j)
    {
    }

    public void preRenderMap2D()
    {
    }

    public void renderMap2D()
    {
    }

    public void renderMap2DcontextResize(int i, int j)
    {
    }

    private void insertFarActorItem(int i, int j, int k, float f, java.lang.String s)
    {
        int l = (int)iconFarFont.width(s);
        for(int i1 = 0; i1 < iconFarListLen[iRenderIndx]; i1++)
        {
            com.maddox.il2.game.FarActorItem faractoritem = (com.maddox.il2.game.FarActorItem)iconFarList[iRenderIndx].get(i1);
            if(f > faractoritem.z)
            {
                if(iconFarList[iRenderIndx].size() == iconFarListLen[iRenderIndx])
                {
                    com.maddox.il2.game.FarActorItem faractoritem1 = new FarActorItem(i, j, k, l, f, s);
                    iconFarList[iRenderIndx].add(faractoritem1);
                } else
                {
                    com.maddox.il2.game.FarActorItem faractoritem2 = (com.maddox.il2.game.FarActorItem)iconFarList[iRenderIndx].get(iconFarListLen[iRenderIndx]);
                    faractoritem2.set(i, j, k, l, f, s);
                    iconFarList[iRenderIndx].remove(iconFarListLen[iRenderIndx]);
                    iconFarList[iRenderIndx].add(i1, faractoritem2);
                }
                iconFarListLen[iRenderIndx]++;
                return;
            }
        }

        if(iconFarList[iRenderIndx].size() == iconFarListLen[iRenderIndx])
        {
            com.maddox.il2.game.FarActorItem faractoritem3 = new FarActorItem(i, j, k, l, f, s);
            iconFarList[iRenderIndx].add(faractoritem3);
        } else
        {
            com.maddox.il2.game.FarActorItem faractoritem4 = (com.maddox.il2.game.FarActorItem)iconFarList[iRenderIndx].get(iconFarListLen[iRenderIndx]);
            faractoritem4.set(i, j, k, l, f, s);
        }
        iconFarListLen[iRenderIndx]++;
    }

    private void clipFarActorItems()
    {
        int i = iconFarFont.height();
        for(int j = 0; j < iconFarListLen[iRenderIndx]; j++)
        {
            com.maddox.il2.game.FarActorItem faractoritem = (com.maddox.il2.game.FarActorItem)iconFarList[iRenderIndx].get(j);
            for(int k = j + 1; k < iconFarListLen[iRenderIndx]; k++)
            {
                com.maddox.il2.game.FarActorItem faractoritem1 = (com.maddox.il2.game.FarActorItem)iconFarList[iRenderIndx].get(k);
                if(faractoritem1.x + faractoritem1.dx >= faractoritem.x && faractoritem1.x <= faractoritem.x + faractoritem.dx && faractoritem1.y + i >= faractoritem.y && faractoritem1.y <= faractoritem.y + i)
                {
                    iconFarList[iRenderIndx].remove(k);
                    iconFarList[iRenderIndx].add(faractoritem1);
                    k--;
                    iconFarListLen[iRenderIndx]--;
                }
            }

        }

    }

    private void clearFarActorItems()
    {
        iconFarListLen[iRenderIndx] = 0;
    }

    private boolean isBomb(com.maddox.il2.engine.Actor actor)
    {
        return (actor instanceof com.maddox.il2.objects.weapons.Bomb) || (actor instanceof com.maddox.il2.objects.weapons.Rocket);
    }

    protected void drawFarActors()
    {
        if(com.maddox.il2.game.Main.state() != null && com.maddox.il2.game.Main.state().id() == 18)
            return;
        if(iconFarMat == null)
            return;
        iconFarFontHeight = iconFarFont.height();
        iconClipX0 = -2D;
        iconClipY0 = -1D;
        if(bRenderMirror)
        {
            iconClipX1 = (float)render2DMirror.getViewPortWidth() + 2.0F;
            iconClipY1 = (float)render2DMirror.getViewPortHeight() + 1.0F;
        } else
        {
            iconClipX1 = (float)_render2D[iRenderIndx].getViewPortWidth() + 2.0F;
            iconClipY1 = (float)_render2D[iRenderIndx].getViewPortHeight() + 1.0F;
        }
        iconFarPlayerActor = com.maddox.il2.ai.World.getPlayerAircraft();
        iconFarViewActor = viewActor();
        iconFarPadlockItem.str = null;
        iconFarPadlockActor = getViewPadlockEnemy();
        _camera3D[iRenderIndx].pos.getRender(farActorFilter.camp);
        com.maddox.JGP.Point3d point3d = farActorFilter.camp;
        float f = com.maddox.il2.engine.Engine.lightEnv().sun().ToLight.z;
        if(f < 0.0F)
            f = 0.0F;
        float f2 = com.maddox.il2.engine.Engine.lightEnv().sun().Ambient + com.maddox.il2.engine.Engine.lightEnv().sun().Diffuze * (0.25F + 0.4F * f);
        if(com.maddox.il2.engine.RenderContext.cfgHardwareShaders.get() > 0)
        {
            f = com.maddox.il2.engine.Engine.lightEnv().sun().Ambient + f * com.maddox.il2.engine.Engine.lightEnv().sun().Diffuze;
            if(f > 1.0F)
                f2 *= f;
        }
        int j = (int)(127F * f2);
        if(j > 255)
            j = 255;
        iconFarColor = j | j << 8 | j << 16;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int k = list.size();
        for(int l = 0; l < k; l++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(l);
            com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
            double d3 = point3d.distance(point3d1);
            if(d3 < 25000D)
                farActorFilter.isUse(actor, d3);
        }

        iconFarPlayerActor = null;
        iconFarViewActor = null;
        iconFarPadlockActor = null;
        if(iconFarListLen[iRenderIndx] != 0)
        {
            clipFarActorItems();
            for(int i = 0; i < iconFarListLen[iRenderIndx]; i++)
            {
                com.maddox.il2.game.FarActorItem faractoritem = (com.maddox.il2.game.FarActorItem)iconFarList[iRenderIndx].get(i);
                if(bRenderMirror)
                {
                    transformMirror.set(faractoritem.x - faractoritem.dx, faractoritem.y, faractoritem.z, faractoritem.dx);
                    iconFarFont.transform(transformMirror, faractoritem.color, faractoritem.str);
                } else
                {
                    iconFarFont.output(faractoritem.color, faractoritem.x, faractoritem.y, faractoritem.z, faractoritem.str);
                }
            }

        }
        if(iconFarPadlockItem.str != null)
        {
            if(!iconFarPadlockItem.bGround)
            {
                iconFarPadlockItem.x++;
                iconFarPadlockItem.y += -7.5F;
                float f1 = 16F;
                line3XYZ[0] = (float)((double)iconFarPadlockItem.x - (double)f1 * 0.86599999999999999D);
                line3XYZ[1] = (float)((double)iconFarPadlockItem.y - (double)f1 * 0.5D);
                line3XYZ[2] = iconFarPadlockItem.z;
                line3XYZ[3] = (float)((double)iconFarPadlockItem.x + (double)f1 * 0.86599999999999999D);
                line3XYZ[4] = (float)((double)iconFarPadlockItem.y - (double)f1 * 0.5D);
                line3XYZ[5] = iconFarPadlockItem.z;
                line3XYZ[6] = iconFarPadlockItem.x;
                line3XYZ[7] = (float)iconFarPadlockItem.y + f1;
                line3XYZ[8] = iconFarPadlockItem.z;
            } else
            {
                camera3D.pos.getRender(_lineP, _lineO);
                double d = ((double)(-_lineO.getKren()) * 3.1415926535897931D) / 180D;
                double d1 = java.lang.Math.sin(d);
                double d2 = java.lang.Math.cos(d);
                iconFarPadlockItem.x++;
                iconFarPadlockItem.y += -7.5F;
                float f3 = 16F;
                line3XYZ[0] = iconFarPadlockItem.x;
                line3XYZ[1] = iconFarPadlockItem.y;
                line3XYZ[2] = iconFarPadlockItem.z;
                line3XYZ[3] = (float)((double)iconFarPadlockItem.x + d2 * (double)f3 * 0.25D + d1 * 1.5D * (double)f3);
                line3XYZ[4] = (float)(((double)iconFarPadlockItem.y - d1 * (double)f3 * 0.25D) + d2 * 1.5D * (double)f3);
                line3XYZ[5] = iconFarPadlockItem.z;
                line3XYZ[6] = (float)(((double)iconFarPadlockItem.x - d2 * (double)f3 * 0.25D) + d1 * 1.5D * (double)f3);
                line3XYZ[7] = (float)((double)iconFarPadlockItem.y + d1 * (double)f3 * 0.25D + d2 * 1.5D * (double)f3);
                line3XYZ[8] = iconFarPadlockItem.z;
            }
            com.maddox.il2.engine.Render.drawBeginLines(-1);
            com.maddox.il2.engine.Render.drawLines(line3XYZ, 3, 1.0F, iconFarPadlockItem.color, com.maddox.il2.engine.Mat.TESTZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 5);
            com.maddox.il2.engine.Render.drawEnd();
        }
        clearFarActorItems();
    }

    protected void drawFarActorsInit()
    {
        iconFarMat = com.maddox.il2.engine.Mat.New("icons/faractor.mat");
        iconFarFont = com.maddox.il2.engine.TTFont.get("arialSmallZ");
        iconFarFinger = com.maddox.rts.Finger.Int("iconFar_shortClassName");
    }

    public void initHotKeys()
    {
        com.maddox.rts.CmdEnv.top().setCommand(new CmdExit(), "exit", "exit game");
        com.maddox.rts.HotKeyCmdEnv.setCurrentEnv("hotkeys");
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "exit") {

            public void begin()
            {
                com.maddox.il2.game.Main.doGameExit();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ScreenShot") {

            public void begin()
            {
                if(scrShot == null)
                    scrShot = new ScrShot("grab");
                if(com.maddox.il2.game.Mission.isNet())
                {
                    long l = com.maddox.rts.Time.real();
                    if(lastTimeScreenShot + 10000L < l)
                        lastTimeScreenShot = l;
                    else
                        return;
                }
                scrShot.grab();
            }

        }
);
        com.maddox.rts.CmdEnv.top().setCommand(new CmdScreenSequence(), "avi", "start/stop save screen shot sequence");
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "ScreenSequence") {

            public void begin()
            {
                if(screenSequence == null)
                    screenSequence = new ScreenSequence();
                screenSequence.doSave();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "land") {

            public void begin()
            {
                if(com.maddox.rts.RTSConf.cur.console.getEnv().levelAccess() == 0)
                    setDrawLand(!isDrawLand());
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "clouds") {

            public void begin()
            {
                if(com.maddox.il2.game.Mission.isSingle() && com.maddox.rts.RTSConf.cur.console.getEnv().levelAccess() == 0)
                    bDrawClouds = !bDrawClouds;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "showTime") {

            public void begin()
            {
                if(com.maddox.rts.RTSConf.cur.console.getEnv().levelAccess() == 0)
                    bShowTime = !bShowTime;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(new com.maddox.rts.HotKeyCmd(true, "pause") {

            public void begin()
            {
                if(com.maddox.il2.game.TimeSkip.isDo())
                    return;
                if(com.maddox.rts.Time.isEnableChangePause())
                {
                    com.maddox.rts.Time.setPause(!com.maddox.rts.Time.isPaused());
                    if(com.maddox.il2.engine.Config.cur.isSoundUse())
                        if(com.maddox.rts.Time.isPaused())
                            com.maddox.sound.AudioDevice.soundsOff();
                        else
                            com.maddox.sound.AudioDevice.soundsOn();
                }
            }

        }
);
    }

    public static float FOVX = 70F;
    public static final float ZNEAR = 1.2F;
    public static final float ZFAR = 48000F;
    protected boolean bDrawIfNotFocused;
    protected boolean bUseStartLog;
    protected boolean bUseGUI;
    private boolean bShowStartIntro;
    public com.maddox.il2.engine.GUIWindowManager guiManager;
    public com.maddox.rts.KeyRecord keyRecord;
    public com.maddox.il2.game.order.OrdersTree ordersTree;
    public com.maddox.il2.game.TimeSkip timeSkip;
    public com.maddox.il2.engine.hotkey.HookView hookView;
    public com.maddox.il2.engine.hotkey.HookViewFly hookViewFly;
    public com.maddox.il2.engine.hotkey.HookViewEnemy hookViewEnemy;
    public com.maddox.il2.game.AircraftHotKeys aircraftHotKeys;
    public com.maddox.il2.objects.air.Cockpit cockpits[];
    public com.maddox.il2.objects.air.Cockpit cockpitCur;
    public com.maddox.il2.objects.effects.OverLoad overLoad;
    public com.maddox.il2.objects.effects.OverLoad _overLoad[];
    public com.maddox.il2.objects.effects.SunGlare sunGlare;
    public com.maddox.il2.objects.effects.SunGlare _sunGlare[];
    public com.maddox.il2.objects.effects.LightsGlare lightsGlare;
    public com.maddox.il2.objects.effects.LightsGlare _lightsGlare[];
    private com.maddox.il2.objects.effects.SunFlare _sunFlare[];
    public com.maddox.il2.engine.Render _sunFlareRender[];
    private boolean bViewFly;
    private boolean bViewEnemy;
    public boolean bEnableFog;
    private boolean bDrawLand;
    public com.maddox.il2.engine.EffClouds clouds;
    public com.maddox.il2.objects.effects.Zip zip;
    public boolean bDrawClouds;
    public com.maddox.il2.objects.effects.SpritesFog spritesFog;
    public com.maddox.il2.objects.effects.Cinema _cinema[];
    public com.maddox.il2.game.Render3D0R render3D0R;
    public com.maddox.il2.game.Camera3DR camera3DR;
    public com.maddox.il2.game.Render3D0 render3D0;
    public com.maddox.il2.game.Render3D1 render3D1;
    public com.maddox.il2.engine.Camera3D camera3D;
    public com.maddox.il2.game.Render3D0 _render3D0[];
    public com.maddox.il2.game.Render3D1 _render3D1[];
    public com.maddox.il2.engine.Camera3D _camera3D[];
    public com.maddox.il2.game.Render2D render2D;
    public com.maddox.il2.engine.CameraOrtho2D camera2D;
    public com.maddox.il2.game.Render2D _render2D[];
    public com.maddox.il2.engine.CameraOrtho2D _camera2D[];
    public com.maddox.il2.game.Render3D0Mirror render3D0Mirror;
    public com.maddox.il2.game.Render3D1Mirror render3D1Mirror;
    public com.maddox.il2.engine.Camera3D camera3DMirror;
    public com.maddox.il2.game.Render2DMirror render2DMirror;
    public com.maddox.il2.engine.CameraOrtho2D camera2DMirror;
    public com.maddox.il2.game.RenderCockpit renderCockpit;
    public com.maddox.il2.engine.Camera3D cameraCockpit;
    public com.maddox.il2.game.RenderCockpit _renderCockpit[];
    public com.maddox.il2.engine.Camera3D _cameraCockpit[];
    public com.maddox.il2.game.RenderCockpitMirror renderCockpitMirror;
    public com.maddox.il2.engine.Camera3D cameraCockpitMirror;
    public com.maddox.il2.game.RenderHUD renderHUD;
    public com.maddox.il2.engine.CameraOrtho2D cameraHUD;
    public com.maddox.il2.game.HUD hud;
    public com.maddox.il2.game.RenderMap2D renderMap2D;
    public com.maddox.il2.engine.CameraOrtho2D cameraMap2D;
    public com.maddox.il2.engine.Land2D land2D;
    public com.maddox.il2.engine.Land2DText land2DText;
    public com.maddox.il2.objects.effects.DarkerNight darkerNight;
    private static java.lang.String _sLastMusic = "ru";
    protected int viewMirror;
    private int iconTypes;
    public static final java.lang.String gameHotKeyCmdEnvs[] = {
        "Console", "hotkeys", "HookView", "PanView", "SnapView", "pilot", "move", "gunner", "misc", "orders", 
        "aircraftView", "timeCompression", "gui"
    };
    public static final java.lang.String builderHotKeyCmdEnvs[] = {
        "Console", "builder", "hotkeys"
    };
    com.maddox.rts.KeyRecordCallback playRecordedMissionCallback;
    java.lang.String playRecordedFile;
    int playBatchCurRecord;
    boolean playEndBatch;
    com.maddox.rts.SectFile playRecordedSect;
    int playRecorderIndx;
    java.lang.String playRecordedPlayFile;
    com.maddox.rts.InOutStreams playRecordedStreams;
    com.maddox.rts.NetChannelInStream playRecordedNetChannelIn;
    com.maddox.il2.game.GameTrack gameTrackRecord;
    com.maddox.il2.game.GameTrack gameTrackPlay;
    private boolean bLoadRecordedStates1Before;
    private boolean bRenderMirror;
    private int iRenderIndx;
    protected double _modelMatrix3D[][];
    protected double _projMatrix3D[][];
    protected int _viewport[][];
    protected double _modelMatrix3DMirror[];
    protected double _projMatrix3DMirror[];
    protected int _viewportMirror[];
    private double _dIn[];
    private double _dOut[];
    private static double shadowPairsR;
    private static double shadowPairsR2;
    private java.util.ArrayList shadowPairsList1;
    private java.util.HashMap shadowPairsMap1;
    private com.maddox.il2.objects.ships.BigshipGeneric shadowPairsCur1;
    private java.util.ArrayList shadowPairsList2;
    private com.maddox.il2.engine.ActorFilter shadowPairsFilter;
    private com.maddox.il2.game.DrwArray drwMaster[] = {
        new DrwArray(), new DrwArray(), new DrwArray()
    };
    private com.maddox.il2.game.DrwArray drwMirror;
    private com.maddox.il2.engine.Loc __l;
    private com.maddox.JGP.Point3d __p;
    private com.maddox.il2.engine.Orient __o;
    private com.maddox.JGP.Vector3d __v;
    private boolean bShowTime;
    public static final java.lang.String ICONFAR_PROPERTY = "iconFar_shortClassName";
    public static final float iconFarActorSizeX = 2F;
    public static final float iconFarActorSizeY = 1F;
    public static final float iconFarSmallActorSize = 1F;
    public static int iconFarColor = 0x7f7f7f;
    protected double iconGroundDrawMin;
    protected double iconSmallDrawMin;
    protected double iconAirDrawMin;
    protected double iconDrawMax;
    private com.maddox.il2.engine.Mat iconFarMat;
    private com.maddox.il2.engine.TTFont iconFarFont;
    private int iconFarFinger;
    private float iconFarFontHeight;
    private double iconClipX0;
    private double iconClipX1;
    private double iconClipY0;
    private double iconClipY1;
    private com.maddox.il2.engine.Actor iconFarPlayerActor;
    private com.maddox.il2.engine.Actor iconFarViewActor;
    private com.maddox.il2.engine.Actor iconFarPadlockActor;
    private com.maddox.il2.game.FarActorItem iconFarPadlockItem;
    private java.util.ArrayList iconFarList[] = {
        new ArrayList(), new ArrayList(), new ArrayList()
    };
    private int iconFarListLen[] = {
        0, 0, 0
    };
    private com.maddox.il2.game.FarActorFilter farActorFilter;
    private float line3XYZ[];
    private com.maddox.JGP.Point3d _lineP;
    private com.maddox.il2.engine.Orient _lineO;
    private com.maddox.il2.game.TransformMirror transformMirror;
    private long lastTimeScreenShot;
    private com.maddox.opengl.util.ScrShot scrShot;
    private com.maddox.il2.game.ScreenSequence screenSequence;

    static 
    {
        shadowPairsR = 1000D;
        shadowPairsR2 = shadowPairsR * shadowPairsR;
    }


































}
