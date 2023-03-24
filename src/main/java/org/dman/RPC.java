package org.dman;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class RPC {
    public static final Web3j web3j_ETH = Web3j.build(new HttpService("https://eth.llamarpc.com"));

    public static final Web3j web3j_Arbitrum = Web3j.build(new HttpService("https://rpc.ankr.com/arbitrum"));
    public static final String Arbitrum_Explorer = "https://arbiscan.io/tx/";
    public static final Web3j web3j_Arbitrum_Testnet = Web3j.build(new HttpService("https://arbitrum-goerli.public.blastapi.io"));
    public static final String Arbitrum_Testnet_Explorer = "https://testnet.arbiscan.io/tx/";
}
