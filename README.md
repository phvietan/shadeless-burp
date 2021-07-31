Description
----

Released as open source by NCC Group Plc - https://www.nccgroup.com/  
Released under AGPL-3.0 see LICENSE for more information  

Fixed to integrate with [Shadeless](https://github.com/phvietan/shadeless) system by Dr.Strnegth.

Shadeless inherits from Logger++ and will send logs to Shadeless API for many more purposes (auto exploits, static code analysis, ...). Read more about Shadeless architecture [here](https://github.com/phvietan/shadeless).

Releases
----

| Version       | Release link |                                   Download link                                         |   Release Date  |
| ------------- |------------------------------|----------------------------------------------------------------------------------------| --------------|
| v1.0.0         |   https://github.com/phvietan/shadeless-burp/releases/tag/v1.0.0 | https://github.com/phvietan/shadeless-burp/releases/download/v1.0.0/shadeless.1.0.0.jar | 31 July 2021  |

How to install
----

- 1: Download latest release
- 2: Install shadeless jar file into your BurpSuite Pro: open Burpsuite > Extender tab > Press Add button > Select file shadeless jar file > Next
- You should see a new tab called Shadeless Burp added to your Burp panel.
![image](https://user-images.githubusercontent.com/25105395/127740267-2e56b249-46a9-4fc1-a1ac-7de95a14e588.png)

How to use
----
- 1: If you only want to see log of packets, go to `View Logs` tab.
- 2: If you want to forward packets to Shadeless API, continue instructions below:
- 3: Press on Configure Shadeless Exporter
  + You might want to change `ubuntu:3000` into the Shadeless API server. (You must run Shadeless API server first, read more [here](https://github.com/phvietan/shadeless-api)).
  + You might want to change Codename to your name if there are multiple user using Shadeless.
- 4: Press on Ping Shadeless API, if everything is correct, you should see message `Reached Shadeless API successfully`.
- 5: Start Shadeless Exporter, now every packets that go though your Burp will be forwarded to Shadeless API server.

Contacts
----
If you find any bug, feel free to create an issue.
