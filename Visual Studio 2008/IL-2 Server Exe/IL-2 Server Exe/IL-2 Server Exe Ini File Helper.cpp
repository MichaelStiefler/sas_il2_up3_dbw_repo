//*************************************************************************
// Includes
//*************************************************************************
#include "stdafx.h"
#include "common.h"
#include "extern_globals.h"

//*************************************************************************
// Suppress new style warning messages
//*************************************************************************
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
	if (g_iModType > 3) g_iModType = 0;
	g_iRamSize = GetPrivateProfileInt(L"Settings", L"RamSize", 512, szIniFile);
	g_b4GBAddressSpaceEnabled = GetPrivateProfileInt(L"Settings", L"LAA", 0, szIniFile) == 0 ? FALSE : TRUE;
}