package org.dman;

import org.dman.contract.ARB;
import org.dman.contract.TokenDistributor;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    private static long chainID;
    private static StaticGasProvider gasProvider;
    private static TransactionManager transactionManager;
    private static final Scanner inputAction = new Scanner(System.in), inputGasPrice = new Scanner(System.in);
    private static Web3j web3j_Ethereum = RPC.web3j_ETH, web3j_Arbitrum;
    private static BigInteger currentBlock, nonce, inGasPrice = BigInteger.valueOf(22000000000L); // 100000000L
    private static final BigInteger startBlock = new BigInteger("16890400"), claimableTokens = new BigInteger("1500000000000000000000");
    private static Credentials credentials = Credentials.create("");
    private static final String publicKey = credentials.getAddress(), contractTokenARB = "0x912CE59144191C1204E64559FE8253a0e49E6548", contractTokenDistributor = "0x67a24CE4321aB3aF51c2D0a4801c3E111D88C9d9";
    private static String ArbitrumExplorer, toAddress;

    static void setCurrentBlock() {
        try {
            currentBlock = web3j_Ethereum.ethBlockNumber().send().getBlockNumber();
            System.out.println("Current Ethereum Block: " + currentBlock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    static void checkTimeBeforeStart() {
        try {
            BigInteger startDiff = startBlock.subtract(currentBlock);
            long timeToStart = (long) (startDiff.longValue() * 12.1);
            int dayToStart = (int) (timeToStart / 60 / 60 / 24);
            timeToStart = timeToStart - (long) dayToStart * 24 * 60 * 60;
            if (timeToStart >= 0) {
                System.out.println("Start Claim Ethereum block: " + startBlock + " (It remains until: " + dayToStart + ":" + LocalTime.ofSecondOfDay(timeToStart) + ")");
                System.out.println("https://etherscan.io/block/countdown/16890400");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static void prepareWeb3j(BigInteger gasPrice, BigInteger gasLimit) {
        try {
            nonce = web3j_Arbitrum.ethGetTransactionCount(publicKey, DefaultBlockParameterName.LATEST).send().getTransactionCount();
            chainID = Long.parseLong(web3j_Arbitrum.netVersion().send().getNetVersion());

            gasProvider = new StaticGasProvider(gasPrice, gasLimit);
            transactionManager = new RawTransactionManager(web3j_Arbitrum, credentials, chainID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static void sendRawTransaction(String contract, String encodedFunction, String function, BigInteger gasPrice) {
        try {
            // EIP-1559
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    chainID,
                    nonce,
                    gasProvider.getGasLimit(),
                    contract,
                    BigInteger.ZERO,
                    encodedFunction,
                    BigInteger.valueOf(0),
                    gasPrice);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j_Arbitrum.ethSendRawTransaction(hexValue).send();

            System.out.println(ArbitrumExplorer + ethSendTransaction.getTransactionHash());
            if (ethSendTransaction.getError() != null)
                System.err.print("error " + function + "(): " + ethSendTransaction.getError().getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static void claim(BigInteger inGasPrice) {
        setCurrentBlock();
        checkTimeBeforeStart();

        //if (currentBlock.compareTo(startBlock.subtract(BigInteger.ONE)) >= 0) {
            try {
                prepareWeb3j(inGasPrice, BigInteger.valueOf(1000000)); // <------------ gas limit from claim()
                TokenDistributor contract = TokenDistributor.load(contractTokenDistributor, web3j_Arbitrum, transactionManager, gasProvider);
                String encodedFunction = contract.claim().encodeFunctionCall();

                System.out.println(encodedFunction);

                sendRawTransaction(contractTokenDistributor, encodedFunction, "claim", inGasPrice);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        //} else
            //System.err.println("ARB Claim hasn't started yet");
    }
    static void send(BigInteger inGasPrice, String to, boolean incrementNonce) {
        try {
            if (!incrementNonce)
                prepareWeb3j(inGasPrice, BigInteger.valueOf(1000000)); // <------------ gas limit from send()
            else
                nonce = nonce.add(BigInteger.ONE);
            ARB arb = ARB.load(contractTokenARB, web3j_Arbitrum, transactionManager, gasProvider);
            String encodedFunction = arb.transfer(to, claimableTokens).encodeFunctionCall();
            sendRawTransaction(contractTokenARB, encodedFunction, "transfer", inGasPrice);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static void prepareConsole() {
        System.out.println("Welcome to Arbitrum Claim CLI!" + "\n");
        System.out.println("0 - set gas price");
        System.out.println("1 - claim");
        System.out.println("2 - send");
        System.out.println("3 - claim and send");
        System.out.println("4 - exit \n");
    }
    static void choosingActions() {
        System.out.print("Select action: ");
        while (true) {
            int action = inputAction.nextInt();
            switch (action) {
                case 0 -> {
                    System.out.print("Enter gas price (Integer!): ");
                    inGasPrice = inputGasPrice.nextBigInteger().multiply(BigInteger.valueOf(1000000000));
                    System.out.print("Select action: ");
                    System.out.println();
                }
                case 1 -> {
                    claim(inGasPrice);
                    System.out.print("Select action: ");
                    System.out.println();
                }
                case 2 -> {
                    send(inGasPrice, toAddress, false);
                    System.out.print("Select action: ");
                    System.out.println();
                }
                case 3 -> {
                    claim(inGasPrice);
                    send(inGasPrice, toAddress, true);
                    System.out.print("Select action: ");
                    System.out.println();
                }
            }
            if (action == 4)
                break;
        }
        inputAction.close();
        inputGasPrice.close();
    }

    public static void main(String[] args) {
        web3j_Arbitrum = RPC.web3j_Arbitrum;
        ArbitrumExplorer = RPC.Arbitrum_Explorer;

        credentials = Credentials.create("");
        toAddress = "";

        prepareConsole();
        choosingActions();
    }
}