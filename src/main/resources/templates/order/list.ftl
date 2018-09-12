
<html>
    <#--<head>-->
        <#--<meta charset="UTF-8">-->
        <#--<link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">-->
    <#--</head>-->

    <#include "../common/header.ftl" >

    <body>

        <div id="wrapper" class="toggled">
            <#--左边菜单-->
            <#--sidebar-->
            <#include "../common/nav.ftl">

            <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>

                                <tbody>
                        <#list orderDTOPage.content as orderDTO>
                           <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getOrderStateEnum().getMessage()}</td>
                            <td>${orderDTO.getPayStatusEnum().getMessage()}</td>
                            <td>${orderDTO.createTime}</td>
                            <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                            <td>
                                <#if orderDTO.getOrderStatus()==0>
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                </#if>
                            </td>
                        </#list>

                                </tbody>
                            </table>
                            <ul class="pagination">
                        <#if currentPage lte 1 >
                            <li class="disabled"><a href="#">Prev</a></li>
                        <#else >
                            <li><a href="/sell/seller/order/list?page=${currentPage-1}">Prev</a></li>
                        </#if>


                        <#list 1..orderDTOPage.getTotalPages() as temp>
                            <#if currentPage== temp>
                                    <li class="disabled">
                                        <a href="#">${temp}</a>
                                    </li>
                            <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${temp}">${temp}</a>
                                    </li>
                            </#if>
                        </#list>

                        <#if currentPage gte orderDTOPage.getTotalPages() >
                            <li class="disabled"><a href="#">Next</a></li>
                        <#else >
                            <li><a href="/sell/seller/order/list?page=${currentPage+1}">Next</a></li>
                        </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div>



    </body>
</html>