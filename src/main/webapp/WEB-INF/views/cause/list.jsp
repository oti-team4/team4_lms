<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/fragment/nav.jsp"%>
<%@ include file="/WEB-INF/views/fragment/head.jsp"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<div class="content" style="border-bottom: 1px gray solid;">

	<div class="container" style="background-color: white">
		<div style="border-top: 1px solid gray;">
			<h2 class="mb-5 mt-3">사유 리스트</h2>
		</div>
		<div class="table-responsive">
			<table class="table">
				<tr>
					<td align="left">
						<tag:paging totalPageCount="${totalPageCount}" nowPage="${page}"/>
					</td>
					<td align="right">
						<a href='<c:url value="/cause/wrtie"/>'>
							<button type="button" class="btn btn-info">
							</button>
						</a>
					</td>
					<td>
						<form method="get">
							<input type="submit" value="달력" />
						</form>
						
					</td>
				</tr> 
			</table>
			<table class="table table-striped custom-table">
				<thead>
					<tr>
						<th scope="col">신청일</th>
						<th scope="col">번호</th>
						<th scope="col">내용</th>
						<th scope="col">사유</th>
						<th scope="col">처리 상태</th>
					</tr>
				</thead>
				<tbody>		
					<c:forEach items="${causeList}" var="causeListDto">
						<tr>
							<td class="column1">${causeListDto.writeDate}</td>
							<td class="column2">${causeListDto.causeId}</td>	
							<td class="column3">${causeListDto.content}</td>
							<td class="column4">${causeListDto.attendanceStatusString}</td>
							<td class="column5">${causeListDto.causeStatus}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/fragment/footer.jsp"%>