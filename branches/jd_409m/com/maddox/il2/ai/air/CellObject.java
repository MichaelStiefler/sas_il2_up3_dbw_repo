package com.maddox.il2.ai.air;

import java.io.PrintStream;
import java.io.Serializable;

public class CellObject
  implements Serializable
{
  private CellObject[][] cells;
  private String id = "*";
  private double cellX;
  private double cellY;
  private double cellSize = 0.5D;

  public void setCells(CellObject[][] paramArrayOfCellObject)
  {
    this.cells = paramArrayOfCellObject; } 
  public CellObject[][] getCells() { return this.cells;
  }

  public String getId()
  {
    return this.id; } 
  public void setId(String paramString) { this.id = paramString;
  }

  public void setCellX(double paramDouble)
  {
    this.cellX = paramDouble; } 
  public double getCellX() { return this.cellX;
  }

  public void setCellY(double paramDouble)
  {
    this.cellY = paramDouble; } 
  public double getCellY() { return this.cellY;
  }

  public void setCoordinates(double paramDouble1, double paramDouble2)
  {
    setCellX(paramDouble1);
    setCellY(paramDouble2);
  }

  public int getWidth()
  {
    if (this.cells == null) return 0;
    return this.cells.length;
  }

  public int getHeight()
  {
    if (this.cells == null) return 0;
    if (this.cells[0] == null) return 0;
    return this.cells[0].length;
  }

  public double getXCoordinate()
  {
    if (this.cells == null) return 0.0D;
    if (this.cells[0][0] == null) return 0.0D;
    return this.cells[0][0].getCellX();
  }

  public double getYCoordinate()
  {
    if (this.cells == null) return 0.0D;
    if (this.cells[0][0] == null) return 0.0D;
    return this.cells[0][0].getCellY();
  }

  public double getCellSize()
  {
    return this.cellSize; } 
  public void setCellSize(double paramDouble) { this.cellSize = paramDouble;
  }

  public void setWorldCoordinates(double paramDouble1, double paramDouble2)
  {
    if (this.cells == null) return;
    if (this.cells[0][0] == null) return;
    double d = this.cells[0][0].getCellSize();
    for (int i = 0; i < this.cells.length; i++)
      for (int j = 0; j < this.cells[0].length; j++)
        if (this.cells[i][j] != null)
          this.cells[i][j].setCoordinates(paramDouble1 + i * d, paramDouble2 + j * d);
  }

  public CellObject getClone()
  {
    if (this.cells == null) return null;
    CellObject[][] arrayOfCellObject = new CellObject[getWidth()][getHeight()];
    for (int i = 0; i < this.cells.length; i++) {
      for (int j = 0; j < this.cells[0].length; j++) {
        if (this.cells[i][j] != null)
          arrayOfCellObject[i][j] = this.cells[i][j];
      }
    }
    return new CellObject(arrayOfCellObject);
  }

  public void clearCells()
  {
    if (this.cells == null) return;
    for (int i = 0; i < this.cells.length; i++)
      for (int j = 0; j < this.cells[0].length; j++)
        this.cells[i][j] = null;
  }

  public int calcFilledCells()
  {
    int i = 0;
    if (this.cells == null) return 0;
    for (int j = 0; j < this.cells.length; j++) {
      for (int k = 0; k < this.cells[0].length; k++) {
        if (this.cells[j][k] == null) continue; i++;
      }
    }
    return i;
  }

  public int calcFreeCells()
  {
    int i = 0;
    if (this.cells == null) return 0;
    if (this.cells[0][0] == null) return 0;
    for (int j = 0; j < this.cells.length; j++) {
      for (int k = 0; k < this.cells[0].length; k++) {
        if (this.cells[j][k] != null) continue; i++;
      }
    }
    return i;
  }

  public void debugPrint()
  {
    if (this.cells == null) return;
    System.out.println();
    for (int i = 0; i < this.cells[0].length; i++) {
      for (int j = 0; j < this.cells.length; j++) {
        if (this.cells[j][i] != null)
          System.out.print(this.cells[j][i].getId());
        else
          System.out.print("_");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void reInitReferences()
  {
    if (this.cells == null) return;
    for (int i = 0; i < this.cells.length; i++)
      for (int j = 0; j < this.cells[0].length; j++) {
        if (this.cells[i][j] == null) continue; this.cells[i][j] = this;
      }
  }

  public CellObject(CellObject[][] paramArrayOfCellObject)
  {
    this.cells = paramArrayOfCellObject;
    reInitReferences();
  }

  public CellObject()
  {
    this.cells = new CellObject[1][1];
    this.cells[0][0] = this;
  }
}