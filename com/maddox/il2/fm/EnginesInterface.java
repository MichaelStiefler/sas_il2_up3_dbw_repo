package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.SectFile;

public class EnginesInterface extends FMMath
{
  public Motor[] engines;
  public boolean[] bCurControl;
  private int num = 0;
  public Vector3d producedF = new Vector3d();
  public Vector3d producedM = new Vector3d();

  private FlightModel reference = null;

  private static Vector3d tmpV3d = new Vector3d();
  private static int tmpI;

  public void load(FlightModel paramFlightModel, SectFile paramSectFile)
  {
    this.reference = paramFlightModel;
    String str = "Engine";

    this.num = 0;

    while (paramSectFile.get(str, "Engine" + this.num + "Family") != null)
    {
      this.num += 1;
    }

    this.engines = new Motor[this.num]; for (tmpI = 0; tmpI < this.num; tmpI += 1) this.engines[tmpI] = new Motor();
    this.bCurControl = new boolean[this.num];
    Aircraft.debugprintln(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, "Loading " + this.num + " engine(s) from '" + paramSectFile.toString() + "....");
    Object localObject1;
    Object localObject2;
    for (tmpI = 0; tmpI < this.num; tmpI += 1)
    {
      localObject1 = paramSectFile.get(str, "Engine" + tmpI + "Family");
      localObject2 = paramSectFile.get(str, "Engine" + tmpI + "SubModel");
      Aircraft.debugprintln(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, "Loading engine model from '" + (String)localObject1 + ".emd', submodel '" + (String)localObject2 + "'....");
      this.engines[tmpI].load(paramFlightModel, "FlightModels/" + (String)localObject1 + ".emd", (String)localObject2, tmpI);
    }

    if (paramSectFile.get(str, "Position0x", -99999.0F) != -99999.0F) {
      localObject1 = new Point3d();
      localObject2 = new Vector3f();
      for (tmpI = 0; tmpI < this.num; tmpI += 1) {
        ((Point3d)localObject1).jdField_x_of_type_Double = paramSectFile.get(str, "Position" + tmpI + "x", 0.0F);
        ((Point3d)localObject1).jdField_y_of_type_Double = paramSectFile.get(str, "Position" + tmpI + "y", 0.0F);
        ((Point3d)localObject1).jdField_z_of_type_Double = paramSectFile.get(str, "Position" + tmpI + "z", 0.0F);
        this.engines[tmpI].setPos((Point3d)localObject1);
        ((Vector3f)localObject2).jdField_x_of_type_Float = paramSectFile.get(str, "Vector" + tmpI + "x", 0.0F);
        ((Vector3f)localObject2).jdField_y_of_type_Float = paramSectFile.get(str, "Vector" + tmpI + "y", 0.0F);
        ((Vector3f)localObject2).jdField_z_of_type_Float = paramSectFile.get(str, "Vector" + tmpI + "z", 0.0F);
        this.engines[tmpI].setVector((Vector3f)localObject2);
        ((Point3d)localObject1).jdField_x_of_type_Double = paramSectFile.get(str, "PropPosition" + tmpI + "x", 0.0F);
        ((Point3d)localObject1).jdField_y_of_type_Double = paramSectFile.get(str, "PropPosition" + tmpI + "y", 0.0F);
        ((Point3d)localObject1).jdField_z_of_type_Double = paramSectFile.get(str, "PropPosition" + tmpI + "z", 0.0F);
        this.engines[tmpI].setPropPos((Point3d)localObject1);
      }
    }
    setCurControlAll(true);
  }

  public void setNotMirror(boolean paramBoolean) {
    for (int i = 0; i < getNum(); i++)
      this.engines[i].setMaster(paramBoolean);
  }

