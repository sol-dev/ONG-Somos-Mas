package com.team32.ong.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team32.ong.service.SlideService;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {

}

/*
 * Description COMO usuario administrador QUIERO ver el listados de slides PARA
 * ver el contenido de forma gráfica.
 * 
 * Criterios de aceptación:
 * 
 * Se debe hacer una petición GET a /slides.
 * 
 * Devolverá el listado de slides, con la imagen y el orden del mismo
 */