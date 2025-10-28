package br.com.sistema.folhapagamento.entities;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import static java.math.RoundingMode.HALF_UP;


public class Hourly extends Employee{

    private BigDecimal hourlyRate; // VALOR DA HORA
    private int workedHours;       // HORAS TRABALHADAS

    // CONSTRUTOR
    public Hourly(String name,
                  String cpf,
                  String address,
                  String contact,
                  String sector,
                  String type,
                  BigDecimal hourlyRate,
                  int workedHours) {
        super(name, cpf, address, contact, sector, type);

        this.hourlyRate = hourlyRate;
        this.workedHours = workedHours;

        this.basePaymentValue = hourlyRate;

    }


    //EXIBIÇÃO DO DADOS
    @Override
    public void showData() {
        super.showData();

        String rateFormatado = NumberFormat.getCurrencyInstance(
                Locale.of("pt", "BR"))
                .format(this.basePaymentValue);


        String salarioFormatado = NumberFormat.getCurrencyInstance(
                        Locale.of("pt", "BR"))
                .format(calculatePayment());

        System.out.printf("""
            ----------------------------------------------
            - Horas Trabalhadas: %d hrs
            - Valor da Hora: %s
            - Pagamento Total: %s
            ----------------------------------------------
            """, this.workedHours, rateFormatado, salarioFormatado);

    }

    // CALCULO DEFINIDO
    @Override
    public BigDecimal calculatePayment() {
        BigDecimal totalHours = BigDecimal.valueOf(getWorkedHours());

        return (this.basePaymentValue == null)?
                BigDecimal.ZERO.setScale(2,HALF_UP) :
                this.basePaymentValue.multiply(totalHours).setScale(2, HALF_UP);

    }

    // GETTERS E SET

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }
}
