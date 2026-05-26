import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Usafa usafa = new Usafa();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==============================");
            System.out.println("SISTEMA DE AGENDAMENTO - USAFA");
            System.out.println("1 - Agendar consulta");
            System.out.println("2 - Consultar meu agendamento");
            System.out.println("0 - Sair");
            System.out.println("==============================");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                agendarConsulta();
            } else if (opcao.equals("2")) {
                consultarAgendamento();
            } else if (opcao.equals("0")) {
                System.out.println("Sistema encerrado.");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    public static void agendarConsulta() {
        System.out.print("\nDigite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        String corCartao = lerCorCartao();
        Paciente paciente = new Paciente(cpf, corCartao);

        while (true) {
            ArrayList<LocalDate> datas = usafa.gerarDatasDisponiveis();
            usafa.mostrarDatas(datas);
            System.out.println("0 - Voltar");

            System.out.print("Escolha uma data: ");
            int opcaoData;

            try {
                opcaoData = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digite um número válido.");
                continue;
            }

            if (opcaoData == 0) {
                return;
            }

            if (opcaoData < 1 || opcaoData > datas.size()) {
                System.out.println("Opção inválida.");
                continue;
            }

            LocalDate dataEscolhida = datas.get(opcaoData - 1);

            if (usafa.pacienteJaTemConsultaNoDia(cpf, dataEscolhida)) {
                System.out.println("Este paciente já possui consulta nessa data.");
                return;
            }

            ArrayList<String> horarios = usafa.gerarHorariosDisponiveis(dataEscolhida, corCartao);

            if (horarios.size() == 0) {
                System.out.println("Não há horários disponíveis para essa data.");
                System.out.print("Deseja verificar outra data? (S/N): ");
                String resposta = scanner.nextLine();

                if (resposta.equalsIgnoreCase("S")) {
                    continue;
                } else {
                    return;
                }
            }

            usafa.mostrarHorarios(horarios);
            System.out.println("0 - Ver outra data");
            System.out.print("Escolha um horário: ");
            int opcaoHorario;

            try {
                opcaoHorario = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digite um número válido.");
                continue;
            }

            if (opcaoHorario == 0) {
                continue;
            }

            if (opcaoHorario < 1 || opcaoHorario > horarios.size()) {
                System.out.println("Opção inválida.");
                continue;
            }

            String horarioEscolhido = horarios.get(opcaoHorario - 1);

            System.out.println("\nConfirmação do agendamento:");
            System.out.println("CPF: " + cpf);
            System.out.println("Cor do cartão: " + corCartao);
            System.out.println("Data: " + dataEscolhida);
            System.out.println("Horário: " + horarioEscolhido);
            System.out.print("Deseja confirmar? (S/N): ");
            String confirmar = scanner.nextLine();

            if (confirmar.equalsIgnoreCase("S")) {
                boolean agendado = usafa.agendarConsulta(paciente, dataEscolhida, horarioEscolhido);

                if (agendado) {
                    System.out.println("Agendamento realizado com sucesso.");
                } else {
                    System.out.println("Não foi possível realizar o agendamento.");
                }
                return;
            } else {
                System.out.println("Agendamento cancelado.");
                return;
            }
        }
    }

    public static void consultarAgendamento() {
        System.out.print("\nDigite seu CPF: ");
        String cpf = scanner.nextLine();

        ArrayList<Agendamento> meusAgendamentos = usafa.buscarAgendamentosPorCpf(cpf);

        if (meusAgendamentos.size() == 0) {
            System.out.println("Nenhum agendamento encontrado para esse CPF.");
            return;
        }

        System.out.println("\nMeus agendamentos:");
        for (int i = 0; i < meusAgendamentos.size(); i++) {
            System.out.println("------------------------");
            System.out.println("Agendamento " + (i + 1));
            meusAgendamentos.get(i).exibirResumo();
        }
    }

    public static String lerCorCartao() {
        while (true) {
            System.out.println("\nEscolha a cor do cartão:");
            System.out.println("1 - Vermelho");
            System.out.println("2 - Amarelo");
            System.out.println("3 - Verde");
            System.out.println("4 - Rosa");
            System.out.println("5 - Azul");
            System.out.print("Opção: ");

            String opcao = scanner.nextLine();

            if (opcao.equals("1")) return "Vermelho";
            if (opcao.equals("2")) return "Amarelo";
            if (opcao.equals("3")) return "Verde";
            if (opcao.equals("4")) return "Rosa";
            if (opcao.equals("5")) return "Azul";

            System.out.println("Opção inválida.");
        }
    }
}