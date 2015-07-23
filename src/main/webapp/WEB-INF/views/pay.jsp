<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<html>

<head>
<meta charset="utf-8">
<title>账单支付</title>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="resources/css/ratchet.min.css">
<link rel="stylesheet" href="resources/css/app.css">
<script src="resources/js/ratchet.min.js"></script>
<script src="resources/js/jquery-2.1.1.min.js"></script>
<style type="text/css">
.pinkbg{
border: 1px solid #ff859f !important;
background:#ff859f !important;

}
.pink{
color:#ff859f !important;
}
</style>
</head>
<body class="gray">
	<header class="bar bar-nav">
		<a class="icon icon-left-nav pull-left pink"></a>
		<h1 class="title">账单支付</h1>
	</header>
	<div class="content hblock">
		<div class="card">
				<ul class="table-view">
  <li class="table-view-cell">
				<span class="pull-left">账单号：</span><span class="pull-right">${bill.id }</span>
				<span class="clearfix"></span>
			</li>
			  <li class="table-view-cell">
				<span class="pull-left">商品：</span><span class="pull-right">${bill.orderName }</span>
				<span class="clearfix"></span>
			</li>
  <li class="table-view-cell">
				<span class="pull-left">金额：</span><span class="pull-right">${bill.amount-bill.paid }</span>

				<span class="clearfix"></span>
			</li></ul>

		</div>
		<div class="card hblock">
							<ul class="table-view">
  <li class="table-view-cell">
				<span class="pull-left">爱逛余额：</span><span class="pull-right">${account.balance }元
					<input type="checkbox" id="payByAccountCbx" name="payByAccount" />
				</span> <span class="clearfix"></span>
			</li>
  <li class="table-view-cell">
				<span class="pull-left">还需支付：</span><span class="pull-right"><font
					id="billLeft">${bill.amount-bill.paid }</font>元</span> <span
					class="clearfix"></span>
					</li></ul>

		</div>
		<span class="hideGroup" style="margin-left: 10px;"> 选择支付方式</span>
		<div class="card hideGroup  hblock">
							<ul class="table-view">
							  <li class="table-view-cell">
				<label for="pay_method_wepay" class="pull-left">微信支付</label><span class="pull-right">
					<input type="radio" id="pay_method_wepay" value="wepay" name="pay_method" /></span> <span
					class="clearfix"></span>
						</li>
  <li class="table-view-cell">
				<label for="pay_method_alipay" class="pull-left">支付宝支付</label><span class="pull-right">
					<input type="radio" id="pay_method_alipay" value="alipay" name="pay_method" checked="checked" />
				</span> <span class="clearfix"></span>
			</li>
  <li class="table-view-cell">
				<label for="pay_method_bank" class="pull-left">银行卡支付</label><span class="pull-right">
					<input type="radio" id="pay_method_bank"  value="bank" name="pay_method" /></span> <span
					class="clearfix"></span>
				</li>
</ul>
		</div>
 
		<button class="btn btn-primary btn-block pinkbg" >确认支付</button>
	</div>


</body>
<script type="text/javascript">
	var balance = ${account.balance};
	var billAmount = ${bill.amount - bill.paid};
	$(function() {
		var left = billAmount - balance;
		if (balance <= 0) {
			$("#payByAccountCbx").attr("disabled", "disabled");
		} else {
			var cbx=$("#payByAccountCbx");
			cbx.click(function() {
				recalculate();
			})
			cbx[0].checked=true;

			recalculate()
		}
		
		$(".btn").click(function(){
			
			window.location.href="https://itunes.apple.com/us/app/daniel-worldwide/id895287160?mt=8";
		});

	});
	function recalculate() {
		var left = $("#payByAccountCbx")[0].checked ? Math.max(billAmount
				- balance, 0) : billAmount;
		$("#billLeft").html(left);
		
		if (left > 0) {
			$(".hideGroup").show();
		}
		else{
			$(".hideGroup").hide();
		}
	}
</script>
</html>
