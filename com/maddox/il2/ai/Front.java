// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Front.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.rts.NetEnv;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai:
//            World, EventLog, Army

public class Front
{
    public static class Marker
    {

        public double x;
        public double y;
        public int army;
        public int _armyMask;
        public double _d2;
        private double _x;
        private double _y;





        public Marker()
        {
            _x = 0.0D;
            _y = 0.0D;
        }
    }


    public Front()
    {
        allMarkers = new ArrayList();
        markers = new ArrayList();
        bExistFront = false;
        frontMat = new com.maddox.il2.engine.Mat[4];
        tilesChanged = false;
        prevNCountMarkers = 0;
        prevCamLeft = 0.0F;
        prevCamBottom = 0.0F;
        prevCamWorldScale = 1.0D;
        prevCamWorldXOffset = 0.0D;
        prevCamWorldYOffset = 0.0D;
        bTilesUpdated = false;
        pointLand0 = new Point3d();
        pointLand1 = new Point3d();
    }

    public static java.util.List markers()
    {
        return com.maddox.il2.ai.World.cur().front.allMarkers;
    }

    public static void checkAllCaptured()
    {
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            if(actor instanceof com.maddox.il2.objects.air.Paratrooper)
            {
                com.maddox.il2.objects.air.Paratrooper paratrooper = (com.maddox.il2.objects.air.Paratrooper)actor;
                if(!paratrooper.isChecksCaptured())
                    paratrooper.checkCaptured();
            } else
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && !actor.isNetMirror())
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
                com.maddox.il2.ai.Front.checkAircraftCaptured(aircraft);
            }
        }

    }

    public static void checkAircraftCaptured(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            if(aircraft.FM.isOk())
                return;
            if(!com.maddox.il2.ai.Front.isCaptured(aircraft))
                return;
            for(int i = 0; i < aircraft.FM.crew; i++)
                if(!aircraft.FM.AS.isPilotDead(i) && !aircraft.FM.AS.isPilotParatrooper(i))
                {
                    com.maddox.il2.ai.EventLog.onCaptured(aircraft, i);
                    if(com.maddox.il2.engine.Config.isUSE_RENDER() && aircraft == com.maddox.il2.ai.World.getPlayerAircraft() && i == com.maddox.il2.game.Main3D.cur3D().cockpitCur.astatePilotIndx())
                    {
                        com.maddox.il2.ai.World.setPlayerCaptured();
                        if(com.maddox.il2.engine.Config.isUSE_RENDER())
                            com.maddox.il2.game.HUD.log("PlayerCAPT");
                        if(com.maddox.il2.game.Mission.isNet())
                            com.maddox.il2.net.Chat.sendLog(1, "gore_captured", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), null);
                    }
                }

        }
    }

    public static boolean isCaptured(com.maddox.il2.engine.Actor actor)
    {
        if(actor == null || actor.pos == null)
            return false;
        else
            return com.maddox.il2.ai.Front.isCaptured(actor.getArmy(), actor.pos.getAbsPoint().x, actor.pos.getAbsPoint().y);
    }

    public static boolean isCaptured(int i, double d, double d1)
    {
        if(i == 0)
            return false;
        int j = com.maddox.il2.ai.Front.army(d, d1);
        if(j == 0)
            return false;
        if(i == j)
            return false;
        java.util.List list = com.maddox.il2.ai.Front.markers();
        int l = list.size();
        com.maddox.il2.ai.Marker marker = null;
        double d2 = 90000000000D;
        for(int i1 = 0; i1 < l; i1++)
        {
            com.maddox.il2.ai.Marker marker1 = (com.maddox.il2.ai.Marker)list.get(i1);
            if(i == marker1.army)
            {
                double d4 = (marker1.x - d) * (marker1.x - d) + (marker1.y - d1) * (marker1.y - d1);
                if(d2 > d4)
                {
                    d2 = d4;
                    marker = marker1;
                }
            }
        }

        if(marker == null)
            return true;
        double d3 = marker.x;
        double d5 = marker.y;
        double d6 = d;
        double d7 = d1;
        do
        {
            double d8 = (d3 - d6) * (d3 - d6) + (d5 - d7) * (d5 - d7);
            if(d8 <= 1000000D)
                break;
            double d9 = (d3 + d6) * 0.5D;
            double d11 = (d5 + d7) * 0.5D;
            int k = com.maddox.il2.ai.Front.army(d9, d11);
            if(k == i)
            {
                d3 = d9;
                d5 = d11;
            } else
            {
                d6 = d9;
                d7 = d11;
            }
        } while(true);
        d3 = (d3 + d6) * 0.5D;
        d5 = (d5 + d7) * 0.5D;
        d2 = (d3 - d) * (d3 - d) + (d5 - d1) * (d5 - d1);
        if(d2 >= 2500000000D)
            return true;
        java.util.List list1 = com.maddox.il2.engine.Engine.targets();
        l = list1.size();
        for(int j1 = 0; j1 < l; j1++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list1.get(j1);
            if(!(actor instanceof com.maddox.il2.objects.air.Aircraft) && i != actor.getArmy())
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                double d12 = (point3d.x - d) * (point3d.x - d) + (point3d.y - d1) * (point3d.y - d1);
                if(d12 < 1000000D)
                    return true;
            }
        }

        double d10 = java.lang.Math.sqrt(d2) / 50000D;
        double d13 = d + d1;
        if(d13 < 0.0D)
            d13 = -d13;
        d13 -= (int)d13;
        return 0.59999999999999998D + d10 * 0.5D + (d13 - 0.5D) >= 0.5D;
    }

    public static int army(double d, double d1)
    {
        return com.maddox.il2.ai.World.cur().front._army(d, d1);
    }

    public int _army(double d, double d1)
    {
        int i = allMarkers.size();
        if(i == 0)
            return 0;
        int j = 0;
        double d2 = 90000000000D;
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.ai.Marker marker = (com.maddox.il2.ai.Marker)allMarkers.get(k);
            double d3 = (marker.x - d) * (marker.x - d) + (marker.y - d1) * (marker.y - d1);
            if(d2 > d3)
            {
                j = marker.army;
                d2 = d3;
            }
        }

        return j;
    }

    public void resetGameClear()
    {
        allMarkers.clear();
        tilesChanged = true;
    }

    public void resetGameCreate()
    {
    }

    public static void loadMission(com.maddox.rts.SectFile sectfile)
    {
        com.maddox.il2.ai.World.cur().front._loadMission(sectfile);
    }

    public void _loadMission(com.maddox.rts.SectFile sectfile)
    {
        allMarkers.clear();
        int i = sectfile.sectionIndex("FrontMarker");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                java.lang.String s = numbertokenizer.next((java.lang.String)null);
                if(s != null && s.startsWith("FrontMarker"))
                {
                    double d = numbertokenizer.next(0.0D);
                    double d1 = numbertokenizer.next(0.0D);
                    int l = numbertokenizer.next(1, 1, com.maddox.il2.ai.Army.amountNet() - 1);
                    if(l <= com.maddox.il2.ai.Army.amountSingle() - 1 || com.maddox.il2.game.Mission.isDogfight())
                    {
                        com.maddox.il2.ai.Marker marker = new Marker();
                        marker.x = d;
                        marker.y = d1;
                        marker.army = l;
                        marker._armyMask = 1 << l - 1;
                        allMarkers.add(marker);
                    }
                }
            }

        }
        tilesChanged = true;
    }

    public static void preRender(boolean flag)
    {
        com.maddox.il2.ai.World.cur().front._preRender(flag);
    }

    public static void render(boolean flag)
    {
        com.maddox.il2.ai.World.cur().front._render(flag);
    }

    public static void setMarkersChanged()
    {
        com.maddox.il2.ai.World.cur().front._setMarkersChanged();
    }

    public void _setMarkersChanged()
    {
        tilesChanged = true;
    }

    private boolean isOneArmy(int i)
    {
        int j = com.maddox.il2.ai.Army.amountNet() - 1;
        int k = 0;
        for(int l = 0; l < j; l++)
            if((i & 1 << l) != 0 && ++k > 1)
                return false;

        return true;
    }

    private void tilesUpdate()
    {
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        int i = (int)(cameraortho2d.right - cameraortho2d.left) / 16 + 5;
        int j = (int)(cameraortho2d.top - cameraortho2d.bottom) / 16 + 5;
        if(i != tNx || j != tNy)
        {
            tNx = i;
            tNy = j;
            mask = new byte[i * j];
            mask2 = new byte[i * j];
        }
        int k = allMarkers.size();
        if(k < 2)
        {
            bExistFront = false;
            return;
        }
        int l = 0;
        for(int j1 = 0; j1 < k; j1++)
        {
            com.maddox.il2.ai.Marker marker = (com.maddox.il2.ai.Marker)allMarkers.get(j1);
            l |= marker._armyMask;
        }

        if(isOneArmy(l))
        {
            bExistFront = false;
            return;
        }
        double d = 16D / cameraortho2d.worldScale;
        long l1 = (long)(cameraortho2d.worldXOffset / d) - 1L;
        camWorldXOffset = (double)l1 * d;
        camLeft = (float)((double)cameraortho2d.left - (cameraortho2d.worldXOffset - camWorldXOffset) * cameraortho2d.worldScale);
        l1 = (long)(cameraortho2d.worldYOffset / d) - 1L;
        camWorldYOffset = (double)l1 * d;
        camBottom = (float)((double)cameraortho2d.bottom - (cameraortho2d.worldYOffset - camWorldYOffset) * cameraortho2d.worldScale);
        double d1 = cameraortho2d.worldYOffset + ((double)(cameraortho2d.right - cameraortho2d.left) / cameraortho2d.worldScale) * 0.5D;
        double d2 = cameraortho2d.worldXOffset + ((double)(cameraortho2d.top - cameraortho2d.bottom) / cameraortho2d.worldScale) * 0.5D;
        double d3 = 90000000000D;
        for(int k1 = 0; k1 < k; k1++)
        {
            com.maddox.il2.ai.Marker marker1 = (com.maddox.il2.ai.Marker)allMarkers.get(k1);
            double d4 = marker1.x - d2;
            double d6 = marker1.y - d1;
            marker1._d2 = d4 * d4 + d6 * d6;
            if(d3 > marker1._d2)
                d3 = marker1._d2;
        }

        d3 = java.lang.Math.sqrt(d3);
        d3 += (double)((cameraortho2d.right - cameraortho2d.left) + (cameraortho2d.top - cameraortho2d.bottom)) / cameraortho2d.worldScale;
        d3 *= d3;
        l = 0;
        for(int i2 = 0; i2 < k; i2++)
        {
            com.maddox.il2.ai.Marker marker2 = (com.maddox.il2.ai.Marker)allMarkers.get(i2);
            if(d3 > marker2._d2)
            {
                markers.add(marker2);
                l |= marker2._armyMask;
            }
        }

        if(markers.size() < 2 || isOneArmy(l))
        {
            markers.clear();
            bExistFront = false;
            return;
        }
        k = markers.size();
        double d5 = camWorldYOffset - 0.5D * d;
        double d7 = camWorldXOffset - 0.5D * d;
        int j2 = 0;
        int k2 = 0;
        l = 0;
        for(int l2 = 0; l2 < j; l2++)
        {
            double d8 = d5 + (double)l2 * d;
            for(int l3 = 0; l3 < i; l3++)
            {
                double d9 = d7 + (double)l3 * d;
                double d10 = d3;
                for(int i5 = 0; i5 < k; i5++)
                {
                    com.maddox.il2.ai.Marker marker3 = (com.maddox.il2.ai.Marker)markers.get(i5);
                    double d11 = (marker3.x - d9) * (marker3.x - d9) + (marker3.y - d8) * (marker3.y - d8);
                    if(d10 > d11)
                    {
                        d10 = d11;
                        j2 = marker3.army;
                        k2 = marker3._armyMask;
                    }
                }

                mask[i * l2 + l3] = (byte)j2;
                l |= k2;
            }

        }

        markers.clear();
        if(isOneArmy(l))
        {
            bExistFront = false;
            return;
        }
        for(int i3 = 1; i3 < j - 1; i3++)
        {
            for(int j3 = 1; j3 < i - 1; j3++)
            {
                int i4 = i3 * i + j3;
                int i1 = mask[i4];
                if(i1 == mask[i4 - 1] && i1 == mask[i4 + 1] && i1 == mask[i4 - i] && i1 == mask[i4 + i])
                    mask2[i4] = 0;
                else
                    mask2[i4] = (byte)i1;
            }

        }

        for(int k3 = 1; k3 < j - 1; k3++)
        {
            for(int j4 = 1; j4 < i - 1; j4++)
            {
                int k4 = k3 * i + j4;
                byte byte0 = mask2[k4];
                if(byte0 == 0)
                {
                    mask[k4] = 0;
                } else
                {
                    int l4 = 0;
                    if(mask2[k4 + 1] == byte0)
                    {
                        if(mask2[k4 + i] != mask2[(k4 - i) + 1] && mask2[k4 - i] != mask2[k4 + i + 1])
                            l4 |= 1;
                    } else
                    if(mask2[k4 + i + 1] == byte0 && mask2[k4 + i] != byte0 && mask2[k4 + i] != mask2[k4 + 1])
                        l4 |= 2;
                    if(mask2[k4 + i] == byte0)
                    {
                        if(mask2[k4 - 1] != mask2[k4 + i + 1] && mask2[(k4 + i) - 1] != mask2[k4 + 1])
                            l4 |= 4;
                    } else
                    if(mask2[(k4 + i) - 1] == byte0 && mask2[k4 - 1] != byte0 && mask2[k4 - 1] != mask2[k4 + i])
                        l4 |= 8;
                    mask[k4] = (byte)l4;
                }
            }

        }

        bExistFront = true;
    }

    public void _preRender(boolean flag)
    {
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        if(!tilesChanged)
            tilesChanged = prevNCountMarkers != allMarkers.size() || prevCamLeft != cameraortho2d.left || prevCamBottom != cameraortho2d.bottom || prevCamWorldScale != cameraortho2d.worldScale || prevCamWorldXOffset != cameraortho2d.worldXOffset || prevCamWorldYOffset != cameraortho2d.worldYOffset;
        if(!tilesChanged && flag)
        {
            for(int i = 0; i < prevNCountMarkers; i++)
            {
                com.maddox.il2.ai.Marker marker = (com.maddox.il2.ai.Marker)allMarkers.get(i);
                if(marker.x == marker._x && marker.y == marker._y)
                    continue;
                tilesChanged = true;
                break;
            }

        }
        if(tilesChanged)
        {
            tilesUpdate();
            prevNCountMarkers = allMarkers.size();
            prevCamLeft = cameraortho2d.left;
            prevCamBottom = cameraortho2d.bottom;
            prevCamWorldScale = cameraortho2d.worldScale;
            prevCamWorldXOffset = cameraortho2d.worldXOffset;
            prevCamWorldYOffset = cameraortho2d.worldYOffset;
            for(int j = 0; j < prevNCountMarkers; j++)
            {
                com.maddox.il2.ai.Marker marker1 = (com.maddox.il2.ai.Marker)allMarkers.get(j);
                marker1._x = marker1.x;
                marker1._y = marker1.y;
            }

            tilesChanged = false;
            bTilesUpdated = true;
        } else
        {
            bTilesUpdated = false;
        }
        if(bExistFront && frontMat[0] == null)
        {
            frontMat[0] = com.maddox.il2.engine.Mat.New("icons/front1.mat");
            frontMat[1] = com.maddox.il2.engine.Mat.New("icons/front2.mat");
            frontMat[2] = com.maddox.il2.engine.Mat.New("icons/front3.mat");
            frontMat[3] = com.maddox.il2.engine.Mat.New("icons/front4.mat");
        }
    }

    public static boolean isMarkersUpdated()
    {
        return com.maddox.il2.ai.World.cur().front.bTilesUpdated;
    }

    private void drawTile3D(float f, float f1, com.maddox.il2.engine.Mat mat, int i)
    {
        double d = camWorldXOffset + (double)f * tStep;
        double d1 = camWorldYOffset + (double)f1 * tStep;
        double d2 = com.maddox.il2.engine.Landscape.HQ((float)d, (float)d1);
        main.project2d(d, d1, d2, pointLand0);
        d += 32D * tStep;
        d1 += 32D * tStep;
        d2 = com.maddox.il2.engine.Landscape.HQ((float)d, (float)d1);
        main.project2d(d, d1, d2, pointLand1);
        com.maddox.il2.engine.Render.drawTile((float)pointLand0.x, (float)pointLand0.y, (float)(pointLand1.x - pointLand0.x), (float)(pointLand1.y - pointLand0.y), 0.0F, mat, i, 0.0F, 0.0F, 1.0F, 1.0F);
    }

    public void _render(boolean flag)
    {
        if(bExistFront)
        {
            int i = tNx;
            int j = tNy;
            if(flag)
            {
                main = com.maddox.il2.game.Main3D.cur3D();
                tStep = 1.0D / prevCamWorldScale;
                for(int k = 1; k < j - 1; k++)
                {
                    int i1 = 16 * (k - 1);
                    for(int k1 = 1; k1 < i - 1; k1++)
                    {
                        int i2 = k * i + k1;
                        byte byte0 = mask[i2];
                        if(byte0 != 0)
                        {
                            int k2 = com.maddox.il2.ai.Army.color(mask2[i2]) | 0xff000000;
                            int i3 = 16 * (k1 - 1);
                            if((byte0 & 1) != 0)
                                drawTile3D(i3, i1, frontMat[0], k2);
                            else
                            if((byte0 & 2) != 0)
                                drawTile3D(i3, i1, frontMat[1], k2);
                            if((byte0 & 4) != 0)
                                drawTile3D(i3, i1, frontMat[2], k2);
                            else
                            if((byte0 & 8) != 0)
                                drawTile3D(i3 - 16, i1, frontMat[3], k2);
                        }
                    }

                }

            } else
            {
                for(int l = 1; l < j - 1; l++)
                {
                    int j1 = 16 * (l - 1);
                    for(int l1 = 1; l1 < i - 1; l1++)
                    {
                        int j2 = l * i + l1;
                        byte byte1 = mask[j2];
                        if(byte1 != 0)
                        {
                            int l2 = com.maddox.il2.ai.Army.color(mask2[j2]) | 0xff000000;
                            int j3 = 16 * (l1 - 1);
                            if((byte1 & 1) != 0)
                                com.maddox.il2.engine.Render.drawTile(camLeft + (float)j3, camBottom + (float)j1, 32F, 32F, 0.0F, frontMat[0], l2, 0.0F, 0.0F, 1.0F, 1.0F);
                            else
                            if((byte1 & 2) != 0)
                                com.maddox.il2.engine.Render.drawTile(camLeft + (float)j3, camBottom + (float)j1, 32F, 32F, 0.0F, frontMat[1], l2, 0.0F, 0.0F, 1.0F, 1.0F);
                            if((byte1 & 4) != 0)
                                com.maddox.il2.engine.Render.drawTile(camLeft + (float)j3, camBottom + (float)j1, 32F, 32F, 0.0F, frontMat[2], l2, 0.0F, 0.0F, 1.0F, 1.0F);
                            else
                            if((byte1 & 8) != 0)
                                com.maddox.il2.engine.Render.drawTile((camLeft + (float)j3) - 16F, camBottom + (float)j1, 32F, 32F, 0.0F, frontMat[3], l2, 0.0F, 0.0F, 1.0F, 1.0F);
                        }
                    }

                }

            }
        }
    }

    private java.util.ArrayList allMarkers;
    private static final double dMin = 1000D;
    private static final double dMax = 50000D;
    private static final double d2Min = 1000000D;
    private static final double d2Max = 2500000000D;
    public static final int M = 16;
    private java.util.ArrayList markers;
    private int tNx;
    private int tNy;
    private double camWorldXOffset;
    private double camWorldYOffset;
    private float camLeft;
    private float camBottom;
    private byte mask[];
    private byte mask2[];
    private boolean bExistFront;
    private com.maddox.il2.engine.Mat frontMat[];
    private boolean tilesChanged;
    private int prevNCountMarkers;
    private float prevCamLeft;
    private float prevCamBottom;
    private double prevCamWorldScale;
    private double prevCamWorldXOffset;
    private double prevCamWorldYOffset;
    private boolean bTilesUpdated;
    private com.maddox.il2.game.Main3D main;
    private double tStep;
    private com.maddox.JGP.Point3d pointLand0;
    private com.maddox.JGP.Point3d pointLand1;
}
