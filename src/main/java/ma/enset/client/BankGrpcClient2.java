package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;

public class BankGrpcClient2 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 3333)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceStub AsynStub = BankServiceGrpc.newStub(managedChannel);
        Bank.ConverCurrencyRequest currencyRequest= Bank.ConverCurrencyRequest.newBuilder()
                .setCurrencyTo("EURO")
                .setCurrencyFrom("DHS")
                .setAmount(1000)
                .build();
        AsynStub.convert(currencyRequest, new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println(convertCurrencyResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());

            }

            @Override
            public void onCompleted() {
                System.out.println("End");

            }
        });
        System.out.println("************?");
        System.in.read();
    }
}
