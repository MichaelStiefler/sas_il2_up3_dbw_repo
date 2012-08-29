package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Landscape;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

public class Way
{
  private ArrayList WList = new ArrayList();
  private int Cur;
  private boolean landing;
  private boolean landingOnShip;
  public Airport landingAirport;
  public Airport takeoffAirport;
  private double prevdist2 = 100000000.0D;
  private double prevdistToNextWP2 = 3.402823466385289E+038D;
  private double curDist;
  private static Vector3d V = new Vector3d();
  private static Point3d P = new Point3d();
  private static Point3d tmpP = new Point3d();
  private static WayPoint defaultWP = new WayPoint();

  public int Cur()
  {
    return this.Cur;
  }
  public Way() {
    this.WList.clear();
    this.Cur = 0;
    this.landing = false;
    this.landingOnShip = false;
    this.landingAirport = null;
    this.takeoffAirport = null;
  }

  public Way(Way paramWay) {
    set(paramWay);
  }

  public void set(Way paramWay) {
    this.WList.clear();
    this.Cur = 0;

    for (int i = 0; i < paramWay.WList.size(); i++) {
      WayPoint localWayPoint = new WayPoint();
      localWayPoint.set(paramWay.get(i));
      this.WList.add(localWayPoint);
      if ((localWayPoint.Action == 2) && (localWayPoint.sTarget != null)) {
        this.landingOnShip = true;
      }
    }
    this.landing = paramWay.landing;
    this.landingAirport = paramWay.landingAirport;
    if (this.takeoffAirport == null) this.takeoffAirport = paramWay.takeoffAirport;
  }

  public WayPoint first()
  {
    this.Cur = 0;
    return curr();
  }

  public WayPoint last()
  {
    this.Cur = Math.max(0, this.WList.size() - 1);
    return curr();
  }

  public WayPoint next()
  {
    int i = this.WList.size();
    this.Cur += 1;
    if (this.Cur >= i)
    {
      this.Cur = Math.max(0, i - 1);
      WayPoint localWayPoint = curr();

      return localWayPoint;
    }
    return curr();
  }

  public WayPoint look_at_point(int paramInt)
  {
    int i = this.WList.size();
    if (i == 0) return defaultWP;
    if (paramInt < 0) paramInt = 0;
    if (paramInt > i - 1) paramInt = i - 1;
    return (WayPoint)this.WList.get(paramInt);
  }

  public void setCur(int paramInt)
  {
    if ((paramInt >= this.WList.size()) || (paramInt < 0)) return;
    this.Cur = paramInt;
  }

  public WayPoint prev() {
    this.Cur -= 1;
    if (this.Cur < 0) this.Cur = 0;
    return curr();
  }

  public WayPoint curr() {
    if (this.WList.size() == 0) return defaultWP;
    return (WayPoint)this.WList.get(this.Cur);
  }

  public WayPoint auto(Point3d paramPoint3d)
  {
    if ((this.Cur == 0) || (isReached(paramPoint3d))) return next();
    return curr();
  }

  public double getCurDist()
  {
    return Math.sqrt(this.curDist);
  }
  public boolean isReached(Point3d paramPoint3d) {
    curr().getP(P);
    V.sub(paramPoint3d, P);
    if ((curr().timeout == -1) && (!isLast())) {
      ((WayPoint)this.WList.get(this.Cur + 1)).getP(tmpP);
      V.sub(paramPoint3d, tmpP);
      this.curDist = (V.x * V.x + V.y * V.y);
      if ((this.curDist < 100000000.0D) && (this.curDist > this.prevdistToNextWP2)) {
        curr().setTimeout(0);
        this.prevdistToNextWP2 = 3.402823466385289E+038D;
        return true;
      }
      this.prevdistToNextWP2 = this.curDist;
    } else {
      this.curDist = (V.x * V.x + V.y * V.y);
      if ((this.curDist < 1000000.0D) && (this.curDist > this.prevdist2)) { this.prevdist2 = 100000000.0D; return true; }
      this.prevdist2 = this.curDist;
    }
    return false;
  }

  public boolean isLanding() {
    return this.landing;
  }

  public boolean isLandingOnShip() {
    return this.landingOnShip;
  }

  public boolean isLast() {
    return this.Cur == this.WList.size() - 1;
  }

  public void setLanding(boolean paramBoolean) {
    this.landing = paramBoolean;
  }

  public void add(WayPoint paramWayPoint)
  {
    this.WList.add(paramWayPoint);
    if ((paramWayPoint.Action == 2) && (paramWayPoint.sTarget != null))
      this.landingOnShip = true;
  }

  public WayPoint get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.WList.size())) return null;
    return (WayPoint)this.WList.get(paramInt);
  }

  public void insert(int paramInt, WayPoint paramWayPoint)
  {
    if (paramInt < 0) { paramInt = 0;
    } else if (paramInt > this.WList.size()) { add(paramWayPoint); return; }
    this.WList.add(paramInt, paramWayPoint);
  }

  public int size()
  {
    return this.WList.size();
  }

  public void load(SectFile paramSectFile, String paramString) throws Exception
  {
    int i = paramSectFile.sectionIndex(paramString);
    int j = paramSectFile.vars(i);

    for (int k = 0; k < j; k++) {
      String str1 = paramSectFile.var(i, k);
      WayPoint localWayPoint = new WayPoint();
      if (str1.equalsIgnoreCase("TAKEOFF")) localWayPoint.Action = 1;
      else if (str1.equalsIgnoreCase("LANDING")) localWayPoint.Action = 2;
      else if (str1.equalsIgnoreCase("GATTACK")) localWayPoint.Action = 3; else
        localWayPoint.Action = 0;
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.value(i, k));
      P.x = localNumberTokenizer.next(0.0F, -1000000.0F, 1000000.0F);
      P.y = localNumberTokenizer.next(0.0F, -1000000.0F, 1000000.0F);
      P.z = (localNumberTokenizer.next(0.0F, -1000000.0F, 1000000.0F) + World.land().HQ(P.x, P.y));
      float f = localNumberTokenizer.next(0.0F, 0.0F, 2800.0F);
      localWayPoint.set(P);
      localWayPoint.set(f / 3.6F);
      String str2 = localNumberTokenizer.next(null);
      if (str2 != null) {
        if (str2.equals("&0")) {
          localWayPoint.bRadioSilence = false;
          str2 = null;
        } else if (str2.equals("&1")) {
          localWayPoint.bRadioSilence = true;
          str2 = null;
        } else {
          localNumberTokenizer.next(0);
          String str3 = localNumberTokenizer.next(null);
          if ((str3 != null) && (str3.equals("&1")))
            localWayPoint.bRadioSilence = true;
        }
      }
      if ((str2 != null) && (str2.startsWith("Bridge")))
        str2 = " " + str2;
      localWayPoint.setTarget(str2);
      add(localWayPoint);
    }
  }
}