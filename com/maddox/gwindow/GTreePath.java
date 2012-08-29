package com.maddox.gwindow;

public class GTreePath
{
  private GTreePath parentPath;
  private Object lastPathComponent;

  public GTreePath(Object[] paramArrayOfObject)
  {
    if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
      throw new IllegalArgumentException("path in GTreePath must be non null and not empty.");
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      if (paramArrayOfObject[i] == null) {
        if (i == 0)
          throw new IllegalArgumentException("path in GTreePath must be not empty.");
        this.lastPathComponent = paramArrayOfObject[(i - 1)];
        if (i > 1)
          this.parentPath = new GTreePath(paramArrayOfObject, i - 1);
        return;
      }
    }
    this.lastPathComponent = paramArrayOfObject[(paramArrayOfObject.length - 1)];
    if (paramArrayOfObject.length > 1)
      this.parentPath = new GTreePath(paramArrayOfObject, paramArrayOfObject.length - 1);
  }

  public GTreePath(Object paramObject)
  {
    if (paramObject == null)
      throw new IllegalArgumentException("path in GTreePath must be non null.");
    this.lastPathComponent = paramObject;
    this.parentPath = null;
  }

  protected GTreePath(GTreePath paramGTreePath, Object paramObject)
  {
    if (paramObject == null)
      throw new IllegalArgumentException("path in GTreePath must be non null.");
    this.parentPath = paramGTreePath;
    this.lastPathComponent = paramObject;
  }

  protected GTreePath(Object[] paramArrayOfObject, int paramInt)
  {
    this.lastPathComponent = paramArrayOfObject[(paramInt - 1)];
    if (paramInt > 1)
      this.parentPath = new GTreePath(paramArrayOfObject, paramInt - 1);
  }

  protected GTreePath()
  {
  }

  public Object[] getPath()
  {
    int i = getPathCount();
    Object[] arrayOfObject = new Object[i--];
    for (GTreePath localGTreePath = this; localGTreePath != null; localGTreePath = localGTreePath.parentPath) {
      arrayOfObject[(i--)] = localGTreePath.lastPathComponent;
    }
    return arrayOfObject;
  }

  public Object[] getPath(Object[] paramArrayOfObject)
  {
    int i = getPathCount();
    if ((paramArrayOfObject == null) || (paramArrayOfObject.length < i))
      paramArrayOfObject = new Object[i];
    i--;
    for (GTreePath localGTreePath = this; localGTreePath != null; localGTreePath = localGTreePath.parentPath) {
      paramArrayOfObject[(i--)] = localGTreePath.lastPathComponent;
    }
    return paramArrayOfObject;
  }

  public Object getLastPathComponent()
  {
    return this.lastPathComponent;
  }

  public int getPathCount()
  {
    int i = 0;
    for (GTreePath localGTreePath = this; localGTreePath != null; localGTreePath = localGTreePath.parentPath) {
      i++;
    }
    return i;
  }

  public Object getPathComponent(int paramInt)
  {
    int i = getPathCount();
    if ((paramInt < 0) || (paramInt >= i))
      throw new IllegalArgumentException("Index " + paramInt + " is out of the specified range");
    GTreePath localGTreePath = this;
    for (int j = i - 1; j != paramInt; j--) {
      localGTreePath = localGTreePath.parentPath;
    }
    return localGTreePath.lastPathComponent;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if ((paramObject instanceof GTreePath)) {
      GTreePath localGTreePath1 = (GTreePath)paramObject;
      if (getPathCount() != localGTreePath1.getPathCount())
        return false;
      for (GTreePath localGTreePath2 = this; localGTreePath2 != null; localGTreePath2 = localGTreePath2.parentPath) {
        if (!localGTreePath2.lastPathComponent.equals(localGTreePath1.lastPathComponent)) {
          return false;
        }
        localGTreePath1 = localGTreePath1.parentPath;
      }
      return true;
    }
    return false;
  }

  public int hashCode()
  {
    return this.lastPathComponent.hashCode();
  }

  public boolean isDescendant(GTreePath paramGTreePath)
  {
    if (paramGTreePath == this) {
      return true;
    }
    if (paramGTreePath != null) {
      int i = getPathCount();
      int j = paramGTreePath.getPathCount();
      if (j < i)
        return false;
      do
        paramGTreePath = paramGTreePath.getParentPath();
      while (j-- > i);

      return equals(paramGTreePath);
    }
    return false;
  }

  public GTreePath pathByAddingChild(Object paramObject)
  {
    if (paramObject == null)
      throw new NullPointerException("Null child not allowed");
    return new GTreePath(this, paramObject);
  }

  public GTreePath getParentPath()
  {
    return this.parentPath;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("[");
    int i = 0; int j = getPathCount();
    for (; i < j; i++) {
      if (i > 0)
        localStringBuffer.append(", ");
      localStringBuffer.append(getPathComponent(i));
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}