package com.maddox.util;

import com.maddox.rts.SectFile;
import java.util.ArrayList;

public class TableFunction2
{
  private String name;
  private float[] arg0;
  private float[] arg1;
  private float[][] val;
  private static ArrayList functions = new ArrayList();

  private static TableFunction2 find(String paramString) {
    for (int i = 0; i < functions.size(); i++) {
      if (paramString.equalsIgnoreCase(((TableFunction2)(TableFunction2)functions.get(i)).name)) {
        return (TableFunction2)(TableFunction2)functions.get(i);
      }
    }
    return null;
  }

  public static TableFunction2 Get(String paramString) {
    TableFunction2 localTableFunction2 = find(paramString);
    if (localTableFunction2 == null) {
      throw new RuntimeException("TableFunction2 '" + paramString + "' is unknown (not loaded?)");
    }
    return localTableFunction2;
  }

  public static void Load(String paramString1, SectFile paramSectFile, String paramString2, int paramInt1, int paramInt2)
  {
    if (find(paramString1) != null) {
      throw new RuntimeException("TableFunction2 '" + paramString1 + "' already loaded");
    }
    TableFunction2 localTableFunction2 = new TableFunction2(paramString1, paramSectFile, paramString2);
    localTableFunction2.check(paramInt1, paramInt2);
    functions.add(localTableFunction2);
  }

  private void TableError(String paramString)
  {
    throw new RuntimeException("TableFunction2 '" + this.name + "'error: " + paramString);
  }

  private TableFunction2(String paramString1, SectFile paramSectFile, String paramString2) {
    this.name = new String(paramString1);

    int i = paramSectFile.sectionIndex(paramString2);

    int j = 0;
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, 0));
    while (localNumberTokenizer.next() != null) {
      j++;
    }
    if (j <= 0) {
      TableError("# arg0");
    }

    int k = paramSectFile.vars(i) - 1;
    if (k <= 0) {
      TableError("# arg1");
    }

    this.arg0 = new float[j];
    this.arg1 = new float[k];
    this.val = new float[k][j];

    localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, 0));
    for (int m = 0; m < j; m++) {
      this.arg0[m] = localNumberTokenizer.nextFloat();
    }
    for (m = 0; m < k; m++) {
      localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, 1 + m));
      this.arg1[m] = localNumberTokenizer.nextFloat();
      for (int n = 0; n < j; n++) {
        this.val[m][n] = localNumberTokenizer.nextFloat();
      }

    }

    for (m = 1; m < j; m++) {
      if (this.arg0[(m - 1)] >= this.arg0[m]) {
        TableError("arg0 non-increasing");
      }
    }
    for (m = 1; m < k; m++)
      if (this.arg1[(m - 1)] >= this.arg1[m])
        TableError("arg1 non-increasing");
  }

  private void check(int paramInt1, int paramInt2)
  {
    int i = this.arg0.length;
    int j = this.arg1.length;
    for (int k = 0; k < j; k++)
      for (int m = 0; m < i; m++) {
        if ((paramInt1 > 0) && (m > 0) && (this.val[k][(m - 1)] > this.val[k][m])) {
          TableError("decreased val for arg0");
        }
        if ((paramInt1 < 0) && (m > 0) && (this.val[k][(m - 1)] < this.val[k][m])) {
          TableError("increased val for arg0");
        }
        if ((paramInt2 > 0) && (k > 0) && (this.val[(k - 1)][m] > this.val[k][m])) {
          TableError("decreased val for arg1");
        }
        if ((paramInt2 < 0) && (k > 0) && (this.val[(k - 1)][m] < this.val[k][m]))
          TableError("increased val for arg1");
      }
  }

  public float Value(float paramFloat1, float paramFloat2)
  {
    int i = this.arg0.length;
    int j = this.arg1.length;
    int m;
    int k;
    float f1;
    int n;
    if (paramFloat1 <= this.arg0[0]) {
      k = m = 0;
      f1 = 0.0F;
    } else if (paramFloat1 >= this.arg0[(i - 1)]) {
      k = m = i - 1;
      f1 = 0.0F;
    } else {
      k = 0;
      m = i - 1;
      while (m - k > 1) {
        n = m + k >> 1;
        if (paramFloat1 >= this.arg0[n])
          k = n;
        else {
          m = n;
        }
      }
      f1 = (paramFloat1 - this.arg0[k]) / (this.arg0[m] - this.arg0[k]);
    }
    int i1;
    float f2;
    if (paramFloat2 <= this.arg1[0]) {
      n = i1 = 0;
      f2 = 0.0F;
    } else if (paramFloat2 >= this.arg1[(j - 1)]) {
      n = i1 = j - 1;
      f2 = 0.0F;
    } else {
      n = 0;
      i1 = j - 1;
      while (i1 - n > 1) {
        int i2 = i1 + n >> 1;
        if (paramFloat2 >= this.arg1[i2])
          n = i2;
        else {
          i1 = i2;
        }
      }
      f2 = (paramFloat2 - this.arg1[n]) / (this.arg1[i1] - this.arg1[n]);
    }

    float f3 = this.val[n][k];
    float f4 = this.val[n][m];
    float f5 = this.val[i1][k];
    float f6 = this.val[i1][m];
    float f7 = f3 + (f4 - f3) * f1;
    float f8 = f5 + (f6 - f5) * f1;

    return f7 + (f8 - f7) * f2;
  }
}