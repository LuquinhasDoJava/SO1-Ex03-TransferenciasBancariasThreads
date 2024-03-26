package controller;

import java.util.concurrent.Semaphore;

public class Tranferencia extends Thread{
    private int codigo;
    private double saldo;
    private double valor;
    private boolean transacao;
    Semaphore saque;
    Semaphore deposito;

    public Tranferencia(int codigoConta,double saldoConta, double valor, boolean transacao, Semaphore saque, Semaphore deposito){
        this.codigo = codigoConta;
        this.saldo = saldoConta;
        this.valor = valor;
        this.transacao = transacao;
        this.saque = saque;
        this.deposito = deposito;
    }
    public void run(){
        if(transacao){
            transacaoSaque();
        }else {
            transacaoDeposito();
        }
    }

    private void transacaoDeposito() {
        try {
            deposito.acquire();
            System.err.println("Conta:"+codigo+"| Depositando:"+ valor+"| Saldo atual:"+saldo);
            saldo += valor;
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.err.println("Conta:"+codigo+"| Saldo atualizado:"+saldo);
            deposito.release();
        }
    }

    private void transacaoSaque() {
        try {
            saque.acquire();
            System.out.println("Conta:"+codigo+"| Saque:"+ valor+"| Saldo atual:"+saldo);
            if(saldo<valor){
                System.out.println("Saldo insuficiente para transação da conta: "+codigo);
            }else{
                sleep(1000);
                saldo -= valor;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Conta:"+codigo+"| Saldo atualizado:"+saldo);
            saque.release();
        }
    }
}