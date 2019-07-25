export const sendEmail = {
	methods: {
		async sendEmailToUser(sendEmailToUserURL, numeroUtilizador, emailUtilizador) {
			return window.axios.post(sendEmailToUserURL, {
				'user_id': numeroUtilizador,
				'user_email': emailUtilizador
			})
			.then((response) => {
				return response.data.status;
			}, (error) => {
				return false;
			});
		},
		
		sendEmailWithToast: function(sendEmailToUserURL, numeroUtilizador, emailUtilizador) {
			this.$refs.sendEmailModalRef.hide();
			this.$snotify.async('A enviar e-mail...', () => new Promise(async (resolve, reject) => {
				let sendEmaiStatus = await this.sendEmailToUser(sendEmailToUserURL, numeroUtilizador, emailUtilizador);
				this.resetForm();
				if (sendEmaiStatus) {
					return resolve({
						body: 'O email foi enviado com sucesso!',
						config: {
							timeout: 3000,
							closeOnClick: true
						}
					});
				} else {
					return reject({
						body: 'O email não foi enviado. Tente mais tarde ou tente outro e-mail.',
						config: {
							timeout: 3000,
							closeOnClick: true
						}
					});
				}
			}));
		},
	}
}
