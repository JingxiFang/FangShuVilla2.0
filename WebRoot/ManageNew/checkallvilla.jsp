<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.sdcet.project.service.HouseInfoService"%>
<%@ page language="java" import="cn.sdcet.project.domain.House"%>
<%@ page language="java" import="javax.servlet.http.HttpSession"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List<House> listHouse = new ArrayList<House>();
HouseInfoService houseService = new HouseInfoService();
listHouse = houseService.getHouseInfo();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>方墅-后台管理系统</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
        
    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">

    <link href="assets/css/table-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

  <section id="container" >
      <!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
      <!--header start-->
      
		<jsp:include page="header.jsp" flush="false"></jsp:include>
        </header>
      <!--header end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
      <!--sidebar start-->
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
              
              	  <p class="centered"><a href="index.jsp"><img src="assets/img/ui-manager.png" class="img-circle" width="60"></a></p>
              	  <h5 class="centered">管理员</h5>
              	  	
                  <li class="mt">
                      <a href="index.jsp">
                          <i class="fa fa-home fa-fw"></i>
                          <span>主页</span>
                      </a>
                  </li>

                  <li class="sub-menu active">
                      <a class="active" href="javascript:;" >
                          <i class="fa fa-th"></i>
                          <span>查看别墅</span>
                      </a>
                      <ul class="sub">
                          <li class="active"><a href="checkallvilla.jsp">查看别墅列表</a></li>
                      </ul>
                  </li>
                  
                  <li class="sub-menu">
                      <a href="javascript:;" >
                          <i class="fa fa-cogs fa-fw"></i>
                          <span>别墅管理</span>
                      </a>
                      <ul class="sub">
                          <li><a  href="addvilla.jsp">新增别墅信息</a></li>
                          <li><a  href="lookvilla.jsp">修改别墅信息</a></li>
                      </ul>
                  </li>
                  <li class="sub-menu">
                      <a href="javascript:;" >
                          <i class="fa fa-tasks fa-fw"></i>
                          <span>代理人管理</span>
                      </a>
                      <ul class="sub">
                          <li><a  href="setagency.jsp">设置代理人</a></li>
                      </ul>
                  </li>
                  <li class="sub-menu">
                      <a href="javascript:;" >
                          <i class=" fa fa-bar-chart-o"></i>
                          <span>图表</span>
                      </a>
                      <ul class="sub">
                          <li><a  href="chartjs.html">Chartjs</a></li>
                      </ul>
                  </li>

              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
          	<h3><i class="fa fa-angle-right"></i> 查看全部别墅</h3>
		  		<div class="row mt">
			  		<div class="col-lg-12">
                      <div class="content-panel">
                      <h4><i class="fa fa-angle-right"></i> 别墅列表</h4>
                          <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                              <thead>
                              <tr>
                                  <th>编号</th>
                                  <th>别墅名称</th>
                                  <th>地址</th>
                                  <th class="numeric">价格</th>
                                  <th class="numeric">面积</th>
                                  <th class="numeric">户型</th>
                                  <th class="numeric">操作</th>
                              </tr>
                              </thead>
                              <tbody>
                              <%
                              for(int i=0;i<listHouse.size();i++)
                              {%>
                              
                              <tr>
                                  <td><%=listHouse.get(i).getId() %></td>
                                  <td><%=listHouse.get(i).getName() %></td>
                                  <td><%=listHouse.get(i).getH_address() %></td>
                                  <td class="numeric"><%=listHouse.get(i).getPrice()%></td>
                                  <td class="numeric"><%=listHouse.get(i).getArea() %>㎡</td>
                                  <td class="numeric"><%=listHouse.get(i).getHousetype() %></td>
								  <td><!-- 功能不实现！！！可以设置用户权限 -->
                                     <button id="btn_setgood" class="btn btn-success btn-xs" onClick="setGoodVilla(<%=listHouse.get(i).getId()%>)" >
                                      	<%if(listHouse.get(i).getFineFlag()==1){%>
                                      		<i class="fa fa-thumbs-up" ></i>
                                      	<%}else{%>
                                      	<i class="fa fa-thumbs-o-up" ></i>
                                      
                                      	<%}%>
                                      </button>
                                      <button class="btn btn-primary btn-xs" onclick="updateVilla(<%=listHouse.get(i).getId()%>)"><i class="fa fa-cog" aria-hidden="true" ></i></a>
                                      <button class="btn btn-danger btn-xs" onclick="isDelete(<%=listHouse.get(i).getId()%>)" data-toggle="modal" data-target="#myModal" ><i class="fa fa-times" aria-hidden="true"></i></button>
                                        
                                      </td>
                              </tr>
                              
                              
                              
                            <%}%>
                              
                              </tbody>
                          </table>
                          </section>
                  </div><!-- /content-panel -->
               </div><!-- /col-lg-4 -->			
		  	</div><!-- /row -->
		  	
		  	<div class="row mt">
              <div class="col-lg-12">
                      <div class="content-panel">
						  <h4><i class="fa fa-angle-right"></i> 别墅数据</h4>
                          <section id="no-more-tables">
                              <table class="table table-bordered table-striped table-condensed cf">
                                  <thead class="cf">
                                  <tr>
                                      <th>编号</th>
                                      <th>名称</th>
                                      <th class="numeric">浏览人数</th>
                                      <th class="numeric">预约人数</th>
                                      <th class="numeric">热度排名</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  
                                  <%
                              for(int i=0;i<listHouse.size();i++)
                              {%>
                              
                              <tr>
                                  <td><%=listHouse.get(i).getId() %></td>
                                  <td><%=listHouse.get(i).getName() %></td>
                                  <td><%=listHouse.get(i).getVisitCount() %></td>
                                  <td class="numeric"><%=listHouse.get(i).getOrderCount()%></td>
                                  <td class="numeric">+5%</td>
                                 
                              </tr>
                              
                              
                            <%}%>
                                  
                                  </tbody>
                              </table>
                          </section>
                      </div><!-- /content-panel -->
                  </div><!-- /col-lg-12 -->
              </div><!-- /row -->

		</section><! --/wrapper -->
      </section><!-- /MAIN CONTENT -->

      <!--main content end-->
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
              2019 - Copyright © Fangshu Project. All rights reserved.
          </div>
      </footer>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
 
    <!--common script for all pages-->
    <script src="assets/js/common-scripts.js"></script>

    <!--script for this page-->
    
    <script>
		function updateVilla(houseid){
			alert(houseid);
			document.location.href="../SearchDetailHouseInfo?houseid="+houseid;
		}
		
		function isDelete(delhouseid) {
			var r=confirm("确定要删除该别墅信息吗");
			  if (r==true)
			    {
				// alert("删除");
					$.ajax({
						url : '${pageContext.request.contextPath}/DelHouseInfo',
						type : 'post',//请求类型get或者post
						data : {"delhouseid":delhouseid},
						success : function(data) {//customer_dialog
							alert('删除成功');
							location.reload(true);
						},
						error : function() {
							alert('ajax请求失败');
						}
					});
			    }
			  else
			    {
			    return;
			    }
			
		}
		function setGoodVilla(houseid){
	    	//var setgood=document.getElementById("btn_setgood");
	    	
	    	$.ajax({
				url : '${pageContext.request.contextPath}/UpdHouseInfo',
				type : 'post',//请求类型get或者post
				data : {"action":"setfine","houseid":houseid},
				success : function(data) {//customer_dialog
					alert(data);
					
					location.reload(true);
				},
				error : function() {
					alert('设置失败');
				}
			});
	    }
	</script>
    

  </body>
</html>
