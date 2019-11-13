package org.openfeed.client.api.impl;

import com.google.common.base.MoreObjects;
import org.openfeed.SubscriptionType;
import org.openfeed.client.api.OpenfeedClientConfig;

import java.util.Set;
import java.util.UUID;

public class OpenfeedClientConfigImpl implements OpenfeedClientConfig {
    private static final long RECONNECT_TIMEOUT_WAIT_SEC = 2;

    private String clientId = UUID.randomUUID().toString();
    // Connection
    private String host = "openfeed.aws.barchart.com";
    private int port = 80;
    private WireProtocol wireProtocol = WireProtocol.PB;
    private String userName = "";
    private String password = "";
    private boolean reconnect = true;
    private long reconnectDelaySec = RECONNECT_TIMEOUT_WAIT_SEC;

    // Subscriptions
    private String[] symbols = null;
    private long[] marketIds;
    private String[] exchanges;
    private int[] channelIds;
    //
    private SubscriptionType subcriptionType = SubscriptionType.QUOTE;
    private boolean instrumentRequest;
    private boolean instrumentCrossReferenceRequest;
    private boolean exchangeRequest;
    private int randomInstruments;
    // logging
    private boolean logAll;
    private boolean logHeartBeat;
    private boolean logInstrument;
    private boolean logSnapshot;
    private boolean logUpdates;
    private boolean logBbo;
    private boolean logTrade;
    private boolean logTradeCancel;
    private boolean logTradeCorrection;
    private Set<String> logSymbols;
    //
    private int numberOfConnections = 1;
    private int statsDisplaySeconds = 30;


    public OpenfeedClientConfigImpl dup() throws CloneNotSupportedException {
        OpenfeedClientConfigImpl o = new OpenfeedClientConfigImpl();
        o.host = this.host;
        o.port = this.port;
        o.wireProtocol = this.wireProtocol;
        o.userName = this.userName;
        o.password = this.password;
        o.reconnect = this.reconnect;
        //
        o.symbols = this.symbols;
        o.marketIds = this.marketIds;
        o.exchanges = this.exchanges;
        o.channelIds = this.channelIds;
        //
        o.subcriptionType = this.subcriptionType;
        o.instrumentRequest = this.instrumentRequest;
        o.instrumentCrossReferenceRequest = this.instrumentCrossReferenceRequest;
        o.randomInstruments = this.randomInstruments;
        //
        o.logAll = this.logAll;
        o.logHeartBeat = this.logHeartBeat;
        o.logInstrument = this.logInstrument;
        o.logSnapshot = this.logSnapshot;
        o.logUpdates = this.logUpdates;
        o.logBbo = this.logBbo;
        o.logTrade = this.logTrade;
        o.logTradeCancel = this.logTradeCancel;
        o.logTradeCorrection = this.logTradeCorrection;
        //
        o.numberOfConnections = this.numberOfConnections;
        o.statsDisplaySeconds = this.statsDisplaySeconds;
        return o;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("server", host + ":" + port).add("protocl", wireProtocol)
                .toString();
    }

    @Override
    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String[] getSymbols() {
        return this.symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    @Override
    public boolean isLogUpdate() {
        return this.logUpdates;
    }

    public void setLogUpdates(boolean b) {
        this.logUpdates = b;

    }

    @Override
    public WireProtocol getWireProtocol() {
        return this.wireProtocol;
    }

    public void setWireProtocol(WireProtocol wp) {
        this.wireProtocol = wp;
    }

    @Override
    public boolean isLogBbo() {
        return logBbo;
    }

    public void setLogBbo(boolean logBbo) {
        this.logBbo = logBbo;
    }

    @Override
    public boolean isLogTrade() {
        return logTrade;
    }

    @Override
    public boolean isLogTradeCancel() {
        return this.logTradeCancel;
    }

    @Override
    public boolean isLogTradeCorrection() {
        return this.logTradeCorrection;
    }

    public void setLogTrade(boolean logTrades) {
        this.logTrade = logTrades;
    }

    @Override
    public boolean isLogInstrument() {
        return logInstrument;
    }

    @Override
    public boolean isExchangeRequest() {
        return exchangeRequest;
    }

    public void setExchangeRequest(boolean exchangeRequest) {
        this.exchangeRequest = exchangeRequest;
    }

    @Override
    public boolean isLogSnapshot() {
        return logSnapshot;
    }

    public boolean isLogUpdates() {
        return logUpdates;
    }

    public boolean isLogTrades() {
        return logTrade;
    }

    public void setLogInstrument(boolean logInstrument) {
        this.logInstrument = logInstrument;
    }

    public void setLogSnapshot(boolean logSnapshot) {
        this.logSnapshot = logSnapshot;
    }

    @Override
    public long[] getMarketIds() {
        return marketIds;
    }

    public void setMarketIds(long[] marketIds) {
        this.marketIds = marketIds;
    }

    @Override
    public String[] getExchanges() {
        return this.exchanges;
    }

    public void setExchanges(String[] exchanges) {
        this.exchanges = exchanges;
    }
    public int[] getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(int[] channelIds) {
        this.channelIds = channelIds;
    }

    @Override
    public int getNumberOfConnections() {
        return this.numberOfConnections;
    }

    public void setNumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    public void setSubscriptonType(SubscriptionType type) {
        this.subcriptionType = type;

    }

    public void setInstrumentRequest(boolean b) {
        this.instrumentRequest = b;

    }

    public void setInstrumentCrossReferenceRequest(boolean b) {
        this.instrumentCrossReferenceRequest = b;
    }

    @Override
    public SubscriptionType getSubcriptionType() {
        return subcriptionType;
    }

    public void setSubcriptionType(SubscriptionType subcriptionType) {
        this.subcriptionType = subcriptionType;
    }

    @Override
    public boolean isInstrumentRequest() {
        return instrumentRequest;
    }

    @Override
    public boolean isInstrumentCrossReferenceRequest() {
        return instrumentCrossReferenceRequest;
    }

    @Override
    public boolean isLogAll() {
        return this.logAll;
    }

    public void setLogAll(boolean logAll) {
        this.logAll = logAll;
    }

    public void setRandomInstruments(int numInstruments) {
        this.randomInstruments = numInstruments;
    }

    @Override
    public int getRandomInstruments() {
        return this.randomInstruments;
    }

    @Override
    public int getStatsDisplaySeconds() {
        return this.statsDisplaySeconds;
    }

    public void setStatsDisplaySeconds(int sec) {
        this.statsDisplaySeconds = sec;
    }

    @Override
    public boolean isLogHeartBeat() {
        return this.logHeartBeat;
    }

    public void setLogHeartBeat(boolean logHeartBeat) {
        this.logHeartBeat = logHeartBeat;
    }

    @Override
    public boolean isReconnect() {
        return reconnect;
    }

    public void setReconnect(boolean reconnect) {
        this.reconnect = reconnect;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public long getReconnectDelaySec() {
        return this.reconnectDelaySec;
    }

    public void setReconnectDelaySec(long sec) {
        this.reconnectDelaySec = sec;
    }

    public void setLogTradeCancel(boolean v) {
        this.logTradeCancel = v;
    }

    public void setLogTradeCorrection(boolean v) {
        this.logTradeCorrection = v;
    }

    public Set<String> getLogSymbols() {
        return logSymbols;
    }

    public void setLogSymbols(Set<String> logSymbols) {
        this.logSymbols = logSymbols;
    }

    @Override
    public boolean isLogSymbol(String s) {
        return logSymbols != null ? logSymbols.contains(s) : false;
    }

}