  public void set(Actor paramActor)
  {
    Point3d localPoint3d = new Point3d(0.0D, 0.0D, 0.0D);
    Loc localLoc = new Loc();
    if (this.engines[0].getPropPos().distanceSquared(new Point3f(0.0F, 0.0F, 0.0F)) > 0.0F) {
      return;
    }

    Vector3f localVector3f = new Vector3f(1.0F, 0.0F, 0.0F);
    float[][] arrayOfFloat1 = new float[4][3];
    float[][] arrayOfFloat2 = new float[this.num][3];
    Hook localHook;
    for (tmpI = 0; tmpI < 4; tmpI += 1) {
      localHook = paramActor.findHook("_Clip0" + tmpI);
      localLoc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHook.computePos(paramActor, paramActor.pos.getAbs(), localLoc);
      localLoc.get(localPoint3d);
      paramActor.pos.getAbs().transformInv(localPoint3d);
      arrayOfFloat1[tmpI][0] = (float)localPoint3d.jdField_x_of_type_Double;
      arrayOfFloat1[tmpI][1] = (float)localPoint3d.jdField_y_of_type_Double;
      arrayOfFloat1[tmpI][2] = (float)localPoint3d.jdField_z_of_type_Double;
    }
    for (tmpI = 0; tmpI < this.num; tmpI += 1) {
      localHook = paramActor.findHook("_Engine" + (tmpI + 1) + "Smoke");
      localLoc.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHook.computePos(paramActor, paramActor.pos.getAbs(), localLoc);
      localLoc.get(localPoint3d);
      paramActor.pos.getAbs().transformInv(localPoint3d);
      arrayOfFloat2[tmpI][0] = (float)localPoint3d.jdField_x_of_type_Double;
      arrayOfFloat2[tmpI][1] = (float)localPoint3d.jdField_y_of_type_Double;
      arrayOfFloat2[tmpI][2] = ((float)localPoint3d.jdField_z_of_type_Double - 0.7F);
    }
    switch (this.reference.Scheme) {
    case 0:
      localPoint3d.set(0.0D, 0.0D, 0.0D);
      this.engines[0].setPos(localPoint3d);
      this.engines[0].setPropPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      break;
    case 1:
      localPoint3d.jdField_x_of_type_Double = (0.25F * (arrayOfFloat1[0][0] + arrayOfFloat1[1][0] + arrayOfFloat1[2][0] + arrayOfFloat1[3][0]));
      localPoint3d.jdField_y_of_type_Double = 0.0D;
      localPoint3d.jdField_z_of_type_Double = (0.25F * (arrayOfFloat1[0][2] + arrayOfFloat1[1][2] + arrayOfFloat1[2][2] + arrayOfFloat1[3][2]));
      this.engines[0].setPropPos(localPoint3d);
      localPoint3d.jdField_x_of_type_Double = arrayOfFloat2[0][0];
      localPoint3d.jdField_y_of_type_Double = 0.0D;
      localPoint3d.jdField_z_of_type_Double = arrayOfFloat2[0][2];
      this.engines[0].setPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      break;
    case 2:
    case 3:
      localPoint3d.jdField_x_of_type_Double = (0.25F * (arrayOfFloat1[0][0] + arrayOfFloat1[1][0] + arrayOfFloat1[2][0] + arrayOfFloat1[3][0]));
      localPoint3d.jdField_y_of_type_Double = (0.5F * (arrayOfFloat1[0][1] + arrayOfFloat1[1][1]));
      localPoint3d.jdField_z_of_type_Double = (0.25F * (arrayOfFloat1[0][2] + arrayOfFloat1[1][2] + arrayOfFloat1[2][2] + arrayOfFloat1[3][2]));
      this.engines[0].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = (0.5F * (arrayOfFloat1[2][1] + arrayOfFloat1[3][1]));
      this.engines[1].setPropPos(localPoint3d);
      localPoint3d.jdField_x_of_type_Double = (0.5F * (arrayOfFloat2[0][0] + arrayOfFloat2[1][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.5F * (arrayOfFloat2[0][2] + arrayOfFloat2[1][2]));
      this.engines[0].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[1][1];
      this.engines[1].setPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      this.engines[1].setVector(localVector3f);
      break;
    case 4:
      localPoint3d.jdField_x_of_type_Double = (0.25F * (arrayOfFloat1[0][0] + arrayOfFloat1[1][0] + arrayOfFloat1[2][0] + arrayOfFloat1[3][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.25F * (arrayOfFloat1[0][2] + arrayOfFloat1[1][2] + arrayOfFloat1[2][2] + arrayOfFloat1[3][2]));
      this.engines[0].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[1][1];
      this.engines[1].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[2][1];
      this.engines[2].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[3][1];
      this.engines[3].setPropPos(localPoint3d);
      localPoint3d.jdField_x_of_type_Double = (0.25F * (arrayOfFloat2[0][0] + arrayOfFloat2[1][0] + arrayOfFloat2[2][0] + arrayOfFloat2[3][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.25F * (arrayOfFloat2[0][2] + arrayOfFloat2[1][2] + arrayOfFloat2[2][2] + arrayOfFloat2[3][2]));
      this.engines[0].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[1][1];
      this.engines[1].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[2][1];
      this.engines[2].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[3][1];
      this.engines[3].setPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      this.engines[1].setVector(localVector3f);
      this.engines[2].setVector(localVector3f);
      this.engines[3].setVector(localVector3f);
      break;
    case 5:
      localPoint3d.jdField_x_of_type_Double = (0.25F * (arrayOfFloat1[0][0] + arrayOfFloat1[1][0] + arrayOfFloat1[2][0] + arrayOfFloat1[3][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.25F * (arrayOfFloat1[0][2] + arrayOfFloat1[1][2] + arrayOfFloat1[2][2] + arrayOfFloat1[3][2]));
      this.engines[0].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[1][1];
      this.engines[1].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = 0.0D;
      this.engines[2].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[2][1];
      this.engines[3].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[3][1];
      this.engines[4].setPropPos(localPoint3d);
      localPoint3d.jdField_x_of_type_Double = (0.2F * (arrayOfFloat2[0][0] + arrayOfFloat2[1][0] + arrayOfFloat2[2][0] + arrayOfFloat2[3][0] + arrayOfFloat2[4][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.2F * (arrayOfFloat2[0][2] + arrayOfFloat2[1][2] + arrayOfFloat2[2][2] + arrayOfFloat2[3][2] + arrayOfFloat2[4][2]));
      this.engines[0].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[1][1];
      this.engines[1].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[2][1];
      this.engines[2].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[3][1];
      this.engines[3].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[4][1];
      this.engines[4].setPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      this.engines[1].setVector(localVector3f);
      this.engines[2].setVector(localVector3f);
      this.engines[3].setVector(localVector3f);
      this.engines[4].setVector(localVector3f);
      break;
    case 6:
      localPoint3d.jdField_x_of_type_Double = (0.3333333F * (arrayOfFloat1[0][0] + arrayOfFloat1[1][0] + arrayOfFloat1[2][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.3333333F * (arrayOfFloat1[0][2] + arrayOfFloat1[1][2] + arrayOfFloat1[2][2]));
      this.engines[0].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[1][1];
      this.engines[1].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[2][1];
      this.engines[2].setPropPos(localPoint3d);
      localPoint3d.jdField_x_of_type_Double = (0.3333333F * (arrayOfFloat2[0][0] + arrayOfFloat2[1][0] + arrayOfFloat2[2][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.3333333F * (arrayOfFloat2[0][2] + arrayOfFloat2[1][2] + arrayOfFloat2[2][2]));
      this.engines[0].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[1][1];
      this.engines[1].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[2][1];
      this.engines[2].setPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      this.engines[1].setVector(localVector3f);
      this.engines[2].setVector(localVector3f);
      break;
    case 7:
      localPoint3d.jdField_x_of_type_Double = (0.25F * (arrayOfFloat1[0][0] + arrayOfFloat1[1][0] + arrayOfFloat1[2][0] + arrayOfFloat1[3][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.25F * (arrayOfFloat1[0][2] + arrayOfFloat1[1][2] + arrayOfFloat1[2][2] + arrayOfFloat1[3][2]));
      this.engines[0].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = 0.0D;
      this.engines[1].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[1][1];
      this.engines[2].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[2][1];
      this.engines[3].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = 0.0D;
      this.engines[4].setPropPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat1[3][1];
      this.engines[5].setPropPos(localPoint3d);
      localPoint3d.jdField_x_of_type_Double = (0.1666667F * (arrayOfFloat2[0][0] + arrayOfFloat2[1][0] + arrayOfFloat2[2][0] + arrayOfFloat2[3][0] + arrayOfFloat2[4][0] + arrayOfFloat2[5][0]));
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[0][1];
      localPoint3d.jdField_z_of_type_Double = (0.1666667F * (arrayOfFloat2[0][2] + arrayOfFloat2[1][2] + arrayOfFloat2[2][2] + arrayOfFloat2[3][2] + arrayOfFloat2[4][2] + arrayOfFloat2[5][2]));
      this.engines[0].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[1][1];
      this.engines[1].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[2][1];
      this.engines[2].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[3][1];
      this.engines[3].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[4][1];
      this.engines[4].setPos(localPoint3d);
      localPoint3d.jdField_y_of_type_Double = arrayOfFloat2[5][1];
      this.engines[5].setPos(localPoint3d);
      this.engines[0].setVector(localVector3f);
      this.engines[1].setVector(localVector3f);
      this.engines[2].setVector(localVector3f);
      this.engines[3].setVector(localVector3f);
      this.engines[4].setVector(localVector3f);
      this.engines[5].setVector(localVector3f);
      break;
    default:
      throw new RuntimeException("UNIDENTIFIED ENGINE DISTRIBUTION.");
    }
  }

  public void update(float paramFloat)
  {
    this.producedF.set(0.0D, 0.0D, 0.0D);
    this.producedM.set(0.0D, 0.0D, 0.0D);
    for (int i = 0; i < this.num; i++) {
      this.engines[i].update(paramFloat);

      this.producedF.jdField_x_of_type_Double += this.engines[i].getEngineForce().jdField_x_of_type_Float;
      this.producedF.jdField_y_of_type_Double += this.engines[i].getEngineForce().jdField_y_of_type_Float;
      this.producedF.jdField_z_of_type_Double += this.engines[i].getEngineForce().jdField_z_of_type_Float;
      this.producedM.jdField_x_of_type_Double += this.engines[i].getEngineTorque().jdField_x_of_type_Float;
      this.producedM.jdField_y_of_type_Double += this.engines[i].getEngineTorque().jdField_y_of_type_Float;
      this.producedM.jdField_z_of_type_Double += this.engines[i].getEngineTorque().jdField_z_of_type_Float;
    }
  }

  public void netupdate(float paramFloat, boolean paramBoolean) {
    for (int i = 0; i < this.num; i++)
      this.engines[i].netupdate(paramFloat, paramBoolean);
  }

  public int getNum()
  {
    return this.num;
  }
  public void setNum(int paramInt) {
    this.num = paramInt;
  }

  public void toggle()
  {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].toggle();
    }
  }

  public void setCurControl(int paramInt, boolean paramBoolean)
  {
    this.bCurControl[paramInt] = paramBoolean;
  }
  public void setCurControlAll(boolean paramBoolean) {
    for (tmpI = 0; tmpI < this.num; tmpI += 1)
      this.bCurControl[tmpI] = paramBoolean; 
  }

  public boolean getCurControl(int paramInt) {
    return this.bCurControl[paramInt];
  }

  public Motor getFirstSelected() {
    for (int i = 0; i < this.num; i++) {
      if (this.bCurControl[i] != 0) return this.engines[i];
    }
    return null;
  }
  public int getNumSelected() {
    int i = 0;
    for (int j = 0; j < this.num; j++) {
      if (this.bCurControl[j] == 0) continue; i++;
    }
    return i;
  }

  public float getPropDirSign()
  {
    float f = 0.0F;
    for (int i = 0; i < getNum(); i++) {
      if (this.engines[i].getPropDir() == 0) f += 1.0F; else
        f -= 1.0F;
    }
    return f / getNum();
  }

  public float getRadiatorPos() {
    float f = 0.0F;
    for (int i = 0; i < getNum(); i++) {
      f += this.engines[i].getControlRadiator();
    }
    return f / getNum();
  }

  public int[] getSublist(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = null;
    if (paramInt2 == 1) {
      switch (paramInt1) {
      case 2:
      case 3:
        arrayOfInt = new int[] { 0 };
        break;
      case 6:
        arrayOfInt = new int[] { 0 };
        break;
      case 4:
        arrayOfInt = new int[] { 0, 1 };
        break;
      case 5:
        arrayOfInt = new int[] { 0, 1 };
        break;
      case 7:
        arrayOfInt = new int[] { 0, 1, 2 };
      }
    }
    else if (paramInt2 == 2) {
      switch (paramInt1) {
      case 2:
      case 3:
        arrayOfInt = new int[] { 1 };
        break;
      case 6:
        arrayOfInt = new int[] { 2 };
        break;
      case 4:
        arrayOfInt = new int[] { 2, 3 };
        break;
      case 5:
        arrayOfInt = new int[] { 3, 4 };
        break;
      case 7:
        arrayOfInt = new int[] { 3, 4, 5 };
      }
    }

    return arrayOfInt;
  }

  public boolean isSelectionHasControlThrottle()
  {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlThrottle();
    }
    return bool;
  }
  public boolean isSelectionHasControlProp() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlProp();
    }
    return bool;
  }
  public boolean isSelectionAllowsAutoProp() {
    World.cur(); if (this.reference != World.getPlayerFM()) return true;
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isAllowsAutoProp();
    }
    return bool;
  }
  public boolean isSelectionHasControlMix() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlMix();
    }
    return bool;
  }
  public boolean isSelectionHasControlMagnetos() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlMagnetos();
    }
    return bool;
  }
  public boolean isSelectionHasControlCompressor() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlCompressor();
    }
    return bool;
  }
  public boolean isSelectionHasControlFeather() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlFeather();
    }
    return bool;
  }
  public boolean isSelectionHasControlExtinguisher() {
    int i = 0;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; i |= (this.engines[tmpI].getExtinguishers() > 0 ? 1 : 0);
    }
    return i;
  }
  public boolean isSelectionHasControlAfterburner() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlAfterburner();
    }
    return bool;
  }
  public boolean isSelectionAllowsAutoRadiator() {
    World.cur(); if (this.reference != World.getPlayerFM()) return true;
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isAllowsAutoRadiator();
    }
    return bool;
  }
  public boolean isSelectionHasControlRadiator() {
    boolean bool = false;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; bool |= this.engines[tmpI].isHasControlRadiator();
    }
    return bool;
  }

  public float getPowerOutput()
  {
    float f = 0.0F;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      f += this.engines[tmpI].getPowerOutput();
    }
    return f / getNum();
  }

  public float getThrustOutput() {
    float f = 0.0F;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      f += this.engines[tmpI].getThrustOutput();
    }
    return f / getNum();
  }

  public float getReadyness() {
    float f = 0.0F;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      f += this.engines[tmpI].getReadyness();
    }
    return f / getNum();
  }

  public float getBoostFactor() {
    float f = 0.0F;
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      f += this.engines[tmpI].getBoostFactor();
    }
    return f / getNum();
  }

  public Vector3d getGyro()
  {
    tmpV3d.set(0.0D, 0.0D, 0.0D);
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      tmpV3d.add(this.engines[tmpI].getEngineGyro());
    }
    return tmpV3d;
  }

  public void setThrottle(float paramFloat)
  {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlThrottle(paramFloat);
    }
  }

  public void setAfterburnerControl(boolean paramBoolean) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlAfterburner(paramBoolean);
    }
  }

  public void setProp(float paramFloat) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlProp(paramFloat);
    }
  }

  public void setPropAuto(boolean paramBoolean) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlPropAuto(paramBoolean);
    }
  }

  public void setFeather(int paramInt) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlFeather(paramInt);
    }
  }

  public void setMix(float paramFloat) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlMix(paramFloat);
    }
  }

  public void setMagnetos(int paramInt) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlMagneto(paramInt);
    }
  }

  public void setCompressorStep(int paramInt) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlCompressor(paramInt);
    }
  }

  public void setRadiator(float paramFloat) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setControlRadiator(paramFloat);
    }
  }

  public void updateRadiator(float paramFloat) {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1)
      this.engines[tmpI].updateRadiator(paramFloat);
  }

  public void setEngineStops()
  {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }
  }

  public void setEngineRunning() {
    for (tmpI = 0; tmpI < getNum(); tmpI += 1) {
      if (this.bCurControl[tmpI] == 0) continue; this.engines[tmpI].setEngineRunning(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }
  }

  public float forcePropAOA(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    float f = 0.0F;
    for (int i = 0; i < getNum(); i++) f += this.engines[i].forcePropAOA(paramFloat1, paramFloat2, paramFloat3, paramBoolean);
    Aircraft.debugprintln(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, "Computed thrust at " + paramFloat1 + " m/s and " + paramFloat2 + " m is " + f + " N..");
    return f;
  }
}