<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="manage-nav">
	<div>
		<h3>订单管理</h3>
		<ul>
			<li><a href="${imall_path}../back/trade/tosendlist"
				target="rightFrame">待发货的订单</a>
			</li>
			<li><a href="${imall_path}../back/trade/sendinglist"
				target="rightFrame">送货中的订单</a>
			</li>
			<li><a href="${imall_path}../back/trade/finishlist"
				target="rightFrame">已完成的订单</a>
			</li>
			<li><a href="${imall_path}../back/trade/cancellist"
				target="rightFrame">取消的订单</a>
			</li>
		</ul>
	</div>
	<div>
		<h3>商品管理</h3>
		<ul>
			<li><a href="${imall_path}../product/create" target="rightFrame">创建商品</a>
			</li>
			<li><a href="${imall_path}../product/delete" target="rightFrame">删除商品</a>
			</li>
		</ul>
	</div>
	<div>
		<h3>广告管理</h3>
		<ul>
			<li><a href="${imall_path}../advertise/create" target="rightFrame">添加广告</a>
			</li>
			<li><a href="${imall_path}../advertise/list" target="rightFrame">删除广告</a>
			</li>
		</ul>
	</div>
</div>