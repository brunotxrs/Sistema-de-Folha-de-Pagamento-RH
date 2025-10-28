package br.com.sistema.folhapagamento.entities;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import static java.math.RoundingMode.HALF_UP;

public class Salaried extends Employee{

    // CONSTRUTOR
    public Salaried(String name,
                    String cpf,
                    String address,
                    String contact,
                    String sector,
                    String type,
                    BigDecimal salary) {
        super(name, cpf, address, contact, sector, type);
        this.basePaymentValue = salary;
    }

    // EXIBIÇÃO DE DADOS
    @Override
    public void showData() {

        super.showData();
        
        String salarioFormatado = NumberFormat.getCurrencyInstance(
                        Locale.of("pt", "BR"))
                .format(calculatePayment());

        System.out.printf("""
                ----------------------------------------------
                - Salário mensal do trabalhador: %s
                ----------------------------------------------
                """, salarioFormatado);
    }

    // CALCULO DEFINIDO
    @Override
    public BigDecimal calculatePayment() {
        return (this.basePaymentValue == null)?
                BigDecimal.ZERO.setScale(2, HALF_UP) :
                this.basePaymentValue.setScale(2, HALF_UP);
    }

}
