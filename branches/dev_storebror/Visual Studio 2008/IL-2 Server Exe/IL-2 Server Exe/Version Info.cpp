//*************************************************************************
// Includes
//*************************************************************************
#include "stdafx.h"
#include <windows.h>
#include "common.h"

#pragma comment(lib, "Version.lib")

//*************************************************************************
// Suppress new style warning messages
//*************************************************************************
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    GetVersionInfo
// FullName:  GetVersionInfo
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: LPCTSTR lpEntry
// Parameter: LPTSTR lpVersionInfo
//************************************
void GetVersionInfo(LPCTSTR lpEntry, LPTSTR lpVersionInfo)
{
	HMODULE hLib = GetModuleHandle(NULL);

	HRSRC hVersion = FindResource( hLib, 
		MAKEINTRESOURCE(VS_VERSION_INFO), RT_VERSION );
	if (hVersion != NULL)
	{
		HGLOBAL hGlobal = LoadResource( hLib, hVersion ); 
		if ( hGlobal != NULL)  
		{  

			LPVOID versionInfo  = LockResource(hGlobal);  
			if (versionInfo != NULL)
			{
				DWORD vLen,langD;
				BOOL retVal;    

				LPVOID retbuf=NULL;

				static TCHAR fileEntry[256];

				_stprintf(fileEntry, L"\\VarFileInfo\\Translation");
				retVal = VerQueryValueW(versionInfo,fileEntry,&retbuf,(UINT *)&vLen);
				if (retVal && vLen==4) 
				{
					memcpy(&langD,retbuf,4);            
					_stprintf(fileEntry, L"\\StringFileInfo\\%02X%02X%02X%02X\\%s",
						(langD & 0xff00)>>8,langD & 0xff,(langD & 0xff000000)>>24, 
						(langD & 0xff0000)>>16, lpEntry);            
				}
				else 
					_stprintf(fileEntry, L"\\StringFileInfo\\%04X04B0\\%s", 
					GetUserDefaultLangID(), lpEntry);

				if (VerQueryValueW(versionInfo,fileEntry,&retbuf,(UINT *)&vLen)) 
					_tcscpy(lpVersionInfo, (LPTSTR)retbuf);
			}
		}

		UnlockResource( hGlobal );  
		FreeResource( hGlobal );  
	}
}

//************************************
// Method:    GetFileVersionX
// FullName:  GetFileVersionX
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: LPTSTR lpVersionInfo
//************************************
void GetFileVersionX(LPTSTR lpVersionInfo)
{
	TCHAR szVersion[MAX_PATH];
	TCHAR szBuild[MAX_PATH];
	memset(szVersion, 0, sizeof(szVersion));
	memset(szBuild, 0, sizeof(szVersion));
	GetVersionInfo(L"FileVersion", szVersion);
	GetVersionInfo(L"SpecialBuild", szBuild);
	_stprintf(lpVersionInfo, L"Version %s (%s)", szVersion, szBuild);
}

//************************************
// Method:    GetProductVersionX
// FullName:  GetProductVersionX
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: LPTSTR lpVersionInfo
//************************************
void GetProductVersionX(LPTSTR lpVersionInfo)
{
	GetVersionInfo(L"ProductVersion", lpVersionInfo);
}