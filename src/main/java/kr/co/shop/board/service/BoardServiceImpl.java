package kr.co.shop.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.shop.board.dao.BoardDAO;
import kr.co.shop.board.dto.ArticleDTO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public List<ArticleDTO> listArticles() throws Exception {
		List<ArticleDTO> articlesList = boardDAO.selectAllArticleList();
		return articlesList;
	}
}
