// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GTreeModel.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GTreePath, GTreeModelListener, GTexRegion, GWindowCellEdit

public interface GTreeModel
{

    public abstract com.maddox.gwindow.GTreePath getRoot();

    public abstract java.lang.String pathToStr(com.maddox.gwindow.GTreePath gtreepath, boolean flag);

    public abstract com.maddox.gwindow.GTreePath strToPath(java.lang.String s, boolean flag);

    public abstract void addExcludePath(com.maddox.gwindow.GTreePath gtreepath);

    public abstract java.lang.Object getChild(com.maddox.gwindow.GTreePath gtreepath, int i);

    public abstract int getChildCount(com.maddox.gwindow.GTreePath gtreepath);

    public abstract boolean isLeaf(com.maddox.gwindow.GTreePath gtreepath);

    public abstract void addListener(com.maddox.gwindow.GTreeModelListener gtreemodellistener);

    public abstract void removeListener(com.maddox.gwindow.GTreeModelListener gtreemodellistener);

    public abstract com.maddox.gwindow.GTexRegion getIcon(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1);

    public abstract java.lang.String getString(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1);

    public abstract float getRenderWidth(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1);

    public abstract float getRenderHeight(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1);

    public abstract boolean render(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1, float f, float f1);

    public abstract boolean isEditable(com.maddox.gwindow.GTreePath gtreepath);

    public abstract com.maddox.gwindow.GWindowCellEdit getEdit(com.maddox.gwindow.GTreePath gtreepath, boolean flag);

    public abstract java.lang.Object getValueAt(com.maddox.gwindow.GTreePath gtreepath);

    public abstract void setValueAt(java.lang.Object obj, com.maddox.gwindow.GTreePath gtreepath);
}
