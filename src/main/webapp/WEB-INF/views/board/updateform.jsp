<%@ include file="/WEB-INF/views/fragment/head.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("p").hide();
	$("#updateFile").click(function(){
	   $("p").toggle();
	});
});
</script>
<%@ include file="/WEB-INF/views/fragment/nav.jsp" %>

<div class="container">
   <div class="pg-opt">
        <div class="row">
            <div class="col-md-6 pc">
                <h2><fmt:message key="UPDATE_ARTICLE"/></h2>
            </div>
            <div class="col-md-6">
                <ol class="breadcrumb">
                    <li><fmt:message key="BOARD"/></li>
                    <li class="active"><fmt:message key="UPDATE_ARTICLE"/></li>
                </ol>
            </div>
        </div>
    </div>
   <div class="content">
   <hr/><h1 class="headline">
   		<c:if test="${board.boardType eq 'reference'}"><fmt:message key="REFERENCE"/></c:if>
   		<c:if test="${board.boardType eq 'community'}"><fmt:message key="COMMUNITY"/></c:if>
   	</h1><hr/>
   <div class="row">
            <div class="col-md-8 pc">
                <h2><fmt:message key="UPDATE_ARTICLE"/></h2>
            </div>
            <div class="col-md-4">
                <ol class="breadcrumb">
                    <li><c:if test="${board.boardType eq 'reference'}"><fmt:message key="REFERENCE"/></c:if>
   						<c:if test="${board.boardType eq 'community'}"><fmt:message key="COMMUNITY"/></c:if>/</li>
                    <li class="active"><fmt:message key="UPDATE_ARTICLE"/></li>
                </ol>
            </div>
        </div>
   <form action="<c:url value='/board/update'/>" method="post" enctype="multipart/form-data" class="form-horizontal">
	   <div class="form-group">
	      <label class="control-label col-sm-2" for="name"><fmt:message key="WRITER"/></label>
	      <div class="col-sm-2">
	      	<input type="text" name="memberName" id="memberName" value="${sessionScope.membername}" ${!empty sessionScope.membername ? "readonly" : "" } class="form-control">
	      	<input type="hidden" name="memberId" id="memberId" class="form-control" value="${board.memberId}">
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="title"><fmt:message key="SUBJECT"/></label>
	      <div class="col-sm-8">
	        <input type="text" name="title" id="title" class="form-control" value="${board.title}" required>
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="content"><fmt:message key="CONTENT"/></label>
	      <div class="col-sm-8">
	        <textarea name="content" rows="10" cols="100" class="form-control">${board.content}</textarea>
	      </div>
	    </div>	    
		<div class="form-group">
		    <label class="control-label col-sm-2" for="photo"><fmt:message key="FILE"/></label>
		    <div class="col-sm-8">
			    <c:if test="${board.fileName!=null}">${board.fileName}&ensp; 
			   		 <a id="updateFile" class="btn" style="color:white; background-color:grey">Edit</a>
				    <p class="hide">
				      <input type="file" id="i_file" name="file" value="${board.fileName}"><span id="droparea" class="help-block"><fmt:message key="FILESIZE_ERROR"/></span>
				    </p>
			    </c:if>
			     <c:if test="${board.fileName==null}">
			      	<input type="file" id="i_file" name="file" value="${board.fileName}"><span id="droparea" class="help-block"><fmt:message key="FILESIZE_ERROR"/></span>
			     </c:if>
		     </div>
		  </div>
	    
	    <div class="form-group">
	       <div class="col-sm-offset-2 col-sm-8">
	         <input type="hidden" name="boardId" value="${board.boardId}">
	         <input type="hidden" name="boardType" value="${board.boardType}">
	         <input type="submit" id="i_submit" class="btn btn-warning" value="<fmt:message key="SAVE"/>"> 
	         <input type="reset" class="btn btn-success" style="background-color:grey" value="<fmt:message key="CANCEL"/>" onclick="history.back()">
	      </div>
	   </div>
   </form><br/>
   </div>
</div>
<%@ include file="/WEB-INF/views/fragment/footer.jsp" %>