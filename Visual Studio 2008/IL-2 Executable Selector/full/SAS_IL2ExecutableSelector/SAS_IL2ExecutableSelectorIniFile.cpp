//*************************************************************************
// Includes
//*************************************************************************
#include "StdAfx.h"
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <commctrl.h>
#include "common.h"
#include "extern_globals.h"
#include "trace.h"

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    ReadIniSettings
// FullName:  ReadIniSettings
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void ReadIniSettings()
{
	g_iModType = GetPrivateProfileInt(L"Settings", L"ModType", 0, szIniFile);
	if (g_iModType > lengthof(g_szModTypes) -1) g_iModType = 0;
	g_iRamSize = GetPrivateProfileInt(L"Settings", L"RamSize", 512, szIniFile);
	g_bAutoStartIl2 = GetPrivateProfileInt(L"Settings", L"AutoStart", 0, szIniFile) == 0 ? FALSE : TRUE;
	g_iStartDelay = GetPrivateProfileInt(L"Settings", L"StartDelay", 10, szIniFile);
	g_bStartImmediately = g_bAutoStartIl2 && (g_iStartDelay == 0);
	if (g_bAutoStartIl2 && !g_bStartImmediately) {
		if (g_iStartDelay < 3) g_iStartDelay = 3;
		if (g_iStartDelay > 99) g_iStartDelay = 99;
	}
	g_iDelayLeft = g_iStartDelay;
	g_bExitWithIl2 = GetPrivateProfileInt(L"Settings", L"ExitWithIL2", 0, szIniFile) == 0 ? FALSE : TRUE;
	g_bTIREnabled = GetPrivateProfileInt(L"Settings", L"TIR", 0, szIniFile) == 0 ? FALSE : TRUE;
	g_bExpertModeEnabled = GetPrivateProfileInt(L"Settings", L"ExpertMode", 0, szIniFile) == 0 ? FALSE : TRUE;
	if (!g_bExpertModeEnabled) g_iRamSize = stepRamSize(g_iRamSize);
	g_b4GBAddressSpaceEnabled = GetPrivateProfileInt(L"Settings", L"LAA", 0, szIniFile) == 0 ? FALSE : TRUE;
	g_bEnableModFilesCache = GetPrivateProfileInt(L"Settings", L"UseCachedFileLists", 0, szIniFile) == 0 ? FALSE : TRUE;
	g_bMultipleInstancesEnabled = GetPrivateProfileInt(L"Settings", L"MultipleInstances", 0, szIniFile) == 0 ? FALSE : TRUE;
}
//************************************
// Method:    WritePrivateProfileInt
// FullName:  WritePrivateProfileInt
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpAppName
// Parameter: LPCTSTR lpKeyName
// Parameter: int nInteger
// Parameter: LPCTSTR lpFileName
//************************************
BOOL WritePrivateProfileInt(LPCTSTR lpAppName, LPCTSTR lpKeyName, int nInteger, LPCTSTR lpFileName)
{
	TCHAR lpString[ 1024 ];
	wsprintf( lpString, L"%d", nInteger );
	return WritePrivateProfileString( lpAppName, lpKeyName, lpString, lpFileName );
}
//************************************
// Method:    WriteIniSettings
// FullName:  WriteIniSettings
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void WriteIniSettings()
{
	SecureDeleteFile(szIniFile);
	WritePrivateProfileInt(L"Settings", L"ModType", g_iModType, szIniFile);
	WritePrivateProfileInt(L"Settings", L"RamSize", g_iRamSize, szIniFile);
	WritePrivateProfileInt(L"Settings", L"AutoStart", g_bAutoStartIl2, szIniFile);
	WritePrivateProfileInt(L"Settings", L"StartDelay", g_bStartImmediately ? 0 : g_iStartDelay, szIniFile);
	WritePrivateProfileInt(L"Settings", L"ExitWithIL2", g_bExitWithIl2, szIniFile);
	WritePrivateProfileInt(L"Settings", L"TIR", g_bTIREnabled, szIniFile);
	WritePrivateProfileInt(L"Settings", L"ExpertMode", g_bExpertModeEnabled, szIniFile);
	WritePrivateProfileInt(L"Settings", L"LAA", g_b4GBAddressSpaceEnabled && g_bExpertModeEnabled, szIniFile);
	WritePrivateProfileInt(L"Settings", L"UseCachedFileLists", g_bEnableModFilesCache && g_bExpertModeEnabled, szIniFile);
	WritePrivateProfileInt(L"Settings", L"MultipleInstances", g_bMultipleInstancesEnabled && g_bExpertModeEnabled, szIniFile);
}
