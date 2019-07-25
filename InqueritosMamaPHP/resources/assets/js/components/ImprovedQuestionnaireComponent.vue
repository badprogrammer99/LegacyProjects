<template>
  <b-row>
    <b-col md="10" offset-md="1">
      <vue-snotify></vue-snotify>
      <b-card no-body v-if="dataReady">
        <div slot="header">Edição do Questionário de {{ pageInformation.questionnaireName }}</div>
        <b-card-body>
          <b-btn variant="primary" v-b-modal.addModal>
            <i class="fas fa-plus"></i> Criar nova Questão
          </b-btn>
          <b-form inline style="padding-top: 20px;">
            <div class="form-group">
              <label style="padding-right: 5px;">Pesquisar por Questão:</label>
              <input
                type="text"
                class="form-control"
                name="pesquisar_questoes"
                :disabled="source.questions.length == 0 ? true : false"
                v-model="filter">
            </div>
          </b-form>
          <template>
            <b-table
              striped
              :items="source.questions"
              :fields="fields"
              :filter="filter"
              :responsive="true"
              style="padding-top: 20px;">
              <template
                slot="answer_names"
                slot-scope="data">{{ data.item.possible_answer_names.possible_answer_names }}</template>
              <template slot="editar" slot-scope="data">
                <b-btn variant="primary" size="sm" @click="pushEditPage(data.item.id)">
                  <i class="fas fa-edit"></i> Editar
                </b-btn>
              </template>
              <template slot="eliminar" slot-scope="data">
                <b-btn
                  variant="danger"
                  size="sm"
                  @click="setQuestionRecordBeingManipulated(data.item.id); deleteQuestionPrompt()">
                  <i class="fas fa-trash"></i> Eliminar
                </b-btn>
              </template>
            </b-table>
          </template>
        </b-card-body>
      </b-card>
    </b-col>
    <b-form @submit.prevent="createNewQuestion">
      <b-modal id="addModal" ref="addModalRef" title="Criar nova Questão">
        <b-form-group label="Inquérito">
          <b-form-input
            name="nome_inquerito"
            type="text"
            required
            :value="pageInformation.inquiryName"
            disabled></b-form-input>
        </b-form-group>
        <b-form-group label="Questionário">
          <b-form-input
            name="nome_questionario"
            type="text"
            required
            :value="pageInformation.questionnaireName"
            disabled></b-form-input>
        </b-form-group>
        <b-form-group label="Nome" label-for="nome_questao">
          <b-form-input
            name="nome_questao"
            type="text"
            required
            placeholder="Nome da Questão"
            :class="{ 'is-invalid': errors.has('nome_questao') }"
            v-validate="'isUniqueQuestionName'"
            data-vv-delay="500"
            v-model="createNewQuestionData.questionName"></b-form-input>
          <span
            v-show="errors.has('nome_questao')"
            class="text-danger">{{ errors.first('nome_questao') }}</span>
        </b-form-group>
        <b-form-group label="Descrição" label-for="descricao_questao">
          <textarea
            name="descricao_questao"
            class="form-control"
            required
            placeholder="Descrição da Questão"
            rows="3"
            v-model="createNewQuestionData.questionDescription"></textarea>
        </b-form-group>
        <b-form-group
          label="Número de respostas às quais o Utilizador poderá responder"
          label-for="numero_respostas_possiveis">
          <select
            name="numero_respostas_possiveis"
            class="form-control"
            required
            v-model="createNewQuestionData.numeroRespostasPossiveis">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
        </b-form-group>
        <div class="form-group">
          <label for="lista_respostas_possiveis">Respostas que o Utilizador poderá escolher</label>
          <textarea
            name="lista_respostas_possiveis"
            id="lista_respostas_possiveis"
            class="form-control"
            required
            placeholder="Lista de respostas que o Utilizador poderá escolher. Exemplo: A, B, C, D"
            rows="3"
            v-model="createNewQuestionData.listaRespostasPossiveis"
            v-validate="{ regex: /^([a-zA-Z0-9]+,?\s*)+$/ }"></textarea>
          <span
            v-show="errors.has('lista_respostas_possiveis')"
            class="text-danger">A lista de possíveis respostas não está corretamente escrita.</span>
        </div>
        <div slot="modal-footer">
          <b-btn
            type="submit"
            :disabled="(createNewQuestionData.listaRespostasPossiveis == '' || 
					        this.$validator.errors.any()) ? true : false"
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
          label: "Nome da Questão",
          sortable: true
        },

        description: {
          label: "Descrição",
          sortable: true
        },

        possible_answers: {
          label: "Número de Respostas Possíveis",
          sortable: true
        },

        answer_names: {
          label: "Lista de Respostas Possíveis",
          sortable: false
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
        questionsURL: this.$attrs.questionsBaseURL
      },

      source: {
        questions: []
      },

      pageInformation: {
        inquiryName: "",
        questionnaireName: ""
      },

      createNewQuestionData: {
        questionName: "",
        questionDescription: "",
        numeroRespostasPossiveis: null,
        listaRespostasPossiveis: ""
      },

      manipulateQuestion: {
        questionIdBeingManipulated: null
      },

      dataReady: false
    };
  },

  mounted() {
    const isUniqueQuestionName = value => {
      return axios
        .post("/api/admin/check-duplicate-question-names", {
          questionnaire_id: this.$route.params.questionnaireId,
          question_name: value
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

    this.$validator.extend("isUniqueQuestionName", {
      validate: isUniqueQuestionName,
      getMessage: (field, params, data) => {
        return data.message;
      }
    });
  },

  created() {
    this.fetchQuestions();
  },

  methods: {
    fetchQuestions: function() {
      window.axios
        .get(
          this.urls.inquiriesURL +
          this.$route.params.inquiryId +
          "/questionnaries/" +
          this.$route.params.questionnaireId
        )
        .then(
          response => {
            this.pageInformation.questionnaireName = response.data.data.name;
            this.pageInformation.inquiryName = response.data.data.inquiry;
            this.source.questions = response.data.data.questions;
            this.dataReady = true;
          },
          error => {
            this.$router.push({ name: "page.not.found" });
          }
        );
    },

    setQuestionRecordBeingManipulated: function(id) {
      this.manipulateQuestion.questionIdBeingManipulated = id;
    },

    createNewQuestion: function() {
      if (!this.$validator.errors.any()) {
        this.$refs.addModalRef.hide();
        let input = this.createNewQuestionData.listaRespostasPossiveis;
        let respostas = JSON.stringify(input.split(","));

        window.axios
          .post(this.urls.questionsURL, {
            questionnaire_id: this.$route.params.questionnaireId,
            question_name: this.createNewQuestionData.questionName,
            question_description: this.createNewQuestionData
              .questionDescription,
            possible_answers: this.createNewQuestionData
              .numeroRespostasPossiveis,
            possible_answer_names: this.createNewQuestionData
              .listaRespostasPossiveis
          })
          .then(
            response => {
              this.fetchQuestions();
              this.$snotify.success("Questão criada com sucesso!", {
                showProgressBar: false
              });
            },
            error => {
              this.showErrorToast();
            }
          );
        this.resetForm();
      }
    },

    deleteQuestionPrompt: function() {
      this.$snotify.confirm("Tem a certeza que deseja eliminar esta questão?", {
        timeout: 7000,
        showProgressBar: false,
        closeOnClick: false,
        pauseOnHover: true,
        buttons: [
          {
            text: "Sim",
            action: toast => (
              this.$snotify.remove(toast.id), this.deleteQuestion()
            ),
            bold: false
          },
          { text: "Não", action: toast => this.$snotify.remove(toast.id) }
        ]
      });
    },

    deleteQuestion: function() {
      window.axios
        .delete(
          this.urls.questionsURL +
            this.manipulateQuestion.questionIdBeingManipulated
        )
        .then(
          response => {
            this.fetchQuestions();
            this.$snotify.success("A questão foi eliminada com sucesso!", {
              showProgressBar: false
            });
          },
          error => {
            this.showErrorToast();
          }
        );
      this.manipulateQuestion.questionIdBeingManipulated = null;
    },

    pushEditPage: function(questionId) {
      let inquiryId = this.$route.params.inquiryId;
      let questionnaireId = this.$route.params.questionnaireId;
      this.$router.push({ path: `/inquiry/${inquiryId}/questionnaire/${questionnaireId}/question/${questionId}` });
    },

    resetForm: function() {
      this.createNewQuestionData.questionName = "";
      this.createNewQuestionData.questionDescription = "";
      this.createNewQuestionData.numeroRespostasPossiveis = null;
      this.createNewQuestionData.listaRespostasPossiveis = "";
    }
  }
};
</script>