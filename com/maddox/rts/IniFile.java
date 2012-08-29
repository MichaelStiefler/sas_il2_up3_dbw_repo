// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IniFile.java

package com.maddox.rts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.maddox.rts:
//            SFSReader, Finger, HomePath, ObjIO

public class IniFile
{

    public IniFile(java.lang.String s)
    {
        this(s, 1);
    }

    public IniFile(java.lang.String s, int i)
    {
        saveOnChange = false;
        bChanged = false;
        bNotSave = false;
        switch(i)
        {
        case 0: // '\0'
            bNotSave = true;
            break;

        case 2: // '\002'
            saveOnChange = true;
            break;
        }
        fileName = s;
        if(!loadFile())
        {
            if(!createFile())
                return;
            loadFile();
        }
        parseLines();
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
        long l = _finger(0L, subjects);
        int i = variables.size();
        for(int j = 0; j < i; j++)
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)variables.get(j);
            if(arraylist != null)
                l = _finger(l, arraylist);
        }

        i = values.size();
        for(int k = 0; k < i; k++)
        {
            java.util.ArrayList arraylist1 = (java.util.ArrayList)values.get(k);
            if(arraylist1 != null)
                l = _finger(l, arraylist1);
        }

        return l;
    }

    public boolean loadFile()
    {
        lines = new ArrayList();
        subjects = new ArrayList();
        variables = new ArrayList();
        values = new ArrayList();
        bChanged = false;
        java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(fileName));
        java.lang.String s = "";
        do
        {
            java.lang.String s1 = bufferedreader.readLine();
            if(s1 == null)
                break;
            lines.add(clampEnd(s1));
        } while(true);
        bufferedreader.close();
        return true;
        java.io.IOException ioexception;
        ioexception;
        java.lang.System.out.println("IniFile load failed: " + ioexception.getMessage());
        ioexception.printStackTrace();
        return false;
    }

    protected boolean createFile()
    {
        if(bNotSave)
            return true;
        java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(fileName, 0))));
        printwriter.println(";INI File: " + fileName);
        printwriter.close();
        return true;
        java.io.IOException ioexception;
        ioexception;
        java.lang.System.out.println("IniFile create failed: " + ioexception.getMessage());
        ioexception.printStackTrace();
        return false;
    }

    protected java.lang.String clamp(java.lang.String s)
    {
        if(s == null)
            return null;
        int i = s.length() - 1;
        int j;
        for(j = 0; j < i && s.charAt(j) <= ' '; j++);
        for(; j < i && s.charAt(i) <= ' '; i--);
        if(j == 0 && i == s.length() - 1)
            return s;
        if(j == i && s.charAt(j) <= ' ')
            return null;
        else
            return s.substring(j, i + 1);
    }

    protected java.lang.String clampEnd(java.lang.String s)
    {
        if(s == null)
            return null;
        int i;
        for(i = s.length() - 1; 0 < i && s.charAt(i) <= ' '; i--);
        if(i == s.length() - 1)
            return s;
        if(0 == i && s.charAt(0) <= ' ')
            return "";
        else
            return s.substring(0, i + 1);
    }

    protected void parseLines()
    {
        Object obj = null;
        java.lang.String s1 = null;
        for(int i = 0; i < lines.size(); i++)
        {
            java.lang.String s = (java.lang.String)lines.get(i);
            java.lang.String s2 = clamp(s);
            if(s2 != null)
                if(isaSubject(s2))
                {
                    s2 = clamp(s2.substring(1, s2.length() - 1));
                    if(s2 != null)
                        s1 = s2;
                    else
                        s1 = null;
                } else
                if(s1 != null && isanAssignment(s))
                    addAssignment(s1, s);
        }

    }

    protected boolean addAssignment(java.lang.String s, java.lang.String s1)
    {
        int i = s1.indexOf("=");
        java.lang.String s3 = s1.substring(0, i);
        java.lang.String s2 = s1.substring(i + 1, s1.length());
        if(s2.length() == 0 || s3.length() == 0)
            return false;
        else
            return addValue(s, s3, s2, false);
    }

    public boolean setValue(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        boolean flag = addValue(s, s1, s2, true);
        if(saveOnChange)
            saveFile();
        else
            bChanged = true;
        return flag;
    }

    public void set(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        setValue(s, s1, s2);
    }

    public void set(java.lang.String s, java.lang.String s1, int i)
    {
        setValue(s, s1, java.lang.Integer.toString(i));
    }

    public void set(java.lang.String s, java.lang.String s1, float f)
    {
        setValue(s, s1, java.lang.Float.toString(f));
    }

    public void set(java.lang.String s, java.lang.String s1, boolean flag)
    {
        setValue(s, s1, flag ? "1" : "0");
    }

    public void set(java.lang.String s, java.lang.String s1, java.lang.Object obj, boolean flag)
    {
        setValue(s, s1, com.maddox.rts.ObjIO.toString(obj, flag));
    }

    public void set(java.lang.String s, java.lang.Object obj)
    {
        if(com.maddox.rts.ObjIO.toStrings(_getsetMap, obj) && _getsetMap.size() > 0)
        {
            java.util.Map.Entry entry;
            for(java.util.Iterator iterator = _getsetMap.entrySet().iterator(); iterator.hasNext(); setValue(s, (java.lang.String)entry.getKey(), (java.lang.String)entry.getValue()))
                entry = (java.util.Map.Entry)iterator.next();

            _getsetMap.clear();
        }
    }

    protected boolean addValue(java.lang.String s, java.lang.String s1, java.lang.String s2, boolean flag)
    {
        if(s == null || s.length() == 0)
            return false;
        if(s1 == null || s1.length() == 0)
            return false;
        java.lang.String s3 = clamp(s1);
        if(s3 == null)
            return false;
        if(!subjects.contains(s))
        {
            subjects.add(s);
            variables.add(new ArrayList());
            values.add(new ArrayList());
        }
        int i = subjects.indexOf(s);
        java.util.ArrayList arraylist = (java.util.ArrayList)variables.get(i);
        java.util.ArrayList arraylist1 = (java.util.ArrayList)values.get(i);
        boolean flag1 = true;
        java.lang.String s4 = null;
        for(int j = 0; j < arraylist.size(); j++)
        {
            s4 = (java.lang.String)arraylist.get(j);
            if(!strEquals(s4, s3))
                continue;
            flag1 = false;
            break;
        }

        if(flag1)
        {
            arraylist.add(s1);
            arraylist1.add(s2);
            int k = arraylist.indexOf(s1);
            arraylist1.set(k, s2);
            if(flag)
                setLine(s, s1, s2);
        } else
        {
            int l = arraylist.indexOf(s4);
            arraylist1.set(l, s2);
            if(flag)
                setLine(s, s4, s2);
        }
        return true;
    }

    protected boolean isaSubject(java.lang.String s)
    {
        return s.startsWith("[") && s.endsWith("]");
    }

    protected void setLine(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        int i = findSubjectLine(s);
        if(i == -1)
        {
            addSubjectLine(s);
            i = lines.size() - 1;
        }
        int j = endOfSubject(i);
        int k = findAssignmentBetween(s1, i, j);
        if(k == -1)
            lines.add(j, s1 + "=" + s2);
        else
            lines.set(k, s1 + "=" + s2);
    }

    protected int findAssignmentLine(java.lang.String s, java.lang.String s1)
    {
        int i = findSubjectLine(s);
        int j = endOfSubject(i);
        return findAssignmentBetween(s1, i, j);
    }

    protected int findAssignmentBetween(java.lang.String s, int i, int j)
    {
        for(int k = i; k < j; k++)
        {
            java.lang.String s1 = (java.lang.String)lines.get(k);
            int l = s1.indexOf('=');
            if(l != -1)
            {
                int i1 = s1.indexOf(s);
                if(i1 != -1 && i1 < l)
                {
                    int j1;
                    for(j1 = 0; j1 < i1; j1++)
                        if(s1.charAt(j1) > ' ')
                            break;

                    if(j1 == i1)
                    {
                        for(i1 += s.length(); i1 < l; i1++)
                            if(s1.charAt(i1) > ' ')
                                break;

                        if(i1 == l)
                            return k;
                    }
                }
            }
        }

        return -1;
    }

    protected void addSubjectLine(java.lang.String s)
    {
        lines.add("[" + s + "]");
    }

    protected int findSubjectLine(java.lang.String s)
    {
        java.lang.String s2 = "[" + s + "]";
        for(int i = 0; i < lines.size(); i++)
        {
            java.lang.String s1 = (java.lang.String)lines.get(i);
            if(s2.equals(s1))
                return i;
        }

        return -1;
    }

    protected int endOfSubject(int i)
    {
        int j = i + 1;
        if(i >= lines.size())
            return lines.size();
        for(int k = i + 1; k < lines.size(); k++)
        {
            if(isanAssignment((java.lang.String)lines.get(k)))
                j = k + 1;
            if(isaSubject((java.lang.String)lines.get(k)))
                return j;
        }

        return j;
    }

    protected boolean isanAssignment(java.lang.String s)
    {
        return s.indexOf("=") != -1 && !s.startsWith(";");
    }

    public java.util.ArrayList getLines()
    {
        return (java.util.ArrayList)lines.clone();
    }

    public java.lang.String[] getVariables(java.lang.String s)
    {
        int i = subjects.indexOf(s);
        if(i != -1)
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)variables.get(i);
            java.lang.String as[] = new java.lang.String[arraylist.size()];
            for(int j = 0; j < arraylist.size(); j++)
                as[j] = (java.lang.String)arraylist.get(j);

            return as;
        } else
        {
            java.lang.String as1[] = new java.lang.String[0];
            return as1;
        }
    }

    public java.lang.String[] getSubjects()
    {
        java.lang.String as[] = new java.lang.String[subjects.size()];
        for(int i = 0; i < subjects.size(); i++)
            as[i] = (java.lang.String)subjects.get(i);

        return as;
    }

    protected boolean strEquals(java.lang.String s, java.lang.String s1)
    {
        int i = s.indexOf(s1);
        if(i != -1)
        {
            int j;
            for(j = 0; j < i; j++)
                if(s.charAt(j) > ' ')
                    break;

            if(j == i)
            {
                for(i += s1.length(); i < s.length(); i++)
                    if(s.charAt(i) > ' ')
                        break;

                if(i == s.length())
                    return true;
            }
        }
        return false;
    }

    protected int vectorIndexOf(java.util.ArrayList arraylist, java.lang.String s)
    {
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            java.lang.String s1 = (java.lang.String)arraylist.get(j);
            if(strEquals(s1, s))
                return j;
        }

        return -1;
    }

    public java.lang.String getValue(java.lang.String s, java.lang.String s1)
    {
        int i = subjects.indexOf(s);
        if(i == -1)
            return "";
        s1 = clamp(s1);
        if(s1 == null)
            return "";
        java.util.ArrayList arraylist = (java.util.ArrayList)values.get(i);
        java.util.ArrayList arraylist1 = (java.util.ArrayList)variables.get(i);
        int j = vectorIndexOf(arraylist1, s1);
        if(j != -1)
        {
            java.lang.String s2 = (java.lang.String)arraylist.get(j);
            int k;
            for(k = 0; k < s2.length(); k++)
                if(s2.charAt(k) > ' ')
                    break;

            if(k > 0)
            {
                if(k == s2.length())
                    return "";
                else
                    return s2.substring(k);
            } else
            {
                return s2;
            }
        } else
        {
            return "";
        }
    }

    public java.lang.Object get(java.lang.String s, java.lang.String s1, java.lang.Object obj)
    {
        java.lang.String s2 = getValue(s, s1);
        if(s2 == "")
            return null;
        else
            return com.maddox.rts.ObjIO.fromString(obj, s2);
    }

    public java.lang.Object get(java.lang.String s, java.lang.String s1, java.lang.Object obj, java.lang.Class class1)
    {
        java.lang.String s2 = getValue(s, s1);
        if(s2 == "")
            return null;
        else
            return com.maddox.rts.ObjIO.fromString(obj, class1, s2);
    }

    public java.lang.Object get(java.lang.String s, java.lang.Object obj, java.lang.Class class1)
    {
        int i = subjects.indexOf(s);
        if(i == -1)
            return null;
        java.util.ArrayList arraylist = (java.util.ArrayList)variables.get(i);
        java.util.ArrayList arraylist1 = (java.util.ArrayList)values.get(i);
        int j = arraylist.size();
        for(int k = 0; k < j; k++)
            _getsetMap.put((java.lang.String)arraylist.get(k), (java.lang.String)arraylist1.get(k));

        java.lang.Object obj1 = com.maddox.rts.ObjIO.fromStrings(obj, class1, _getsetMap);
        _getsetMap.clear();
        return obj1;
    }

    public java.lang.String get(java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        java.lang.String s3 = getValue(s, s1);
        if("".equals(s3))
            return s2;
        else
            return s3;
    }

    public float get(java.lang.String s, java.lang.String s1, float f)
    {
        java.lang.String s2 = get(s, s1, java.lang.Float.toString(f));
        float f1 = f;
        try
        {
            f1 = java.lang.Float.parseFloat(s2);
        }
        catch(java.lang.Exception exception) { }
        return f1;
    }

    public float get(java.lang.String s, java.lang.String s1, float f, float f1, float f2)
    {
        float f3 = get(s, s1, f);
        if(f3 < f1)
            f3 = f1;
        if(f3 > f2)
            f3 = f2;
        return f3;
    }

    public int get(java.lang.String s, java.lang.String s1, int i)
    {
        java.lang.String s2 = get(s, s1, java.lang.Integer.toString(i));
        int j = i;
        try
        {
            j = java.lang.Integer.parseInt(s2);
        }
        catch(java.lang.Exception exception) { }
        return j;
    }

    public int get(java.lang.String s, java.lang.String s1, int i, int j, int k)
    {
        int l = get(s, s1, i);
        if(l < j)
            l = j;
        if(l > k)
            l = k;
        return l;
    }

    public boolean get(java.lang.String s, java.lang.String s1, boolean flag)
    {
        int i = get(s, s1, flag ? 1 : 0);
        return i != 0;
    }

    public void deleteValue(java.lang.String s, java.lang.String s1)
    {
        int i = subjects.indexOf(s);
        if(i == -1)
            return;
        s1 = clamp(s1);
        if(s1 == null)
            return;
        java.util.ArrayList arraylist = (java.util.ArrayList)values.get(i);
        java.util.ArrayList arraylist1 = (java.util.ArrayList)variables.get(i);
        int j = vectorIndexOf(arraylist1, s1);
        if(j != -1)
        {
            arraylist.remove(j);
            arraylist1.remove(j);
            int k = findAssignmentLine(s, s1);
            if(k != -1)
                lines.remove(k);
            if(arraylist1.size() == 0)
                deleteSubject(s);
            if(saveOnChange)
                saveFile();
            else
                bChanged = true;
        }
    }

    public void deleteSubject(java.lang.String s)
    {
        int i = subjects.indexOf(s);
        if(i == -1)
            return;
        values.remove(i);
        variables.remove(i);
        subjects.remove(i);
        int j = findSubjectLine(s);
        int k = endOfSubject(j);
        for(int l = j; l < k; l++)
            lines.remove(j);

        if(saveOnChange)
            saveFile();
        else
            bChanged = true;
    }

    public void saveFile()
    {
        if(bNotSave)
            return;
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(fileName, 0))));
            for(int i = 0; i < lines.size(); i++)
                printwriter.println((java.lang.String)lines.get(i));

            printwriter.close();
            bChanged = false;
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("IniFile save failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
    }

    public void saveFile(java.lang.String s)
    {
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(s, 0))));
            for(int i = 0; i < lines.size(); i++)
                printwriter.println((java.lang.String)lines.get(i));

            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("IniFile save failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
    }

    public boolean isChanged()
    {
        return bChanged;
    }

    protected void finalize()
    {
        if(bChanged)
            saveFile();
    }

    public static final int NOT_SAVE = 0;
    public static final int SAVE = 1;
    public static final int SAVE_ON_CHANGE = 2;
    protected java.util.ArrayList lines;
    protected java.util.ArrayList subjects;
    protected java.util.ArrayList variables;
    protected java.util.ArrayList values;
    protected java.lang.String fileName;
    protected boolean saveOnChange;
    protected boolean bChanged;
    protected boolean bNotSave;
    private static java.util.HashMap _getsetMap = new HashMap();

}
