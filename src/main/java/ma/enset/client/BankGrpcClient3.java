package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;

public class BankGrpcClient3 {
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
        AsynStub.getCurrencyStream(currencyRequest, new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("----------------------");
                System.out.println(convertCurrencyResponse);
                System.out.println("----------------------");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("END");

            }
        });
        System.out.println("************?");
        System.in.read();
    }
}
