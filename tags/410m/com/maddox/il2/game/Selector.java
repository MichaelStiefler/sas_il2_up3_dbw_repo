// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Selector.java

package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

// Referenced classes of package com.maddox.il2.game:
//            VisibilityChecker, Main3D, Mission

public class Selector
{
    static class Compare
        implements java.util.Comparator
    {

        public int compare(java.lang.Object obj, java.lang.Object obj1)
        {
            double d = com.maddox.rts.Property.doubleValue(obj, "SelectorLookKey");
            double d1 = com.maddox.rts.Property.doubleValue(obj1, "SelectorLookKey");
            if(d < d1)
                return -1;
            return d <= d1 ? 0 : 1;
        }

        Compare()
        {
        }
    }


    public Selector()
    {
    }

    public static void resetGame()
    {
        currentActor = null;
        excludeActor = null;
        target = null;
        tree.clear();
    }

    public static void setTarget(com.maddox.il2.engine.Actor actor)
    {
        target = actor;
    }

    public static com.maddox.il2.engine.Actor getTarget()
    {
        return target;
    }

    public static com.maddox.il2.engine.Actor look(boolean flag, boolean flag1, com.maddox.il2.engine.Camera3D camera3d, int i, int j, com.maddox.il2.engine.Actor actor, boolean flag2)
    {
        boolean flag3 = vArmy != i || viewArmy != j || bGround != flag1 || bDirected != flag;
        if(com.maddox.il2.game.Selector.isEnableTrackArgs())
        {
            currentActor = com.maddox.il2.game.Selector.getTrackArg1();
            return com.maddox.il2.game.Selector.setCurRecordArg1(currentActor);
        }
        vArmy = i;
        viewArmy = j;
        bGround = flag1;
        bDirected = flag;
        camera = camera3d;
        excludeActor = actor;
        maxdist = flag1 ? MAXGDIST : MAXDIST;
        camP = camera.pos.getAbsPoint();
        if(flag3)
            flag2 = false;
        if(currentActor == null)
            flag2 = false;
        if(flag2)
        {
            java.util.Iterator iterator = tree.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)iterator.next();
                if(com.maddox.il2.game.Selector.isUse(actor1, false))
                    continue;
                flag2 = false;
                break;
            } while(true);
            if(flag2)
            {
                java.util.List list1 = com.maddox.il2.engine.Engine.targets();
                int l = list1.size();
                int k1 = 0;
                do
                {
                    if(k1 >= l)
                        break;
                    com.maddox.il2.engine.Actor actor4 = (com.maddox.il2.engine.Actor)list1.get(k1);
                    if(com.maddox.il2.game.Selector.isUse(actor4, false) && !tree.contains(actor4))
                    {
                        flag2 = false;
                        break;
                    }
                    k1++;
                } while(true);
            }
            if(flag2)
            {
                com.maddox.il2.engine.Actor actor2 = com.maddox.il2.game.Selector._next(true);
                if(actor2 == null)
                {
                    flag2 = false;
                    currentActor = null;
                } else
                {
                    currentActor = actor2;
                    return com.maddox.il2.game.Selector.setCurRecordArg1(currentActor);
                }
            }
        }
        tree.clear();
        if(bDirected)
        {
            Vc.set(1.0D, 0.0D, 0.0D);
            camera.pos.getAbsOrient().transform(Vc);
            FOVc = camera.FOV() / 2.0F;
        }
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int k = list.size();
        for(int i1 = 0; i1 < k; i1++)
        {
            com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)list.get(i1);
            if(!com.maddox.il2.game.Selector.isUse(actor3, false))
                continue;
            if(bDirected)
            {
                Ve.sub(actor3.pos.getAbsPoint(), camP);
                Ve.normalize();
                double d = Vc.dot(Ve);
                com.maddox.rts.Property.set(actor3, "SelectorLookKey", 1.0D - d);
            } else
            {
                com.maddox.rts.Property.set(actor3, "SelectorLookKey", _useDistance);
            }
            tree.add(actor3);
        }

        currentActor = null;
        int j1 = tree.size();
        if(j1 == 0)
            return com.maddox.il2.game.Selector.setCurRecordArg1(null);
        java.util.Iterator iterator1 = tree.iterator();
        int l1 = 0;
        do
        {
            if(l1 >= j1)
                break;
            com.maddox.il2.engine.Actor actor5 = (com.maddox.il2.engine.Actor)iterator1.next();
            if(com.maddox.il2.game.Selector.isUse(actor5, bDirected))
            {
                currentActor = actor5;
                break;
            }
            l1++;
        } while(true);
        return com.maddox.il2.game.Selector.setCurRecordArg1(currentActor);
    }

    public static com.maddox.il2.engine.Actor next(boolean flag)
    {
        if(com.maddox.il2.game.Selector.isEnableTrackArgs())
            return com.maddox.il2.game.Selector.setCurRecordArg0(com.maddox.il2.game.Selector.getTrackArg0());
        com.maddox.il2.engine.Actor actor = com.maddox.il2.game.Selector._next(flag);
        if(actor != null)
            currentActor = actor;
        return com.maddox.il2.game.Selector.setCurRecordArg0(currentActor);
    }

    private static com.maddox.il2.engine.Actor _next(boolean flag)
    {
        int i = tree.size();
        if(i == 0)
            return null;
        if(bDirected)
            FOVc = 180D;
        java.util.Iterator iterator = tree.iterator();
        com.maddox.il2.engine.Actor actor = null;
        com.maddox.il2.engine.Actor actor1 = null;
        com.maddox.il2.engine.Actor actor2 = null;
        boolean flag1 = false;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)iterator.next();
            if(actor3 == currentActor)
            {
                flag1 = true;
                if(!flag && actor1 != null)
                    return actor1;
                continue;
            }
            if(!com.maddox.il2.game.Selector.isUse(actor3, bDirected))
                continue;
            if(flag1)
            {
                if(flag)
                    return actor3;
                actor2 = actor3;
                continue;
            }
            if(actor == null)
                actor = actor3;
            actor1 = actor3;
        }

        if(flag)
            return actor;
        else
            return actor2;
    }

    private static boolean isUse(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(actor == excludeActor)
            return false;
        if(bGround)
        {
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
                return false;
        } else
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return false;
        int i = actor.getArmy();
        if(i == 0)
            return false;
        if(vArmy >= 0 && vArmy == i)
            return false;
        if(viewArmy >= 0 && viewArmy != i)
            return false;
        _useDistance = camP.distance(actor.pos.getAbsPoint());
        if(_useDistance > maxdist)
            return false;
        if(!flag)
            return true;
        com.maddox.il2.game.VisibilityChecker.checkLandObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkCabinObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkPlaneObstacle = true;
        com.maddox.il2.game.VisibilityChecker.checkObjObstacle = true;
        if(com.maddox.il2.game.VisibilityChecker.computeVisibility(null, actor) > 0.0F)
            return (double)com.maddox.il2.game.VisibilityChecker.resultAng <= FOVc;
        else
            return false;
    }

    private static com.maddox.il2.engine.Actor _getTrackArg(int i)
    {
        if(i == -1)
            return null;
        com.maddox.rts.NetChannelInStream netchannelinstream = com.maddox.il2.game.Main3D.cur3D().playRecordedNetChannelIn();
        if(netchannelinstream == null)
            return null;
        com.maddox.rts.NetObj netobj = netchannelinstream.getMirror(i);
        if(netobj == null)
            return null;
        if(netobj.superObj() instanceof com.maddox.il2.engine.Actor)
            return (com.maddox.il2.engine.Actor)netobj.superObj();
        else
            return null;
    }

    private static com.maddox.il2.engine.Actor _getTrackArg(java.lang.String s)
    {
        return com.maddox.il2.engine.Actor.getByName(s);
    }

    public static com.maddox.il2.engine.Actor _getTrackArg0()
    {
        if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            return null;
        if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
            return com.maddox.il2.game.Selector._getTrackArg(com.maddox.il2.game.Main3D.cur3D().keyRecord._getPlayArg0());
        else
            return com.maddox.il2.game.Selector._getTrackArg(com.maddox.il2.game.Main3D.cur3D().keyRecord._getPlaySArg0());
    }

    public static com.maddox.il2.engine.Actor _getTrackArg1()
    {
        if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            return null;
        if(com.maddox.il2.game.Main3D.cur3D().playRecordedStreams() != null)
            return com.maddox.il2.game.Selector._getTrackArg(com.maddox.il2.game.Main3D.cur3D().keyRecord._getPlayArg1());
        else
            return com.maddox.il2.game.Selector._getTrackArg(com.maddox.il2.game.Main3D.cur3D().keyRecord._getPlaySArg1());
    }

    public static com.maddox.il2.engine.Actor getTrackArg0()
    {
        if(!com.maddox.il2.game.Main3D.cur3D().keyRecord.isEnablePlayArgs())
            return null;
        else
            return com.maddox.il2.game.Selector._getTrackArg0();
    }

    public static com.maddox.il2.engine.Actor getTrackArg1()
    {
        if(!com.maddox.il2.game.Main3D.cur3D().keyRecord.isEnablePlayArgs())
            return null;
        else
            return com.maddox.il2.game.Selector._getTrackArg1();
    }

    public static boolean isEnableTrackArgs()
    {
        return com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() && com.maddox.il2.game.Main3D.cur3D().keyRecord.isEnablePlayArgs();
    }

    public static void setCurRecordArgs(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        com.maddox.il2.game.Main3D.cur3D().keyRecord.setCurRecordArgs(actor == null ? null : ((com.maddox.rts.NetObj) (actor.net)), actor1 == null ? null : ((com.maddox.rts.NetObj) (actor1.net)));
        if(com.maddox.il2.game.Mission.isSingle() && !com.maddox.il2.net.NetMissionTrack.isPlaying() && com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
            com.maddox.il2.game.Main3D.cur3D().keyRecord.setCurRecordSArgs(actor == null ? null : actor.name(), actor1 == null ? null : actor1.name());
    }

    public static com.maddox.il2.engine.Actor setCurRecordArg0(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.game.Main3D.cur3D().keyRecord.setCurRecordArg0(actor == null ? null : ((com.maddox.rts.NetObj) (actor.net)));
        if(com.maddox.il2.game.Mission.isSingle() && !com.maddox.il2.net.NetMissionTrack.isPlaying() && com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
            com.maddox.il2.game.Main3D.cur3D().keyRecord.setCurRecordSArg0(actor == null ? null : actor.name());
        return actor;
    }

    public static com.maddox.il2.engine.Actor setCurRecordArg1(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.game.Main3D.cur3D().keyRecord.setCurRecordArg1(actor == null ? null : ((com.maddox.rts.NetObj) (actor.net)));
        if(com.maddox.il2.game.Mission.isSingle() && !com.maddox.il2.net.NetMissionTrack.isPlaying() && com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
            com.maddox.il2.game.Main3D.cur3D().keyRecord.setCurRecordSArg1(actor == null ? null : actor.name());
        return actor;
    }

    public static float MAXDIST = 3000F;
    public static float MAXGDIST = 5000F;
    static java.util.TreeSet tree = new TreeSet(new Compare());
    static boolean bGround;
    static boolean bDirected;
    static com.maddox.il2.engine.Camera3D camera;
    static double FOVc;
    static double maxdist;
    static int vArmy;
    static int viewArmy;
    static com.maddox.il2.engine.Actor excludeActor;
    static com.maddox.il2.engine.Actor currentActor;
    static com.maddox.JGP.Point3d camP;
    static com.maddox.JGP.Vector3d Ve = new Vector3d();
    static com.maddox.JGP.Vector3d Vc = new Vector3d();
    static com.maddox.il2.engine.Actor target;
    private static double _useDistance;

}
