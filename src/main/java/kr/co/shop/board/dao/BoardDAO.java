package kr.co.shop.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BoardDAO {

	public List selectAllArticleList() throws DataAccessException;

	public int insertNewArticle(Map<String, Object> articleMap) throws DataAccessException;

}
