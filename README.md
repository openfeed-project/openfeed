
# Openfeed Java Client

## Connection
  
  This client will open up a websocket connection to:  ws://openfeed.aws.barchart.com:80/ws
  
## To Build

	mvn clean install

An executable jar will be build in `target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar`, with the main class being [OpenfeedClientExampleMain](src/main/java/org/openfeed/client/OpenfeedClientExampleMain.java) 

## Command line examples

```
java -jar target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar -h
```

### To connect and subscribe for IBM and display updates

```shell
java -jar target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar -u <user> -p <password> -s IBM -lu
```

### Subscribe for all of NYSE and NASDAQ

```shell
java -jar target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar -u <user> -p <password> -e NYSE,NASDAQ
```

### Subscribe only for trades for AAPL and IBM and display the updates

```shell
java -jar target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar -u <user> -p <password> -t -s AAPL,IBM -lu
```  

### Get all instruments for NYSE and display them.

```shell
java -jar target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar -u <user> -p <password> -ir -e NYSE -li
```

### To log heartbeats (keep alives)

```shell
java -jar target/openfeed-client-1.0.0-SNAPSHOT-shaded.jar -u <user> -p <password> -e NYSE -lh
```

The default heartbeat will be of the form:

```
client-0:  < {"transactionTime":"1570465358319000000"}
```

Heartbeats generated by an exchange keepalive will be of the form:

```
client-0: Exchange < {"transactionTime":"1570465382000080120","exchange":true,"channel":29}
```
  
## API Usage

The client API has three main interfaces:
 	
1. [OpenfeedClient.java](src/main/java/org/openfeed/client/api/OpenfeedClient.java)        - connect, subscribe methods
2. [OpenfeedClientHandler.java](src/main/java/org/openfeed/client/api/OpenfeedClientHandler.java)  - Openfeed message handler
3. [OpenfeedClientEventHandler](src/main/java/org/openfeed/client/api/OpenfeedClientEventHandler.java) - Event callback
### Client Usage

To use the client you must do the following:
	
1. Create a Client Message Handler, an example handler is provided: [OpenfeedClientHandlerImpl.java](src/main/java/org/openfeed/client/examples/OpenfeedClientHandlerImpl.java)
2. Create an Event Handler, an example handler is provided: [OpenfeedClientEventHandlerImpl](src/main/java/org/openfeed/client/examples/OpenfeedClientEventHandler.java).
2. Create the web socket client and connect, see `OpenfeedClientExampleMain.start()`
3. Subscribe to symbols/exchanges using the OpenfeedClient interface, see see `OpenfeedClientExampleMain.executeCommands()`.
	
To connect and subscribe to all of AMEX:

```java
config.setClientId("client");
ConnectionStats connectionStats = new ConnectionStats();
OpenfeedClientEventHandler eventHandler = new OpenfeedClientEventHandlerImpl(config,instrumentCache,connectionStats);
OpenfeedClientHandler clientHandler = new OpenfeedClientHandlerImpl(config, instrumentCache,connectionStats);
OpenfeedClientWebSocket client = new OpenfeedClientWebSocket(config, eventHandler, clientHandler);
// Connect
client.connectAndLogin();
// Subscribe to AMEX
String exchanges = new String[] { "AMEX" };
client.subscribeExchange(Service.REAL_TIME, config.getSubcriptionType(), exchanges);
```

The client will receive the following for each symbol:
	 
1. InstrumentDefinition
2. MarketSnapshot
3. Stream of MarketUpdate(s)
	 
### Notes:

* The Protocol Buffer message definitions are at: https://github.com/openfeed-org/proto.  The build will download them and put them in the proto directory.
* Client to server message definitions are in: [openfeed_api.proto](./proto/openfeed_api.proto)
* All times are in Epoch Nanoseconds 	
* Exchange and Channel ids are listed in ExchangeId.java

### Trade Cancel and Trade Correction TradeId Matching

Trade Cancel and Trade Corrections are matched by the "tradeId" field.  The tradeId field is of a bytes field type, to convert the bytes to a string:

```java
 String tradeId = trade.getTradeId().toStringUtf8();
```

Below is an example for USHY (marketId = 4997695455292339999) on channel 31 (AMEX) for a Trade Cancel.
Notice the tradeId fields match.
 
```javascript
08:38:27.019 USHY/4997695455292339999/31: Trade tradeId: 71675256329293  < 
{
    "marketId": "4997695455292339999",
    "symbol": "USHY",
    "transactionTime": "1570628306989699000",
    "marketSequence": "1799",
    "session": {
        "last": {
            "transactionTime": "1570628306862294126",
            "tradeDate": 20191009,
            "price": "404943",
            "quantity": "207"
        },
        "volume": {
            "volume": "4863"
        },
        "numberOfTrades": {
            "numberTrades": "7"
        },
        "monetaryValue": {
            "value": "1927002001"
        }
    },
    "trades": {
        "trades": [{
            "trade": {
                "originatorId": "RA==",
                "transactionTime": "1570628306862294126",
                "price": "404943",
                "quantity": "207",
                "tradeId": "NzE2NzUyNTYzMjkyOTM=",
                "tradeDate": 20191009,
                "saleCondition": "ICAgIA==",
                "session": "@",
                "distributionTime": "1570628306862827981",
                "transactionTime2": "1570628306862294126"
            }
        }]
    }
}

08:49:12.847 USHY/4997695455292339999/31: Cancel tradeId: 71675256329293 < 
{
    "marketId": "4997695455292339999",
    "symbol": "USHY",
    "transactionTime": "1570628952816851000",
    "marketSequence": "3346",
    "trades": {
        "trades": [{
            "tradeCancel": {
                "originatorId": "RA==",
                "transactionTime": "1570628952667443092",
                "correctedTradePrice": "404943",
                "correctedTradeQuantity": "9425",
                "tradeId": "NzE2NzUyNTYzMjkyOTM=",
                "saleCondition": "ICAgIA==",
                "distributionTime": "1570628952668103917",
                "transactionTime2": "1570628952667443092"
            }
        }]
    }
}
```

Below is an example for a Trade Correction for BYND (marketId = 97499913580948752) on channel 16 (NASDAQ).

```javascript
14:43:37.704  BYND/97499913580948752/16: Trade tradeId: 14  <
 {
     "marketId": "97499913580948752",
     "symbol": "BYND",
     "transactionTime": "1570563817670241000",
     "marketSequence": "46767",
     "session": {
         "volume": {
             "volume": "1046270"
         }
     },
     "trades": {
         "trades": [{
             "trade": {
                 "transactionTime": "1570563817643565000",
                 "price": "1428800",
                 "quantity": "1000",
                 "tradeId": "MTQ=",
                 "tradeDate": 20191008,
                 "saleCondition": "QDcgVw==",
                 "doesNotUpdateLast": true,
                 "session": "7",
                 "distributionTime": "1570563817644189263"
             }
         }]
     }
 }

 15:22:45.244 BYND/97499913580948752/16: Correction tradeId: 14 < 
{
    "marketId": "97499913580948752",
    "symbol": "BYND",
    "transactionTime": "1570566165217444000",
    "marketSequence": "62776",
    "trades": {
        "trades": [{
            "tradeCorrection": {
                "transactionTime": "1570566164507000000",
                "price": "1427800",
                "quantity": "1000",
                "tradeId": "MTQ=",
                "tradeDate": 20191008,
                "distributionTime": "1570566165099713276"
            }
        }]
    }
}
```

### Linux Operating System Settings

Ensure these kernel parameters are set to 5M or greater.

```
net.core.rmem_max = 50331648
net.core.wmem_max = 50331648
```
