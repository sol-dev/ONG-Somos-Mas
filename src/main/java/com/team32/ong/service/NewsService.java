package com.team32.ong.service;

import com.team32.ong.dto.NewsDTO;
import com.team32.ong.model.News;

public interface NewsService {
	
	News save(NewsDTO newsDto);

}
