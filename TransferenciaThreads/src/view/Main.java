package view;

import controller.Tranferencia;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore saque = new Semaphore(1);
        Semaphore deposito = new Semaphore(1);
        Random random = new Random();
        boolean transacao = true;
        for(int x = 0; x<20; x++){
            int codigo = random.nextInt(1000,9000);
            double valor = random.nextDouble();
            double saldo = random.nextDouble();
            valor *= 100;
            saldo *= 100;
            valor = Math.round(valor * 100.0) / 100.0;
            saldo = Math.round(saldo * 100.0) / 100.0;
            Tranferencia tf = new Tranferencia(codigo,saldo,valor,transacao,saque,deposito);
            tf.start();
            transacao = !transacao;
        }

    }
}
