package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import java.io.Serializable;
import java.util.ArrayList;

public class CellAirField extends CellObject
  implements Serializable
{
  private Point3d leftUpperCorner = new Point3d();

  private int resX = 0; private int resY = 0;

  public Point3d leftUpperCorner()
  {
    return this.leftUpperCorner; } 
  public void setLeftUpperCorner(Point3d paramPoint3d) { this.leftUpperCorner.set(paramPoint3d);
  }

  public boolean findPlaceForAirPlane(CellAirPlane paramCellAirPlane)
  {
    if (!paramCellAirPlane.checkAirFieldSize(this)) return false;
    if (!paramCellAirPlane.checkAirFieldCapacity(this)) return false;
    for (int i = 0; i <= getHeight() - paramCellAirPlane.getHeight(); i++) {
      for (int j = 0; j <= getWidth() - paramCellAirPlane.getWidth(); j++) {
        if (isThereFreePlace(paramCellAirPlane, j, i)) {
          this.resX = j;
          this.resY = i;
          return true;
        }
      }
    }
    return false;
  }

  public int resX()
  {
    return this.resX; } 
  public int resY() { return this.resY;
  }

  public boolean isThereFreePlace(CellAirPlane paramCellAirPlane, int paramInt1, int paramInt2)
  {
    if (getCells() == null) return false;
    for (int i = paramInt2; i < paramInt2 + paramCellAirPlane.getHeight(); i++) {
      for (int j = paramInt1; j < paramInt1 + paramCellAirPlane.getWidth(); j++) {
        if ((getCells()[j][i] != null) && (paramCellAirPlane.getCells()[(j - paramInt1)][(i - paramInt2)] != null)) return false;
        if (paramCellAirPlane.getCells()[(j - paramInt1)][(i - paramInt2)] == null)
          continue;
        for (int k = i; k < getHeight(); k++) {
          if ((getCells()[j][k] != null) && 
            (getCells()[j][k] != this)) return false;
        }
      }
    }

    return true;
  }

  public void placeAirPlane(CellAirPlane paramCellAirPlane, int paramInt1, int paramInt2)
  {
    if (getCells() == null) return;
    paramCellAirPlane.setWorldCoordinates(getXCoordinate() + paramInt1 * getCellSize(), getYCoordinate() + paramInt2 * getCellSize());
    for (int i = 0; i < paramCellAirPlane.getHeight(); i++)
      for (int j = 0; j < paramCellAirPlane.getWidth(); j++) {
        if (paramCellAirPlane.getCells()[j][i] == null) continue; getCells()[(paramInt1 + j)][(paramInt2 + i)] = paramCellAirPlane;
      }
  }

  public void placeAirPlaneId(CellAirPlane paramCellAirPlane, int paramInt1, int paramInt2, String paramString) {
    if (getCells() == null) return;
    paramCellAirPlane.setWorldCoordinates(getXCoordinate() + paramInt1 * getCellSize(), getYCoordinate() + paramInt2 * getCellSize());
    for (int i = 0; i < paramCellAirPlane.getHeight(); i++)
      for (int j = 0; j < paramCellAirPlane.getWidth(); j++)
        if (paramCellAirPlane.getCells()[j][i] != null) {
          getCells()[(paramInt1 + j)][(paramInt2 + i)] = paramCellAirPlane;
          getCells()[(paramInt1 + j)][(paramInt2 + i)].setId(paramString);
        }
  }

  public boolean removeAirPlane(CellAirPlane paramCellAirPlane)
  {
    if (getCells() == null) return false;
    int i = 0;
    for (int j = 0; j < getCells().length; j++) {
      for (int k = 0; k < getCells()[0].length; k++) {
        if (getCells()[j][k] != paramCellAirPlane)
          continue;
        getCells()[j][k] = null;
        i = 1;
      }
    }

    return i;
  }

  public void replaceAirPlane(CellAirPlane paramCellAirPlane, int paramInt1, int paramInt2)
  {
    if (getCells() == null) return;
    for (int i = 0; i < paramCellAirPlane.getWidth(); i++)
      for (int j = 0; j < paramCellAirPlane.getHeight(); j++) {
        if (getCells()[i][j] != paramCellAirPlane) continue; getCells()[(paramInt1 + i)][(paramInt2 + j)] = null;
      }
  }

  public void freeCells()
  {
    if (getCells() == null) return;
    for (int i = 0; i < getCells().length; i++)
      for (int j = 0; j < getCells()[0].length; j++)
        if (getCells()[i][j] != this)
          getCells()[i][j] = null;
  }

  public Point3d getLeftUpperPoint(ArrayList paramArrayList)
  {
    Point3d localPoint3d1 = new Point3d();
    double d1 = 1000.0D; double d2 = -1000.0D;
    for (int i = 0; i < paramArrayList.size(); i++) {
      Point3d localPoint3d2 = (Point3d)paramArrayList.get(i);
      if (localPoint3d2.x < d1) d1 = localPoint3d2.x;
      if (localPoint3d2.y <= d2) continue; d2 = localPoint3d2.y;
    }
    localPoint3d1.set(d1, d2, 0.0D);
    return localPoint3d1;
  }

  public Point3d getRightDownPoint(ArrayList paramArrayList)
  {
    Point3d localPoint3d1 = new Point3d();
    double d1 = -1000.0D; double d2 = 1000.0D;
    for (int i = 0; i < paramArrayList.size(); i++) {
      Point3d localPoint3d2 = (Point3d)paramArrayList.get(i);
      if (localPoint3d2.x > d1) d1 = localPoint3d2.x;
      if (localPoint3d2.y >= d2) continue; d2 = localPoint3d2.y;
    }
    localPoint3d1.set(d1, d2, 0.0D);
    return localPoint3d1;
  }

  public void fieldInitWithComplexPoly(ArrayList paramArrayList, double paramDouble)
  {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    Point3d localPoint3d3 = new Point3d();
    Point3d localPoint3d4 = new Point3d();
    setCellSize(paramDouble);

    Point3d localPoint3d5 = getLeftUpperPoint(paramArrayList);
    setLeftUpperCorner(localPoint3d5);

    Point3d localPoint3d6 = getRightDownPoint(paramArrayList);

    int i = (int)(Math.abs(localPoint3d6.x - localPoint3d5.x) / paramDouble);
    int j = (int)(Math.abs(localPoint3d6.y - localPoint3d5.y) / paramDouble);

    CellObject[][] arrayOfCellObject = new CellObject[i][j];
    setCells(arrayOfCellObject);
    clearCells();

    for (int k = 0; k < getWidth(); k++)
      for (int m = 0; m < getHeight(); m++)
      {
        localPoint3d5.x += k * paramDouble;
        localPoint3d5.y -= m * paramDouble;

        localPoint3d2.x = (localPoint3d5.x + k * paramDouble + paramDouble);
        localPoint3d5.y -= m * paramDouble;

        localPoint3d5.x += k * paramDouble;
        localPoint3d3.y = (localPoint3d5.y - m * paramDouble + paramDouble);

        localPoint3d4.x = (localPoint3d5.x + k * paramDouble + paramDouble);
        localPoint3d4.y = (localPoint3d5.y - m * paramDouble + paramDouble);

        if ((CellTools.belongsToComplex(paramArrayList, localPoint3d1)) && (CellTools.belongsToComplex(paramArrayList, localPoint3d2)) && (CellTools.belongsToComplex(paramArrayList, localPoint3d3)) && (CellTools.belongsToComplex(paramArrayList, localPoint3d4)))
        {
          getCells()[k][m] = null;
        }
        else getCells()[k][m] = this;
      }
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
    CellAirField localCellAirField = new CellAirField(arrayOfCellObject, leftUpperCorner());
    localCellAirField.setCellSize(getCellSize());
    return localCellAirField;
  }

  public CellAirField(CellObject[][] paramArrayOfCellObject)
  {
    super(paramArrayOfCellObject);
  }

  public CellAirField(CellObject[][] paramArrayOfCellObject, Point3d paramPoint3d)
  {
    super(paramArrayOfCellObject);
    setLeftUpperCorner(paramPoint3d);
  }

  public CellAirField(CellObject[][] paramArrayOfCellObject, ArrayList paramArrayList, double paramDouble)
  {
    super(paramArrayOfCellObject);
    fieldInitWithComplexPoly(paramArrayList, paramDouble);
  }
}