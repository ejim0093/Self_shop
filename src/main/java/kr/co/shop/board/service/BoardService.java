package kr.co.shop.board.service;

import java.util.List;
import kr.co.shop.board.dto.ArticleDTO;

public interface BoardService {

	List<ArticleDTO> listArticles() throws Exception ;

}
