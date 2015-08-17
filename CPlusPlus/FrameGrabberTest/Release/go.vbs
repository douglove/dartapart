Set WshShell = WScript.CreateObject("WScript.Shell") 


WshShell.Run "FrameGrabberTest.exe" 

WScript.Sleep 3000 

WshShell.AppActivate "Untitled - FrameGrabberTest"

WshShell.SendKeys "%f" 
WshShell.SendKeys "{DOWN}{DOWN}{ENTER}" 
WshShell.SendKeys "{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}" 
WshShell.SendKeys "{RIGHT}{RIGHT}{TAB}{UP}{ENTER}" 

WScript.Sleep 2000 

WshShell.SendKeys "%v" 
WshShell.SendKeys "{RIGHT}{DOWN}{DOWN}{ENTER}" 

WScript.Sleep 5000 

WshShell.SendKeys "{ENTER}" 

WScript.Sleep 2000 

WshShell.Run "FrameGrabberTest.exe" 

WScript.Sleep 3000 

'WshShell.AppActivate "Video Source" 

'WshShell.SendKeys "{UP}{DOWN}{DOWN}" 
'WshShell.SendKeys "{ENTER}"

WshShell.SendKeys "%f" 
WshShell.SendKeys "{DOWN}{DOWN}{ENTER}" 
WshShell.SendKeys "{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}{TAB}" 
WshShell.SendKeys "{RIGHT}{RIGHT}{TAB}{DOWN}{ENTER}" 

WScript.Sleep 2000 

WshShell.SendKeys "%v" 
WshShell.SendKeys "{RIGHT}{DOWN}{DOWN}{ENTER}" 

WScript.Sleep 5000 

WshShell.SendKeys "{ENTER}" 

'WScript.Sleep 4000 

'WshShell.AppActivate "Camera Analyzer 10111" 
'WshShell.AppActivate "Camera Analyzer 10112" 
