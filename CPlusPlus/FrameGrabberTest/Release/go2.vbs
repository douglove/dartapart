Set WshShell = WScript.CreateObject("WScript.Shell") 


WshShell.Run "cam.bat 10111" 

WScript.Sleep 4000 

WshShell.Run "cam.bat 10112" 

WScript.Sleep 4000 

WshShell.AppActivate "Video Source" 

WshShell.SendKeys "{DOWN}" 
WshShell.SendKeys "{ENTER}"


WScript.Sleep 4000 

WshShell.AppActivate "Camera Analyzer 10111" 
WshShell.AppActivate "Camera Analyzer 10112" 