<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="jk" tagdir="/WEB-INF/tags"%>
<%@ include file="/WEB-INF/views/fragment/nav.jsp"%>
<%@ include file="/WEB-INF/views/fragment/head.jsp"%>
<style>
#detail{
color: grey;
}
#detail:hover {
  color: orange;
}
</style>

<div class="content" style="border-bottom: 1px gray solid;">

	<div class="container">
		<div style="border-top: 1px solid gray;">
			<h2 class="mb-5 mt-3"><c:if test="${boardType=='reference'}">자료실</c:if>
   		<c:if test="${boardType=='community'}">커뮤니티</c:if></h2>
		</div>
		<div class="table-responsive" style="border-top: 1px solid gray;"><br/>
			<form action="<c:url value='/board/search/${boardType}/1'/>" method="get">
				<div class="pull-right" style="margin-bottom: 5px;">
					<div class="row">
						<div class="col-md-8">
				        <input type="text" name="keyword" class="form-control" placeholder="Search...">
				        </div>
				        <div class="col-md-3">				        
				        <input type="submit" class="btn btn-warning" value="<fmt:message key="SEARCH"/>">
				        </div>
				    </div>
				</div>
			</form>
			<h5>" ${keyword} " 검색 결과 </h5>
			<table class="table table-striped custom-table">
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">작성 일자</th>
						<th scope="col">조회수</th>
						<th scope="col">
							<c:if test="${boardType=='reference'}">다운로드 수</c:if>
							<c:if test="${boardType=='community'}">좋아요 수</c:if>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var ="board" items="${boardList}">					
						<tr>
							<td class="column1">${board.boardId}</td>
							<c:url var="viewLink" value="/board/detail/${board.boardId}"/>
							<td class="column2"><a href='${viewLink}' id="detail">${board.title}</a></td>
							<td class="column3">${board.memberId}</td>
							<td class="column4"><fmt:formatDate value="${board.boardDate}" pattern="YYYY-MM-dd"/></td>
							<td class="column5">${board.viewCount}</td>
							<td class="column6"><c:if test="${boardType=='reference'}">${board.fileDownloadCount}</c:if></td>
							<td class="column6"><c:if test="${boardType=='community'}">${board.heartCount}</c:if></td>
						</tr>
					</c:forEach>									
				</tbody>
			</table>
			<table class="table">
		<tr>
			<td align="left">page</td>
			<td align="left">
			<c:if test="${totalPageCount==0}">검색 결과가 없습니다. </c:if>
				<jk:paging boardType="${boardType}" totalPageCount="${totalPageCount}" nowPage="${page}"/>
			</td>
			<td align="right">
				<a href='<c:url value="/board/write"/>'><button type="button" class="btn btn-warning"><fmt:message key="WRITE_NEW_ARTICLE"/></button> </a>
			</td>
		</tr>
		</table>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/fragment/footer.jsp"%>