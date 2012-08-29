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
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Selector
{
  public static float MAXDIST = 3000.0F;
  public static float MAXGDIST = 5000.0F;

  static TreeSet tree = new TreeSet(new Compare());
  static boolean bGround;
  static boolean bDirected;
  static Camera3D camera;
  static double FOVc;
  static double maxdist;
  static int vArmy;
  static int viewArmy;
  static Actor excludeActor;
  static Actor currentActor;
  static Point3d camP;
  static Vector3d Ve = new Vector3d();
  static Vector3d Vc = new Vector3d();
  static Actor target;
  private static double _useDistance;

  public static void resetGame()
  {
    currentActor = null;
    excludeActor = null;
    target = null;
    tree.clear();
  }
  public static void setTarget(Actor paramActor) {
    target = paramActor; } 
  public static Actor getTarget() { return target;
  }

  public static Actor look(boolean paramBoolean1, boolean paramBoolean2, Camera3D paramCamera3D, int paramInt1, int paramInt2, Actor paramActor, boolean paramBoolean3)
  {
    int i = (vArmy != paramInt1) || (viewArmy != paramInt2) || (bGround != paramBoolean2) || (bDirected != paramBoolean1) ? 1 : 0;

    if (isEnableTrackArgs()) {
      currentActor = getTrackArg1();
      return setCurRecordArg1(currentActor);
    }
    vArmy = paramInt1;
    viewArmy = paramInt2;
    bGround = paramBoolean2;
    bDirected = paramBoolean1;
    camera = paramCamera3D;
    excludeActor = paramActor;
    maxdist = MAXDIST;
    camP = camera.pos.getAbsPoint();

    if (i != 0) paramBoolean3 = false;
    if (currentActor == null) paramBoolean3 = false;
    if (paramBoolean3) {
      localObject1 = tree.iterator();
      Object localObject2;
      while (((Iterator)localObject1).hasNext()) {
        localObject2 = (Actor)((Iterator)localObject1).next();
        if (!isUse((Actor)localObject2, false)) {
          paramBoolean3 = false;
          break;
        }
      }
      if (paramBoolean3) {
        localObject2 = Engine.targets();
        k = ((List)localObject2).size();
        for (int m = 0; m < k; m++) {
          Actor localActor1 = (Actor)((List)localObject2).get(m);
          if ((isUse(localActor1, false)) && (!tree.contains(localActor1))) {
            paramBoolean3 = false;
            break;
          }
        }
      }
      if (paramBoolean3) {
        localObject2 = _next(true);
        if (localObject2 == null) {
          paramBoolean3 = false;
          currentActor = null;
        } else {
          currentActor = (Actor)localObject2;
          return setCurRecordArg1(currentActor);
        }
      }
    }

    tree.clear();

    if (bDirected) {
      Vc.set(1.0D, 0.0D, 0.0D); camera.pos.getAbsOrient().transform(Vc);
      FOVc = camera.FOV() / 2.0F;
    }
    Object localObject1 = Engine.targets();
    int j = ((List)localObject1).size();
    for (int k = 0; k < j; k++) {
      localObject3 = (Actor)((List)localObject1).get(k);
      if (isUse((Actor)localObject3, false)) {
        if (bDirected) {
          Ve.sub(((Actor)localObject3).pos.getAbsPoint(), camP); Ve.normalize();
          double d = Vc.dot(Ve);
          Property.set(localObject3, "SelectorLookKey", 1.0D - d);
        } else {
          Property.set(localObject3, "SelectorLookKey", _useDistance);
        }
        tree.add(localObject3);
      }
    }

    currentActor = null;

    k = tree.size();
    if (k == 0) {
      return setCurRecordArg1(null);
    }
    Object localObject3 = tree.iterator();
    for (int n = 0; n < k; n++) {
      Actor localActor2 = (Actor)((Iterator)localObject3).next();
      if (isUse(localActor2, bDirected)) {
        currentActor = localActor2;
        break;
      }
    }

    return (Actor)(Actor)(Actor)setCurRecordArg1(currentActor);
  }

  public static Actor next(boolean paramBoolean) {
    if (isEnableTrackArgs())
      return setCurRecordArg0(getTrackArg0());
    Actor localActor = _next(paramBoolean);
    if (localActor != null)
      currentActor = localActor;
    return setCurRecordArg0(currentActor);
  }

  private static Actor _next(boolean paramBoolean) {
    int i = tree.size();
    if (i == 0) {
      return null;
    }
    if (bDirected) {
      FOVc = 180.0D;
    }
    Iterator localIterator = tree.iterator();

    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    int j = 0;
    for (int k = 0; k < i; k++) {
      Actor localActor = (Actor)localIterator.next();
      if (localActor == currentActor) {
        j = 1;
        if ((!paramBoolean) && (localObject2 != null))
          return localObject2;
      } else if (isUse(localActor, bDirected)) {
        if (j != 0) {
          if (paramBoolean)
            return localActor;
          localObject3 = localActor;
        } else {
          if (localObject1 == null)
            localObject1 = localActor;
          localObject2 = localActor;
        }
      }
    }
    if (paramBoolean) {
      return localObject1;
    }
    return localObject3;
  }

  private static boolean isUse(Actor paramActor, boolean paramBoolean)
  {
    if (paramActor == excludeActor) return false;
    if (bGround) {
      if ((paramActor instanceof Aircraft)) return false;
    }
    else if (!(paramActor instanceof Aircraft)) return false;

    int i = paramActor.getArmy();
    if (i == 0) return false;
    if ((vArmy >= 0) && (vArmy == i)) return false;
    if ((viewArmy >= 0) && (viewArmy != i)) return false;

    _useDistance = camP.distance(paramActor.pos.getAbsPoint());
    if (_useDistance > maxdist) return false;

    if (!paramBoolean) return true;
    VisibilityChecker.checkLandObstacle = true;
    VisibilityChecker.checkCabinObstacle = true;
    VisibilityChecker.checkPlaneObstacle = true;
    VisibilityChecker.checkObjObstacle = true;
    if (VisibilityChecker.computeVisibility(null, paramActor) > 0.0F)
      return VisibilityChecker.resultAng <= FOVc;
    return false;
  }

  private static Actor _getTrackArg(int paramInt)
  {
    if (paramInt == -1) return null;
    NetChannelInStream localNetChannelInStream = Main3D.cur3D().playRecordedNetChannelIn();
    if (localNetChannelInStream == null) return null;
    NetObj localNetObj = localNetChannelInStream.getMirror(paramInt);
    if (localNetObj == null) return null;
    if ((localNetObj.superObj() instanceof Actor))
      return (Actor)localNetObj.superObj();
    return null;
  }
  private static Actor _getTrackArg(String paramString) {
    return Actor.getByName(paramString);
  }

  public static Actor _getTrackArg0() {
    if (!Main3D.cur3D().isDemoPlaying()) return null;
    if (Main3D.cur3D().playRecordedStreams() != null) {
      return _getTrackArg(Main3D.cur3D().keyRecord._getPlayArg0());
    }
    return _getTrackArg(Main3D.cur3D().keyRecord._getPlaySArg0());
  }
  public static Actor _getTrackArg1() {
    if (!Main3D.cur3D().isDemoPlaying()) return null;
    if (Main3D.cur3D().playRecordedStreams() != null) {
      return _getTrackArg(Main3D.cur3D().keyRecord._getPlayArg1());
    }
    return _getTrackArg(Main3D.cur3D().keyRecord._getPlaySArg1());
  }

  public static Actor getTrackArg0() {
    if (!Main3D.cur3D().keyRecord.isEnablePlayArgs()) return null;
    return _getTrackArg0();
  }
  public static Actor getTrackArg1() {
    if (!Main3D.cur3D().keyRecord.isEnablePlayArgs()) return null;
    return _getTrackArg1();
  }

  public static boolean isEnableTrackArgs() {
    return (Main3D.cur3D().isDemoPlaying()) && (Main3D.cur3D().keyRecord.isEnablePlayArgs());
  }

  public static void setCurRecordArgs(Actor paramActor1, Actor paramActor2) {
    Main3D.cur3D().keyRecord.setCurRecordArgs(paramActor1 != null ? paramActor1.net : null, paramActor2 != null ? paramActor2.net : null);
    if ((Mission.isSingle()) && (!NetMissionTrack.isPlaying()) && (NetMissionTrack.countRecorded == 0))
      Main3D.cur3D().keyRecord.setCurRecordSArgs(paramActor1 != null ? paramActor1.name() : null, paramActor2 != null ? paramActor2.name() : null); 
  }

  public static Actor setCurRecordArg0(Actor paramActor) {
    Main3D.cur3D().keyRecord.setCurRecordArg0(paramActor != null ? paramActor.net : null);
    if ((Mission.isSingle()) && (!NetMissionTrack.isPlaying()) && (NetMissionTrack.countRecorded == 0))
      Main3D.cur3D().keyRecord.setCurRecordSArg0(paramActor != null ? paramActor.name() : null);
    return paramActor;
  }
  public static Actor setCurRecordArg1(Actor paramActor) {
    Main3D.cur3D().keyRecord.setCurRecordArg1(paramActor != null ? paramActor.net : null);
    if ((Mission.isSingle()) && (!NetMissionTrack.isPlaying()) && (NetMissionTrack.countRecorded == 0))
      Main3D.cur3D().keyRecord.setCurRecordSArg1(paramActor != null ? paramActor.name() : null);
    return paramActor;
  }

  static class Compare
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      double d1 = Property.doubleValue(paramObject1, "SelectorLookKey");
      double d2 = Property.doubleValue(paramObject2, "SelectorLookKey");
      if (d1 < d2) return -1;
      if (d1 > d2) return 1;
      return 0;
    }
  }
}