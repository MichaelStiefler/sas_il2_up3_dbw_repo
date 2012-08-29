#pragma once

//*************************************************************************
// Includes
//*************************************************************************
#include <windows.h>

//*************************************************************************
// Global Variables
//*************************************************************************
int g_iModType = 0;
int g_iRamSize = 0;
BOOL g_b4GBAddressSpaceEnabled = FALSE;
TCHAR szAppPath[MAX_PATH];
TCHAR szIniFile[MAX_PATH];
TCHAR szPathName[MAX_PATH];
TCHAR szIl2ServerExeFilePath[MAX_PATH];
TCHAR szWrapperFilePath[MAX_PATH];
TCHAR* g_szModTypeExeParms[4] = {L"", L" /f:files /m:mods", L" /f:none /m:#SAS", L" /f:none /m:#UP#"};
TCHAR* g_szModTypes[4] = {L"Stock IL-2 1946 Server", L"Classic Mod Type Server", L"SAS Modact 3.x Server", L"Ultrapack 3.x Server"};
TCHAR g_szCmdLine[0x8000];