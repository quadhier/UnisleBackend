$(document).ready(function () {
	function loadAjax() {
		selfRst=$.ajax({url:"searchResult.html",async:false});
		$("#searchRst").css("top","4px");
		$("#searchRst").html(selfRst.responseText);
	}

	$("#type_self").css("background-color","rgba(255,255,255,0.57)");
	loadAjax();
	
	$("#type_self").click(function () {
		$(this).css("background-color","rgba(255,255,255,0.57)");
		$("#type_org").css("background-color","rgba(204,204,204,0.8)");
		$("#type_post").css("background-color","rgba(204,204,204,0.8)");

		loadAjax();
	});

	$("#type_org").click(function () {
		$(this).css("background-color","rgba(255,255,255,0.57)");
		$("#type_self").css("background-color","rgba(204,204,204,0.8)");
		$("#type_post").css("background-color","rgba(204,204,204,0.8)");

		loadAjax();
	});
	
	$("#type_post").click(function () {
		$(this).css("background-color","rgba(255,255,255,0.57)");
		$("#type_org").css("background-color","rgba(204,204,204,0.8)");
		$("#type_self").css("background-color","rgba(204,204,204,0.8)");

		loadAjax();
	});

	$("input").focus(function(){
		$(this).parent().css("border-color","#bbbbff");
		$(this).parent().css("box-shadow","0 0 4px #bbbbff");
	});
	$("input").blur(function(){
		$(this).parent().css("border-color","#cccccc");
		$(this).parent().css("box-shadow","none");
	});
});