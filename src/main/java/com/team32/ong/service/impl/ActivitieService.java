package com.team32.ong.service.impl;

import com.amazonaws.services.mq.model.NotFoundException;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.ActivitiesDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.Activities;
import com.team32.ong.repository.ActivitiesRepository;
import com.team32.ong.service.IActivitiesServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ActivitieService implements IActivitiesServices {

    @Autowired
    private ActivitiesRepository activitiesRepository;

    @Override
    @Transactional
    public ActivitiesDTO save(ActivitiesDTO activitiesDTO , MultipartFile image) throws Exception {
        try {
            if (activitiesDTO.getName() == null || activitiesDTO.getName() == "") {
                throw new EmptyInputException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
            }
            if (activitiesDTO.getContent() == null || activitiesDTO.getContent() == ""){

                throw new EmptyInputException(ConstantExceptionMessage.MSG_EMPTY_ACTIVITY);
            }

            //todo: capturar imagen
            activitiesDTO.setImage("default.jpg");

            activitiesRepository.save(dtoToModel(activitiesDTO));
            return activitiesDTO;

        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }

    @Override
    public ActivitiesDTO update(Long idActivities, ActivitiesDTO activitiesDTO) {

        if (!activitiesRepository.existsById(idActivities)){
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUD_ACTIVITY);
        }else if (activitiesDTO.getContent().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
        }else if (activitiesDTO.getName().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        Activities activities = dtoToModel(activitiesDTO);
        activities.setId(idActivities);

        return modelToDTO(activitiesRepository.save(activities));
    }

    private ActivitiesDTO modelToDTO(Activities activities){
        ModelMapper modelMapper = new ModelMapper();
        ActivitiesDTO map = modelMapper.map(activities, ActivitiesDTO.class);
        return map;
    }

    private Activities dtoToModel(ActivitiesDTO activitiesDTO){
        ModelMapper modelMapper = new ModelMapper();
        Activities map = modelMapper.map(activitiesDTO, Activities.class);
        return map;
    }

 
}
