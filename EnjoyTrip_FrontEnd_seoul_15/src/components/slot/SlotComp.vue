<script setup></script>

<template>
	<div>
		<slot> </slot>
	</div>
</template>

<style scoped>
div {
	background-color: rgb(255, 255, 255);
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	transition: 0.3s;
	flex-basis: 150px;
	border-radius: 10px;
	border: solid black 2px;
	margin: 10px;
	padding: 20px 10px 0 10px;
	text-align: center;
	display: grid;
	place-items: center; /* 수평 및 수직 가운데 정렬 */
}
div:hover {
	box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
	background-color: rgb(221, 217, 231);
	cursor: pointer;
}
</style>
