<template>
	<div>
		<body>
			<div class="flex-center position-ref full-height">
				<div class="content">
					<div class="title">
						A página solicitada não foi encontrada.
					</div>
				</div>
			</div>
		</body>
	</div>
</template>

<style scoped>
	html, body {
		font-family: 'Nunito', sans-serif;
		font-weight: 100;
		height: 100vh;
		margin: 0;
	}

	.full-height {
		height: 100vh;
	}

	.flex-center {
		align-items: center;
		display: flex;
		justify-content: center;
	}

	.position-ref {
		position: relative;
	}

	.content {
		text-align: center;
	}

	.title {
		font-size: 36px;
		padding: 20px;
	}
</style>
