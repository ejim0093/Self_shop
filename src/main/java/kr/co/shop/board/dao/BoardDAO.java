package kr.co.shop.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import kr.co.shop.board.dto.ArticleDTO;

public interface BoardDAO {

	public List selectAllArticleList() throws DataAccessException;

	public int insertNewArticle(Map<String, Object> articleMap) throws DataAccessException;
	public ArticleDTO viewArtivle(int articleNO) throws DataAccessException;
	public void modArticle(Map<String, Object> articleMap) throws DataAccessException;

}
