#Senior Project : Rubber Blockchain
My senior project in 4th year of Computer Science, Kasetsart University.

The purpose of the project is to collect rubber transaction by the Corda ledger technology.

##Setting up
In order to run the project, the following should be created/installed
- Create a Linux (Ubuntu) based Virtual Machine in Azure

- Install JAVA 8 JVM
    - Visit http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    - Scroll down to “Java SE Development Kit 8uXXX” (where “XXX” is the latest minor version number)
    - Toggle “Accept License Agreement”
    - Click the download link for jdk-8uXXX-windows-x64.exe (where “XXX” is the latest minor version number)
    - Download and run the executable to install Java (use the default settings)
    - Open a new command prompt and run java -version to test that Java is installed correctly
    
- Install the project
    - Open a command prompt
    - Clone the CorDapp example repo by running 
      ```git clone https://github.com/iphatchanya/rubber-blockchain.git```
    - Move to the folder created cd rubber-blockchain

##Run project
Go into the project directory and deploy nodes by running : 
```
./gradlew deployNodes
```
Start the nodes by running :
```
./build/nodes/runnodes
```
Now , you should have three Corda terminals opened automatically.


###Creating Accounts
Go to the Agriculturist's node and paste in the following code :
```
flow start CreateNewAccount accountName: Ms.Garden
```
Go to the Middleman's node and paste in the following code :
```
flow start CreateNewAccount accountName: Mr.Sale
```

This is creating 1 account under Agriculturist's node and creating 1 account under Middleman's node.

###Sharing Accounts
Go to the Agriculturist's node and paste in the following code :
```
flow start ShareAccount accountNameShared: Ms.Garden, shareTo: Admin
flow start ShareAccount accountNameShared: Ms.Garden, shareTo: Middleman
```
Go to the Middleman's node and paste in the following code :
```
flow start ShareAccount accountNameShared: Ms.Sale, shareTo: Admin
flow start ShareAccount accountNameShared: Mr.Sale, shareTo: Agriculturist
```
This is sharing account with their specific conterpartie's node or account.

###Transaction 
Go to the Agriculturist's node and paste in the following code:
```
flow start AddTransaction source: Ms.Garden, rubberType: RRIT408, volume: 80, price: 400, destination: Mr.Sale 
```
This will send an invoice from Ms.Garden (Agriculturist's node) to Mr.Sale (Middleman's node) with 80 of RRIT408, and the total price is 400 Baht.


###Run Server
```
./gradlew runAgriculturistServer
./gradlew runMiddlemanServer
./gradlew runTemplateClient
```