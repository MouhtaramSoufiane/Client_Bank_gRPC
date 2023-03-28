package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcClient4 {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 3333)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceStub AsynStub = BankServiceGrpc.newStub(managedChannel);

        StreamObserver<Bank.ConverCurrencyRequest> performStream = AsynStub.performStream(new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse convertCurrencyResponse) {
                System.out.println("-----------------------");
                System.out.println(convertCurrencyResponse);
                System.out.println("-----------------------");
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
        Timer timer=new Timer("Timer");
        timer.schedule(new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                Bank.ConverCurrencyRequest currencyRequest= Bank.ConverCurrencyRequest.newBuilder()
                        .setAmount(Math.random()*1000)
                        .build();
                performStream.onNext(currencyRequest);
                System.out.println("===============Counter=============="+counter);
                counter++;
                if (counter==30){
                    timer.cancel();
                    performStream.onCompleted();
                }

            }
        },1000,1000);
        System.out.println("************?");
        System.in.read();
    }
}
