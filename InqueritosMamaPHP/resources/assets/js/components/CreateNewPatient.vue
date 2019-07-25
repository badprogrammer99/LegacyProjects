<template>
  <b-row>
    <b-col md="10" offset-md="1">
      <vue-snotify></vue-snotify>
      <b-card no-body>
        <div slot="header">Criar novo Paciente</div>
        <b-card-body>
          <p>Um paciente será o utilizador que irá utilizar a aplicação móvel para responder a inquéritos e questionários criados por administradores.</p>
          <b-form @submit.prevent="createNewUser" v-if="show">
            <b-form-group label="Número" label-for="numero_paciente">
              <b-form-input
                name="numero_paciente"
                type="number"
                required
                placeholder="Número do Paciente"
                :class="{'is-invalid': errors.has('numero_paciente') }"
                :disabled="locked == true ? true : false"
                v-validate="'isUniqueUserId'"
                data-vv-delay="500"
                v-model="patientNumber"></b-form-input>
              <span
                v-show="errors.has('numero_paciente')"
                class="text-danger">{{ errors.first('numero_paciente') }}</span>
            </b-form-group>
            <b-form-group label="Nome" label-for="nome_paciente">
              <b-form-input
                name="nome_paciente"
                type="text"
                required
                placeholder="Nome do Paciente"
                :disabled="locked == true ? true : false"
                v-model="patientName"></b-form-input>
            </b-form-group>
            <b-form-group
              label="Inquérito ao qual o Paciente deverá estar associado:"
              label-for="select_inquiry">
              <b-form-select
                name="select_inquiry"
                :options="inquiries"
                required
                value-field="id"
                text-field="name"
                :disabled="locked == true ? true : false"
                v-model="inquiryId"></b-form-select>
            </b-form-group>
            <b-btn
              type="submit"
              :disabled="locked == true ? true : (this.$validator.errors.any() ? true : false)"
              variant="primary">Enviar</b-btn>
          </b-form>
        </b-card-body>
      </b-card>
    </b-col>
    <b-form
      @submit.prevent="sendEmailWithToast(sendEmailToUserBaseURL, patientNumber, patientEmail)">
      <b-modal
        id="sendEmailModal"
        ref="sendEmailModalRef"
        title="Introdução do e-mail do Paciente para envio de password"
        :no-close-on-backdrop="true"
        :no-close-on-esc="true"
        :hide-header-close="true">
				
        <b-form-group
          label="Introduza o e-mail do Paciente para envio de password:"
          label-for="email_paciente">
          <b-form-input
            name="email_paciente"
            type="email"
            required
            placeholder="Email do Paciente"
            v-model="patientEmail"></b-form-input>
        </b-form-group>
        <div slot="modal-footer">
          <b-btn type="submit" variant="primary">Enviar</b-btn>
        </div>
      </b-modal>
    </b-form>
  </b-row>
</template>

<script>
import { checkDuplicateUserIds } from "./mixins/CheckDuplicateUserIdsMixin";
import { sendEmail } from "./mixins/SendEmailMixin";

export default {
  mixins: [checkDuplicateUserIds, sendEmail],

  data() {
    return {
      inquiries: [],
      addPatientBaseURL: this.$attrs.addNewPatient,
      inquiriesURL: this.$attrs.inquiriesBaseURL,
      sendEmailToUserBaseURL: this.$attrs.sendEmailToUser,
      inquiryId: null,
      patientNumber: "",
      patientName: "",
      patientEmail: "",
      show: true,
      locked: false
    };
  },

  created() {
    this.fetchInquiries();
  },

  methods: {
    fetchInquiries: function() {
      window.axios.get(this.inquiriesURL).then(
        response => {
          this.inquiries = response.data.data;
        },
        error => {
          this.showErrorToastAndLockForm();
        }
      );
    },

    createNewUser: function() {
      window.axios
        .post(this.addPatientBaseURL, {
          inquiry_id: this.inquiryId,
          patient_id: this.patientNumber,
          patient_name: this.patientName
        })
        .then(
          response => {
            if (response.data.status == true) {
              this.$refs.sendEmailModalRef.show();
            } else {
              this.showWarningToast();
            }
          },
          error => {
            this.showErrorToastAndLockForm();
          }
        );
    },

    resetForm: function() {
      this.inquiryId = null;
      this.patientNumber = "";
      this.patientName = "";
      this.patientEmail = "";
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },

    showErrorToastAndLockForm: function() {
      this.showErrorToast();
      this.locked = true;
    }
  }
};
</script>