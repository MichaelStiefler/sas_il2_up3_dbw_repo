package com.maddox.gwindow;

public abstract interface GTreeModel
{
  public abstract GTreePath getRoot();

  public abstract String pathToStr(GTreePath paramGTreePath, boolean paramBoolean);

  public abstract GTreePath strToPath(String paramString, boolean paramBoolean);

  public abstract void addExcludePath(GTreePath paramGTreePath);

  public abstract Object getChild(GTreePath paramGTreePath, int paramInt);

  public abstract int getChildCount(GTreePath paramGTreePath);

  public abstract boolean isLeaf(GTreePath paramGTreePath);

  public abstract void addListener(GTreeModelListener paramGTreeModelListener);

  public abstract void removeListener(GTreeModelListener paramGTreeModelListener);

  public abstract GTexRegion getIcon(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2);

  public abstract String getString(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2);

  public abstract float getRenderWidth(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2);

  public abstract float getRenderHeight(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2);

  public abstract boolean render(GTreePath paramGTreePath, boolean paramBoolean1, boolean paramBoolean2, float paramFloat1, float paramFloat2);

  public abstract boolean isEditable(GTreePath paramGTreePath);

  public abstract GWindowCellEdit getEdit(GTreePath paramGTreePath, boolean paramBoolean);

  public abstract Object getValueAt(GTreePath paramGTreePath);

  public abstract void setValueAt(Object paramObject, GTreePath paramGTreePath);
}