import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Agendamento {
    private Paciente paciente;
    private LocalDate dataConsulta;
    private String horarioConsulta;

    public Agendamento(Paciente paciente, LocalDate dataConsulta, String horarioConsulta) {
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
        this.horarioConsulta = horarioConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public String getHorarioConsulta() {
        return horarioConsulta;
    }

    public void exibirResumo() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("CPF: " + paciente.getCpf());
        System.out.println("Cor do cartão: " + paciente.getCorCartao());
        System.out.println("Data: " + dataConsulta.format(formatador));
        System.out.println("Horário: " + horarioConsulta);
    }
}