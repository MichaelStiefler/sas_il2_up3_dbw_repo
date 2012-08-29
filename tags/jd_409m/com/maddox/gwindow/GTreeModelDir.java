package com.maddox.gwindow;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class GTreeModelDir
  implements GTreeModel
{
  public GTreePath root;
  private static Filter filter = new Filter();

  private HashMap fileChild = new HashMap();

  private ArrayList listeners = new ArrayList();

  public GTreePath getRoot()
  {
    return this.root;
  }
  public String pathToStr(GTreePath paramGTreePath, boolean paramBoolean) {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramBoolean) {
      File localFile1 = (File)this.root.getLastPathComponent();
      localStringBuffer.append(localFile1.getAbsolutePath());
    }
    int i = paramGTreePath.getPathCount();
    int j = this.root.getPathCount();
    for (int k = j; k < i; k++) {
      if ((paramBoolean) || (k > j))
        localStringBuffer.append("/");
      File localFile2 = (File)paramGTreePath.getPathComponent(k);
      localStringBuffer.append(localFile2.getName());
    }
    return localStringBuffer.toString();
  }

  public GTreePath strToPath(String paramString, boolean paramBoolean) {
    int i = 0;
    if (this.root != null) {
      String str = pathToStr(this.root, true);
      if (paramBoolean) {
        if (!paramString.startsWith(str))
          return null;
      }
      else paramString = str + "/" + paramString;

      i = str.length() + 1;
    }
    int j = paramString.length();
    GTreePath localGTreePath = this.root;
    int k = 0;
    int m = i;
    int n = i;
    for (; n < j; n++) {
      k = paramString.charAt(n);
      if (((k != 92) && (k != 47)) || 
        (n <= m)) continue;
      localGTreePath = _fromString(localGTreePath, paramString, m, n);
      if (localGTreePath == null)
        return null;
      m = n + 1;
    }

    if ((k != 92) && (k != 47) && (n > m))
      localGTreePath = _fromString(localGTreePath, paramString, m, n);
    return localGTreePath;
  }

  private GTreePath _fromString(GTreePath paramGTreePath, String paramString, int paramInt1, int paramInt2) {
    String str = paramString.substring(paramInt1, paramInt2);
    if (paramGTreePath != null) {
      File[] arrayOfFile = childs((File)paramGTreePath.getLastPathComponent());
      if ((arrayOfFile == null) || (arrayOfFile.length == 0))
        return null;
      for (int i = 0; i < arrayOfFile.length; i++)
        if (str.equals(arrayOfFile[i].getName()))
          return paramGTreePath.pathByAddingChild(arrayOfFile[i]);
    } else {
      return new GTreePath(new File(str));
    }
    return null;
  }

  public void addExcludePath(GTreePath paramGTreePath) {
    GTreePath localGTreePath = paramGTreePath.getParentPath();
    if (localGTreePath == null)
      return;
    File localFile1 = (File)localGTreePath.getLastPathComponent();
    File[] arrayOfFile1 = childs(localFile1);
    if ((arrayOfFile1 == null) || (arrayOfFile1.length == 0))
      return;
    File localFile2 = (File)paramGTreePath.getLastPathComponent();
    for (int i = 0; i < arrayOfFile1.length; i++)
      if (localFile2.equals(arrayOfFile1[i])) {
        File[] arrayOfFile2 = new File[arrayOfFile1.length - 1];
        int j = 0;
        int k = 0;
        for (; j < arrayOfFile1.length; j++) {
          if (j != i)
            arrayOfFile2[(k++)] = arrayOfFile1[j];
        }
        this.fileChild.put(localFile1, arrayOfFile2);
        changed();
        return;
      }
  }

  private File[] childs(File paramFile) {
    File[] arrayOfFile = (File[])this.fileChild.get(paramFile);
    if (arrayOfFile == null) {
      arrayOfFile = paramFile.listFiles(filter);
      if (arrayOfFile == null) arrayOfFile = new File[0];
      this.fileChild.put(paramFile, arrayOfFile);
    }
    return arrayOfFile;
  }

  public Object getChild(GTreePath paramGTreePath, int paramInt)
  {
    File localFile = (File)paramGTreePath.getLastPathComponent();
    File[] arrayOfFile = childs(localFile);
    if ((arrayOfFile != null) && (paramInt >= 0) && (paramInt < arrayOfFile.length))
      return arrayOfFile[paramInt];
    return null;
  }

  public int getChildCount(GTreePath paramGTreePath) {
    File localFile = (File)paramGTreePath.getLastPathComponent();
    File[] arrayOfFile = childs(localFile);
    if (arrayOfFile != null)
      return arrayOfFile.length;
    return 0;
  }

  public boolean isLeaf(GTreePath paramGTreePath) {
    File localFile = (File)paramGTreePath.getLastPathComponent();
    File[] arrayOfFile = childs(localFile);
    return (arrayOfFile == null) || (arrayOfFile.length <= 0);
  }

  public void addListener(GTreeModelListener paramGTreeModelListener) {
    if (this.listeners.contains(paramGTreeModelListener)) return;
    this.listeners.add(paramGTreeModelListener);
  }
  public void removeListener(GTreeModelListener paramGTreeModelListener) {
    this.listeners.remove(paramGTreeModelListener);
  }

  private void changed()
  {
    int i = this.listeners.size();
    if (i == 0) return;
    ArrayList localArrayList = new ArrayList(this.listeners);
    for (int j = 0; j < i; j++)
      ((GTreeModelListener)localArrayList.get(j)).treeModelChanged(this);
  }

  public GTexRegion getIcon(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2) {
    return null;
  }

  public String getString(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2) {
    File localFile = (File)paramGTreePath.getLastPathComponent();
    if (this.root.equals(paramGTreePath)) {
      return localFile.getAbsolutePath();
    }
    return localFile.getName();
  }

  public float getRenderWidth(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2)
  {
    return -1.0F;
  }

  public float getRenderHeight(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2) {
    return -1.0F;
  }

  public boolean render(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public boolean isEditable(GTreePath paramGTreePath)
  {
    return false;
  }
  public GWindowCellEdit getEdit(GTreePath paramGTreePath, boolean paramBoolean) {
    return null;
  }

  public Object getValueAt(GTreePath paramGTreePath)
  {
    return null;
  }
  public void setValueAt(Object paramObject, GTreePath paramGTreePath) {
  }
  public GTreeModelDir(String paramString) {
    File localFile = new File(paramString);
    this.root = new GTreePath(localFile);
  }

  static class Filter
    implements FileFilter
  {
    public boolean accept(File paramFile)
    {
      return paramFile.isDirectory();
    }
  }
}