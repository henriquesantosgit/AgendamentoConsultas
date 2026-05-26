import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Usafa {
    private ArrayList<Agendamento> agendamentos;
    private DateTimeFormatter formatador;

    public Usafa() {
        agendamentos = new ArrayList<>();
        formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public ArrayList<LocalDate> gerarDatasDisponiveis() {
        ArrayList<LocalDate> datas = new ArrayList<>();
        LocalDate data = LocalDate.of(2026, 6, 1);

        while (datas.size() < 10) {
            if (ehDiaUtil(data) && !ehFeriado(data)) {
                datas.add(data);
            }
            data = data.plusDays(1);
        }

        return datas;
    }

    public ArrayList<String> gerarHorariosDisponiveis(LocalDate data, String corCartao) {
        ArrayList<String> horariosBase = new ArrayList<>();
        ArrayList<String> horariosLivres = new ArrayList<>();

        horariosBase.add("07:30");
        horariosBase.add("08:30");
        horariosBase.add("09:30");
        horariosBase.add("10:30");
        horariosBase.add("11:30");
        horariosBase.add("13:30");
        horariosBase.add("14:30");
        horariosBase.add("15:30");

        for (String horario : horariosBase) {
            if (!existeAgendamentoMesmoHorarioECor(data, horario, corCartao)) {
                horariosLivres.add(horario);
            }
        }

        return horariosLivres;
    }

    public boolean agendarConsulta(Paciente paciente, LocalDate data, String horario) {
        if (pacienteJaTemConsultaNoDia(paciente.getCpf(), data)) {
            return false;
        }

        if (existeAgendamentoMesmoHorarioECor(data, horario, paciente.getCorCartao())) {
            return false;
        }

        Agendamento novo = new Agendamento(paciente, data, horario);
        agendamentos.add(novo);
        return true;
    }

    public ArrayList<Agendamento> buscarAgendamentosPorCpf(String cpf) {
        ArrayList<Agendamento> resultado = new ArrayList<>();

        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getPaciente().getCpf().equals(cpf)) {
                resultado.add(agendamento);
            }
        }

        return resultado;
    }

    public boolean pacienteJaTemConsultaNoDia(String cpf, LocalDate data) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getPaciente().getCpf().equals(cpf)
                    && agendamento.getDataConsulta().equals(data)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeAgendamentoMesmoHorarioECor(LocalDate data, String horario, String corCartao) {
        for (Agendamento agendamento : agendamentos) {
            boolean mesmaData = agendamento.getDataConsulta().equals(data);
            boolean mesmoHorario = agendamento.getHorarioConsulta().equals(horario);
            boolean mesmaCor = agendamento.getPaciente().getCorCartao().equalsIgnoreCase(corCartao);

            if (mesmaData && mesmoHorario && mesmaCor) {
                return true;
            }
        }
        return false;
    }

    public boolean ehDiaUtil(LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        return dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY;
    }

    public boolean ehFeriado(LocalDate data) {
        if (data.equals(LocalDate.of(2026, 6, 4))) {
            return true;
        }
        return false;
    }

    public void mostrarDatas(ArrayList<LocalDate> datas) {
        System.out.println("\nDatas disponíveis:");
        for (int i = 0; i < datas.size(); i++) {
            System.out.println((i + 1) + " - " + datas.get(i).format(formatador));
        }
    }

    public void mostrarHorarios(ArrayList<String> horarios) {
        System.out.println("\nHorários disponíveis:");
        for (int i = 0; i < horarios.size(); i++) {
            System.out.println((i + 1) + " - " + horarios.get(i));
        }
    }
}