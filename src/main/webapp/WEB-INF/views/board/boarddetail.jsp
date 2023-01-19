<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/fragment/head.jsp"%>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value='/cal/js/jquery-3.3.1.min.js'/>"></script>
<script >
	function writeReply(boardId) {
		var content1 = $("#replyContent").val();
		$.ajax({
			type : 'POST',	 // get방식으로 통신
			url : "/reply/write",
			data : {boardId: boardId, content:content1},
			error : function() { // 통신 실패시
				console.log('통신실패!');
			},
			success : function(data) { // 통신 성공시 탭 내용담는 div를 읽어들인 값으로 채운다.
				 location.href = "/board/detail/"+boardId;
			}
		});
	}
	function deleteReply(boardId, replyId) {
		console.log(replyId);
		$.ajax({
			type : 'POST',	 // get방식으로 통신
			url : "/reply/delete",
			data : {boardId: boardId, replyId:replyId},
			error : function() { // 통신 실패시
				console.log('통신실패!');
			},
			success : function(data) { // 통신 성공시 탭 내용담는 div를 읽어들인 값으로 채운다.
				 location.href = "/board/detail/"+boardId;
			}
		});
	}

	function copy() {
		navigator.clipboard.writeText(window.location.href);
		alert("클립 보드에 복사되었습니다.");
	}
