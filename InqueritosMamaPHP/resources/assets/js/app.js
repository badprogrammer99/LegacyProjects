require('./bootstrap');

import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import VueRouter from 'vue-router';
import VeeValidate from 'vee-validate';
import Snotify, { SnotifyPosition, SnotifyToastConfig } from 'vue-snotify';

const snotifyOptions = {
	toast: {
		position: SnotifyPosition.centerTop
	}
};

const veeValidateConfig = {
	fieldsBagName: 'fieldsValidate',
};

Vue.use(BootstrapVue);
Vue.use(VueRouter);
Vue.use(VeeValidate, veeValidateConfig);
Vue.use(Snotify, snotifyOptions);

var App = Vue.component('app', require('./components/App.vue'));

var router = new VueRouter({
	mode: 'history',
	routes: [
		{ 
			path: '/', 
			name: 'home', 
			component: Vue.component('home-component', require('./components/HomeComponent.vue'))
		},
		{
			path: '/create-new-administrator',
			name: 'create.new.administrator',
			component: Vue.component('create-new-administrator', require('./components/CreateNewAdministrator.vue')),
			props: {
				addNewAdministrator: '/api/admin/create-new-administrator',
				sendEmailToAdministrator: '/api/admin/send-email-with-password'
			}
		},
		{
			path: '/create-new-patient',
			name: 'create.new.patient',
			component: Vue.component('create-new-patient', require('./components/CreateNewPatient.vue')),
			props: {
				addNewPatient: '/api/admin/create-new-patient',
				inquiriesBaseURL: '/api/admin/inquiries',
				sendEmailToUser: '/api/admin/send-email-with-password'
			}
		},
		{
			path: '/inquiry-builder',
			name: 'inquiry.builder',
			component: Vue.component('inquiry-builder', require('./components/InquiryBuilderComponent.vue')),
			props: {
				inquiriesBaseURL: '/api/admin/inquiries/'
			}
		},
		{
			path: '/inquiry/:id',
			component: Vue.component('inquiry-component', require('./components/ImprovedInquiryComponent.vue')),
			props: {
				inquiriesBaseURL: '/api/admin/inquiries/',
				questionnairesBaseURL: '/api/admin/questionnaries/'
			}
		},
		{
			path: '/inquiry/:inquiryId/questionnaire/:questionnaireId',
			component: Vue.component('questionnaire-component', require('./components/ImprovedQuestionnaireComponent.vue')),
			props: {
				inquiriesBaseURL: '/api/admin/inquiries/',
				questionsBaseURL: '/api/admin/questions/'
			}
		},
		{
			path: '/inquiry/:inquiryId/questionnaire/:questionnaireId/question/:questionId',
			component: Vue.component('question-edit-component', require('./components/EditQuestionComponent.vue')),
			props: {
				inquiriesBaseURL: '/api/admin/inquiries/',
				questionsBaseURL: '/api/admin/questions/'
			}
		},
		{
			path: '*',
			name: 'page.not.found',
			component: Vue.component('page-not-found', require('./components/errors/PageNotFound.vue'))
		}
	]
});

var toastsMixin = {
	methods: {
		showErrorToast: function() {
			this.$snotify.error('Ocorreu um erro interno durante o processamento desta ação. Tente novamente mais tarde ou contacte o administrador do serviço.', {
				timeout: 4500,
				showProgressBar: false
			});
		},
		
		showWarningToast: function() {
			this.$snotify.warning('A ação pretendida não foi realizada. Tente novamente mais tarde ou contacte o administrador do serviço.', {
				timeout: 4500,
				showProgressBar: false
			});
		}
	}
};

Vue.mixin(toastsMixin);

var app = new Vue({
    el: '#app',
    components: { App },
    router,
});
