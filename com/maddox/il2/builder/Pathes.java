// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Pathes.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.rts.Message;
import com.maddox.rts.Property;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.builder:
//            Path, PPoint, PNodes, PAirdrome, 
//            PathAir, PathChief, PAir, Plugin, 
//            Builder, BldConfig

public class Pathes extends com.maddox.il2.engine.Actor
{

    public void renderMap2D(boolean flag, int i)
    {
        com.maddox.il2.engine.Render.drawBeginLines(-1);
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        com.maddox.il2.engine.IconDraw.setScrSize(i, i);
        double d = cameraortho2d.left - (float)(i / 2);
        double d1 = cameraortho2d.bottom - (float)(i / 2);
        double d2 = cameraortho2d.right + (float)(i / 2);
        double d3 = cameraortho2d.top + (float)(i / 2);
        int j = 0;
        int k = 0;
        int i1 = 1;
        pathes = getOwnerAttached(pathes);
        for(int j1 = 0; j1 < pathes.length; j1++)
        {
            com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)pathes[j1];
            pathes[j1] = null;
            if(path == null)
                break;
            path.renderPoints = 0;
            if(path.isDrawing())
            {
                points = path.getOwnerAttached(points);
                com.maddox.il2.builder.PPoint ppoint = null;
                boolean flag1 = false;
                if(lineNXYZ.length / 3 <= points.length)
                    lineNXYZ = new float[(points.length + 1) * 3];
                lineNCounter = 0;
                for(int i3 = 0; i3 < points.length; i3++)
                {
                    com.maddox.il2.builder.PPoint ppoint3 = (com.maddox.il2.builder.PPoint)points[i3];
                    points[i3] = null;
                    if(ppoint3 == null)
                        break;
                    com.maddox.il2.builder.Plugin.builder.project2d(ppoint3.pos.getCurrentPoint(), p2d);
                    ppoint3.screenX = p2d.x;
                    ppoint3.screenY = p2d.y;
                    if(ppoint3.screenX < d)
                    {
                        if(ppoint3.screenY < d1)
                            ppoint3.screenQuad = 6;
                        else
                        if(ppoint3.screenY > d3)
                            ppoint3.screenQuad = 0;
                        else
                            ppoint3.screenQuad = 7;
                    } else
                    if(ppoint3.screenX > d2)
                    {
                        if(ppoint3.screenY < d1)
                            ppoint3.screenQuad = 4;
                        else
                        if(ppoint3.screenY > d3)
                            ppoint3.screenQuad = 2;
                        else
                            ppoint3.screenQuad = 3;
                    } else
                    if(ppoint3.screenY < d1)
                        ppoint3.screenQuad = 5;
                    else
                    if(ppoint3.screenY > d3)
                        ppoint3.screenQuad = 1;
                    else
                        ppoint3.screenQuad = 8;
                    if(ppoint3 instanceof com.maddox.il2.builder.PNodes)
                    {
                        com.maddox.il2.builder.PNodes pnodes = (com.maddox.il2.builder.PNodes)ppoint3;
                        if(pnodes.posXYZ != null)
                        {
                            boolean flag4 = currentPPoint != null && currentPPoint.getOwner() == path;
                            if(lineNXYZ.length / 3 <= pnodes.posXYZ.length / 4)
                                lineNXYZ = new float[(pnodes.posXYZ.length / 4 + 1) * 3];
                            lineNCounter = 0;
                            for(int i4 = 0; i4 < pnodes.posXYZ.length / 4; i4++)
                            {
                                com.maddox.il2.builder.Plugin.builder.project2d(pnodes.posXYZ[i4 * 4 + 0], pnodes.posXYZ[i4 * 4 + 1], pnodes.posXYZ[i4 * 4 + 2], p2d);
                                lineNXYZ[lineNCounter * 3 + 0] = (float)p2d.x;
                                lineNXYZ[lineNCounter * 3 + 1] = (float)p2d.y;
                                lineNXYZ[lineNCounter * 3 + 2] = 0.0F;
                                lineNCounter++;
                            }

                            if(flag4)
                            {
                                k = com.maddox.il2.builder.Builder.colorSelected();
                                i1 = 3;
                            } else
                            {
                                k = com.maddox.il2.ai.Army.color(path.getArmy());
                                i1 = 1;
                            }
                            com.maddox.il2.engine.Render.drawLines(lineNXYZ, lineNCounter, i1, k, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
                        }
                    } else
                    if(ppoint != null)
                    {
                        boolean flag3;
                        for(flag3 = ppoint3.screenQuad == 8 || ppoint.screenQuad == 8; !flag3; flag3 = true)
                            if(ppoint3.screenQuad == ppoint.screenQuad || ppoint3.screenQuad == (ppoint.screenQuad + 1 & 7) || ppoint.screenQuad == (ppoint3.screenQuad + 1 & 7) || (ppoint3.screenQuad & 1) == 0 && (ppoint.screenQuad & 1) == 0 && (ppoint3.screenQuad == (ppoint.screenQuad + 2 & 7) || ppoint.screenQuad == (ppoint3.screenQuad + 2 & 7)))
                                break;

                        if(flag3)
                        {
                            if(!flag1)
                            {
                                flag1 = true;
                                boolean flag5 = currentPPoint != null && currentPPoint.getOwner() == path;
                                if(flag5)
                                {
                                    k = com.maddox.il2.builder.Builder.colorSelected();
                                    i1 = 3;
                                } else
                                {
                                    k = com.maddox.il2.ai.Army.color(path.getArmy());
                                    i1 = 1;
                                }
                                lineNXYZ[lineNCounter * 3 + 0] = (float)ppoint.screenX;
                                lineNXYZ[lineNCounter * 3 + 1] = (float)ppoint.screenY;
                                lineNXYZ[lineNCounter * 3 + 2] = 0.0F;
                                lineNCounter++;
                            }
                            lineNXYZ[lineNCounter * 3 + 0] = (float)ppoint3.screenX;
                            lineNXYZ[lineNCounter * 3 + 1] = (float)ppoint3.screenY;
                            lineNXYZ[lineNCounter * 3 + 2] = 0.0F;
                            lineNCounter++;
                        } else
                        if(flag1)
                        {
                            flag1 = false;
                            com.maddox.il2.engine.Render.drawLines(lineNXYZ, lineNCounter, i1, k, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
                        }
                    }
                    if(ppoint3.screenQuad == 8)
                    {
                        path.renderPoints++;
                        j++;
                    }
                    ppoint = ppoint3;
                }

                if(flag1)
                {
                    boolean flag2 = false;
                    com.maddox.il2.engine.Render.drawLines(lineNXYZ, lineNCounter, i1, k, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
                }
            }
        }

        com.maddox.il2.engine.Render.drawEnd();
        if(j != 0)
        {
            pathes = getOwnerAttached(pathes);
            for(int k1 = 0; k1 < pathes.length; k1++)
            {
                com.maddox.il2.builder.Path path1 = (com.maddox.il2.builder.Path)pathes[k1];
                pathes[k1] = null;
                if(path1 == null)
                    break;
                if(path1.renderPoints != 0)
                {
                    points = path1.getOwnerAttached(points);
                    for(int j2 = 0; j2 < points.length; j2++)
                    {
                        com.maddox.il2.builder.PPoint ppoint2 = (com.maddox.il2.builder.PPoint)points[j2];
                        points[j2] = null;
                        if(ppoint2 == null)
                            break;
                        if(ppoint2.screenQuad == 8)
                        {
                            int l;
                            if(currentPPoint == ppoint2 && currentPPoint.getOwner() == ppoint2.getOwner())
                                l = com.maddox.il2.builder.Builder.colorSelected();
                            else
                            if(com.maddox.il2.builder.Plugin.builder.isMiltiSelected(ppoint2))
                                l = 0xff0000ff;
                            else
                                l = com.maddox.il2.ai.Army.color(path1.getArmy());
                            com.maddox.il2.engine.IconDraw.setColor(l);
                            com.maddox.il2.engine.IconDraw.render(ppoint2, ppoint2.screenX, ppoint2.screenY);
                            strBuf.delete(0, strBuf.length());
                            strBuf.append(j2);
                            if(com.maddox.il2.builder.Plugin.builder.conf.bShowTime && !(ppoint2 instanceof com.maddox.il2.builder.PAirdrome))
                            {
                                strBuf.append('(');
                                int j3 = (int)java.lang.Math.round(ppoint2.time / 60D) + (int)(com.maddox.il2.ai.World.getTimeofDay() * 60F);
                                strBuf.append((j3 / 60) % 24);
                                strBuf.append(':');
                                int l3 = j3 % 60;
                                if(l3 < 10)
                                    strBuf.append('0');
                                strBuf.append(l3);
                                strBuf.append(')');
                            }
                            com.maddox.il2.builder.Plugin.builder.smallFont.output(l, (int)ppoint2.screenX + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int)ppoint2.screenY - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, strBuf.toString());
                            if(com.maddox.il2.builder.Plugin.builder.conf.bShowName && path1.isNamed())
                                if(path1 instanceof com.maddox.il2.builder.PathAir)
                                    com.maddox.il2.builder.Plugin.builder.smallFont.output(l, (int)ppoint2.screenX + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, ((int)ppoint2.screenY + com.maddox.il2.builder.Plugin.builder.smallFont.height()) - com.maddox.il2.builder.Plugin.builder.smallFont.descender() - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, ((com.maddox.il2.builder.PathAir)path1).typedName);
                                else
                                if(path1 instanceof com.maddox.il2.builder.PathChief)
                                    com.maddox.il2.builder.Plugin.builder.smallFont.output(l, (int)ppoint2.screenX + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, ((int)ppoint2.screenY + com.maddox.il2.builder.Plugin.builder.smallFont.height()) - com.maddox.il2.builder.Plugin.builder.smallFont.descender() - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, com.maddox.rts.Property.stringValue(path1, "i18nName", ""));
                                else
                                    com.maddox.il2.builder.Plugin.builder.smallFont.output(l, (int)ppoint2.screenX + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, ((int)ppoint2.screenY + com.maddox.il2.builder.Plugin.builder.smallFont.height()) - com.maddox.il2.builder.Plugin.builder.smallFont.descender() - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, path1.name());
                        }
                    }

                }
            }

        }
        pathes = getOwnerAttached(pathes);
        for(int l1 = 0; l1 < pathes.length; l1++)
        {
            com.maddox.il2.builder.Path path2 = (com.maddox.il2.builder.Path)pathes[l1];
            pathes[l1] = null;
            if(path2 == null)
                break;
            if(path2 instanceof com.maddox.il2.builder.PathAir)
            {
                points = path2.getOwnerAttached(points);
                for(int k2 = 0; k2 < points.length; k2++)
                {
                    com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)points[k2];
                    points[k2] = null;
                    if(pair == null)
                        break;
                    if(com.maddox.il2.engine.Actor.isValid(pair.getTarget()))
                    {
                        com.maddox.il2.builder.Plugin.builder.project2d(pair.getTarget().pos.getCurrentPoint(), p2d);
                        int k3 = com.maddox.il2.builder.Plugin.builder.conf.iconSize;
                        com.maddox.il2.engine.Render.drawTile((float)(p2d.x - (double)(k3 / 2)), (float)(p2d.y - (double)(k3 / 2)), k3, k3, 0.0F, com.maddox.il2.builder.Plugin.targetIcon, 0xff00ff00, 0.0F, 0.0F, 1.0F, 1.0F);
                    }
                }

            }
        }

        if(j != 0 && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Path.player) && com.maddox.il2.builder.Path.player.renderPoints != 0)
        {
            points = com.maddox.il2.builder.Path.player.getOwnerAttached(points);
            for(int i2 = 0; i2 < points.length; i2++)
            {
                com.maddox.il2.builder.PPoint ppoint1 = (com.maddox.il2.builder.PPoint)points[i2];
                if(ppoint1 == null)
                    break;
                if(ppoint1.screenQuad != 8)
                    continue;
                com.maddox.il2.engine.Render.drawTile((float)ppoint1.screenX, (float)ppoint1.screenY, i, i, 0.0F, com.maddox.il2.builder.Plugin.playerIcon, com.maddox.il2.ai.Army.color(com.maddox.il2.builder.Path.player.getArmy()), 0.0F, 1.0F, 1.0F, -1F);
                break;
            }

            for(int l2 = 0; l2 < points.length; l2++)
                points[l2] = null;

        }
    }

    public void renderMap2DTargetLines()
    {
        com.maddox.il2.engine.Render.drawBeginLines(-1);
        pathes = getOwnerAttached(pathes);
        for(int i = 0; i < pathes.length; i++)
        {
            com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)pathes[i];
            pathes[i] = null;
            if(path == null)
                break;
            if(path instanceof com.maddox.il2.builder.PathAir)
            {
                points = path.getOwnerAttached(points);
                for(int j = 0; j < points.length; j++)
                {
                    com.maddox.il2.builder.PAir pair = (com.maddox.il2.builder.PAir)points[j];
                    points[j] = null;
                    if(pair == null)
                        break;
                    if(com.maddox.il2.engine.Actor.isValid(pair.getTarget()))
                    {
                        com.maddox.il2.builder.Plugin.builder.project2d(pair.pos.getCurrentPoint(), p2d);
                        lineNXYZ[0] = (float)p2d.x;
                        lineNXYZ[1] = (float)p2d.y;
                        lineNXYZ[2] = 0.0F;
                        com.maddox.il2.builder.Plugin.builder.project2d(pair.getTarget().pos.getCurrentPoint(), p2d);
                        lineNXYZ[3] = (float)p2d.x;
                        lineNXYZ[4] = (float)p2d.y;
                        lineNXYZ[5] = 0.0F;
                        com.maddox.il2.engine.Render.drawLines(lineNXYZ, 2, 3F, 0xff00ff00, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
                    }
                }

            }
        }

        com.maddox.il2.engine.Render.drawEnd();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Pathes()
    {
        pointsMap = new HashMapExt();
        p2d = new Point2d();
        lineNXYZ = new float[6];
        strBuf = new StringBuffer();
        pathes = new java.lang.Object[1];
        points = new java.lang.Object[1];
        flags |= 0x2000;
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public void clear()
    {
        pathes = getOwnerAttached(pathes);
        for(int i = 0; i < pathes.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)pathes[i];
            if(actor == null)
                break;
            actor.destroy();
            pathes[i] = null;
        }

        currentPPoint = null;
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        pathes = getOwnerAttached(pathes);
        for(int i = 0; i < pathes.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)pathes[i];
            if(actor == null)
                break;
            actor.destroy();
            pathes[i] = null;
        }

        currentPPoint = null;
        super.destroy();
    }

    public com.maddox.il2.builder.PPoint currentPPoint;
    private com.maddox.util.HashMapExt pointsMap;
    private com.maddox.JGP.Point2d p2d;
    private float lineNXYZ[];
    private int lineNCounter;
    private java.lang.StringBuffer strBuf;
    private java.lang.Object pathes[];
    private java.lang.Object points[];
}
