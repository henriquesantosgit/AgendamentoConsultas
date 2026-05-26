public class Paciente {
    private String cpf;
    private String corCartao;

    public Paciente(String cpf, String corCartao) {
        this.cpf = cpf;
        this.corCartao = corCartao;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCorCartao() {
        return corCartao;
    }
}