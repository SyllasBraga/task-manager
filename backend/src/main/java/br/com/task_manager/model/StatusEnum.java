package br.com.task_manager.model;

public enum StatusEnum {
    DONE("Concluída"), TODO("Para fazer"), CANCELED("Cancelada");
    
    private String status;

    StatusEnum(String status){
        this.status = status;
    }

    public String getStatus() {
      return this.status;
    }
}
