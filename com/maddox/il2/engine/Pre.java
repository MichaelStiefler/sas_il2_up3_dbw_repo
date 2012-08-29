// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Pre.java

package com.maddox.il2.engine;

import com.maddox.rts.Finger;
import com.maddox.sound.SoundPreset;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

// Referenced classes of package com.maddox.il2.engine:
//            Mesh, HierMesh, FObj

public class Pre
{

    public static java.lang.String load(java.lang.String s)
    {
        if(bRegister)
        {
            if(names == null)
                names = new HashMapInt();
            int i = com.maddox.rts.Finger.Int(s.toLowerCase());
            if(!names.containsKey(i))
                names.put(i, s);
        }
        return s;
    }

    public static boolean isRegister()
    {
        return bRegister;
    }

    public static void register(boolean flag)
    {
        bRegister = flag;
    }

    public static void save(java.lang.String s)
    {
        if(names == null)
            return;
        java.util.TreeSet treeset = new TreeSet();
        for(com.maddox.util.HashMapIntEntry hashmapintentry = names.nextEntry(null); hashmapintentry != null; hashmapintentry = names.nextEntry(hashmapintentry))
            treeset.add(hashmapintentry.getValue());

        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(s)));
            java.lang.String s1;
            for(java.util.Iterator iterator = treeset.iterator(); iterator.hasNext(); printwriter.println("preload " + s1))
                s1 = (java.lang.String)iterator.next();

            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("Preload file create failed: " + ioexception.getMessage());
        }
    }

    public static void loading(java.lang.String s)
    {
        int i;
        if(loaded == null)
            loaded = new LinkedList();
        i = s.length() - 4;
        if(i <= 0)
            break MISSING_BLOCK_LABEL_211;
        if(s.regionMatches(true, i, ".eff", 0, 4))
        {
            java.lang.Object obj = com.maddox.il2.engine.FObj.Get(s);
            loaded.add(obj);
            return;
        }
        if(s.regionMatches(true, i, ".mat", 0, 4))
        {
            java.lang.Object obj1 = com.maddox.il2.engine.FObj.Get(s);
            loaded.add(obj1);
            return;
        }
        if(s.regionMatches(true, i, ".sim", 0, 4))
        {
            com.maddox.il2.engine.Mesh mesh = new Mesh(s);
            loaded.add(mesh);
            return;
        }
        if(s.regionMatches(true, i, ".him", 0, 4))
        {
            com.maddox.il2.engine.HierMesh hiermesh = new HierMesh(s);
            loaded.add(hiermesh);
            return;
        }
        try
        {
            if(s.regionMatches(true, i, ".prs", 0, 4))
            {
                com.maddox.sound.SoundPreset.get(s.substring(0, i));
                return;
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Preload resource " + s + " failed: " + exception.getMessage());
        }
    }

    public static void clear()
    {
        if(loaded != null)
        {
            int i = loaded.size();
            for(int j = 0; j < i; j++)
                loaded.set(j, null);

            loaded.clear();
            loaded = null;
        }
    }

    private Pre()
    {
    }

    private static boolean bRegister = false;
    private static com.maddox.util.HashMapInt names = null;
    private static java.util.LinkedList loaded = null;

}
