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

public class Pre
{
  private static boolean bRegister = false;
  private static HashMapInt names = null;

  private static LinkedList loaded = null;

  public static String load(String paramString)
  {
    if (bRegister) {
      if (names == null)
        names = new HashMapInt();
      int i = Finger.Int(paramString.toLowerCase());
      if (!names.containsKey(i))
        names.put(i, paramString);
    }
    return paramString;
  }
  public static boolean isRegister() {
    return bRegister; } 
  public static void register(boolean paramBoolean) { bRegister = paramBoolean; } 
  public static void save(String paramString) {
    if (names == null) return;
    TreeSet localTreeSet = new TreeSet();
    HashMapIntEntry localHashMapIntEntry = names.nextEntry(null);
    while (localHashMapIntEntry != null) {
      localTreeSet.add(localHashMapIntEntry.getValue());
      localHashMapIntEntry = names.nextEntry(localHashMapIntEntry);
    }
    try {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(paramString)));
      Iterator localIterator = localTreeSet.iterator();
      while (localIterator.hasNext()) {
        String str = (String)localIterator.next();
        localPrintWriter.println("preload " + str);
      }
      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("Preload file create failed: " + localIOException.getMessage());
    }
  }

  public static void loading(String paramString)
  {
    if (loaded == null)
      loaded = new LinkedList();
    int i = paramString.length() - 4;
    try {
      if (i > 0)
      {
        Object localObject;
        if (paramString.regionMatches(true, i, ".eff", 0, 4)) {
          localObject = FObj.Get(paramString);

          loaded.add(localObject);
          return;
        }
        if (paramString.regionMatches(true, i, ".mat", 0, 4)) {
          localObject = FObj.Get(paramString);
          loaded.add(localObject);
          return;
        }
        if (paramString.regionMatches(true, i, ".sim", 0, 4)) {
          localObject = new Mesh(paramString);
          loaded.add(localObject);
          return;
        }
        if (paramString.regionMatches(true, i, ".him", 0, 4)) {
          localObject = new HierMesh(paramString);
          loaded.add(localObject);
          return;
        }
        if (paramString.regionMatches(true, i, ".prs", 0, 4)) {
          SoundPreset.get(paramString.substring(0, i));
          return;
        }
      }
    } catch (Exception localException) {
      System.out.println("Preload resource " + paramString + " failed: " + localException.getMessage());
    }
  }

  public static void clear()
  {
    if (loaded != null) {
      int i = loaded.size();
      for (int j = 0; j < i; j++)
        loaded.set(j, null);
      loaded.clear();
      loaded = null;
    }
  }
}