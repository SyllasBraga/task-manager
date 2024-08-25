package br.com.task_manager.mapper;

import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.api.request.TaskRequest;
import br.com.task_manager.api.response.SubtaskResponse;
import br.com.task_manager.api.response.TaskResponse;
import br.com.task_manager.model.SubtaskModel;
import br.com.task_manager.model.TaskModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T11:33:09-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Azul Systems, Inc.)"
)
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskModel taskRequestToTaskModel(TaskRequest taskRequest) {
        if ( taskRequest == null ) {
            return null;
        }

        TaskModel taskModel = new TaskModel();

        taskModel.setTitle( taskRequest.getTitle() );
        taskModel.setDescription( taskRequest.getDescription() );
        taskModel.setStatus( taskRequest.getStatus() );
        taskModel.setSubtasks( subtaskRequestListToSubtaskModelList( taskRequest.getSubtasks() ) );

        return taskModel;
    }

    @Override
    public TaskResponse taskModelToTaskResponse(TaskModel taskModel) {
        if ( taskModel == null ) {
            return null;
        }

        TaskResponse.TaskResponseBuilder taskResponse = TaskResponse.builder();

        taskResponse.id( taskModel.getId() );
        taskResponse.title( taskModel.getTitle() );
        taskResponse.description( taskModel.getDescription() );
        taskResponse.status( taskModel.getStatus() );
        taskResponse.createdDate( taskModel.getCreatedDate() );
        taskResponse.endDate( taskModel.getEndDate() );
        taskResponse.subtasks( subtaskModelListToSubtaskResponseList( taskModel.getSubtasks() ) );

        return taskResponse.build();
    }

    protected SubtaskModel subtaskRequestToSubtaskModel(SubtaskRequest subtaskRequest) {
        if ( subtaskRequest == null ) {
            return null;
        }

        SubtaskModel subtaskModel = new SubtaskModel();

        subtaskModel.setTitle( subtaskRequest.getTitle() );
        subtaskModel.setDescription( subtaskRequest.getDescription() );
        subtaskModel.setStatus( subtaskRequest.getStatus() );
        subtaskModel.setPriority( subtaskRequest.getPriority() );

        return subtaskModel;
    }

    protected List<SubtaskModel> subtaskRequestListToSubtaskModelList(List<SubtaskRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<SubtaskModel> list1 = new ArrayList<SubtaskModel>( list.size() );
        for ( SubtaskRequest subtaskRequest : list ) {
            list1.add( subtaskRequestToSubtaskModel( subtaskRequest ) );
        }

        return list1;
    }

    protected SubtaskResponse subtaskModelToSubtaskResponse(SubtaskModel subtaskModel) {
        if ( subtaskModel == null ) {
            return null;
        }

        SubtaskResponse.SubtaskResponseBuilder subtaskResponse = SubtaskResponse.builder();

        subtaskResponse.id( subtaskModel.getId() );
        subtaskResponse.title( subtaskModel.getTitle() );
        subtaskResponse.description( subtaskModel.getDescription() );
        subtaskResponse.status( subtaskModel.getStatus() );
        subtaskResponse.createdDate( subtaskModel.getCreatedDate() );
        subtaskResponse.endDate( subtaskModel.getEndDate() );
        subtaskResponse.priority( subtaskModel.getPriority() );

        return subtaskResponse.build();
    }

    protected List<SubtaskResponse> subtaskModelListToSubtaskResponseList(List<SubtaskModel> list) {
        if ( list == null ) {
            return null;
        }

        List<SubtaskResponse> list1 = new ArrayList<SubtaskResponse>( list.size() );
        for ( SubtaskModel subtaskModel : list ) {
            list1.add( subtaskModelToSubtaskResponse( subtaskModel ) );
        }

        return list1;
    }
}
