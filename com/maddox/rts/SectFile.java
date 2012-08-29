// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SectFile.java

package com.maddox.rts;

import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.maddox.rts:
//            SFSReader, KryptoOutputFilter, HomePath, Finger, 
//            ObjIO

public class SectFile
{

    public int[] kryptoGetKey()
    {
        return kryptoKey;
    }

    public void kryptoSetKey(int ai[])
    {
        kryptoKey = ai;
        if(kryptoKey != null && kryptoKey.length == 0)
            kryptoKey = null;
    }

    public SectFile(java.lang.String s)
    {
        this(s, 0, true, null, null, false);
    }

    public SectFile(java.lang.String s, int i)
    {
        this(s, i, true, null, null, false);
    }

    public SectFile(java.lang.String s, int i, boolean flag)
    {
        this(s, i, flag, null, null, false);
    }

    public SectFile(java.lang.String s, int i, boolean flag, int ai[])
    {
        this(s, i, flag, ai, null, false);
    }

    public SectFile(java.lang.String s, int i, boolean flag, int ai[], java.lang.String s1, boolean flag1)
    {
        sectNames = null;
        hashNames = null;
        sectVars = null;
        hashVars = null;
        sectValues = null;
        fileName = null;
        saveOnChange = false;
        bChanged = false;
        bNotSave = false;
        bComment = true;
        bTypeErrorMessages = true;
        kryptoKey = null;
        enc = null;
        bUseUnicodeTo8bit = false;
        bTypeErrorMessages = flag;
        enc = s1;
        bUseUnicodeTo8bit = flag1;
        kryptoSetKey(ai);
        switch(i & 3)
        {
        case 0: // '\0'
            bNotSave = true;
            break;

        case 2: // '\002'
            saveOnChange = true;
            break;
        }
        if((i & 4) == 4)
            bComment = false;
        fileName = s;
        if(!loadFile())
        {
            if(!createFile())
                return;
            loadFile();
        }
    }

    public SectFile(java.io.InputStreamReader inputstreamreader)
    {
        sectNames = null;
        hashNames = null;
        sectVars = null;
        hashVars = null;
        sectValues = null;
        fileName = null;
        saveOnChange = false;
        bChanged = false;
        bNotSave = false;
        bComment = true;
        bTypeErrorMessages = true;
        kryptoKey = null;
        enc = null;
        bUseUnicodeTo8bit = false;
        bTypeErrorMessages = true;
        bNotSave = true;
        bChanged = false;
        fileName = "";
        loadFile(inputstreamreader);
    }

    public SectFile()
    {
        sectNames = null;
        hashNames = null;
        sectVars = null;
        hashVars = null;
        sectValues = null;
        fileName = null;
        saveOnChange = false;
        bChanged = false;
        bNotSave = false;
        bComment = true;
        bTypeErrorMessages = true;
        kryptoKey = null;
        enc = null;
        bUseUnicodeTo8bit = false;
        bNotSave = true;
        sectNames = new ArrayList();
        sectVars = new ArrayList();
        sectValues = new ArrayList();
        bChanged = false;
    }

    public boolean loadFile()
    {
        return loadFile(null);
    }

