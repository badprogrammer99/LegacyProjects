<template>
  <b-row>
    <b-col md="10" offset-md="1">
      <vue-snotify></vue-snotify>
      <b-card no-body v-if="dataReady">
        <div slot="header">Edição do Inquérito de {{ pageInformation.inquiryName }}</div>
        <b-card-body>
          <b-btn variant="primary" v-b-modal.addModal>
            <i class="fas fa-plus"></i> Criar novo Questionário
          </b-btn>
          <b-form inline style="padding-top: 20px;">
            <div class="form-group">
              <label style="padding-right: 5px;">Pesquisar por Questionário:</label>
              <input
                type="text"
                class="form-control"
                name="pesquisar_questionarios"
                :disabled="source.questionnaires.length == 0 ? true : false"
                v-model="filter">
            </div>
          </b-form>
          <template>
            <b-table
              striped
              :items="source.questionnaires"
              :fields="fields"
              :filter="filter"
              :responsive="true"
              style="padding-top: 20px;">
              <template slot="editar" slot-scope="data">
                <b-btn variant="primary" size="sm" @click="pushQuestionsPage(data.item.id)">
                  <i class="fas fa-edit"></i> Editar
                </b-btn>
              </template>
              <template slot="eliminar" slot-scope="data">
                <b-btn
                  variant="danger"
                  size="sm"
                  @click="setQuestionnaireRecordBeingManipulated(data.item.id); deleteQuestionnairePrompt()">
                  <i class="fas fa-trash"></i> Eliminar
                </b-btn>
              </template>
            </b-table>
          </template>
        </b-card-body>
      </b-card>
    </b-col>
    <b-form @submit.prevent="createNewQuestionnaire">
      <b-modal id="addModal" ref="addModalRef" title="Criar novo Questionário">
        <b-form-group label="Inquérito">
          <b-form-input
            name="nome_inquerito"
            type="text"
            required
            :value="pageInformation.inquiryName"
            disabled></b-form-input>
        </b-form-group>
        <b-form-group label="Nome" label-for="nome_questionario">
          <b-form-input
            name="nome_questionario"
            type="text"
            required
            placeholder="Nome do Questionário"
            :class="{ 'is-invalid': errors.has('nome_questionario') }"
            v-validate="'isUniqueQuestionnaireName'"
            data-vv-delay="500"
            v-model="createNewQuestionnaireData.questionnaireName"></b-form-input>
          <span
            v-show="errors.has('nome_questionario')"
            class="text-danger">{{ errors.first('nome_questionario') }}</span>
        </b-form-group>
        <div slot="modal-footer">
          <b-btn
            type="submit"
            :disabled="this.$validator.errors.any() ? true : false"
            variant="primary">Criar</b-btn>
        </div>
      </b-modal>
    </b-form>
  </b-row>
</template>

<script>
export default {
  data() {
    return {
      fields: {
        name: {
          label: "Nome do Questionário",
          sortable: true
        },

        editar: {
          label: "Editar",
          sortable: false
        },

        eliminar: {
          label: "Eliminar",
          sortable: false
        }
      },

      filter: "",

      urls: {
        inquiriesURL: this.$attrs.inquiriesBaseURL,
        questionnairesURL: this.$attrs.questionnairesBaseURL
      },

      source: {
        questionnaires: []
      },

      pageInformation: {
        inquiryName: ""
      },

      createNewQuestionnaireData: {
        questionnaireName: ""
      },

      manipulateQuestionnaire: {
        questionnaireIdBeingManipulated: null
      },

      dataReady: false
    };
  },

  mounted() {
    const isUniqueQuestionnaireName = value => {
      return axios
        .post("/api/admin/check-duplicate-questionnaire-names", {
          inquiry_id: this.$route.params.id,
          questionnaire_name: value
        })
        .then(response => {
          return {
            valid: response.data.valid,
            data: {
              message: response.data.message
            }
          };
        });
    };

    this.$validator.extend("isUniqueQuestionnaireName", {
      validate: isUniqueQuestionnaireName,
      getMessage: (field, params, data) => {
        return data.message;
      }
    });
  },

  created() {
    this.fetchQuestionnaires();
  },

  methods: {
    fetchQuestionnaires: function() {
      window.axios.get(this.urls.inquiriesURL + this.$route.params.id).then(
        response => {
          this.pageInformation.inquiryName = response.data.data.name;
          this.source.questionnaires = response.data.data.questionnaries;
          this.dataReady = true;
        },
        error => {
          this.$router.push({ name: "page.not.found" });
        }
      );
    },

    setQuestionnaireRecordBeingManipulated: function(id) {
      this.manipulateQuestionnaire.questionnaireIdBeingManipulated = id;
    },

    createNewQuestionnaire: function() {
      this.$refs.addModalRef.hide();
      window.axios
        .post(this.urls.questionnairesURL, {
          inquiry_id: this.$route.params.id,
          questionnarie_name: this.createNewQuestionnaireData.questionnaireName
        })
        .then(
          response => {
            this.fetchQuestionnaires();
            this.$snotify.success("Questionário criado com sucesso!", {
              showProgressBar: false
            });
          },
          error => {
            this.showErrorToast();
          }
        );
      this.resetForm();
    },

    pushQuestionsPage: function(questionnaireId) {
      let inquiryId = this.$route.params.id;
      this.$router.push({
        path: `/inquiry/${inquiryId}/questionnaire/${questionnaireId}`
      });
    },

    deleteQuestionnairePrompt: function() {
      this.$snotify.confirm(
        "Tem a certeza que deseja eliminar este questionário?",
        {
          timeout: 7000,
          showProgressBar: false,
          closeOnClick: false,
          pauseOnHover: true,
          buttons: [
            {
              text: "Sim",
              action: toast => (
                this.$snotify.remove(toast.id), this.deleteQuestionnaire()
              ),
              bold: false
            },
            { text: "Não", action: toast => this.$snotify.remove(toast.id) }
          ]
        }
      );
    },

    deleteQuestionnaire: function() {
      window.axios
        .delete(
          this.urls.questionnairesURL +
            this.manipulateQuestionnaire.questionnaireIdBeingManipulated
        )
        .then(
          response => {
            this.fetchQuestionnaires();
            this.$snotify.success("O questionário foi eliminado com sucesso!", {
              showProgressBar: false
            });
          },
          error => {
            this.showErrorToast();
          }
        );
      this.manipulateQuestionnaire.questionnaireIdBeingManipulated = null;
    },

    resetForm: function() {
      this.createNewQuestionnaireData.questionnaireName = "";
    }
  }
};
</script>