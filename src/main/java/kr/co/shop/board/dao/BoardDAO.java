package kr.co.shop.board.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

public interface BoardDAO {

	public List selectAllArticleList() throws DataAccessException;

}
