package com.team4.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.team4.myapp.board.model.Board;
import com.team4.myapp.board.model.BoardUploadFile;

public interface IBoardRepository {
	int selectMaxArticleNo();
	int selectMaxFileId();
	
	void insertArticle(Board board);
	void insertFileData(Board board);
	
	List<Board> selectArticleListByCategory(@Param("boardType") String boardType, @Param("start") int start, @Param("end") int end);
	
	Board selectArticle(int boardId);
	BoardUploadFile getFile(int fileId);
		
	void updateReadCount(int boardId);
	void updateDownloadCount(int boardId);
	void updateHeartCount(int boardId);

	void updateReplyNumber(@Param("masterId") int masterId, @Param("replyNumber") int replyNumber);
	void replyArticle(Board boardId);
	
	void updateArticle(Board board);
	void updateFileData(Board board);
	
	void deleteFileData(int boardId);
	void deleteReplyFileData(int boardId);
	Board selectDeleteArticle(int boardId);
	void deleteArticleByBoardId(int boardId);
	void deleteArticleByMasterId(int boardId);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByCategoryId(String boardType);

	int selectTotalArticleCountByKeyword(@Param("keyword") String keyword, @Param("boardType") String boardType);
	List<Board> searchListByContentKeyword(@Param("keyword") String keyword, @Param("boardType") String boardType, @Param("start") int start, @Param("end") int end);
}
