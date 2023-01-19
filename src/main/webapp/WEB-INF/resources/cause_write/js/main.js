$(function(){
	/*$("#wizard").steps({
        headerTag: "h2",
        bodyTag: "section",
        transitionEffect: "fade",
        enableAllSteps: true,
        transitionEffectSpeed: 500,
        labels: {
            finish: "Submit",
            next: "Forward",
            previous: "Backward"
        }
    });
	$('.wizard > .steps li a').click(function(){
    	$(this).parent().addClass('checked');
		$(this).parent().prevAll().addClass('checked');
		$(this).parent().nextAll().removeClass('checked');
    });
    // Custome Jquery Step Button
    $('.forward').click(function(){
    	$("#wizard").steps('next');
    })
    $('.backward').click(function(){
        $("#wizard").steps('previous');
    })
    // Select Dropdown
    $('html').click(function() {
    	console.log("Aa");
        $('.select .dropdown').hide(); 
    });
    $('.select').click(function(event){
        event.stopPropagation();
    });
    $('.select .select-control').click(function(){
        $(this).parent().next().toggle();
    })    
    $('.select .dropdown li').click(function(){
        $(this).parent().toggle();
        var text = $(this).attr('rel');
        $(this).parent().prev().find('div').text(text);
    })*/
    
    //이미지 미리보기
	$('#file').on('change', function() {
	    ext = $(this).val().split('.').pop().toLowerCase(); //확장자
	    console.log("확장자: "+ext);
	    //배열에 추출한 확장자가 존재하는지 체크
	    if($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
	        resetFormElement($(this)); //폼 초기화
	        window.alert('이미지 파일이 아닙니다! (gif, png, jpg, jpeg 만 업로드 가능)');
	    } else {
	    	var file = event.target.files[0]; //event인터페이스의 target속성 => event가 발생한 객체
	        var reader = new FileReader(); 
	        
	        reader.onload = function(e) {
	            $("#preview").attr("src", e.target.result);
	        }
	        reader.readAsDataURL(file);
	    }
	});
})
    
   
