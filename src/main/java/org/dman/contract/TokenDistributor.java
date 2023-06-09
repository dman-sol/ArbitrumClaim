package org.dman.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class TokenDistributor extends Contract {
    public static final String BINARY = "60e06040523480156200001157600080fd5b5060405162001b8038038062001b80833981016040819052620000349162000436565b6200003f3362000315565b6001600160a01b038616620000a75760405162461bcd60e51b8152602060048201526024808201527f546f6b656e4469737472696275746f723a207a65726f20746f6b656e206164646044820152637265737360e01b60648201526084015b60405180910390fd5b6001600160a01b0385166200010b5760405162461bcd60e51b8152602060048201526024808201527f546f6b656e4469737472696275746f723a207a65726f207377656570206164646044820152637265737360e01b60648201526084016200009e565b6001600160a01b0384166200016f5760405162461bcd60e51b8152602060048201526024808201527f546f6b656e4469737472696275746f723a207a65726f206f776e6572206164646044820152637265737360e01b60648201526084016200009e565b438311620001c75760405162461bcd60e51b815260206004820152602f602482015260008051602062001b6083398151915260448201526e6520696e207468652066757475726560881b60648201526084016200009e565b8282116200021c5760405162461bcd60e51b815260206004820152602c602482015260008051602062001b6083398151915260448201526b19481899599bdc9948195b9960a21b60648201526084016200009e565b6001600160a01b0381166200027f5760405162461bcd60e51b815260206004820152602260248201527f546f6b656e4469737472696275746f723a207a65726f2064656c656761746520604482015261746f60f01b60648201526084016200009e565b6040516317066a5760e21b81526001600160a01b038281166004830152871690635c19a95c90602401600060405180830381600087803b158015620002c357600080fd5b505af1158015620002d8573d6000803e3d6000fd5b5050506001600160a01b03871660805250620002f48562000365565b60a083905260c0829052620003098462000315565b505050505050620004b3565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6001600160a01b038116620003d35760405162461bcd60e51b815260206004820152602d60248201527f546f6b656e4469737472696275746f723a207a65726f2073776565702072656360448201526c6569766572206164647265737360981b60648201526084016200009e565b600180546001600160a01b0319166001600160a01b0383169081179091556040517fbea8251f76064f657f2a744bf08a1b5700486d06eb94922162892eb022d95ef690600090a250565b6001600160a01b03811681146200043357600080fd5b50565b60008060008060008060c087890312156200045057600080fd5b86516200045d816200041d565b602088015190965062000470816200041d565b604088015190955062000483816200041d565b80945050606087015192506080870151915060a0870151620004a5816200041d565b809150509295509295509295565b60805160a05160c051611637620005296000396000818161011c0152818161040e01526107fd015260008181610167015261074d015260008181610261015281816102c0015281816104c7015281816106020152818161097f01528181610b3001528181610bd20152610fc601526116376000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c806378e2b59411610097578063b438abde11610066578063b438abde14610216578063f2fde38b14610229578063f6e0df9f1461023c578063fc0c546a1461025c57600080fd5b806378e2b5941461019157806384d24226146101a45780638da5cb5b146101c4578063ae373c1b1461020357600080fd5b80634838ed19116100d35780634838ed19146101515780634e71d92d1461015a57806358c13b7e14610162578063715018a61461018957600080fd5b80632e1a7d4d146100fa57806335faa4161461010f5780633da082a014610117575b600080fd5b61010d6101083660046113ac565b610283565b005b61010d61040c565b61013e7f000000000000000000000000000000000000000000000000000000000000000081565b6040519081526020015b60405180910390f35b61013e60035481565b61010d61074b565b61013e7f000000000000000000000000000000000000000000000000000000000000000081565b61010d610aaa565b61010d61019f3660046113e7565b610abe565b61013e6101b236600461143f565b60026020526000908152604090205481565b60005473ffffffffffffffffffffffffffffffffffffffff165b60405173ffffffffffffffffffffffffffffffffffffffff9091168152602001610148565b61010d6102113660046114af565b610ce8565b61010d61022436600461143f565b6110dc565b61010d61023736600461143f565b6110f0565b6001546101de9073ffffffffffffffffffffffffffffffffffffffff1681565b6101de7f000000000000000000000000000000000000000000000000000000000000000081565b61028b6111a4565b6040517fa9059cbb000000000000000000000000000000000000000000000000000000008152336004820152602481018290527f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff169063a9059cbb906044016020604051808303816000875af115801561031e573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610342919061151b565b6103d3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602560248201527f546f6b656e4469737472696275746f723a206661696c207472616e736665722060448201527f746f6b656e00000000000000000000000000000000000000000000000000000060648201526084015b60405180910390fd5b60405181815233907f7fcf532c15f0a6db0bd6d0e038bea71d30d808c7d98cb3bf7268a95bf5081b65906020015b60405180910390a250565b7f0000000000000000000000000000000000000000000000000000000000000000431015610496576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601b60248201527f546f6b656e4469737472696275746f723a206e6f7420656e646564000000000060448201526064016103ca565b6040517f70a082310000000000000000000000000000000000000000000000000000000081523060048201526000907f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff16906370a0823190602401602060405180830381865afa158015610523573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610547919061153d565b9050806000036105b3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601e60248201527f546f6b656e4469737472696275746f723a206e6f206c6566746f76657273000060448201526064016103ca565b6001546040517fa9059cbb00000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff9182166004820152602481018390527f00000000000000000000000000000000000000000000000000000000000000009091169063a9059cbb906044016020604051808303816000875af115801561064d573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610671919061151b565b6106fd576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602560248201527f546f6b656e4469737472696275746f723a206661696c20746f6b656e2074726160448201527f6e7366657200000000000000000000000000000000000000000000000000000060648201526084016103ca565b6040518181527f7f221332ee403570bf4d61630b58189ea566ff1635269001e9df6a890f413dd89060200160405180910390a160015473ffffffffffffffffffffffffffffffffffffffff16ff5b7f00000000000000000000000000000000000000000000000000000000000000004310156107fb576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602360248201527f546f6b656e4469737472696275746f723a20636c61696d206e6f74207374617260448201527f746564000000000000000000000000000000000000000000000000000000000060648201526084016103ca565b7f00000000000000000000000000000000000000000000000000000000000000004310610884576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601d60248201527f546f6b656e4469737472696275746f723a20636c61696d20656e64656400000060448201526064016103ca565b3360009081526002602052604090205480610921576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602260248201527f546f6b656e4469737472696275746f723a206e6f7468696e6720746f20636c6160448201527f696d00000000000000000000000000000000000000000000000000000000000060648201526084016103ca565b3360008181526002602052604080822091909155517fa9059cbb00000000000000000000000000000000000000000000000000000000815260048101919091526024810182905273ffffffffffffffffffffffffffffffffffffffff7f0000000000000000000000000000000000000000000000000000000000000000169063a9059cbb906044016020604051808303816000875af11580156109c8573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109ec919061151b565b610a78576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602560248201527f546f6b656e4469737472696275746f723a206661696c20746f6b656e2074726160448201527f6e7366657200000000000000000000000000000000000000000000000000000060648201526084016103ca565b60405181815233907f8629b200ebe43db58ad688b85131d53251f3f3be4c14933b4641aeebacf1c08c90602001610401565b610ab26111a4565b610abc6000611225565b565b610ac661074b565b6040517fc3cda52000000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff8681166004830152600060248301526044820186905260ff851660648301526084820184905260a482018390527f0000000000000000000000000000000000000000000000000000000000000000169063c3cda5209060c401600060405180830381600087803b158015610b7457600080fd5b505af1158015610b88573d6000803e3d6000fd5b50506040517f587cde1e00000000000000000000000000000000000000000000000000000000815233600482015273ffffffffffffffffffffffffffffffffffffffff88811693507f000000000000000000000000000000000000000000000000000000000000000016915063587cde1e90602401602060405180830381865afa158015610c1a573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c3e9190611556565b73ffffffffffffffffffffffffffffffffffffffff1614610ce1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602160248201527f546f6b656e4469737472696275746f723a2064656c6567617465206661696c6560448201527f640000000000000000000000000000000000000000000000000000000000000060648201526084016103ca565b5050505050565b610cf06111a4565b828114610d7f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602660248201527f546f6b656e4469737472696275746f723a20696e76616c69642061727261792060448201527f6c656e677468000000000000000000000000000000000000000000000000000060648201526084016103ca565b60035460005b84811015610f955760026000878784818110610da357610da3611573565b9050602002016020810190610db8919061143f565b73ffffffffffffffffffffffffffffffffffffffff16815260208101919091526040016000205415610e6c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602760248201527f546f6b656e4469737472696275746f723a20726563697069656e7420616c726560448201527f616479207365740000000000000000000000000000000000000000000000000060648201526084016103ca565b838382818110610e7e57610e7e611573565b9050602002013560026000888885818110610e9b57610e9b611573565b9050602002016020810190610eb0919061143f565b73ffffffffffffffffffffffffffffffffffffffff168152602081019190915260400160002055858582818110610ee957610ee9611573565b9050602002016020810190610efe919061143f565b73ffffffffffffffffffffffffffffffffffffffff167f87aeeb9eda09a064caef63d00f62c15063631980bfc422ad7dd30c8a79f0cbb7858584818110610f4757610f47611573565b90506020020135604051610f5d91815260200190565b60405180910390a2838382818110610f7757610f77611573565b90506020020135820191508080610f8d906115a2565b915050610d85565b506040517f70a0823100000000000000000000000000000000000000000000000000000000815230600482015281907f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff16906370a0823190602401602060405180830381865afa158015611022573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190611046919061153d565b10156110d3576040517f08c379a0000000000000000000000000000000000000000000000000000000008152602060048201526024808201527f546f6b656e4469737472696275746f723a206e6f7420656e6f7567682062616c60448201527f616e63650000000000000000000000000000000000000000000000000000000060648201526084016103ca565b60035550505050565b6110e46111a4565b6110ed8161129a565b50565b6110f86111a4565b73ffffffffffffffffffffffffffffffffffffffff811661119b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201527f646472657373000000000000000000000000000000000000000000000000000060648201526084016103ca565b6110ed81611225565b60005473ffffffffffffffffffffffffffffffffffffffff163314610abc576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657260448201526064016103ca565b6000805473ffffffffffffffffffffffffffffffffffffffff8381167fffffffffffffffffffffffff0000000000000000000000000000000000000000831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b73ffffffffffffffffffffffffffffffffffffffff811661133d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602d60248201527f546f6b656e4469737472696275746f723a207a65726f2073776565702072656360448201527f656976657220616464726573730000000000000000000000000000000000000060648201526084016103ca565b600180547fffffffffffffffffffffffff00000000000000000000000000000000000000001673ffffffffffffffffffffffffffffffffffffffff83169081179091556040517fbea8251f76064f657f2a744bf08a1b5700486d06eb94922162892eb022d95ef690600090a250565b6000602082840312156113be57600080fd5b5035919050565b73ffffffffffffffffffffffffffffffffffffffff811681146110ed57600080fd5b600080600080600060a086880312156113ff57600080fd5b853561140a816113c5565b945060208601359350604086013560ff8116811461142757600080fd5b94979396509394606081013594506080013592915050565b60006020828403121561145157600080fd5b813561145c816113c5565b9392505050565b60008083601f84011261147557600080fd5b50813567ffffffffffffffff81111561148d57600080fd5b6020830191508360208260051b85010111156114a857600080fd5b9250929050565b600080600080604085870312156114c557600080fd5b843567ffffffffffffffff808211156114dd57600080fd5b6114e988838901611463565b9096509450602087013591508082111561150257600080fd5b5061150f87828801611463565b95989497509550505050565b60006020828403121561152d57600080fd5b8151801515811461145c57600080fd5b60006020828403121561154f57600080fd5b5051919050565b60006020828403121561156857600080fd5b815161145c816113c5565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b60007fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036115fa577f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b506001019056fea26469706673582212200f8c309fa553f24bef509e94696c8c86f4c28ecc8d5acb1810aa60a75a5c339064736f6c63430008100033546f6b656e4469737472696275746f723a2073746172742073686f756c642062000000000000000000000000912ce59144191c1204e64559fe8253a0e49e6548000000000000000000000000bfc1feca8b09a5c5d3effe7429ebe24b9c09ef580000000000000000000000002b9acfd85440b7828db8e54694ee07b2b056b30c000000000000000000000000000000000000000000000000000000000101ba20000000000000000000000000000000000000000000000000000000000115d50000000000000000000000000000000000000000000000000000000000000a4b86";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_CLAIMANDDELEGATE = "claimAndDelegate";

    public static final String FUNC_CLAIMPERIODEND = "claimPeriodEnd";

    public static final String FUNC_CLAIMPERIODSTART = "claimPeriodStart";

    public static final String FUNC_CLAIMABLETOKENS = "claimableTokens";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETRECIPIENTS = "setRecipients";

    public static final String FUNC_SETSWEEPRECIEVER = "setSweepReciever";

    public static final String FUNC_SWEEP = "sweep";

    public static final String FUNC_SWEEPRECEIVER = "sweepReceiver";

    public static final String FUNC_TOKEN = "token";

    public static final String FUNC_TOTALCLAIMABLE = "totalClaimable";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event CANCLAIM_EVENT = new Event("CanClaim", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event HASCLAIMED_EVENT = new Event("HasClaimed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event SWEEPRECEIVERSET_EVENT = new Event("SweepReceiverSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event SWEPT_EVENT = new Event("Swept", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWAL_EVENT = new Event("Withdrawal", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected TokenDistributor(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TokenDistributor(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TokenDistributor(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TokenDistributor(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<CanClaimEventResponse> getCanClaimEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CANCLAIM_EVENT, transactionReceipt);
        ArrayList<CanClaimEventResponse> responses = new ArrayList<CanClaimEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CanClaimEventResponse typedResponse = new CanClaimEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CanClaimEventResponse> canClaimEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CanClaimEventResponse>() {
            @Override
            public CanClaimEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCLAIM_EVENT, log);
                CanClaimEventResponse typedResponse = new CanClaimEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CanClaimEventResponse> canClaimEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCLAIM_EVENT));
        return canClaimEventFlowable(filter);
    }

    public static List<HasClaimedEventResponse> getHasClaimedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(HASCLAIMED_EVENT, transactionReceipt);
        ArrayList<HasClaimedEventResponse> responses = new ArrayList<HasClaimedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HasClaimedEventResponse typedResponse = new HasClaimedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<HasClaimedEventResponse> hasClaimedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, HasClaimedEventResponse>() {
            @Override
            public HasClaimedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HASCLAIMED_EVENT, log);
                HasClaimedEventResponse typedResponse = new HasClaimedEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<HasClaimedEventResponse> hasClaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HASCLAIMED_EVENT));
        return hasClaimedEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<SweepReceiverSetEventResponse> getSweepReceiverSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SWEEPRECEIVERSET_EVENT, transactionReceipt);
        ArrayList<SweepReceiverSetEventResponse> responses = new ArrayList<SweepReceiverSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SweepReceiverSetEventResponse typedResponse = new SweepReceiverSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newSweepReceiver = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SweepReceiverSetEventResponse> sweepReceiverSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SweepReceiverSetEventResponse>() {
            @Override
            public SweepReceiverSetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SWEEPRECEIVERSET_EVENT, log);
                SweepReceiverSetEventResponse typedResponse = new SweepReceiverSetEventResponse();
                typedResponse.log = log;
                typedResponse.newSweepReceiver = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SweepReceiverSetEventResponse> sweepReceiverSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SWEEPRECEIVERSET_EVENT));
        return sweepReceiverSetEventFlowable(filter);
    }

    public static List<SweptEventResponse> getSweptEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SWEPT_EVENT, transactionReceipt);
        ArrayList<SweptEventResponse> responses = new ArrayList<SweptEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SweptEventResponse typedResponse = new SweptEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SweptEventResponse> sweptEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SweptEventResponse>() {
            @Override
            public SweptEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SWEPT_EVENT, log);
                SweptEventResponse typedResponse = new SweptEventResponse();
                typedResponse.log = log;
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SweptEventResponse> sweptEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SWEPT_EVENT));
        return sweptEventFlowable(filter);
    }

    public static List<WithdrawalEventResponse> getWithdrawalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWAL_EVENT, transactionReceipt);
        ArrayList<WithdrawalEventResponse> responses = new ArrayList<WithdrawalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawalEventResponse>() {
            @Override
            public WithdrawalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWAL_EVENT, log);
                WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWAL_EVENT));
        return withdrawalEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> claim() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimAndDelegate(String delegatee, BigInteger expiry, BigInteger v, byte[] r, byte[] s) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMANDDELEGATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, delegatee), 
                new org.web3j.abi.datatypes.generated.Uint256(expiry), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> claimPeriodEnd() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLAIMPERIODEND, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> claimPeriodStart() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLAIMPERIODSTART, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> claimableTokens(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLAIMABLETOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setRecipients(List<String> _recipients, List<BigInteger> _claimableAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETRECIPIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_recipients, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_claimableAmount, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSweepReciever(String _sweepReceiver) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSWEEPRECIEVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sweepReceiver)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sweep() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SWEEP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> sweepReceiver() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SWEEPRECEIVER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> token() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalClaimable() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALCLAIMABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TokenDistributor load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenDistributor(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TokenDistributor load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenDistributor(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TokenDistributor load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TokenDistributor(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TokenDistributor load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TokenDistributor(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TokenDistributor> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _token, String _sweepReceiver, String _owner, BigInteger _claimPeriodStart, BigInteger _claimPeriodEnd, String delegateTo) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _sweepReceiver), 
                new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodEnd), 
                new org.web3j.abi.datatypes.Address(160, delegateTo)));
        return deployRemoteCall(TokenDistributor.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<TokenDistributor> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _token, String _sweepReceiver, String _owner, BigInteger _claimPeriodStart, BigInteger _claimPeriodEnd, String delegateTo) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _sweepReceiver), 
                new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodEnd), 
                new org.web3j.abi.datatypes.Address(160, delegateTo)));
        return deployRemoteCall(TokenDistributor.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TokenDistributor> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _token, String _sweepReceiver, String _owner, BigInteger _claimPeriodStart, BigInteger _claimPeriodEnd, String delegateTo) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _sweepReceiver), 
                new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodEnd), 
                new org.web3j.abi.datatypes.Address(160, delegateTo)));
        return deployRemoteCall(TokenDistributor.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TokenDistributor> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _token, String _sweepReceiver, String _owner, BigInteger _claimPeriodStart, BigInteger _claimPeriodEnd, String delegateTo) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _sweepReceiver), 
                new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodStart), 
                new org.web3j.abi.datatypes.generated.Uint256(_claimPeriodEnd), 
                new org.web3j.abi.datatypes.Address(160, delegateTo)));
        return deployRemoteCall(TokenDistributor.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CanClaimEventResponse extends BaseEventResponse {
        public String recipient;

        public BigInteger amount;
    }

    public static class HasClaimedEventResponse extends BaseEventResponse {
        public String recipient;

        public BigInteger amount;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class SweepReceiverSetEventResponse extends BaseEventResponse {
        public String newSweepReceiver;
    }

    public static class SweptEventResponse extends BaseEventResponse {
        public BigInteger amount;
    }

    public static class WithdrawalEventResponse extends BaseEventResponse {
        public String recipient;

        public BigInteger amount;
    }
}
