package br.com.task_manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.task_manager.api.request.SubtaskRequest;
import br.com.task_manager.api.response.SubtaskResponse;
import br.com.task_manager.model.SubtaskModel;

@Mapper
public interface SubtaskMapper {

    SubtaskMapper INSTANCE = Mappers.getMapper(SubtaskMapper.class);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "id", ignore = true)
    SubtaskModel subtaskRequestToSubtaskModel(SubtaskRequest subtaskRequest);

    SubtaskResponse subtaskModelToSubtaskResponse(SubtaskModel subtaskModel);
}
