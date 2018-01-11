<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="${pageContext.request.contextPath}/img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="${pageContext.request.contextPath}/img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top: 20px">
		<ol class="list-inline">
			<c:if test="${ empty user }">
				<li><a href="${pageContext.request.contextPath }/jsp/login.jsp">登录</a></li>
				<li><a
					href="${pageContext.request.contextPath }/jsp/register.jsp">注册</a></li>
			</c:if>
			<c:if test="${ not empty user }">
				<li>${user.username }</li>
				<li><a href="${pageContext.request.contextPath }/user?method=logout">注销</a></li>
				<li><a href="${pageContext.request.contextPath }/order?method=findByPage&pageNumber=1">我的订单</a></li>
			</c:if>
			<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
		</ol>
	</div>
</div>
<!--
            	时间：2015-12-30
            	描述：导航条
            -->
   <script>
   		//1.确定事件:文档加载完成事件
   		$(function(){
   			//函数中干的事: 页面初始化
   			//1.发送AJAX请求
   			var url = "${pageContext.request.contextPath}/category";
   			var param = {"method":"findAll"};
   			$.getJSON(url,param,function(data){
   				// 向控制台打印数据
   				//console.log(data);
   				$(data).each(function(i,category){
   					//console.log(i+" == "+category)
   					// 动态添加 <li><a href="#">电脑办公</a></li>
   					$("#menu_ul").append("<li><a href='${pageContext.request.contextPath}/product?method=findByPage&pageNumber=1&cid="+category.cid+"'>"+category.cname+"</a></li>");
   				})
   			});
   		});
   </script>         
            
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="menu_ul">
					<!-- <li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li> -->
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>

			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>


