<%@ include file="/WEB-INF/views/fragment/head.jsp" %>


<%@ include file="/WEB-INF/views/fragment/nav.jsp" %>
<div class="container">
   <div class="pg-opt">
        <div class="row">
            <div class="col-md-6 pc">
                <h2><fmt:message key="WRITE_NEW_ARTICLE"/></h2>
            </div>
            <div class="col-md-6">
                <ol class="breadcrumb">
                    <li><fmt:message key="BOARD"/></li>
                    <li class="active"><fmt:message key="WRITE_NEW_ARTICLE"/></li>
                </ol>
            </div>
        </div>
    </div>
   <div class="content">
   <hr/><h1 class="headline"><c:if test="${boardType eq 'reference'}"><fmt:message key="REFERENCE"/></c:if>
   		<c:if test="${boardType eq 'community'}"><fmt:message key="COMMUNITY"/></c:if></h1><hr/>
   <div class="row">
            <div class="col-md-8 pc">
                <h2><fmt:message key="WRITE_NEW_ARTICLE"/></h2>
            </div>
            <div class="col-md-4">
                <ol class="breadcrumb">
                    <li><c:if test="${boardType eq'reference'}"><fmt:message key="REFERENCE"/></c:if>
   						<c:if test="${boardType eq'community'}"><fmt:message key="COMMUNITY"/></c:if>/</li>
                    <li class="active"><fmt:message key="WRITE_NEW_ARTICLE"/></li>
                </ol>
            </div>
        </div>
   <form action="<c:url value='/board/write'/>" method="post" enctype="multipart/form-data" class="form-horizontal">
	   <div class="form-group">
	      <label class="control-label col-sm-2" for="name"><fmt:message key="WRITER"/></label>
	      <div class="col-sm-2">
	        <input type="text" name="memberName" id="memberName" value="${sessionScope.membername}" ${!empty sessionScope.membername ? "readonly" : "" } class="form-control">
	      	<input type="hidden" name="memberId" id="memberId" value="${sessionScope.memberid}" ${!empty sessionScope.memberid ? "readonly" : "" } class="form-control">
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="title"><fmt:message key="SUBJECT"/></label>
	      <div class="col-sm-8">
	        <input type="text" name="title" id="title" class="form-control" required>
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="content"><fmt:message key="CONTENT"/></label>
	      <div class="col-sm-8">
	        <textarea name="content" rows="10" cols="100" class="form-control"></textarea>
	      </div>
	    </div>	    
		<div class="form-group">
		   <label class="control-label col-sm-2" for="photo"><fmt:message key="FILE"/></label>
		    <div class="col-sm-8">
		        <input type="file" id="i_file" name="file"><span id="droparea" class="help-block"><fmt:message key="FILESIZE_ERROR"/></span>
		    </div>
		</div>	    
	    <div class="form-group">
	       <div class="col-sm-offset-2 col-sm-8">
	         <input type="hidden" name="boardType" value="${boardType}">
	         <input type="submit" id="i_submit" class="btn btn-warning" value="<fmt:message key="SAVE"/>"> 
	         <input type="reset" class="btn btn-success" style="background-color:grey" value="<fmt:message key="CANCEL"/>" onclick="history.back()">
	      </div>
	   </div>
   </form><br/>
   </div>
</div>
<%@ include file="/WEB-INF/views/fragment/footer.jsp" %>