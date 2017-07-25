$(document).ready(function () {

	/*
	*
	*  展示函数定义
	*
	* */
	
    //组长权限
    function mainAdmin() {
        $("#horizonLine").show();
        $(".mainAdminDiv").show();
        $("#setManager").show();
        $("#handOver").show();
        $("#container").css("height","725px");

    }

    //创建组织
    function createGroup() {
    	$("#groupMessageDiv").hide();
        $(".mainAdminDiv").append("<div id=\"createBtn\" class=\"Btn\"><span>创 建</span></div>");
        $("#createBtn").click(function () {
            alert("");
        })
    }
    
    //解散组织
    function dismissGroup() {
        $(".mainAdminDiv").append("<div id=\"dismissBtn\" class=\"Btn\"><span>解 散</span></div>");
        $("#dismissBtn").click(function () {
            alert("");
        })
    }

    //成员权限
    function memberEntry() {
        $(".Btn").css("display","none");
        $("#addImg").css("display","none");
        $(".baseInfoRight").css("display","none");
        $(".baseInfoLeft").css("left","140px");
        $("#verticalLine").css("height","545px");
        $("textarea").attr("readonly","readonly");
        $("#horizonLine").show();
        $(".mainAdminDiv").append("<div id=\"exitBtn\" class=\"Btn\"><span>退 出</span></div>");
        
        $("#container").css("height","725px");
        $(".mainAdminDiv").css("display","block");
        $("#exitBtn").click(function () {
            alert("");
        })
    }

    // 陌生人权限
    function strangerEntry() {
        $(".Btn").css("display","none");
        $("#addImg").css("display","none");
        $(".baseInfoRight").css("display","none");
        $(".baseInfoLeft").css("left","140px");
        $("#verticalLine").css("height","545px");
        $("textarea").attr("readonly","readonly");
        $("#horizonLine").show();
        $(".mainAdminDiv").append("<div id=\"addBtn\" class=\"Btn\"><span>加 入</span></div>");
        $("#container").css("height","725px");
        $(".mainAdminDiv").css("display","block");
        $("#addBtn").click(function () {
            alert("");
        })
    }
    
    //动态加载成员卡片函数
    function appendMemberCard(place){
    	
    	//在这请求所有成员列表...
    	var jsonMemberInfo='[{"nickname":"GoBang","mailBox":"goBang@163.com","admin":"false","userID":"o1"},'+
			'{"nickname":"Miku","mailBox":"mikujam@sina.com","admin":"false","userID":"o2"},'+
			'{"nickname":"Kid","mailBox":"kid@sina.com","admin":"false","userID":"o3"},'+
			'{"nickname":"friendA","mailBox":"627695620@qq.com","admin":"false","userID":"o4"},'+
			'{"nickname":"gobang","mailBox":"gobang@qq.com","admin":"false","userID":"o5"},'+
			'{"nickname":"xx","mailBox":"xx@mrayyg.com","admin":"false","userID":"o6"},'+
			'{"nickname":"zz","mailBox":"zz@sina.com","admin":"true","userID":"o7"},'+
			'{"nickname":"Kudo","mailBox":"kudo@qq.com","admin":"true","userID":"o8"}'+
			']';
    	
    	$.each(JSON.parse(jsonMemberInfo),function(index,obj){
    		var color="#666666";
    		if(obj.admin==="true"){
    			color="#FF6489"
    		} 
    		
    		if(place==="head"){
    			$(".common_memberList").append('<div class="memberCard" id="' + obj.userID + '" style="color:' + color + '">'+
    									    		'<span>' + obj.nickname + '</span><br /><span>' + obj.mailBox + '</span>'+
    									   	   '</div>');
    			
    		}else if(place==="ALL"){
    			$(".memberContainer").append('<div class="memberCard" id="' + obj.userID + '" style="color:' + color + '">'+
			    		'<span>' + obj.nickname + '</span><br /><span>' + obj.mailBox + '</span>'+
			   	   '</div>');

    		}else{
    			if(obj.admin==="false"){
    				$(".common_memberList").append('<div class="memberCard" id="' + obj.userID + '" style="color:' + color + '">'+
				    		'<span>' + obj.nickname + '</span><br /><span>' + obj.mailBox + '</span>'+
				   	   '</div>');
    			}
    			
    		}
    	});
    	
    	
    }
    
    //操作处理函数
    function opPro(){
    	
    	appendMemberCard("ALL");
    	//以下写click事件...
    	$(".memberListContainer .memberCard").live('click',function () {
            if($(this).parent().attr('class') === "common_memberList"){
            	
            	$(".onSelect_memberList").append($(this).clone());
                $(this).remove();
            }
            else {
            	
                $(".common_memberList").append($(this).clone());
                $(this).remove();
            }
        });
    	
 
    	$("#setManager").click(function(){
    		var opRes = '{"selectID":[';
    		$(".onSelect_memberList .memberCard").each(function(){
    			opRes += ('"'+$(this).attr('id')+'",');
    		});
    		opRes = opRes.substr(0,opRes.length-1);
    		opRes += "]}";
    		
    		//在此向后台传输   设置管理员   数据.....
    		alert(opRes);
    	});
    	$("#handOver").click(function(){
    		var opRes = '{"selectID":[';
    		$(".onSelect_memberList .memberCard").each(function(){
    			opRes += ('"'+$(this).attr('id')+'",');
    		});
    		opRes = opRes.substr(0,opRes.length-1);
    		opRes += "]}";
    		
    		//在此向后台传输   传递组长   数据.....
    		alert(opRes);
    	});
    	$("#kickOut").click(function(){
    		var opRes = '{"selectID":[';
    		$(".onSelect_memberList .memberCard").each(function(){
    			opRes += ('"'+$(this).attr('id')+'",');
    		});
    		opRes = opRes.substr(0,opRes.length-1);
    		opRes += "]}";
    		
    		//在此向后台传输   踢出成员   数据.....
    		alert(opRes);
    	});
    	
    	$("#o_name_btn").click(function(){
    		//在此向后台传输   组织改名   数据.....
    		alert($("#o_name").val());
    		$("#o_name").val("");
    		
    	});
    	$("#o_invite_btn").click(function(){
    		//在此向后台传输   邀请成员   数据.....
    		alert($("#o_invite").val());
    		$("#o_name").val("");
    	});
    	$("#o_tag_btn").click(function(){
    		//在此向后台传输   组织标签   数据.....
    		alert($("#o_tag").val());
    	});
    	$("#o_belong_btn").click(function(){
    		//在此向后台传输   组织所属   数据.....
    		alert($("#o_belong").val());
    	});
    	$("#o_inform_btn").click(function(){
    		//在此向后台传输   组织公告   数据.....
    		alert($("#o_inform").val());
    	});
    	$("#o_description_btn").click(function(){
    		//在此向后台传输   组织简介   数据.....
    		alert($("#o_description").val());
    	});
    }
    
    function opLoading(){
    	var jsonObj_OrgInfo='{"o_name":"Knight","o_tag":"eat","o_belong":"whu",'+
    						'"o_inform":"nothing","o_description":"we like eating!","o_pic":"000"}';
    	alert(jsonObj_OrgInfo);
    	var json = JSON.parse(jsonObj_OrgInfo);
    	$("#o_orgName").text(json.o_name);
    	$("#o_tag").val(json.o_tag);
    	$("#o_belong").val(json.o_belong);
    	$("#o_inform").val(json.o_inform);
    	$("#o_description").val(json.o_description);
    }
  
    
    //预定义函数结束

    //以下与后台接口
    
    //check...

    $.ajax({
        type: "GET",
        url: "group",
        dataType: "json",
        data: {
            "groupid": groupid
        },
        async:false,
        timeout: 5000,
        cache: false,
        beforeSend: function () {

        },
        error: function () {

        },
        success: function (res) {

            if(res.result === "LOGINERROR") {
                window.location.replace("home.html");
            } else if(res.result === "SUCCESS") {
                var group = res.data;
                $("#o_orgName").text(group.name);
                $("#o_tag").val(group.tag);
                $("#o_belong").val(group.school + "  " + group.department);
                $("#o_inform").val("");
                $("#o_description").val(group.description);


                //check the user's privilege    head-组长/admin-管理员/creator-创建时的选项/stranger-陌生人选项/member-成员选项


                if(json.privilege === "head"){
                    mainAdmin();
                    dismissGroup();

                    opLoading();
                    appendMemberCard("head");

                    opPro();


                }else if(json.privilege === "member"){
                    memberEntry();
                    opLoading();
                }else if(json.privilege === "creator"){
                    mainAdmin();
                    createGroup();
                    opPro();
                }else if(json.privilege === "admin"){
                    appendMemberCard("admin");
                    opLoading();
                    opPro();

                }else if(json.privilege === "stranger"){
                    strangerEntry();
                    opLoading();

                }else{
                    alert("ERROR");
                }


            }else{

                window.location.replace("login.html");
            }

        }

    });



    
    
    $(".triangle-right").click(function () {
        if($(this).attr('id')==="memberManage"){
        	var dh = $(document).height();
        	$(".clickHide").css({
        		width:"100%",
        		height:dh+"px"
        	});
            $(".memberListContainer").show();
        }
        else {
            if($(this).parents(".headerDiv").children(".hiddenDiv").css("display") === "none")
                $(this).css("transform", "rotate(90deg)");
            else
                $(this).css("transform", "rotate(0)");
            $(this).parents(".headerDiv").children(".hiddenDiv").toggle(200);
        }

    });

    $(".clickHide").click(function () {
        $(".memberListContainer").hide();
    })


});