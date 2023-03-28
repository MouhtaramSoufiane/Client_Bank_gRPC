package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;

public class BankGrpcClient {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 3333)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceBlockingStub blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        Bank.ConverCurrencyRequest currencyRequest= Bank.ConverCurrencyRequest.newBuilder()
                .setCurrencyTo("EURO")
                .setCurrencyFrom("DHS")
                .setAmount(1000)
                .build();
        Bank.ConvertCurrencyResponse currencyResponse=blockingStub.convert(currencyRequest);
        System.out.println(currencyResponse);
    }
}
