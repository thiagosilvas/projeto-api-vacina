package br.edu.unime.paciente.apiPaciente.validation;

public class ValidationErrorResponse {
    private String mensagem;

    public ValidationErrorResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}