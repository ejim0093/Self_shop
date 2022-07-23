package kr.co.shop.board.service;

import java.util.List;
import java.util.Map;

import kr.co.shop.board.dto.ArticleDTO;

public interface BoardService {

	List<ArticleDTO> listArticles() throws Exception ;

	public int addNewArticle(Map<String, Object> articleMap) throws Exception;
	public ArticleDTO viewArtivle(int articleNO) throws Exception;
	public void modArticle(Map<String, Object> articleMap) throws Exception;

}
