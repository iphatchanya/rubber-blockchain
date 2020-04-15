# Senior Project : Rubber Blockchain
My senior project in 4th year of Computer Science, Kasetsart University.

The purpose of the project is to collect rubber transaction by the Corda ledger technology.

## Setting up
In order to run the project, the following should be created/installed
- Create a Linux (Ubuntu) based Virtual Machine in Azure
    - https://portal.azure.com

- Install JAVA 8 JVM
    - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    
- Install the project
    - Open a command prompt
    - Clone the CorDapp example repo by running 
      ```$ git clone https://github.com/iphatchanya/rubber-blockchain.git```
    - Move to the folder created ```$ cd rubber-blockchain```
    
- Install IntelliJ Editor (Optional)
    - https://www.jetbrains.com/idea/download/ 

## Run project
Go into the project directory and deploy nodes by running : 
```
$ ./gradlew clean deployNodes
```
Start the nodes by running :
```
$ ./build/nodes/runnodes
```
Now , you should have three Corda terminals opened automatically.


### Creating Accounts
Go to the Agriculturist's node and paste in the following code :
```
$ flow start CreateNewAccount accountName: Ms.Garden
```
Go to the Middleman's node and paste in the following code :
```
$ flow start CreateNewAccount accountName: Mr.Sale
```

This is creating 1 account under Agriculturist's node and creating 1 account under Middleman's node.

### Sharing Accounts
Go to the Agriculturist's node and paste in the following code :
```
$ flow start ShareAccount accountNameShared: Ms.Garden, shareTo: Admin
$ flow start ShareAccount accountNameShared: Ms.Garden, shareTo: Middleman
```
Go to the Middleman's node and paste in the following code :
```
$ flow start ShareAccount accountNameShared: Ms.Sale, shareTo: Admin
$ flow start ShareAccount accountNameShared: Mr.Sale, shareTo: Agriculturist
```
This is sharing account with their specific conterpartie's node or account.

### Transaction 
Go to the Agriculturist's node and paste in the following code:
```
$ flow start AddTransaction source: Ms.Garden, rubberType: RRIT408, volume: 80, price: 400, destination: Mr.Sale 
```
This will send an invoice from Ms.Garden (Agriculturist's node) to Mr.Sale (Middleman's node) with 80 of RRIT408, and the total price is 400 Baht.


## Run Server
To set up and run the web servers 
```
$ ./gradlew runAgriculturistServer
$ ./gradlew runMiddlemanServer
$ ./gradlew runTemplateClient
```

## Check Result
Show all flow in the project
```
$ flow list
```

Show all account in network
```
$ flow start AllAccounts
```

Show all account in our node
```
$ flow start ViewMyAccounts
```

Show transaction by account name (example Mr.Sale)
```
$ flow start ViewInboxByAccount accountName: Mr.Sale 
```

