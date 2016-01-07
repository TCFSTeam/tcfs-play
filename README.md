# The CheeseCake Factory Simplificator
##Current bulid status <a href="https://travis-ci.org/TCFSTeam/tcfs-play"><img src="https://travis-ci.org/TCFSTeam/tcfs-play.svg?branch=master"></a>
##Environment Requirements
-----------
* Java 7
* Intellij IDEA Ultimate
* Scala plugin for IDEA
* SBT plugin for IDEA
* Play 2.0 plugin for IDEA
* SBT 
* Typesafe Activator

##Setting up environment
### Method 1 (simple & outdated)
-----------
* Install VMWare Workstation 10+ 
* Connect to vmware.p-hosting.ru
* Enjoy using Elementary OS

### Method 2 (developer mode)
-----------
* Install Java 7
* Install Intellij IDEA Ultimate (get this from you student license)
* Install Scala plugin for IDEA
* Install SBT plugin for IDEA
* Install Play 2.0 plugin for IDEA
* Clone this repo, open project and start develop

## Zip Distribution
To create a zip distribution:
```
activator|sbt dist
```
For Play 2.0.x and 2.1.x use the start script (Unix Only) in the extracted zip:
```
start -Dhttp.port=8080 -Dapplication.secret="QCY?tAnf"  -DapplyEvolutions.default=true
```
For Play 2.2.x use the appropriate script in the [appname]-[version]/bin directory:
```
[appname]-[version]/bin/[appname] -Dhttp.port=8080
```
With Play 2.2.x on Windows:
```
[appname]-[version]\bin\[appname].bat -Dhttp.port=8080
```
