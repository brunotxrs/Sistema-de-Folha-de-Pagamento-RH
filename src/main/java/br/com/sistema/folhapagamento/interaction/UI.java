package br.com.sistema.folhapagamento.interaction;

import br.com.sistema.folhapagamento.entities.Employee;
import br.com.sistema.folhapagamento.entities.Salaried;
import br.com.sistema.folhapagamento.entities.Hourly;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// CLASSE PARA INTERAÇÃO DO USUÁRIO
public class UI {

    // ATRIBUTOS DE INSTÂNCIA
    private final Scanner scanner = new Scanner(System.in);
    // LISTA POLIMÓRFICA, GUARDA ASSALARIADOS E HORISTAS NA MESMA LISTA
    private final List<Employee> employeeList = new ArrayList<>();
    // LIMITE PARA CRIAÇÃO DE TIPOS DE TRABALHADORES
    private static final int MAX_EMPLOYEES = 10;

    // METHOD PARA RODAR O PROGRAMA NA CLASSE MAIN
    public void runProgram() {

        System.out.println("==============================================");
        System.out.println("****** Sistema de Folha de Pagamento RH ******");
        System.out.println("==============================================");

        int count = 0;

        // LOOP PARA CADASTRAR ATÉ 10 FUNCIONÁRIOS
        while (count < MAX_EMPLOYEES) {
            String choice = readValidString("Deseja adicionar o funcionário " + (count + 1) + " de " + MAX_EMPLOYEES + " [S/N]? ");

            if (choice.equalsIgnoreCase("N")) {
                break;
            }
            if (choice.equalsIgnoreCase("S")) {
                Employee newEmployee = registerNewEmployee();
                if (newEmployee != null) {
                    employeeList.add(newEmployee);
                    count++;
                }
            }
        }

        // MOSTRAR DADOS E PAGAMENTOS ANTERIORES ---
        System.out.println("\n======== PAGAMENTOS ANTES DO AUMENTO ========");
        displayAllEmployeesData();


        // INTERAÇÃO PARA IMPLEMENTAR AUMENTO, CONTENDO LOOP PARA ENTRADAS APENA [S | N ]
        var question = readValidYesNo("Deseja aplicar aumento? [S/N] ");

        if (question.equalsIgnoreCase("S")){

            // --- APLICAR AUMENTO GERAL ---
            applyGeneralIncrease();

            // --- MOSTRAR DADOS E PAGAMENTOS APÓS O AUMENTO ---
            System.out.println("\n========= PAGAMENTOS APÓS O AUMENTO ==========");
            displayAllEmployeesData();

        }

        System.out.println("Programa finalizado!");


    }

    // METHOD PARA ENTRADA DE DADOS DOS FUNCIONÁRIOS E SEUS TIPOS
    private Employee registerNewEmployee() {
        System.out.println("\n--- Cadastro de Novo Funcionário ---");

        // LEITURA DOS DADOS COMUNS USANDO VALIDADORES ROBUSTOS
        String name = readValidName("Informe o nome do Funcionário: ");

        String cpf = readValidCPF("Informe o CPF: ");

        String address = readValidString("Informe o Endereço: ");

        String contact = readValidContact("Informe o Telefone: ");

        String sector = readValidString("Informe o Setor: ");

        // PONTO DE VALIDAÇÃO DE TIPO [ ASSALARIADO / HORISTA ] - (POLIMORFISMO)
        String type = readValidEmployeeType("Informe o tipo [Assalariado/Horista]: ");

        // CONDICIONAL PARA CRIAÇÃO DOS NOVOS OBJETOS COM SEUS TIPOS
        if (type.equalsIgnoreCase("assalariado")) {
            BigDecimal salary = readValidBigDecimal("Informe o Salário Mensal: R$ ");
            return new Salaried(name, cpf, address, contact, sector, type, salary);

        } else if (type.equalsIgnoreCase("horista")) {
            // LÓGICA PARA HORISTA (Você fará isso após criar a classe Hourly)
             int hours = readValidInt("Informe as Horas Trabalhadas: ");
             BigDecimal rate = readValidBigDecimal("Informe o Valor da Hora: R$ ");
             return new Hourly(name, cpf, address, contact, sector, type, rate, hours);
        }

        return null;
    }

    //METHOD PARA EXIBIR DADOS (POLIMORFISMO EM AÇÃO!)
    private void displayAllEmployeesData() {
        for (Employee emp : employeeList) {
            emp.showData(); // O JAVA IRA CHAMAR O [ showData() ] CORRETO (ASSALARIADO ou HORISTA)
        }
    }

