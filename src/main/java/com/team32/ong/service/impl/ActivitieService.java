package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.ActivitiesDTO;
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
                throw new EmptyInputException(ConstantMessage.MSG_NAME_BAD_REQUEST);
            }
            if (activitiesDTO.getContent() == null || activitiesDTO.getContent() == ""){
                throw new EmptyInputException(ConstantMessage.MSG_EMPTY_ACTIVITY);
            }

            //todo: capturar imagen
            activitiesDTO.setImage("default.jpg");

            activitiesRepository.save(dtoToModel(activitiesDTO));
            return activitiesDTO;

        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
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
