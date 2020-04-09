package tech.openchat.telestore.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;

/**
 * @author vgorin
 * file created on 2020-04-09 10:47
 */

@Service
public class TetherService {
    private final String tokenAddress;
    private final Web3j web3;

    private final int tokenDecimals;
    private final BigDecimal tokenPrecision;

    public TetherService(@Value("${tether.token_address}") String tokenAddress, Web3j web3) {
        this.tokenAddress = tokenAddress;
        this.web3 = web3;

        try {
            tokenDecimals = retrieveTokenDecimals();
            tokenPrecision = BigDecimal.TEN.pow(tokenDecimals);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int retrieveTokenDecimals() throws IOException {
        Function function = new Function(
                "decimals",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>(){})
        );

        return call(function, BigInteger.class).intValue();
    }

    public BigDecimal getBalance(String owner) throws IOException {
        Function function = new Function(
                "balanceOf",
                Collections.singletonList(new Address(160, owner)),
                Collections.singletonList(new TypeReference<Uint256>(){})
        );

        BigInteger balanceWei = call(function, BigInteger.class);

        return new BigDecimal(balanceWei).divide(tokenPrecision, tokenDecimals, RoundingMode.UNNECESSARY);
    }

    private <T> T call(Function function, Class<T> clazz) throws IOException {
        String result = createCall(function).send().getResult();
        return clazz.cast(FunctionReturnDecoder.decode(result, function.getOutputParameters()).iterator().next().getValue());
    }

    private Request<?, EthCall> createCall(Function function) {
        return web3.ethCall(
                Transaction.createEthCallTransaction(null, tokenAddress, FunctionEncoder.encode(function)),
                DefaultBlockParameter.valueOf("latest")
        );
    }
}
