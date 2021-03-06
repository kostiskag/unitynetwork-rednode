# Unity Network
Unity Network is a virtual network (VPN) capable to be deployed in any kind of IP network as a LAN network or over the Internet.

* It is based on a divide and conquer logic with distributed roles, behavior and decupoled network traffic from the network logic which allows it to serve a large number of host-clients from many platforms.
* It is based in software written in Java rather than hardware wich provides enchanced resilience and easy deployment, thanks to maven, to any kind of platform which may support Java.

## key-principles
The network is based in three key-principles:

* **Identification**: Each registered user or organisation may own multiple host-clients where each host-client may receive the same network address each time the device connects to the network
* **Freedom**: Each client may host any kind of service and transfer any kind of data towards any other host-client without limitations of any kind
* **Privacy**: In order to defend its host-client privacy, the network is built with public key distribution and provides RSA and AES algorithms for authentication and confidentiality.
(If you do not want to use the encrypted version you may use the non_encrypted version from the appropriate branch)

## Reasoning
This software was build as part of my BSc Thesis in order to demonstare a live and tangible example of a better version of today's Internet. Inside the network, users may experience a much more vivid communication, the ability to share any kind of data or services between them and the ability to know each other. 

In order to learn more about the Thesis please visit the following url:
https://kostiskag.wordpress.com/2017/05/25/unity-network/

### Some feasible examples of the network's behaviour are:
In general, a host-client may perform any kind of task that is currently being done in an IP network as the Internet
**plus** that IP to IP communication is enhanced and the connected host clients may have a direct communication between them in the form of:

[someone] uses [one of his devices] to directly exchange data with [someone else's device]
* Bob may directly send a file from his laptop to David's Laptop
* Lucy may connect from her mobile phone to a social media server
* Steve may video-call Jenny from his computer to her mobile phone
* May leaves a message from her laptop to her home's noticeboard
* Bill calls Dave from his mobile to Dave's mobile

## Applications
UnityNetwork is composed by three software applications which may be found on their respective repositories:
* unitynetwork-tracker  [https://github.com/kostiskag/unitynetwork-tracker] : The tracker is responsible to keep the network authentication and identification data but does not forward any network traffic.
* unitynetwork-bluenode [https://github.com/kostiskag/unitynetwork-bluenode] : Bluenode hosts are responsible to forward the network traffic from rednode to bluenode and from bluenode to bluenode. All the bluenodes use a tracker to authenticate.
* unitynetwork-rednode  [you are here] : The rednode is the host-client application which is able to transfer a host to the network and exchange traffic towards the closest bluenode.

# unitynetwork-rednode
The rednode is the host-client application responsible to transfer a host to the network and exchange traffic towards the closest bluenode. RedNode may be used from a variety of systems which may support a TAN/TUP virtual network adapter. It is currently tested to work on windows and linux with Java Runtime Environment 7 or greater.

## Option A - Build from source code
In order to build this project, Java JDK 1.7 or greater and Apache Maven have to be installed on your system.
```
git clone https://github.com/kostiskag/unitynetwork-rednode.git
cd unitynetwork-rednode
mvn package
```

## Option B - Download and use a pre-built version
You can download, unzip and use a pre-built version of Red Node from this url:
https://drive.google.com/file/d/0BzPrI7NjFz2SR1NROV9JNGYwSUE/view?usp=sharing

In order to establish data integrity, you should verify the zip file's signature to be:

| Algorithm | Hash Signature |
| --- | --- |
| MD5 | 8a81c8919f5676cc979bd310c24dd6b1 |
| SHA256 | 53f04c3a7d2f8f215d7bff5d840bf40c490b268ca6c55a33700221ecb9891520 |

## Requirements
In order to be able to use the Red Node, your system has to be able to create tun/tap interfaces or to have a tun/tap adapter pre-installed.

**In Linux Ubuntu:** The adapters can be dynamicaly generated, other Linux distributions may work under the same manner

**In Windows 7 or older:** You may download OpenVPN's tun/tap adapter from the below url under the section Tap-windows:
https://openvpn.net/index.php/download/community-downloads.html

## Run
```
cd target 
java -jar UnityNetwork_RedNode-1.0.jar 
```

## Authorship
The source code was created by Konstantinos Kagiampakis

The source code makes use of OpenVPN's tun/tap adapter and tun/tap libraries written by Wolfgang Ginolas

## License
The project's article and source code are licensed under Creative Commons Atribution 4.0 International: https://creativecommons.org/licenses/by/4.0/

You may use the source code commercially. You should provide the appropriate attribution for all the authors involved in this project.

## Looking for developers in order to deploy the platform for real use on the Internet
In order for the platform to be fully operational it needs the following todo list to be done:
* improve the control flow algorithm
* fix minor bugs
* deploy and test on the Internet

If you are interested in joining the project or to provide professional advice and guidance please send me an email.
