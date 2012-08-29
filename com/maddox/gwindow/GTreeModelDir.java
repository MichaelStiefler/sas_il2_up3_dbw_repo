// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GTreeModelDir.java

package com.maddox.gwindow;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.maddox.gwindow:
//            GTreePath, GTreeModelListener, GTreeModel, GTexRegion, 
//            GWindowCellEdit

public class GTreeModelDir
    implements com.maddox.gwindow.GTreeModel
{
    static class Filter
        implements java.io.FileFilter
    {

        public boolean accept(java.io.File file)
        {
            return file.isDirectory();
        }

        Filter()
        {
        }
    }


    public com.maddox.gwindow.GTreePath getRoot()
    {
        return root;
    }

    public java.lang.String pathToStr(com.maddox.gwindow.GTreePath gtreepath, boolean flag)
    {
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        if(flag)
        {
            java.io.File file = (java.io.File)root.getLastPathComponent();
            stringbuffer.append(file.getAbsolutePath());
        }
        int i = gtreepath.getPathCount();
        int j = root.getPathCount();
        for(int k = j; k < i; k++)
        {
            if(flag || k > j)
                stringbuffer.append("/");
            java.io.File file1 = (java.io.File)gtreepath.getPathComponent(k);
            stringbuffer.append(file1.getName());
        }

        return stringbuffer.toString();
    }

    public com.maddox.gwindow.GTreePath strToPath(java.lang.String s, boolean flag)
    {
        int i = 0;
        if(root != null)
        {
            java.lang.String s1 = pathToStr(root, true);
            if(flag)
            {
                if(!s.startsWith(s1))
                    return null;
            } else
            {
                s = s1 + "/" + s;
            }
            i = s1.length() + 1;
        }
        int j = s.length();
        com.maddox.gwindow.GTreePath gtreepath = root;
        int k = 0;
        int l = i;
        int i1;
        for(i1 = i; i1 < j; i1++)
        {
            k = s.charAt(i1);
            if((k == 92 || k == 47) && i1 > l)
            {
                gtreepath = _fromString(gtreepath, s, l, i1);
                if(gtreepath == null)
                    return null;
                l = i1 + 1;
            }
        }

        if(k != 92 && k != 47 && i1 > l)
            gtreepath = _fromString(gtreepath, s, l, i1);
        return gtreepath;
    }

    private com.maddox.gwindow.GTreePath _fromString(com.maddox.gwindow.GTreePath gtreepath, java.lang.String s, int i, int j)
    {
        java.lang.String s1 = s.substring(i, j);
        if(gtreepath != null)
        {
            java.io.File afile[] = childs((java.io.File)gtreepath.getLastPathComponent());
            if(afile == null || afile.length == 0)
                return null;
            for(int k = 0; k < afile.length; k++)
                if(s1.equals(afile[k].getName()))
                    return gtreepath.pathByAddingChild(afile[k]);

        } else
        {
            return new GTreePath(new File(s1));
        }
        return null;
    }

    public void addExcludePath(com.maddox.gwindow.GTreePath gtreepath)
    {
        com.maddox.gwindow.GTreePath gtreepath1 = gtreepath.getParentPath();
        if(gtreepath1 == null)
            return;
        java.io.File file = (java.io.File)gtreepath1.getLastPathComponent();
        java.io.File afile[] = childs(file);
        if(afile == null || afile.length == 0)
            return;
        java.io.File file1 = (java.io.File)gtreepath.getLastPathComponent();
        for(int i = 0; i < afile.length; i++)
            if(file1.equals(afile[i]))
            {
                java.io.File afile1[] = new java.io.File[afile.length - 1];
                int j = 0;
                int k = 0;
                for(; j < afile.length; j++)
                    if(j != i)
                        afile1[k++] = afile[j];

                fileChild.put(file, afile1);
                changed();
                return;
            }

    }

    private java.io.File[] childs(java.io.File file)
    {
        java.io.File afile[] = (java.io.File[])fileChild.get(file);
        if(afile == null)
        {
            afile = file.listFiles(filter);
            if(afile == null)
                afile = new java.io.File[0];
            fileChild.put(file, afile);
        }
        return afile;
    }

    public java.lang.Object getChild(com.maddox.gwindow.GTreePath gtreepath, int i)
    {
        java.io.File file = (java.io.File)gtreepath.getLastPathComponent();
        java.io.File afile[] = childs(file);
        if(afile != null && i >= 0 && i < afile.length)
            return afile[i];
        else
            return null;
    }

    public int getChildCount(com.maddox.gwindow.GTreePath gtreepath)
    {
        java.io.File file = (java.io.File)gtreepath.getLastPathComponent();
        java.io.File afile[] = childs(file);
        if(afile != null)
            return afile.length;
        else
            return 0;
    }

    public boolean isLeaf(com.maddox.gwindow.GTreePath gtreepath)
    {
        java.io.File file = (java.io.File)gtreepath.getLastPathComponent();
        java.io.File afile[] = childs(file);
        return afile == null || afile.length <= 0;
    }

    public void addListener(com.maddox.gwindow.GTreeModelListener gtreemodellistener)
    {
        if(listeners.contains(gtreemodellistener))
        {
            return;
        } else
        {
            listeners.add(gtreemodellistener);
            return;
        }
    }

    public void removeListener(com.maddox.gwindow.GTreeModelListener gtreemodellistener)
    {
        listeners.remove(gtreemodellistener);
    }

    private void changed()
    {
        int i = listeners.size();
        if(i == 0)
            return;
        java.util.ArrayList arraylist = new ArrayList(listeners);
        for(int j = 0; j < i; j++)
            ((com.maddox.gwindow.GTreeModelListener)arraylist.get(j)).treeModelChanged(this);

    }

    public com.maddox.gwindow.GTexRegion getIcon(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1)
    {
        return null;
    }

    public java.lang.String getString(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1)
    {
        java.io.File file = (java.io.File)gtreepath.getLastPathComponent();
        if(root.equals(gtreepath))
            return file.getAbsolutePath();
        else
            return file.getName();
    }

    public float getRenderWidth(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1)
    {
        return -1F;
    }

    public float getRenderHeight(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1)
    {
        return -1F;
    }

    public boolean render(com.maddox.gwindow.GTreePath gtreepath, boolean flag, boolean flag1, float f, float f1)
    {
        return false;
    }

    public boolean isEditable(com.maddox.gwindow.GTreePath gtreepath)
    {
        return false;
    }

    public com.maddox.gwindow.GWindowCellEdit getEdit(com.maddox.gwindow.GTreePath gtreepath, boolean flag)
    {
        return null;
    }

    public java.lang.Object getValueAt(com.maddox.gwindow.GTreePath gtreepath)
    {
        return null;
    }

    public void setValueAt(java.lang.Object obj, com.maddox.gwindow.GTreePath gtreepath)
    {
    }

    public GTreeModelDir(java.lang.String s)
    {
        fileChild = new HashMap();
        listeners = new ArrayList();
        java.io.File file = new File(s);
        root = new GTreePath(file);
    }

    public com.maddox.gwindow.GTreePath root;
    private static com.maddox.gwindow.Filter filter = new Filter();
    private java.util.HashMap fileChild;
    private java.util.ArrayList listeners;

}
