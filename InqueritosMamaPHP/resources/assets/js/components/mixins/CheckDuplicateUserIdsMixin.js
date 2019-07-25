export const checkDuplicateUserIds = {
	mounted() {
		const isUniqueUserId = (value) => {
			return axios.post('/api/admin/check-duplicate-user-ids', { 'user_id': value }).then((response) => {
				return {
					valid: response.data.valid,
					data: {
						message: response.data.message
					}
				};
			});
		};
				
		this.$validator.extend('isUniqueUserId', {
			validate: isUniqueUserId,
			getMessage: (field, params, data) => {
				return data.message;
			}
		});
	},
}