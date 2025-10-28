package br.com.sistema.folhapagamento.entities;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;


public abstract class Employee {

    private String name;
    private String cpf;
    private String address;
    private String contact;
    private String sector;
    private String type;
    protected BigDecimal basePaymentValue;


    // METHODS
    public void applyIncrease(BigDecimal percentage) {
        // basePaymentValue = basePaymentValue * (1 + (percentage / 100))
        BigDecimal multiplier = percentage.divide(new BigDecimal("100"), 2, HALF_UP).add(ONE);
        this.basePaymentValue = this.basePaymentValue.multiply(multiplier);

    }

    public void showData() {
        // PEGANDO A PRIMEIRA LETRA DO TITULO E CONCATENANDO COM O RESTANTE
        var titleOfType = this.getType().substring(0, 1).toUpperCase() +
                this.getType().substring(1) ;

        System.out.printf("""
                           ========== Funcionários %s ==========
                           Funcionário: %s
                           CPF: %s
                           Endereço: %s
                           Telefone: %s
                           Setor de Trabalho: %s
                           Tipo de Contrato: %s
                           """,
                titleOfType,
                getName(),
                getCpf(),
                getAddress(),
                getContact(),
                getSector(),
                getType());
    }

    // METHOD ABSTRATO, AS CLASSES FILHAS IRAM DEFINIR SEUS PRÓPRIOS CÁLCULOS
    public abstract BigDecimal calculatePayment();

    // GETTERS AND SETS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    // CONSTRUCT
    public Employee(String name, 
                    String cpf, 
                    String address, 
                    String contact, 
                    String sector, 
                    String type) {
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.contact = contact;
        this.sector = sector;
        this.type = type;
    }
}