    private boolean loadFile(java.io.InputStreamReader inputstreamreader)
    {
        sectNames = new ArrayList();
        sectVars = new ArrayList();
        sectValues = new ArrayList();
        clearIndexes();
        bChanged = false;
        java.util.ArrayList arraylist = null;
        java.util.ArrayList arraylist1 = null;
        java.io.BufferedReader bufferedreader = null;
        if(inputstreamreader != null)
            bufferedreader = new BufferedReader(inputstreamreader);
        else
        if(enc == null)
        {
            if(kryptoKey != null)
                bufferedreader = new BufferedReader(new SFSReader(fileName, kryptoKey));
            else
                bufferedreader = new BufferedReader(new SFSReader(fileName));
        } else
        if(kryptoKey != null)
            bufferedreader = new BufferedReader(new SFSReader(fileName, enc, kryptoKey));
        else
            bufferedreader = new BufferedReader(new SFSReader(fileName, enc));
        do
        {
            java.lang.String s = bufferedreader.readLine();
            if(s == null)
                break;
            if(bUseUnicodeTo8bit)
                s = com.maddox.util.UnicodeTo8bit.load(s, false);
            int i = s.length();
            if(i != 0)
            {
                if(strBuf.length < i)
                    strBuf = new char[i];
                s.getChars(0, i, strBuf, 0);
                strPtr = 0;
                strEnd = i;
                removeComments();
                if(strEnd - strPtr > 0)
                    if(strBuf[strPtr] == '[' && strBuf[strEnd - 1] == ']')
                    {
                        if(strEnd - strPtr > 2)
                        {
                            strPtr++;
                            strEnd--;
                            arraylist = new ArrayList();
                            arraylist1 = new ArrayList();
                            sectNames.add(new String(strBuf, strPtr, strEnd - strPtr));
                            sectVars.add(arraylist);
                            sectValues.add(arraylist1);
                        }
                    } else
                    if(arraylist != null)
                    {
                        java.lang.String s1 = readVariable();
                        if(s1 != null)
                        {
                            arraylist.add(s1);
                            arraylist1.add(readValue());
                        }
                    }
            }
        } while(true);
        if(inputstreamreader == null)
            bufferedreader.close();
        return true;
        java.io.IOException ioexception;
        ioexception;
        if(bTypeErrorMessages)
        {
            java.lang.System.out.println("SectFile load failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
        return false;
    }

    private void removeComments()
    {
        for(; strPtr < strEnd && strBuf[strPtr] <= ' '; strPtr++);
        if(bComment)
        {
            int i = strPtr;
            boolean flag = false;
            for(; i < strEnd; i++)
            {
                char c = strBuf[i];
                if(c == '\t')
                    strBuf[i] = c = ' ';
                if(c < ' ' || c == '#' || c == ';')
                {
                    strEnd = i;
                    break;
                }
                if(c == '/')
                {
                    if(flag)
                    {
                        strEnd = i - 1;
                        break;
                    }
                    flag = true;
                } else
                {
                    flag = false;
                }
            }

        }
        for(; strEnd > strPtr && strBuf[strEnd - 1] <= ' '; strEnd--);
    }

    private java.lang.String readVariable()
    {
        int i;
        for(i = strPtr; i < strEnd && strBuf[i] > ' '; i++);
        java.lang.String s = new String(strBuf, strPtr, i - strPtr);
        strPtr = i;
        return s;
    }

    private java.lang.String readValue()
    {
        for(; strPtr < strEnd && strBuf[strPtr] <= ' '; strPtr++);
        if(strPtr >= strEnd)
            return "";
        else
            return new String(strBuf, strPtr, strEnd - strPtr);
    }

    protected boolean createFile()
    {
        if(bNotSave)
            return false;
        java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(fileName, 0))));
        printwriter.close();
        return true;
        java.io.IOException ioexception;
        ioexception;
        if(bTypeErrorMessages)
        {
            java.lang.System.out.println("SectFile create failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
        return false;
    }

    public boolean saveFile(java.lang.String s)
    {
        java.io.PrintWriter printwriter = null;
        if(enc == null)
        {
            if(kryptoKey != null)
                printwriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new KryptoOutputFilter(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0)), kryptoKey))));
            else
                printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(s, 0))));
        } else
        if(kryptoKey != null)
            printwriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new KryptoOutputFilter(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0)), kryptoKey), enc)));
        else
            printwriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0)), enc)));
        if(bUseUnicodeTo8bit)
        {
            for(int i = 0; i < sectNames.size(); i++)
            {
                printwriter.println('[' + com.maddox.util.UnicodeTo8bit.save((java.lang.String)(java.lang.String)sectNames.get(i), false) + ']');
                java.util.List list = (java.util.List)sectVars.get(i);
                java.util.List list2 = (java.util.List)sectValues.get(i);
                for(int k = 0; k < list.size(); k++)
                {
                    java.lang.String s1 = (java.lang.String)list.get(k);
                    java.lang.String s3 = (java.lang.String)list2.get(k);
                    if(s3.length() > 0)
                        printwriter.println("  " + com.maddox.util.UnicodeTo8bit.save(s1, false) + ' ' + com.maddox.util.UnicodeTo8bit.save(s3, false));
                    else
                        printwriter.println("  " + com.maddox.util.UnicodeTo8bit.save(s1, false));
                }

            }

        } else
        {
            for(int j = 0; j < sectNames.size(); j++)
            {
                printwriter.println('[' + (java.lang.String)(java.lang.String)sectNames.get(j) + ']');
                java.util.List list1 = (java.util.List)sectVars.get(j);
                java.util.List list3 = (java.util.List)sectValues.get(j);
                for(int l = 0; l < list1.size(); l++)
                {
                    java.lang.String s2 = (java.lang.String)list1.get(l);
                    java.lang.String s4 = (java.lang.String)list3.get(l);
                    if(s4.length() > 0)
                        printwriter.println("  " + s2 + ' ' + s4);
                    else
                        printwriter.println("  " + s2);
                }

            }

        }
        printwriter.close();
        return true;
        java.io.IOException ioexception;
        ioexception;
        if(bTypeErrorMessages)
        {
            java.lang.System.out.println("SectFile save failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
        return false;
    }

    public void saveFile()
    {
        if(bNotSave)
        {
            return;
        } else
        {
            bChanged = !saveFile(fileName);
            return;
        }
    }

    public boolean isChanged()
    {
        return bChanged;
    }

    private void checkSaved()
    {
        if(saveOnChange)
            saveFile();
        else
            bChanged = true;
    }

    protected void finalize()
    {
        if(bChanged)
            saveFile();
    }

    private long _finger(long l, java.util.ArrayList arraylist)
    {
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = (java.lang.String)arraylist.get(j);
            if(s != null && s.length() > 0)
                l = com.maddox.rts.Finger.incLong(l, s);
        }

        return l;
    }

    public long finger()
    {
        return finger(0L);
    }

    public long finger(long l)
    {
        long l1 = _finger(l, sectNames);
        int i = sectVars.size();
        for(int j = 0; j < i; j++)
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)sectVars.get(j);
            if(arraylist != null)
                l1 = _finger(l1, arraylist);
        }

        i = sectValues.size();
        for(int k = 0; k < i; k++)
        {
            java.util.ArrayList arraylist1 = (java.util.ArrayList)sectValues.get(k);
            if(arraylist1 != null)
                l1 = _finger(l1, arraylist1);
        }

        return l1;
    }

    public long fingerExcludeSectPrefix(java.lang.String s)
    {
        long l = 0L;
        int i = sectNames.size();
        for(int j = 0; j < i; j++)
        {
            java.lang.String s1 = (java.lang.String)sectNames.get(j);
            if(s1.startsWith(s))
                continue;
            l = com.maddox.rts.Finger.incLong(l, s1);
            java.util.ArrayList arraylist = (java.util.ArrayList)sectVars.get(j);
            if(arraylist != null)
                l = _finger(l, arraylist);
            arraylist = (java.util.ArrayList)sectValues.get(j);
            if(arraylist != null)
                l = _finger(l, arraylist);
        }

        return l;
    }

    private int listFindString(java.util.List list, int i, java.lang.String s)
    {
        for(; i < list.size(); i++)
        {
            java.lang.String s1 = (java.lang.String)list.get(i);
            if(s.compareToIgnoreCase(s1) == 0)
                return i;
        }

        return -1;
    }

    public java.lang.String fileName()
    {
        return fileName;
    }

    public void setFileName(java.lang.String s)
    {
        fileName = s;
    }

    public void createIndexes()
    {
        clearIndexes();
        int i = sectNames.size();
        hashNames = new HashMap();
        hashVars = new ArrayList(i);
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = (java.lang.String)sectNames.get(j);
            if(!hashNames.containsKey(s))
                hashNames.put(s, new Integer(j));
            java.util.List list = (java.util.List)sectVars.get(j);
            int k = list.size();
            java.util.HashMap hashmap = new HashMap();
            hashVars.add(hashmap);
            for(int l = 0; l < k; l++)
            {
                java.lang.String s1 = (java.lang.String)list.get(l);
                if(!hashmap.containsKey(s1))
                    hashmap.put(s1, new Integer(l));
            }

        }

    }

    public void clearIndexes()
    {
        hashNames = null;
        hashVars = null;
    }

    public int sections()
    {
        return sectNames.size();
    }

    public java.lang.String sectionName(int i)
    {
        return (java.lang.String)sectNames.get(i);
    }

    public void sectionRename(int i, java.lang.String s)
    {
        sectNames.set(i, s);
        checkSaved();
    }

    public int sectionAdd(java.lang.String s)
    {
        clearIndexes();
        java.util.ArrayList arraylist = new ArrayList();
        java.util.ArrayList arraylist1 = new ArrayList();
        sectNames.add(s);
        sectVars.add(arraylist);
        sectValues.add(arraylist1);
        checkSaved();
        return sectNames.size() - 1;
    }

    public boolean sectionExist(java.lang.String s, int i)
    {
        return sectionIndex(s, i) != -1;
    }

    public boolean sectionExist(java.lang.String s)
    {
        return sectionIndex(s, 0) != -1;
    }

    public int sectionIndex(java.lang.String s, int i)
    {
        if(i == 0 && hashNames != null)
        {
            java.lang.Integer integer = (java.lang.Integer)hashNames.get(s);
            if(integer != null)
                return integer.intValue();
        }
        return listFindString(sectNames, i, s);
    }

    public int sectionIndex(java.lang.String s)
    {
        return sectionIndex(s, 0);
    }

    public void sectionClear(int i)
    {
        clearIndexes();
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        list.clear();
        list1.clear();
        checkSaved();
    }

    public void sectionRemove(int i)
    {
        sectionClear(i);
        sectNames.remove(i);
        sectVars.remove(i);
        sectValues.remove(i);
    }

    public void clear()
    {
        for(int i = sections(); i-- > 0;)
            sectionRemove(0);

    }

    public int vars(int i)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        return list.size();
    }

    public java.lang.String var(int i, int j)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        return (java.lang.String)list.get(j);
    }

    public java.lang.String value(int i, int j)
    {
        java.util.List list = (java.util.List)sectValues.get(i);
        return (java.lang.String)list.get(j);
    }

    public java.lang.String line(int i, int j)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        java.lang.String s = (java.lang.String)list1.get(j);
        if("".equals(s))
            return (java.lang.String)list.get(j);
        else
            return (java.lang.String)list.get(j) + ' ' + s;
    }

    public void var(int i, int j, java.lang.String s)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        list.set(j, s);
        checkSaved();
    }

    public void value(int i, int j, java.lang.String s)
    {
        java.util.List list = (java.util.List)sectValues.get(i);
        list.set(j, s);
        checkSaved();
    }

    public void line(int i, int j, java.lang.String s, java.lang.String s1)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        list.set(j, s);
        list1.set(j, s1);
        checkSaved();
    }

    public void line(int i, int j, java.lang.String s)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        if(s == null || s.length() == 0)
        {
            list.remove(j);
            list1.remove(j);
            checkSaved();
            return;
        }
        int k = s.length();
        if(strBuf.length < k)
            strBuf = new char[k];
        s.getChars(0, k, strBuf, 0);
        strPtr = 0;
        strEnd = k;
        removeComments();
        if(strEnd - strPtr > 0)
        {
            java.lang.String s1 = readVariable();
            if(s1 != null)
            {
                list.set(j, s1);
                list1.set(j, readValue());
            } else
            {
                list.remove(j);
                list1.remove(j);
            }
        } else
        {
            list.remove(j);
            list1.remove(j);
        }
        checkSaved();
    }

    public void lineRemove(int i, int j)
    {
        clearIndexes();
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        list.remove(j);
        list1.remove(j);
        checkSaved();
    }

    public int varAdd(int i, java.lang.String s)
    {
        clearIndexes();
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        list.add(s);
        list1.add("");
        checkSaved();
        return list.size() - 1;
    }

    public int lineAdd(int i, java.lang.String s, java.lang.String s1)
    {
        clearIndexes();
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        list.add(s);
        list1.add(s1);
        checkSaved();
        return list.size() - 1;
    }

    public int lineAdd(int i, java.lang.String s)
    {
        clearIndexes();
        java.util.List list = (java.util.List)sectVars.get(i);
        java.util.List list1 = (java.util.List)sectValues.get(i);
        if(s == null || s.length() == 0)
            return -1;
        int j = s.length();
        if(strBuf.length < j)
            strBuf = new char[j];
        s.getChars(0, j, strBuf, 0);
        strPtr = 0;
        strEnd = j;
        removeComments();
        if(strEnd - strPtr > 0)
        {
            java.lang.String s1 = readVariable();
            if(s1 != null)
            {
                list.add(s1);
                list1.add(readValue());
            } else
            {
                return -1;
            }
        } else
        {
            return -1;
        }
        checkSaved();
        return list.size() - 1;
    }

    public boolean varExist(int i, java.lang.String s, int j)
    {
        return varIndex(i, s, j) != -1;
    }

    public boolean varExist(int i, java.lang.String s)
    {
        return varIndex(i, s, 0) != -1;
    }

    public int varIndex(int i, java.lang.String s, int j)
    {
        java.util.List list = (java.util.List)sectVars.get(i);
        if(j == 0 && hashNames != null)
        {
            java.util.HashMap hashmap = (java.util.HashMap)hashVars.get(i);
            java.lang.Integer integer = (java.lang.Integer)hashmap.get(s);
            if(integer != null)
                return integer.intValue();
        }
        return listFindString(list, j, s);
    }

    public int varIndex(int i, java.lang.String s)
    {
        return varIndex(i, s, 0);
    }

    public boolean exist(java.lang.String s, java.lang.String s1)
    {
        int i = sectionIndex(s);
        if(i == -1)
            return false;
        else
            return varExist(i, s1);
    }

    public java.lang.String get(java.lang.String s, java.lang.String s1)
    {
        int i = sectionIndex(s);
        if(i == -1)
            return null;
        int j = varIndex(i, s1);
        if(j == -1)
            return null;
        else
            return value(i, j);
    }

    public java.lang.Object get(java.lang.String s, java.lang.String s1, java.lang.Object obj)
    {
        java.lang.String s2 = get(s, s1);
        if(s2 == null)
            return null;
        else
            return com.maddox.rts.ObjIO.fromString(obj, s2);
    }

    public java.lang.Object get(java.lang.String s, java.lang.String s1, java.lang.Object obj, java.lang.Class class1)
    {
        java.lang.String s2 = get(s, s1);
        if(s2 == null)
            return null;
        else
            return com.maddox.rts.ObjIO.fromString(obj, class1, s2);
    }

    public java.lang.Object get(java.lang.String s, java.lang.Object obj, java.lang.Class class1)
    {
        int i = sectionIndex(s);
        if(i == -1)
            return null;
        int j = vars(i);
        for(int k = 0; k < j; k++)
            _getsetMap.put(var(i, k), value(i, k));

        java.lang.Object obj1 = com.maddox.rts.ObjIO.fromStrings(obj, class1, _getsetMap);
        _getsetMap.clear();
        return obj1;
    }

    public void remove(java.lang.String s, java.lang.String s1)
    {
        int i = sectionIndex(s);
        if(i == -1)
            return;
        int j = varIndex(i, s1);
        if(j == -1)
        {
            return;
        } else
        {
            lineRemove(i, j);
            return;
        }
    }

    public void set(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        int i = sectionIndex(s);
        if(i == -1)
            i = sectionAdd(s);
        int j = varIndex(i, s1);
        if(j == -1)
            lineAdd(i, s1, s2);
        else
            value(i, j, s2);
    }

    public void set(java.lang.String s, java.lang.String s1, int i)
    {
        set(s, s1, java.lang.Integer.toString(i));
    }

    public void set(java.lang.String s, java.lang.String s1, float f)
    {
        set(s, s1, java.lang.Float.toString(f));
    }

    public void set(java.lang.String s, java.lang.String s1, boolean flag)
    {
        set(s, s1, flag ? "1" : "0");
    }

    public void set(java.lang.String s, java.lang.String s1, java.lang.Object obj, boolean flag)
    {
        set(s, s1, com.maddox.rts.ObjIO.toString(obj, flag));
    }

    public void set(java.lang.String s, java.lang.Object obj)
    {
        if(com.maddox.rts.ObjIO.toStrings(_getsetMap, obj) && _getsetMap.size() > 0)
        {
            java.util.Map.Entry entry;
            for(java.util.Iterator iterator = _getsetMap.entrySet().iterator(); iterator.hasNext(); set(s, (java.lang.String)entry.getKey(), (java.lang.String)entry.getValue()))
                entry = (java.util.Map.Entry)iterator.next();

            _getsetMap.clear();
        }
    }

    public java.lang.String get(java.lang.String s, java.lang.String s1, java.lang.String s2, boolean flag)
    {
        int i = sectionIndex(s);
        if(i == -1)
        {
            if(!flag)
                return s2;
            i = sectionAdd(s);
        }
        int j = varIndex(i, s1);
        if(j == -1)
        {
            if(flag)
                lineAdd(i, s1, s2 != null ? s2 : "");
            return s2;
        } else
        {
            return value(i, j);
        }
    }

    public java.lang.String get(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        return get(s, s1, s2, s2 != null ? !bNotSave : false);
    }

    public float get(java.lang.String s, java.lang.String s1, float f, boolean flag)
    {
        java.lang.String s2 = get(s, s1, java.lang.Float.toString(f), flag);
        float f1 = f;
        try
        {
            f1 = java.lang.Float.parseFloat(s2);
        }
        catch(java.lang.Exception exception) { }
        return f1;
    }

    public float get(java.lang.String s, java.lang.String s1, float f)
    {
        return get(s, s1, f, !bNotSave);
    }

    public float get(java.lang.String s, java.lang.String s1, float f, float f1, float f2, boolean flag)
    {
        float f3 = get(s, s1, f, flag);
        if(f3 < f1)
            f3 = f1;
        if(f3 > f2)
            f3 = f2;
        return f3;
    }

    public float get(java.lang.String s, java.lang.String s1, float f, float f1, float f2)
    {
        return get(s, s1, f, f1, f2, !bNotSave);
    }

    public int get(java.lang.String s, java.lang.String s1, int i, boolean flag)
    {
        java.lang.String s2 = get(s, s1, java.lang.Integer.toString(i), flag);
        int j = i;
        try
        {
            j = java.lang.Integer.parseInt(s2);
        }
        catch(java.lang.Exception exception) { }
        return j;
    }

    public int get(java.lang.String s, java.lang.String s1, int i)
    {
        return get(s, s1, i, !bNotSave);
    }

    public int get(java.lang.String s, java.lang.String s1, int i, int j, int k, boolean flag)
    {
        int l = get(s, s1, i, flag);
        if(l < j)
            l = j;
        if(l > k)
            l = k;
        return l;
    }

    public int get(java.lang.String s, java.lang.String s1, int i, int j, int k)
    {
        return get(s, s1, i, j, k, !bNotSave);
    }

    public boolean get(java.lang.String s, java.lang.String s1, boolean flag, boolean flag1)
    {
        int i = get(s, s1, flag ? 1 : 0, flag1);
        return i != 0;
    }

    public boolean get(java.lang.String s, java.lang.String s1, boolean flag)
    {
        return get(s, s1, flag, !bNotSave);
    }

    public static final int NOT_SAVE = 0;
    public static final int SAVE = 1;
    public static final int SAVE_ON_CHANGE = 2;
    public static final int NOT_COMMENT = 4;
    protected java.util.ArrayList sectNames;
    private java.util.HashMap hashNames;
    protected java.util.ArrayList sectVars;
    private java.util.ArrayList hashVars;
    protected java.util.ArrayList sectValues;
    protected java.lang.String fileName;
    protected boolean saveOnChange;
    protected boolean bChanged;
    protected boolean bNotSave;
    protected boolean bComment;
    protected boolean bTypeErrorMessages;
    private int kryptoKey[];
    private java.lang.String enc;
    private boolean bUseUnicodeTo8bit;
    private static char strBuf[] = new char[256];
    private static int strPtr = 0;
    private static int strEnd = 0;
    private static java.util.HashMap _getsetMap = new HashMap();

}
