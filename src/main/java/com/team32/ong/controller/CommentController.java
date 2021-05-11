package com.team32.ong.controller;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/news/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AddCommentBody commentDto)
                                                  throws Exception {

        Map<String, Object> response = new HashMap<>();
        AddCommentBody commentDtoRes = null;


        commentDtoRes = commentService.update(id,commentDto);

        response.put("message", "Comentrio Actualizado");
        response.put("comment", commentDtoRes);

        return new ResponseEntity(response, HttpStatus.OK);
    }


}