    // METHOD PARA CHECAGEM CASO TENHA OU NAO FUNCIONÁRIOS PARA APLICAR O AUMENTO
    private void applyGeneralIncrease() {
        // CONDIÇÃO CASO A LISTA DE FUNCIONÁRIO ESTEJA VAZIA
        if (employeeList.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado para aplicar aumento.");
            return;
        }

        // VALIDADOR DE PORCENTAGEM, INCREMENTADO A ENTRADA DE DADOS SOBRE O VALOR A SER APLICADO AO AUMENTO
        BigDecimal percentage = readValidBigDecimal("\nInforme a porcentagem de aumento geral (ex: 10 para 10%): ");


        for (Employee emp : employeeList) {
            emp.applyIncrease(percentage); // O AUMENTO É APLICADO NA CLASSE BASE(Employee) ASSIM APLICANDO A TODOS OS FUNCIONÁRIOS
        }
        System.out.printf("Aumento de %.2f%% aplicado a todos os %d funcionários.\n", percentage.doubleValue(), employeeList.size());
    }

    // VALIDADOR PARA ENTRADA APENAS ASSALARIADO E HORISTA
    private String readValidEmployeeType(String promptMessage) {
        String input;
        while (true) {
            input = readValidString(promptMessage);
            if (input.equalsIgnoreCase("assalariado") || input.equalsIgnoreCase("horista")) {
                return input;
            }
            System.out.println("❌ ERRO: Tipo inválido. Digite 'Assalariado' ou 'Horista'.");
        }
    }

