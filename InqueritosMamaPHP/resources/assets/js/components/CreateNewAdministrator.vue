<template>
  <b-row>
    <b-col md="10" offset-md="1">
      <vue-snotify></vue-snotify>
      <b-card no-body>
        <div slot="header">Criar novo Administrador</div>
        <b-card-body>
          <p>Um administrador será responsável por gerir todo o processo de registo de inquéritos, questionários, questões, e pacientes.</p>
          <b-form @submit.prevent="createNewAdministrator" v-if="show">
            <b-form-group label="Número" label-for="numero_administrador">
              <b-form-input
                name="numero_administrador"
                type="number"
                required
                placeholder="Número do Administrador"
                :class="{'is-invalid': errors.has('numero_administrador') }"
                :disabled="locked == true ? true : false"
                v-validate="'isUniqueUserId'"
                data-vv-delay="500"
                v-model="administratorNumber"></b-form-input>
              <span
                v-show="errors.has('numero_administrador')"
                class="text-danger">{{ errors.first('numero_administrador') }}</span>
            </b-form-group>
            <b-form-group label="Nome" label-for="nome_administrador">
              <b-form-input
                name="nome_administrador"
                type="text"
                required
                placeholder="Nome do Administrador"
                :disabled="locked == true ? true : false"
                v-model="administratorName"></b-form-input>
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
      @submit.prevent="sendEmailWithToast(sendEmailToAdministratorBaseURL, administratorNumber, administratorEmail)">
      <b-modal
        id="sendEmailModal"
        ref="sendEmailModalRef"
        title="Introdução do e-mail do administrador para envio de password"
        :no-close-on-backdrop="true"
        :no-close-on-esc="true"
        :hide-header-close="true">
        
        <b-form-group
          label="Introduza o e-mail do administrador para envio de password:"
          label-for="email_administrador">
          <b-form-input
            name="email_administrador"
            type="email"
            required
            placeholder="Email do Administrador"
            v-model="administratorEmail"></b-form-input>
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
      addAdministratorBaseURL: this.$attrs.addNewAdministrator,
      sendEmailToAdministratorBaseURL: this.$attrs.sendEmailToAdministrator,
      administratorNumber: "",
      administratorName: "",
      administratorEmail: "",
      show: true,
      locked: false
    };
  },

  methods: {
    createNewAdministrator: function() {
      window.axios
        .post(this.addAdministratorBaseURL, {
          admin_id: this.administratorNumber,
          admin_name: this.administratorName
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
      this.administratorNumber = "";
      this.administratorName = "";
      this.administratorEmail = "";
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