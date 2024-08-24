package br.com.task_manager.exception.enums;

public enum ExceptionsEnum {
    CONSTRAINT_VIOLATION("E-mail já cadastrado."),
    NOT_FOUND("Usuário não encontrado.");

    private final String msg;

    ExceptionsEnum(String msg){
        this.msg = msg;
    }

    public String getMsg() {
      return this.msg;
    }
}