    // --- MÉTODOS DE VALIDAÇÃO ---
    private String readValidString(String promptMessage) {
        String input;

        while (true) {
            System.out.print(promptMessage);

            // LEITURA DA LINHA E REMOÇÃO DOS ESPAÇOS EM BRANCO DO INÍCIO AO FIM
            input = scanner.nextLine().trim();

            // CONDIÇÃO SE A STRING ESTÁ VAZIA
            if (input.isEmpty()) {
                System.out.println("""
                        ❌ ERRO: A entrada não pode ser vazia. Por favor, \s
                        digite uma informação válida.\s""");

            } else {
                // A STRING É VALIDA E NÃO ESTÁ VAZIA
                return input; // SUCESSO: SAI DO LOOP
            }
        }

    }
    private BigDecimal readValidBigDecimal(String promptMessage) {
        BigDecimal value;

        while (true) {

            System.out.print(promptMessage);
            String input = scanner.nextLine().trim();

            try {
                value = new BigDecimal(input);

                // CONDIÇÃO PARA NÃO ACEITAR ENTRADA NEGATIVO
                if (value.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("❌ ERRO: O valor não pode ser negativo. Tente novamente.");

                } else {
                    // VALOR ACEITO
                    return value;
                }

            } catch (NumberFormatException e) {
                // VALIDADOR QUE A ENTRADA SE APENAS NÚMEROS COM VALORES DECIMAL [ . ] - NÃO PERMITINDO LETRAS E CARACTERES
                System.out.println("""
                    ❌ ERRO: Valor monetário inválido.
                    Digite apenas números. Use o ponto '.' para decimais, se necessário.
                    """);
            }
        }
    }
    private int readValidInt(String promptMessage) {
        int value;

        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine().trim(); // lLEITURA DA STRING

            try {
                // CONVERTE A STRING PARA NUMERO INTEIRO
                value = Integer.parseInt(input);

                // CONDIÇÃO QUE O VALOR NÃO SEJA NUMERO NEGATIVO, OU SEJA, 0
                if (value <= 0) {
                    System.out.println("""
                            ❌ ERRO: Entrada inválida. Digite um número inteiro positivo (ex: 8, 40, 160).
                            """);
                } else {
                    // VALOR POSITIVO ASSIM SENDO VALIDO
                    return value; // SAI DO LOOP
                }

            } catch (NumberFormatException e) {
                // VALIDANDO A ENTRADA SEJA APENAS NÚMEROS, NÃO PERMITINDO QUE SEJA LETRA OU CARACTERES
                System.out.println("❌ ERRO: Entrada inválida. Por favor, digite apenas números inteiros.");
            }
        }
    }
    // --------------------------------------------------------------------------


    // VALIDADOR DE STRING COM USO DE MATCHES PARA APENAS LETRAS
    private boolean isValidString(String promptName){
        if (promptName.trim().isEmpty()) {
            return false;
        }

        return promptName.trim().matches("[a-zA-Z\\s]+");
    }

    // VALIDAR ESPECÍFICOS, NOME | CPF | CONTATO
    private String readValidName(String promptMessage) {
        String input;

        while (true) {
            System.out.print(promptMessage);

            // LEITURA DA LINHA E REMOÇÃO DE ESPAÇOS EM BRANCO DO INICIO E FIM
            input = scanner.nextLine().trim();

            // CONDIÇÃO DE CHECAGEM DE STRING ESTEJA VAZIA
            if (input.isEmpty()) {
                System.out.println("""
                        ❌ ERRO: A entrada não pode ser vazia.
                        Tente novamente!""");
                // O LOOP CONTINUA E O PROMPT É REIMPRESSO
            } else if(!isValidString(input)){
                System.out.println("""
                        ❌ ERRO: Nome inválido. Por favor, utilize apenas letras e espaços.
                        """);
            } else {
                // A STRING É VÁLIDA
                return (input); // SAI DO LOOP
            }
        }

    }

    private String readValidCPF(String promptMessage) {

        String input;

        while (true) {
            System.out.print(promptMessage);


            try {

                // LEITURA DA LINHA E REMOÇÃO DE ESPAÇOS EM BRANCO DO INICIO E FIM
                input = scanner.nextLine().trim();

                // REMOÇÃO DE TODOS OS CARACTERES QUE NAO SAO DÍGITOS LIMPAR A ENTRADA
                String cleanedInput = input.replaceAll("[^0-9]", "");

                // CONDIÇÃO DE CHECAGEM DE STRING ESTEJA VAZIA
                if (cleanedInput.isEmpty()) {
                    System.out.println("""
                        ❌ ERRO: A entrada não pode ser vazia. Por favor, \s
                        digite uma informação válida.\s""");
                    // O LOOP CONTINUA E O PROMPT É REIMPRESSO
                } else if (cleanedInput.length() != 11){ // STRING DEVE TER 11 DÍGITOS
                    System.out.println("""
                        ❌ ERRO: O CPF deve ter exatamente 11 dígitos.
                        Por favor, digite novamente (apenas números).
                        """);
                } else {
                    // A STRING É VÁLIDA
                    return formatCPF(cleanedInput); // SUCESSO SAI DO LOOP
                }
            }catch (NumberFormatException ex){ // VALIDADOR QUE NAO PERMITI LETRAS
                System.out.println("❌ ERRO: Entrada inválida. Por favor, digite apenas números inteiros.");
            }

        }

    }

    private String readValidContact(String promptMessage) {
        String input;

        while (true) {
            System.out.print(promptMessage);

            // LEITURA DA LINHA E REMOÇÃO DE ESPAÇOS EM BRANCO DO INICIO E FIM
            input = scanner.nextLine().trim();

            // REMOÇÃO DE TODOS OS CARACTERES QUE NAO SAO DÍGITOS LIMPAR A ENTRADA
            String cleanedInput = input.replaceAll("[^0-9]", "");

            // CONDIÇÃO DE CHECAGEM DE STRING ESTEJA VAZIA
            if (cleanedInput.isEmpty()) {
                System.out.println("""
                        ❌ ERRO: A entrada não pode ser vazia. Por favor, \s
                        digite uma informação válida.\s""");
                // O LOOP CONTINUA E O PROMPT É REIMPRESSO
            }else if (cleanedInput.length() != 10 && cleanedInput.length() != 11){ // PONTO DE VALIDADOR QUE DÍGITOS SEJA DE 10 A 11
                System.out.println("""
                        ❌ Informe o contato valido com 10 a 11 números!
                        """);
            } else {
                // A STRING É VÁLIDA
                return formatContact(cleanedInput); // SUCESSO SAI DO LOOP
            }
        }

    }


    // FORMATANDO SAÍDAS | CPF | CONTATO
    private String formatCPF(String cpf) {
        // REMOÇÃO DE TODOS OS CARACTERES QUE NAO SAO DÍGITOS LIMPAR A ENTRADA
        cpf = cpf.replaceAll("[^0-9]", "");

        // APLICANDO A MÁSCARA USANDO A EXPRESSÃO REGULAR
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private String formatContact(String contact){

        // REMOÇÃO DE TODOS OS CARACTERES QUE NAO SAO DÍGITOS LIMPAR A ENTRADA
        String numberClean = contact.replaceAll("\\D", "");

        // CONDIÇÃO PARA CRIAÇÃO DA MASCARA DO FORMATO DOS CONTATOS TANTO FIXO QUANDO CELULAR
        if (numberClean.length() == 10) {
            String dd = contact.substring(0, 2);
            String firstFour = contact.substring(2, 6);
            String firstFinal = contact.substring(6);
            return "(" + dd + ") " + firstFour + "-" + firstFinal; // PARA TELEFONE FIXO 8 DÍGITOS (XX) XXXX-XXXX

        } else if (numberClean.length() == 11) {
            String dd = contact.substring(0, 2);
            String firstNumber = contact.substring(2,3);
            String firstFour = contact.substring(3, 7);
            String firstFinal = contact.substring(7);
            return "(" + dd + ") " + firstNumber + " " + firstFour + "-" + firstFinal; // PARA CELULAR 9 DÍGITOS (XX) X XXXX-XXXX

        }

        return numberClean;
    }

    // METHOD AUXILIAR
    private String readValidYesNo(String promptMessage) {
        String input;
        while (true) {
            input = readValidString(promptMessage); // REUTILIZA A CHECAGEM DE VAZIO

            if (input.equalsIgnoreCase("s") || input.equalsIgnoreCase("n")) {
                return input;
            }
            System.out.println("❌ ERRO: Opção inválida. Digite 'S' (Sim) ou 'N' (Não).");
        }
    }

}