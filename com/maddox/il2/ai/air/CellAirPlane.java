package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import java.io.Serializable;

public class CellAirPlane extends CellObject
  implements Serializable
{
  public double ofsX;
  public double ofsY;

  public boolean checkAirFieldSize(CellAirField paramCellAirField)
  {
    if (paramCellAirField.getWidth() < getWidth()) return false;
    return paramCellAirField.getHeight() >= getHeight();
  }

  public boolean checkAirFieldCapacity(CellAirField paramCellAirField)
  {
    return paramCellAirField.calcFreeCells() >= calcFilledCells();
  }

  public void blurSiluetUp()
  {
    if (getCells() == null) return;
    for (int i = 0; i < getHeight(); i++)
      for (int j = 0; j < getWidth(); j++)
        if (getCells()[j][i] == this)
          for (int k = i; k >= 0; k--)
            getCells()[j][k] = this;
  }

  public void blurSiluetDown()
  {
    if (getCells() == null) return;
    for (int i = 0; i < getHeight(); i++)
      for (int j = 0; j < getWidth(); j++)
        if (getCells()[j][i] == this)
          for (int k = i; k < getHeight(); k++)
            getCells()[j][k] = this;
  }

  public void blurSiluet4x()
  {
    if (getCells() == null) return;
    CellObject localCellObject = getClone();
    localCellObject.clearCells();
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {
        if (getCells()[j][i] == this) {
          localCellObject.getCells()[j][i] = localCellObject;
          if (j > 0) localCellObject.getCells()[(j - 1)][i] = localCellObject;
          if (j < getWidth() - 1) localCellObject.getCells()[(j + 1)][i] = localCellObject;
          if (i > 0) localCellObject.getCells()[j][(i - 1)] = localCellObject;
          if (i >= getHeight() - 1) continue; localCellObject.getCells()[j][(i + 1)] = localCellObject;
        }
      }
    }
    clearCells();
    setCells(localCellObject.getCells());
    reInitReferences();
  }

  public void blurSiluet8x()
  {
    if (getCells() == null) return;
    CellObject localCellObject = getClone();
    for (int i = 0; i < getHeight(); i++)
      for (int j = 0; j < getWidth(); j++)
        if (localCellObject.getCells()[j][i] != null) {
          if (j > 0) getCells()[(j - 1)][i] = this;
          if (j < getWidth() - 1) getCells()[(j + 1)][i] = this;
          if (i > 0) getCells()[j][(i - 1)] = this;
          if (i < getHeight() - 1) getCells()[j][(i + 1)] = this;
          if ((j > 0) && (i > 0)) getCells()[(j - 1)][(i - 1)] = this;
          if ((j < getWidth() - 1) && (i > 0)) getCells()[(j + 1)][(i - 1)] = this;
          if ((j > 0) && (i < getHeight() - 1)) getCells()[(j - 1)][(i + 1)] = this;
          if ((j >= getWidth() - 1) || (i >= getHeight() - 1)) continue; getCells()[(j + 1)][(i + 1)] = this;
        }
  }

  public void clampCells()
  {
    CellObject[][] arrayOfCellObject1 = getCells();
    if (arrayOfCellObject1 == null) return;
    int i = getWidth();
    int j = getHeight();

    int k = 0;
    int m = 0;
    for (; (m < i) && (k == 0); m++)
      for (n = 0; n < j; n++) { if (arrayOfCellObject1[n][m] == null) continue; k = 1; break;
      }
    k = 0;
    int n = i - 1;
    for (; (n > 0) && (k == 0); n--)
      for (i1 = 0; i1 < j; i1++) { if (arrayOfCellObject1[i1][n] == null) continue; k = 1; break;
      }
    k = 0;
    int i1 = 0;
    for (; (i1 < j) && (k == 0); i1++)
      for (i2 = 0; i2 < i; i2++) { if (arrayOfCellObject1[i1][i2] == null) continue; k = 1; break;
      }
    k = 0;
    int i2 = j - 1;
    for (; (i2 > 0) && (k == 0); i2--)
      for (int i3 = 0; i3 < i; i3++) { if (arrayOfCellObject1[i2][i3] == null) continue; k = 1; break;
      }
    if ((m == 0) && (m == i - 1) && (i1 == 0) && (i2 == j - 1))
      return;
    if ((m > n) || (i1 > i2)) {
      return;
    }
    CellObject[][] arrayOfCellObject2 = new CellObject[i2 - i1 + 1][n - m + 1];
    for (int i4 = 0; i4 < i2 - i1 + 1; i4++) {
      for (int i5 = 0; i5 < n - m + 1; i5++) {
        if (arrayOfCellObject1[(i4 + i1)][(i5 + m)] != null)
          arrayOfCellObject2[i4][i5] = this;
      }
    }
    this.ofsY -= m * getCellSize();
    this.ofsX -= i1 * getCellSize();
    setCells(arrayOfCellObject2);
  }

  public void initCellsThroughCollision(HierMesh paramHierMesh, Loc paramLoc, double paramDouble)
  {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    setCellSize(paramDouble);
    int i = (int)(paramHierMesh.collisionR() * 2.0F / paramDouble) + 6;

    CellObject[][] arrayOfCellObject = new CellObject[i][i];

    for (int j = 0; j < i; j++) {
      for (int k = 0; k < i; k++)
      {
        double d2 = paramLoc.getY() + (i / 2 - j) * paramDouble - paramDouble * 0.5D;
        double d1 = paramLoc.getX() + (i / 2 - k) * paramDouble - paramDouble * 0.5D;
        int m = 0;
        for (int n = -1; (n <= 1) && (m == 0); n++) {
          for (int i1 = -1; (i1 <= 1) && (m == 0); i1++) {
            localPoint3d1.set(d1 + n * paramDouble * 0.4D, d2 + i1 * paramDouble * 0.4D, paramLoc.getZ() + paramHierMesh.collisionR());
            localPoint3d2.set(d1 + n * paramDouble * 0.4D, d2 + i1 * paramDouble * 0.4D, paramLoc.getZ() - paramHierMesh.collisionR());

            float f = paramHierMesh.detectCollisionLine(paramLoc, localPoint3d1, localPoint3d2);
            if ((f > 0.0F) && (f < 1.0F)) {
              m = 1;
              break;
            }
          }
        }
        if (m != 0)
          arrayOfCellObject[j][k] = this;
        else {
          arrayOfCellObject[j][k] = null;
        }
      }
    }
    this.ofsX = (this.ofsY = i / 2 * paramDouble);

    setCells(arrayOfCellObject);
  }

  public CellObject getClone()
  {
    if (getCells() == null) return null;
    CellObject[][] arrayOfCellObject = new CellObject[getWidth()][getHeight()];
    for (int i = 0; i < getCells().length; i++) {
      for (int j = 0; j < getCells()[0].length; j++) {
        arrayOfCellObject[i][j] = getCells()[i][j];
      }
    }
    CellAirPlane localCellAirPlane = new CellAirPlane(arrayOfCellObject);
    localCellAirPlane.setCellSize(getCellSize());
    localCellAirPlane.ofsX = this.ofsX;
    localCellAirPlane.ofsY = this.ofsY;
    return localCellAirPlane;
  }

  public CellAirPlane(CellObject[][] paramArrayOfCellObject)
  {
    super(paramArrayOfCellObject);
  }

  public CellAirPlane(CellObject[][] paramArrayOfCellObject, HierMesh paramHierMesh, Loc paramLoc, double paramDouble)
  {
    super(paramArrayOfCellObject);
    initCellsThroughCollision(paramHierMesh, paramLoc, paramDouble);
  }
}