package kr.co.shop.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import kr.co.shop.board.dto.ArticleDTO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List selectAllArticleList() throws DataAccessException {
		List<ArticleDTO> articlesList = sqlSession.selectList("mapper.board.selectAllArticlesList");
		return articlesList;
	}

	@Override
	public int insertNewArticle(Map<String, Object> articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle", articleMap);
		return articleNO;
	}

	private int selectNewArticleNO() {
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}

}
