package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.Comment;
import com.team32.ong.model.News;
import com.team32.ong.model.User;
import com.team32.ong.repository.CommentRepository;
import com.team32.ong.service.CommentService;
import com.team32.ong.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;



    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public AddCommentBody update(Long id, AddCommentBody commentDto) throws Exception {

        logger.info("En el service");
        logger.info("id :".concat(id.toString()));
        if (!existsById(id)){
            logger.error("El comentario no existe");
            throw new NotFoundException(ConstantMessage.MSG_COMMENT_NOT_FOUND.concat(id.toString()));
        }else {
            logger.info("El comentario existe");
        }

        if (commentDto.getBody() == null || commentDto.getBody() == ""){
            logger.error("el cuerpo del comentario esta vacio");
            throw new EmptyInputException(ConstantMessage.NSG_EMPTY_COMMENT_BODY);
        }

        //todo: comprobar usuario
        logger.info("busco el comentario en BD");
        Comment oldComment = findById(id).get();
        logger.info("encontre el comentario");

        Comment newComment = new Comment();
        newComment.setId(oldComment.getId());
        newComment.setBody(oldComment.getBody());
        newComment.setDeleted(false);
        newComment.setUser(oldComment.getUser());
        newComment.setNews(oldComment.getNews());

        commentRepository.save(newComment);
        return commentDto;
    }

    @Override
    public boolean existsById(Long id) throws Exception {
        return commentRepository.existsById(id);
    }

    @Override
    public Optional<Comment> findById(Long id) throws Exception {
        return commentRepository.findById(id);
    }


    public CommentDto modelToDto(Comment comment) {

        ModelMapper mapper = new ModelMapper();

        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        return commentDto;

    }



    public Comment dtoToModel(CommentDto commentDto) {

        ModelMapper mapper = new ModelMapper();

        Comment comment = mapper.map(commentDto, Comment.class);

        return comment;

    }


}