</script>
<style>
		* {
		  box-sizing: border-box;
		  outline: none;
		}
		body {
		  margin: 0;
		  font-family: Sans-serif;
		 background-color: #efefef;
		}
		.dashboard {
		  display: flex;
		}
		.left {
		  height: 100%;
		  display: flex;
		}
		.sidebar {
		  width: 80px;
		  height: 100%;
		  background-image: linear-gradient(
		    -225deg,
		    #5271c4 0%,
		    #b19fff 48%,
		    #eca1fe 100%
		  );
		  display: flex;
		}
		.wrapper {
		  padding: 0 25px;
		  height: 100vh;
		  display: flex;
		  flex-direction: column;
		}
		
		.items {
		  margin-top: 50px;
		}
		
		.items > img {
		  margin-bottom: 25px;
		}
		
		.profile > img {
		  border-radius: 50%;
		  width: 305x;
		  height: 35px;
		  border: 1px solid white;
		}
		.profile {
		  margin-top: auto;
		}
		.menu {
		  margin-top: 20px;
		}
		.navigation {
		  width: 270px;
		  border-right: 1px solid #ddd;
		}
		.abilan > img {
		  width: 120px;
		  margin-bottom: 30px;
		  margin-left: -8px;
		}
		
		.wrapper2 {
		  padding: 0 25px;
		  height: 100%;
		  overflow: auto;
		}
		
		.compose {
		  width: 150px;
		  text-align: center;
		  height: 50px;
		  border-radius: 13px;
		  border: 1px solid;
		  background-image: linear-gradient(120deg, #f093fb 0%, #f5576c 100%);
		  color: white;
		  font-size: 16px;
		  padding-left: 20px;
		  position: relative;
		}
		.folders {
		  margin-top: 30px;
		  color: #b8b8b8;
		  font-size: 14px;
		}
		.folder-icons {
		  margin-top: 20px;
		  display: flex;
		  align-items: center;
		}
		
		.icon-name1 {
		  margin-left: 10px;
		  color: #8b47a9;
		}
		
		.icon-name {
		  margin-left: 10px;
		  color: grey;
		}
		.names {
		  margin-left: 10px;
		  font-weight: bold;
		  font-size: 15px;
		}
		
		.avatar > img {
		  width: 35px;
		  border-radius: 10px;
		}
		.section1 {
		  margin-top: 25px;
		  margin-bottom: 10px;
		}
		
		.btn {
		  border-radius: 3px;
		  border: 1px;
		  font-weight: bold;
		  position: relative;
		}
		
		.buton1 {
		  background-color: #f8f1ff;
		  color: #a84bfe;
		}
		.buton2 {
		  background-color: #fff1f6;
		  color: #fe4b85;
		}
		.buton3 {
		  background-color: #eef0ff;
		  color: #2632fe;
		}
		.buton4 {
		  background-color: #ebfbf8;
		  color: #01c991;
		}
		.tag {
		  position: absolute;
		  left: 8px;
		}
		.plus > img {
		  width: 16px;
		  height: 16px;
		}
		.plus {
		  left: 10px;
		  position: absolute;
		}
		.buton-span {
		  border-radius: 15px;
		  padding: 5px 10px;
		  border: 1px;
		  color: white;
		  background-color: #fe365c;
		  box-shadow: 0px 0px 10px 2px #f7bfc9;
		  margin-left: 70px;
		}
		.online {
		  position: absolute;
		  top: -5px;
		  right: -5px;
		  border: 4px solid #fbfcf8;
		  box-sizing: unset;
		  background-color: #02c997;
		  width: 10px;
		  height: 10px;
		  border-radius: 50%;
		}
		.avatar {
		  position: relative;
		}
		.red {
		  background-color: #fe4663;
		}
		
		.big-inbox {
		  font-size: 25px;
		  font-weight: 500;
		}
		.right-side {
		  background-color: #f2f3f7;
		  width: 100%;
		  padding: 8px 30px;
		  display: flex;
		  flex-direction: column;
		}
		.right-body {
		  flex: 1;
		  display: flex;
		  
		}
		.top-bar {
		  display: flex;
		  align-items: center;
		  text-align: center;
		}
		.top-bar-justify {
		  display: flex;
		  justify-content: space-between;
		  width: 100%;
		}
		.top-bar-items {
		  align-items: center;
		  display: flex;
		  justify-content: space-between;
		  width: 180px;
		}
		.profile2 > img {
		  border-radius: 50%;
		  width: 28x;
		  height: 28px;
		  border: 2px solid white;
		  margin-left: 25px;
		  margin-right: 5px;
		}
		.profile2 {
		  display: flex;
		  align-items: center;
		
		  width: 120px;
		}
		.icon-name5 {
		  font-size: 13px;
		  color: grey;
		}
		.new-hr {
		  border: 0.6px solid #ddd;
		  margin-bottom: 25px;
		}
		.notif {
		  position: relative;
		}
		.pink {
		  background-color: #fe96db;
		  width: 7px;
		  height: 7px;
		  margin: -3px;
		}
		
		.checkbox > input {
		  width: 20px;
		  height: 20px;
		}
		
		.right-bottom {
		  display: flex;
		  align-items: center;
		  justify-content: space-between;
		}
		
		.down-arrow > img {
		  width: 12px;
		  height: 14px;
		  margin-bottom: -2px;
		  margin-left: 5px;
		}
		.new:hover {
		  background-color: white;
		}
		.new {
		  border-radius: 4px;
		  border: none;

		  background-color: #edeef5;
		}
		.check {
		  display: flex;
		  align-items: center;
		}
		.has-search .text {
		  padding-left: 30px;
		  margin-left: 45px;
		}
		.form {
		  display: flex;
		  align-items: center;
		}
		.searchIcon {
		  margin-left: 53px;
		  position: absolute;
		  margin-top: 2px;
		}
		.text {
		  border: 1px solid #ddd;
		  border-radius: 4px;
		  width: 280px;
		  height: 30px;
		  background-color: #edeef5;
		}
		.buttons {
		  display: flex;
		}
		
		.button {
		    padding: 0.65rem 1.9rem;
		}
		
		.middle-buttons {
		  display: flex;
		}
		
		.scroll-cards {
		  width: 370px;
		  height: 100%;
		  overflow: auto;
		
		  padding: 20px 0px 5px 0px;
		}
		.card {
		  background-color: white;
		  border-radius: 4px;
		  margin-top: 8px;
		  margin-bottom: 5px;
		  padding: 25px 25px 15px 25px;
		  transition: 0.3s;
		}
		.card:hover {
		  box-shadow: 5px 1px 20px 1px #ddd;
		  transform: scale(0.96);
		}
		
		.mail-names {
		  color: grey;
		  font-weight: bold;
		  font-size: 15px;
		  margin-left: 10px;
		}
		
		.mails {
		  display: flex;
		  align-items: center;
		}
		.mails > img {
		  width: 35px;
		  border-radius: 10px;
		}
		.mail-info {
		  margin: 10px 65px;
		  margin-left: 0px;
		  line-height: 1.7;
		  font-weight: 600;
		}
		.check1 {
		  display: flex;
		  align-items: center;
		  justify-content: space-between;
		  width: 100px;
		}
		.bottom-info {
		  display: flex;
		  justify-content: space-between;
		}
		.date {
		  color: grey;
		}
		.person {
		  width: 25px;
		  height: 25px;
		  border-radius: 10px;
		  text-align: center;
		  color: white;
		  padding: 5px 3px 0px;
		}
		.border1 {
		  background-color: #5f4bfd;
		}
		.border2 {
		  background-color: #e32553;
		}
		.border3 {
		  background-color: #01c828;
		}
		.message {
		  margin: 20px 10px;
		  flex: 1;
		  background-color: white;
		  padding: 25px;
		 
		}
		.mes-date {
		  color: grey;
		  font-size: 14px;
		}
		.who {
		  font-weight: 600;
		}
		.title {
		  font-size: 18px;
		  font-weight: bold;
		  margin-bottom: 10px;
		  margin-top: 10px;
		}
		
		.message-from {
		  margin-top: 20px;
		  color: grey;
		  font-size: 17px;
		}
		.attachment-last {
		  display: flex;
		  align-items: center;
		  justify-content: space-between;
		  width: 350px;
		}
		.att-write {
		  color: grey;
		}
		.buton0 {
		  background-color: #ecefff;
		  color: #7175c0;
		}
		.buton9 {
		  background-color: #e0f8e3;
		  color: #79b992;
		}
		
		.btn1 {
		  border-radius: 3px;
		  border: 1px;
		  font-weight: bold;
		}
		.inside-img > img, .dropdown > img {
		  width: 100px;
		  border-radius: 10px;
		  margin-top: 20px;
		}
		.inside-img > img:hover {
		  transform: scale(0.95);
		}
		.son-buton {
		  width: 100px;
		  height: 68px;
		  border-radius: 10px;
		  border: 1px;
		  margin-top: 17px;
		  background-color: #f5e9f9;
		  color: #b79ed8;
		  font-weight: 500;
		  font-size: 20px;
		}
		.son-images {
		  display: flex;
		  align-items: center;
		  justify-content: space-between;
		  width: 520px;
		}
		.btn2 {
		  margin-top: 40px;
		  border-radius: 3px;
		  border: 1px;
		  height: 35px;
		  padding: 5px 30px;
		  color: white;
		  position: relative;
		}
		.butona {
		  background-color: #8e44ad;
		}
		.butonb {
		  background-color: #fe4a85;
		}
		.butona:hover {
		  background-color: #892bb4;
		}
		.butonb:hover {
		  background-color: #ec195f;
		}
		::-webkit-scrollbar {
		  width: 10px;
		}
		
		/* Track */
		::-webkit-scrollbar-track {
		  background: #f1f1f1;
		}
		
		/* Handle */
		::-webkit-scrollbar-thumb {
		  background: #888;
		}
		
		/* Handle on hover */
		::-webkit-scrollbar-thumb:hover {
		  background: #555;
		}
		
		#thumb:hover, #share:hover {
		color: blue;
		}
		
		.dropdown {
		  position: relative;
		  display: inline-block;
		}
		
		.dropdown-content {
		  display: none;
		  position: absolute;
		  background-color: #f9f9f9;
		  min-width: 160px;
		  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
		  z-index: 1;
		}
		
		.dropdown:hover .dropdown-content {
		  display: block;
		}
		
		.desc {
		  padding: 15px;
		  text-align: center;
		}
				
	</style>
	<%@ include file="/WEB-INF/views/fragment/nav.jsp" %>
	
	<div class="container">

	<div class="dashboard">
		<div class="right-side">
			<br /> <br />
			<div class="right-header">
				<br /> <br />
				<hr class="new-hr">
				<div class="right-bottom">
					<div class="check">
						<h1>
							<c:if test="${board.boardType eq 'reference'}">
								<fmt:message key="REFERENCE" />
							</c:if>
							<c:if test="${board.boardType eq 'community'}">
								<fmt:message key="COMMUNITY" />
							</c:if>
						</h1>
					</div>
					<div class="search-arrow">
						<div class="buttons">
							<button class="new button" onclick="location.reload()">
								<img src="https://i.ibb.co/X4j3TZR/reload.png" />
							</button>
							<c:if test="${sessionScope.memberid eq board.memberId}">
								<button class="new button"
									onclick="location.href='/board/update/${board.boardId}'">
									<i class="fa-solid fa-pen" style="color: #A4A4A4"></i>
								</button>
							</c:if>
							<c:if test="${sessionScope.memberid eq board.memberId}">
								<button class="new button" data-toggle="modal"
									data-target="#deleteModal">
									<img src="https://i.ibb.co/Lv6TqBG/waste-bin.png" />
								</button>
							</c:if>
							<div class="modal fade" id="deleteModal" aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											삭제 확인
											<button class="close" type="button" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">해당 게시물을 삭제하시겠습니까?</div>
										<div class="modal-footer">
											<form action='<c:url value="/board/delete"/>' class="form-inline" method="post">
												<input type="hidden" name="boardId" value="${board.boardId}">
												<input type="hidden" name="boardType" value="${board.boardType}"> 
												<button type="submit" class="btn btn-danger">삭제</button>&ensp;
												<button type="button" class="btn btn-success"
													style="background-color: grey" data-dismiss="modal"
													aria-label="Close">취소</button>
											</form>
										</div>
									</div>
								</div>

							</div>
	    				</div> 
	    			</div>
	    		</div>
	    	</div>
	    <div class="right-body">
			<div class="message">
				<div class="mes-date row">
					<div class="col">
						<fmt:setLocale value="en_us" scope="session" />
						<fmt:formatDate value="${board.boardDate}" dateStyle="full" />
					</div>
					<div class="text-right col"><b> No. ${board.boardId}</b></div>

				</div>
				<div class="title">
					제목: ${board.title}
					<div class="title-icons"></div>
				</div>
				<div class="from">
					<span class="who">작성자: </span>${board.memberId}
				</div>
					<div class="row">
					<div class="col">
						<c:if test="${board.boardType=='community'}">좋아요 
			        		<i class="fa fa-thumbs-up" aria-hidden="true" id="thumb" onclick="location.href='/board/like/${board.boardId}'"></i> 
			        		${board.heartCount}&emsp;
			        	</c:if>
			        	공유	        	
			        	<i class="fa fa-share-alt" aria-hidden="true" id="share" onclick="copy()"></i> 	
			        </div>	        			        
			        <div class="col text-right">
			          <span>조회수 </span><i class="fa fa-search-plus" aria-hidden="true"></i> ${board.viewCount}
			          <c:if test="${board.boardType=='reference'}"><span> 다운로드 수 </span>
			          	<i class="fa fa-download" aria-hidden="true"></i> ${board.fileDownloadCount}
			          </c:if>
			        </div>
		        </div>	 
		        <div class="message-from" style="border-top: 1px solid gray;"><br/>
					<p style="color: black;">${board.content}</p>
				</div>	        
		        <div class="attachment-last">
		          <img src="https://i.ibb.co/FW9tsHK/attachment.png" />
		          <div class="att-write">
		            	첨부 파일 (80MB)
		          </div>
		          <button class="btn1 buton0" data-toggle="modal" data-target="#viewModal"> View <span class="tag"></span>
		          </button>
		          <c:if test="${board.boardType=='reference'}">
		          <button class="btn1 buton9" onclick="location.href='<c:url value="/board/download/${board.boardId}/cnt"/>'"> Download
		          </button>   
		          </c:if>       
		        </div>
		        <div class="son-images">
			          <div class="inside-img">
			            <c:if test="${!empty board.fileName}">
							<tr>
								<td>
									<c:set var="len" value="${fn:length(board.fileName)}"/>
									<c:set var="filetype" value="${fn:toUpperCase(fn:substring(board.fileName, len-4, len))}"/>					
									<c:if test="${(filetype eq '.JPG') or (filetype eq '.JPEG') or (filetype eq '.PNG') or (filetype eq '.GIF')}">													
										<div class="dropdown">	
											<img src='<c:url value="/board/download/${board.boardId}"/>' class="img-thumbnail"><br>
										  	<div class="dropdown-content">
										  		<img src='<c:url value="/board/download/${board.boardId}"/>'  width="400px">								 		 
										 		 <div class="desc">${board.fileName}</div>
										 	</div>
										</div>
										<div class="modal fade" id="viewModal">
											<div class="modal-dialog modal-lg">
												<div class="modal-content">
													<img src='<c:url value="/board/download/${board.boardId}"/>' style="width:800px">
												</div>
											</div>
										</div>
									</c:if> <br/>								
									<a <c:if test="${board.boardType=='reference'}">href='<c:url value="/board/download/${board.boardId}/cnt"/>'</c:if>>${board.fileName} (<fmt:formatNumber>${board.fileSize}</fmt:formatNumber>byte)</a>
								</td>
							</tr>
						</c:if>
					  </div>
				</div>	
					<div class="content">
						<form enctype="multipart/form-data" class="form-horizontal">
							<div class="form-group">
								<div class="card-header text-center">댓글 (${reply.size()})</div>
								<div class="col-sm-16">
									<textarea name="content" rows="5" cols="100"
										class="form-control" id="replyContent"></textarea>
								</div>
								<div class="card-footer text-right">
									<button class="butona btn btn-primary" type="button"
										onclick="writeReply(${board.boardId})" style="width: 150px;">
										Reply<span class="tag"> <img
											src="https://i.ibb.co/GQf8frw/reply.png" />
										</span>
									</button>
								</div>
							</div>
						</form>
						<form enctype="multipart/form-data" class="form-horizontal">
						<div class="card-header text-center">댓글 리스트</div>
						<ul class="list-group">
							<c:forEach var="reply" items="${reply}">
								<li class="list-group-item d-flex justify-content-between" style="border-left: none; border-right:none;">
									${reply.replyId}. 작성자 : ${reply.memberName}<h6>${reply.replyDate}</h6>
									<button class="badge" type="button" style="color:black;" onclick="deleteReply(${board.boardId},${reply.replyId})">삭제</button>
								</li>
								<li class="list-group-item d-flex justify-content-between" style="border-left: none; border-right:none; border-bottom:3px solid black;"><div>${reply.content}</div></li>
								
							</c:forEach>
						</ul>
						</form>
					</div>					
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/fragment/footer.jsp"%>
