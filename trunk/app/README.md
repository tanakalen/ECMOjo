Information & Overview
----------------------

[ECMOjo][ecmojo] is a simulator and trainer for extracorporeal membrane
oxygenation (ECMO). It consists of a graphical user interface to allow
interaction and train ECMO practitioners, and has been developed by [Telehealth
Research Institute (TRI), University of Hawaii][tri]. [ECMOjo][ecmojo] contains
the word ECMO and mojo (magic charm). In intensive care medicine,
extracorporeal membrane oxygenation (ECMO) is an artificial device to provide
both cardiac and respiratory support oxygen to patients whose heart and lungs
are so severely diseased or damaged that they can no longer serve their
function.

[ECMOjo][ecmojo] is open source software hosted on SourceForge and GitHub. The
program has been implemented in Java and can be run as either a standalone
desktop application or via the Internet. [ECMOjo][ecmojo] has been developed by
[Telehealth Research Institute, University of Hawaii][tri].


Credits
-------

* Lawrence P. Burgess, MD, Principal Investigator
* Mark T. Ogino, MD, Project Manager
* Christoph Aschwanden, PhD, Lead Developer
* Kin Lik Wang (Alex), Developer
* Len Tanaka, MD, Developer
* Kaleiohu Lee, Artist
* Donald McCurnin, MD, Godfather
* Kristen Costales, CCP, Advisor
* Melinda Hamilton, MD, Advisor
* Kent Kelly, CCP, Advisor
* Melody Kilcommons, RNC, Advisor
* John Lutz, Advisor
* Takanari Ikeyama, MD, Translation (Japanese)
* Yuko Shiima, MD, Translation (Japanese)
* Felipe Amaya, Translation (Spanish)
* Diego Goncalves, Translation (Portuguese)


Acknowledgement
---------------

This project has been supported by grant No. W81XWH-06-2-0061 awarded by
Department of Defense (DoD), United States of America.


Installation
------------

**Windows:**  
  Unzip file. Copy specific application directory to desired location. Double-click application exe file.

**Mac:**  
  Double-click (ECMOjo).dmg. Copy to desired location. Debug app bundle with:  
    `export JAVA_LAUNCHER_VERBOSE=1`  
    `cd (ECMOjo).app`  
    `Contents/MacOS/JavaAppLauncher`  

**Source:** (With Java and Ant)  
    `tar xzf (ECMOjo).tar.gz`  
    `cd (ECMOjo)`  
    `ant -f build.xml application`  

Where (ECMOjo) is the current version. Executables will be located in directory target/app/application


**Maven:**

Install [jsyntaxpane][jsp] as jar to local repository using command:  
    `mvn install:install-file -Dfile=<path to jsyntaxpane.jar> \`  
    `    -DgroupId=jsyntaxpane -DartifactId=jsyntaxpane -Dversion=0.9.5 \`  
    `    -Dpackaging=jar`  

Then, issue command, or other specific Maven command:  
    `mvn package`

[ecmojo]: http://ecmojo.sourceforge.net
[tri]: http://www.tri.jabsom.hawaii.edu
[jsp]: http://code.google.com/p/jsyntaxpane
