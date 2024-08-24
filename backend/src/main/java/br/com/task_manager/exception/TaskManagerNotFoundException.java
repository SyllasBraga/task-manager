package br.com.task_manager.exception;

public class TaskManagerNotFoundException extends RuntimeException{
    
    public TaskManagerNotFoundException(String msg){
        super(msg);
    }
}
